/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from uk
 */
public class uk_0
extends do_1
implements ov_1 {
    public static final String apH = "CHALLENGE_";
    public static final String apI = "_BEGIN";
    private long[] apJ;
    private int apK;
    private String m_name;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            po_0.abV().abW();
            this.b(avr_02);
            this.aYY();
            if (!add_1.aOG().kR(apH + this.apK + apI)) {
                nd = (aod_2)add_1.aOG().a(apH + this.apK + apI, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
                ago_2.getInstance().getLayeredContainer().a(nd, 25000);
                for (axu_0 axu_02 : this.aYW()) {
                    if (!(axu_02 instanceof tp_1)) continue;
                    tp_1 tp_12 = (tp_1)axu_02;
                    nd.setTarget(tp_12, 150, -50);
                    tp_12.aY("1_AnimParlotte");
                }
                nd.setForcedDisplaySpark(true);
                nd.setUseTargetPositionning(true);
                nd.setText(aon_0.aYc().a(29, this.apK, new Object[0]));
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
            a.error((Object)"[GameDesign] un Challenge poss\u00e8de 1 seul param\u00e8tre : l'id du challenge");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.apK = Integer.valueOf(stringArray[1]);
        this.apJ = new long[stringArray.length - 2];
        for (int j = 2; j < stringArray.length; ++j) {
            this.apJ[j - 2] = Integer.valueOf(stringArray[j].trim()).intValue();
        }
    }

    public boolean a(ke ke2) {
        if (ke2.aV().compareTo(qe_1.bFB) == 0) {
            if (ke2.oF() == nd.getButtons().get(0)) {
                ahy_1.axg().k(this.apJ);
                apN.aDK().a(cj_0.La());
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

    static /* synthetic */ void a(uk_0 uk_02, ym_0 ym_02) {
        uk_02.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

