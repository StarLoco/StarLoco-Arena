/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from aGg
 */
public class agg_0
implements sb_2 {
    private nu_1 dfo;
    static final /* synthetic */ boolean bb;
    final /* synthetic */ aau_0 dIg;

    public agg_0(aau_0 aau_02, nu_1 nu_12) {
        this.dIg = aau_02;
        if (nu_12 == null) {
            throw new IllegalArgumentException("le groupe d\u00e9fini est null !");
        }
        this.dfo = nu_12;
    }

    public final xt_0 a(long l2, float f, int n2, long l3, long l4, int n3) {
        auk auk2;
        if (!bb && this.dfo == null) {
            throw new AssertionError((Object)"Le groupe est null ! Comment est-ce possible ?");
        }
        if (!this.dfo.abd().bJ()) {
            aau_0.a.warn((Object)"On essaie de jouer un son alors que le son n'est pas initialis\u00e9");
            return null;
        }
        try {
            auk2 = this.dfo.abe().aJ(l2);
        }
        catch (IOException iOException) {
            return null;
        }
        if (auk2 == null) {
            return null;
        }
        avE avE2 = this.dfo.a(auk2, -1L);
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
        this.dfo.b(avE2);
        return avE2;
    }

    public xt_0 a(long l2, float f, int n2, long l3, long l4, int n3, qq_1 qq_12, int n4, boolean bl2) {
        return this.a(l2, f, n2, l3, l4, n3);
    }

    public void a(long l2, avE avE2) {
        this.dfo.abd().a(avE2);
    }

    public void aeG() {
    }

    public void B(float f, float f2) {
    }

    public void aeH() {
    }

    static {
        bb = !aau_0.class.desiredAssertionStatus();
    }
}

