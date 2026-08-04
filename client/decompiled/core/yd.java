/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class yd
extends ael_2 {
    int it;
    long[] aAd;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.it = byteBuffer.getInt();
        int n2 = byteBuffer.getInt();
        this.aAd = new long[n2];
        for (int j = 0; j < n2; ++j) {
            this.aAd[j] = byteBuffer.getLong();
        }
        return true;
    }

    public int getId() {
        return 4800;
    }

    public int eA() {
        return this.it;
    }

    public long[] EG() {
        return this.aAd;
    }
}

