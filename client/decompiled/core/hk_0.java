/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from HK
 */
class hk_0
extends aea_0 {
    final /* synthetic */ azw_0 bfv;

    hk_0(azw_0 azw_02, int n2) {
        this.bfv = azw_02;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        if (azw_0.a(this.bfv) != null) {
            byteBuffer.putInt(azw_0.a(this.bfv).getX());
            byteBuffer.putInt(azw_0.a(this.bfv).getY());
            byteBuffer.putShort(azw_0.a(this.bfv).wk());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        }
        if (azw_0.b(this.bfv) != null && azw_0.b(this.bfv) instanceof kc_2) {
            byteBuffer.putLong(((kc_2)((Object)azw_0.b(this.bfv))).getId());
        } else {
            byteBuffer.putLong(0L);
        }
    }

    public void f(ByteBuffer byteBuffer) {
        kc_2 kc_22;
        azw_0.a(this.bfv, new ry(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
        long l2 = byteBuffer.getLong();
        if (l2 != 0L && (kc_22 = this.bfv.Np().gW().cL(l2)) instanceof tl_2) {
            azw_0.a(this.bfv, (tl_2)((Object)kc_22));
        }
    }
}

