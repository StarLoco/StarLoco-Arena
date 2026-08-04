/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bt
 */
public class bt_2 {
    private String fZ;
    private int aG;
    private int aH;
    private int fb;
    private int fc;

    public bt_2(String string, int n2, int n3, int n4, int n5) {
        this.fZ = string;
        this.aG = n2;
        this.aH = n3;
        this.fb = n4;
        this.fc = n5;
    }

    public String getKey() {
        return this.fZ;
    }

    public int getX() {
        return this.aG;
    }

    public int getY() {
        return this.aH;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public String cN() {
        return add_1.aOG().kE(this.fZ);
    }
}

