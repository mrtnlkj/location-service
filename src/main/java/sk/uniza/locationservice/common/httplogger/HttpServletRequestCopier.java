package sk.uniza.locationservice.common.httplogger;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpServletRequestCopier extends HttpServletRequestWrapper {

	private final ServletInputStreamCopier inputStream;

	public HttpServletRequestCopier(HttpServletRequest request) throws IOException {
		super(request);
		inputStream = new ServletInputStreamCopier(request.getInputStream());
	}

	@Override
	public ServletInputStream getInputStream() {
		return this.inputStream;
	}

	public byte[] getContentAsByteArray() {
		return inputStream.toByteArray();
	}

	private static class ServletInputStreamCopier extends ServletInputStream {

		private final ByteArrayInputStream in;
		private final ByteArrayOutputStream out;

		public ServletInputStreamCopier(ServletInputStream input) throws IOException {

			// copy request stream to output stream
			out = new ByteArrayOutputStream();
			InputStreamReader reader = new InputStreamReader(input);
			int b = reader.read();
			while (b >= 0) {
				out.write(b);
				b = reader.read();
			}

			// provide input stream from output stream
			in = new ByteArrayInputStream(out.toByteArray());
		}

		public byte[] toByteArray() {
			return out.toByteArray();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			// no action
		}

		@Override
		public int read() {
			return in.read();
		}
	}
}