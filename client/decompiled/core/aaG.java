/*
 * Decompiled with CFR 0.152.
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class aaG
implements wb_2 {
    private static final String cgw = "Either the millis or the datetime attribute must be set.";
    private static final ga_2 xa = ga_2.Qo();
    private Long cgx = null;
    private String cgy = null;
    private String pattern = null;
    private mm_2 cgz = mm_2.Jx;
    private long cgA = xa.Qp();

    public synchronized void setMillis(long l2) {
        this.cgx = new Long(l2);
    }

    public synchronized long getMillis() {
        return this.cgx == null ? -1L : this.cgx;
    }

    public synchronized void setDateTime(String string) {
        this.cgy = string;
        this.cgx = null;
    }

    public synchronized String apn() {
        return this.cgy;
    }

    public synchronized void ds(long l2) {
        this.cgA = l2;
    }

    public synchronized long apo() {
        return this.cgA;
    }

    public synchronized void setPattern(String string) {
        this.pattern = string;
    }

    public synchronized String getPattern() {
        return this.pattern;
    }

    public synchronized void a(mm_2 mm_22) {
        this.cgz = mm_22;
    }

    public synchronized mm_2 app() {
        return this.cgz;
    }

    public synchronized boolean a(iv_1 iv_12) {
        if (this.cgy == null && this.cgx == null) {
            throw new eq_2(cgw);
        }
        if (this.cgx == null) {
            DateFormat dateFormat = this.pattern == null ? DateFormat.getDateTimeInstance(3, 3, Locale.US) : new SimpleDateFormat(this.pattern);
            try {
                long l2 = dateFormat.parse(this.cgy).getTime();
                if (l2 < 0L) {
                    throw new eq_2("Date of " + this.cgy + " results in negative milliseconds value" + " relative to epoch (January 1, 1970, 00:00:00 GMT).");
                }
                this.setMillis(l2);
            }
            catch (ParseException parseException) {
                throw new eq_2("Date of " + this.cgy + " Cannot be parsed correctly. It should be in" + (this.pattern == null ? " MM/DD/YYYY HH:MM AM_PM" : this.pattern) + " format.");
            }
        }
        return this.cgz.b(iv_12.getLastModified(), this.cgx, this.cgA);
    }
}

