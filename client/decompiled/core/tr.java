/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.URL;
import org.apache.log4j.Logger;

public class tr
implements azy_0 {
    protected static final Logger a = Logger.getLogger(sk_2.class);
    private String alL;
    private String alM;

    public void cj(String string) {
        this.alL = string;
    }

    public void ck(String string) {
        this.alM = string;
    }

    public auk aJ(long l2) {
        try {
            return new ae_1(new URL(this.alL + l2 + "." + this.alM));
        }
        catch (IOException iOException) {
            a.error((Object)("Erreur de cr\u00e9ation de l'URL \u00e0 partir de l'ID sp\u00e9cif\u00e9 : r\u00e9sultat = " + this.alL + l2 + "." + this.alM), (Throwable)iOException);
            return null;
        }
    }
}

