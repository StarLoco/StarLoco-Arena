/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from jj
 */
class jj_1
extends aea_0 {
    final /* synthetic */ ZT zr;

    jj_1(ZT zT, int n2) {
        this.zr = zT;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(ZT.a(this.zr) != null ? ZT.b(this.zr).getId() : 0L);
    }

    public void f(ByteBuffer byteBuffer) {
        long l2 = byteBuffer.getLong();
        if (l2 == 0L) {
            ZT.a(this.zr, null);
        } else if (ZT.c(this.zr) != null && ZT.d(this.zr).gW() != null) {
            this.zr.g(ZT.e(this.zr).gW().cL(l2));
        } else {
            a.error((Object)("pas de contexte, impossible de r\u00e9cuperer la cible type de RE : " + ZT.f(this.zr)));
        }
    }
}

