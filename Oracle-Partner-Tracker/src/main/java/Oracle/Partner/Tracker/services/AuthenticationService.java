package Oracle.Partner.Tracker.services;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Oracle.Partner.Tracker.dto.AuthDTO;
import Oracle.Partner.Tracker.repositories.PartnerRepository;
@Service
public class AuthenticationService {

    @Autowired
    PartnerRepository partnerRepository;

    private final JwtService jwtService;
    
    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(AuthDTO authentication) throws AuthenticationException{
        if (authentication.email() == null || authentication.email().isBlank()) {
            throw new RuntimeException("Email null ou blank");
        }
        if (authentication.password() == null || authentication.password().isBlank()) {
            throw new RuntimeException("Senha null ou blank");
        }
        if(partnerRepository.existsByEmail(authentication.email()) == false) {
            throw new AuthenticationException("User not found: " + authentication.email());
        }
        return jwtService.generateToken(authentication);
    }
}
