/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from xe
 */
class xe_0
extends km_1 {
    private final TK awr;
    private final jy_2 aws;

    xe_0(jy_2 jy_22, TK tK) {
        this.aws = jy_22;
        this.awr = tK;
    }

    public void b(jy_2 jy_22) {
        if (jy_2.a(jy_22) != null && this.awr != jy_2.a(jy_22)) {
            throw new aHY("Enclosing block statement for rvalue \"" + jy_22 + "\" at " + jy_22.aP() + " is already set");
        }
        jy_2.a(jy_22, this.awr);
        super.b(jy_22);
    }

    public void a(uy_1 uy_12) {
        uy_12.a(this.awr);
    }

    public void a(atu_0 atu_02) {
        if (atu_0.b(atu_02) != null && this.awr != atu_0.b(atu_02)) {
            throw new aHY("Enclosing scope already set for type \"" + this.toString() + "\" at " + atu_02.aP());
        }
        atu_0.a(atu_02, this.awr);
        super.a(atu_02);
    }
}

