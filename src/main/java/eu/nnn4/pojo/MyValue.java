package eu.nnn4.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MyValue {
    public String myValue;

    @Override
    public String toString() {
        return "MyValuePojo{" + "value=" + myValue + '}';
    }
}
