/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/*
 * Renamed from aHU
 */
public class ahu_0
extends bZ
implements cs_2,
mt_2 {
    public static final String dOf = "root";
    final arN dJI;
    private int size;
    private int dOg = 0;
    private final List dOh = new ArrayList();
    private Hashtable dOi;
    private cr_0 dOj;
    private final ayz_0 dOk = new ayz_0();
    boolean bgs = false;

    public ahu_0() {
        this.dOi = new Hashtable();
        this.dOj = new cr_0(this);
        this.dJI = new arN(dOf, null, this);
        this.dJI.b(rl_2.agc);
        this.dOi.put(dOf, this.dJI);
        this.d("EVALUATOR_MAP", new HashMap());
        this.size = 1;
    }

    private void aUj() {
        this.dOj = new cr_0(this);
        for (arN arN2 : this.dOi.values()) {
            arN2.aEV();
        }
    }

    public void c(String string, String string2) {
        super.c(string, string2);
        this.aUj();
    }

    public void setName(String string) {
        super.setName(string);
        this.aUj();
    }

    public final arN J(Class clazz) {
        return this.lw(clazz.getName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final arN lw(String string) {
        int n2;
        if (string == null) {
            throw new IllegalArgumentException("name argument cannot be null");
        }
        if (dOf.equalsIgnoreCase(string)) {
            return this.dJI;
        }
        int n3 = 0;
        arN arN2 = this.dJI;
        arN arN3 = (arN)this.dOi.get(string);
        if (arN3 != null) {
            return arN3;
        }
        do {
            String string2 = (n2 = string.indexOf(46, n3)) == -1 ? string : string.substring(0, n2);
            n3 = n2 + 1;
            arN arN4 = arN2;
            synchronized (arN4) {
                arN3 = arN2.jp(string2);
                if (arN3 == null) {
                    arN3 = arN2.jr(string2);
                    this.dOi.put(string2, arN3);
                    this.aUk();
                }
            }
            arN2 = arN3;
        } while (n2 != -1);
        return arN3;
    }

    private void aUk() {
        ++this.size;
    }

    int size() {
        return this.size;
    }

    public arN lx(String string) {
        return (arN)this.dOi.get(string);
    }

    final void a(arN arN2) {
        if (this.dOg++ == 0) {
            this.ea().c(new apQ("No appenders present in context [" + this.getName() + "] for logger [" + arN2.getName() + "].", arN2));
        }
    }

    public List acg() {
        Collection collection = this.dOi.values();
        ArrayList arrayList = new ArrayList(collection);
        Collections.sort(arrayList, new zm_2());
        return arrayList;
    }

    public cr_0 aUl() {
        return this.dOj;
    }

    public void reset() {
        this.dJI.aET();
        this.aUo();
        this.aUs();
        this.aUp();
        this.aUm();
    }

    private void aUm() {
        Ju ju = this.ea();
        for (pm_1 pm_12 : ju.VT()) {
            ju.b(pm_12);
        }
    }

    public ayz_0 aUn() {
        return this.dOk;
    }

    public void a(tm_0 tm_02) {
        this.dOk.add(tm_02);
    }

    public void aUo() {
        this.dOk.clear();
    }

    final vq_0 c(axe axe2, arN arN2, rl_2 rl_22, String string, Object[] objectArray, Throwable throwable) {
        if (this.dOk.size() == 0) {
            return vq_0.bTo;
        }
        return this.dOk.b(axe2, arN2, rl_22, string, objectArray, throwable);
    }

    final vq_0 a(axe axe2, arN arN2, rl_2 rl_22, String string, Object object, Throwable throwable) {
        if (this.dOk.size() == 0) {
            return vq_0.bTo;
        }
        return this.dOk.b(axe2, arN2, rl_22, string, new Object[]{object}, throwable);
    }

    final vq_0 a(axe axe2, arN arN2, rl_2 rl_22, String string, Object object, Object object2, Throwable throwable) {
        if (this.dOk.size() == 0) {
            return vq_0.bTo;
        }
        return this.dOk.b(axe2, arN2, rl_22, string, new Object[]{object, object2}, throwable);
    }

    public void a(af_2 af_22) {
        this.dOh.add(af_22);
    }

    public void b(af_2 af_22) {
        this.dOh.remove(af_22);
    }

    private void aUp() {
        ArrayList<af_2> arrayList = new ArrayList<af_2>();
        for (af_2 af_22 : this.dOh) {
            if (!af_22.bh()) continue;
            arrayList.add(af_22);
        }
        this.dOh.retainAll(arrayList);
    }

    private void aUq() {
        this.dOh.clear();
    }

    public List aUr() {
        return new ArrayList(this.dOh);
    }

    private void aUs() {
        for (af_2 af_22 : this.dOh) {
            af_22.b(this);
        }
    }

    private void aUt() {
        for (af_2 af_22 : this.dOh) {
            af_22.a(this);
        }
    }

    private void aUu() {
        for (af_2 af_22 : this.dOh) {
            af_22.c(this);
        }
    }

    public boolean isStarted() {
        return this.bgs;
    }

    public void start() {
        this.bgs = true;
        this.aUt();
    }

    public void stop() {
        this.reset();
        this.aUu();
        this.aUq();
        this.bgs = false;
    }

    public String toString() {
        return this.getClass().getName() + "[" + this.getName() + "]";
    }
}

