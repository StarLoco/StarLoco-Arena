/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from iZ
 */
class iz_2
extends aea_0 {
    final /* synthetic */ axw co;

    iz_2(axw axw2, int n2) {
        this.co = axw2;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.co.aW);
        byteBuffer.putInt(this.co.cAq);
    }

    public void f(ByteBuffer byteBuffer) {
        this.co.aW = byteBuffer.getInt();
        this.co.cAq = byteBuffer.get();
    }
}

