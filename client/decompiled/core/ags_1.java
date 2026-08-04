/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aGS
 */
public class ags_1 {
    private aef_2 dJK;
    private int aG = 0;
    private int fb = 0;

    public aef_2 aSV() {
        return this.dJK;
    }

    public void a(aef_2 aef_22) {
        this.dJK = aef_22;
    }

    public int getX() {
        return this.aG;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public int getY() {
        if (this.dJK != null) {
            return this.dJK.getY();
        }
        return 0;
    }

    public int getWidth() {
        return this.fb;
    }

    public void setWidth(int n2) {
        this.fb = n2;
    }

    public int getHeight() {
        if (this.dJK != null) {
            return this.dJK.getHeight();
        }
        return 0;
    }
}

