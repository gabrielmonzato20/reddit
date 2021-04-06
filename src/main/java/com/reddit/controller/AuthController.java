package com.reddit.controller;

import com.reddit.dto.AuthenticateDto;
import com.reddit.dto.LoginRequest;
import com.reddit.dto.RefrashTokenDto;
import com.reddit.dto.UserAuthRequest;
import com.reddit.service.AuthService;
import com.reddit.service.RefrashTokenService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefrashTokenService refrashTokenService;

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
    public ResponseEntity<AuthenticateDto> login(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.getPassworld() + "=====> "+ loginRequest.getUserName());
        return ResponseEntity.ok().body( authService.login(loginRequest));
    }

        @PostMapping("/refrash/token")
    public ResponseEntity<AuthenticateDto> refrashToken(
            @Valid @RequestBody RefrashTokenDto refrashTokenDto){
        return ResponseEntity.ok(
                authService.refrashToken(refrashTokenDto));
    }
@PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefrashTokenDto refrashTokenDto) throws JSONException {
    refrashTokenService.deleteRefrashToken(
            refrashTokenDto.getRefrashToken());
    JSONObject json = new JSONObject();
    json.put("status",HttpStatus.OK);
    json.put("message","Token deleted"+
            refrashTokenDto.getRefrashToken()
            + "sucessufull");

    return ResponseEntity.status(HttpStatus.OK).body(json.toString());
}
}

