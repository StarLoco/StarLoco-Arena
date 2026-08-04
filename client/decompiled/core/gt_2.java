/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from gT
 */
class gt_2
implements zD {
    final /* synthetic */ lb_0 uT;
    final /* synthetic */ lb_0 uU;
    final /* synthetic */ ajp_0 uS;

    gt_2(ajp_0 ajp_02, lb_0 lb_02, lb_0 lb_03) {
        this.uS = ajp_02;
        this.uT = lb_02;
        this.uU = lb_03;
    }

    public boolean a(int n2, String string) {
        String string2 = n2 != 0 ? (String)this.uT.get(n2 / 2) : "";
        byte by = -1;
        if (string2 != null && !string2.equals("?")) {
            by = string2.equals("") || string.equals(string2) ? (byte)1 : 0;
        }
        ad_1 ad_12 = new ad_1(string, by);
        this.uU.c(n2, ad_12);
        return true;
    }
}

