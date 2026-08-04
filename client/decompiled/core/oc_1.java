/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/*
 * Renamed from oc
 */
public abstract class oc_1
implements axx_0 {
    protected static final Logger a = Logger.getLogger(oc_1.class);
    protected final DataSource Qv;
    protected final ArrayList Qw;
    protected int Qx;
    protected String Qy;
    protected final int Qz;
    protected String QA;
    protected String QB;
    protected int QC;
    protected String QD;
    protected String QE;
    private HashMap QF = new HashMap();

    protected oc_1(String string, String string2, String string3, String string4, int n2, int n3) {
        this.QA = string;
        this.QB = string2;
        this.QC = n3;
        this.QD = string3;
        this.QE = string4;
        this.Qw = new ArrayList();
        this.Qz = n2;
        this.Qv = this.a(string, string2, string3, string4, n3);
    }

    protected abstract DataSource a(String var1, String var2, String var3, String var4, int var5);

    public DataSource sV() {
        return this.Qv;
    }

    public void initialize() {
        for (int j = 0; j < this.Qz; ++j) {
            jn_0 jn_02 = new jn_0(this.Qv);
            jn_02.bp(this.Qy + "_" + j);
            jn_02.a(this.QF);
            this.Qw.add(jn_02);
            jn_02.start();
        }
    }

    public void sW() {
        for (jn_0 jn_02 : this.Qw) {
            jn_02.f(false);
            try {
                jn_02.join();
            }
            catch (InterruptedException interruptedException) {
                a.error((Object)"Thread interrupted : ", (Throwable)interruptedException);
            }
        }
    }

    public boolean sX() {
        for (jn_0 jn_02 : this.Qw) {
            if (!jn_02.isRunning() && jn_02.Wh() <= 0) continue;
            return false;
        }
        return true;
    }

    public boolean a(arr arr2) {
        jn_0 jn_02 = null;
        int n2 = Integer.MAX_VALUE;
        int n3 = arr2.kn();
        if (n3 < 0 || this.Qw.size() <= n3) {
            for (jn_0 jn_03 : this.Qw) {
                if (jn_03.Wh() > n2) continue;
                jn_02 = jn_03;
                n2 = jn_03.Wh();
            }
        } else {
            jn_02 = (jn_0)this.Qw.get(n3);
        }
        if (jn_02 != null) {
            jn_02.c(arr2);
            return true;
        }
        a.error((Object)"Pas de cannal disponible pour poster la requ\u00eate");
        return false;
    }

    public boolean a(arr arr2, int n2) {
        try {
            jn_0 jn_02 = (jn_0)this.Qw.get(n2);
            jn_02.c(arr2);
            return true;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return false;
        }
    }

    public pr_0 b(arr arr2) {
        jn_0 jn_02 = null;
        int n2 = Integer.MAX_VALUE;
        int n3 = arr2.kn();
        if (n3 < 0 || this.Qw.size() <= n3) {
            for (jn_0 jn_03 : this.Qw) {
                if (jn_03.Wh() > n2) continue;
                jn_02 = jn_03;
                n2 = jn_03.Wh();
            }
        } else {
            jn_02 = (jn_0)this.Qw.get(n3);
        }
        if (jn_02 != null) {
            return jn_02.b(arr2);
        }
        a.error((Object)"Pas de cannal disponible pour poster la requ\u00eate");
        return null;
    }

    public pr_0 b(arr arr2, int n2) {
        try {
            jn_0 jn_02 = (jn_0)this.Qw.get(n2);
            return jn_02.b(arr2);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
            return null;
        }
    }

    public int sY() {
        return this.Qw.size();
    }

    public String sZ() {
        return this.Qy;
    }

    public void bp(String string) {
        this.Qy = string;
    }

    public int ta() {
        return this.Qx;
    }

    public void cv(int n2) {
        this.Qx = n2;
    }

    public String tb() {
        return this.QA;
    }

    public String tc() {
        return this.QB;
    }

    public String td() {
        return this.QD;
    }

    public String te() {
        return this.QE;
    }

    public HashMap tf() {
        return this.QF;
    }

    public void a(HashMap hashMap) {
        this.QF = hashMap;
    }

    public int[] tg() {
        int n2 = this.Qw.size();
        int[] nArray = new int[n2];
        for (int j = 0; j < n2; ++j) {
            nArray[j] = ((jn_0)this.Qw.get(j)).Wh();
            for (int n3 : nArray) {
                a.trace((Object)(n3 + " - "));
            }
            a.trace((Object)"");
        }
        return nArray;
    }

    public int cw(int n2) {
        if (n2 >= 0 && n2 < this.Qw.size()) {
            return ((jn_0)this.Qw.get(n2)).Wh();
        }
        return -1;
    }

    public jn_0 cx(int n2) {
        if (n2 >= 0 && n2 < this.Qw.size()) {
            return (jn_0)this.Qw.get(n2);
        }
        return null;
    }
}

