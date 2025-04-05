//package com.taskmanagerapp.Backend.security;
//
//
//import com.taskmanagerapp.Backend.services.jwt.UserServiceImpl;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.stereotype.Component;
//import java.util.Date;
//
//@Component
//public class JwtTokenUtil {
//
//    private String jwtSecret = "your-secret-key";  // You should store this securely
//    private int jwtExpirationMs = 86400000;  // 1 day in milliseconds
//
//    public String generateJwtToken(Authentication authentication) {
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(jwtSecret)
//                    .parseClaimsJws(authToken);
//            return true;
//        } catch (Exception e) {
//            // Token is invalid
//        }
//        return false;
//    }
//
//    public String getUsernameFromJwtToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}
//
