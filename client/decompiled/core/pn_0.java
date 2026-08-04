/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Pn
 */
public class pn_0
extends do_1
implements ov_1 {
    public static final String bDu = "DEMON_CHALLENGE";
    public static final String bDv = "_BEGIN";
    private int bbw;
    private int bDw;
    private int bDx;
    private int it;
    private String m_name;
    private boolean bDy = false;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            po_0.abV().abW();
            this.b(avr_02);
            this.aYY();
            if (this.it != 0) {
                if (!this.bDy && anr_0.aXN().j(this.it, true) != null) {
                    this.bDy = true;
                    long[] lArray = new long[]{this.nD};
                    anr_0.aXN().a(this.it, 0, lArray, false);
                } else {
                    this.abT();
                }
            } else {
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12.c(avq_0.ce((short)278))) {
                    this.abT();
                } else {
                    this.abU();
                }
            }
        }
        return true;
    }

    public avr_0 dR() {
        return avr_0.dgg;
    }

    public avr_0[] dS() {
        return new avr_0[]{avr_0.dgg};
    }

    public void gD() {
        super.gD();
        this.abT();
    }

    public void j() {
        super.j();
    }

    public void b() {
        super.b();
        this.amP = 1;
        this.aQv = true;
        this.mY = true;
        this.mX = true;
    }

    public String getName() {
        return this.m_name;
    }

    public void gi() {
        super.gi();
        String[] stringArray = this.cmX.split(";");
        if (this.cmX == null || this.cmX.equals("null")) {
            a.error((Object)"[GameDesign] un D\u00e9monChallenge poss\u00e8de 4 param\u00e8tres");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.it = Integer.valueOf(stringArray[1]);
        this.bbw = Integer.valueOf(stringArray[2]);
        this.bDw = Integer.valueOf(stringArray[3]);
        if (stringArray.length > 4) {
            this.bDx = Integer.valueOf(stringArray[4]);
        }
    }

    private void abT() {
        if (!add_1.aOG().kR(bDu + this.bDw + bDv)) {
            nd = (aod_2)add_1.aOG().a(bDu + this.bDw + bDv, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
            ago_2.getInstance().getLayeredContainer().a(nd, 25000);
            for (axu_0 axu_02 : this.aYW()) {
                if (!(axu_02 instanceof tp_1)) continue;
                tp_1 tp_12 = (tp_1)axu_02;
                nd.setTarget(tp_12, 160, -200);
                tp_12.aY("1_AnimParlotte");
            }
            nd.setForcedDisplaySpark(true);
            nd.setUseTargetPositionning(true);
            nd.setText(aon_0.aYc().a(29, this.bDw, new Object[0]));
            nd.a(aon_0.aYc().getString("challenge.accept"), (ov_1)this, true);
            nd.a(aon_0.aYc().getString("challenge.refuse"), (ov_1)this, true);
            nd.setActAsButton(true);
            nd.setVisible(true);
            nd.setCloseOnClick(true);
        } else {
            nd.aab();
            nd = null;
        }
    }

    private void abU() {
        if (!add_1.aOG().kR(bDu + this.bDx + bDv)) {
            nd = (aod_2)add_1.aOG().a(bDu + this.bDx + bDv, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
            ago_2.getInstance().getLayeredContainer().a(nd, 25000);
            for (axu_0 axu_02 : this.aYW()) {
                if (!(axu_02 instanceof tp_1)) continue;
                tp_1 tp_12 = (tp_1)axu_02;
                nd.setTarget(tp_12, 160, -200);
                tp_12.aY("1_AnimParlotte");
            }
            nd.setForcedDisplaySpark(true);
            nd.setUseTargetPositionning(true);
            nd.setText(aon_0.aYc().a(29, this.bDx, new Object[0]));
            nd.a("ok", (ov_1)this, true);
            nd.setActAsButton(true);
            nd.setVisible(true);
            nd.setCloseOnClick(true);
        } else {
            nd.aab();
            nd = null;
        }
    }

    public boolean a(ke ke2) {
        if (ke2.aV().compareTo(qe_1.bFB) == 0) {
            na_1 na_12 = ke2.oF();
            if (na_12 instanceof aqq_0 && ((aqq_0)na_12).getText().equals(aon_0.aYc().getString("challenge.accept"))) {
                apN.aDK().a(do_2.Mm());
                apN.aDK().a(wg_2.CC());
                alv_1 alv_12 = new alv_1();
                alv_12.fH(this.bbw);
                alv_12.bM((short)99);
                apN.aDK().vJ().b(alv_12);
            } else {
                nd.aab();
                nd = null;
            }
            return true;
        }
        return false;
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(pn_0 pn_02, ym_0 ym_02) {
        pn_02.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

