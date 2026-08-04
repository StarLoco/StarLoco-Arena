/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

public class ay {
    public static final String PACKAGE = "dofusarena.mail";
    private static ArrayList cv = new ArrayList();

    public static void readMail(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof ho_0) {
            ((ho_0)object).fk(true);
            azs_0.aLV().a((aho_0)ayg_0.aKP(), ayg_0.ce);
            azs_0.aLV().g("mailbox.mail", object);
        }
    }

    public static void newMail(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20060);
        acu_1.ara().c(sb_02);
    }

    public static void closeNewMailDialog(ke ke2) {
        sj_1 sj_12 = apN.aDK().Ln();
        ky_2 ky_22 = sj_12.aQn();
        for (int j = 0; j < cv.size(); ++j) {
            wy_2 wy_22 = (wy_2)cv.get(j);
            try {
                ky_22.f(wy_22);
                continue;
            }
            catch (xR xR2) {
                xR2.printStackTrace();
                continue;
            }
            catch (gg gg2) {
                gg2.printStackTrace();
            }
        }
        cv.clear();
        add_1.aOG().kO("newMailDialog");
        azs_0.aLV().a((aho_0)sj_12, "cardInventory");
    }

    public static void sendMail(ke ke2, Ur ur) {
        if (ur.isValid()) {
            Object object;
            afl_0 afl_02 = ur.getProperty("mailbox.newMail");
            if (afl_02 != null && (object = afl_02.getValue()) instanceof ho_0) {
                ho_0 ho_02 = (ho_0)object;
                ayg_0.aKP().a(ho_02);
            }
        } else {
            add_1.aOG().f(aon_0.aYc().getString("mail.mustHaveValidReceiver"), 102, 1);
        }
        cv.clear();
    }

    public static boolean validateNewMailForm(Ur ur) {
        Object object;
        ur.agN();
        afl_0 afl_02 = ur.getProperty("mailbox.newMail");
        if (afl_02 != null && (object = afl_02.getValue()) instanceof ho_0) {
            ho_0 ho_02 = (ho_0)object;
            return ho_02.aWe() != null && !ho_02.aWe().equals("") && !afl_02.isEmpty() && ho_02.aWb() != 0L;
        }
        return false;
    }

    public static void testName(ke ke2, Ur ur) {
        Object object;
        ur.agN();
        afl_0 afl_02 = ur.getProperty("mailbox.newMail");
        if (afl_02 != null && (object = afl_02.getValue()) instanceof ho_0) {
            ho_0 ho_02 = (ho_0)object;
            rr_1 rr_12 = new rr_1();
            rr_12.setName(ho_02.aWe());
            apN.aDK().vJ().b(rr_12);
        }
    }

    public static void reply(ke ke2) {
        Object object = azs_0.aLV().getProperty("mailbox.mail").getValue();
        if (object != null) {
            ho_0 ho_02 = (ho_0)object;
            if (ho_02.aWa() > 0L) {
                if (!ayg_0.aKP().cS(apN.aDK().Ln().getId())) {
                    apN.aDK().Ln().yH();
                    ho_0 ho_03 = new ho_0();
                    ho_03.lE(ho_02.aWc());
                    ho_03.eH(ho_02.aWa());
                    ho_03.setTitle("Re : " + ho_02.getTitle());
                    azs_0.aLV().g("mailbox.newMail", ho_03);
                    add_1.aOG().a("newMailDialog", oh_2.bq("newMailDialog"), 1L, (short)19501);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.mail.mailboxFull"), 1090L, 102, 1);
                }
            } else {
                add_1.aOG().a(aon_0.aYc().getString("error.mail.noReply"), 1090L, 102, 1);
            }
        }
    }

    public static void deleteMail(ke ke2, ho_0 ho_02) {
        if (ho_02 != null) {
            ads_0 ads_02 = new ads_0();
            ads_02.l(new long[]{ho_02.getId()});
            apN.aDK().vJ().b(ads_02);
            ayg_0.aKP().cQ(ho_02.getId());
            ArrayList arrayList = ho_02.aWb() == apN.aDK().Ln().getId() ? ayg_0.aKP().aKS() : ayg_0.aKP().aKT();
            if (arrayList != null && !arrayList.isEmpty()) {
                Collections.sort(arrayList, new rx_1());
                azs_0.aLV().g("mailbox.mail", arrayList.get(0));
            } else {
                azs_0.aLV().g("mailbox.mail", (Object)null);
            }
        }
    }

    public static void toggleInventory(vY vY2, aht_1 aht_12) {
        if (aht_12 == null) {
            return;
        }
        aht_12.setVisible(vY2.isSelected());
    }

    public static void addItemToMail(aiU aiU2) {
        Object object = aiU2.getValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ky_2 ky_22 = apN.aDK().Ln().yD();
            if (!((xj)wy_22.NR()).tp() && ky_22.bU(wy_22.jf())) {
                ho_0 ho_02 = (ho_0)azs_0.aLV().getProperty("mailbox.newMail").getValue();
                jg_0 jg_02 = ho_02.Ax();
                if (jg_02 == null || jg_02.size() < 10) {
                    ho_02.aM(wy_22.jf());
                    sj_1 sj_12 = apN.aDK().Ln();
                    sj_12.yD().f(wy_22.je(), (short)-1);
                    wy_2 wy_23 = aoi_0.aXY().ac(ByteBuffer.wrap(wy_22.cd()));
                    wy_23.q((short)1);
                    cv.add(wy_23);
                    azs_0.aLV().a((aho_0)apN.aDK().Ln(), "cardInventory");
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.mail.cantAddMoreCard"), 66L, 102, 1);
                }
            } else {
                add_1.aOG().a(aon_0.aYc().getString("error.mail.cantSendALinkedCard"), 66L, 102, 1);
            }
        }
    }

    public static void removeItemFromMail(aly_2 aly_22) {
        Object object = aly_22.getValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ho_0 ho_02 = (ho_0)azs_0.aLV().getProperty("mailbox.newMail").getValue();
            int n2 = wy_22.jf();
            ho_02.aN(n2);
            sj_1 sj_12 = apN.aDK().Ln();
            wy_2 wy_23 = null;
            for (int j = 0; j < cv.size(); ++j) {
                if (((wy_2)cv.get(j)).jf() != n2) continue;
                wy_23 = (wy_2)cv.get(j);
            }
            if (wy_23 != null) {
                try {
                    sj_12.yD().f(wy_23);
                }
                catch (xR xR2) {
                    xR2.printStackTrace();
                }
                catch (gg gg2) {
                    gg2.printStackTrace();
                }
            }
        }
    }

    public static void removeItemFromMail(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ho_0 ho_02 = (ho_0)azs_0.aLV().getProperty("mailbox.newMail").getValue();
            int n2 = wy_22.jf();
            ho_02.aN(n2);
            sj_1 sj_12 = apN.aDK().Ln();
            wy_2 wy_23 = null;
            for (int j = 0; j < cv.size(); ++j) {
                if (((wy_2)cv.get(j)).jf() != n2) continue;
                wy_23 = (wy_2)cv.get(j);
            }
            if (wy_23 != null) {
                try {
                    sj_12.yD().f(wy_23);
                }
                catch (xR xR2) {
                    xR2.printStackTrace();
                }
                catch (gg gg2) {
                    gg2.printStackTrace();
                }
            }
        }
    }

    public static void getItemFromMail(ke ke2) {
        ho_0 ho_02 = (ho_0)azs_0.aLV().getProperty("mailbox.mail").getValue();
        if (ho_02 != null && ho_02.Ax() != null) {
            if (!ay.a(ho_02.Ax())) {
                add_1.aOG().f(aon_0.aYc().getString("error.mail.uniqueCoachCardAlreadyThere"), 102, 1);
            }
            akk_1 akk_12 = new akk_1();
            akk_12.eE(ho_02.getId());
            apN.aDK().vJ().b(akk_12);
        }
    }

    public static void tabItemChange(vY vY2) {
        String string = ((dl_1)vY2.oF()).getText();
        ArrayList arrayList = null;
        if (string.equals(aon_0.aYc().getString("inbox"))) {
            arrayList = ayg_0.aKP().aKS();
        } else if (string.equals(aon_0.aYc().getString("sentbox"))) {
            arrayList = ayg_0.aKP().aKT();
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            Collections.sort(arrayList, new rw_0());
            azs_0.aLV().g("mailbox.mail", arrayList.get(0));
        } else {
            azs_0.aLV().g("mailbox.mail", (Object)null);
        }
    }

    private static boolean a(jg_0 jg_02) {
        boolean bl2 = true;
        for (int j = 0; j < jg_02.size(); ++j) {
            int n2 = jg_02.bu(j);
            if (!((xj)la_0.XJ().pj(n2)).isUnique()) continue;
            ky_2 ky_22 = apN.aDK().Ln().aQn();
            xj xj2 = (xj)la_0.XJ().pj(n2);
            if (!(ky_22.pI().ab(xj2.jf()) && ((wy_2)ky_22.pI().ad(xj2.jf()).get(0)).hG() > 1 || ky_22.pH().ab(xj2.jf()) && ((wy_2)ky_22.pH().ad(xj2.jf()).get(0)).hG() > 1) && (!ky_22.pI().ab(xj2.jf()) || !ky_22.pH().ab(xj2.jf()))) continue;
            bl2 = false;
        }
        return bl2;
    }
}

