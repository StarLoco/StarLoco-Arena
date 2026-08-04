/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from ana
 */
public class ana_2
implements aho_0 {
    public static final String vq = "message";
    public static final String[] ce = new String[]{"message"};
    private short cIm;
    private short cIn;
    private short cIo;
    private short cIp;
    private hp_2 cIq;
    public static Comparator OU = new bq_0();

    public ana_2(short s, short s2, short s3, short s4, hp_2 hp_22) {
        this.cIm = s;
        this.cIn = s2;
        this.cIo = s3;
        this.cIp = s4;
        this.cIq = hp_22;
    }

    public short aBY() {
        return this.cIm;
    }

    public short aBZ() {
        return this.cIn;
    }

    public short aCa() {
        return this.cIo;
    }

    public short aCb() {
        return this.cIp;
    }

    public hp_2 aCc() {
        return this.cIq;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(vq)) {
            return aon_0.aYc().a(60, this.cIm, new Object[0]);
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

