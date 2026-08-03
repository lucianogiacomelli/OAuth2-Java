package com.giacomelli.oauth2.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@PreAuthorize("denyAll()")
public class HelloController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sec")
    public ResponseEntity<String> sec(){
        return ResponseEntity.ok("sec");
    }

    @GetMapping("/no-sec")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> noSec(){
        return ResponseEntity.ok("No sec");
    }

}
