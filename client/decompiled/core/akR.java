/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

public final class akR {
    private final int aW;
    private Ts[] cEh;
    private ArrayList cEi = new ArrayList();

    public akR(int n2) {
        this.aW = n2;
    }

    public final int getId() {
        return this.aW;
    }

    public final void a(ByteBuffer byteBuffer, adf_0 adf_02) {
        int n2;
        int n3 = byteBuffer.getShort() & 0xFFFF;
        this.cEh = new Ts[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            Ts ts;
            int n4 = byteBuffer.getShort() & 0xFFFF;
            this.cEh[n2] = ts = adf_02.a(n4, byteBuffer);
            if (ts.afY() != AV.aIr) continue;
            this.cEi.add((gt_0)ts);
        }
        this.cEi.trimToSize();
        for (n2 = 0; n2 < this.cEh.length; ++n2) {
            Ts ts = this.cEh[n2];
            int n5 = ts.afZ();
            if (n5 == 0) continue;
            abx_2[] abx_2Array = new abx_2[n5];
            for (int j = 0; j < abx_2Array.length; ++j) {
                int n6 = byteBuffer.getShort() & 0xFFFF;
                abx_2Array[j] = (abx_2)this.cEh[n6];
            }
            ts.a(abx_2Array);
        }
    }

    public final void update(int n2) {
        int n3;
        for (n3 = 0; n3 < this.cEh.length; ++n3) {
            this.cEh[n3].update(n2);
        }
        for (n3 = 0; n3 < this.cEi.size(); ++n3) {
            ((gt_0)this.cEi.get(n3)).Ql();
        }
    }

    ArrayList aAg() {
        return this.cEi;
    }
}

