package com.group3.fams.serviceImpls;

import com.group3.fams.repositories.InvalidTokenRepository;
import com.group3.fams.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpls implements JwtService {

  private static final String SECRET_KEY =
      "6f32d36705fdba89ccfe14129429528b5bc8304fa55462ce7262bc6da1217ec0";

  private final InvalidTokenRepository invalidTokenRepository;

  public String extractID(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    return claimResolver.apply(extractAllClaim(token));
  }

  public Claims extractAllClaim(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigninKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigninKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extractedClaim, UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>(extractedClaim);
    claims.put("roles", userDetails.getAuthorities()); // Include user roles in the claims

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 1000))
        .signWith(getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    return userDetails.getUsername().equalsIgnoreCase(extractID(token))
        && !isExpiredToken(token)
        && !invalidTokenRepository.existsByToken(token);
  }

  public boolean isExpiredToken(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }
}
