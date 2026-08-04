/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aaV
 */
public class aav_1
extends xf_2 {
    public static final String TAG = "Sound";
    private int cgU = -1;

    public String getTag() {
        return TAG;
    }

    public void setSoundId(int n2) {
        this.cgU = n2;
    }

    public int getSoundId() {
        return this.cgU;
    }

    public void run() {
        if (this.cgU != -1) {
            aek.atD().jY(this.cgU);
        }
    }
}

