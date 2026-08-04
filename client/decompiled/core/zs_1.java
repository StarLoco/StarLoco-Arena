/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Zs
 */
public class zs_1
extends do_1
implements ov_1 {
    public static final String cdc = "BREEDMASTER_";
    public static final String cdd = "_BEGIN";
    private int apK;
    private int cde;
    private int bbw;
    private byte fp;
    private String m_name;

    public void a(axu_0 axu_02) {
    }

    public boolean a(avr_0 avr_02, aox_2 aox_22) {
        a.info((Object)("Action performed on interactive element : " + avr_02.toString()));
        if (avr_02 == avr_0.dgg && this.gA()) {
            po_0.abV().abW();
            this.b(avr_02);
            this.aYY();
            if (!add_1.aOG().kR(cdc + this.apK + cdd)) {
                nd = (aod_2)add_1.aOG().a(cdc + this.apK + cdd, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
                ago_2.getInstance().getLayeredContainer().a(nd, 25000);
                for (axu_0 axu_02 : this.aYW()) {
                    if (!(axu_02 instanceof tp_1)) continue;
                    tp_1 tp_12 = (tp_1)axu_02;
                    nd.setTarget(tp_12, 140, -240);
                }
                nd.setForcedDisplaySpark(true);
                nd.setUseTargetPositionning(true);
                nd.setText(aon_0.aYc().a(29, this.apK, new Object[0]));
                nd.a(aon_0.aYc().getString("breedmaster.testBreed"), (ov_1)this, true);
                nd.a(aon_0.aYc().getString("breedmaster.recruit"), (ov_1)this, true);
                nd.a(aon_0.aYc().getString("cancel"), (ov_1)this, true);
                nd.setActAsButton(true);
                nd.setVisible(true);
                nd.setCloseOnClick(true);
            } else {
                nd.aab();
                nd = null;
            }
            nq nq2 = new nq();
            nq2.K(or_0.YY.tI());
            nq2.ab(true);
            nq2.L((short)1);
            apN.aDK().vJ().b(nq2);
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
            a.error((Object)"[GameDesign] un BreedMaster poss\u00e8de 5 param\u00e8tres : l'id du texte pour le nom, l'id du texte pour le dialogue,l'id du dialogue si le joueur a d\u00e9j\u00e0 3 fighters, l'id de classe et l'id du d\u00e9fi");
            return;
        }
        this.m_name = aon_0.aYc().a(29, Integer.valueOf(stringArray[0]), new Object[0]);
        this.apK = Integer.valueOf(stringArray[1]);
        this.cde = Integer.valueOf(stringArray[2]);
        this.fp = Byte.valueOf(stringArray[3]);
        this.bbw = Integer.valueOf(stringArray[4]);
    }

    public boolean a(ke ke2) {
        if (ke2.aV().compareTo(qe_1.bFB) == 0 && nd.getButtons().size() == 3) {
            if (ke2.oF() == nd.getButtons().get(0)) {
                apN.aDK().a(do_2.Mm());
                apN.aDK().a(wg_2.CC());
                alv_1 alv_12 = new alv_1();
                alv_12.fH(this.bbw);
                alv_12.bM((short)99);
                apN.aDK().vJ().b(alv_12);
                nd.aab();
                nd = null;
                return false;
            }
            if (ke2.oF() == nd.getButtons().get(1)) {
                Object object;
                aba_0 aba_02 = xz_0.amc().afE();
                int n2 = 0;
                int n3 = 0;
                for (long l2 : aba_02.eJ()) {
                    ee_2 ee_22 = adY.atu().dz(l2);
                    if (ee_22 == null || ee_22.NB() != 0) continue;
                    ++n2;
                    if (ee_22.NY().lV() != this.fp) continue;
                    ++n3;
                }
                if (n3 < 2) {
                    azs_0.aLV().g("fighterCreationTutorialBreedTitle", aon_0.aYc().getString("content.5." + this.fp));
                    azs_0.aLV().g("fighterCreationTutorialBreedDescription", aon_0.aYc().getString("content.6." + this.fp));
                    azs_0.aLV().g("fighterCreationTutorialBreedStyle", "fighterCreationBreed" + this.fp);
                    object = new sb_0();
                    ((aed_2)object).f(23050);
                    acu_1.ara().c((pr_0)object);
                    sb_0 sb_02 = new sb_0();
                    sb_02.a(this.fp);
                    sb_02.f(23057);
                    acu_1.ara().c(sb_02);
                    nd.aab();
                    nd = null;
                } else {
                    nd.aab();
                    nd = null;
                    nd = (aod_2)add_1.aOG().a(cdc + this.cde + cdd, oh_2.bq("interactiveBubbleDialog"), Integer.MAX_VALUE, 64L, (short)30001);
                    ago_2.getInstance().getLayeredContainer().a(nd, 25000);
                    object = this.aYW().iterator();
                    while (object.hasNext()) {
                        axu_0 axu_02 = (axu_0)object.next();
                        if (!(axu_02 instanceof tp_1)) continue;
                        tp_1 tp_12 = (tp_1)axu_02;
                        nd.setTarget(tp_12, 140, -240);
                        tp_12.aY("1_AnimParlotte");
                    }
                    nd.setForcedDisplaySpark(true);
                    nd.setUseTargetPositionning(true);
                    int n4 = this.cde;
                    if (n3 >= 2) {
                        nd.setText(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"));
                    } else {
                        nd.setText(aon_0.aYc().a(29, n4, new Object[0]));
                    }
                    nd.a("OK", (ov_1)this, true);
                    nd.setActAsButton(true);
                    nd.setVisible(true);
                    nd.setCloseOnClick(true);
                }
            } else {
                nd.aab();
                nd = null;
            }
            return true;
        }
        if (nd.getButtons().size() == 1) {
            nd.aab();
            nd = null;
            return true;
        }
        return false;
    }

    public xy_0 getCursorType() {
        return xy_0.bYv;
    }

    static /* synthetic */ void a(zs_1 zs_12, ym_0 ym_02) {
        zs_12.a(ym_02);
    }

    static /* synthetic */ Logger dT() {
        return a;
    }
}

