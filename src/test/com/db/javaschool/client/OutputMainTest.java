package com.db.javaschool.client;

import org.json.JSONObject;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class OutputMainTest {

    @Test
    public void shouldDisplaySimapleMessageFromJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "msg");
        jsonObject.put("msg", "Hello");



    }

}