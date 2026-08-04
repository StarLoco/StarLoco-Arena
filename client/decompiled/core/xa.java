/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

public abstract class xa
extends alb_0
implements TK {
    public final jy_2[] avU;
    private aim_2 avV = null;
    public Map avW = null;

    protected xa(lc_0 lc_02, jy_2[] jy_2Array) {
        super(lc_02);
        this.avU = jy_2Array;
        for (int j = 0; j < jy_2Array.length; ++j) {
            jy_2Array[j].a(this);
        }
    }

    public void a(aim_2 aim_22) {
        if (this.avV != null && aim_22 != null) {
            throw new aHY("Enclosing scope is already set for statement \"" + this.toString() + "\" at " + this.aP());
        }
        this.avV = aim_22;
    }

    public aim_2 Dw() {
        return this.avV;
    }

    public fb_2 cL(String string) {
        if (this.avW == null) {
            return null;
        }
        return (fb_2)this.avW.get(string);
    }

    public abstract void a(awv_0 var1);
}

