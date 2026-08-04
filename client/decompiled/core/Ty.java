/*
 * Decompiled with CFR 0.152.
 */
class Ty
implements ja_1 {
    final /* synthetic */ ado_1 bOu;

    Ty(ado_1 ado_12) {
        this.bOu = ado_12;
    }

    public void b(int n2) {
        if (n2 == 8) {
            lp_0 lp_02 = new lp_0(this.bOu.bCc.BC() + "." + "apf");
            lp_02.e(this.bOu.bCc.By());
            if (!br.b(lp_02)) {
                add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
            }
        }
    }
}

