package com.example.networkofgiving.controllers;

import com.example.networkofgiving.entities.User;
import com.example.networkofgiving.models.JwtAuthenticationResponse;
import com.example.networkofgiving.models.LoginDTO;
import com.example.networkofgiving.models.RegistrationDTO;
import com.example.networkofgiving.models.UserInformationDTO;
import com.example.networkofgiving.security.AuthenticatedUserInfo;
import com.example.networkofgiving.security.JwtUtil;
import com.example.networkofgiving.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@Valid @NotNull @RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        AuthenticatedUserInfo principal = (AuthenticatedUserInfo)auth.getPrincipal();
        User userPrincipal = principal.getUser();
        JwtAuthenticationResponse tokenAuthenticationResponse =
                jwtUtil.createTokenAuthenticationResponse(userPrincipal);
        return tokenAuthenticationResponse;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void register(@Valid @NotNull @RequestBody RegistrationDTO registrationDTO) {
        this.userService.register(registrationDTO);
    }

    @GetMapping("/me")
    public UserInformationDTO getCurrentUserInformation() {
        return this.userService.getCurrentUserInformation();
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Username already exists!")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void conflict() {
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            BadCredentialsException.class,
            MethodArgumentNotValidException.class
    })
    public void badRequest() { }
}
