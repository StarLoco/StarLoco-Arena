/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aij
 */
public enum aij_2 {
    cxE((ayt_0)new ajw_1()),
    cxF((ayt_0)new auD()),
    cxG((ayt_0)new mf_1()),
    cxH((ayt_0)new za_2()),
    cxI(null),
    cxJ(null),
    cxK(null),
    cxL(null);

    protected static Logger a;
    private ayt_0 cxM;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aij_2() {
        void var3_1;
        this.cxM = var3_1;
    }

    public ayt_0 axE() {
        if (this.cxM == null) {
            a.error((Object)("Acc\u00e8s \u00e0 un parser non impl\u00e9ment\u00e9 dans ParserType." + this.name()));
        }
        return this.cxM;
    }

    static {
        a = Logger.getLogger(aij_2.class);
    }
}

