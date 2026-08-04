/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from jf
 */
class jf_2
extends aea_0 {
    final /* synthetic */ ZT zr;

    jf_2(ZT zT, int n2) {
        this.zr = zT;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        if (ZT.l(this.zr) != null) {
            byteBuffer.putInt(ZT.m(this.zr).iP());
            byteBuffer.putLong(ZT.n(this.zr).iO());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putLong(0L);
        }
    }

    public void f(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getInt();
        long l2 = byteBuffer.getLong();
        Pi pi = null;
        switch (n2) {
            case 13: {
                pi = ((adt_2)ZT.o(this.zr)).aFF().el(l2);
                break;
            }
            case 3: {
                pi = ame_1.aWP().eN(l2);
                break;
            }
            case 14: {
                pi = cw_1.eO().w(l2);
                break;
            }
            case 12: {
                pi = ((adt_2)ZT.p(this.zr)).aFG().E((int)l2);
            }
        }
        this.zr.a(pi);
    }
}

