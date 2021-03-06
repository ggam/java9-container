package eu.ggam.container.impl.servletcontainer.container;

import eu.ggam.container.api.http.HttpResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ContainerHttpResponseImpl implements HttpResponse {

    private int status = 200;
    private Map<String, List<String>> headers = new HashMap<>();

    private final ByteArrayOutputStream output;

    private boolean completed;

    public ContainerHttpResponseImpl() {
        this.output = new ResponseOutputStream();
    }

    @Override
    public void setHeaders(Map<String, List<String>> headers) {
        if (completed) {
            throw new IllegalStateException("Response already finished");
        }

        this.headers = headers;
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        if (completed) {
            throw new IllegalStateException("Response already finished");
        }

        return output;
    }

    @Override
    public void setStatus(int status) {
        if (completed) {
            throw new IllegalStateException("Response already finished");
        }

        this.status = status;
    }

    @Override
    public int getStatus() {
        return status;
    }

    private class ResponseOutputStream extends ByteArrayOutputStream {

        @Override
        public void write(byte[] b) throws IOException {
            checkNotCompleted();
            super.write(b);
        }

        @Override
        public synchronized void write(byte[] b, int off, int len) {
            checkNotCompleted();
            super.write(b, off, len);
        }

        @Override
        public synchronized void write(int b) {
            checkNotCompleted();
            super.write(b);
        }

        private void checkNotCompleted() {
            if (completed) {
                throw new IllegalStateException("Response already finished");
            }
        }

    }
}
