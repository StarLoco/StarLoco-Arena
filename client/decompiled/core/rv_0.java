/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from RV
 */
class rv_0
extends aea_0 {
    final /* synthetic */ do_1 bKR;

    rv_0(do_1 do_12, int n2) {
        this.bKR = do_12;
        super(n2);
    }

    public void c(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("La synchronisation du contenu de l'objet est faite depuis le serveur => par de s\u00e9rialisation");
    }

    public void f(ByteBuffer byteBuffer) {
        do_1.d(this.bKR, byteBuffer.getShort());
        do_1.c(this.bKR, byteBuffer.get() == 1);
    }
}

