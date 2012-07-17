package org.springframework.web.servlet.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public final class RequestUtil {

    private RequestUtil() {
        // singleton
    }

    public static HttpServletRequest getRequestFromContext(final RequestContext p_context) {
        return p_context.getRequest();
    }

    public static String getPathWithinApplication(final HttpServletRequest p_request) {
        final String contextPath = p_request.getContextPath();
        final String requestUri = p_request.getRequestURI();
        String ret = requestUri;
        if (StringUtils.startsWithIgnoreCase(requestUri, contextPath)) {
            final String path = requestUri.substring(contextPath.length());
            ret = path;
            if (!StringUtils.hasText(ret)) {
                ret = "/";
            }
        }
        return ret;
    }
}
