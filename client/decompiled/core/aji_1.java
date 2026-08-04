/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * Renamed from ajI
 */
public class aji_1 {
    private static final Logger a = Logger.getLogger(aji_1.class);
    public static final String cAI = ".";
    public static final String cAJ = "~";
    public static String cAK = "#";
    private String rE;
    private HashMap cAL;
    private final HashMap cAM = new HashMap();
    private afq_1 cAN;
    private HashMap cAO = null;
    private aji_1 cAP = null;
    private ArrayList uA = null;

    public aji_1(String string, afq_1 afq_12) {
        this.rE = string;
        this.cAN = afq_12;
    }

    public void a(String string, na_1 na_12) {
        if (this.cAL == null) {
            this.cAL = new HashMap();
        }
        if (na_12 != null && string != null && this.cAL.get(string) != na_12) {
            na_12.setId(string);
            this.cAL.put(string, na_12);
        }
    }

    public na_1 R(String string) {
        aji_1 aji_12;
        na_1 na_12;
        String[] stringArray = string.split("\\.", 2);
        String string2 = string;
        String string3 = null;
        String string4 = null;
        int n2 = -1;
        if (stringArray.length >= 1) {
            string2 = stringArray[0];
        }
        if (stringArray.length >= 2) {
            string4 = stringArray[1];
        }
        if ((stringArray = string2.split(cAK, 2)).length >= 1) {
            string2 = stringArray[0];
        }
        if (stringArray.length >= 2) {
            n2 = Gr.d((Object)stringArray[1], -1);
        }
        if ((stringArray = string2.split(cAJ, 2)).length >= 1) {
            string2 = stringArray[0];
        }
        if (stringArray.length >= 2) {
            string3 = stringArray[1];
        }
        na_1 na_13 = na_12 = this.cAL != null ? (na_1)this.cAL.get(string2) : null;
        if (na_12 == null && this.cAP != null) {
            na_12 = this.cAP.R(string2);
        }
        if (na_12 instanceof he_2 && n2 != -1) {
            na_12 = ((he_2)((Object)na_12)).getWidget(string3, n2);
        }
        if (string4 != null && na_12 instanceof qa_1 && (aji_12 = ((qa_1)na_12).getInnerElementMap()) != null) {
            na_12 = aji_12.R(string4);
        }
        return na_12;
    }

    public afq_1 azj() {
        return this.cAN;
    }

    public void a(afq_1 afq_12) {
        if (afq_12 != this.cAN) {
            this.cAN = afq_12;
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                ((aji_1)this.uA.get(j)).a(this.cAN);
            }
        }
    }

    public String getId() {
        return this.rE;
    }

    public boolean io(String string) {
        if (string == null) {
            return false;
        }
        if (string.equalsIgnoreCase(this.rE) || add_1.aOG().aq(this.rE, string)) {
            this.ip(string);
            return true;
        }
        return false;
    }

    private void ip(String string) {
        String string2 = this.rE;
        this.cAN.at(this.rE, string);
        this.rE = string;
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                aji_1 aji_12 = (aji_1)this.uA.get(j);
                if (aji_12.getId() == null) continue;
                String string3 = this.rE + aji_12.getId().substring(string2.length());
                aji_12.ip(string3);
            }
        }
    }

    public boolean iq(String string) {
        if (string == null) {
            return false;
        }
        return this.R(string) != null;
    }

    public boolean X(String string, String string2) {
        if (string == null && string2 == null || string != null && string.equalsIgnoreCase(string2)) {
            return true;
        }
        if (this.cAL == null || this.cAL.containsKey(string2) || !this.cAL.containsKey(string)) {
            return false;
        }
        na_1 na_12 = (na_1)this.cAL.remove(string);
        if (string2 != null) {
            this.cAL.put(string2, na_12);
        }
        return true;
    }

    public void removeElement(String string) {
        if (this.cAL != null) {
            this.cAL.remove(string);
        }
    }

    public void o(na_1 na_12) {
        if (na_12 == null || this.cAL == null) {
            return;
        }
        String string = na_12.getId();
        if (string == null) {
            for (Map.Entry entry : this.cAL.entrySet()) {
                if (entry.getValue() != na_12) continue;
                string = (String)entry.getKey();
                break;
            }
        }
        if (string != null) {
            this.cAL.remove(string);
        }
    }

    private void a(aji_1 aji_12) {
        if (this.uA == null) {
            this.uA = new ArrayList(5);
        }
        this.uA.add(aji_12);
    }

    private void b(aji_1 aji_12) {
        if (this.uA == null) {
            return;
        }
        this.uA.remove(aji_12);
    }

    public void c(aji_1 aji_12) {
        if (this.cAP != null) {
            this.cAP.b(this);
        }
        this.cAP = aji_12;
        if (this.cAP != null) {
            this.cAP.a(this);
        }
    }

    public aji_1 azk() {
        return this.cAP;
    }

    public void b(afl_0 afl_02) {
        this.cAM.put(afl_02.getName(), afl_02);
    }

    public afl_0 getProperty(String string) {
        afl_0 afl_02 = (afl_0)this.cAM.get(string);
        if (afl_02 == null && this.cAP != null) {
            afl_02 = this.cAP.getProperty(string);
        }
        return afl_02;
    }

    public Collection getProperties() {
        return this.cAM.values();
    }

    public void clear() {
        if (this.cAL != null) {
            this.cAL.clear();
            this.cAL = null;
        }
        if (this.cAO != null) {
            this.cAO.clear();
        }
        for (afl_0 afl_02 : this.cAM.values()) {
            azs_0.aLV().e(afl_02);
        }
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                ((aji_1)this.uA.get(j)).clear();
            }
            this.uA.clear();
            this.uA = null;
        }
        this.cAM.clear();
        this.rE = null;
        this.cAP = null;
    }

    public void a(String string, awz_0 awz_02) {
        if (this.cAO == null) {
            this.cAO = new HashMap();
        }
        this.cAO.put(string, awz_02);
    }

    public awz_0 ir(String string) {
        awz_0 awz_02 = null;
        if (this.cAO != null) {
            awz_02 = (awz_0)this.cAO.get(string);
        }
        if (awz_02 == null && this.cAP != null) {
            awz_02 = this.cAP.ir(string);
        }
        return awz_02;
    }

    public boolean is(String string) {
        boolean bl2 = false;
        if (this.cAO != null) {
            bl2 = this.cAO.containsKey(string);
        }
        if (!bl2 && this.cAP != null) {
            bl2 = this.cAP.is(string);
        }
        return bl2;
    }

    public void it(String string) {
        awz_0 awz_02 = null;
        if (this.cAO != null) {
            awz_02 = (awz_0)this.cAO.remove(string);
        }
        if (this.cAP != null && awz_02 == null) {
            this.cAP.it(string);
        }
    }
}

