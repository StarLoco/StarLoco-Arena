/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class Yk
extends aea_0 {
    final /* synthetic */ xb_2 cat;

    Yk(xb_2 xb_22, int n2) {
        this.cat = xb_22;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.cat.bWl != null ? this.cat.bWl.getId() : 0L);
    }

    public void f(ByteBuffer byteBuffer) {
        long l2 = byteBuffer.getLong();
        if (l2 == 0L) {
            this.cat.bWl = null;
        } else if (this.cat.bdv != null && this.cat.bdv.gW() != null) {
            this.cat.g(this.cat.bdv.gW().cL(l2));
            if (this.cat.bWl == null) {
                a.error((Object)String.format("Impossible de d\u00e9s\u00e9rialiser une partie de %s : caster inconnu : %d sur un effet de type %d - %s", this.cat.getClass().getSimpleName(), l2, this.cat.getId(), bl_0.B(8)));
            }
        } else {
            a.error((Object)("pas de contexte, impossible de r\u00e9cuperer la cible type de RE : " + this.cat.aW));
        }
    }
}

