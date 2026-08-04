/*
 * Decompiled with CFR 0.152.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/*
 * Renamed from aIQ
 */
public class aiq_1
extends gi_2 {
    long cqy = -1L;
    String cqz = null;
    SimpleDateFormat dQK = null;

    public void start() {
        String string = this.aqI();
        if (string == null) {
            string = "yyyy-MM-dd HH:mm:ss,SSS";
        }
        if (string.equals("ISO8601")) {
            string = "yyyy-MM-dd HH:mm:ss,SSS";
        }
        try {
            this.dQK = new SimpleDateFormat(string);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.d("Could not instantiate SimpleDateFormat with pattern " + string, illegalArgumentException);
            this.dQK = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        }
        List list = this.aqJ();
        if (list != null && list.size() > 1) {
            TimeZone timeZone = TimeZone.getTimeZone((String)list.get(1));
            this.dQK.setTimeZone(timeZone);
        }
    }

    public String b(tz_0 tz_02) {
        long l2 = tz_02.getTimeStamp();
        if (l2 == this.cqy) {
            return this.cqz;
        }
        this.cqy = l2;
        this.cqz = this.dQK.format(new Date(l2));
        return this.cqz;
    }
}

