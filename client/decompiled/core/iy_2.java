/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Renamed from iY
 */
class iy_2
extends aea_0 {
    private HashMap zk = new HashMap();
    final /* synthetic */ axw co;

    iy_2(axw axw2) {
        this.co = axw2;
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putShort((short)this.co.aKv());
        Iterator iterator = this.co.aKu();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            byteBuffer.putLong(kc_22.getId());
            byte[] byArray = (byte[])this.zk.get(kc_22);
            if (byArray != null) {
                byteBuffer.putShort((short)byArray.length);
                byteBuffer.put(byArray);
                continue;
            }
            byteBuffer.putShort((short)0);
            a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : EffectUser \u00e9gal \u00e0 null."));
        }
        this.zk.clear();
    }

    public void f(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        for (short s2 = 0; s2 < s; s2 = (short)(s2 + 1)) {
            kc_2 kc_22 = this.co.cL(byteBuffer.getLong());
            byte[] byArray = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray);
            if (kc_22 != null && byArray.length > 0) {
                kc_22.I(byArray);
                continue;
            }
            a.error((Object)("Impossible de d\u00e9s\u00e9rialiser le match d'id " + this.co.aW + " : EffectUser \u00e9gal \u00e0 null ou donn\u00e9es vides."));
        }
    }

    public int lF() {
        int n2 = 0;
        n2 += 2;
        Iterator iterator = this.co.aKu();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            byte[] byArray = kc_22.PW();
            this.zk.put(kc_22, byArray);
            n2 += 10 + byArray.length;
        }
        return n2;
    }
}

