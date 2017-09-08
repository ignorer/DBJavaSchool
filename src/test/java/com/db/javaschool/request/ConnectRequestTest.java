package com.db.javaschool.request;

import com.db.javaschool.protocol.request.ConnectRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConnectRequestTest {
    private JSONObject jsonObject;

    @Before
    public void setUp() {
        jsonObject = new JSONObject();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidConstructorParams() {
        String actualMessage = "";

        try {
            new ConnectRequest(jsonObject);
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong json format", actualMessage);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCommandTypeInConstructor() {
        String actualMessage = "";

        try {
            new ConnectRequest(jsonObject.put("type", "NOT_CONNECT")
                    .put("username", "username")
                    .put("token", "token"));
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong request type", actualMessage);
    }

    @Test
    public void shouldCorrectlySerializeToJsonWhenToJsonString() {
        String jsonString = new ConnectRequest("username", "token").toJsonString();

        assertEquals("{\"type\":\"connect\",\"username\":\"username\",\"token\":\"token\"}", jsonString);
    }
}
