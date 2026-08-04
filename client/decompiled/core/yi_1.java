/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Yi
 */
class yi_1
extends aea_0 {
    final /* synthetic */ xb_2 cat;

    yi_1(xb_2 xb_22, int n2) {
        this.cat = xb_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.cat.je());
        byteBuffer.putLong(this.cat.ajN());
        byteBuffer.putInt(this.cat.bWj == null ? this.cat.akO().ST() : this.cat.bWj.ST());
        if (this.cat.bWn == null) {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        } else {
            byteBuffer.putInt(this.cat.bWn.getX());
            byteBuffer.putInt(this.cat.bWn.getY());
            byteBuffer.putShort(this.cat.bWn.wk());
        }
        byteBuffer.putInt(this.cat.r);
    }

    public void f(ByteBuffer byteBuffer) {
        long l2;
        long l3 = byteBuffer.getLong();
        this.cat.dh(l3);
        this.cat.bWq = l2 = byteBuffer.getLong();
        int n2 = byteBuffer.getInt();
        if (this.cat.bdv != null && this.cat.bdv.gR() != null) {
            this.cat.bWj = this.cat.bdv.gR().iK(n2);
            if (this.cat.bWj == null) {
                a.error((Object)("Impossible de d\u00e9s\u00e9rialiser un WakfuRunningEffect : generic effet inconnu : " + n2));
            }
        }
        this.cat.bWn.l(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort());
        this.cat.r = byteBuffer.getInt();
    }
}

