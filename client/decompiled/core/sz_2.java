/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sz
 */
class sz_2
implements axq_0 {
    final /* synthetic */ adg_2 aki;
    final /* synthetic */ ex_2 akj;
    final /* synthetic */ String akk;
    final /* synthetic */ String akl;
    final /* synthetic */ add_1 ajf;

    sz_2(add_1 add_12, adg_2 adg_22, ex_2 ex_22, String string, String string2) {
        this.ajf = add_12;
        this.aki = adg_22;
        this.akj = ex_22;
        this.akk = string;
        this.akl = string2;
    }

    public void aL(String string) {
        if (this.aki != null && this.aki.getElementMap() != null && string.equals(this.aki.getElementMap().getId())) {
            this.akj.getWindowManager().e(this.aki, this.akk);
            if (this.akl != null) {
                this.akj.getWindowManager().g(this.aki, this.akk);
            }
            this.ajf.b(this);
        }
    }
}

