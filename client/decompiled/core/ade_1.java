/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from ade
 */
public class ade_1
extends abk_2 {
    protected static final Logger a = Logger.getLogger(ade_1.class);
    private static final Object aBY = new Object();
    private static final HashMap aBZ = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ade_1 hy(String string) {
        Object object = aBY;
        synchronized (object) {
            return (ade_1)aBZ.get(string);
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
    public static void a(String string, ade_1 ade_12) {
        Object object = aBY;
        synchronized (object) {
            int n2 = 0;
            String string2 = string;
            while (aBZ.containsKey(string2)) {
                string2 = string + " #" + n2++;
            }
            aBZ.put(string2, ade_12);
        }
    }

    public ade_1(sq_1 sq_12, int n2) {
        super(sq_12, n2);
        try {
            Object object = this.adr();
            String string = object.getClass().getName();
            ade_1.a(string, this);
            this.af(object);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    public ade_1(sq_1 sq_12) {
        super(sq_12);
        try {
            Object object = this.adr();
            String string = object.getClass().getName();
            ade_1.a(string, this);
            this.af(object);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }
}

