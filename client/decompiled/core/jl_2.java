/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from jl
 */
public abstract class jl_2
implements atG {
    private static Logger a = Logger.getLogger(jl_2.class);
    protected ee_2 bN = null;
    protected static ry bm = null;
    protected static int bk;
    protected static int bl;
    protected lp_1 Au = null;

    public void b(ee_2 ee_22) {
        this.bN = ee_22;
    }

    protected abstract Pi mi();

    protected abstract void f(int var1, int var2, short var3);

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void mj() {
        String string;
        if (this.Au != null) {
            this.Au.Yb();
        }
        if ((string = this.mk()) != null) {
            this.aB(string);
        } else {
            mb_0.Yl().hide();
        }
    }

    protected abstract String mk();

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 30001: {
                abu_1 abu_12;
                ry ry2;
                if (this.bN != null && (ry2 = this.e(bk = (abu_12 = (abu_1)pr_02).au(), bl = abu_12.av(), this.bN.NW().getAltitude())) != null && !ry2.equals(bm)) {
                    bm = ry2;
                    this.ml();
                }
                return false;
            }
            case 30000: {
                if (this.bN != null) {
                    ado ado2 = (ado)pr_02;
                    if (ado2.aqY() == 1) {
                        boolean bl2 = false;
                        int n2 = 0;
                        int n3 = 0;
                        short s = 0;
                        bk = ado2.au();
                        ry ry3 = this.e(bk, bl = ado2.av(), this.bN.NW().getAltitude());
                        if (ry3 != null && ry3.equals(bm) && this.Au.p(ry3)) {
                            n2 = ry3.getX();
                            n3 = ry3.getY();
                            s = ry3.wk();
                            bl2 = true;
                        }
                        if (bl2) {
                            this.f(n2, n3, s);
                        }
                    }
                    apN.aDK().b(this);
                    mb_0.Yl().hide();
                }
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        bk = S.as().au();
        bl = S.as().av();
        S.as().at();
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            this.Au.Yb();
        } else {
            mb_0.Yl().hide();
        }
        S.as().b(bk, bl);
        S.as().aw();
    }

    private ry c(int n2, int n3) {
        boolean bl2 = true;
        if (apN.aDK().c(avu_0.aIB())) {
            bl2 = !avu_0.aIB().aIC();
        }
        return MJ.a(DofusArenaClientInstance.yl().YP(), n2, n3, bl2);
    }

    private void aB(String string) {
        if (string != null) {
            mb_0.Yl().a(string, null, 10, -30, BT.aJT);
        }
    }

    public void ml() {
        if (bm != null && this.Au.p(bm)) {
            this.Au.b(this.bN);
            this.Au.b(this.bN.Oc());
            this.Au.a(this.mi(), bm);
        } else {
            this.Au.Yc();
        }
    }

    public ry e(double d, double d2, double d3) {
        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
        ArrayList arrayList = qs_22.a(d, d2, (float)d3, ma_0.buh);
        if (arrayList == null) {
            return null;
        }
        int n2 = arrayList.size();
        if (n2 == 0) {
            return null;
        }
        for (int j = 0; j < n2; ++j) {
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(j);
            ScreenElement screenElement = displayedScreenElement.atV();
            ry ry2 = screenElement.avX();
            if (!this.Au.p(ry2)) continue;
            return ry2;
        }
        return ((DisplayedScreenElement)arrayList.get(0)).atV().avX();
    }
}

