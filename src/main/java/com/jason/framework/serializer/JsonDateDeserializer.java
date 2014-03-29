package com.jason.framework.serializer;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date>{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Override  
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)  
            throws IOException, JsonProcessingException {  
        String unformatedDate= jsonParser.getText();  
        Date retVal;  
        try {  
            retVal = dateFormat.parse(unformatedDate);  
        } catch (ParseException e) {  
            return null;  
        }  
        return retVal;  
    }  
}
