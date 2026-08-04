/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from um
 */
public final class um_1 {
    public static final Logger a = Logger.getLogger(um_1.class);
    protected static final um_1 apL = new um_1();
    protected azx_0 apM = null;

    private um_1() {
    }

    public static um_1 AF() {
        return apL;
    }

    public void b(String string, String string2, String string3, String string4) {
        try {
            this.apM = new azx_0(string, string2, string3, string4);
        }
        catch (Exception exception) {
            this.apM = null;
            a.error((Object)("Impossible de cr\u00e9er le crypteur/decrypteur de donn\u00e9es (raison : " + exception.getMessage() + ")"));
        }
    }

    public void a(URL uRL, String string, String string2, String string3) {
        try {
            this.apM = new azx_0(uRL, string, string2, string3);
        }
        catch (Exception exception) {
            this.apM = null;
            a.error((Object)("Impossible de cr\u00e9er le crypteur/decrypteur de donn\u00e9es (raison : " + exception.getMessage() + ")"));
        }
    }

    public static byte[] x(byte[] byArray) {
        if (um_1.apL.apM != null) {
            return um_1.apL.apM.x(byArray);
        }
        return null;
    }

    public static byte[] y(byte[] byArray) {
        if (um_1.apL.apM != null) {
            return um_1.apL.apM.y(byArray);
        }
        return null;
    }
}

