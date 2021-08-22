package eu.nnn4.config;

import org.eclipse.microprofile.config.spi.Converter;

import eu.nnn4.pojo.MyValue;

/**
 * Example Custom Converter that wraps the value in a TestPojo object
 */
public class CustomConverter implements Converter<MyValue> {

    @Override
    public MyValue convert(String value) {
        MyValue result = new MyValue();
        result.myValue = value;
        return result;
    }
    
}
