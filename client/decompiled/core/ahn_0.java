/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.log4j.Logger;

/*
 * Renamed from aHN
 */
public final class ahn_0
implements yv_2 {
    private static final Logger a = Logger.getLogger(ahn_0.class);
    public static final ahn_0 dNL = new ahn_0();
    private final lb_0 dNM = new lb_0();
    private final ArrayList gl = new ArrayList();
    private final ArrayList dNN = new ArrayList(2000);
    private static final Comparator dNO = new apY();
    private final ArrayList dNP = new ArrayList(5);
    private final ArrayList dNQ = new ArrayList(5);
    private float dNR;
    private float dNS = -3.4028235E38f;
    private final zx_2 dNT = new zx_2();
    private final uQ dNU = uQ.AV();
    private final ArrayList dNV = new ArrayList();
    private boolean dNW = true;

    private ahn_0() {
    }

    public void initialize() {
        do_0.aNC.a(new w_0(), this);
    }

    public boolean aUg() {
        return this.dNW;
    }

    public void fc(boolean bl2) {
        this.dNW = bl2;
    }

    public lP oD(int n2) {
        return (lP)this.dNM.get(n2);
    }

    public void oE(int n2) {
        lP lP2 = (lP)this.dNM.get(n2);
        if (lP2 != null) {
            lP2.setEnabled(true);
        }
    }

    public void oF(int n2) {
        lP lP2 = (lP)this.dNM.get(n2);
        if (lP2 != null) {
            lP2.setEnabled(false);
        }
    }

    public void oG(int n2) {
        lP lP2 = (lP)this.dNM.remove(n2);
        if (lP2 != null) {
            akK.cDL.d(lP2);
        }
    }

    public void b(int n2, long l2, long l3) {
        if (l3 == 0L) {
            this.oG(n2);
            return;
        }
        lP lP2 = (lP)this.dNM.get(n2);
        if (lP2 != null) {
            lP2.p(l2, l3);
        }
    }

    public void a(aNH aNH2) {
        this.dNM.c(aNH2.getId(), (lP)aNH2);
        akK.cDL.c(aNH2);
    }

    public void b(aNH aNH2) {
        this.dNM.remove(aNH2.getId());
        akK.cDL.d(aNH2);
    }

    public void a(aht_2 aht_22) {
        if (!this.gl.contains(aht_22)) {
            this.gl.add(aht_22);
        }
    }

    public void b(aht_2 aht_22) {
        this.gl.remove(aht_22);
    }

    public void a(afr_1 afr_12) {
        if (!this.dNP.contains(afr_12)) {
            this.dNP.add(afr_12);
            Collections.sort(this.dNP, dNO);
        }
    }

    public void b(afr_1 afr_12) {
        this.dNP.remove(afr_12);
    }

    public void c(afr_1 afr_12) {
        if (!this.dNQ.contains(afr_12)) {
            this.dNQ.add(afr_12);
            Collections.sort(this.dNP, dNO);
        }
    }

    public void d(afr_1 afr_12) {
        this.dNQ.remove(afr_12);
    }

    public void c(aba_2 aba_22, int n2) {
        if (!this.dNW) {
            return;
        }
        this.f(aba_22);
        this.oH(n2);
        this.bU(this.dNR);
        if (this.dNU.AX() || this.dNR - this.dNS > Math.abs(0.05f)) {
            this.dNU.a(this.dNR);
            this.dNU.aK(false);
        }
        this.aUh();
        this.dNT.normalize();
        this.aUi();
    }

    private void oH(int n2) {
        int n3;
        int n4 = this.dNP.size();
        for (n3 = 0; n3 < n4; ++n3) {
            ((afr_1)this.dNP.get(n3)).update(n2);
        }
        n4 = this.dNQ.size();
        for (n3 = 0; n3 < n4; ++n3) {
            ((afr_1)this.dNQ.get(n3)).update(n2);
        }
    }

    private void bU(float f) {
        this.dNV.clear();
        long l2 = System.currentTimeMillis();
        if (!this.dNM.isEmpty()) {
            this.dNM.a(new apt_0(this, f, l2));
        }
        if (!this.dNV.isEmpty()) {
            int n2 = this.dNV.size();
            for (int j = 0; j < n2; ++j) {
                aNH aNH2 = (aNH)this.dNV.get(j);
                this.b(aNH2);
            }
            this.dNV.clear();
        }
    }

    private void f(aba_2 aba_22) {
        yg_1 yg_12 = aba_22.vC();
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MIN_VALUE;
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MIN_VALUE;
        this.dNN.clear();
        int n6 = this.gl.size();
        for (int j = 0; j < n6; ++j) {
            aht_2 aht_22 = (aht_2)this.gl.get(j);
            ArrayList arrayList = aht_22.a(yg_12);
            if (arrayList == null || arrayList.isEmpty()) continue;
            this.dNN.add(arrayList);
            int n7 = arrayList.size();
            for (int i2 = 0; i2 < n7; ++i2) {
                amw_0 amw_02 = (amw_0)arrayList.get(i2);
                int n8 = amw_02.gn();
                int n9 = amw_02.go();
                if (n8 < n2) {
                    n2 = n8;
                }
                if (n8 > n3) {
                    n3 = n8;
                }
                if (n9 < n4) {
                    n4 = n9;
                }
                if (n9 > n5) {
                    n5 = n9;
                }
                this.dNT.bg(n8, n9);
            }
        }
        this.dNT.setBounds(n2, n4, n3 - n2 + 1, n5 - n4 + 1);
    }

    private void aUh() {
        int n2;
        int n3 = this.dNP.size();
        for (n2 = 0; n2 < n3; ++n2) {
            this.a((ri_0)this.dNP.get(n2));
        }
        if (!this.dNM.isEmpty()) {
            this.dNM.a(new apv_0(this));
        }
        n3 = this.dNQ.size();
        for (n2 = 0; n2 < n3; ++n2) {
            this.a((ri_0)this.dNQ.get(n2));
        }
    }

    private void aUi() {
        int n2 = this.dNN.size();
        float[] fArray = new float[6];
        for (int j = 0; j < n2; ++j) {
            ArrayList arrayList = (ArrayList)this.dNN.get(j);
            int n3 = arrayList.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                byte by;
                int n4;
                amw_0 amw_02 = (amw_0)arrayList.get(i2);
                int n5 = amw_02.gn();
                adb_0 adb_02 = this.dNU.n(n5, n4 = amw_02.go(), by = amw_02.atZ());
                if (adb_02 == null) {
                    ko_1.b(fArray, this.dNT.F(n5, n4, by));
                } else {
                    ko_1.a(fArray, adb_02.Aa());
                    if (adb_02.dxI) {
                        ko_1.c(fArray, this.dNT.F(n5, n4, by));
                    }
                    if (adb_02.aPv()) {
                        ko_1.d(fArray, adb_02.aPu());
                        if (fArray[0] > 2.0f) {
                            fArray[0] = 2.0f;
                        }
                        if (fArray[1] > 2.0f) {
                            fArray[1] = 2.0f;
                        }
                        if (fArray[2] > 2.0f) {
                            fArray[2] = 2.0f;
                        }
                    }
                }
                amw_02.e(fArray);
            }
        }
    }

    private void a(ri_0 ri_02) {
        ri_02.a(this.dNT);
    }

    public void reset() {
        this.dNM.clear();
        this.dNP.clear();
        this.dNQ.clear();
        this.dNR = 1.0f;
        this.dNS = -3.4028235E38f;
    }

    public void jW() {
        this.dNU.aK(true);
    }

    public void b(kC kC2) {
    }

    public void bV(float f) {
        this.dNR = f;
    }

    static /* synthetic */ ArrayList a(ahn_0 ahn_02) {
        return ahn_02.dNV;
    }

    static /* synthetic */ void a(ahn_0 ahn_02, ri_0 ri_02) {
        ahn_02.a(ri_02);
    }
}

