/*
 * Decompiled with CFR 0.152.
 */
class axN
implements ov_1 {
    final /* synthetic */ aqq_0 dkj;
    final /* synthetic */ String dkk;
    final /* synthetic */ ee_0 dkl;
    final /* synthetic */ String dkm;
    final /* synthetic */ aaz_2 aTO;

    axN(aaz_2 aaz_22, aqq_0 aqq_02, String string, ee_0 ee_02, String string2) {
        this.aTO = aaz_22;
        this.dkj = aqq_02;
        this.dkk = string;
        this.dkl = ee_02;
        this.dkm = string2;
    }

    public boolean a(ke ke2) {
        int n2 = aaz_2.e(this.aTO).size();
        for (int j = 0; j < n2; ++j) {
            aqq_0 aqq_02 = (aqq_0)aaz_2.e(this.aTO).get(j);
            if (this.dkj == aqq_02) continue;
            aqq_02.setPixmap(null);
            aqq_02.setStyle(this.dkk, false);
        }
        if (aaz_2.u(this.aTO) != null) {
            aaz_2.a(this.aTO, aaz_2.u(this.aTO).a(aaz_2.a(this.aTO), this.dkl.getColumnId()));
            StringBuilder stringBuilder = new StringBuilder("table");
            if (this.dkm != null) {
                stringBuilder.append(this.dkm);
            }
            stringBuilder.append("$");
            if (aaz_2.u(this.aTO).isDirect()) {
                stringBuilder.append("directSortButton");
            } else {
                stringBuilder.append("indirectSortButton");
            }
            this.dkj.setStyle(stringBuilder.toString(), true);
            aaz_2.t(this.aTO);
        }
        return false;
    }
}

