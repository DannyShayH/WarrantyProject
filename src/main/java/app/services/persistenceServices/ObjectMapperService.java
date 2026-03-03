package app.services.persistenceServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperService {

    protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

     protected ObjectMapper getMapper(){
        return objectMapper;
    }
}
