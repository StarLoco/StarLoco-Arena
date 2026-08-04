/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Rectangle;

class rA
extends Rectangle {
    private final bo_0 aht;

    public rA(int n2, int n3, int n4, int n5, bo_0 bo_02) {
        super(n2, n3, n4, n5);
        this.aht = bo_02;
    }

    public bo_0 xd() {
        return this.aht;
    }

    public int dg(int n2) {
        int n3 = (int)this.getX();
        if (this.aht == bo_0.aJu) {
            n3 = (int)((double)n3 + (this.getWidth() - (double)n2));
        }
        return n3;
    }

    public int dh(int n2) {
        int n3 = (int)this.getY();
        if (this.aht == bo_0.aJs) {
            n3 = (int)((double)n3 + (this.getHeight() - (double)n2));
        }
        return n3;
    }

    public boolean di(int n2) {
        if (this.aht == bo_0.aJu) {
            return (double)n2 > this.getX();
        }
        return (double)n2 < this.getX() + this.getWidth();
    }

    public boolean dj(int n2) {
        if (this.aht == bo_0.aJs) {
            return (double)n2 > this.getY();
        }
        return (double)n2 < this.getY() + this.getHeight();
    }

    public int dk(int n2) {
        if (this.aht == bo_0.aJu) {
            return -n2;
        }
        return n2;
    }

    public int dl(int n2) {
        if (this.aht == bo_0.aJs) {
            return -n2;
        }
        return n2;
    }
}

