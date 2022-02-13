package com.example.inputclasses;

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
    public byte[] convertToByteArray(Person self) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(self);
        oos.flush();
        byte [] data = bos.toByteArray();
        bos.close();
        oos.close();
        return data;
    }
    public FakedMessageListener(MessageListener realMessageListener, int frequency, List<Person> personList){
        this.messageListener = realMessageListener;
        this.executor = Executors.newSingleThreadScheduledExecutor();

       // Message message = new Message(personMsg);
       // this.messageListener.onFound(message);
        executor.scheduleAtFixedRate(()-> {
            //String name = "";
            for (Person person : personList) {
                byte[] serializedPerson = null;
                try {
                    serializedPerson = convertToByteArray(person);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (serializedPerson != null) {
                    //Message message = new Message(name.getBytes(StandardCharsets.UTF_8));
                    Message message = new Message(serializedPerson);
                    this.messageListener.onFound(message);
                    this.messageListener.onLost(message);
                }
            }
        }, 0, frequency, TimeUnit.SECONDS);
    }



}
