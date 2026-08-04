/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Array;
import org.apache.log4j.Logger;

/*
 * Renamed from aqy
 */
public abstract class aqy_0 {
    private static Logger a = Logger.getLogger(aqy_0.class);
    private final int aW;
    protected final akw_0[] UD;
    private oj_0[] cOn;

    public aqy_0(int n2, akw_0[] akw_0Array) {
        this.aW = n2;
        this.UD = akw_0Array;
    }

    public int getId() {
        return this.aW;
    }

    public void b(oj_0 oj_02) {
        if (this.cOn == null) {
            this.cOn = (oj_0[])Array.newInstance(oj_0.class, 1);
        } else {
            oj_0[] oj_0Array = (oj_0[])Array.newInstance(oj_0.class, this.cOn.length + 1);
            System.arraycopy(this.cOn, 0, oj_0Array, 0, this.cOn.length);
            this.cOn = oj_0Array;
        }
        this.cOn[this.cOn.length - 1] = oj_02;
    }

    public oj_0[] aEb() {
        return this.cOn;
    }

    public int size() {
        return this.cOn != null ? this.cOn.length : 0;
    }

    public int c(oj_0 oj_02) {
        try {
            if (this.cOn != null) {
                for (int j = 0; j < this.cOn.length; ++j) {
                    if (this.cOn[j] != oj_02) continue;
                    return j;
                }
            }
        }
        catch (Exception exception) {
            a.error((Object)("Probl\u00e8me lors du indexOf(" + oj_02 + ")"));
            return -1;
        }
        return -1;
    }

    public boolean a(ky_2 ky_22) {
        for (oj_0 oj_02 : this.aEb()) {
            if (ky_22.bT(oj_02.jf()) != null || ky_22.bW(oj_02.jf()) != null || ky_22.bT(-oj_02.jf()) != null || ky_22.bW(-oj_02.jf()) != null) continue;
            return false;
        }
        return true;
    }

    public akw_0[] tu() {
        return this.UD;
    }
}

