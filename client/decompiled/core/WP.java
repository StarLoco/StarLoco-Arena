/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class WP {
    private static final boolean DEBUG = false;
    protected static Logger a = Logger.getLogger(WP.class);
    protected aix_1[] bVw;
    protected ano_2 bVx = new ano_2();
    protected ym_0[] bVy;
    protected ym_0[] bVz;
    protected final Object bVA = new Object();
    protected final Object bVB = new Object();
    protected final Object bVC = new Object();
    protected HashMap bVD = new HashMap();
    protected ArrayList bVE = new ArrayList();
    protected int eT;
    protected int bVF = 5;
    protected final List aoz = new ArrayList();
    private final List bVG = new ArrayList();
    private final List bVH = new ArrayList();
    protected boolean bVI = true;

    public WP(aix_1[] aix_1Array, boolean bl2) {
        if (aix_1Array == null) {
            return;
        }
        this.bVw = aix_1Array;
        this.bVy = new ym_0[this.bVw.length];
        this.bVz = new ym_0[this.bVw.length];
        for (int j = 0; j < aix_1Array.length; ++j) {
            this.bVy[j] = new ym_0(this.bVw[j].ayp());
            this.bVz[j] = new ym_0(this.bVw[j].ayq());
            this.bVx.bz(this.bVw[j].ao(), j);
        }
        this.eT = 0;
        if (bl2) {
            ip_2.Un().a(new J(this), 1000L, -1);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public aaf_2 iL(int n2) {
        int n3 = this.bVx.get(n2);
        AW aW = this.iM(n3);
        aaf_2 aaf_22 = this.iN(n3);
        if (aW == null) {
            if (aaf_22 != null) {
                this.a(n3, aaf_22);
                aaf_22 = null;
            }
        } else if (aaf_22 == null) {
            this.a(n3, aW);
        } else {
            aaf_22.cQ(true);
            aaf_22.c(aW);
            aaf_22.jv(this.eT);
            aaf_22.h(n2);
            Object object = this.bVB;
            synchronized (object) {
                this.bVD.put(aW, aaf_22);
            }
        }
        return aaf_22;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a(AW aW) {
        Object object = this.bVB;
        synchronized (object) {
            return this.bVD.containsKey(aW);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean b(AW aW) {
        aaf_2 aaf_22 = (aaf_2)this.bVD.get(aW);
        if (aaf_22 != null && aaf_22.api() == aW) {
            Object object = this.bVC;
            synchronized (object) {
                if (this.bVE.contains(aaf_22)) {
                    this.bVE.remove(aaf_22);
                }
            }
            object = aaf_22.apl();
            synchronized (object) {
                aaf_22.jv(this.eT);
                if (aaf_22.apk()) {
                    try {
                        aW.a(aaf_22);
                    }
                    catch (Exception exception) {
                        a.error((Object)"Exception", (Throwable)exception);
                    }
                    aaf_22.cQ(false);
                    this.d(aaf_22);
                }
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(aaf_2 aaf_22) {
        Object object = this.bVC;
        synchronized (object) {
            if (this.bVI) {
                this.bVE.add(aaf_22);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ajt() {
        Object object = this.bVC;
        synchronized (object) {
            Object object2 = this.bVB;
            synchronized (object2) {
                this.bVE.clear();
                this.bVE.addAll(this.bVD.values());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private AW iM(int n2) {
        if (this.bVx.dy(n2)) {
            Object object = this.bVA;
            synchronized (object) {
                try {
                    return (AW)this.bVy[n2].adr();
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(int n2, AW aW) {
        if (this.bVx.dy(n2)) {
            Object object = this.bVA;
            synchronized (object) {
                try {
                    this.bVy[n2].af(aW);
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private aaf_2 iN(int n2) {
        if (this.bVx.dy(n2)) {
            Object object = this.bVA;
            synchronized (object) {
                try {
                    return (aaf_2)this.bVz[n2].adr();
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(int n2, aaf_2 aaf_22) {
        if (this.bVx.dy(n2)) {
            Object object = this.bVA;
            synchronized (object) {
                try {
                    this.bVz[n2].af(aaf_22);
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void update() {
        Object object;
        ++this.eT;
        if (!this.bVG.isEmpty()) {
            for (adr_2 object2 : this.bVG) {
                this.aoz.remove(object2);
            }
            this.bVG.clear();
        }
        if (!this.bVH.isEmpty()) {
            for (adr_2 adr_22 : this.bVH) {
                if (this.aoz.contains(adr_22)) continue;
                this.aoz.add(adr_22);
            }
            this.bVH.clear();
        }
        Object[] objectArray = null;
        Object object2 = this.bVC;
        synchronized (object2) {
            objectArray = this.bVE.toArray();
            this.bVE.clear();
        }
        if (objectArray != null) {
            for (Object object3 : objectArray) {
                Object object4 = (aaf_2)object3;
                object = ((aaf_2)object4).apl();
                synchronized (object) {
                    ((aaf_2)object4).jv(-2 * this.bVF);
                    ((aaf_2)object4).cR(true);
                }
            }
        }
        Object object5 = this.bVB;
        synchronized (object5) {
            for (Object object4 : this.bVD.values().toArray()) {
                object = (aaf_2)object4;
                Object object6 = ((aaf_2)object).apl();
                synchronized (object6) {
                    int n2 = this.eT - ((aaf_2)object).apj();
                    if (n2 >= this.bVF && ((aaf_2)object).abG() && this.bVI) {
                        if (!((aaf_2)object).apk()) {
                            this.e((aaf_2)object);
                            ((aaf_2)object).api().b((aaf_2)object);
                            ((aaf_2)object).cQ(true);
                        }
                        if (((aaf_2)object).apm()) {
                            try {
                                int n3 = this.bVx.get(((aaf_2)object).ao());
                                Object object7 = this.bVA;
                                synchronized (object7) {
                                    Object object8 = this.bVB;
                                    synchronized (object8) {
                                        AW aW = ((aaf_2)object).api();
                                        this.bVy[n3].af(aW);
                                        this.bVz[n3].af(object);
                                        this.bVD.remove(aW);
                                    }
                                }
                            }
                            catch (Exception exception) {
                                a.error((Object)"Exception", (Throwable)exception);
                            }
                        }
                    }
                }
            }
        }
    }

    public int aju() {
        return this.bVF;
    }

    public void iO(int n2) {
        this.bVF = n2;
    }

    public String ajv() {
        long l2 = 0L;
        for (aix_1[] aix_1Array : this.bVD.keySet()) {
            l2 += aix_1Array.HV();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getClass().getName()).append(" stats\n");
        for (aix_1 aix_12 : this.bVw) {
            int n2 = aix_12.ao();
            int n3 = this.bVx.get(n2);
            stringBuffer.append("\tNb objects out  = ").append(this.bVy[n3].jy()).append("\n");
            stringBuffer.append("\tNb objects in   = ").append(this.bVy[n3].jx()).append("\n");
            stringBuffer.append("\tNb contexts out = ").append(this.bVz[n3].jy()).append("\n");
            stringBuffer.append("\tNb contexts in  = ").append(this.bVz[n3].jx()).append("\n");
            stringBuffer.append("\tMemory usage    = ").append((float)l2 / 1024000.0f).append(" MByte(s)");
        }
        return stringBuffer.toString();
    }

    public int ajw() {
        return this.eT;
    }

    public void a(adr_2 adr_22) {
        this.bVH.add(adr_22);
    }

    public void b(adr_2 adr_22) {
        this.bVG.add(adr_22);
    }

    public void ajx() {
        this.bVG.addAll(this.aoz);
    }

    protected void d(aaf_2 aaf_22) {
        for (adr_2 adr_22 : this.aoz) {
            adr_22.g(aaf_22);
        }
    }

    protected void e(aaf_2 aaf_22) {
        for (adr_2 adr_22 : this.aoz) {
            adr_22.f(aaf_22);
        }
    }

    public boolean ajy() {
        return this.bVI;
    }

    public void cy(boolean bl2) {
        this.bVI = bl2;
    }
}

