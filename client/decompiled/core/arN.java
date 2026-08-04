/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class arN
implements Bk,
acs_0,
Serializable,
od_1 {
    private static final long serialVersionUID = 5454405123156820674L;
    public static final String cQD = arN.class.getName();
    static int cQE = 0;
    private String name;
    private rl_2 bPc;
    private int cQF;
    private arN cQG;
    private List cQH;
    private transient zl_0 cQI;
    private boolean additive = true;
    final transient ahu_0 cFW;
    hK bPh;
    private static final int cQJ = 5;

    arN(String string, arN arN2, ahu_0 ahu_02) {
        this.name = string;
        this.cQG = arN2;
        this.cFW = ahu_02;
        this.aEV();
        ++cQE;
    }

    public final rl_2 aEP() {
        return rl_2.cY(this.cQF);
    }

    int aEQ() {
        return this.cQF;
    }

    public rl_2 agr() {
        return this.bPc;
    }

    public String getName() {
        return this.name;
    }

    private final boolean aER() {
        return this.cQG == null;
    }

    arN jp(String string) {
        if (this.cQH == null) {
            return null;
        }
        int n2 = this.cQH.size();
        for (int j = 0; j < n2; ++j) {
            arN arN2 = (arN)this.cQH.get(j);
            String string2 = arN2.getName();
            if (!string.equals(string2)) continue;
            return arN2;
        }
        return null;
    }

    public synchronized void b(rl_2 rl_22) {
        if (this.bPc == rl_22) {
            return;
        }
        this.bPc = rl_22;
        if (rl_22 == null) {
            if (this.aER()) {
                throw new IllegalArgumentException("The level of the root logger cannot be set to null");
            }
            this.cQF = this.cQG.cQF;
        } else {
            this.cQF = rl_22.agf;
        }
        if (this.cQH != null) {
            int n2 = this.cQH.size();
            for (int j = 0; j < n2; ++j) {
                arN arN2 = (arN)this.cQH.get(j);
                arN2.lW(this.cQF);
            }
        }
    }

    private synchronized void lW(int n2) {
        if (this.bPc == null) {
            this.cQF = n2;
            if (this.cQH != null) {
                int n3 = this.cQH.size();
                for (int j = 0; j < n3; ++j) {
                    arN arN2 = (arN)this.cQH.get(j);
                    arN2.lW(n2);
                }
            }
        }
    }

    public void tO() {
        if (this.cQI != null) {
            this.cQI.tO();
        }
    }

    public boolean bt(String string) {
        if (this.cQI == null) {
            return false;
        }
        return this.cQI.bt(string);
    }

    public synchronized void a(adr_0 adr_02) {
        if (this.cQI == null) {
            this.cQI = new zl_0();
        }
        this.cQI.a(adr_02);
    }

    public boolean b(adr_0 adr_02) {
        if (this.cQI == null) {
            return false;
        }
        return this.cQI.b(adr_02);
    }

    public Iterator tN() {
        if (this.cQI == null) {
            return Collections.EMPTY_LIST.iterator();
        }
        return this.cQI.tN();
    }

    public adr_0 bs(String string) {
        if (this.cQI == null) {
            return null;
        }
        return this.cQI.bs(string);
    }

    public void g(tz_0 tz_02) {
        int n2 = 0;
        arN arN2 = this;
        while (arN2 != null) {
            n2 += arN2.h(tz_02);
            if (!arN2.additive) break;
            arN2 = arN2.cQG;
        }
        if (n2 == 0) {
            this.cFW.a(this);
        }
    }

    private int h(tz_0 tz_02) {
        if (this.cQI != null) {
            return this.cQI.au(tz_02);
        }
        return 0;
    }

    public boolean c(adr_0 adr_02) {
        if (this.cQI == null) {
            return false;
        }
        return this.cQI.c(adr_02);
    }

    arN jq(String string) {
        int n2 = string.indexOf(46);
        if (n2 != -1) {
            throw new IllegalArgumentException("Child name [" + string + " passed as parameter, may not include [" + '.' + "]");
        }
        if (this.cQH == null) {
            this.cQH = new ArrayList();
        }
        arN arN2 = this.aER() ? new arN(string, this, this.cFW) : new arN(this.name + '.' + string, this, this.cFW);
        this.cQH.add(arN2);
        arN2.cQF = this.cQF;
        return arN2;
    }

    private void aES() {
        this.cQF = 10;
        this.bPc = this.aER() ? rl_2.agc : null;
    }

    void aET() {
        this.tO();
        this.aES();
        this.additive = true;
        if (this.cQH == null) {
            return;
        }
        for (arN arN2 : this.cQH) {
            arN2.aET();
        }
    }

    arN jr(String string) {
        int n2 = string.indexOf(46, this.name.length() + 1);
        if (n2 != -1) {
            throw new IllegalArgumentException("For logger [" + this.name + "] child name [" + string + " passed as parameter, may not include '.' after index" + (this.name.length() + 1));
        }
        if (this.cQH == null) {
            this.cQH = new ArrayList(5);
        }
        arN arN2 = new arN(string, this, this.cFW);
        this.cQH.add(arN2);
        arN2.cQF = this.cQF;
        return arN2;
    }

    private final void a(String string, axe axe2, rl_2 rl_22, String string2, Object[] objectArray, Throwable throwable) {
        vq_0 vq_02 = this.cFW.c(axe2, this, rl_22, string2, objectArray, throwable);
        if (vq_02 == vq_0.bTo ? this.cQF > rl_22.agf : vq_02 == vq_0.bTn) {
            return;
        }
        this.b(string, axe2, rl_22, string2, objectArray, throwable);
    }

    private final void a(String string, axe axe2, rl_2 rl_22, String string2, Object object, Throwable throwable) {
        vq_0 vq_02 = this.cFW.a(axe2, this, rl_22, string2, object, throwable);
        if (vq_02 == vq_0.bTo ? this.cQF > rl_22.agf : vq_02 == vq_0.bTn) {
            return;
        }
        this.b(string, axe2, rl_22, string2, new Object[]{object}, throwable);
    }

    private final void a(String string, axe axe2, rl_2 rl_22, String string2, Object object, Object object2, Throwable throwable) {
        vq_0 vq_02 = this.cFW.a(axe2, this, rl_22, string2, object, object2, throwable);
        if (vq_02 == vq_0.bTo ? this.cQF > rl_22.agf : vq_02 == vq_0.bTn) {
            return;
        }
        this.b(string, axe2, rl_22, string2, new Object[]{object, object2}, throwable);
    }

    private void b(String string, axe axe2, rl_2 rl_22, String string2, Object[] objectArray, Throwable throwable) {
        tz_0 tz_02 = new tz_0(string, this, rl_22, string2, throwable, objectArray);
        tz_02.f(axe2);
        this.g(tz_02);
    }

    public void trace(String string) {
        this.a(cQD, null, rl_2.agd, string, null, null);
    }

    public final void i(String string, Object object) {
        this.a(cQD, null, rl_2.agd, string, object, null);
    }

    public void a(String string, Object object, Object object2) {
        this.a(cQD, null, rl_2.agd, string, object, object2, null);
    }

    public void a(String string, Object[] objectArray) {
        this.a(cQD, (axe)null, rl_2.agd, string, objectArray, (Throwable)null);
    }

    public void trace(String string, Throwable throwable) {
        this.a(cQD, null, rl_2.agd, string, null, throwable);
    }

    public final void a(axe axe2, String string) {
        this.a(cQD, axe2, rl_2.agd, string, null, null);
    }

    public void a(axe axe2, String string, Object object) {
        this.a(cQD, axe2, rl_2.agd, string, object, null);
    }

    public void a(axe axe2, String string, Object object, Object object2) {
        this.a(cQD, axe2, rl_2.agd, string, object, object2, null);
    }

    public void a(axe axe2, String string, Object[] objectArray) {
        this.a(cQD, axe2, rl_2.agd, string, objectArray, (Throwable)null);
    }

    public void a(axe axe2, String string, Throwable throwable) {
        this.a(cQD, axe2, rl_2.agd, string, null, throwable);
    }

    public final boolean isDebugEnabled() {
        return this.b((axe)null);
    }

    public final boolean b(axe axe2) {
        vq_0 vq_02 = this.b(axe2, rl_2.agc);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= 10000;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public final void debug(String string) {
        this.a(cQD, null, rl_2.agc, string, null, null);
    }

    public final void j(String string, Object object) {
        this.a(cQD, null, rl_2.agc, string, object, null);
    }

    public final void b(String string, Object object, Object object2) {
        this.a(cQD, null, rl_2.agc, string, object, object2, null);
    }

    public final void b(String string, Object[] objectArray) {
        this.a(cQD, (axe)null, rl_2.agc, string, objectArray, (Throwable)null);
    }

    public void debug(String string, Throwable throwable) {
        this.a(cQD, null, rl_2.agc, string, null, throwable);
    }

    public final void b(axe axe2, String string) {
        this.a(cQD, axe2, rl_2.agc, string, null, null);
    }

    public void b(axe axe2, String string, Object object) {
        this.a(cQD, axe2, rl_2.agc, string, object, null);
    }

    public void b(axe axe2, String string, Object object, Object object2) {
        this.a(cQD, axe2, rl_2.agc, string, object, object2, null);
    }

    public void b(axe axe2, String string, Object[] objectArray) {
        this.a(cQD, axe2, rl_2.agc, string, objectArray, (Throwable)null);
    }

    public void b(axe axe2, String string, Throwable throwable) {
        this.a(cQD, axe2, rl_2.agc, string, null, throwable);
    }

    public void error(String string) {
        this.a(cQD, null, rl_2.afZ, string, null, null);
    }

    public void m(String string, Object object) {
        this.a(cQD, null, rl_2.afZ, string, object, null);
    }

    public void e(String string, Object object, Object object2) {
        this.a(cQD, null, rl_2.afZ, string, object, object2, null);
    }

    public void error(String string, Object[] objectArray) {
        this.a(cQD, (axe)null, rl_2.afZ, string, objectArray, (Throwable)null);
    }

    public void error(String string, Throwable throwable) {
        this.a(cQD, null, rl_2.afZ, string, null, throwable);
    }

    public void e(axe axe2, String string) {
        this.a(cQD, axe2, rl_2.afZ, string, null, null);
    }

    public void e(axe axe2, String string, Object object) {
        this.a(cQD, axe2, rl_2.afZ, string, object, null);
    }

    public void e(axe axe2, String string, Object object, Object object2) {
        this.a(cQD, axe2, rl_2.afZ, string, object, object2, null);
    }

    public void e(axe axe2, String string, Object[] objectArray) {
        this.a(cQD, axe2, rl_2.afZ, string, objectArray, (Throwable)null);
    }

    public void e(axe axe2, String string, Throwable throwable) {
        this.a(cQD, axe2, rl_2.afZ, string, null, throwable);
    }

    public boolean isInfoEnabled() {
        return this.c((axe)null);
    }

    public boolean c(axe axe2) {
        vq_0 vq_02 = this.b(axe2, rl_2.agb);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= 20000;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public void info(String string) {
        this.a(cQD, null, rl_2.agb, string, null, null);
    }

    public void k(String string, Object object) {
        this.a(cQD, null, rl_2.agb, string, object, null);
    }

    public void c(String string, Object object, Object object2) {
        this.a(cQD, null, rl_2.agb, string, object, object2, null);
    }

    public void c(String string, Object[] objectArray) {
        this.a(cQD, (axe)null, rl_2.agb, string, objectArray, (Throwable)null);
    }

    public void info(String string, Throwable throwable) {
        this.a(cQD, null, rl_2.agb, string, null, throwable);
    }

    public void c(axe axe2, String string) {
        this.a(cQD, axe2, rl_2.agb, string, null, null);
    }

    public void c(axe axe2, String string, Object object) {
        this.a(cQD, axe2, rl_2.agb, string, object, null);
    }

    public void c(axe axe2, String string, Object object, Object object2) {
        this.a(cQD, axe2, rl_2.agb, string, object, object2, null);
    }

    public void c(axe axe2, String string, Object[] objectArray) {
        this.a(cQD, axe2, rl_2.agb, string, objectArray, (Throwable)null);
    }

    public void c(axe axe2, String string, Throwable throwable) {
        this.a(cQD, axe2, rl_2.agb, string, null, throwable);
    }

    public final boolean isTraceEnabled() {
        return this.a((axe)null);
    }

    public boolean a(axe axe2) {
        vq_0 vq_02 = this.b(axe2, rl_2.agd);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= 5000;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public final boolean Ij() {
        return this.e(null);
    }

    public boolean e(axe axe2) {
        vq_0 vq_02 = this.b(axe2, rl_2.afZ);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= 40000;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public boolean Ii() {
        return this.d(null);
    }

    public boolean d(axe axe2) {
        vq_0 vq_02 = this.b(axe2, rl_2.aga);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= 30000;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public boolean a(axe axe2, rl_2 rl_22) {
        vq_0 vq_02 = this.b(axe2, rl_22);
        if (vq_02 == vq_0.bTo) {
            return this.cQF <= rl_22.agf;
        }
        if (vq_02 == vq_0.bTn) {
            return false;
        }
        if (vq_02 == vq_0.bTp) {
            return true;
        }
        throw new IllegalStateException("Unknown FilterReply value: " + (Object)((Object)vq_02));
    }

    public boolean c(rl_2 rl_22) {
        return this.a(null, rl_22);
    }

    public void warn(String string) {
        this.a(cQD, null, rl_2.aga, string, null, null);
    }

    public void warn(String string, Throwable throwable) {
        this.a(cQD, null, rl_2.aga, string, null, throwable);
    }

    public void l(String string, Object object) {
        this.a(cQD, null, rl_2.aga, string, object, null);
    }

    public void d(String string, Object object, Object object2) {
        this.a(cQD, null, rl_2.aga, string, object, object2, null);
    }

    public void warn(String string, Object[] objectArray) {
        this.a(cQD, (axe)null, rl_2.aga, string, objectArray, (Throwable)null);
    }

    public void d(axe axe2, String string) {
        this.a(cQD, axe2, rl_2.aga, string, null, null);
    }

    public void d(axe axe2, String string, Object object) {
        this.a(cQD, axe2, rl_2.aga, string, object, null);
    }

    public void d(axe axe2, String string, Object[] objectArray) {
        this.a(cQD, axe2, rl_2.aga, string, objectArray, (Throwable)null);
    }

    public void d(axe axe2, String string, Object object, Object object2) {
        this.a(cQD, axe2, rl_2.aga, string, object, object2, null);
    }

    public void d(axe axe2, String string, Throwable throwable) {
        this.a(cQD, axe2, rl_2.aga, string, null, throwable);
    }

    public boolean aEU() {
        return this.additive;
    }

    public void dU(boolean bl2) {
        this.additive = bl2;
    }

    public String toString() {
        return "Logger[" + this.name + "]";
    }

    private vq_0 b(axe axe2, rl_2 rl_22) {
        return this.cFW.c(axe2, this, rl_22, null, null, null);
    }

    public ahu_0 ON() {
        return this.cFW;
    }

    public hK agu() {
        return this.bPh;
    }

    void aEV() {
        this.bPh = new hK(this.name, this.cFW);
    }

    public void a(axe axe2, String string, int n2, String string2, Throwable throwable) {
        rl_2 rl_22 = null;
        switch (n2) {
            case 0: {
                rl_22 = rl_2.agd;
                break;
            }
            case 10: {
                rl_22 = rl_2.agc;
                break;
            }
            case 20: {
                rl_22 = rl_2.agb;
                break;
            }
            case 30: {
                rl_22 = rl_2.aga;
                break;
            }
            case 40: {
                rl_22 = rl_2.afZ;
                break;
            }
            default: {
                throw new IllegalArgumentException(n2 + " not a valid level value");
            }
        }
        this.a(string, axe2, rl_22, string2, null, throwable);
    }

    protected Object readResolve() {
        return LD.D(this.getName());
    }
}

