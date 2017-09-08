package com.db.javaschool.storage;

import com.db.javaschool.server.MessagePool;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.FileSystemStorage;
import org.junit.Test;

import java.io.IOException;

public class SystemStorageTest {
    @Test
    public void test() throws IOException, InterruptedException {
        FileSystemStorage fileSystemStorage = null;
        try {
            fileSystemStorage = new FileSystemStorage("target");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessagePool pool = new MessagePool();

        for (int i = 0; i < 30; i++) {
            pool.putMessage(new Message(123, "user", "message"));
        }

    }
}
