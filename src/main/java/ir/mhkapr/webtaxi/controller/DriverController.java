package ir.mhkapr.webtaxi.controller;

import ir.mhkapr.webtaxi.DTOs.DriverAuthenticationResponse;
import ir.mhkapr.webtaxi.DTOs.DriverRegisterRequest;
import ir.mhkapr.webtaxi.exception.DriverAlreadyExistsException;
import ir.mhkapr.webtaxi.exception.UserAlreadyExistsException;
import ir.mhkapr.webtaxi.exception.UserNotFoundException;
import ir.mhkapr.webtaxi.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/register-driver")
    public ResponseEntity<DriverAuthenticationResponse> register(@RequestBody DriverRegisterRequest request) throws UserNotFoundException, UserAlreadyExistsException, DriverAlreadyExistsException {
       return ResponseEntity.ok(driverService.register(request));
    }
}
