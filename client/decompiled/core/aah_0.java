/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from aAh
 */
class aah_0
implements sb_2 {
    final /* synthetic */ nk_0 doY;

    aah_0(nk_0 nk_02) {
        this.doY = nk_02;
    }

    public avE b(long l2, float f, int n2, long l3, long l4, int n3) {
        auk auk2;
        nu_1 nu_12 = aIL.dQD.aIN();
        try {
            auk2 = nu_12.abe().aJ(l2);
        }
        catch (IOException iOException) {
            nk_0.Dm().error((Object)("Impossible de charger le son d'id " + l2));
            return null;
        }
        avE avE2 = nu_12.a(auk2, -1L);
        if (avE2 == null) {
            return null;
        }
        avE2.setGain(f);
        if (n2 == 0) {
            avE2.eq(true);
        } else if (n2 > 1) {
            avE2.mA(n2);
        }
        if (l3 != -1L) {
            avE2.dX(l3);
        }
        if (l4 != -1L) {
            avE2.dY(l4);
        }
        nu_12.b(avE2);
        return avE2;
    }

    public avE b(long l2, float f, int n2, long l3, long l4, int n3, qq_1 qq_12, int n4, boolean bl2) {
        return this.b(l2, f, n2, l3, l4, n3);
    }

    public void a(long l2, avE avE2) {
        aIL.dQD.aIN().abd().a(avE2);
    }

    public void aeG() {
        awb awb2 = (awb)aIL.dQD.aIN();
        awb2.aeG();
    }

    public void B(float f, float f2) {
        awb awb2 = (awb)aIL.dQD.aIN();
        awb2.bl(f);
        if (f2 != -1.0f) {
            awb2.bm(f2);
        }
    }

    public void aeH() {
    }
}

