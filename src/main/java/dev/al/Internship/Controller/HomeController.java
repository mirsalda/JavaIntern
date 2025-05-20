package dev.al.Internship.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }


    @GetMapping("/static-message")
    public String getStaticMessage() {
        return "Mesazh statik!";
    }
    @PostMapping("/greet")
    public String greetByLanguagePost(@RequestParam(required = false) String language) {
        if (language == null) {
            return "Language is required.";
        }
        switch (language.toLowerCase()) {
            case "en":
                return "Hello!";
            case "sq":
                return "Pershendetje!";
            case "de":
                return "Halo!";
            case "fr":
                return "Bonjour!";
            default:
                return "Language not supported.";
        }
    }


}
