/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Level
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/*
 * Renamed from ahC
 */
public class ahc_2
implements cn_1 {
    protected static final Logger a = Logger.getLogger(ahc_2.class);
    private static final Logger bGT = Logger.getLogger((String)"debug");
    private static final boolean cwB = false;
    private aja_1 bHj;
    private int aFD;
    private int aFE;
    private short cwC;
    private int boR;
    private int boS;
    private short cwD;
    private int cwE;
    private int cwF;
    private short cwG;
    private short cwH;
    private boolean cwI = false;
    private final sl_1[] cwJ = new sl_1[32];
    private static final acl_0 aU;
    private aPl cwK = null;

    private ahc_2() {
        for (int j = 0; j < this.cwJ.length; ++j) {
            this.cwJ[j] = new sl_1();
        }
    }

    public static ahc_2 axo() {
        try {
            return (ahc_2)aU.adr();
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
            return null;
        }
    }

    public final void release() {
        try {
            aU.af(this);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    public final void b() {
    }

    public final void j() {
        this.bHj = null;
    }

    public final void a(aja_1 aja_12) {
        this.bHj = aja_12;
    }

    public final void C(int n2, int n3, short s) {
        this.aFD = n2;
        this.aFE = n3;
        this.cwC = s;
    }

    public final void z(ry ry2) {
        this.aFD = ry2.getX();
        this.aFE = ry2.getY();
        this.cwC = ry2.wk();
    }

    public final void D(int n2, int n3, short s) {
        this.boR = n2;
        this.boS = n3;
        this.cwD = s;
    }

    public final void A(ry ry2) {
        this.boR = ry2.getX();
        this.boS = ry2.getY();
        this.cwD = ry2.wk();
    }

    private boolean axp() {
        boolean bl2;
        acm_1 acm_12 = this.bHj.ch(this.cwE, this.cwF);
        if (acm_12 == null) {
            return false;
        }
        boolean bl3 = bl2 = !this.cwI && this.cwE == this.boR && this.cwF == this.boS || this.cwE == this.aFD && this.cwF == this.aFE;
        if (!bl2 && this.bHj.bE(this.cwE, this.cwF)) {
            return false;
        }
        int n2 = acm_12.a(this.cwE, this.cwF, this.cwJ, 0);
        if (n2 <= 0) {
            return false;
        }
        for (int j = 0; j < n2; ++j) {
            sl_1 sl_12 = this.cwJ[j];
            if (sl_12.wp <= this.cwG) continue;
            if (sl_12.wp - sl_12.aba >= this.cwH) {
                return j > 0;
            }
            if (this.cwH <= sl_12.wp - sl_12.aba || this.cwG >= sl_12.wp || sl_12.aiT) continue;
            return false;
        }
        return true;
    }

    private boolean E(int n2, int n3, short s) {
        if (this.cwE == Integer.MAX_VALUE) {
            this.cwE = n2;
            this.cwF = n3;
            this.cwG = s;
            this.cwH = s;
            return true;
        }
        if (n2 == this.cwE && n3 == this.cwF) {
            if (s < this.cwG) {
                this.cwG = s;
            }
            if (s > this.cwH) {
                this.cwH = s;
            }
            return true;
        }
        if (!this.axp()) {
            return false;
        }
        this.cwE = n2;
        this.cwF = n3;
        this.cwG = s;
        this.cwH = s;
        return true;
    }

    public final boolean axq() {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        assert (this.bHj != null) : "No TopologyMapInstanceSet defined for this LOS Check";
        this.cwE = Integer.MAX_VALUE;
        this.cwF = Integer.MAX_VALUE;
        this.cwG = Short.MAX_VALUE;
        this.cwH = Short.MIN_VALUE;
        int n8 = this.aFD;
        int n9 = this.aFE;
        short s = this.cwC;
        int n10 = this.boR - this.aFD;
        int n11 = this.boS - this.aFE;
        int n12 = this.cwD - this.cwC;
        if (n10 < 0) {
            n7 = -1;
            n6 = -n10;
        } else {
            n7 = 1;
            n6 = n10;
        }
        if (n11 < 0) {
            n5 = -1;
            n4 = -n11;
        } else {
            n5 = 1;
            n4 = n11;
        }
        if (n12 < 0) {
            n3 = -1;
            n2 = -n12;
        } else {
            n3 = 1;
            n2 = n12;
        }
        int n13 = n6 << 2;
        int n14 = n4 << 2;
        int n15 = n2 << 2;
        if (n6 >= n4 && n6 >= n2) {
            int n16 = (n14 >>> 1) - 2 * n6;
            int n17 = (n15 >>> 1) - 2 * n6;
            for (int j = 0; j < n6; ++j) {
                n8 += n7;
                if (n16 < 0) {
                    if (n17 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n17 == 0) {
                        n17 -= n13;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        n17 -= n13;
                        if (!this.E(n8 - n7, n9, s = (short)(s + n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else if (n16 == 0) {
                    n16 -= n13;
                    n9 += n5;
                    if (n17 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n17 == 0) {
                        n17 -= n13;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        n17 -= n13;
                        if (!this.E(n8 - n7, n9 - n5, s = (short)(s + n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else {
                    n16 -= n13;
                    n9 += n5;
                    if (n17 < 0) {
                        if (!this.E(n8 - n7, n9, s)) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n17 == 0) {
                        if (!this.E(n8 - n7, n9, s)) {
                            return false;
                        }
                        n17 -= n13;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        int n18;
                        s = (short)(s + n3);
                        int n19 = n16 + n16 - n14;
                        if (n19 > (n18 = (n17 -= n13) + n17 - n15) ? !this.E(n8 - n7, n9, (short)(s - n3)) : n18 > n19 && !this.E(n8 - n7, n9 - n5, s)) {
                            return false;
                        }
                        if (!this.E(n8 - n7, n9, s)) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                }
                n16 += n14;
                n17 += n15;
            }
        } else if (n4 >= n6 && n4 >= n2) {
            int n20 = (n13 >>> 1) - 2 * n4;
            int n21 = (n15 >>> 1) - 2 * n4;
            for (int j = 0; j < n4; ++j) {
                n9 += n5;
                if (n20 < 0) {
                    if (n21 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n21 == 0) {
                        n21 -= n14;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        n21 -= n14;
                        if (!this.E(n8, n9 - n5, s = (short)(s + n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else if (n20 == 0) {
                    n20 -= n14;
                    n8 += n7;
                    if (n21 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n21 == 0) {
                        n21 -= n14;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        n21 -= n14;
                        if (!this.E(n8 - n7, n9 - n5, s = (short)(s + n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else {
                    n20 -= n14;
                    n8 += n7;
                    if (n21 < 0) {
                        if (!this.E(n8, n9 - n5, s)) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n21 == 0) {
                        if (!this.E(n8, n9 - n5, s)) {
                            return false;
                        }
                        n21 -= n14;
                        if (!this.E(n8, n9, s = (short)(s + n3))) {
                            return false;
                        }
                    } else {
                        int n22;
                        s = (short)(s + n3);
                        int n23 = n20 + n20 - n13;
                        if (n23 > (n22 = (n21 -= n14) + n21 - n15) ? !this.E(n8, n9 - n5, (short)(s - n3)) : n22 > n23 && !this.E(n8 - n7, n9 - n5, s)) {
                            return false;
                        }
                        if (!this.E(n8, n9 - n5, s)) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                }
                n20 += n13;
                n21 += n15;
            }
        } else {
            int n24 = (n13 >>> 1) - 2 * n2;
            int n25 = (n14 >>> 1) - 2 * n2;
            for (int j = 0; j < n2; ++j) {
                s = (short)(s + n3);
                if (n24 < 0) {
                    if (n25 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n25 == 0) {
                        n25 -= n15;
                        if (!this.E(n8, n9 += n5, s)) {
                            return false;
                        }
                    } else {
                        n25 -= n15;
                        if (!this.E(n8, n9 += n5, (short)(s - n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else if (n24 == 0) {
                    n24 -= n15;
                    n8 += n7;
                    if (n25 < 0) {
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n25 == 0) {
                        n25 -= n15;
                        if (!this.E(n8, n9 += n5, s)) {
                            return false;
                        }
                    } else {
                        n25 -= n15;
                        if (!this.E(n8 - n7, n9 += n5, (short)(s - n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                } else {
                    n24 -= n15;
                    n8 += n7;
                    if (n25 < 0) {
                        if (!this.E(n8, n9, (short)(s - n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    } else if (n25 == 0) {
                        if (!this.E(n8, n9, (short)(s - n3))) {
                            return false;
                        }
                        n25 -= n15;
                        if (!this.E(n8, n9 += n5, s)) {
                            return false;
                        }
                    } else {
                        int n26;
                        n9 += n5;
                        int n27 = n24 + n24 - n13;
                        if (n27 > (n26 = (n25 -= n15) + n25 - n14) ? !this.E(n8, n9 - n5, (short)(s - n3)) : n26 > n27 && !this.E(n8 - n7, n9, (short)(s - n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, (short)(s - n3))) {
                            return false;
                        }
                        if (!this.E(n8, n9, s)) {
                            return false;
                        }
                    }
                }
                n24 += n13;
                n25 += n14;
            }
        }
        return this.axp();
    }

    public void a(aPl aPl2) {
        this.cwK = aPl2;
    }

    /* synthetic */ ahc_2(avy avy2) {
        this();
    }

    static {
        bGT.setLevel(Level.ALL);
        aU = new ym_0(new avy());
    }
}

