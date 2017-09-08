package com.db.javaschool.server;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ServerTest {
    private static final int OVERFLOW_MESSAGE_COUNT = 1000;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldDumpToFileWhenPoolIsGoingToOverflow() {
        MessagePool spy = spy(new MessagePool());

        for (int i = 0; i < OVERFLOW_MESSAGE_COUNT + 10; i++) {
            spy.addMessage(mock(Message.class));
        }

        verify(spy).dumpToFile();
    }

    @Test
    public void shouldOpenOutputStreamWhenContextSendAll() throws IOException {
        Context mockedContext = new Context(new MessagePool());
        Socket spySocket = spy(new Socket());

        mockedContext.add("test", spySocket);
        mockedContext.sendAll("message");

        verify(spySocket).getOutputStream();
    }

    @Test
    public void shouldAddAndIncrementSizeOfPoolWhenConnectionAdded() {
        Context context = new Context(new MessagePool());

        context.add("Test", mock(Socket.class));

        assertEquals(1, context.getConnections().size());
        assertTrue(context.getConnections().containsKey("Test"));
    }
}
