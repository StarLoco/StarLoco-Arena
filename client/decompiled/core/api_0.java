/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aPi
 */
class api_0
extends aea_0 {
    final /* synthetic */ hy_1 eoI;

    api_0(hy_1 hy_12, int n2) {
        this.eoI = hy_12;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(hy_1.a(this.eoI));
    }

    public void f(ByteBuffer byteBuffer) {
        hy_1.a(this.eoI, byteBuffer.getLong());
        hy_1.a(this.eoI, null);
    }
}

