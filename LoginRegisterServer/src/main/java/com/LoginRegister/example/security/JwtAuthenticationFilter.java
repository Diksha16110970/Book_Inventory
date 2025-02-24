//package com.LoginRegister.example.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            if (jwtUtil.isTokenValid(token)) {
//                String email = jwtUtil.extractEmail(token);
//               SecurityContextHolder.getContext().setAuthentication(
//                       new User(email, "", new ArrayList<>())
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                	    email, null, new ArrayList<>()
//                	);
//                	SecurityContextHolder.getContext().setAuthentication(authentication);
//
//               
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
