/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from IF
 */
public class if_1 {
    private static Logger a = Logger.getLogger(if_1.class);
    private final HashMap bhM = new HashMap();
    private static final if_1 bhN = new if_1();

    private if_1() {
        this.UH();
    }

    public static synchronized if_1 UG() {
        return bhN;
    }

    private void UH() {
        this.a(vP.class, new pa_2());
        this.a(agj_1.class, new ahr_0());
        this.a(Insets.class, new aeu_2());
        this.a(String.class, new pp_1());
        this.a(auC.class, new aER());
        this.a(ef_1.class, new acx_0());
        hr_0 hr_02 = new hr_0();
        this.a(bo_0.class, hr_02);
        this.a(BP.class, hr_02);
        this.a(BV.class, hr_02);
        this.a(BT.class, hr_02);
        this.a(ajt_0.class, hr_02);
        this.a(ajn_1.class, hr_02);
        this.a(aea_2.class, hr_02);
        this.a(ahq_1.class, hr_02);
        this.a(xy_0.class, hr_02);
        this.a(qe_1.class, hr_02);
        this.a(acX.class, hr_02);
        this.a(eF.class, hr_02);
        this.a(acf_0.class, hr_02);
        this.a(aiq_0.class, hr_02);
        this.a(aao_2.class, hr_02);
        this.a(aaM.class, hr_02);
        this.a(aDM.class, hr_02);
        this.a(xd_1.class, hr_02);
        this.a(kx_1.class, hr_02);
        this.a(Object.class, new yq_0());
        anh_0 anh_02 = new anh_0();
        this.a(Boolean.TYPE, anh_02);
        this.a(Integer.TYPE, anh_02);
        this.a(Long.TYPE, anh_02);
        this.a(Float.TYPE, anh_02);
        this.a(Double.TYPE, anh_02);
        this.a(Byte.TYPE, anh_02);
        this.a(Short.TYPE, anh_02);
        this.a(Boolean.class, anh_02);
        this.a(Integer.class, anh_02);
        this.a(Long.class, anh_02);
        this.a(Float.class, anh_02);
        this.a(Double.class, anh_02);
        this.a(Byte.class, anh_02);
        this.a(Short.class, anh_02);
        asB asB2 = new asB();
        this.a(atn.class, asB2);
        this.a(fa_2.class, asB2);
        this.a(ml_1.class, asB2);
        this.a(awX.class, asB2);
        this.a(anb_0.class, asB2);
        this.a(aza_0.class, asB2);
        this.a(nf_0.class, asB2);
        this.a(av_2.class, asB2);
        this.a(jd_2.class, asB2);
        this.a(nX.class, asB2);
        this.a(aq_0.class, asB2);
        this.a(fk.class, asB2);
        this.a(aBn.class, asB2);
        this.a(nh_0.class, asB2);
        this.a(amv_2.class, asB2);
        this.a(wf_1.class, asB2);
        this.a(alw_0.class, asB2);
        this.a(aue_0.class, asB2);
        this.a(ahF.class, asB2);
        this.a(aky_2.class, asB2);
        this.a(apc.class, asB2);
        this.a(auh_0.class, asB2);
        this.a(Tg.class, asB2);
        this.a(to_0.class, asB2);
        this.a(aqz.class, asB2);
        this.a(gb_0.class, asB2);
        this.a(Se.class, asB2);
        this.a(yV.class, asB2);
        this.a(Lw.class, asB2);
        this.a(aCb.class, asB2);
        this.a(fk_1.class, asB2);
        this.a(aah_2.class, asB2);
        this.a(ala_0.class, asB2);
        this.a(fu_1.class, asB2);
        this.a(ez_1.class, asB2);
        this.a(Cm.class, asB2);
        this.a(adz_0.class, asB2);
        this.a(pf_1.class, asB2);
        this.a(ag_0.class, new ahM());
        this.a(awl_0.class, new aCk());
    }

    public void a(Class clazz, apG apG2) {
        if (!this.bhM.containsKey(clazz)) {
            this.bhM.put(clazz, apG2);
        } else {
            a.error((Object)("le convertisseur (template=" + clazz.toString() + ") est d\u00e9j\u00e0 utilis\u00e9 !"));
        }
    }

    public boolean n(Class clazz) {
        boolean bl2;
        block2: {
            Class clazz2;
            if (clazz.equals(Object.class)) {
                return false;
            }
            bl2 = this.bhM.containsKey(clazz);
            if (bl2) break block2;
            Iterator iterator = this.bhM.keySet().iterator();
            while (iterator.hasNext() && !(bl2 = clazz.isAssignableFrom(clazz2 = (Class)iterator.next()))) {
            }
        }
        return bl2;
    }

    public Object c(Class clazz, String string) {
        return this.o(clazz).c(clazz, string);
    }

    public apG o(Class clazz) {
        apG apG2 = (apG)this.bhM.get(clazz);
        if (apG2 == null) {
            throw new NullPointerException("On essaye de trouver un convertisseur pour le type " + clazz.getSimpleName());
        }
        return apG2;
    }

    public vP eK(String string) {
        return (vP)this.c(vP.class, string);
    }

    public agj_1 eL(String string) {
        return (agj_1)this.c(agj_1.class, string);
    }

    public String eM(String string) {
        return (String)this.c(String.class, string);
    }

    public Insets eN(String string) {
        return (Insets)this.c(Insets.class, string);
    }

    public ef_1 eO(String string) {
        return (ef_1)this.c(ef_1.class, string);
    }

    public af_1 eP(String string) {
        return (af_1)this.c(af_1.class, string);
    }

    public auC eQ(String string) {
        return (auC)this.c(auC.class, string);
    }
}

