package com.db.javaschool.request;

import com.db.javaschool.protocol.request.ConnectRequest;
import com.db.javaschool.protocol.request.HistoryRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HistoryRequestTest {
    private JSONObject jsonObject;

    @Before
    public void setUp() {
        jsonObject = new JSONObject();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidConstructorParams() {
        String actualMessage = "";

        try {
            new HistoryRequest(jsonObject);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong json format", actualMessage);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCommandTypeInConstructor() {
        String actualMessage = "";

        try {
            new HistoryRequest(jsonObject.put("type", "NOT_HIST")
                    .put("page", 100)
                    .put("token", "token"));
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong request type", actualMessage);
    }

    @Test
    public void shouldCorrectlySerializeToJsonWhenToJsonString() {
        String jsonString = new HistoryRequest("token", 100).toJsonString();

        assertEquals("{\"page\":\"100\",\"type\":\"hist\",\"token\":\"token\"}", jsonString);
    }
}