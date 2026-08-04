/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from akq
 */
public final class akq_1 {
    private int fb;
    private int fc;
    public float bsB;
    public float bsC;
    public float bsD;
    public float bsA;
    private ef_1 tl;
    private int aG;
    private int aH;
    private boolean bnh;
    private boolean cDr;
    private boolean aFi = false;
    private boolean aFj = false;
    private xd_1 cDs = xd_1.azj;
    private final ArrayList cDt = new ArrayList();

    public akq_1() {
        this.aG = 0;
        this.aH = 0;
        this.fb = -1;
        this.fc = -1;
        this.cDr = true;
    }

    public akq_1(ef_1 ef_12) {
        assert (ef_12 != null);
        this.tl = ef_12;
        this.tl.HE();
        this.aG = 0;
        this.aH = 0;
        this.fb = -1;
        this.fc = -1;
        this.cDr = true;
        this.azR();
    }

    public akq_1(ef_1 ef_12, int n2, int n3, int n4, int n5) {
        assert (ef_12 != null);
        this.tl = ef_12;
        this.tl.HE();
        this.aG = n2;
        this.aH = n3;
        this.fb = n4;
        this.fc = n5;
        this.cDr = false;
        this.azR();
    }

    public final void a(ie_1 ie_12) {
        assert (ie_12 != null) : "Client can't be null";
        assert (!this.cDt.contains(ie_12)) : "Client already registered for this pixmap";
        this.cDt.add(ie_12);
    }

    public final void b(ie_1 ie_12) {
        assert (ie_12 != null) : "Client can't be null";
        this.cDt.remove(ie_12);
    }

    public final ef_1 jI() {
        return this.tl;
    }

    public final int azM() {
        return this.fc;
    }

    public final int azN() {
        return this.fb;
    }

    public final int getHeight() {
        return this.cDs.Ep() ? this.fb : this.fc;
    }

    public final int getWidth() {
        return this.cDs.Ep() ? this.fc : this.fb;
    }

    public final int getX() {
        return this.aG;
    }

    public final int getY() {
        return this.aH;
    }

    public final float Hw() {
        return this.bsB;
    }

    public final float Hx() {
        return this.bsC;
    }

    public final float Hz() {
        return this.bsD;
    }

    public final float Hy() {
        return this.bsA;
    }

    public final void setTexture(ef_1 ef_12) {
        if (ef_12 != null) {
            ef_12.HE();
        }
        if (this.tl != null) {
            this.tl.HF();
        }
        this.tl = ef_12;
        this.bnh = false;
    }

    public final void setX(int n2) {
        this.aG = n2;
        this.bnh = false;
    }

    public final void setY(int n2) {
        this.aH = n2;
        this.bnh = false;
    }

    public final void setWidth(int n2) {
        this.fb = n2;
        this.bnh = false;
    }

    public final void setHeight(int n2) {
        this.fc = n2;
        this.bnh = false;
    }

    public void setFlipVerticaly(boolean bl2) {
        this.aFj = bl2;
        this.bnh = false;
    }

    public void setFlipHorizontaly(boolean bl2) {
        this.aFi = bl2;
        this.bnh = false;
    }

    public boolean Gl() {
        return this.aFi;
    }

    public boolean Gm() {
        return this.aFj;
    }

    public xd_1 getRotation() {
        return this.cDs;
    }

    public void setRotation(xd_1 xd_12) {
        if (xd_12 != this.cDs) {
            this.cDs = xd_12;
            this.bnh = false;
        }
    }

    public final void dF(boolean bl2) {
        this.cDr = bl2;
        this.bnh = false;
    }

    public final boolean azO() {
        return this.cDr;
    }

    public final boolean azP() {
        return !this.bnh;
    }

    public final adz_1 azQ() {
        return cx_0.JY().c(this.tl);
    }

    public void azR() {
        float f;
        adz_1 adz_12;
        if (this.tl == null) {
            return;
        }
        if (this.cDr) {
            adz_12 = this.azQ();
            adz_1 adz_13 = this.tl.lC(0);
            f = adz_13.getX();
            float f2 = adz_13.getY();
            this.aG = 0;
            this.aH = 0;
            this.fb = adz_12.getX();
            this.fc = adz_12.getY();
            this.bsA = 0.0f;
            this.bsB = 0.0f;
            this.bsD = (float)this.fc / f2;
            this.bsC = (float)this.fb / f;
        } else {
            adz_12 = this.tl.lC(0);
            float f3 = adz_12.getX();
            f = adz_12.getY();
            this.bsB = (float)this.aG / f3;
            this.bsC = (float)(this.aG + this.fb) / f3;
            this.bsA = (float)this.aH / f;
            this.bsD = (float)(this.aH + this.fc) / f;
        }
        if (this.aFi) {
            float f4 = this.bsB;
            this.bsB = this.bsC;
            this.bsC = f4;
        }
        if (this.aFj) {
            float f5 = this.bsA;
            this.bsA = this.bsD;
            this.bsD = f5;
        }
        this.bnh = true;
        for (int j = this.cDt.size() - 1; j >= 0; --j) {
            ((ie_1)this.cDt.get(j)).a(this);
        }
    }
}

