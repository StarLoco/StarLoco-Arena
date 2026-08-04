/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class axQ
extends aea_0 {
    final /* synthetic */ aox_1 dkp;

    axQ(aox_1 aox_12, int n2) {
        this.dkp = aox_12;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        if (aox_1.a(this.dkp) != null) {
            byteBuffer.putInt(aox_1.a(this.dkp).getX());
            byteBuffer.putInt(aox_1.a(this.dkp).getY());
            byteBuffer.putShort(aox_1.a(this.dkp).wk());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        }
        if (aox_1.b(this.dkp) != null) {
            byteBuffer.putInt(aox_1.b(this.dkp).getX());
            byteBuffer.putInt(aox_1.b(this.dkp).getY());
            byteBuffer.putShort(aox_1.b(this.dkp).wk());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        }
    }

    public void f(ByteBuffer byteBuffer) {
        aox_1.a(this.dkp, new ry(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
        aox_1.b(this.dkp, new ry(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
    }
}

