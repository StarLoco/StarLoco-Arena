/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from uU
 */
class uu_2
extends aea_0 {
    final /* synthetic */ ca_0 arA;

    uu_2(ca_0 ca_02) {
        this.arA = ca_02;
    }

    public void c(ByteBuffer byteBuffer) {
    }

    public void f(ByteBuffer byteBuffer) {
        ca_0.c(this.arA, byteBuffer.getLong());
        int n2 = byteBuffer.getInt();
        short s = byteBuffer.getShort();
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        ca_0.a(this.arA, new aen_1(n2, s, aey_0.V(byArray)));
        ca_0.b(this.arA, (long)byteBuffer.getShort());
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        ca_0.b(this.arA, aey_0.V(byArray2));
        ca_0.a(this.arA, byteBuffer.getShort());
        ca_0.a(this.arA, byteBuffer.getInt());
        ca_0.b(this.arA, byteBuffer.getInt());
        ca_0.b(this.arA, byteBuffer.getShort());
    }

    public int lF() {
        return 0;
    }
}

