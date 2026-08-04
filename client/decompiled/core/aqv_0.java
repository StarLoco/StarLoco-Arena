/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.log4j.Logger;

/*
 * Renamed from aqv
 */
public class aqv_0 {
    public static final String PACKAGE = "dofusarena.reportBug";
    private static final Logger a = Logger.getLogger(aqv_0.class);

    public static void closeReportBugDialog(ke ke2) {
        if (ke2.aV() == qe_1.bFB) {
            sb_0 sb_02 = new sb_0();
            sb_02.f(16428);
            acu_1.ara().c(sb_02);
        }
    }

    public static boolean validateReportBugForm(Ur ur) {
        ur.agN();
        afl_0 afl_02 = ur.getProperty("editableBugReport");
        if (afl_02 != null) {
            String string = afl_02.hV("title");
            String string2 = afl_02.hV("description");
            String string3 = afl_02.hV("selectedType");
            if (string != null && string2 != null && string.length() > 1 && string2.length() > 1 && string3 != null && string3.length() > 1) {
                return true;
            }
            add_1.aOG().a(aon_0.aYc().getString("ReportBug.invalidForm"), 1091L, 102, 1);
            return false;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    public static void sendReportBug(ke ke2, Ur ur, auY auY2) {
        if ((ke2.aV() == qe_1.bFB || ke2.aV() == qe_1.bFm && ((aqG)ke2).getKeyCode() == 10) && ur.isValid()) {
            File file;
            String string;
            BufferedReader bufferedReader;
            Object object;
            File file2;
            aho_0 aho_02;
            JG jG;
            auY2.jK("");
            auY2.dT(0L);
            auY2.jL("");
            auY2.setType((String)ur.getProperty("bugTypeSelected").getValue());
            apN apN2 = apN.aDK();
            if (apN2 != null) {
                jG = apN2.aDL();
                if (jG != null) {
                    auY2.jK(String.valueOf(((axw)jG).getId()));
                }
                if ((aho_02 = apN2.Ln()) != null) {
                    auY2.dT(aho_02.Lc());
                    auY2.jL(aho_02.getName());
                }
            }
            auY2.at(0L);
            auY2.bx("");
            auY2.ms(0);
            auY2.mt(0);
            jG = apN.aDK().Ln();
            if (jG != null) {
                auY2.at(((ahh_1)((Object)jG)).getId());
                auY2.bx(((aez_0)jG).Ld());
                auY2.ms((int)((ahh_1)((Object)jG)).getWorldX());
                auY2.mt((int)((ahh_1)((Object)jG)).getWorldY());
            }
            aho_02 = DofusArenaClientInstance.yl().aod();
            auY2.c(DofusArenaClientInstance.yl().YN().lb());
            auY2.setVersion(kS.FL);
            afl_0 afl_02 = ur.getProperty("editableBugReport");
            String string2 = "";
            String string3 = "";
            try {
                file2 = new File(System.getProperty("user.dir"));
                if (file2 != null && (object = file2.listFiles()) != null && ((File[])object).length > 0) {
                    for (File file3 : object) {
                        if (file3.getName().length() <= 5 || !file3.getName().substring(0, 6).equalsIgnoreCase("hs_err")) continue;
                        bufferedReader = new BufferedReader(new FileReader(file3));
                        while ((string = bufferedReader.readLine()) != null) {
                            string3 = string3 + string;
                        }
                        bufferedReader.close();
                        string2 = string2 + "\n\n";
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "output.log");
                bufferedReader = new BufferedReader(new FileReader(file));
                string2 = string2 + "output.log :\n";
                while ((string = bufferedReader.readLine()) != null) {
                    string2 = string2 + string;
                }
                bufferedReader.close();
                string2 = string2 + "\n\n";
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "error.log");
                bufferedReader = new BufferedReader(new FileReader(file));
                string2 = string2 + "error.log :\n";
                while ((string = bufferedReader.readLine()) != null) {
                    string2 = string2 + string;
                }
                bufferedReader.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
            auY2.jM(string2);
            try {
                ny_2.sR().flush();
                file2 = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "replays");
                if (file2 != null) {
                    object = file2.listFiles();
                    Object var14_16 = null;
                    Object object2 = null;
                    if (object != null && ((Object)object).length > 0) {
                        for (Object object3 : object) {
                            void var14_17;
                            if (var14_17 != null && ((File)object3).getName().substring(0, 8).compareTo((String)var14_17) <= 0) continue;
                            String string4 = ((File)object3).getName().substring(0, 8);
                            object2 = object3;
                        }
                        bufferedReader = new BufferedReader(new FileReader((File)object2));
                        while ((string = bufferedReader.readLine()) != null) {
                            string3 = string3 + string;
                        }
                        bufferedReader.close();
                    }
                }
            }
            catch (Exception exception) {
                a.error((Object)"Erreur \u00e0 la lecture des replays : ", (Throwable)exception);
            }
            auY2.jN(string3);
            object = new aEy();
            ((aed_2)object).f(16389);
            ((aEy)object).a(auY2);
            acu_1.ara().c((pr_0)object);
        }
    }
}

