package com.ww.pcms.filter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class APIKeyRequestFilter extends AbstractPreAuthenticatedProcessingFilter {

    private String principalRequestHeader;

    public APIKeyRequestFilter(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(principalRequestHeader);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}