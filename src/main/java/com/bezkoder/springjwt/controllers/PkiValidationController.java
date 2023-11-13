package com.bezkoder.springjwt.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PkiValidationController {
    @GetMapping("/.well-known/pki-validation/4BB472DF7F934D43196AC156A2B32D48.txt")
    @ResponseBody
    public String servePkiValidationFile() {
        // Aquí puedes devolver el contenido del archivo de validación si es necesario.
        return "Contenido del archivo de validación";
    }
    @GetMapping("/.well-known/acme-challenge/")
    @ResponseBody
    public String serveacmechallengeValidationFile() {
        // Aquí puedes devolver el contenido del archivo de validación si es necesario.
        return "Contenido del archivo de validación ACME";
    }
}
