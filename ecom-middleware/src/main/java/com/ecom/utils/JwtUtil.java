package com.ecom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {

    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //    public static final long JWT_TOKEN_VALIDITY =  60;
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



//   private String SECRET_KEY = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
//    //private static final Logger LOGGER =
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        log.error("claims are {}", claims);
//        return claimsResolver.apply(claims);
//    }
////    private Claims extractAllClaims(String token) {
////        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token.trim()).getBody();
////    }
//
//    private Claims extractAllClaims(String token) {
//        log.error("claims from token");
//        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token.replace("{", "").replace("}","")).getBody();
//    }
//
//
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(UserDetails userDetails) throws UnsupportedEncodingException {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }
//
////    private String createToken(Map<String, Object> claims, String subject) throws UnsupportedEncodingException {
////
////        String signingKeyB64= Base64.getEncoder().encodeToString(SECRET_KEY.getBytes("utf-8"));
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(subject)
////                .setIssuedAt(new Date(System.currentTimeMillis()))
////                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
////                .signWith(SignatureAlgorithm.HS256, signingKeyB64).compact();
////    }
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        log.error("create token ");
//        return Jwts.builder().setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(SignatureAlgorithm.HS512,
//                        SECRET_KEY.getBytes(StandardCharsets.UTF_8)).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }


 // previous

//    private static final String SECRET ="afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
//
//    public String generateToken(String userName){
//        Map<String, Objects> claims = new HashMap<>();
//        return createToken(claims, userName);
//    }
//
//    private String createToken(Map<String, Objects> claims, String userName){
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userName)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 10000*60*30))
//                .signWith(getSignKey(), SignatureAlgorithm.ES256)
//                .compact();
//    }
//    private Key getSignKey(){
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
//        System.out.println("keys :");
//        System.out.println(Keys.hmacShaKeyFor(keyBytes));
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String extractUsername(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    private Claims extractAllClaims(String token){
//        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
//                .parseClaimsJwt(token).getBody();
//    }
//    private Boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }
//    public Date extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails){
//        final String userName = extractUsername(token);
//        return (userName.equals(userDetails.getUsername()) && isTokenExpired(token));
//    }



}
