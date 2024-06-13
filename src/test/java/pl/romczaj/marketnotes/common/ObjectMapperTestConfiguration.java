package pl.romczaj.marketnotes.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.romczaj.marketnotes.common.objectmapper.ObjectMapperConfiguration;

public class ObjectMapperTestConfiguration {
    private final static ObjectMapperConfiguration objectMapperConfiguration = new ObjectMapperConfiguration();
    public static ObjectMapper objectMapper = objectMapperConfiguration.objectMapper();
}
