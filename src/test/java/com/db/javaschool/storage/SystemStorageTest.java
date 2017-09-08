package com.db.javaschool.storage;

import com.db.javaschool.server.MessagePool;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.FileSystemStorage;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class SystemStorageTest {
    FileSystemStorage fileSystemStorage;
    private final int CRITICAL_MESSAGE_COUNT = 20;

    @Before
    public void setUp() {
        fileSystemStorage = null;
        try {
            fileSystemStorage = new FileSystemStorage(new File("./storage/test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        MessagePool pool = new MessagePool(fileSystemStorage);

        for (int i = 0; i < CRITICAL_MESSAGE_COUNT + 5; i++) {
            pool.putMessageToDeque(new Message(1, "user", "message"));
        }

        for (int i = 0; i < CRITICAL_MESSAGE_COUNT + 5; i++) {
            pool.getMessageFromDeque();
        }

        fileSystemStorage.getData(0);
    }
}
