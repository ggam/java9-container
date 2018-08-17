package eu.ggam.container.impl.servletcontainer.jsr154;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServletOutputStreamImpl extends ServletOutputStream {

    private final ByteArrayOutputStream os;

    public ServletOutputStreamImpl(ByteArrayOutputStream os) {
        this.os = os;
    }

    @Override
    public void write(int b) throws IOException {
        os.write(b);
    }

    public byte[] toByteArray() {
        return os.toByteArray();
    }

}
