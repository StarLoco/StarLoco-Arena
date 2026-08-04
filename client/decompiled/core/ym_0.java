/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from ym
 */
public class ym_0
extends aIT {
    protected static final Logger a = Logger.getLogger(ym_0.class);
    protected static final Object aBY = new Object();
    protected static final HashMap aBZ = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ym_0 cY(String string) {
        Object object = aBY;
        synchronized (object) {
            return (ym_0)aBZ.get(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static int EQ() {
        Object object = aBY;
        synchronized (object) {
            return aBZ.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Iterable ER() {
        Object object = aBY;
        synchronized (object) {
            return aBZ.keySet();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(String string, ym_0 ym_02) {
        Object object = aBY;
        synchronized (object) {
            int n2 = 0;
            String string2 = string;
            while (aBZ.containsKey(string2)) {
                string2 = string + " #" + n2++;
            }
            aBZ.put(string2, ym_02);
        }
    }

    public ym_0(sq_1 sq_12) {
        super(sq_12);
        try {
            Object object = this.adr();
            String string = object.getClass().getName();
            ym_0.a(string, this);
            this.af(object);
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e : ", (Throwable)exception);
        }
    }

    public ym_0(sq_1 sq_12, int n2) {
        super(sq_12);
        if (n2 < 1) {
            n2 = 1;
        }
        try {
            Object object = null;
            for (int j = 0; j < n2; ++j) {
                object = this.adr();
                this.af(object);
            }
            if (object != null) {
                String string = object.getClass().getName();
                ym_0.a(string, this);
            }
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e : ", (Throwable)exception);
        }
    }
}

