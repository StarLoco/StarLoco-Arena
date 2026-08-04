/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from uT
 */
class ut_2
extends aea_0 {
    final /* synthetic */ ca_0 arA;

    ut_2(ca_0 ca_02) {
        this.arA = ca_02;
    }

    public void c(ByteBuffer byteBuffer) {
    }

    public void f(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        ca_0.b(this.arA, aey_0.V(byArray));
        ca_0.a(this.arA, byteBuffer.getLong());
        ca_0.b(this.arA, (long)byteBuffer.getShort());
        ca_0.a(this.arA, byteBuffer.getShort());
        ca_0.a(this.arA, byteBuffer.getInt());
        ca_0.b(this.arA, byteBuffer.getInt());
        ca_0.b(this.arA, byteBuffer.getShort());
    }

    public int lF() {
        return 0;
    }
}

