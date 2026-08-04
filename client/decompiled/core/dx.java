/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class dx
extends aea_0 {
    final /* synthetic */ sa_2 lK;

    dx(sa_2 sa_22, int n2) {
        this.lK = sa_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        if (sa_2.a(this.lK) != null) {
            byteBuffer.putInt(sa_2.a(this.lK).getX());
            byteBuffer.putInt(sa_2.a(this.lK).getY());
            byteBuffer.putShort(sa_2.a(this.lK).wk());
        } else {
            byteBuffer.putInt(0);
            byteBuffer.putInt(0);
            byteBuffer.putShort((short)0);
        }
    }

    public void f(ByteBuffer byteBuffer) {
        sa_2.a(this.lK, new ry(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort()));
    }
}

