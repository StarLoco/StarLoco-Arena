/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from yf
 */
public class yf_0
extends abS
implements aje,
dE {
    private static final Logger a = Logger.getLogger(yf_0.class);
    public static final String TAG = "wakfuBubble";
    private static final int aAt = 5;
    private static final int aAu = 60;
    public static final String aAv = "text";
    public static final String aAw = "container";
    public static final String aAx = "coloredContainer";
    public static final String aAy = "image";
    public static int aAz = Integer.MIN_VALUE;
    private yt_1 aAA;
    private boolean aAB;
    private boolean aAC = true;
    private boolean aAD = false;
    private boolean aAE = true;
    private boolean aAF = false;
    private boolean aAG = false;
    private adv_2 aAH;
    private abm_2 aAI;
    private acj_2 aAJ;
    private float Gx = 1.0f;
    private boolean aAK;
    private vP aZ;

    public void initialize() {
        this.d(true, true);
    }

    public void d(boolean bl2, boolean bl3) {
        this.b(true, bl2, bl3, false);
    }

    public void a(boolean bl2, boolean bl3, boolean bl4) {
        this.b(true, bl2, bl3, bl4);
    }

    public void b(boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        this.aAA = (yt_1)this.getElementMap().R(aAv);
        if (!this.aAD || this.aAE != bl2) {
            this.aAE = bl2;
            this.EJ();
        }
        this.aAB = bl3;
        this.aAK = bl4;
        this.aAG = bl5;
    }

    protected void EJ() {
        if (this.aAH != null && this.aAH.asE() != null) {
            this.aAH.asE().b(this);
        }
        this.setAlign(this.aAE ? BT.aJT : BT.aJV);
        if (this.q != null) {
            this.a(this.q, this.q.getScreenX(), this.q.getScreenY(), 0);
        }
        this.invalidate();
        this.aAD = true;
    }

    protected void transform() {
        this.aAH.asE().a(this);
    }

    public String setText(String string) {
        return this.setText(string, 1.0f);
    }

    public String setText(String string, float f) {
        assert (this.aAA != null);
        pf_0 pf_02 = adv_2.hz(string);
        if (!((adv_2)((Object)pf_02.getFirst())).equals((Object)this.aAH)) {
            this.aAH = (adv_2)((Object)pf_02.getFirst());
            this.transform();
        }
        string = ((String)pf_02.acl()).trim();
        this.aAA.setText(string);
        this.setDuration((int)((float)xS.cT(string) * f));
        if (this.aAG) {
            this.aV(true);
        }
        this.EK();
        return string;
    }

    public void setTarget(gq_2 gq_22) {
        this.setTarget((ahh_1)gq_22);
        if (gq_22 != null) {
            this.c(gq_22.L());
        }
    }

    public void setTarget(ahh_1 ahh_12) {
        if (ahh_12 == this.q) {
            return;
        }
        if (this.q != null && this.q instanceof ahh_1) {
            ((ahh_1)this.q).b(this);
        }
        super.setTarget(ahh_12);
        if (ahh_12 != null) {
            ahh_12.a(this);
            this.setTargetIsVisible(ahh_12.isVisible());
        }
    }

    public void setBubbleObserver(abm_2 abm_22) {
        this.aAI = abm_22;
        this.aAI.a(this);
    }

    private void c(qc_0 qc_02) {
        boolean bl2 = yf_0.isRightDirection(qc_02);
        if (bl2 != this.aAE) {
            this.aAE = bl2;
            this.EJ();
        }
    }

    public void qa() {
        if (this.aAG) {
            this.aV(false);
        } else {
            this.cleanUp();
        }
    }

    public void cleanUp() {
        this.aAF = false;
        this.aAJ = null;
        if (this.aAB) {
            this.EN();
        } else {
            this.setBubbleIsVisible(false);
        }
        if (this.aAI != null) {
            this.aAI.b(this);
        }
    }

    public void setBubbleIsVisible(boolean bl2) {
        if (this.aAC == bl2) {
            return;
        }
        this.aAC = bl2;
        this.EK();
    }

    protected void EK() {
        this.setVisible(this.ciK && this.aAC);
    }

    public final void invalidate() {
        super.invalidate();
    }

    public adz_1 getComputedPosition(int n2, int n3, int n4) {
        adz_1 adz_12 = super.getComputedPosition(n2, n3, n4);
        int n5 = adz_12.getX();
        int n6 = adz_12.getY();
        int n7 = this.ciO.getDuration();
        if (this.aAF && (n7 == -1 || this.ciO.QI() < n7 / 2) || this.aAH == adv_2.cmK && this.ciO.QI() < 500) {
            n5 += ej_0.n(-3, 3);
            n6 += ej_0.n(-3, 3);
        }
        adz_12.set(n5, n6);
        return adz_12;
    }

    public void a(aFy aFy2, int n2, int n3, int n4) {
        this.EK();
        this.a(this.aAI);
        super.a(aFy2, n2, n3, 0);
    }

    public void setShakingBubble(boolean bl2) {
        this.aAF = bl2;
    }

    public void setDuration(int n2) {
        this.ciO.setDuration(n2);
    }

    public void EL() {
        this.ciO.fW(0);
        this.EK();
    }

    public int getAdviserId() {
        return this.ciO.getId();
    }

    public boolean isToRight() {
        return this.aAE;
    }

    public vP getColor() {
        return this.aZ;
    }

    public void setColor(vP vP2) {
        this.aZ = vP2;
        this.EM();
    }

    public void EM() {
        aht_1 aht_12 = (aht_1)this.getElementMap().R(aAx);
        aht_12.setVisible(this.aZ != null);
        aht_12.getAppearance().setModulationColor(this.aZ);
    }

    public void EN() {
        if (this.getElementMap() != null) {
            add_1.aOG().kO(this.getElementMap().getId());
        }
    }

    public void a(boolean bl2, ns_1 ns_12) {
        this.setTargetIsVisible(bl2);
    }

    public static boolean isRightDirection(qc_0 qc_02) {
        switch (qc_02) {
            case bEP: 
            case bEQ: 
            case bEJ: 
            case bEK: 
            case bEL: {
                return false;
            }
            case bEN: 
            case bEO: 
            case bEM: {
                return true;
            }
        }
        return false;
    }

    public double getWorldX() {
        if (this.q != null) {
            return this.q.getWorldX();
        }
        return 0.0;
    }

    public double getWorldY() {
        if (this.q != null) {
            return this.q.getWorldY();
        }
        return 0.0;
    }

    public double getAltitude() {
        if (this.q != null) {
            return this.q.getAltitude();
        }
        return 0.0;
    }

    public int getDuration() {
        return this.ciO.getDuration();
    }

    public void EO() {
        super.EO();
        if (this.q != null && this.q instanceof ahh_1) {
            ((ahh_1)this.q).b(this);
        }
    }

    public void j() {
        super.j();
        this.aAD = false;
    }

    private void aV(boolean bl2) {
        aji_1 aji_12 = this.getElementMap();
        ArrayList<Zb> arrayList = new ArrayList<Zb>();
        if (aji_12 == null) {
            return;
        }
        adg_2 adg_22 = (adg_2)aji_12.R(aAy);
        if (adg_22 != null) {
            arrayList.add(adg_22.getAppearance());
        }
        if ((adg_22 = (adg_2)aji_12.R(aAw)) != null) {
            arrayList.add(adg_22.getAppearance());
        }
        if ((adg_22 = (adg_2)aji_12.R(aAv)) != null) {
            arrayList.add(adg_22.getAppearance());
        }
        if (adg_22 != null) {
            vP vP2 = new vP(bl2 ? vP.atI.Cf() : vP.atL.Cf());
            vP vP3 = new vP(bl2 ? vP.atL.Cf() : vP.atI.Cf());
            afm_2 afm_22 = new afm_2(vP2, vP3, arrayList, 0, 500, 1, ys.aCq);
            if (!bl2) {
                afm_22.a(new zi_2(this, afm_22));
            }
            adg_22.a(afm_22);
        }
    }

    public void a(abm_2 abm_22, int n2, int n3, short s) {
        this.a(abm_22);
    }

    private void a(abm_2 abm_22) {
        if (abm_22 == null) {
            return;
        }
        int n2 = abm_22.aTI().k(this.q.gn(), this.q.go(), this.q.gp());
        acj_2 acj_22 = n2 > this.aAH.asF() ? acj_2.dua : acj_2.dub;
        if (acj_22 != this.aAJ) {
            if (this.aAK || this.aAH == adv_2.cmL) {
                this.a(acj_22);
            }
            this.aAJ = acj_22;
        }
    }

    private void a(acj_2 acj_22) {
        yt_1 yt_12 = (yt_1)this.getElementMap().R(aAv);
        if (this.aAJ != null) {
            yt_12.setZoomTween(acj_22.getZoomScale(), 500);
        } else {
            yt_12.setZoom(acj_22.getZoomScale());
        }
    }
}

