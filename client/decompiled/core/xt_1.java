/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Xt
 */
public class xt_1 {
    private akq_1 arn;
    private int aG;
    private int aH;

    public xt_1(akq_1 akq_12, int n2, int n3) {
        this.arn = akq_12;
        this.aG = n2;
        this.aH = n3;
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public int getX() {
        return this.aG;
    }

    public int getY() {
        return this.aH;
    }

    public int getWidth() {
        return this.arn.getWidth();
    }

    public int getHeight() {
        return this.arn.getHeight();
    }
}

