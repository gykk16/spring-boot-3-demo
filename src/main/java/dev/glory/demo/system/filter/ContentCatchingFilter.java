package dev.glory.demo.system.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dev.glory.demo.system.filter.servletwrapperprovider.CachedBodyHttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * Request/Response Body 를 Catching 하는 Filter
 */
@Component
@WebFilter(filterName = "ContentCatchingFilter", urlPatterns = "/**")
public class ContentCatchingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CachedBodyHttpServletRequest contentCachingRequest = new CachedBodyHttpServletRequest(request);
        // ContentCachingRequestWrapper contentCachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper contentCachingResponse = new ContentCachingResponseWrapper(response);

        // ==============================
        // doFilter
        // ==============================
        filterChain.doFilter(contentCachingRequest, contentCachingResponse);

        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(contentCachingResponse, ContentCachingResponseWrapper.class);

        Assert.notNull(wrapper, "wrapper response is null");
        wrapper.copyBodyToResponse();
    }
}