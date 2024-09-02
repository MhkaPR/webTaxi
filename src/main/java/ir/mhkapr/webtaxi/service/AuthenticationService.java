package ir.mhkapr.webtaxi.service;

import ir.mhkapr.webtaxi.DTOs.AuthenticationResponse;
import ir.mhkapr.webtaxi.DTOs.LoginRequest;
import ir.mhkapr.webtaxi.DTOs.RegisterRequest;
import ir.mhkapr.webtaxi.entity.User;
import ir.mhkapr.webtaxi.entity.enums.Role;
import ir.mhkapr.webtaxi.entity.enums.UserStatus;
import ir.mhkapr.webtaxi.exception.UserAlreadyExistsException;
import ir.mhkapr.webtaxi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Value("${token.remaining-time}")
    private Long REMAINING_TIME_TOKEN;
    @Value("${token.remaining-refresh-time}")
    private Long REMAINING_REFRESH_TIME_TOKEN;

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException {
        var user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(UserStatus.INACTIVE)
                .build();

        Optional<User> temp = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(temp.isPresent()) throw new UserAlreadyExistsException();

        userRepository.save(user);

        String token =  jwtService.generateToken(user,REMAINING_TIME_TOKEN);
        String refreshToken = jwtService.generateToken(user,REMAINING_REFRESH_TIME_TOKEN);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
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

        String token =  jwtService.generateToken(user,REMAINING_TIME_TOKEN);
        String refreshToken = jwtService.generateToken(user,REMAINING_REFRESH_TIME_TOKEN);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponse refreshToken(){
        String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        String token =  jwtService.generateToken(user,REMAINING_TIME_TOKEN);
        String refreshToken = jwtService.generateToken(user,REMAINING_REFRESH_TIME_TOKEN);
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
