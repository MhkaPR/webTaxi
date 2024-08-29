package ir.mhkapr.webtaxi.controller;

import ir.mhkapr.webtaxi.DTOs.LoginRequest;
import ir.mhkapr.webtaxi.DTOs.AuthenticationResponse;
import ir.mhkapr.webtaxi.DTOs.RegisterRequest;
import ir.mhkapr.webtaxi.excepption.UserAlreadyExistsException;
import ir.mhkapr.webtaxi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
