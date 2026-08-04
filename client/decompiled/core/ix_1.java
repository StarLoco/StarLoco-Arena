/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from iX
 */
class ix_1
extends aea_0 {
    private byte[] zj;
    final /* synthetic */ axw co;

    ix_1(axw axw2) {
        this.co = axw2;
    }

    public void c(ByteBuffer byteBuffer) {
        if (this.zj != null) {
            byteBuffer.putShort((short)this.zj.length);
            byteBuffer.put(this.zj);
        } else {
            byteBuffer.putShort((short)0);
            a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : Timeline s\u00e9rialis\u00e9 \u00e9gale \u00e0 null."));
        }
    }

    public void f(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray);
        this.co.ab(byArray);
    }

    public int lF() {
        this.zj = this.co.djH.JN();
        return 2 + this.zj.length;
    }
}

