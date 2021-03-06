package Admin.LearnTogether.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
  Created by Luvbert
  this class uses for: after login send redirect to another custom direct.
  Do at: security_xml => authentication-success-handler-ref
*/

public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    public CustomSuccessHandler(){
        super();
        setUseReferer(true);
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String targetUrl = super.determineTargetUrl(request, response);
        if(targetUrl.contains("/login")) {
            targetUrl = getURL(request) + "/home";
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    public static String getURL(HttpServletRequest req) {

        String scheme = req.getScheme();             // http
        String serverName = req.getServerName();     // hostname.com
        int serverPort = req.getServerPort();        // 8080
        String contextPath = req.getContextPath();   // /mywebapp
        String servletPath = req.getServletPath();
        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        return url.toString();
    }
}
