package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iyan.donapp.model.User;
import com.iyan.donapp.model.dto.UserRegistroDto;
import com.iyan.donapp.services.UserService;
import com.iyan.donapp.services.email.EmailSender;
import com.iyan.donapp.services.validation.AuthenticationValidation;

@Controller
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailSender emailService;
	
	@Autowired
	private AuthenticationValidation validador;

	@ModelAttribute("user")
	public UserRegistroDto nuevoUserRegistroDto() {
		return new UserRegistroDto();
	}

	@GetMapping("/registrarse")
	public String mostrarRegistro() {
		return "registrarse";
	}

	@PostMapping("/registrarse")
	public String registrarse(@ModelAttribute("user") UserRegistroDto dto) {
		if (!validador.validarEmailRegistro(dto)) {
			return "redirect:/registrarse?error1";
		} else if (!validador.validarUsernameRegistro(dto)) {
			return "redirect:/registrarse?error";
		} else if (!validador.validarPasswordsCoinciden(dto)) {
			return "redirect:/registrarse?errorCoincidenciaPass";
		} else if (!validador.validarPasswordRegistro(dto)) {
			return "redirect:/registrarse?errorPassDebil";
		} else if (!validador.validarEmailFormatRegistro(dto)) {
			return "redirect:/registrarse?errorEmailIncorrecto";
		} else {
			String token = userService.saveUserToBeVerified(dto);
			String confirmationLink = "https://donapp-25d4421f1d6f.herokuapp.com/registrarse/" + token;
			String emailBody = "<p>Por favor, haz clic en el siguiente enlace para confirmar tu registro a Donapp:</p>"
					+ "<p><a href=\"" + confirmationLink
					+ "\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px;\">Confirmar Registro</a></p>";
			emailService.sendMail(dto.getEmail(), "Confirmación de Registro", emailBody);
			return "redirect:/iniciarsesion?confirmacionNecesaria";
		}
	}

	@GetMapping("/registrarse/{token}")
	public String confirmarRegistro(@PathVariable String token) {
		User user = userService.confirmUserByToken(token);
		if (user == null) {
			System.out.println("User: null");
			return "redirect:/registrarse?errorConfirmacion";
		} else {
			System.out.println("User: " + user.getEmail());
			userService.saveConfirmedUser(user);
			emailService.sendMail(user.getEmail(), "Donapp - Te damos la bienvenida",
					"¡Te damos la bienvenida a Donapp!, te has registrado correctamente y ya puedes utilizar nuestros servicios.");
			return "redirect:/iniciarsesion?exito";
		}
	}

	@PostMapping("/recuperarcontrasena")
	public String solicitarRecuperacionContrasena(@RequestParam("email") String email) {
		if (!validador.validarEmailFormatRegistro(email)) {
			return "redirect:/recuperarcontrasena?errorFormatoEmail";
		}
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return "redirect:/recuperarcontrasena?error";
		} else {
			String token = userService.generatePasswordResetToken(user);
			String resetLink = "https://donapp-25d4421f1d6f.herokuapp.com/recuperarcontrasena/" + token;
			String emailBody = "<p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>"
					+ "<p><a href=\"" + resetLink + "\">Restablecer Contraseña</a></p>";
			emailService.sendMail(email, "Restablecer Contraseña", emailBody);
			return "redirect:/recuperarcontrasena?exito";
		}
	}

	@GetMapping("/recuperarcontrasena")
	public String mostrarFormularioRecuperacion() {
		return "recuperarcontrasena";
	}

	@GetMapping("/recuperarcontrasena/{token}")
	public String mostrarFormularioRecuperacion(@PathVariable String token, Model model) {
		User user = userService.getUserByPasswordResetToken(token);
		if (user == null) {
			return "redirect:/recuperarcontrasena?errorTokenUsado";
		} else {
			model.addAttribute("token", token);
			return "formulario-recuperacion";
		}
	}

	@PostMapping("/recuperarcontrasena/{token}")
	public String resetearContrasena(@PathVariable String token, @RequestParam("password") String password,
			@RequestParam("confirmPassword") String confirmPassword) {
		if (!validador.validarPasswordsCoinciden(password, confirmPassword)) {
			return "redirect:/recuperarcontrasena?errorCoincidenciaPass";
		}
		if (!validador.validarPasswordRegistro(password)) {
			return "redirect:/recuperarcontrasena?errorPass";
		}
		User user = userService.getUserByPasswordResetToken(token);
		if (user == null) {
			return "redirect:/recuperarcontrasena?errorToken";
		} else {
			userService.resetPassword(user, password);
			return "redirect:/iniciarsesion?exitoRestablecimiento";
		}
	}

}
