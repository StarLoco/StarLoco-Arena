/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.AL
 *  org.apache.log4j.Logger
 */
import net.java.games.joal.AL;
import org.apache.log4j.Logger;

/*
 * Renamed from aAG
 */
public class aag_1 {
    private static final Logger a = Logger.getLogger(aag_1.class);
    private final aL bAT;
    private final AL cY;
    private final int dfj;
    private final int[] bZl;
    private final float bAQ;
    private final float dpU;
    private final qa_2 dpV;
    private float Ov;
    private float dpW;
    private boolean chi;
    private ahb_1 dpX;

    public aag_1(float f, float f2, int n2, aL aL2, boolean bl2) {
        if (aL2 == null) {
            throw new IllegalArgumentException("Argument 3 for @NotNull parameter of com/ankamagames/framework/sound/openAL/LowPassFilter.<init> must not be null");
        }
        this.bZl = new int[]{0};
        this.dpV = new qa_2();
        this.dpX = null;
        this.bAT = aL2;
        this.cY = this.bAT.bI();
        this.dfj = n2;
        this.chi = bl2;
        this.bAQ = f;
        this.dpU = f2;
        this.Ov = this.chi ? 1.0f : f;
        this.dpW = this.chi ? 1.0f : f2;
    }

    public void aMS() {
        this.cY.alGenFilters(1, this.bZl, 0);
        this.cY.alFilteri(this.bZl[0], 32769, 1);
    }

    public void aMT() {
        if (this.bZl[0] != 0) {
            this.cY.alDeleteFilters(1, this.bZl, 0);
            this.bZl[0] = 0;
        }
    }

    public void eF(boolean bl2) {
        if (this.chi == bl2) {
            return;
        }
        this.chi = bl2;
        this.dpX = this.chi ? new ahb_1(this, 1.0f, 1.0f, 500) : new ahb_1(this, this.bAQ, this.dpU, 500);
    }

    public int zU() {
        return this.dfj;
    }

    public int aMU() {
        return this.bZl[0];
    }

    public void aD(long l2) {
        if (this.dpX != null && !this.dpX.ev(l2)) {
            this.dpX = null;
        }
    }

    private void Q(float f, float f2) {
        this.cY.alFilterf(this.bZl[0], 1, f);
        this.cY.alFilterf(this.bZl[0], 2, f2);
        for (int j = this.dpV.size() - 1; j >= 0; --j) {
            this.eo(this.dpV.get(j));
        }
    }

    private void eo(long l2) {
        avE avE2 = ahz_1.aUa().ex(l2);
        if (avE2 != null) {
            avE2.my(this.bZl[0]);
        }
    }

    public void ep(long l2) {
        if (!this.dpV.m(l2)) {
            this.dpV.ct(l2);
            this.eo(l2);
        }
    }

    public void eq(long l2) {
        int n2 = this.dpV.cw(l2);
        if (n2 != -1) {
            this.dpV.remove(n2);
            if (this.dpV.size() == 0) {
                this.aMV();
            }
        }
    }

    private void aMV() {
        if (this.bAT != null) {
            this.bAT.o(this.dfj);
        }
    }

    static /* synthetic */ float a(aag_1 aag_12) {
        return aag_12.Ov;
    }

    static /* synthetic */ float b(aag_1 aag_12) {
        return aag_12.dpW;
    }

    static /* synthetic */ float a(aag_1 aag_12, float f) {
        aag_12.Ov = f;
        return aag_12.Ov;
    }

    static /* synthetic */ float b(aag_1 aag_12, float f) {
        aag_12.dpW = f;
        return aag_12.dpW;
    }

    static /* synthetic */ void a(aag_1 aag_12, float f, float f2) {
        aag_12.Q(f, f2);
    }
}

