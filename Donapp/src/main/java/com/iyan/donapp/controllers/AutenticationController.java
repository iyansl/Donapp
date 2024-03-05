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
import com.iyan.donapp.services.EmailService;
import com.iyan.donapp.services.UserService;

@Controller
public class AutenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    
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
        if (userService.getUserByEmail(dto.getEmail()) != null) {
            return "redirect:/registrarse?error1";
        } else if (userService.getUserByUsername(dto.getUsername()) != null) {
            return "redirect:/registrarse?error";
        } else if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return "redirect:/registrarse?errorCoincidenciaPass";
        } else {
            String token = userService.saveUserToBeVerified(dto);
            String confirmationLink = "https://donapp-25d4421f1d6f.herokuapp.com/registrarse/" + token;
            String emailBody = "<p>Por favor, haz clic en el siguiente enlace para confirmar tu registro a Donapp:</p>" +
                               "<p><a href=\"" + confirmationLink + "\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; border-radius: 5px;\">Confirmar Registro</a></p>";
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
            emailService.sendMail(user.getEmail(), "Donapp - Te damos la bienvenida", "¡Te damos la bienvenida a Donapp!, te has registrado correctamente y ya puedes utilizar nuestros servicios.");
            return "redirect:/iniciarsesion?exito";
        }
    }

    @PostMapping("/recuperarcontrasena")
    public String solicitarRecuperacionContrasena(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/recuperarcontrasena?error";
        } else {
            String token = userService.generatePasswordResetToken(user);
            String resetLink = "http://localhost:8080/recuperarcontrasena/" + token;
            String emailBody = "<p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>" +
                               "<p><a href=\"" + resetLink + "\">Restablecer Contraseña</a></p>";
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
    public String resetearContrasena(@PathVariable String token, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/recuperarcontrasena?errorCoincidenciaPass";
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
