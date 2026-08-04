/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;

/*
 * Renamed from vB
 */
class vb_2 {
    private ByteArrayOutputStream ath;
    private boolean ati = false;

    private vb_2() {
    }

    vb_2(yJ yJ2) {
        this();
    }

    static ByteArrayOutputStream a(vb_2 vb_22, ByteArrayOutputStream byteArrayOutputStream) {
        vb_22.ath = byteArrayOutputStream;
        return vb_22.ath;
    }

    static boolean a(vb_2 vb_22, boolean bl2) {
        vb_22.ati = bl2;
        return vb_22.ati;
    }

    static ByteArrayOutputStream a(vb_2 vb_22) {
        return vb_22.ath;
    }

    static boolean b(vb_2 vb_22) {
        return vb_22.ati;
    }
}

