package com.db.javaschool.storage;

import com.db.javaschool.server.MessagePool;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.FileSystemStorage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SystemStorageTest {
    private static File file;

    @Before
    public void setUp() {
        file = new File("storage" + File.pathSeparator + "test");
    }

    @AfterClass
    public static void cleanUp() {
        for (File f : file.listFiles()) {
            f.delete();
        }
        file.delete();
    }

    @Test
    public void shouldCreateEmptyFolderWhenConstructor() throws IOException, InterruptedException {
        File mockedFile = mock(File.class);
        FileSystemStorage systemStorage = new FileSystemStorage(mockedFile);

        verify(mockedFile).mkdir();
        //assertEquals(0, systemStorage.getNumberOfPages());
    }

    @Test
    public void shouldCorrectlyWriteAndReadToFileWhenStore() throws IOException, InterruptedException {
        FileSystemStorage systemStorage = new FileSystemStorage(file);
        List<Message> messagesToWrite = new ArrayList<>();
        Message message = new Message(1, "123", "123");
        messagesToWrite.add(message);

        systemStorage.store(messagesToWrite);
        List<Message> messagesToRead = systemStorage.getData(0);


        //assertTrue(file.listFiles().length == 1);
        assertTrue(messagesToRead.size() == 1);
        assertTrue(messagesToRead.contains(message));

    }
}
