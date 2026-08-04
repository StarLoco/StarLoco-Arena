/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Comparator;
import org.apache.log4j.Logger;

/*
 * Renamed from arq
 */
public final class arq_0
implements ob_0 {
    public static final aAY cPG = new aAY(0L, 0L);
    public static final Comparator OU = new arr_0();
    private static final Logger a = Logger.getLogger(arq_0.class);
    private static final arq_0 cPH = new arq_0();
    private final zm_1 cPI = new zm_1();
    private final zm_1 cPJ = new zm_1();
    private sv_1 cPK = new sv_1(aet_0.dCi, OU);
    private String cPL;
    private boolean cPM = false;

    public static arq_0 aEv() {
        return cPH;
    }

    public boolean isLoaded() {
        return this.cPM;
    }

    public void jc(String string) {
        this.cPL = string;
    }

    public rs_2 e(short s, long l2) {
        cp_2 cp_22 = (cp_2)this.cPJ.an(s);
        if (cp_22 != null) {
            return (rs_2)cp_22.t(l2);
        }
        if (s == 1) {
            a.error((Object)("Attention, on a un rapport de statistiques null : " + s + " " + l2), (Throwable)new Exception());
        }
        return null;
    }

    public void a(short s, long l2, rs_2 rs_22) {
        cp_2 cp_22 = (cp_2)this.cPJ.an(s);
        if (cp_22 == null) {
            cp_22 = new cp_2();
            this.cPJ.b(s, cp_22);
        }
        cp_22.a(l2, rs_22);
    }

    public void aEw() {
        axh_0 axh_02 = axh_0.aJP();
        axh_02.a(this);
        oc_1 oc_12 = sm_1.yS().cn(this.cPL);
        if (oc_12 != null) {
            a.info((Object)"[PLAYER STATISTICS REPORT FOR LADDER] Start loading all datas.");
            oc_12.a(axh_02);
        } else {
            a.error((Object)("La connecteur \u00e0 la base de donn\u00e9e (" + this.cPL + ") n'existe pas"));
        }
    }

    public void b(rs_2 rs_22) {
        if (rs_22 != null) {
            gn_1 gn_12 = gn_1.km();
            gn_12.y(rs_22.wC());
            gn_12.R(rs_22.wD());
            gn_12.a(this);
            gn_12.I(false);
            gn_12.J(false);
            gn_12.f(rs_22.wB());
            oc_1 oc_12 = sm_1.yS().cn(this.cPL);
            if (oc_12 != null) {
                oc_12.a(gn_12);
            } else {
                a.error((Object)("La connecteur \u00e0 la base de donn\u00e9e (" + this.cPL + ") n'existe pas"));
            }
        }
    }

    public void f(short s, long l2) {
        rs_2 rs_22 = (rs_2)((cp_2)this.cPJ.an(s)).t(l2);
        if (rs_22 != null) {
            gn_1 gn_12 = gn_1.km();
            gn_12.y(rs_22.wC());
            gn_12.R(rs_22.wD());
            gn_12.a(this);
            gn_12.I(false);
            gn_12.J(true);
            gn_12.f(rs_22.wB());
            oc_1 oc_12 = sm_1.yS().cn(this.cPL);
            if (oc_12 != null) {
                oc_12.a(gn_12);
            } else {
                a.error((Object)("La connecteur \u00e0 la base de donn\u00e9e (" + this.cPL + ") n'existe pas"));
            }
            ((cp_2)this.cPJ.an(rs_22.wC())).u(rs_22.wD());
        }
    }

    public void g(short s, long l2) {
        rs_2 rs_22 = this.e(s, l2);
        if (rs_22 != null) {
            this.b(rs_22);
        } else {
            a.error((Object)("Pas de sauvegarde possible des statistiques de ce joueur (id=" + l2 + ") - Pas de rapport"));
        }
    }

    public void h(short s, long l2) {
        rs_2 rs_22 = this.i(s, l2);
        this.a(s, l2, rs_22);
        gn_1 gn_12 = gn_1.km();
        gn_12.y(s);
        gn_12.R(l2);
        gn_12.a(this);
        gn_12.I(true);
        gn_12.J(false);
        gn_12.f(rs_22.wB());
        oc_1 oc_12 = sm_1.yS().cn(this.cPL);
        if (oc_12 != null) {
            oc_12.a(gn_12);
        } else {
            a.error((Object)("La connecteur \u00e0 la base de donn\u00e9e (" + this.cPL + ") n'existe pas"));
        }
    }

    public rs_2 i(short s, long l2) {
        rs_2 rs_22 = this.e(s, l2);
        if (rs_22 == null && (rs_22 = this.bT(s)) != null) {
            rs_22.y(s);
            rs_22.R(l2);
        }
        return rs_22;
    }

    public rs_2 aa(byte[] byArray) {
        short s = rs_2.v(byArray);
        rs_2 rs_22 = this.bT(s);
        if (rs_22 != null) {
            rs_22.u(byArray);
        } else {
            a.error((Object)("Impossible de cr\u00e9\u00e9r une instance du rapport de statistiques, le mod\u00e8le n'est pas reconnu : modelId=" + s));
        }
        return rs_22;
    }

    private rs_2 bT(short s) {
        rs_2 rs_22 = (rs_2)this.cPI.an(s);
        if (rs_22 != null) {
            rs_2 rs_23 = rs_22.S();
            if (rs_23 != null) {
                rs_23.a(rs_22);
            }
            return rs_23;
        }
        return null;
    }

    public void c(rs_2 rs_22) {
        if (rs_22 == null) {
            return;
        }
        if (rs_22.W((short)16) != 0L) {
            a.error((Object)("On tente de m'ajouter deux fois ce joueur !!!! " + rs_22.wD()), (Throwable)new Exception());
            return;
        }
        long l2 = System.currentTimeMillis();
        rs_22.b((short)16, l2);
        aAY aAY2 = new aAY(rs_22.wD(), l2);
        if (this.cPK.size() >= aet_0.dCi) {
            aAY aAY3 = (aAY)this.cPK.getFirst();
            this.cPK.remove(0);
            ((cp_2)this.cPJ.an(rs_22.wC())).u(aAY3.wD());
        }
        this.cPK.add(aAY2);
    }

    public void a(rs_2 rs_22, long l2) {
        long l3 = rs_22.W((short)16);
        if (l3 != 0L) {
            a.info((Object)("Retir\u00e9 de la cache : " + l2));
            this.cPK.remove(cPG.n(l2, l3));
            rs_22.b((short)16, 0);
        }
    }

    public void jd(String string) {
        aAN aAN2 = aAN.aMW();
        qf_1 qf_12 = aAN2.aCj();
        aAN2.iJ(string);
        aAN2.a(qf_12, new tf_2[0]);
        aAN2.close();
        this.h(qf_12);
    }

    public void h(qf_1 qf_12) {
        k_0 k_02 = qf_12.by("reportModels");
        if (k_02 != null) {
            ArrayList arrayList = k_02.e("reportModel");
            if (arrayList != null) {
                for (k_0 k_03 : arrayList) {
                    k_0 k_04 = k_03.f("id");
                    k_0 k_05 = k_03.f("class");
                    if (k_04 != null && k_05 != null) {
                        rs_2 rs_22 = arq_0.je(k_05.getStringValue());
                        if (rs_22 != null) {
                            ArrayList arrayList2 = k_03.e("reportEntry");
                            if (arrayList2 != null) {
                                for (k_0 k_06 : arrayList2) {
                                    k_0 k_07 = k_06.f("id");
                                    k_0 k_08 = k_06.f("typeId");
                                    k_0 k_09 = k_06.f("default");
                                    String string = k_08.getStringValue().toLowerCase();
                                    short s = (short)k_07.getIntValue();
                                    if (string.equals("int")) {
                                        rs_22.a(s, k_09.getIntValue());
                                        continue;
                                    }
                                    if (string.equals("long")) {
                                        rs_22.a(s, k_09.getLongValue());
                                        continue;
                                    }
                                    if (!string.equals("float")) continue;
                                    rs_22.a(s, k_09.getFloatValue());
                                }
                                short s = (short)k_04.getIntValue();
                                rs_22.y(s);
                                this.cPI.b(s, rs_22);
                                continue;
                            }
                            a.warn((Object)("Aucune entr\u00e9e trouv\u00e9e pour ce mod\u00e8le de rapport : ID=" + k_04.getIntValue()));
                            continue;
                        }
                        a.error((Object)"Impossible d'instancier le mod\u00e8le de rapport de statistiques");
                        continue;
                    }
                    a.error((Object)"Erreur de formatage de rapport : param\u00e8tre 'id' ou 'class' introuvable");
                }
            } else {
                a.warn((Object)"Pas de model de rapport de statistique d\u00e9finit ( entr\u00e9es 'reportModel' introuvalbles)");
            }
        } else {
            a.error((Object)"Mauvais format de document : racine 'reportModels' introuvable.");
        }
    }

    private static rs_2 je(String string) {
        rs_2 rs_22 = null;
        try {
            Class<?> clazz = Class.forName(string, true, ClassLoader.getSystemClassLoader());
            rs_22 = (rs_2)clazz.newInstance();
        }
        catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        return rs_22;
    }

    public boolean a(pr_0 pr_02) {
        if (pr_02 instanceof ajv_1) {
            ajv_1 ajv_12 = (ajv_1)pr_02;
            rs_2 rs_22 = this.e(ajv_12.wC(), ajv_12.wD());
            if (rs_22 == null) {
                switch (ajv_12.aHG()) {
                    case 1: {
                        a.info((Object)"[PLAYER STATISTICS REPORT FOR LADDER] All datas are loaded.");
                        this.cPM = true;
                        break;
                    }
                    case 3: {
                        a.error((Object)"[PLAYER STATISTICS REPORT FOR LADDER] All datas are not loaded.");
                        break;
                    }
                    default: {
                        a.warn((Object)"[PLAYER STATISTICS REPORT FOR LADDER] Result message unknown (cas de la destruction du coach en g\u00e9n\u00e9ral).");
                        break;
                    }
                }
            } else {
                switch (ajv_12.aHG()) {
                    case 4: {
                        System.err.println("[PLAYER STATISTICS REPORT] Save error (model=" + rs_22.wC() + ", id=" + rs_22.wD() + "): " + ajv_12.getErrorMessage());
                        break;
                    }
                    case 2: {
                        break;
                    }
                    default: {
                        a.warn((Object)("Code de resultat non traite (code=" + ajv_12.aHG() + ")"));
                    }
                }
            }
        }
        return false;
    }

    public void a(arr arr2, Exception exception) {
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

