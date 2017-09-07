package com.db.javaschool.client;

import org.json.JSONObject;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OutputClienTest {

    @Test
    public void shouldParseSimpleJSONmessage() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "msg");
        jsonObject.put("message", "Hello");

        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(String.valueOf(jsonObject));
        when(bufferedReader.readLine()).thenReturn(null);

        OutputMain sut = new OutputMain();
        OutputMain spy = spy(sut);
        sut.readMessages(bufferedReader);

        verify(spy).outputMessage("Hello");

    }

    @Test
    public void shouldParseMaltiStringJSONmessage() throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "msg");
        jsonObject.put("message", "Hello\nThere");

        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(String.valueOf(jsonObject));
        when(bufferedReader.readLine()).thenReturn(null);

        OutputMain sut = new OutputMain();
        OutputMain spy = spy(sut);
        sut.readMessages(bufferedReader);

        verify(spy).outputMessage("Hello\nThere");

    }

    @Test
    public void shouldCorrectrlyOutputSeveralMessagesFromHistory() throws IOException  {
        JSONObject jsonObject = new JSONObject();
        List<String> list = Arrays.asList("msg1", "msg2", "msg3");
        jsonObject.put("type", "hist");
        jsonObject.put("messages", list);

        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn(String.valueOf(jsonObject));
        when(bufferedReader.readLine()).thenReturn(null);

        OutputMain sut = new OutputMain();
        OutputMain spy = spy(sut);
        sut.readMessages(bufferedReader);

        verify(spy).outputMessage("history: msg1");
        verify(spy).outputMessage("history: msg2");
        verify(spy).outputMessage("history: msg3");



    }



}
