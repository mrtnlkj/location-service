package sk.uniza.locationservice.common.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpRequestResponseLoggingFilter extends CommonsRequestLoggingFilter {

	public HttpRequestResponseLoggingFilter(int maxPayloadLength) {
		setIncludeClientInfo(true);
		setIncludeQueryString(true);
		setIncludeHeaders(true);
		setIncludePayload(true);
		setMaxPayloadLength(maxPayloadLength);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		boolean isFirstRequest = !isAsyncDispatch(request);
		HttpServletRequest requestToUse = request;

		if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
			requestToUse = new HttpServletRequestCopier(request);
		}

		boolean shouldLog = shouldLog(requestToUse);
		if (shouldLog && isFirstRequest) {
			beforeRequest(requestToUse, getBeforeMessage(requestToUse));
		}

		HttpServletResponseCopier responseToUse = new HttpServletResponseCopier(response);

		try {
			filterChain.doFilter(requestToUse, responseToUse);
		} finally {
			if (shouldLog && !isAsyncStarted(requestToUse)) {
				afterRequest(responseToUse);
			}
		}
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.info(message);
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.info(message);
	}

	@Override
	protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
		StringBuilder msg = new StringBuilder();
		msg.append(prefix);
		msg.append(request.getMethod()).append(" ");
		msg.append(request.getRequestURI());

		if (isIncludeQueryString()) {
			String queryString = request.getQueryString();
			if (queryString != null) {
				msg.append('?').append(queryString);
			}
		}

		if (isIncludeClientInfo()) {
			String client = request.getRemoteAddr();
			if (StringUtils.hasLength(client)) {
				msg.append(", client=").append(client);
			}
			HttpSession session = request.getSession(false);
			if (session != null) {
				msg.append(", session=").append(session.getId());
			}
			String user = request.getRemoteUser();
			if (user != null) {
				msg.append(", user=").append(user);
			}
		}

		if (isIncludeHeaders()) {
			HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
			// NOTE headerPredicate logic was removed
			msg.append(", headers=").append(headers);
		}

		if (isIncludePayload()) {
			HttpServletRequestCopier wrapper = WebUtils.getNativeRequest(request, HttpServletRequestCopier.class);
			if (wrapper != null) {
				String payload = readFromBuffer(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
				if (payload != null) {
					msg.append(", payload=").append(payload);
				}
			}
		}

		msg.append(suffix);
		return msg.toString();
	}

	protected String createResponseMessage(HttpServletResponse response, String prefix, String suffix) {
		StringBuilder msg = new StringBuilder();
		msg.append(prefix);

		msg.append(response.getStatus());

		String contentType = response.getContentType();
		if (contentType != null) {
			msg.append(", contentType=").append(contentType);
		}

		boolean isIncludePayload = isIncludePayload();

		if ("application/octet-stream".equals(response.getContentType())
				|| "image/gif".equals(response.getContentType())) {
			isIncludePayload = false;
		}

		if (isIncludePayload) {
			HttpServletResponseCopier wrapper = WebUtils.getNativeResponse(response, HttpServletResponseCopier.class);
			if (wrapper != null) {
				String payload = readFromBuffer(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
				if (payload != null) {
					msg.append(", payload=").append(payload);
				}
			}
		}

		msg.append(suffix);
		return msg.toString();
	}

	protected String getBeforeMessage(HttpServletRequest request) {
		return createMessage(request, "Request[", "]");
	}

	protected void afterRequest(HttpServletResponse response) {
		logger.info(createResponseMessage(response, "Response[", "]"));
	}

	@Nullable
	private String readFromBuffer(byte[] buf, String encoding) {
		if (buf.length > 0) {
			int length = Math.min(buf.length, getMaxPayloadLength());
			try {
				return new String(buf, 0, length, encoding);
			} catch (UnsupportedEncodingException ex) {
				return "[unknown]";
			}
		}
		return null;
	}
}