/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class Iy
implements gh_1,
RE,
eG {
    private static final HashMap bhz = new HashMap();
    protected static final Logger a = Logger.getLogger(Iy.class);
    public final aga_1 bhA;
    public RE Jo;
    public gh_1 Jp;
    public eG Jq;
    public ym_0 bhB;
    private boolean bhC = true;
    private final Object Js = new Object();
    private final ArrayList Jt = new ArrayList();

    public static void a(String string, Iy iy) {
        bhz.put(string, iy);
    }

    public static Iy eH(String string) {
        return (Iy)bhz.get(string);
    }

    public static int Uy() {
        return bhz.size();
    }

    public static Iterable Uz() {
        return bhz.keySet();
    }

    public Iy(String string) {
        this.bhA = new aga_1(this);
        Iy.a(string, this);
    }

    public boolean isRunning() {
        if (this.bhA != null) {
            return this.bhA.isRunning();
        }
        return false;
    }

    public void UA() {
        if (this.bhA != null) {
            this.bhA.UA();
        }
    }

    public void bH(boolean bl2) {
        this.bhC = bl2;
    }

    public void n(String string, int n2) {
        if (this.Jq == null) {
            throw new Exception("Le d\u00e9codeur de messages n'a pas \u00e9t\u00e9 sp\u00e9cifi\u00e9");
        }
        if (this.bhB == null) {
            throw new Exception("Le pool de ConnectionUser+MessageUser n'a pas \u00e9t\u00e9 sp\u00e9cifi\u00e9");
        }
        this.bhA.u(string, n2);
    }

    public void start() {
        this.bhA.ai(true);
        this.bhA.start();
    }

    public void a(RE rE) {
        this.Jo = rE;
    }

    public void a(gh_1 gh_12) {
        this.Jp = gh_12;
    }

    public void a(eG eG2) {
        this.Jq = eG2;
    }

    public void a(Cs cs) {
        this.bhB = new alt_2(cs);
    }

    public boolean a(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.a(ka_22);
        }
        return false;
    }

    public boolean b(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.b(ka_22);
        }
        return false;
    }

    public boolean c(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.c(ka_22);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean a(ka_2 ka_22, ait_0 ait_02) {
        try {
            cf_2 cf_22 = (cf_2)this.bhB.adr();
            if (cf_22 == null) {
                return false;
            }
            cf_22.a(this.bhB);
            cf_22.a(ait_02);
            ait_02.a(cf_22);
            cf_22.Ko();
            Object object = this.Js;
            synchronized (object) {
                this.Jt.add(ait_02);
            }
            if (this.Jo != null) {
                return this.Jo.a(ka_22, ait_02);
            }
        }
        catch (Throwable throwable) {
            a.error((Object)"ServerInstance exception : ", throwable);
            return false;
        }
        return true;
    }

    public synchronized boolean b(ka_2 ka_22, ait_0 ait_02) {
        ByteBuffer byteBuffer = ait_02.ayi();
        if (byteBuffer != null) {
            pr_0 pr_02;
            ArrayList<pr_0> arrayList = new ArrayList<pr_0>();
            do {
                if ((pr_02 = this.Jq.g(byteBuffer)) == null) continue;
                if (pr_02.uy() == null) {
                    pr_02.a((alx_0)((Object)ait_02.axY()));
                }
                arrayList.add(pr_02);
            } while (pr_02 != null);
            byteBuffer.compact();
            if (!arrayList.isEmpty()) {
                acu_1.ara().t(arrayList);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean c(ka_2 ka_22, ait_0 ait_02) {
        boolean bl2 = true;
        try {
            if (this.Jo != null) {
                bl2 = this.Jo.c(ka_22, ait_02);
            }
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
        }
        return bl2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean d(ka_2 ka_22, ait_0 ait_02) {
        boolean bl2 = true;
        try {
            Object object = this.Js;
            synchronized (object) {
                this.Jt.remove(ait_02);
            }
            if (this.Jo != null) {
                bl2 = this.Jo.d(ka_22, ait_02);
            }
            if (this.bhC && (object = (cf_2)ait_02.axY()) != null) {
                ((cf_2)object).release();
            }
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return false;
        }
        return bl2;
    }

    public synchronized boolean e(ka_2 ka_22, ait_0 ait_02) {
        cf_2 cf_22 = (cf_2)ait_02.axY();
        if (cf_22 != null) {
            cf_22.Kr();
        }
        if (this.Jo != null) {
            return this.Jo.e(ka_22, ait_02);
        }
        return true;
    }

    public synchronized void f(ka_2 ka_22, ait_0 ait_02) {
        if (this.Jo != null) {
            this.Jo.f(ka_22, ait_02);
        }
    }

    public pr_0 g(ByteBuffer byteBuffer) {
        if (this.Jq != null) {
            return this.Jq.g(byteBuffer);
        }
        return null;
    }

    public ka_2 rd() {
        return this.bhA;
    }

    public void a(akm_2 akm_22) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int re() {
        Object object = this.Js;
        synchronized (object) {
            return this.Jt.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ArrayList UB() {
        Object object = this.Js;
        synchronized (object) {
            return this.Jt;
        }
    }
}

