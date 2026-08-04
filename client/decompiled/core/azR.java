/*
 * Decompiled with CFR 0.152.
 */
class azR
implements zD {
    final /* synthetic */ dt_2 doO;

    private azR(dt_2 dt_22) {
        this.doO = dt_22;
    }

    public boolean a(int n2, aNu aNu2) {
        if (!aNu2.MJ()) {
            ((aNu)dt_2.a(this.doO).remove(n2)).release();
        } else {
            aNu2.bi(false);
        }
        return true;
    }

    /* synthetic */ azR(dt_2 dt_22, akf_2 akf_22) {
        this(dt_22);
    }
}

