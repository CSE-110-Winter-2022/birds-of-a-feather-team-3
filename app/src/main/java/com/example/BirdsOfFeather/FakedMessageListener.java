package com.example.BirdsOfFeather;

import android.util.Log;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FakedMessageListener extends MessageListener {

    private final MessageListener messageListener;
    private final ScheduledExecutorService executor;


    //fake message listener class that wraps real messagelistener to mock receiving bluetooth messages
    public FakedMessageListener(MessageListener realMessageListener, int frequency, List<Person> personList){
        this.messageListener = realMessageListener;
        this.executor = Executors.newSingleThreadScheduledExecutor();

        //"receives" fake messages at intervals
        executor.scheduleAtFixedRate(()-> {
            //serialize each person object to be sent as message
            for (Person person : personList) {
                byte[] serializedPerson = null;
                try {
                    PersonSerializer personSerializer = new PersonSerializer();
                    serializedPerson = personSerializer.convertToByteArray(person);
                    Log.i("Person Before Serialized", person.toString());
                    Log.i("Person After Serialized", serializedPerson.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (serializedPerson != null) {
                    Log.i("FakeMessageListener", "Fake message received");
                    Message message = new Message(serializedPerson);
                    this.messageListener.onFound(message);
                    this.messageListener.onLost(message);
                    for (String personToWaveToId : person.getWaveMocks()) {
                        String waveFormatString = "WAVE:" + person.getUniqueId() + ":::" + personToWaveToId;
                        Message waveMessage = new Message(waveFormatString.getBytes(StandardCharsets.UTF_8));
                        this.messageListener.onFound(waveMessage);
                        this.messageListener.onLost(waveMessage);
                    }
                }
            }
        }, 0, frequency, TimeUnit.SECONDS);
    }



}
