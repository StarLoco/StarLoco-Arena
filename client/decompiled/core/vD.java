/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import org.apache.log4j.Logger;

public class vD
extends amg_1 {
    private static Logger a = Logger.getLogger(vD.class);
    private ee_2 bN = null;
    private FreeParticleSystem atr;
    private FreeParticleSystem ats;
    private FreeParticleSystem att;
    protected static ati_0 ve = new ati_0();
    protected static ati_0 atu;

    public vD(ee_2 ee_22) {
        super(ee_22.getId());
        this.bN = ee_22;
        this.bJ((short)6);
        this.setVisible(false);
        this.be((byte)4);
    }

    public ee_2 tG() {
        return this.bN;
    }

    public short BP() {
        return this.bN.BP();
    }

    public void a(arh_0 arh_02, boolean bl2, boolean bl3) {
        super.a(arh_02, bl2, false);
        int[] nArray = arh_02.aEI();
        if (nArray != null && nArray.length == 3) {
            this.tG().n(nArray[0], nArray[1], (short)nArray[2]);
        }
    }

    public void b(qc_0 qc_02) {
        super.b(qc_02);
        this.tG().d(qc_02);
    }

    public boolean aY(String string) {
        boolean bl2 = super.aY(string);
        if (bl2) {
            this.tG().Ob();
        }
        return bl2;
    }

    public void setVisible(boolean bl2) {
        super.setVisible(bl2);
        if (!bl2) {
            this.BS();
            this.BU();
            this.BW();
        } else {
            this.BR();
        }
    }

    protected yn_0 ae(short s) {
        return yn_0.ae(s);
    }

    public void b(ve_0 ve_02) {
        short s = ve_02.Vk().aiJ();
        yn_0 yn_02 = this.ae(s);
        if (yn_02 == null) {
            return;
        }
        try {
            int n2 = ve_02.jf();
            String[] stringArray = yn_02.ES();
            String string = mu_1.rM().getString("fighterANMEquipmentPath");
            string = String.format(string, n2);
            this.a(s, string, stringArray);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur au chargement de l'\u00e9quipment : " + ve_02.getId() + ", " + exception.toString()));
        }
    }

    public void BQ() {
        if (this.tG() != null) {
            en_1 en_12 = this.tG().Oi();
            for (ve_0 ve_02 : en_12) {
                this.b(ve_02);
            }
        }
    }

    public void c(ve_0 ve_02) {
        short s = ve_02.Vk().aiJ();
        yn_0 yn_02 = this.ae(s);
        if (yn_02 == null) {
            return;
        }
        this.bN(s);
        azs_0.aLV().a((aho_0)this.bN, "actorEquipment");
    }

    public void BR() {
        byte by;
        if (this.bN == null) {
            a.error((Object)("Impossible d'afficher le cercle d'\u00e9quipe sur l'acteur id=" + this.getId()));
        } else if (this.ats == null && 0 <= (by = this.bN.PH().lV()) && by < mx_0.Ks.length && (this.ats = aiJ.ayv().kT(mx_0.Ks[by])) != null) {
            this.ats.a(this);
            qd_1.uW().b(this.ats);
        }
    }

    public void BS() {
        if (this.ats != null) {
            qd_1.uW().cK(this.ats.getId());
            this.ats = null;
        }
    }

    public void BT() {
        byte by;
        if (this.bN != null && (by = this.bN.PH().lV()) >= 0 && by < mx_0.Ks.length) {
            this.BU();
            this.atr = aiJ.ayv().kT(mx_0.Kr[by]);
            if (this.atr != null) {
                this.atr.a(this.tG().NW());
                qd_1.uW().b(this.atr);
                return;
            }
        }
        a.error((Object)("Impossible d'afficher le cercle d'\u00e9quipe sur l'acteur id=" + this.getId()));
    }

    public void BU() {
        if (this.atr != null) {
            qd_1.uW().cK(this.atr.getId());
            this.atr = null;
        }
    }

    public void BV() {
        if (this.bN != null) {
            this.BW();
            this.att = aiJ.ayv().kT(9004);
            if (this.att != null) {
                this.att.a(this.tG().NW());
                qd_1.uW().b(this.att);
                return;
            }
        }
        a.error((Object)("Impossible d'afficher le symbole d'immobilisation sur l'acteur id=" + this.getId()));
    }

    public void BW() {
        if (this.att != null) {
            qd_1.uW().cK(this.att.getId());
            this.att = null;
        }
    }

    public void BX() {
        this.c(ve);
        this.dLn.c(this.tJ);
    }

    public void aO(boolean bl2) {
        if (bl2) {
            this.c(atu);
        } else {
            this.aTA();
        }
        this.dLn.c(this.tJ);
    }

    public void BY() {
        this.aTA();
        this.dLn.c(this.tJ);
    }

    static {
        ve.w(0.2f, 0.2f, 0.2f, 0.0f);
        ve.dZ(true);
        atu = new ati_0();
        atu.w(0.2f, 0.2f, 0.8f, 1.0f);
        atu.dZ(true);
    }
}

