/*
 * Decompiled with CFR 0.152.
 */
public class asB
implements apG {
    private Class ach = atn.class;

    public atn jx(String string) {
        return null;
    }

    public atn j(Class clazz, String string) {
        te_1 te_12 = null;
        if (string != null) {
            if (clazz.equals(fa_2.class)) {
                te_12 = new fa_2();
            } else if (clazz.equals(ml_1.class)) {
                te_12 = new ml_1();
            } else if (clazz.equals(awX.class)) {
                te_12 = new awX();
            } else if (clazz.equals(nh_0.class)) {
                te_12 = new nh_0();
            } else if (clazz.equals(amv_2.class)) {
                te_12 = new amv_2();
            } else if (clazz.equals(wf_1.class)) {
                te_12 = new wf_1();
            } else if (clazz.equals(aue_0.class)) {
                te_12 = new aue_0();
            } else if (clazz.equals(ahF.class)) {
                te_12 = new ahF();
            } else if (clazz.equals(aky_2.class)) {
                te_12 = new aky_2();
            } else if (clazz.equals(Lw.class)) {
                te_12 = new Lw();
            } else if (clazz.equals(aCb.class)) {
                te_12 = new aCb();
            } else if (clazz.equals(apc.class)) {
                te_12 = new apc();
            } else if (clazz.equals(auh_0.class)) {
                te_12 = new auh_0();
            } else if (clazz.equals(gb_0.class)) {
                te_12 = new gb_0();
            } else if (clazz.equals(Se.class)) {
                te_12 = new Se();
            } else if (clazz.equals(aqz.class)) {
                te_12 = new aqz();
            } else if (clazz.equals(Tg.class)) {
                te_12 = new Tg();
            } else if (clazz.equals(to_0.class)) {
                te_12 = new to_0();
            } else if (clazz.equals(yV.class)) {
                te_12 = new yV();
            } else if (clazz.equals(aCb.class)) {
                te_12 = new aCb();
            } else if (clazz.equals(fk_1.class)) {
                te_12 = new fk_1();
            } else if (clazz.equals(aah_2.class)) {
                te_12 = new aah_2();
            } else if (clazz.equals(ala_0.class)) {
                te_12 = new ala_0();
            } else if (clazz.equals(fu_1.class)) {
                te_12 = new fu_1();
            } else if (clazz.equals(ez_1.class)) {
                te_12 = new ez_1();
            } else if (clazz.equals(Cm.class)) {
                te_12 = new Cm();
            } else if (clazz.equals(nX.class)) {
                te_12 = new nX();
            } else if (clazz.equals(aq_0.class)) {
                te_12 = new aq_0();
            } else if (clazz.equals(fk.class)) {
                te_12 = new fk();
            } else if (clazz.equals(aBn.class)) {
                te_12 = new aBn();
            } else if (clazz.equals(anb_0.class)) {
                te_12 = new anb_0();
            } else if (clazz.equals(aza_0.class)) {
                te_12 = new aza_0();
            } else if (clazz.equals(nf_0.class)) {
                te_12 = new nf_0();
            } else if (clazz.equals(av_2.class)) {
                te_12 = new av_2();
            } else if (clazz.equals(jd_2.class)) {
                te_12 = new jd_2();
            } else if (clazz.equals(alw_0.class)) {
                te_12 = new alw_0();
            } else if (clazz.equals(adz_0.class)) {
                te_12 = new adz_0();
            } else if (clazz.equals(pf_1.class)) {
                te_12 = new pf_1();
            }
        }
        if (te_12 != null) {
            te_12.fS(string);
        }
        return te_12;
    }

    public Class uk() {
        return this.ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        String string2 = zp_12.GQ();
        zp_12.j(clazz);
        zp_12.a(new aKI(clazz, string2, "new " + clazz.getSimpleName() + "()"));
        zp_12.a(new aza(null, "setCallBackFunc", string2, "\"" + string + "\""));
        return string2;
    }
}

