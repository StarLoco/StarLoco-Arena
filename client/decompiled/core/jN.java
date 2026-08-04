/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.LinkedList;
import java.util.Queue;
import org.apache.log4j.Logger;

public final class jN
implements Runnable {
    protected static final Logger a = Logger.getLogger(jN.class);
    private static final jN CH = new jN();
    private final Queue CI = new LinkedList();
    private boolean CJ = false;
    private String CK;
    private static final int CL = 1000;
    private static final int CM = 30000;
    public static boolean CN = false;

    public static jN nY() {
        return CH;
    }

    private jN() {
    }

    public void a(mu mu2) {
        if (!this.CI.contains(mu2) && mu2.getPropertyName() != null && !mu2.getPropertyName().equals("")) {
            this.CI.add(mu2);
        }
    }

    public void b(mu mu2) {
        this.CI.remove(mu2);
    }

    public int nZ() {
        return this.CI.size();
    }

    public Iterable oa() {
        return this.CI;
    }

    public mu aH(String string) {
        for (mu mu2 : this.CI) {
            if (!mu2.getPropertyName().equals(string)) continue;
            return mu2;
        }
        return null;
    }

    public void aI(String string) {
        if (!this.CJ) {
            this.CK = string;
            this.CJ = true;
            if (CN) {
                ip_2.Un().a(this, 30000L, -1);
            }
        }
    }

    public void ob() {
        YY.cbS.f(false);
    }

    public void run() {
        try {
            long l2 = System.currentTimeMillis();
            int n2 = Math.min(this.CI.size(), 1000);
            YY.cbS.gQ(this.CK);
            for (int j = 0; j < n2; ++j) {
                mu mu2 = (mu)this.CI.poll();
                this.CI.offer(mu2);
                String string = mu2.getPropertyName();
                String[] stringArray = mu2.rm();
                if (stringArray != null && stringArray.length > 0) {
                    for (String string2 : stringArray) {
                        int[] nArray = mu2.rn();
                        if (nArray == null || nArray.length <= 0) continue;
                        for (int n3 : nArray) {
                            YY.cbS.g(new nm_2(this.CK, string, string2, n3, mu2.k(string2, n3), l2));
                        }
                    }
                    continue;
                }
                int[] nArray = mu2.rn();
                if (nArray == null || nArray.length <= 0) continue;
                for (int n4 : nArray) {
                    YY.cbS.g(new nm_2(this.CK, string, "", n4, mu2.k(null, n4), l2));
                }
            }
            YY.cbS.and();
        }
        catch (Exception exception) {
            a.error((Object)"Exception ", (Throwable)exception);
        }
    }
}

