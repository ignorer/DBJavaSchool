package com.db.javaschool.server;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class FileHandlerTest {
    private static String folderPath;

    @Before
    public void setUp() {
        folderPath = "target/test";
        new File("target/test").mkdir();
    }

    @Test
    public void shouldCreateFileWhenDump() {
        FileHandler fileHandler = new FileHandler(folderPath);

        fileHandler.dumpFile(new JSONObject());

        assertTrue(new File(folderPath + "/1_chatek.txt").exists());
    }

    @After
    public void cleanUp() {
        File file = new File(folderPath);
        for (File child : file.listFiles())
            child.delete();
    }
}
