package com.db.javaschool.storage;

import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.FileSystemStorage;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SystemStorageTest {
    @Test
    public void test() {
        FileSystemStorage fileSystemStorage = null;
        try {
            fileSystemStorage = new FileSystemStorage("target");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Message> list = new ArrayList<>();
        list.add(new Message(123, "user", "message"));
        try {
            fileSystemStorage.store(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
