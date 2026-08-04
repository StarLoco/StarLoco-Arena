/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from TU
 */
class tu_0
implements ja_1 {
    final /* synthetic */ ox_0 bOT;

    tu_0(ox_0 ox_02) {
        this.bOT = ox_02;
    }

    public void b(int n2) {
        if (n2 == 8) {
            lp_0 lp_02 = new lp_0(this.bOT.bCc.BC() + "." + "apf");
            lp_02.e(this.bOT.bCc.By());
            if (!br.b(lp_02)) {
                add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
            }
        }
    }
}

