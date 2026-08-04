/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xP
 */
public class xp_1
extends aj_1
implements aim_2 {
    public final anb_1 azD;
    public final lo_2 azE;
    private aqt azF = null;

    public xp_1(lc_0 lc_02, anb_1 anb_12, lo_2 lo_22) {
        super(lc_02);
        this.azD = anb_12;
        this.azD.HD.a(this);
        this.azE = lo_22;
        this.azE.a(this);
    }

    public void a(aqt aqt2) {
        if (this.azF != null && aqt2 != this.azF) {
            throw new aHY("Enclosing TYR statement already set for catch clause " + this.toString() + " at " + this.aP());
        }
        this.azF = aqt2;
    }

    public aim_2 Dw() {
        return this.azF;
    }
}

