package com.reddit.controller;

import com.reddit.dto.AuthenticateDto;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.UserAuthRequest;
import com.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserAuthRequest req){
        authService.signup(req);
      return new ResponseEntity<>("User created sucessed ", HttpStatus.OK);
    }
    @GetMapping("/accountVerification/{token}")
     ResponseEntity<String> verifyToken(@PathVariable("token") String token){
        authService.verifyToken(token);
        return new ResponseEntity<>("Token verify sucssefull", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticateDto login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getPassworld() + "=====> "+ loginRequest.getUserName());
        return authService.login(loginRequest);
    }
}
