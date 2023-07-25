package com.studentManagement.filter;

import com.studentManagement.model.PODUserInfoResult;
import com.studentManagement.model.SocketPrincipal;
import com.studentManagement.model.User;
import com.studentManagement.service.JwtService;
import com.studentManagement.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_BEARER_TYPE = "Bearer ";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt;
        String userEmail;
        if (header == null || !header.startsWith(TOKEN_BEARER_TYPE)) {
            filterChain.doFilter(request, response);
            return;
        }


//        jwt = header.substring(7);
//        userEmail = jwtService.extractUsername(jwt);
//
////        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
////            if (jwtService.isTokenValid(jwt, userDetails)) {
////                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
////                        userDetails,
////                        null,
////                        userDetails.getAuthorities()
////                );
//////                authtoken.setDetails(
//////                        new WebAuthenticationDetailsSource().buildDetails(request)
//////                );
////
////
////            }
////        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null) {
            token = token.replace(TOKEN_BEARER_TYPE, "");

            if (token == null || token.length() == 0)
                return null;

            PODUserInfoResult userInfo = userService.getUserInfo(token);
            Optional<User> user = null;
            SocketPrincipal socketPrincipal = null;

            if (userInfo != null) {
                user = userService.getUserBySsoId(Long.parseLong(userInfo.getSsoId()));

                socketPrincipal = SocketPrincipal.builder()
                        .name(userInfo.getSsoId())
                        .token(token)
                        .build();
            }

            return new UsernamePasswordAuthenticationToken(
                    socketPrincipal,
                    token,
                    user.get().getAuthorities()
            );
        }
        return null;
    }
}

















