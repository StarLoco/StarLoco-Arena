/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aps
 */
class aps_1
extends aea_0 {
    final /* synthetic */ aww_0 cMe;

    aps_1(aww_0 aww_02, int n2) {
        this.cMe = aww_02;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(aww_0.a(this.cMe));
    }

    public void f(ByteBuffer byteBuffer) {
        aww_0.a(this.cMe, byteBuffer.getLong());
        aww_0.a(this.cMe, null);
    }
}

