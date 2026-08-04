/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from BL
 */
class bl_2
implements sg_1 {
    final /* synthetic */ ByteBuffer aJl;
    final /* synthetic */ sw_1 aJm;

    bl_2(sw_1 sw_12, ByteBuffer byteBuffer) {
        this.aJm = sw_12;
        this.aJl = byteBuffer;
    }

    public boolean f(long l2, long l3) {
        this.aJl.putLong(l2);
        this.aJl.putLong(l3);
        return true;
    }
}

