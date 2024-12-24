package org.authentication.two_factor_auth.controller;

import lombok.AllArgsConstructor;

import org.authentication.two_factor_auth.dto.UserDto;
import org.authentication.two_factor_auth.dto.UserLoginDto;
import org.authentication.two_factor_auth.entity.User;
import org.authentication.two_factor_auth.repository.UserRepository;
import org.authentication.two_factor_auth.service.TwoFactorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TwoFactorService twoFactorService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserDto userDto) {

        // Generate TOTP secret key
        String secret = twoFactorService.generateKey();

        User user = new User(userDto.username(), userDto.password(), secret);
        userRepository.save(user);

        // Generate QR code URL
        String qrCodeUrl = twoFactorService.generateQRUrl(secret, userDto.username());

        Map<String, String> response = new HashMap<>();
        response.put("secret", secret);
        response.put("qrCodeUrl", qrCodeUrl);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {

        Optional<User> user = userRepository.findByUsername(userLoginDto.username());

        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or Password is incorrect");
        }
        // Validate the TOTP code
        boolean isValid = twoFactorService.isValid(user.get().getSecretKey(), userLoginDto.otp());

        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid TOTP code");
        }
    }


}

