package com.example.BirdsOfFeather;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersonSerializer {
    //Serialize object into a byte array for sending over Nearby Messages
    public byte[] convertToByteArray(Person person) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        Log.i("Serialized", person.getName() + ", " + person.getUniqueId());
        oos.writeObject(person);
        oos.flush();
        byte [] data = bos.toByteArray();
        bos.close();
        oos.close();
        return data;
    }
    //Converts byte array back into a Person object
    public Person convertFromByteArray(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Person person = (Person) ois.readObject();
        Log.i("Deserialized", person.getName() + ", " + person.getUniqueId());
        bis.close();
        ois.close();
        return person;
    }
}
