/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from alr
 */
public class alr_1 {
    private static final alr_1 cFg = new alr_1();
    private static final Logger a = Logger.getLogger(alr_1.class);
    public static final String cFh = ".tgam";
    public static final String aea = "";
    private String cFi = ".tgam";
    private String aec = "";

    private alr_1() {
    }

    public static alr_1 aAO() {
        return cFg;
    }

    public void iD(String string) {
        this.cFi = string;
        if (string.startsWith(".")) {
            this.cFi = "." + this.cFi;
        }
    }

    public void bD(String string) {
        this.aec = string;
        if (!string.endsWith("/")) {
            this.aec = this.aec + "/";
        }
    }

    public ef_1 ln(int n2) {
        db_2 db_22 = arX.cQT.iE();
        cx_0 cx_02 = cx_0.JY();
        ef_1 ef_12 = cx_02.bt(n2);
        if (ef_12 == null) {
            String string = this.aec + n2 + this.cFi;
            ef_12 = cx_02.a(db_22, (long)n2, string, false);
        }
        return ef_12;
    }
}

