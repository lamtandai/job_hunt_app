package application.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomepageController {
    @GetMapping("/")
    public ResponseEntity<Object> getHomePage(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("homepage");
    }
}
