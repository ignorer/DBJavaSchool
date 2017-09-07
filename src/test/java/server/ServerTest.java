package java.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import server.MessagePool;

import static org.mockito.Mockito.verify;

public class ServerTest {

    @Before
    public void setUp() {

    }
    @Test
    public void shouldDumpToFileWhenPoolIsGoingToOverflow() {
        MessagePool real = new MessagePool();
        MessagePool spy = Mockito.spy(real);

        for (int i = 0; i < 1000; i++) {
            real.addMessage(i + "");
        }

        verify(spy).dumpToFile();
    }
}
