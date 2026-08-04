/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
 * Renamed from WB
 */
public class wb_0
extends gi_2 {
    String key;
    private static final String EMPTY_STRING = "";

    public void start() {
        this.key = this.aqI();
        super.start();
    }

    public void stop() {
        this.key = null;
        super.stop();
    }

    public String b(tz_0 tz_02) {
        Map map = tz_02.agy();
        if (map == null) {
            return EMPTY_STRING;
        }
        if (this.key == null) {
            StringBuffer stringBuffer = new StringBuffer();
            Set set = map.keySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                String string2 = (String)map.get(string);
                stringBuffer.append(string).append('=').append(string2);
                if (!iterator.hasNext()) continue;
                stringBuffer.append(", ");
            }
            return stringBuffer.toString();
        }
        String string = (String)tz_02.agy().get(this.key);
        if (string != null) {
            return string;
        }
        return EMPTY_STRING;
    }
}

