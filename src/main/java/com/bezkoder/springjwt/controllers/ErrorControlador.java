package com.bezkoder.springjwt.controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600)//https://appicmlab.icmetrologia.com
@RestController
@RequestMapping("/api/v1")
public class ErrorControlador {
    @GetMapping("/error-page")
    public ModelAndView showErrorPageWithMessage() {
        ModelAndView modelAndView = new ModelAndView("redirect:/error");
        modelAndView.addObject("message", "Ocurrió un error al procesar su solicitud.");
        return modelAndView;
    }
}
