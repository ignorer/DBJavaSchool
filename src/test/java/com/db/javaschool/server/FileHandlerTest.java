package com.db.javaschool.server;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileHandlerTest {
    private static String folderPath;
    private FileHandler fileHandler;

    @Before
    public void setUp() {
        folderPath = "test";
        new File("test").mkdir();
        fileHandler = new FileHandler(folderPath);

    }

    @Test
    public void shouldCreateFileWhenDump() {
        fileHandler.dumpFile(new JSONObject());

        assertTrue(new File(folderPath + "/1_chatek.txt").exists());
        assertEquals(1, fileHandler.getFileNumber());
    }

    @Test
    public void shouldCorrectlyReadFromFileOnReadFile() {
        String expectedString = "expected";
        MessagePool mockedPool = mock(MessagePool.class);
        when(mockedPool.toJson()).thenReturn(new JSONObject().put("expected", "expected"));
        fileHandler.dumpFile(mockedPool.toJson());

        String actual = fileHandler.readFile(1);
        JSONObject jsonObject = new JSONObject(actual);

        assertEquals(expectedString, jsonObject.getString("expected"));
    }

    @After
    public void cleanUp() {
        File file = new File(folderPath);
        for (File child : file.listFiles())
            child.delete();
    }
}
