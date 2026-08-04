/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from azs
 */
public class azs_0
implements zw {
    private static Logger a = Logger.getLogger(azs_0.class);
    private static azs_0 dnz = new azs_0();
    private final HashMap dnA = new HashMap();
    private final List dnB = Collections.synchronizedList(new ArrayList());
    private final ArrayList dnC = new ArrayList();
    private final ArrayList dnD = new ArrayList();
    private final ArrayList G = new ArrayList();

    private azs_0() {
    }

    public static final azs_0 aLV() {
        return dnz;
    }

    public void a(ov_0 ov_02) {
        if (ov_02 != null) {
            this.G.add(ov_02);
        }
    }

    public void b(ov_0 ov_02) {
        if (ov_02 != null) {
            this.G.remove(ov_02);
        }
    }

    public void b(afl_0 afl_02) {
        if (!afl_02.isLocal()) {
            this.dnA.put(afl_02.getName(), afl_02);
        }
        this.dnB.add(afl_02);
        this.a(avi.ddI, afl_02);
    }

    public Iterable oa() {
        return this.dnB;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean aLW() {
        boolean bl2;
        boolean bl3 = bl2 = !this.dnD.isEmpty();
        if (bl2) {
            int n2 = this.dnD.size();
            ArrayList arrayList = this.dnD;
            synchronized (arrayList) {
                this.dnC.addAll(this.dnD);
                this.dnD.clear();
            }
            for (int j = 0; j < n2; ++j) {
                ((afl_0)this.dnC.get(j)).avq();
            }
            this.dnC.clear();
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void f(afl_0 afl_02) {
        ArrayList arrayList = this.dnD;
        synchronized (arrayList) {
            if (afl_02 != null && !this.dnD.contains(afl_02)) {
                this.dnD.add(afl_02);
            }
        }
    }

    public void kb(String string) {
        this.e((afl_0)this.dnA.get(string));
    }

    public void e(afl_0 afl_02) {
        if (afl_02 == null) {
            return;
        }
        if (!afl_02.isLocal()) {
            this.dnA.remove(afl_02.getName());
        }
        this.dnB.remove(afl_02);
        this.a(avi.ddJ, afl_02);
    }

    public void g(String string, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 == null) {
            afl_02 = new afl_0(string, null);
            this.b(afl_02);
        }
        afl_02.setValue(object);
    }

    public void a(String string, Object object, String string2) {
        aji_1 aji_12 = add_1.aOG().azj().lh(string2);
        if (aji_12 != null) {
            this.a(string, object, aji_12);
        }
    }

    public void a(String string, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.g(string, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 == null) {
                afl_02 = new afl_0(string, aji_12);
                this.b(afl_02);
                aji_12.b(afl_02);
            }
            afl_02.setValue(object);
        }
    }

    public void s(String string, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 == null) {
            this.g(string, object);
        } else {
            afl_02.ax(object);
        }
    }

    public void b(String string, Object object, String string2) {
        aji_1 aji_12 = add_1.aOG().azj().lh(string2);
        this.b(string, object, aji_12);
    }

    public void b(String string, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.s(string, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 == null) {
                this.a(string, object, aji_12);
            } else {
                afl_02.ax(object);
            }
        }
    }

    public void t(String string, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 == null) {
            this.g(string, object);
        } else {
            afl_02.ay(object);
        }
    }

    public void c(String string, Object object, String string2) {
        aji_1 aji_12 = add_1.aOG().azj().lh(string2);
        this.c(string, object, aji_12);
    }

    public void c(String string, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.t(string, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 == null) {
                this.a(string, object, aji_12);
            } else {
                afl_02.ay(object);
            }
        }
    }

    public void a(String string, String string2, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            afl_02.a(string2, object);
        } else {
            a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
        }
    }

    public void a(String string, String string2, Object object, String string3) {
        this.a(string, string2, object, add_1.aOG().azj().lh(string3));
    }

    public void a(String string, String string2, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.a(string, string2, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 != null) {
                afl_02.a(string2, object);
            } else {
                a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
            }
        }
    }

    public void b(String string, String string2, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 == null) {
            this.g(string, object);
        } else {
            afl_02.ax(object);
        }
    }

    public void b(String string, String string2, Object object, String string3) {
        this.b(string, string2, object, add_1.aOG().azj().lh(string3));
    }

    public void b(String string, String string2, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.b(string, string2, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 != null) {
                afl_02.c(string2, object);
            } else {
                a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
            }
        }
    }

    public void c(String string, String string2, Object object) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            afl_02.b(string2, object);
        } else {
            a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
        }
    }

    public void c(String string, String string2, Object object, String string3) {
        this.c(string, string2, object, add_1.aOG().azj().lh(string3));
    }

    public void c(String string, String string2, Object object, aji_1 aji_12) {
        if (aji_12 == null) {
            this.c(string, string2, object);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 != null) {
                afl_02.b(string2, object);
            } else {
                a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aho_0 aho_02, String ... stringArray) {
        asz asz2 = new asz();
        List list = this.dnB;
        synchronized (list) {
            int n2 = this.dnB.size();
            for (int j = 0; j < n2; ++j) {
                afl_0 afl_02 = (afl_0)this.dnB.get(j);
                Object object = afl_02.getValue();
                if (object == null || !object.equals(aho_02)) continue;
                for (String string : stringArray) {
                    afl_02.a(string, asz2);
                }
            }
        }
    }

    public void ac(String string, String string2) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            afl_02.a(string2, null);
        } else {
            a.error((Object)("La d\u00e9finition d'une valeur de champ " + string2 + " est impossible sur la propri\u00e9t\u00e9 " + string));
        }
    }

    public void b(String string, String string2, aji_1 aji_12) {
        if (aji_12 == null) {
            this.ac(string, string2);
        } else {
            afl_0 afl_02 = aji_12.getProperty(string);
            if (afl_02 != null) {
                afl_02.a(string2, null);
            } else {
                a.error((Object)("La d\u00e9finition d'une valeur de champ est impossible sur la propri\u00e9t\u00e9 " + string));
            }
        }
    }

    public String jj(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getString();
        }
        return null;
    }

    public String ad(String string, String string2) {
        return this.e(string, add_1.aOG().azj().lh(string2));
    }

    public String e(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getString();
        }
        return null;
    }

    public boolean getBooleanProperty(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getBoolean();
        }
        return false;
    }

    public boolean ae(String string, String string2) {
        return this.f(string, add_1.aOG().azj().lh(string2));
    }

    public boolean f(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getBoolean();
        }
        return false;
    }

    public int jf(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getInt();
        }
        return 0;
    }

    public int af(String string, String string2) {
        return this.g(string, add_1.aOG().azj().lh(string2));
    }

    public int g(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getInt();
        }
        return 0;
    }

    public float jh(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getFloat();
        }
        return 0.0f;
    }

    public float ag(String string, String string2) {
        return this.h(string, add_1.aOG().azj().lh(string2));
    }

    public float h(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getFloat();
        }
        return 0.0f;
    }

    public double ji(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getDouble();
        }
        return 0.0;
    }

    public double ah(String string, String string2) {
        return this.i(string, add_1.aOG().azj().lh(string2));
    }

    public double i(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getDouble();
        }
        return 0.0;
    }

    public long jg(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getLong();
        }
        return 0L;
    }

    public long ai(String string, String string2) {
        return this.j(string, add_1.aOG().azj().lh(string2));
    }

    public long j(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getLong();
        }
        return 0L;
    }

    public Object kc(String string) {
        afl_0 afl_02 = (afl_0)this.dnA.get(string);
        if (afl_02 != null) {
            return afl_02.getValue();
        }
        return null;
    }

    public Object aj(String string, String string2) {
        return this.k(string, add_1.aOG().azj().lh(string2));
    }

    public Object k(String string, aji_1 aji_12) {
        afl_0 afl_02 = aji_12 == null ? (afl_0)this.dnA.get(string) : aji_12.getProperty(string);
        if (afl_02 != null) {
            return afl_02.getValue();
        }
        return null;
    }

    public afl_0 getProperty(String string) {
        return (afl_0)this.dnA.get(string);
    }

    public afl_0 ak(String string, String string2) {
        return this.l(string, add_1.aOG().azj().lh(string2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public afl_0 a(aho_0 aho_02, aji_1 aji_12) {
        List list = this.dnB;
        synchronized (list) {
            for (afl_0 afl_02 : this.dnB) {
                if (afl_02.getValue() != aho_02 || afl_02.getElementMap() != aji_12) continue;
                return afl_02;
            }
        }
        return null;
    }

    public afl_0 l(String string, aji_1 aji_12) {
        if (aji_12 == null) {
            return this.getProperty(string);
        }
        return aji_12.getProperty(string);
    }

    public void a(avi avi2, afl_0 afl_02) {
        if (this.G.size() != 0) {
            aes_1 aes_12 = new aes_1(avi2, afl_02);
            for (int j = this.G.size() - 1; j >= 0; --j) {
                ((ov_0)this.G.get(j)).a(aes_12);
            }
        }
    }
}

