package org.springframework.web.servlet.support;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtil {

    private RequestUtil() {
        // singleton
    }

    public static HttpServletRequest getRequestFromContext(final RequestContext p_context) {
        return p_context.getRequest();
    }
}
