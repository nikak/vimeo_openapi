package eu.nnn4.config;


import java.util.Arrays;
import java.util.logging.Logger;

import org.eclipse.microprofile.config.spi.Converter;

import eu.nnn4.pojo.MyListOfValues;
import lombok.extern.slf4j.Slf4j;

/**
 * Example Custom Converter that converts a comma separated string into a list of Strings
 */

public class ConfigListConverter implements Converter<MyListOfValues>{

    @Override
    public MyListOfValues convert(String value) {
        Logger.getLogger("listConverter").info("000000000000000000000000000000000000000--"+value);
        return new MyListOfValues(Arrays.asList(value.split(",")) );
        
    }
    
}