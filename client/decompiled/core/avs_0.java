/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from avS
 */
class avs_0
extends aea_0 {
    final /* synthetic */ ud_2 dgU;

    avs_0(ud_2 ud_22, int n2) {
        this.dgU = ud_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(ud_2.a(this.dgU));
    }

    public void f(ByteBuffer byteBuffer) {
        ud_2.a(this.dgU, byteBuffer.getLong());
        ud_2.a(this.dgU, null);
    }
}

