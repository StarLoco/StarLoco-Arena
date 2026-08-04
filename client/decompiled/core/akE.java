/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

public abstract class akE
extends aj_1
implements TK {
    private aim_2 avV = null;
    public Map avW = null;

    protected akE(lc_0 lc_02) {
        super(lc_02);
    }

    public void a(aim_2 aim_22) {
        if (this.avV != null && aim_22 != this.avV) {
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

