/*
 * Decompiled with CFR 0.152.
 */
class aEo
implements xy {
    final /* synthetic */ long dzJ;
    final /* synthetic */ int dzK;
    final /* synthetic */ yb_2 dzL;
    final /* synthetic */ aoq_2 dzI;

    aEo(aoq_2 aoq_22, long l2, int n2, yb_2 yb_22) {
        this.dzI = aoq_22;
        this.dzJ = l2;
        this.dzK = n2;
        this.dzL = yb_22;
    }

    public boolean isValid() {
        if (System.currentTimeMillis() - this.dzJ < (long)this.dzK) {
            return false;
        }
        this.dzL.b(this);
        return true;
    }
}

