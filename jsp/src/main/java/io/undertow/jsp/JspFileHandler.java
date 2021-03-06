package io.undertow.jsp;

import javax.servlet.ServletRequest;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.blocking.BlockingHttpHandler;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import org.apache.jasper.Constants;

/**
 *
 *
 * @author Stuart Douglas
 */
public class JspFileHandler implements BlockingHttpHandler {

    private final String jspFile;
    private final BlockingHttpHandler next;

    public JspFileHandler(final String jspFile, final BlockingHttpHandler next) {
        this.jspFile = jspFile;
        this.next = next;
    }

    @Override
    public void handleBlockingRequest(final HttpServerExchange exchange) throws Exception {
        ServletRequest request = exchange.getAttachment(HttpServletRequestImpl.ATTACHMENT_KEY);
        Object old = request.getAttribute(Constants.JSP_FILE);
        try {
            request.setAttribute(Constants.JSP_FILE, jspFile);
            next.handleBlockingRequest(exchange);
        } finally {
            request.setAttribute(Constants.JSP_FILE, old);
        }
    }
}
