/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from amp
 */
class amp_2
extends aea_0 {
    final /* synthetic */ apn_0 cGK;

    amp_2(apn_0 apn_02) {
        this.cGK = apn_02;
    }

    public void c(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("Impossible de s\u00e9rialiser un \u00e9l\u00e9ment interactif c\u00f4t\u00e9 client");
    }

    public void f(ByteBuffer byteBuffer) {
        a.trace((Object)("D\u00e9codage des donn\u00e9es globales (len=" + byteBuffer.remaining() + ")"));
        if (byteBuffer.remaining() > 0) {
            if (byteBuffer.remaining() < 1) {
                a.error((Object)"Impossible de d\u00e9s\u00e9rialiser une partie vide");
                return;
            }
            int n2 = byteBuffer.get() & 0xFF;
            if (byteBuffer.remaining() != n2 * 6) {
                a.error((Object)("Impossible de d\u00e9s\u00e9rialiser " + n2 + " actions dans un buffer de " + byteBuffer.remaining() + " octets"));
                return;
            }
            for (int j = 0; j < n2; ++j) {
                short s = byteBuffer.getShort();
                int n3 = byteBuffer.getInt();
                avr_0 avr_02 = avr_0.cf(s);
                if (avr_02 != null) {
                    apn_0.e(this.cGK).put(avr_02, n3);
                    continue;
                }
                a.error((Object)("Pas de InteractiveElementAction d'ID=" + s));
            }
        }
    }
}

