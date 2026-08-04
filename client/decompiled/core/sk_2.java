/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from sK
 */
public class sk_2
implements azy_0 {
    protected static final Logger a = Logger.getLogger(sk_2.class);
    private String alL;
    private String alM;
    public static final int alN = 0;
    public static final int alO = 1;
    public static final int alP = 2;
    private String[] alQ = new String[]{"loading", "island", "fight"};

    public void cj(String string) {
        this.alL = string;
    }

    public void ck(String string) {
        this.alM = string;
    }

    public auk aJ(long l2) {
        if (l2 >= 0L && l2 < (long)this.alQ.length) {
            try {
                return new ae_1(new URL(this.alL + this.alQ[(int)l2] + "." + this.alM));
            }
            catch (IOException iOException) {
                a.error((Object)("Erreur de cr\u00e9ation de l'URL \u00e0 partir de l'ID sp\u00e9cif\u00e9 : r\u00e9sultat = " + this.alL + this.alQ[(int)l2] + "." + this.alM), (Throwable)iOException);
            }
        }
        return null;
    }
}

