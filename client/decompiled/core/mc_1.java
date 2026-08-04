/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/*
 * Renamed from mc
 */
public class mc_1
implements aho_0 {
    public static final String IA = "friends.list";
    public static final String IB = "ignore.list";
    private String IC;
    private static final String[] ce = new String[]{"friends.list", "ignore.list"};
    private static mc_1 IE = new mc_1();
    private HashMap IF = new HashMap();
    private zx_0 IG = new zx_0();

    public static mc_1 qM() {
        return IE;
    }

    public mc_1() {
        azs_0.aLV().g("userManager", this);
    }

    public void a(short s, axa_0 axa_02) {
        if (!this.IF.containsKey(axa_02.getName().toLowerCase())) {
            axa_02.cg(s);
            this.IF.put(axa_02.getName().toLowerCase(), axa_02);
        } else {
            axa_0 axa_03 = (axa_0)this.IF.get(axa_02.getName().toLowerCase());
            axa_03.cg(s);
        }
        azs_0.aLV().a((aho_0)this, ce);
    }

    public void a(short s, Iterable iterable) {
        for (axa_0 axa_02 : iterable) {
            this.a(s, axa_02);
        }
        azs_0.aLV().a((aho_0)this, ce);
    }

    public boolean a(short s, String string) {
        if (this.IF.containsKey(string.toLowerCase())) {
            axa_0 axa_02 = (axa_0)this.IF.get(string.toLowerCase());
            axa_02.ch(s);
            if (s == axa_0.diQ) {
                axa_02.ai(false);
            }
            if (axa_02.aJL()) {
                this.IF.remove(string.toLowerCase());
            }
            azs_0.aLV().a((aho_0)this, ce);
            return true;
        }
        return false;
    }

    public HashMap qN() {
        HashMap<String, axa_0> hashMap = new HashMap<String, axa_0>();
        for (axa_0 axa_02 : this.IF.values()) {
            if (!axa_02.ci(axa_0.diQ)) continue;
            hashMap.put(axa_02.getName().toLowerCase(), axa_02);
        }
        return hashMap;
    }

    public HashMap qO() {
        HashMap<String, axa_0> hashMap = new HashMap<String, axa_0>();
        for (axa_0 axa_02 : this.IF.values()) {
            if (!axa_02.ci(axa_0.diR)) continue;
            hashMap.put(axa_02.getName().toLowerCase(), axa_02);
        }
        return hashMap;
    }

    public zx_0 qP() {
        return this.IG;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(IA)) {
            ArrayList<axa_0> arrayList = new ArrayList<axa_0>();
            for (axa_0 axa_02 : this.IF.values()) {
                if (!axa_02.ci(axa_0.diQ)) continue;
                arrayList.add(axa_02);
            }
            Collections.sort(arrayList);
            return arrayList.toArray();
        }
        if (string.equals(IB)) {
            ArrayList<axa_0> arrayList = new ArrayList<axa_0>();
            for (axa_0 axa_03 : this.IF.values()) {
                if (!axa_03.ci(axa_0.diR)) continue;
                arrayList.add(axa_03);
            }
            Collections.sort(arrayList);
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

    public String getUserName() {
        return this.IC;
    }

    public void setUserName(String string) {
        this.IC = string;
    }

    public void qQ() {
        this.IF.clear();
    }
}

