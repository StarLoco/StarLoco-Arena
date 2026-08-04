/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aCn
 */
public class acn_0
extends do_1
implements ov_1 {
    public static final String duh = "DEMONIII";
    public static final String dui = "_BEGIN";
    private long[] apJ;
    private int[] duj;
    private String m_name;
    private int duk = 1;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            po_0.abV().abW();
            this.b(avr_02);
            this.aYY();
            if (!add_1.aOG().kR(duh + this.duj[0] + dui)) {
                nd = (aod_2)add_1.aOG().a(duh + this.duj[0] + dui, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
                ago_2.getInstance().getLayeredContainer().a(nd, 25000);
                for (JG jG : this.aYW()) {
                    if (!(jG instanceof tp_1)) continue;
                    tp_1 tp_12 = (tp_1)jG;
                    nd.setTarget(tp_12, 150, -50);
                    tp_12.aY("1_AnimParlotte");
                }
                nd.setForcedDisplaySpark(true);
                nd.setUseTargetPositionning(true);
                sj_1 sj_12 = apN.aDK().Ln();
                if (!sj_12.c(avq_0.ce((short)275))) {
                    JG jG;
                    jG = new nq();
                    ((nq)jG).K(or_0.YN.tI());
                    ((nq)jG).ab(true);
                    ((nq)jG).L((short)1);
                    apN.aDK().vJ().b((pr_0)jG);
                    nd.setText(aon_0.aYc().a(29, this.duj[0], new Object[0]));
                    nd.a(aon_0.aYc().getString("dialog.next"), (ov_1)this, true);
                } else if (!sj_12.c(avq_0.ce((short)276))) {
                    nd.setText(aon_0.aYc().a(29, this.duj[3], new Object[0]));
                    nd.a("OK", (ov_1)this, true);
                } else if (sj_12.c(avq_0.ce((short)284))) {
                    nd.setText(aon_0.aYc().a(29, this.duj[5], new Object[0]));
                    nd.a("OK", (ov_1)this, true);
                } else if (!sj_12.c(avq_0.ce((short)276))) {
                    nd.setText(aon_0.aYc().a(29, this.duj[3], new Object[0]));
                    nd.a("OK", (ov_1)this, true);
                } else {
                    nd.setText(aon_0.aYc().a(29, this.duj[4], new Object[0]));
                    nd.a(aon_0.aYc().getString("challenge.accept"), (ov_1)this, true);
                    nd.a(aon_0.aYc().getString("challenge.refuse"), (ov_1)this, true);
                }
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
        int n2;
        super.gi();
        String[] stringArray = this.cmX.split(";");
        if (this.cmX == null || this.cmX.equals("null")) {
            a.error((Object)"[GameDesign] un DemonIII poss\u00e8de 10 param\u00e8tres");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.duj = new int[6];
        for (n2 = 1; n2 < 7; ++n2) {
            this.duj[n2 - 1] = Integer.valueOf(stringArray[n2].trim());
        }
        this.apJ = new long[stringArray.length - 7];
        for (n2 = 7; n2 < stringArray.length; ++n2) {
            this.apJ[n2 - 7] = Integer.valueOf(stringArray[n2].trim()).intValue();
        }
    }

    public boolean a(ke ke2) {
        if (ke2.aV().compareTo(qe_1.bFB) == 0) {
            na_1 na_12 = ke2.oF();
            if (na_12 instanceof aqq_0 && ((aqq_0)na_12).getText().equals(aon_0.aYc().getString("dialog.next"))) {
                nd.aab();
                nd = null;
                nd = (aod_2)add_1.aOG().a(duh + this.duj[0] + dui, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
                ago_2.getInstance().getLayeredContainer().a(nd, 25000);
                for (axu_0 axu_02 : this.aYW()) {
                    if (!(axu_02 instanceof tp_1)) continue;
                    tp_1 tp_12 = (tp_1)axu_02;
                    nd.setTarget(tp_12, 150, -50);
                    tp_12.aY("1_AnimParlotte");
                }
                nd.setText(aon_0.aYc().a(29, this.duj[this.duk], new Object[0]));
                if (this.duk < 2) {
                    nd.a(aon_0.aYc().getString("dialog.next"), (ov_1)this, true);
                } else {
                    nd.a("ok", (ov_1)this, true);
                }
                nd.setActAsButton(true);
                nd.setCloseOnClick(true);
                nd.setVisible(true);
                ++this.duk;
            } else if (na_12 instanceof aqq_0 && ((aqq_0)na_12).getText().equals(aon_0.aYc().getString("challenge.accept"))) {
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

    static /* synthetic */ void a(acn_0 acn_02, ym_0 ym_02) {
        acn_02.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

