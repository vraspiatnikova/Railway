package com.tsystems.jschool.railway.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        MultiValueMap<String, String> queryParams =
                UriComponentsBuilder.fromUriString(request.getHeader("Referer")).build().getQueryParams();
        if(!queryParams.isEmpty()) targetUrl = "/buyticket/"+ queryParams.get("board").get(0)+"/"+ queryParams.get("from").get(0) +"/"+queryParams.get("to").get(0);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isManager(roles)) {
            url = "/allTrainsRoutes";
        } else if (isUser(roles)) {
            url = "/myInfo";
        } else if (isAdmin(roles)) {
            url = "/users";
        } else {
            url = "/";
        }
        return url;
    }

    private boolean isUser(List<String> roles) {
        return roles.contains("ROLE_USER");
    }

    private boolean isManager(List<String> roles) {
        return roles.contains("ROLE_MANAGER");
    }

    private boolean isAdmin(List<String> roles) {
        return roles.contains("ROLE_ADMIN");
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    @Override
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
