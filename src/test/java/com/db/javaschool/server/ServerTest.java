package com.db.javaschool.server;

import com.db.javaschool.server.Context;
import org.junit.Before;
import org.junit.Test;
import com.db.javaschool.server.MessagePool;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ServerTest {
    private static final int CRITICAL_MESSAGE_COUNT = 1000;

    @Before
    public void setUp() {
    }

    @Test
    public void shouldDumpToFileWhenPoolIsGoingToOverflow() {
        MessagePool spy = spy(new MessagePool());
        Message mockedMessage = mock(Message.class);

        for (int i = 0; i < CRITICAL_MESSAGE_COUNT; i++) {
            spy.addMessage(mockedMessage);
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

        context.add("Test", new Socket());

        assertEquals(1, context.getConnections().size());
        assertTrue(context.getConnections().containsKey("Test"));
    }

    @Test
    public void shouldCreateFileWhenMessagePoolDump() {
        MessagePool pool = new MessagePool();
        File file = new File("target", "0test.txt");
        file.delete();

        pool.dumpToFile();

        assertTrue(new File("target", "0test.txt").exists());
    }

    @Test
    public void test() {
        MessagePool messagePool = new MessagePool();
        messagePool.addMessage(new Message(111, "imbananko", "привет!"));
        messagePool.addMessage(new Message(112, "imbananko1", "привет!123"));
        messagePool.addMessage(new Message(113, "imbananko12", "привет12313!"));

        String test = messagePool.toJsonString();



    }
}
