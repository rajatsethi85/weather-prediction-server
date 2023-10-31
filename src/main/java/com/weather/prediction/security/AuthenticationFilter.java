package com.weather.prediction.security;

import com.weather.prediction.customExceptions.CityNotFoundException;
import com.weather.prediction.service.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter class for authentication the suer request.
 */

public class AuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            logger.info("requested URI " + ((HttpServletRequest) servletRequest).getRequestURI());
            if (shouldSkipRequest(((HttpServletRequest) servletRequest).getRequestURI())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) servletRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (CityNotFoundException ce) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            if (exp.getCause().getMessage().equals("city not found")) {
                httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }

    /**
     * Method to allow some request URI's to directly hit our application by bypassing the spring security.
     *
     * @return boolean true is URI should be skipped for authentication else false.
     */
    private boolean shouldSkipRequest(String requestURI) {
        if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs") || requestURI.startsWith("/error")) {
            logger.info("Skipping this URI from security filter");
            return true;
        } else {
            return false;
        }
    }
}
