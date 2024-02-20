package com.iyan.donapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        } else {
            String token = userService.saveUserToBeVerified(dto);
            String confirmationLink = "http://localhost:8080/registrarse/" + token;
            String emailBody = "<p>Por favor, haz clic en el siguiente enlace para confirmar tu registro:</p>" +
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
            return "redirect:/iniciarsesion?exito";
        }
    }

    
    
}
