/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from BH
 */
class bh_2
implements px_1 {
    final /* synthetic */ ByteBuffer aJl;
    final /* synthetic */ sw_1 aJm;

    bh_2(sw_1 sw_12, ByteBuffer byteBuffer) {
        this.aJm = sw_12;
        this.aJl = byteBuffer;
    }

    public boolean aM(long l2) {
        this.aJl.putLong(l2);
        return true;
    }
}

