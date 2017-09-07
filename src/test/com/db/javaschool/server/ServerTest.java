package com.db.javaschool.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.db.javaschool.server.MessagePool;

import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ServerTest {

    @Before
    public void setUp() {

    }

    @Test
    public void shouldDumpToFileWhenPoolIsGoingToOverflow() {
        MessagePool spy = spy(new MessagePool());

        for (int i = 0; i < 1000; i++) {
            spy.addMessage(i + "");
        }

        verify(spy).dumpToFile();
    }

    @Test
    public void openOutputStreamWhenContextSendAll() throws IOException {
        Context mockedContext = new Context(new MessagePool());
        Socket spySocket = spy(new Socket());

        mockedContext.add("test", spySocket);
        mockedContext.sendAll("message");

        verify(spySocket).getOutputStream();
    }
}
