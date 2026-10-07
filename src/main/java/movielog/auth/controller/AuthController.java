package movielog.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import movielog.auth.dto.LoginRequest;
import movielog.auth.dto.LoginResponse;
import movielog.auth.dto.SignupRequest;
import movielog.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(
            @RequestBody
            @Valid
            SignupRequest signupRequest
    ) {
        authService.signup(signupRequest);
    }//signup

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody
            @Valid
            LoginRequest loginRequest
    ){
        return authService.login(loginRequest);
    }//login
}
