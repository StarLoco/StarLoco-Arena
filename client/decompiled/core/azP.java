/*
 * Decompiled with CFR 0.152.
 */
class azP
implements alx_0 {
    final /* synthetic */ aIg il;

    private azP(aIg aIg2) {
        this.il = aIg2;
    }

    public boolean a(pr_0 pr_02) {
        axe_0 axe_02 = (axe_0)pr_02;
        if (axe_02.aKE() == 1) {
            this.il.setValue(this.il.getValue() + this.il.getButtonJump());
        } else if (axe_02.aKE() == 2) {
            this.il.setValue(this.il.getValue() - this.il.getButtonJump());
        }
        aIg.a(this.il);
        return false;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    /* synthetic */ azP(aIg aIg2, oz_2 oz_22) {
        this(aIg2);
    }
}

