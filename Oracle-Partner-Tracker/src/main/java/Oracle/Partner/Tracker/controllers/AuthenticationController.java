package Oracle.Partner.Tracker.controllers;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Oracle.Partner.Tracker.dto.AuthDTO;
import Oracle.Partner.Tracker.services.AuthenticationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Object> authenticate(@RequestBody AuthDTO authentication) throws AuthenticationException, JsonProcessingException {
        
        System.out.println();
        System.out.println(new ObjectMapper().writeValueAsString(authentication));
        System.out.println();

        try {
            String token = authenticationService.authenticate(authentication);
            if (token != null && !token.contains("FALSE")) {
                return ResponseEntity.created(null).body(token);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(token);
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
