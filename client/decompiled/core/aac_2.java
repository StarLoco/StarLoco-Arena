/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aAC
 */
public class aac_2
extends do_1 {
    public static final String dpQ = "DEMONI";
    public static final String dpR = "_BEGIN";
    private int[] dpS = new int[3];
    private int[] dpT = new int[2];
    private String m_name;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            po_0.abV().abW();
            this.b(avr_02);
            this.aYY();
            sj_1 sj_12 = apN.aDK().Ln();
            if (sj_12.c(avq_0.ce((short)277))) {
                this.abU();
            } else {
                this.aMR();
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
            a.error((Object)"[GameDesign] un DemonI poss\u00e8de 4 param\u00e8tres");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.dpS[0] = Integer.valueOf(stringArray[1]);
        this.dpS[1] = Integer.valueOf(stringArray[2]);
        this.dpS[2] = Integer.valueOf(stringArray[3]);
        this.dpT[0] = Integer.valueOf(stringArray[4]);
        this.dpT[1] = Integer.valueOf(stringArray[5]);
    }

    private void aMR() {
        if (!add_1.aOG().kR(dpQ + this.dpS[0] + dpR)) {
            int n2 = wj_2.Df().Dg();
            nd = (aod_2)add_1.aOG().a(dpQ + this.dpS[0] + dpR, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
            ago_2.getInstance().getLayeredContainer().a(nd, 25000);
            for (axu_0 axu_02 : this.aYW()) {
                if (!(axu_02 instanceof tp_1)) continue;
                tp_1 tp_12 = (tp_1)axu_02;
                nd.setTarget(tp_12, 160, -200);
            }
            nd.setForcedDisplaySpark(true);
            nd.setUseTargetPositionning(true);
            nd.setActAsButton(true);
            Object object = new int[]{0};
            nd.setText(aon_0.aYc().a(29, this.dpS[0], new Object[0]));
            nd.a(aon_0.aYc().getString("dialog.next"), (ov_1)new od_0(this, (int[])object), true);
            nd.setCloseOnClick(false);
            nd.setVisible(true);
        }
    }

    private void abU() {
        if (!add_1.aOG().kR(dpQ + this.dpT[0] + dpR)) {
            int n2 = wj_2.Df().Dg();
            nd = (aod_2)add_1.aOG().a(dpQ + this.dpT[0] + dpR, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
            ago_2.getInstance().getLayeredContainer().a(nd, 25000);
            for (axu_0 axu_02 : this.aYW()) {
                if (!(axu_02 instanceof tp_1)) continue;
                tp_1 tp_12 = (tp_1)axu_02;
                nd.setTarget(tp_12, 160, -200);
            }
            nd.setForcedDisplaySpark(true);
            nd.setUseTargetPositionning(true);
            nd.setActAsButton(true);
            Object object = new int[]{0};
            nd.setText(aon_0.aYc().a(29, this.dpT[0], new Object[0]));
            nd.a(aon_0.aYc().getString("dialog.next"), (ov_1)new oa_2(this, (int[])object), true);
            nd.setCloseOnClick(false);
            nd.setVisible(true);
        }
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(aac_2 aac_22, ym_0 ym_02) {
        aac_22.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }

    static /* synthetic */ int[] a(aac_2 aac_22) {
        return aac_22.dpS;
    }

    static /* synthetic */ int[] b(aac_2 aac_22) {
        return aac_22.dpT;
    }
}

