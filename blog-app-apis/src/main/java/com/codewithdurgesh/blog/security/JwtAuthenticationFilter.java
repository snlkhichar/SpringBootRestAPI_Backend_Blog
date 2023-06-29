package com.codewithdurgesh.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

//4.JwtAuthenticationFilter
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	//Call when API request hit hogi
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("doFilterInternal method");
		// 1.Get jwt Token from request
		String requestToken = request.getHeader("Authorization");
		System.out.println("Token:"+requestToken);
		
		String userName = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			try
			{
			token =requestToken.substring(7);
			userName = this.jwtTokenHelper.getUserNameFromToken(token);
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("unable to get jwt token");
				ex.printStackTrace();
			}
			catch(ExpiredJwtException ex)
			{
				System.out.println("Jwt token expired");
				ex.printStackTrace();
			}
			catch(MalformedJwtException ex)
			{
				System.out.println("invalid jwt token");
				ex.printStackTrace();
			}
		}
		else
		{
			System.out.println("Jwt token does not start with Bearer");
		}
		
		//2.once we get the token, now validate.
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				//Authentication krna h
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				//Set the details in usernamePasswordAuthenticationToken
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("Invalid JWT Token");
			}
		}
		else
		{
			System.out.println("username is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}

}
