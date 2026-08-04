/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aiv
 */
public class aiv_2
extends rf_0
implements Fc,
px_2 {
    public static final String TAG = "CircularList";
    private boolean cyG = false;
    private ArrayList cyH = new ArrayList();
    private ArrayList cyI = new ArrayList();

    public boolean next() {
        return this.kR(1);
    }

    public boolean previous() {
        return this.kS(1);
    }

    public void e(abd_1 abd_12) {
    }

    public boolean kR(int n2) {
        this.cyG = true;
        this.dA(this.cyG);
        float f = this.bJD + (float)n2;
        boolean bl2 = false;
        if (f > (float)(this.ec.size() - (this.dS.size() - 1))) {
            for (int j = 0; j < Math.abs(n2); ++j) {
                this.f(this.ec.get(0), this.ec.size() - 1);
            }
            this.ca();
            this.setListOffset(this.bJD - (float)n2);
            bl2 = true;
        }
        this.q(yd_0.class);
        yd_0 yd_02 = new yd_0(this.bJD, f, this, 0, 500, ys.aCq);
        yd_02.a(new bj_1(this, yd_02));
        this.a(yd_02);
        return bl2;
    }

    public boolean kS(int n2) {
        this.cyG = true;
        this.dA(this.cyG);
        float f = this.bJD - (float)n2;
        boolean bl2 = false;
        if (f < 0.0f) {
            for (int j = 0; j < Math.abs(n2); ++j) {
                this.f(this.ec.get(this.ec.size() - (1 + j)), 0);
            }
            this.ca();
            this.setListOffset(this.bJD + (float)n2);
            bl2 = true;
        }
        this.q(yd_0.class);
        yd_0 yd_02 = new yd_0(this.bJD, f, this, 0, 500, ys.aCq);
        yd_02.a(new bh_0(this, yd_02));
        this.a(yd_02);
        return bl2;
    }

    public boolean isMoving() {
        return this.cyG;
    }

    public void a(oF oF2) {
        if (oF2 != null && !this.cyH.contains(oF2)) {
            this.cyH.add(oF2);
        }
    }

    public void b(oF oF2) {
        this.cyH.remove(oF2);
    }

    public void ayo() {
        for (int j = this.cyH.size() - 1; j >= 0; --j) {
            ((oF)this.cyH.get(j)).tP();
        }
    }

    public void a(aal_0 aal_02) {
        if (aal_02 != null && !this.cyI.contains(aal_02)) {
            this.cyI.add(aal_02);
        }
    }

    public void b(aal_0 aal_02) {
        this.cyI.remove(aal_02);
    }

    public void dA(boolean bl2) {
        for (int j = this.cyI.size() - 1; j >= 0; --j) {
            ((aal_0)this.cyI.get(j)).cN(bl2);
        }
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        this.ayo();
        return bl2;
    }

    static /* synthetic */ boolean a(aiv_2 aiv_22, boolean bl2) {
        aiv_22.cyG = bl2;
        return aiv_22.cyG;
    }

    static /* synthetic */ boolean a(aiv_2 aiv_22) {
        return aiv_22.cyG;
    }
}

