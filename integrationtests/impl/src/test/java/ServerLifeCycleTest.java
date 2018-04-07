
import eu.ggam.container.api.Server;
import eu.ggam.container.api.event.ServerStartedEvent;
import eu.ggam.container.api.event.ServerStartingEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Guillermo González de Agüero
 */
public class ServerLifeCycleTest {

    private Stream<String> logLines;

    @BeforeEach
    public void prepare() throws IOException {
        logLines = Files.lines(Paths.get("target", "jre-dist2", "logs", "server.log"));
    }

    @Test
    public void serverStarting() {
        String line = ServerStartingEvent.class.getName() + " Server starting. Current state: " + Server.State.STARTING;
        
        Optional<String> findFirst = logLines.filter(l -> l.contains(line)).findFirst();
        Assertions.assertTrue(findFirst.isPresent());
    }

    @Test
    public void serverStarted() {
        String line = ServerStartedEvent.class.getName() + " Server started. Current state: " + Server.State.RUNNING;
        
        Optional<String> findFirst = logLines.filter(l -> l.contains(line)).findFirst();
        Assertions.assertTrue(findFirst.isPresent());
    }

    @Test
    @Disabled
    public void serverStopping() {
        // TODO: Figure out a way to implement this test
    }
}
