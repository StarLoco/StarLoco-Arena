/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from iM
 */
class im_2
extends aea_0 {
    public byte[] yN;
    final /* synthetic */ axw co;

    im_2(axw axw2) {
        this.co = axw2;
    }

    public void c(ByteBuffer byteBuffer) {
        if (this.yN != null) {
            byteBuffer.putShort((short)this.yN.length);
            byteBuffer.put(this.yN);
        } else {
            byteBuffer.putShort((short)0);
            a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : EffectAreaManager s\u00e9rialis\u00e9 \u00e9gal \u00e0 null."));
        }
    }

    public void f(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray);
        this.co.djK.J(byArray);
    }

    public int lF() {
        this.yN = this.co.djK.SC();
        return 2 + this.yN.length;
    }
}

