package com.db.javaschool.request;

import com.db.javaschool.protocol.request.ConnectRequest;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.IllegalFormatException;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

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
            new ConnectRequest(new JSONObject());
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong json format", actualMessage);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidCommandTypeInConstructor() {
        String actualMessage = "";

        try {
            new ConnectRequest(new JSONObject().put("type", "NOT_CONNECT")
                    .put("username", "username")
                    .put("token", "token"));
        } catch (IllegalArgumentException e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Wrong request type", actualMessage);
    }

    @Test
    public void shouldCorrectlySerializeToJsonWhenToJsonString() {
        String jsonObject = new ConnectRequest("username", "token").toJsonString();

        assertEquals("{\"type\":\"connect\",\"username\":\"username\",\"token\":\"token\"}", jsonObject);
    }
}
