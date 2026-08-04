/*
 * Decompiled with CFR 0.152.
 */
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from aDQ
 */
public abstract class adq_0
implements Ga {
    public static final int dzi = 500;
    protected String dzj;
    protected String dzk;
    protected adq_0 dzl;
    protected final ArrayList dzm = new ArrayList();
    protected final ArrayList uA = new ArrayList();
    protected final HashMap dzn = new HashMap();
    protected final HashSet dzo = new HashSet();
    protected int aGl = 0;
    private int dzp;

    public adq_0(String string, String string2, adq_0 adq_02, boolean bl2) {
        this.dzj = string;
        this.dzk = string2;
        this.dzl = adq_02;
    }

    void a(oy_0 oy_02) {
        this.dzm.add(oy_02);
    }

    void b(adq_0 adq_02) {
        this.uA.add(adq_02);
    }

    String L(Object object) {
        String string = "var";
        if (object != null) {
            string = string + object.getClass().getSimpleName();
        }
        String string2 = string;
        do {
            string = string2 + this.aGl;
            ++this.aGl;
        } while (this.dzn.containsKey(string));
        this.dzn.put(string, object);
        return string;
    }

    public String aPJ() {
        return this.dzj;
    }

    public adq_0 aPK() {
        return this.dzl;
    }

    public Object dn(String string) {
        return this.dzn.get(string);
    }

    public String GQ() {
        String string = null;
        do {
            string = "var" + this.aGl;
            ++this.aGl;
        } while (this.dzn.containsKey(string));
        this.dzn.put(string, null);
        return string;
    }

    public boolean isFull() {
        return this.dzm.size() >= 500;
    }

    public void h(String string, Object object) {
        this.dzn.put(string, object);
    }

    public boolean do(String string) {
        return this.dzo.contains(string);
    }

    public void dp(String string) {
        this.dzo.add(string);
    }

    public ArrayList aPL() {
        return this.dzm;
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public String getMethodName() {
        return this.dzk;
    }

    public void mark() {
        this.dzp = this.dzm.size();
    }

    public void resetMark() {
        this.dzp = -1;
    }

    public void GS() {
        if (this.dzp == -1) {
            return;
        }
        for (int j = this.dzm.size() - 1; j >= this.dzp; --j) {
            this.dzm.remove(j);
        }
    }

    public abstract void a(PrintWriter var1);
}

