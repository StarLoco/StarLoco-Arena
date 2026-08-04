/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from oM
 */
public class om_0
implements atG {
    protected static final Logger a = Logger.getLogger(om_0.class);
    private static om_0 aaP = new om_0();

    public static om_0 tQ() {
        return aaP;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 3140: {
                xb_1 xb_12 = (xb_1)pr_02;
                zc_0 zc_02 = new zc_0(xb_12.fa(), xb_12.fb());
                zc_02.eD(3);
                ql_1.acX().a(zc_02, xb_12.eQ());
                return false;
            }
            case 3128: {
                by_1 by_12 = (by_1)pr_02;
                return false;
            }
            case 3130: {
                cz_0 cz_02 = (cz_0)pr_02;
                return false;
            }
            case 3132: {
                Bs bs = (Bs)pr_02;
                return false;
            }
            case 3134: {
                Uy uy = (Uy)pr_02;
                return false;
            }
            case 3136: {
                ato_0 ato_02 = (ato_0)pr_02;
                return false;
            }
            case 3138: {
                ax_2 ax_22 = (ax_2)pr_02;
                return false;
            }
            case 3142: {
                wh_0 wh_02 = (wh_0)pr_02;
                return false;
            }
            case 3156: {
                kz_1 kz_12 = (kz_1)pr_02;
                axa_0 axa_02 = new axa_0(kz_12.uj(), kz_12.ui(), true, kz_12.WK(), true);
                mc_1.qM().a(axa_0.diQ, axa_02);
                String string = aon_0.aYc().getString("chat.notify.addFriend", kz_12.ui());
                zc_0 zc_03 = new zc_0(string);
                zc_03.eD(5);
                ql_1.acX().a(zc_03);
                return false;
            }
            case 3160: {
                adw_1 adw_12 = (adw_1)pr_02;
                if (!mc_1.qM().a(axa_0.diQ, adw_12.ui())) {
                    String string = aon_0.aYc().getString("error.chat.userNotFound", adw_12.ui());
                    zc_0 zc_04 = new zc_0(string);
                    zc_04.eD(4);
                    ql_1.acX().a(zc_04);
                } else {
                    String string = aon_0.aYc().getString("chat.notify.removeFriend", adw_12.ui());
                    zc_0 zc_05 = new zc_0(string);
                    zc_05.eD(5);
                    ql_1.acX().a(zc_05);
                }
                return false;
            }
            case 3148: {
                dh_0 dh_02 = (dh_0)pr_02;
                HashMap hashMap = mc_1.qM().qN();
                if (hashMap != null) {
                    axa_0 axa_03 = (axa_0)hashMap.get(dh_02.ui().toLowerCase());
                    if (axa_03 != null) {
                        axa_03.ai(true);
                        axa_03.c(dh_02.no());
                        if (axa_03.aJM()) {
                            String string = aon_0.aYc().getString("chat.notify.friendOnline", dh_02.ui());
                            zc_0 zc_06 = new zc_0(string);
                            zc_06.eD(5);
                            ql_1.acX().a(zc_06);
                        }
                    } else {
                        a.error((Object)("Ami inconnu " + dh_02.ui()));
                    }
                }
                azs_0.aLV().a((aho_0)mc_1.qM(), "friends.list");
                return false;
            }
            case 3150: {
                pv_0 pv_02 = (pv_0)pr_02;
                HashMap hashMap = mc_1.qM().qN();
                if (hashMap != null) {
                    axa_0 axa_04 = (axa_0)hashMap.get(pv_02.ui().toLowerCase());
                    if (axa_04 != null) {
                        axa_04.ai(false);
                        if (axa_04.aJM()) {
                            String string = aon_0.aYc().getString("chat.notify.friendOffline", pv_02.ui());
                            zc_0 zc_07 = new zc_0(string);
                            zc_07.eD(5);
                            ql_1.acX().a(zc_07);
                        }
                    } else {
                        a.error((Object)("Ami inconnu " + pv_02.ui()));
                    }
                }
                azs_0.aLV().a((aho_0)mc_1.qM(), "friends.list");
                return false;
            }
            case 3144: {
                aaf_1 aaf_12 = (aaf_1)pr_02;
                ArrayList<axa_0> arrayList = new ArrayList<axa_0>();
                for (qm qm2 : aaf_12.aMB()) {
                    arrayList.add(new axa_0(qm2.adM, qm2.name, qm2.adP != -1L, qm2.adP, qm2.adO));
                }
                mc_1.qM().a(axa_0.diQ, arrayList);
                return false;
            }
            case 3158: {
                ft_0 ft_02 = (ft_0)pr_02;
                axa_0 axa_05 = new axa_0(ft_02.nn());
                mc_1.qM().a(axa_0.diR, axa_05);
                String string = aon_0.aYc().getString("chat.notify.addIgnore", ft_02.nn());
                zc_0 zc_08 = new zc_0(string);
                zc_08.eD(5);
                ql_1.acX().a(zc_08);
                return false;
            }
            case 3162: {
                ahm_0 ahm_02 = (ahm_0)pr_02;
                if (!mc_1.qM().a(axa_0.diR, ahm_02.nn())) {
                    String string = aon_0.aYc().getString("error.chat.userNotFound", ahm_02.nn());
                    zc_0 zc_09 = new zc_0(string);
                    zc_09.eD(4);
                    ql_1.acX().a(zc_09);
                } else {
                    String string = aon_0.aYc().getString("chat.notify.removeIgnore", ahm_02.nn());
                    zc_0 zc_010 = new zc_0(string);
                    zc_010.eD(5);
                    ql_1.acX().a(zc_010);
                }
                return false;
            }
            case 3164: {
                jH jH2 = (jH)pr_02;
                HashMap hashMap = mc_1.qM().qN();
                if (hashMap != null) {
                    axa_0 axa_06 = (axa_0)hashMap.get(jH2.nn().toLowerCase());
                    if (axa_06 != null) {
                        axa_06.ai(true);
                        axa_06.c(jH2.no());
                        String string = aon_0.aYc().getString("chat.notify.ignoreOnline", jH2.nn());
                        zc_0 zc_011 = new zc_0(string);
                        zc_011.eD(5);
                        ql_1.acX().a(zc_011);
                    } else {
                        a.error((Object)("Ignor\u00e9 inconnu " + jH2.nn()));
                    }
                }
                return false;
            }
            case 3166: {
                jf_0 jf_02 = (jf_0)pr_02;
                HashMap hashMap = mc_1.qM().qN();
                if (hashMap != null) {
                    pL pL2 = (pL)hashMap.get(jf_02.nn().toLowerCase());
                    if (pL2 != null) {
                        pL2.ai(false);
                        String string = aon_0.aYc().getString("chat.notify.ignoreOffline", jf_02.nn());
                        zc_0 zc_012 = new zc_0(string);
                        zc_012.eD(5);
                        ql_1.acX().a(zc_012);
                    } else {
                        a.error((Object)("Ignor\u00e9 inconnu " + jf_02.nn()));
                    }
                }
                return false;
            }
            case 3146: {
                abh_0 abh_02 = (abh_0)pr_02;
                ArrayList<axa_0> arrayList = new ArrayList<axa_0>();
                for (String string : abh_02.aNe()) {
                    arrayList.add(new axa_0(string));
                }
                mc_1.qM().a(axa_0.diR, arrayList);
                return false;
            }
            case 3154: {
                ais_2 ais_22 = (ais_2)pr_02;
                zc_0 zc_013 = new zc_0(ais_22.fa(), ais_22.eZ(), ais_22.fb());
                zc_013.eD(2);
                ql_1.acX().a(zc_013, ais_22.fa());
                if (!azs_0.aLV().getBooleanProperty("chat.isMaximize")) {
                    azs_0.aLV().g("chat.isMaximize", true);
                }
                if (!add_1.aOG().kR("chatDialog")) {
                    add_1.aOG().a("chatDialog", oh_2.bq("chatDialog"), 1025L, (short)19501);
                }
                return false;
            }
            case 3152: {
                ck_0 ck_02 = (ck_0)pr_02;
                if (!mc_1.qM().qO().containsKey(ck_02.fa().toLowerCase())) {
                    zc_0 zc_014 = new zc_0(ck_02.fa(), ck_02.eZ(), ck_02.fb());
                    zc_014.eD(1);
                    ql_1.acX().a(zc_014);
                }
                return false;
            }
            case 3198: {
                ano_1 ano_12 = (ano_1)pr_02;
                if (!mc_1.qM().qO().containsKey(ano_12.fa().toLowerCase())) {
                    zc_0 zc_015 = new zc_0(ano_12.fa(), ano_12.eZ(), ano_12.fb());
                    zc_015.eD(7);
                    ql_1.acX().a(zc_015);
                }
                return false;
            }
            case 3168: {
                ayy ayy2 = (ayy)pr_02;
                if (!mc_1.qM().qO().containsKey(ayy2.fa().toLowerCase())) {
                    zc_0 zc_016 = new zc_0(ayy2.fa(), ayy2.eZ(), ayy2.fb());
                    zc_016.eD(8);
                    ql_1.acX().a(zc_016);
                }
                return false;
            }
            case 3170: {
                aik_1 aik_12 = (aik_1)pr_02;
                if (!mc_1.qM().qO().containsKey(aik_12.fa().toLowerCase())) {
                    zc_0 zc_017 = new zc_0(aik_12.fa(), aik_12.eZ(), aik_12.fb());
                    zc_017.eD(9);
                    ql_1.acX().a(zc_017);
                }
                return false;
            }
            case 3206: {
                String string = aon_0.aYc().getString("error.chat.malformedCommand");
                zc_0 zc_018 = new zc_0(string);
                zc_018.eD(4);
                ql_1.acX().a(zc_018);
                return false;
            }
            case 3202: {
                qv_0 qv_02 = (qv_0)pr_02;
                String string = aon_0.aYc().getString("error.chat.channelNotFound", qv_02.eQ());
                zc_0 zc_019 = new zc_0(string);
                zc_019.eD(4);
                ql_1.acX().a(zc_019);
                return false;
            }
            case 3214: {
                String string = aon_0.aYc().getString("error.chat.targetIsYourself");
                zc_0 zc_020 = new zc_0(string);
                zc_020.eD(4);
                ql_1.acX().a(zc_020);
                return false;
            }
            case 3204: {
                ve_1 ve_12 = (ve_1)pr_02;
                String string = aon_0.aYc().getString("error.chat.userNotFound", ve_12.getUserName());
                zc_0 zc_021 = new zc_0(string);
                zc_021.eD(4);
                ql_1.acX().a(zc_021);
                if (apN.aDK().c(qu_2.adx())) {
                    add_1.aOG().f(aon_0.aYc().getString("error.chat.userNotFound", ve_12.getUserName()), 102, 1);
                }
                return false;
            }
            case 3212: {
                String string = aon_0.aYc().getString("error.chat.notYetImplemented");
                zc_0 zc_022 = new zc_0(string);
                zc_022.eD(4);
                ql_1.acX().a(zc_022);
                return false;
            }
            case 3210: {
                String string = aon_0.aYc().getString("error.chat.notEnoughPrivileges");
                zc_0 zc_023 = new zc_0(string);
                zc_023.eD(4);
                ql_1.acX().a(zc_023);
                return false;
            }
            case 3216: {
                String string = aon_0.aYc().getString("error.chat.operationNotPermited");
                zc_0 zc_024 = new zc_0(string);
                zc_024.eD(4);
                ql_1.acX().a(zc_024);
                return false;
            }
            case 2070: {
                add_1 add_12;
                nj nj2 = (nj)pr_02;
                zc_0 zc_025 = new zc_0(nj2.getMessage());
                zc_025.eD(5);
                ql_1.acX().a(zc_025);
                azs_0 azs_02 = azs_0.aLV();
                if (!azs_02.getBooleanProperty("chat.isMaximize")) {
                    azs_02.g("chat.isMaximize", true);
                }
                if (!(add_12 = add_1.aOG()).kR("chatDialog")) {
                    add_1.aOG().a("chatDialog", oh_2.bq("chatDialog"), 1025L, (short)19501);
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

