package com.codewithdurgesh.blog.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//3.JWTToken helper
@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY=5*60*60;
	private String secret ="jwtTokenKey";
	
	//retrieve username from token
	public String getUserNameFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
		
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	//for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//Check if token is expired
	private Boolean isTokenExpired(String token)
	{
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//Generate token for user
	public String generateToken(UserDetails userDetails)
	{
		Map<String,Object> claims = new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}

	//While creating the token
	//1.Define claims of the token, like issuer,Expiration,Subject and the ID
	//2.Sign the JWT using HS512 algorithm and secret key.
	//3.According to JWS compact serialization
	//compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String username)
	{
		
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	
	//validate token
	public Boolean validateToken(String token,UserDetails userDetails)
	{
		final String username = getUserNameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	

}
