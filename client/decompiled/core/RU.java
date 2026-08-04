/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

class RU
extends aea_0 {
    final /* synthetic */ do_1 bKR;

    RU(do_1 do_12) {
        this.bKR = do_12;
    }

    public void c(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("Les \u00e9l\u00e9ments interactifs client ne peuvent pas \u00eatre s\u00e9rialis\u00e9s");
    }

    public void f(ByteBuffer byteBuffer) {
        a.trace((Object)("D\u00e9codage des donn\u00e9es sp\u00e9cifiques (len=" + byteBuffer.remaining() + ")"));
        if (byteBuffer.remaining() < 1) {
            a.error((Object)"Impossible de d\u00e9s\u00e9rialiser un \u00e9l\u00e9ment interactif vide");
            return;
        }
        if (byteBuffer.remaining() < 23) {
            a.error((Object)("Taille de donn\u00e9es restantes dans le buffer invalide : " + byteBuffer.remaining()));
            return;
        }
        do_1.a(this.bKR, byteBuffer.getShort());
        do_1.a(this.bKR).setX(byteBuffer.getInt());
        do_1.b(this.bKR).setY(byteBuffer.getInt());
        do_1.c(this.bKR).T(byteBuffer.getShort());
        do_1.b(this.bKR, byteBuffer.getShort());
        do_1.a(this.bKR, byteBuffer.get() != 0);
        do_1.b(this.bKR, byteBuffer.get() != 0);
        do_1.a(this.bKR, qc_0.hf(byteBuffer.get()));
        do_1.c(this.bKR, byteBuffer.getShort());
        this.bKR.gh();
        short s = byteBuffer.getShort();
        for (int j = s - 1; j >= 0; --j) {
            ry ry2 = new ry();
            ry2.setX(byteBuffer.getInt());
            ry2.setY(byteBuffer.getInt());
            ry2.T(byteBuffer.getShort());
            do_1.d(this.bKR).add(ry2);
        }
        byte[] byArray = new byte[byteBuffer.getShort() & 0xFFFF];
        byteBuffer.get(byArray);
        do_1.a(this.bKR, aey_0.V(byArray));
        this.bKR.gi();
        byte by = (byte)(byteBuffer.get() & 0xFF);
        if (by > 0) {
            a.error((Object)"Houl\u00e0, j'ai des properties sur ce MapInteractiveElement, alors que je suis pas cens\u00e9 avoir de properties dans DA.");
        }
        if (byteBuffer.remaining() > 0) {
            a.error((Object)("Il reste des donn\u00e9es non trait\u00e9es dans le buffer : " + byteBuffer.remaining()));
        }
    }
}

