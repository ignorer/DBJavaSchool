package com.db.javaschool.request;

import com.db.javaschool.protocol.request.HistoryRequest;
import com.db.javaschool.protocol.request.SendRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SendRequestTest {
    private JSONObject jsonObject;

    @Before
    public void setUp() {
        jsonObject = new JSONObject();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidConstructorParams() {
        String actualMessage = "";

        try {
            new SendRequest(jsonObject);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong json format", actualMessage);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCommandTypeInConstructor() {
        String actualMessage = "";

        try {
            new SendRequest(jsonObject.put("type", "NOT_SND")
                    .put("message", "message")
                    .put("token", "token"));
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong request type", actualMessage);
    }

    @Test
    public void shouldCorrectlySerializeToJsonWhenToJsonString() {
        String jsonString = new SendRequest("message", "token").toJsonString();

        assertEquals("{\"type\":\"snd\",\"message\":\"message\",\"token\":\"token\"}", jsonString);
    }
}
