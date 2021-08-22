package eu.nnn4.config;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 *
 * Example config source that echos back the input prepending the string "ECHO: "
 * This config source will resolve only property names that start with "echo." prefix, otherwise returns null.
 * @author Steve Millidge (Payara Foundation)
 */
public class EchoConfigSource implements ConfigSource {

    @Override
    public Map<String, String> getProperties() {
        return new HashMap<>();
    }

    @Override
    public int getOrdinal() {
        return 10;
    }

    @Override
    public String getValue(String propertyName) {
        if (propertyName.startsWith("echo.")) {
            return "ECHO: " + propertyName;
        } else {
            return null;
        }
    }

    @Override
    public String getName() {
        return "Echo";
    }
    
}
