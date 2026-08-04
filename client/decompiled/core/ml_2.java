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

/*
 * Renamed from ml
 */
public class ml_2
implements gh_1,
RE,
eG {
    private static final HashMap Jm = new HashMap();
    protected static final Logger a = Logger.getLogger(Iy.class);
    public final bA Jn;
    public RE Jo;
    public gh_1 Jp;
    public eG Jq;
    public acl_0 Jr;
    private int gP = 500;
    private int gQ = Integer.MAX_VALUE;
    private final Object Js = new Object();
    private final ArrayList Jt = new ArrayList();
    private final ArrayList Ju = new ArrayList();

    public static void a(String string, ml_2 ml_22) {
        Jm.put(string, ml_22);
    }

    public static ml_2 aW(String string) {
        return (ml_2)Jm.get(string);
    }

    public static int rb() {
        return Jm.size();
    }

    public static Iterable rc() {
        return Jm.keySet();
    }

    public ml_2(String string) {
        this.Jn = new bA(this);
        ml_2.a(string, this);
    }

    public boolean isRunning() {
        return this.Jn != null;
    }

    public void initialize() {
        if (this.Jq == null) {
            throw new Exception("Le d\u00c3\u00a9codeur de messages n'a pas \u00c3\u00a9t\u00c3\u00a9 sp\u00c3\u00a9cifi\u00c3\u00a9");
        }
        if (this.Jr == null) {
            throw new Exception("Le pool de ConnectionUser+MessageUser n'a pas \u00c3\u00a9t\u00c3\u00a9 sp\u00c3\u00a9cifi\u00c3\u00a9");
        }
        this.Jn.dc();
    }

    public void n(byte[] byArray) {
        this.Jn.ai(true);
        this.Jn.z(this.gP);
        this.Jn.A(this.gQ);
        this.Jn.start();
        if (byArray != null) {
            ip_2.Un().a(new ayG(this, byArray), 10000L, -1);
        }
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
        this.Jr = new ym_0(cs);
    }

    public boolean a(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.a(ka_22);
        }
        a.warn((Object)"onConnectionHandlerCreationError non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        return false;
    }

    public boolean b(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.b(ka_22);
        }
        a.warn((Object)"onConnectionHandlerInitializationError non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        return false;
    }

    public boolean c(ka_2 ka_22) {
        if (this.Jo != null) {
            return this.Jo.c(ka_22);
        }
        a.warn((Object)"onConnectionHandlerInLoopError non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean a(ka_2 ka_22, ait_0 ait_02) {
        boolean bl2 = true;
        try {
            cf_2 cf_22 = (cf_2)this.Jr.adr();
            if (cf_22 == null) {
                return false;
            }
            cf_22.a(this.Jr);
            cf_22.a(ait_02);
            ait_02.a(cf_22);
            cf_22.Ko();
            Object object = this.Js;
            synchronized (object) {
                this.Jt.add(ait_02);
            }
            if (this.Jo != null) {
                bl2 = this.Jo.a(ka_22, ait_02);
            } else {
                a.warn((Object)"onNewConnection non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
            }
        }
        catch (Exception exception) {
            ka_22.d(exception);
            return false;
        }
        return bl2;
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
        } else {
            return false;
        }
        return true;
    }

    public synchronized boolean c(ka_2 ka_22, ait_0 ait_02) {
        if (this.Jo != null) {
            return this.Jo.c(ka_22, ait_02);
        }
        a.warn((Object)"onConnectionError non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean d(ka_2 ka_22, ait_0 ait_02) {
        try {
            Object object = this.Js;
            synchronized (object) {
                this.Jt.remove(ait_02);
            }
            object = (cf_2)ait_02.axY();
            if (object != null) {
                ((cf_2)object).Kq();
            }
        }
        catch (Exception exception) {
            ka_22.d(exception);
            return false;
        }
        if (this.Jo != null) {
            return this.Jo.d(ka_22, ait_02);
        }
        a.warn((Object)"onConnectionClose non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        return true;
    }

    public synchronized boolean e(ka_2 ka_22, ait_0 ait_02) {
        cf_2 cf_22;
        if (ait_02 != null && (cf_22 = (cf_2)ait_02.axY()) != null) {
            cf_22.Ks();
            if (this.Jo != null) {
                return this.Jo.e(ka_22, ait_02);
            }
            a.warn((Object)"onConnectionRecovered non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        }
        return true;
    }

    public synchronized void f(ka_2 ka_22, ait_0 ait_02) {
        cf_2 cf_22 = (cf_2)ait_02.axY();
        if (cf_22 != null) {
            cf_22.Kr();
        }
        if (this.Jo != null) {
            this.Jo.f(ka_22, ait_02);
        } else {
            a.warn((Object)"onReconnectionScheduled non forward\u00c3\u00a9 : pas de handler d\u00c3\u00a9fini");
        }
    }

    public pr_0 g(ByteBuffer byteBuffer) {
        if (this.Jq != null) {
            return this.Jq.g(byteBuffer);
        }
        return null;
    }

    public ka_2 rd() {
        return this.Jn;
    }

    public ait_0 h(String string, int n2) {
        try {
            if (this.Jn != null) {
                this.Jn.A(this.gQ);
                this.Jn.z(this.gP);
                return this.Jn.c(string, n2);
            }
        }
        catch (Exception exception) {
            a.error((Object)"openConnection exception");
        }
        return null;
    }

    public ait_0 a(String string, int n2, int n3, int n4) {
        try {
            if (this.Jn != null) {
                this.Jn.A(this.gQ);
                this.Jn.z(this.gP);
                mu_0 mu_02 = this.Jn.c(string, n2);
                if (mu_02.isConnected()) {
                    return mu_02;
                }
                for (int j = 0; j < n4; ++j) {
                    if (mu_02.isConnected()) {
                        return mu_02;
                    }
                    a.info((Object)("Blocking connection pending ... (" + string + ":" + n2 + ")"));
                    Thread.sleep(n3);
                }
                a.error((Object)("Blocking connection timedout (" + string + ":" + n2 + ")"));
                return null;
            }
        }
        catch (Exception exception) {
            a.error((Object)("blockingOpenConnection exception (" + string + ":" + n2 + ")"));
        }
        return null;
    }

    public ait_0 i(String string, int n2) {
        ait_0 ait_02 = this.h(string, n2);
        if (ait_02 != null) {
            while (!ait_02.isConnected()) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                    a.error((Object)"Interruption lors de l'\u00c3\u00a9tablissement d'une connexion");
                }
                if (!ait_02.ayg() && !ait_02.ayh()) continue;
                return null;
            }
        }
        return ait_02;
    }

    public void a(akm_2 akm_22) {
    }

    public boolean a(pr_0 pr_02) {
        return false;
    }

    public long getId() {
        if (this.Jn != null) {
            return this.Jn.getId();
        }
        a.warn((Object)"getId() retourne -1 : pas de handler d\u00c3\u00a9fini");
        return -1L;
    }

    public void c(long l2) {
    }

    public void z(int n2) {
        this.gP = n2;
    }

    public void A(int n2) {
        this.gQ = n2;
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
    public Iterable rf() {
        Object object = this.Js;
        synchronized (object) {
            return this.Jt;
        }
    }

    static /* synthetic */ Object a(ml_2 ml_22) {
        return ml_22.Js;
    }

    static /* synthetic */ ArrayList b(ml_2 ml_22) {
        return ml_22.Jt;
    }
}

