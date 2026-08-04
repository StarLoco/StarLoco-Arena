/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.DofusArenaReplayPlayerInstance;

/*
 * Renamed from re
 */
public abstract class re_0
extends kr_0 {
    private static int afE = 999;
    private static float afF = 1.4f;
    protected static aLN Hv = new aLN();
    private Du afG;
    protected boolean afH;
    protected boolean afI;
    protected int aG;
    protected int aH;
    protected short wp;
    protected double Ev;

    public re_0(int n2, int n3, int n4, boolean bl2, boolean bl3, long l2, int n5, int n6, short s) {
        super(n2, n3, n4);
        this.a(gp_2.Sb());
        this.a(Wz.ajg());
        this.a(aau_0.apB());
        this.a(new us_2(this));
        this.bB(l2);
        this.afH = bl2;
        this.afI = bl3;
        this.aG = n5;
        this.aH = n6;
        this.wp = s;
    }

    public long oS() {
        if (this.afI) {
            this.bG(afE);
        }
        if (this.afH || this.afI) {
            YR yR = DofusArenaClientInstance.yl().YP() != null ? DofusArenaClientInstance.yl().YP().vn() : DofusArenaReplayPlayerInstance.XY().YP().vn();
            this.afG = yR.Fx();
            this.Ev = yR.Ft();
            vD vD2 = ((ee_2)apN.aDK().aDL().eg(this.Nl())).NW();
            double d = (vD2.getWorldX() + (double)this.getX()) / 2.0;
            double d2 = (vD2.getWorldY() + (double)this.getY()) / 2.0;
            double d3 = (vD2.getAltitude() + (double)this.wk()) / 2.0;
            et_0 et_02 = new et_0(d, d2, d3);
            yR.c(et_02);
            yR.k((double)afF * this.Ev);
        }
        return super.oS();
    }

    protected void ax() {
        if (this.afH || this.afI) {
            YR yR = DofusArenaClientInstance.yl().YP() != null ? DofusArenaClientInstance.yl().YP().vn() : DofusArenaReplayPlayerInstance.XY().YP().vn();
            yR.c(this.afG);
            yR.k(this.Ev);
        }
        super.ax();
    }

    public boolean wi() {
        return this.afH;
    }

    public void as(boolean bl2) {
        this.afH = bl2;
    }

    public boolean wj() {
        return this.afI;
    }

    public void at(boolean bl2) {
        this.afI = bl2;
    }

    public int getX() {
        return this.aG;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public int getY() {
        return this.aH;
    }

    public void setY(int n2) {
        this.aH = n2;
    }

    public short wk() {
        return this.wp;
    }

    public void T(short s) {
        this.wp = s;
    }
}

