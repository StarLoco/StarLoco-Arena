/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class YI
extends aea_0 {
    final /* synthetic */ dw_0 cbn;

    YI(dw_0 dw_02, int n2) {
        this.cbn = dw_02;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.cbn.nA);
    }

    public void f(ByteBuffer byteBuffer) {
        this.cbn.nA = byteBuffer.getInt();
    }
}

