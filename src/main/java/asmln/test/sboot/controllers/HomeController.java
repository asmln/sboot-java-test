package asmln.test.sboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home. Simple request.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String hello() {
        return "Hello from Spring Boot!";
    }
}
