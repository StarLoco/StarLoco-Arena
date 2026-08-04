/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.log4j.Logger;

/*
 * Renamed from BS
 */
public class bs_0
implements aho_0 {
    public static final byte aJD = 0;
    public static final byte aJE = 1;
    public static final byte aJF = 2;
    public static final byte aJG = 3;
    public static final byte aJH = 4;
    private static final Logger a = Logger.getLogger(bs_0.class);
    private static File zx = null;
    public static final String aJI = "teamManagement.teamPreset1vs1List";
    public static final String aJJ = "teamManagement.teamPreset2vs2List";
    public static final String aJK = "teamManagement.teamPresetTournamentList";
    public static final String aJL = "teamsIconsList";
    public static final String aJM = "teamsBackgroundsList";
    public static final String aJN = "teamsDatas";
    public static final asV[] aJO;
    public static final asV[] aJP;
    public static final String[] ce;
    private static bs_0 aJQ;
    private zm_1 aJR = new zm_1();
    private zK aJS = null;
    private cp_2 aGY;

    public bs_0() {
        byte by;
        azs_0.aLV().g("teamManagement.teamManager", this);
        for (by = 0; by < aJO.length; by = (byte)((byte)(by + 1))) {
            try {
                bs_0.aJO[by] = new asV(0, by, 0);
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        for (by = 0; by < aJP.length; by = (byte)(by + 1)) {
            try {
                bs_0.aJP[by] = new asV(1, by, 0);
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static bs_0 IF() {
        return aJQ;
    }

    public void clear() {
        this.aJR.clear();
        azs_0.aLV().kb("teamManagement.editableTeamPreset");
    }

    public boolean isEmpty() {
        return this.aJR.isEmpty();
    }

    public void c(zK zK2) {
        this.aJR.b(zK2.tI(), zK2);
        azs_0.aLV().a((aho_0)this, aJI);
        azs_0.aLV().a((aho_0)this, aJJ);
        azs_0.aLV().a((aho_0)this, aJK);
    }

    public void a(Iterable iterable) {
        for (zK zK2 : iterable) {
            if (this.aJR.an(zK2.tI()) != null) {
                this.as(zK2.tI());
            }
            this.c(zK2);
        }
    }

    public void IG() {
        int n2;
        ArrayList<sw_1> arrayList = new ArrayList<sw_1>();
        Object[] objectArray = this.aJR.getValues();
        int n3 = objectArray.length;
        for (n2 = 0; n2 < n3; ++n2) {
            sw_1 sw_12 = (sw_1)objectArray[n2];
            if (sw_12.afK()) continue;
            arrayList.add(sw_12);
        }
        for (n2 = 0; n2 < arrayList.size(); ++n2) {
            this.as(((sw_1)arrayList.get(n2)).tI());
        }
    }

    public void as(short s) {
        sw_1 sw_12 = (sw_1)this.aJR.an(s);
        for (long l2 : sw_12.afE().eJ()) {
            adY.atu().Y(l2);
        }
        this.aJR.ao(s);
        this.d(zK.a(null));
        azs_0.aLV().a((aho_0)this, aJI);
        azs_0.aLV().a((aho_0)this, aJJ);
        azs_0.aLV().a((aho_0)this, aJK);
    }

    public zm_1 IH() {
        return this.aJR;
    }

    public zK at(short s) {
        return (zK)this.aJR.an(s);
    }

    public sw_1 du(String string) {
        sw_1 sw_12 = null;
        Object[] objectArray = this.aJR.getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            sw_1 sw_13 = (sw_1)objectArray[j];
            if (!sw_13.getName().equals(string)) continue;
            sw_12 = sw_13;
            break;
        }
        return sw_12;
    }

    public zK II() {
        return this.aJS;
    }

    public void d(zK zK2) {
        this.aJS = zK2;
        this.IK();
        if (zK2 != null) {
            DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cma, zK2.tI());
        }
    }

    public zK IJ() {
        return new zK();
    }

    public void bc(long l2) {
        Object[] objectArray = this.aJR.getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            zK zK2 = (zK)objectArray[j];
            if (!zK2.m(l2)) continue;
            zK2.l(l2);
        }
        if (this.aJS.m(l2)) {
            this.aJS.l(l2);
            this.IK();
        }
    }

    public short bd(long l2) {
        Object[] objectArray = this.aJR.getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            sw_1 sw_12 = (sw_1)objectArray[j];
            if (!sw_12.m(l2)) continue;
            return sw_12.tI();
        }
        return -1;
    }

    private void IK() {
        azs_0.aLV().g("teamManagement.editableTeamPreset", this.aJS);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        File[] fileArray;
        if (string.equals(aJI)) {
            boolean bl2 = DofusArenaClientInstance.yl().aod().a(adc_0.clV);
            ArrayList<zK> arrayList = new ArrayList<zK>();
            Object[] objectArray = this.aJR.getValues();
            int n2 = objectArray.length;
            for (int j = 0; j < n2; ++j) {
                zK zK2 = (zK)objectArray[j];
                if (zK2.getType() == -6) {
                    arrayList.add(zK2);
                    continue;
                }
                if (zK2.getType() != -21 || !bl2) continue;
                arrayList.add(zK2);
            }
            Collections.sort(arrayList, new awx_0(this));
            return arrayList;
        }
        if (string.equals(aJJ)) {
            ArrayList<zK> arrayList = new ArrayList<zK>();
            Object[] objectArray = this.aJR.getValues();
            int n3 = objectArray.length;
            for (int j = 0; j < n3; ++j) {
                zK zK3 = (zK)objectArray[j];
                if (!zK3.afL()) continue;
                arrayList.add(zK3);
            }
            Collections.sort(arrayList, new awh(this));
            return arrayList;
        }
        if (string.equals(aJK)) {
            ArrayList<zK> arrayList = new ArrayList<zK>();
            Object[] objectArray = this.aJR.getValues();
            int n4 = objectArray.length;
            for (int j = 0; j < n4; ++j) {
                zK zK4 = (zK)objectArray[j];
                if (zK4.getType() != -5) continue;
                arrayList.add(zK4);
            }
            Collections.sort(arrayList, new awf(this));
            return arrayList;
        }
        if (string.equals(aJL)) {
            return aJO;
        }
        if (string.equals(aJM)) {
            return aJP;
        }
        if (string.equals(aJN) && (fileArray = zx.listFiles()) != null) {
            ArrayList<String> arrayList = new ArrayList<String>(fileArray.length + 1);
            for (int j = 0; j < fileArray.length; ++j) {
                String[] stringArray = fileArray[j].getName().split("\\.");
                if (!fileArray[j].isFile() || stringArray.length <= 0 || !stringArray[stringArray.length - 1].equals("atd")) continue;
                arrayList.add(fileArray[j].getName());
            }
            return arrayList.toArray();
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    public String be(long l2) {
        return (String)this.aGY.t(l2);
    }

    public void a(cp_2 cp_22) {
        this.aGY = cp_22;
    }

    public cp_2 Hj() {
        return this.aGY;
    }

    static {
        try {
            File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + mu_1.rM().getString("savesPath"));
            if (file.exists() || file.mkdir()) {
                File file2 = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + mu_1.rM().getString("savesPath") + System.getProperty("file.separator") + "Teams");
                if (file2.exists() || file2.mkdir()) {
                    zx = file2;
                } else {
                    a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des sauvegardes d'\u00e9quipes : R\u00e9pertoire non cr\u00e9\u00e9.");
                }
            } else {
                a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des sauvegardes d'\u00e9quipes : R\u00e9pertoire non cr\u00e9\u00e9.");
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des sauvegardes d'\u00e9quipes : ", (Throwable)exception);
        }
        aJO = new asV[61];
        aJP = new asV[14];
        ce = new String[]{aJI, aJJ, aJK, aJN};
        aJQ = new bs_0();
    }
}

