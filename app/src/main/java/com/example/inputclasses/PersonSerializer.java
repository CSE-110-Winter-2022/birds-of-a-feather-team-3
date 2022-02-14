package com.example.inputclasses;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersonSerializer {
    public byte[] convertToByteArray(Person person) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(person);
        oos.flush();
        byte [] data = bos.toByteArray();
        bos.close();
        oos.close();
        return data;
    }

    public Person convertFromByteArray(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Person person = (Person) ois.readObject();
        bis.close();
        ois.close();
        return person;
    }
}
