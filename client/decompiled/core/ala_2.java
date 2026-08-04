/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aLa
 */
public class ala_2
extends wh_1 {
    private final WL dJc;

    public ala_2(WL wL) {
        super(new ach_0());
        this.dJc = wL;
    }

    public final void clear() {
        for (int j = 0; j < this.aun.size(); ++j) {
            ((abb_0)this.aun.jx(j)).a(this.dJc);
        }
        super.clear();
    }

    protected void a(abb_0 abb_02) {
        abb_02.a(this.dJc);
    }
}

