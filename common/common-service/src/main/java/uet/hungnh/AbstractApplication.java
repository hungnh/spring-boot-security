package uet.hungnh;

import java.util.TimeZone;

public abstract class AbstractApplication {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
