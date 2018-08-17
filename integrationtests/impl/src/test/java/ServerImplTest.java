
import java.io.IOException;
import java.net.Socket;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServerImplTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8282;

    @Test
    @Disabled    // SKip for now
    public void serverClosesConnectionOnError() throws IOException, InterruptedException {
        try (Socket socket = new Socket(HOST, PORT)) {
            // Will throw an exception if server is not started
        }

        assertThrows(ProcessingException.class, () -> {
            String get = ClientBuilder.newClient().
                    target("http://" + HOST + ":" + PORT).
                    request().
                    get(String.class);
        });
    }
}
