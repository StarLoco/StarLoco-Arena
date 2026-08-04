/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aaA
 */
class aaa_0
extends aea_0 {
    final /* synthetic */ na_2 cfU;

    aaa_0(na_2 na_22, int n2) {
        this.cfU = na_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        if (na_2.a(this.cfU) != null) {
            byteBuffer.putInt(na_2.a(this.cfU).getX());
            byteBuffer.putInt(na_2.a(this.cfU).getY());
            byteBuffer.putShort(na_2.a(this.cfU).wk());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        }
        if (na_2.b(this.cfU) != null && na_2.b(this.cfU) instanceof kc_2) {
            byteBuffer.putLong(((kc_2)((Object)na_2.b(this.cfU))).getId());
        } else {
            byteBuffer.putLong(0L);
        }
    }

    public void f(ByteBuffer byteBuffer) {
        kc_2 kc_22;
        na_2.a(this.cfU, new ry(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
        long l2 = byteBuffer.getLong();
        if (l2 != 0L && (kc_22 = this.cfU.Np().gW().cL(l2)) instanceof tl_2) {
            na_2.a(this.cfU, (tl_2)((Object)kc_22));
        }
    }
}

