/*
 * Decompiled with CFR 0.152.
 */
class Mg
implements aLR {
    final /* synthetic */ int btf;
    final /* synthetic */ apx btg;
    final /* synthetic */ eq_0 bth;

    Mg(eq_0 eq_02, int n2, apx apx2) {
        this.bth = eq_02;
        this.btf = n2;
        this.btg = apx2;
    }

    public boolean eG(int n2) {
        if (n2 != this.btf) {
            this.btg.a(this.bth.getContainerFromLayer(n2));
        }
        return true;
    }
}

