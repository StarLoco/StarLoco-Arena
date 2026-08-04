/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ji
 */
class ji_2
extends aea_0 {
    final /* synthetic */ ZT zr;

    ji_2(ZT zT, int n2) {
        this.zr = zT;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(ZT.g(this.zr) != null ? ZT.h(this.zr).getId() : 0L);
    }

    public void f(ByteBuffer byteBuffer) {
        long l2 = byteBuffer.getLong();
        if (l2 == 0L) {
            ZT.b(this.zr, null);
        } else if (ZT.i(this.zr) != null && ZT.j(this.zr).gW() != null) {
            ZT.c(this.zr, ZT.k(this.zr).gW().cL(l2));
        }
    }
}

