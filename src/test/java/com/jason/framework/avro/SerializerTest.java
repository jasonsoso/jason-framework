package com.jason.framework.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import com.jason.framework.avro.mail.Message;

public class SerializerTest {

	public static void main(String[] args) {
		Message message = Message.newBuilder()
				.setTo("you")
				.setFrom("me")
				.setBody("I Love you!")
				.build();
		System.out.println("EXPECTED: " + message);
		
		Schema schema = ReflectData.get().getSchema(Message.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Encoder encoder = EncoderFactory.get().binaryEncoder(baos, null);
        DatumWriter<Message> writer = new ReflectDatumWriter<Message>(schema);
        try {
        	
            writer.write(message, encoder);
           //System.out.println("编码后："+encoder.toString());
            
            encoder.flush();
            
            Decoder decoder = DecoderFactory.get().binaryDecoder(baos.toByteArray(), null);
            DatumReader<Message> reader = new ReflectDatumReader<Message>(schema);
            Message actual = reader.read(null, decoder);
            System.out.println("ACTUAL: " + actual);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        
        
		/*DatumWriter<Message> userDatumWriter = new SpecificDatumWriter<Message>(
				Message.class);
		DataFileWriter<Message> dataFileWriter = new DataFileWriter<Message>(
				userDatumWriter);
		dataFileWriter.create(user1.getSchema(), new File("users.avro"));
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.append(user4);
		dataFileWriter.close();*/
		    
		    
		
	}
}
