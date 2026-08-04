/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class atC
extends aea_0 {
    final /* synthetic */ ds_1 cUg;

    atC(ds_1 ds_12, int n2) {
        this.cUg = ds_12;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(ds_1.a(this.cUg));
    }

    public void f(ByteBuffer byteBuffer) {
        ds_1.a(this.cUg, byteBuffer.getLong());
        ds_1.a(this.cUg, null);
    }
}

