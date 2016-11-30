package asmln.test.sboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by samoylenko on 30.11.16.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    @ResponseBody
    String hello() {
        return "Hello from Spring Boot!";
    }
}
