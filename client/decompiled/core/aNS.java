/*
 * Decompiled with CFR 0.152.
 */
public class aNS
extends aFH {
    public aNS() {
        this.a(nf_2.NO);
        this.setEndIndex(1);
    }

    public int CX() {
        return 1;
    }

    public void c(yb_0 yb_02) {
        super.c(yb_02);
    }

    public aoz_2 aXO() {
        return (aoz_2)this.dHz;
    }

    public akq_1 getPixmap() {
        if (this.dHz != null) {
            return this.aXO().getPixmap();
        }
        return null;
    }

    public int getImageHeight() {
        return this.aXO().getHeight();
    }

    public int getHeight() {
        return Math.max(super.getHeight(), this.aXO().getHeight());
    }

    public int getWidth() {
        return Math.max(super.getWidth(), this.aXO().getWidth());
    }

    public int a(af_1 af_12, int n2) {
        return 0;
    }

    public int b(af_1 af_12, int n2) {
        return 0;
    }

    public int c(af_1 af_12, int n2) {
        return this.getWidth();
    }
}

