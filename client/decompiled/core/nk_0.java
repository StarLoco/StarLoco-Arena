/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.ALException
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import net.java.games.joal.ALException;
import org.apache.log4j.Logger;

/*
 * Renamed from Nk
 */
public class nk_0
extends tk_1 {
    private sb_2 bzk = new aah_0(this);
    private static final nk_0 bzl = new nk_0();
    private int bzm;

    public nk_0() {
        aL.a(this);
    }

    public static nk_0 aaq() {
        return bzl;
    }

    protected boolean bN() {
        return false;
    }

    protected boolean bP() {
        aIL.dQC.aIN().a(new sk_2());
        this.a(aIL.dQC.aIN());
        aIL.dQD.aIN().a(new tr());
        this.a(aIL.dQD.aIN());
        aIL.dQF.aIN().a(new tr());
        this.a(aIL.dQF.aIN());
        aIL.dQE.aIN().a(new tr());
        this.a(aIL.dQE.aIN());
        aau_0.apB().a(this.bzk);
        return true;
    }

    protected void bQ() {
        this.b(aIL.dQC.aIN());
        this.b(aIL.dQD.aIN());
        this.b(aIL.dQF.aIN());
        this.b(aIL.dQE.aIN());
    }

    public void ce(long l2) {
        aae_0 aae_02 = (aae_0)aIL.dQC.aIN();
        aae_02.c(l2, aae_02.getGain());
    }

    public void ah(float f) {
        aIL.dQC.aIN().setMaxGain(f);
        aIL.dQC.aIN().setGain(f);
    }

    public float aar() {
        return aIL.dQC.aIN().getMaxGain();
    }

    public void bU(boolean bl2) {
        aIL.dQC.aIN().setMute(bl2);
    }

    public boolean aas() {
        return aIL.dQC.aIN().abg();
    }

    public void fp(String string) {
        sk_2 sk_22 = (sk_2)aIL.dQC.aIN().abe();
        sk_22.cj(string);
        sk_22.ck("ogg");
    }

    public void ai(float f) {
        aIL.dQD.aIN().setMaxGain(f);
        aIL.dQD.aIN().setGain(f);
        aIL.dQF.aIN().setMaxGain(f);
        aIL.dQF.aIN().setGain(f);
        aIL.dQE.aIN().setMaxGain(f);
        aIL.dQE.aIN().setGain(f);
    }

    public float aat() {
        return aIL.dQD.aIN().getMaxGain();
    }

    public void bV(boolean bl2) {
        aIL.dQD.aIN().setMute(bl2);
        aIL.dQF.aIN().setMute(bl2);
    }

    public boolean aau() {
        return aIL.dQD.aIN().abg();
    }

    public void fq(String string) {
        tr tr2 = (tr)aIL.dQD.aIN().abe();
        tr2.cj(string);
        tr2.ck("ogg");
        tr2 = (tr)aIL.dQF.aIN().abe();
        tr2.cj(string);
        tr2.ck("ogg");
        tr2 = (tr)aIL.dQE.aIN().abe();
        tr2.cj(string);
        tr2.ck("ogg");
    }

    public or_1 a(amj_1 amj_12, int n2, int n3, int n4) {
        or_1 or_12;
        auk auk2;
        if (!this.isRunning()) {
            return null;
        }
        if (amj_12 == null) {
            return null;
        }
        qe qe2 = (qe)aIL.dQF.aIN();
        if (!qe2.abh()) {
            return null;
        }
        try {
            auk2 = qe2.abe().aJ(amj_12.ajF());
        }
        catch (IOException iOException) {
            a.debug((Object)("Impossible de charger le son d'id " + amj_12.ajF()));
            return null;
        }
        if (auk2 == null) {
            a.debug((Object)("Impossible de charger le son d'id " + amj_12.ajF()));
            return null;
        }
        try {
            or_12 = qe2.a(auk2, amj_12.getMaxGain(), new ty_0(n2, n3, 0.0f), amj_12.ajI(), amj_12.getMaxDistance(), amj_12.ajH(), amj_12.ajJ(), amj_12.ajG(), true, false, false, 200.0f, -1L);
        }
        catch (Exception exception) {
            a.debug((Object)"Exception lev\u00e9e lors de la cr\u00e9ation d'une source positionn\u00e9e", (Throwable)exception);
            return null;
        }
        return or_12;
    }

    protected zu_1 r(int n2) {
        return null;
    }

    public ain_1 p(int n2) {
        return null;
    }

    protected ald_0 q(int n2) {
        return null;
    }

    protected void s(int n2) {
        aIL.dQD.aIN().cL(n2);
    }

    public void a(aCZ aCZ2) {
        aIL.dQF.aIN().a(aCZ2);
        super.a(aCZ2);
    }

    public void at(int n2, int n3) {
        ru_2 ru_22 = gC.kg().p(n2, n3);
        int n4 = ru_22.W(n2, n3);
        if (n4 != this.bzm) {
            block6: {
                ke_0 ke_02 = ke_0.pk();
                afe_2 afe_22 = ke_02.bP(n4);
                if (afe_22 != null) {
                    this.aL((short)afe_22.dHr);
                    try {
                        if (afe_22.dHs) {
                            this.m(afe_22.dHt);
                            break block6;
                        }
                        this.m(-1);
                    }
                    catch (ALException aLException) {
                        a.error((Object)"Exception :", (Throwable)aLException);
                    }
                } else {
                    this.aav();
                }
            }
            this.bzm = n4;
        }
    }

    public void aL(short s) {
        aix_0 aix_02 = IV.Vd().aE(s);
        if (aix_02 == null) {
            this.aav();
        } else {
            hD hD2 = aix_02.aVa();
            if (hD2 == null) {
                this.aav();
            } else {
                this.b(hD2.kJ(), (float)hD2.kL() / 100.0f);
            }
        }
    }

    public void aav() {
        if (!this.isRunning()) {
            return;
        }
        aae_0 aae_02 = (aae_0)aIL.dQE.aIN();
        aae_02.aI(8000.0f);
    }

    private void b(long l2, float f) {
        if (!this.isRunning()) {
            return;
        }
        if (l2 == 0L) {
            return;
        }
        aae_0 aae_02 = (aae_0)aIL.dQE.aIN();
        aae_02.c(l2, f);
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }
}

