/*
 * Decompiled with CFR 0.152.
 */
public abstract class aOE
extends aj_1
implements aR {
    private el_1 emB;
    public final boolean emC;

    protected aOE(lc_0 lc_02, boolean bl2) {
        super(lc_02);
        this.emC = bl2;
    }

    public void a(el_1 el_12) {
        if (this.emB != null && el_12 != null) {
            throw new aHY("Declaring type for type body declaration \"" + this.toString() + "\"at " + this.aP() + " is already set");
        }
        this.emB = el_12;
    }

    public el_1 bV() {
        return this.emB;
    }

    public boolean isStatic() {
        return this.emC;
    }

    public void a(aim_2 aim_22) {
        this.emB = (el_1)aim_22;
    }

    public aim_2 Dw() {
        return this.emB;
    }

    public abstract void a(ea_2 var1);
}

