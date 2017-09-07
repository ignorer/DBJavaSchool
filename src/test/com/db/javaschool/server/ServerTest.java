package com.db.javaschool.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.db.javaschool.server.MessagePool;

import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServerTest {

    @Before
    public void setUp() {

    }
    @Test
    public void shouldDumpToFileWhenPoolIsGoingToOverflow() {
        MessagePool spy = Mockito.spy(new MessagePool());

        for (int i = 0; i < 1000; i++) {
            spy.addMessage(i + "");
        }

        verify(spy).dumpToFile();
    }

    @Test
    public void test() throws IOException {
        Context mockedContext = mock(Context.class);
        Socket mockedSocket = mock(Socket.class);


        mockedContext.add("test", mockedSocket);
        mockedContext.sendAll("message");


        verify(mockedSocket).getOutputStream();


    }
}
