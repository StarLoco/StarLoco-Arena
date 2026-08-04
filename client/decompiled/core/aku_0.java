/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from aKu
 */
public class aku_0 {
    private static final Logger a = Logger.getLogger(aku_0.class);
    private static String arj;
    private static final zm_1 dTA;

    public static void lC(String string) {
        arj = string;
    }

    public static Om cv(short s) {
        Om om = (Om)dTA.an(s);
        try {
            if (om == null) {
                om = new Om();
                om.b(acf.T(vq_2.readFile(aku_0.b(arj, s))));
                dTA.b(s, om);
            }
        }
        catch (IOException iOException) {
            a.error((Object)"", (Throwable)iOException);
        }
        return om;
    }

    private static String b(String string, short s) {
        assert (string != null && string.contains("%d"));
        return String.format(string, s, s);
    }

    public static void clear() {
        dTA.clear();
    }

    static {
        dTA = new zm_1();
    }
}

