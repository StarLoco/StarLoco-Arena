/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from OG
 */
public final class og_1 {
    private rr_0 bCj;
    private ef_1 tl;
    private kf_0 bCk;
    private int bCl = -1;
    private float bsD = 0.0f;
    private float bsA = 1.0f;
    private float bsB = 0.0f;
    private float bsC = 1.0f;

    public void a(rr_0 rr_02) {
        if (!rr_02.isInitialized()) {
            throw new UnsupportedOperationException("Tentative d'initialisation d'une entit\u00e9 video \u00e0 partir d'une video non initialis\u00e9e");
        }
        this.bCj = rr_02;
        this.tl = new Ss(yh_0.FD(), ej_0.aq(this.bCj.getWidth()), ej_0.aq(this.bCj.getHeight()), false);
        this.bCk = this.tl.lB(0);
        this.abA();
        this.bCl = -1;
    }

    public void k(db_2 db_22) {
        if (this.bCj == null || this.bCj.ww() == null) {
            return;
        }
        int n2 = this.bCj.wu();
        if (n2 == this.bCl) {
            return;
        }
        ayh ayh2 = this.bCj.ww();
        ByteBuffer byteBuffer = ayh2.getByteBuffer();
        this.bCk.a(byteBuffer, ayh2.getWidth(), ayh2.getHeight());
        this.tl.j(db_22);
        this.abA();
        this.bCl = n2;
    }

    private void abA() {
        adz_1 adz_12 = this.tl.lC(0);
        float f = this.bCj.wy();
        float f2 = this.bCj.wz();
        this.bsD = 0.0f;
        this.bsA = f2 / (float)adz_12.getY();
        this.bsB = 0.0f;
        this.bsC = f / (float)adz_12.getX();
    }

    public ef_1 jI() {
        return this.tl;
    }

    public rr_0 abB() {
        return this.bCj;
    }

    public float Hz() {
        return this.bsD;
    }

    public float Hy() {
        return this.bsA;
    }

    public float Hw() {
        return this.bsB;
    }

    public float Hx() {
        return this.bsC;
    }
}

