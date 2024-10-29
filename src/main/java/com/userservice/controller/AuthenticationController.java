package com.userservice.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.userservice.requests.AuthenticationRequest;
import com.userservice.response.AuthenticationResponse;
import com.userservice.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

	  @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private UserDetailsService myUserDetailsService;

	    @PostMapping("/authenticate")
	    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
	        authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	        );

	        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getEmail());

	        // Generate JWT token with email as the subject
	        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().toString());

	        return ResponseEntity.ok(new AuthenticationResponse(jwt));
	    }
}
