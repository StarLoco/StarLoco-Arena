/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;

public class lC
extends qs_0 {
    private static aLN Hv = new aLN();
    private static String Hw = "AnimMort";
    private static long Hx = 1500L;

    public lC(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public long oS() {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 == null) {
            return 0L;
        }
        ee_2 ee_22 = (ee_2)adu_02.eg(this.mS());
        sj_1 sj_12 = apN.aDK().Ln();
        if (!(ee_22 == null || adu_02.ass().JI() == 0 || sj_12 != null && sj_12.yQ())) {
            if (adu_02.Zy() == ko_2.bpv && adu_02.ass().du() && ee_22.getId() == adu_02.ass().JG()) {
                azz_0.dnT = Hx * (long)ee_22.PC();
            }
            Hv.info(aon_0.aYc().getString("fight.die", ee_22.getName()));
            ee_22.NW().BS();
            ee_22.NW().BU();
            ee_22.NW().BW();
            ee_22.bm(false);
            ee_22.NW().aY(Hw);
            if (ee_22.oz() != 0) {
                FreeParticleSystem freeParticleSystem = aiJ.ayv().kT(ee_22.oz());
                freeParticleSystem.setPosition((float)ee_22.getWorldX(), (float)ee_22.getWorldY(), (float)ee_22.getAltitude());
                freeParticleSystem.eC(adu_02.getId());
                qd_1.uW().b(freeParticleSystem);
            }
            return Hx;
        }
        return 0L;
    }

    protected void ax() {
        a.info((Object)"onActionFinished DieAction");
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            ee_2 ee_22 = (ee_2)adu_02.eg(this.mS());
            if (ee_22 != null) {
                apN.aDK().aDL().h(ee_22);
                if (ee_22.Dk()) {
                    azs_0.aLV().a((aho_0)ee_22.NX(), "spells");
                }
            } else {
                a.error((Object)"on demande de tuer un personnage qui n'est pas (encore ?) dans le combat");
            }
        } else {
            a.error((Object)"on demande de tuer un personnage alors qu'on n'est pas (plus ?) en combat");
        }
    }
}

