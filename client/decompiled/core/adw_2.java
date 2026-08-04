/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aDW
 */
public class adw_2 {
    protected static Logger a = Logger.getLogger(adw_2.class);
    private String oA;
    private aka_2 dzq;

    public void a(aez_0 aez_02, String string) {
        this.oA = string;
        this.dzq = new aka_2();
        this.dzq.a(aez_02.Ov(), string);
    }

    public void aPO() {
        this.dzq.aPO();
    }

    public void Ok() {
        this.dzq.Ok();
    }

    public void cleanUp() {
        this.dzq.cleanUp();
        this.dzq = null;
        this.oA = null;
    }

    public void reset() {
        this.dzq.reset();
    }

    public String aPP() {
        return this.oA;
    }

    public aka_2 aPQ() {
        return this.dzq;
    }
}

