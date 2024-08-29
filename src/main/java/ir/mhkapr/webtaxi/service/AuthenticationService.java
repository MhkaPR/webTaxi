package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.AuthenticationResponse;
import ir.mhkapr.webtaxi.DTOs.LoginRequest;
import ir.mhkapr.webtaxi.DTOs.RegisterRequest;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.Roles;
import ir.mhkapr.webtaxi.excepption.UserAlreadyExistsException;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        var user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.USER)
                .build();

        Optional<User> temp = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(temp.isPresent()) throw new UserAlreadyExistsException();

        userRepository.save(user);

        String token =  jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    public AuthenticationResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new UsernameNotFoundException("in authentication user not found!"));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
