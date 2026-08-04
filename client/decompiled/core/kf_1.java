/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from KF
 */
public class kf_1
extends ael_2 {
    private ca_0[] boM;
    private int aW;

    public kf_1(int n2) {
        this.aW = n2;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getInt();
        this.boM = new ca_0[n2];
        for (int j = 0; j < n2; ++j) {
            ca_0 ca_02 = new ca_0();
            byte[] byArray2 = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray2);
            ca_02.ad(byArray2);
            this.boM[j] = ca_02;
        }
        return true;
    }

    public ca_0[] WT() {
        return this.boM;
    }

    public int getId() {
        return this.aW;
    }
}

