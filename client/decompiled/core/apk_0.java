/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.HelpCommand;
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.NavigateToParentCommandSetCommand;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from apK
 */
public final class apk_0
extends OK {
    private static final String cMI = ";";
    private static final String cMJ = "/";
    private static final String cMK = "!";
    private static final String cML = ">";
    private static final Pattern cMM = Pattern.compile("^((/(\\w+))+/){1}|^(((\\w+)/)+){1}|^(/\\w+){1}");
    private static final acb_0 cMN = new acb_0("[.]{2}", null, new NavigateToParentCommandSetCommand(), false);
    private static final acb_0 cMO = new acb_0("/\\?", null, new HelpCommand(), false);
    private static final apk_0 cMP = new apk_0();
    private aiw_2 cMQ;
    private aiw_2 aUK;
    private MC cMR = null;
    private List cMS;
    private boolean cMT = true;
    private boolean cMU = true;
    private byte cMV = (byte)127;

    public apk_0() {
        this.cMQ = new aiw_2();
        this.cMQ.a(cMN);
        this.cMQ.a(cMO);
        this.aUK = new aiw_2();
        this.cMS = new ArrayList();
    }

    public static apk_0 aDz() {
        return cMP;
    }

    public void a(MC mC) {
        this.cMR = mC;
    }

    public boolean aDA() {
        return this.cMT;
    }

    public void dO(boolean bl2) {
        this.cMT = bl2;
    }

    public boolean aDB() {
        return this.cMU;
    }

    public void dP(boolean bl2) {
        this.cMU = bl2;
    }

    public byte aDC() {
        return this.cMV;
    }

    public void aP(byte by) {
        this.cMV = by;
    }

    public void a(nz_0 nz_02) {
        this.cMS.add(nz_02);
    }

    public boolean b(nz_0 nz_02) {
        if (this.cMS.contains(nz_02)) {
            this.cMS.remove(nz_02);
            return true;
        }
        return false;
    }

    public aiw_2 aDD() {
        return this.cMQ;
    }

    public void aDE() {
        if (this.aUK != null && this.aUK.ayE() != null) {
            this.b(this.aUK.ayE());
        }
    }

    public void b(aiw_2 aiw_22) {
        if (aiw_22 != null && aiw_22 != this.aUK) {
            this.aUK = aiw_22;
            String string = this.getPrompt();
            for (nz_0 nz_02 : this.cMS) {
                nz_02.setPrompt(string);
            }
        }
    }

    public boolean f(URL uRL) {
        aiw_2 aiw_22;
        if (this.aUK != null && (aiw_22 = this.aUK.ayF()) != null) {
            return aiw_22.f(uRL);
        }
        return false;
    }

    public aiw_2 aDF() {
        return this.aUK;
    }

    public String getPrompt() {
        if (this.aUK != null) {
            return this.aUK.getPath() + cML;
        }
        return "";
    }

    public void iS(String string) {
        this.v(string, true);
    }

    public void v(String string, boolean bl2) {
        if (bl2) {
            this.fz(string);
        }
        String[] stringArray = null;
        stringArray = this.aDA() ? string.split(cMI) : new String[]{string};
        for (String string2 : stringArray) {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            string2 = string2.trim();
            aiw_2 aiw_22 = null;
            if (this.aDB() && string2.startsWith(cMK)) {
                string2 = string2.substring(1);
                aiw_22 = this.aUK;
            }
            if (this.aDB() && ((Matcher)(object4 = cMM.matcher(string2))).find()) {
                object3 = ((Matcher)object4).group();
                boolean bl3 = ((String)object3).startsWith(cMJ);
                if (bl3) {
                    object2 = ((String)object3).substring(1).split(cMJ);
                    object = this.aUK.ayF();
                } else {
                    object2 = ((String)object3).split(cMJ);
                    object = this.aUK;
                }
                if (((String[])object2).length == 1 && !((String)object3).endsWith(cMJ)) {
                    string2 = string2.substring(1);
                } else {
                    for (String string3 : object2) {
                        ArrayList arrayList = ((aiw_2)object).b(string3, this.cMV);
                        if (arrayList.isEmpty()) {
                            this.err("Chemin " + string3 + " invalide");
                            break;
                        }
                        if (arrayList.size() == 1) {
                            adb_2 adb_22 = (adb_2)arrayList.get(0);
                            if (!(adb_22 instanceof aiw_2)) {
                                this.err("Chemin " + string3 + " invalide");
                                break;
                            }
                        } else {
                            this.err("Trop de possibilit\u00e9s");
                            break;
                        }
                        object = (aiw_2)arrayList.get(0);
                    }
                    string2 = string2.substring(((String)object3).length());
                }
                this.b((aiw_2)object);
            }
            object4 = new ArrayList();
            ((ArrayList)object4).addAll(this.aUK.b(string2, this.cMV));
            ((ArrayList)object4).addAll(this.cMQ.b(string2, this.cMV));
            if (((ArrayList)object4).isEmpty()) {
                if (this.cMR != null) {
                    object3 = new ArrayList<String>();
                    ((ArrayList)object3).add(string2);
                    this.cMR.a(this, null, (ArrayList)object3);
                } else {
                    this.err("Commande '" + string2 + "' invalide");
                }
            } else {
                object3 = ((ArrayList)object4).iterator();
                while (object3.hasNext()) {
                    adb_2 adb_23 = (adb_2)object3.next();
                    object = adb_23.arn();
                    object2 = adb_23.aOE();
                    Matcher matcher = ((Pattern)object2).matcher(string2);
                    if (matcher.matches()) {
                        matcher.reset();
                        ArrayList<String> arrayList = new ArrayList<String>();
                        while (matcher.find()) {
                            for (int j = 0; j <= matcher.groupCount(); ++j) {
                                arrayList.add(matcher.group(j));
                            }
                        }
                        try {
                            object.a(this, adb_23, arrayList);
                        }
                        catch (Exception exception) {
                            this.err("Exception dans l'ex\u00e9cution de la commande \u00e0 la ligne : " + string2);
                        }
                    } else if (((Pattern)object2).pattern().length() != 0) {
                        this.err("Les param\u00e8tres de commande ne correspondent pas !");
                    }
                    if (object.J()) continue;
                    break;
                }
            }
            if (aiw_22 == null) continue;
            this.b(aiw_22);
        }
    }

    public void log(String string) {
        for (nz_0 nz_02 : this.cMS) {
            nz_02.log(string);
        }
    }

    public void trace(String string) {
        for (nz_0 nz_02 : this.cMS) {
            nz_02.trace(string);
        }
    }

    public void b(String string, int n2) {
        for (nz_0 nz_02 : this.cMS) {
            nz_02.b(string, n2);
        }
    }

    public void err(String string) {
        for (nz_0 nz_02 : this.cMS) {
            nz_02.err(string);
        }
    }
}

