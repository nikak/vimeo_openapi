package eu.nnn4.config;

import org.eclipse.microprofile.config.spi.Converter;

import eu.nnn4.pojo.MyToken;

public class CustomConfigTokenConverter  implements Converter<MyToken> {
 
    @Override
    public MyToken convert(String value) {
        String[] chunks = value.split(",");
        MyToken result = new MyToken(chunks[0], chunks[1]);
        return result;
    }
}