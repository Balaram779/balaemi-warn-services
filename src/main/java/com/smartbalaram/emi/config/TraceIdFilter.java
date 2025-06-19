package com.smartbalaram.emi.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * 🌐 TraceIdFilter is a servlet filter that generates a unique trace ID
 * for every HTTP request and stores it in the MDC (Mapped Diagnostic Context).
 * 
 * ✅ This enables structured and traceable logging across microservices
 * without modifying individual log statements.
 *
 * 📍 MDC.put("traceId", ...) automatically injects the traceId into log patterns
 * if configured in logback-spring.xml.
 */
@Component
public class TraceIdFilter implements Filter {

    // Constant MDC key for trace ID
    private static final String TRACE_ID = "traceId";

    /**
     * This method is called for every HTTP request.
     *
     * 1. Generates a new UUID trace ID
     * 2. Puts it into MDC so that logs include this trace ID
     * 3. Ensures the trace ID is removed after the request completes (finally block)
     *
     * @param request  incoming HTTP servlet request
     * @param response outgoing HTTP servlet response
     * @param chain    the servlet filter chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 1️⃣ Generate a random trace ID
        String traceId = UUID.randomUUID().toString();

        // 2️⃣ Add it to MDC (so logs can include it)
        MDC.put(TRACE_ID, traceId);

        try {
            // 3️⃣ Continue with next filter/controller
            chain.doFilter(request, response);
        } finally {
            // 4️⃣ Always clean up MDC after request
            MDC.remove(TRACE_ID);
        }
    }
}
