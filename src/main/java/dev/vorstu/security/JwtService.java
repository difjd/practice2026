package dev.vorstu.security;

import dev.vorstu.entities.User;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String generateToken(User user){
        return "123";
    }
}
