/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Lt
 */
class lt_2
extends apc {
    final /* synthetic */ yq_2 brv;

    lt_2(yq_2 yq_22) {
        this.brv = yq_22;
    }

    public boolean a(ke ke2) {
        aji_1 aji_12;
        afl_0 afl_02 = azs_0.aLV().getProperty("chat.selectedView");
        String string = "/w " + yq_2.a(this.brv).getName() + " ";
        afl_02.a("input", (Object)string);
        UV uV = null;
        if (add_1.aOG().kR("chatDialog") && (aji_12 = add_1.aOG().azj().lh("chatDialog")) != null) {
            uV = (UV)aji_12.R("chatInput");
        }
        if (uV != null) {
            lb_2.XL().g(uV);
        }
        yq_2.a(this.brv, null);
        return false;
    }
}

