package com.db.javaschool.server;

import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.FileSystemStorage;
import com.db.javaschool.server.storage.Storage;
import com.db.javaschool.storage.SystemStorageTest;
import jdk.nashorn.internal.runtime.StoredScript;
import org.junit.Before;
import org.junit.Test;
import sun.misc.resources.Messages_es;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

public class MessagePoolTest {

    private static int MESSAGE_COUNT_CRITICAL = 20;
    private MessagePool messagePool;
    Storage storage;


    @Before
    public void setUp() throws IOException {
        storage = mock(Storage.class);
        messagePool = new MessagePool(storage);
    }

    @Test
    public void shouldCallStoreWhenCacheOverflow() throws IOException, InterruptedException {
        for (int i = 0; i < MESSAGE_COUNT_CRITICAL + 2; i++) {
            messagePool.putMessageToCache(mock(Message.class));
        }

        verify(storage).store(anyList());
    }

    @Test
    public void shouldIncrementAndContainWhenPutMessageToDeque() throws IOException, InterruptedException {
        Message stub = mock(Message.class);

        messagePool.putMessageToDeque(stub);

        assertEquals(1, messagePool.getMessageQueue().size());
        assertTrue(messagePool.getMessageQueue().contains(stub));
    }

    @Test
    public void shouldIncrementAndContainWhenPutMessageToCache() throws IOException, InterruptedException {
        Message stub = mock(Message.class);

        messagePool.putMessageToCache(stub);

        assertEquals(1, messagePool.getCache().size());
        assertTrue(messagePool.getCache().contains(stub));
    }
}
