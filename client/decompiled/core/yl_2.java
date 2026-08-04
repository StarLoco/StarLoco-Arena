/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Yl
 */
class yl_2
extends aea_0 {
    final /* synthetic */ xb_2 cat;

    yl_2(xb_2 xb_22, int n2) {
        this.cat = xb_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.cat.bWm != null ? this.cat.bWm.getId() : 0L);
    }

    public void f(ByteBuffer byteBuffer) {
        long l2 = byteBuffer.getLong();
        if (l2 == 0L) {
            this.cat.bWm = null;
        } else if (this.cat.bdv != null && this.cat.bdv.gW() != null) {
            this.cat.bWm = this.cat.bdv.gW().cL(l2);
            if (this.cat.bWm == null) {
                a.error((Object)String.format("Impossible de d\u00e9s\u00e9rialiser une partie de %s : target inconnue : %d sur un effet de type %d - %s", this.cat.getClass().getSimpleName(), l2, this.cat.getId(), bl_0.B(8)));
            }
        }
    }
}

