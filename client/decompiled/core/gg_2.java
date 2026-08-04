/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;

/*
 * Renamed from GG
 */
public class gg_2
implements xu_1 {
    private int aX;
    public final int bci = -1;
    private int wg = -1;
    private int bcj = 0;
    private int aW;
    private float bck = 1.0f;
    final /* synthetic */ abS bcl;

    private gg_2(abS abS2) {
        this.bcl = abS2;
    }

    public aln_1 Ek() {
        return this.bcl.q;
    }

    public void c(aln_1 aln_12) {
    }

    public int getXOffset() {
        return this.bcl.getXOffset();
    }

    public void setXOffset(int n2) {
        abS.a(this.bcl, n2);
    }

    public int getYOffset() {
        return this.bcl.getYOffset();
    }

    public void setYOffset(int n2) {
        abS.b(this.bcl, n2);
    }

    public double getWorldX() {
        return this.bcl.getWorldX();
    }

    public double getWorldY() {
        return this.bcl.getWorldY();
    }

    public double getAltitude() {
        if (this.bcl.q != null) {
            return this.bcl.q.getAltitude();
        }
        return 0.0;
    }

    public void c(float f, float f2, float f3, float f4) {
        this.bcl.a(null, this.bcl.q.getScreenX(), this.bcl.q.getScreenY(), 0);
    }

    public int getDuration() {
        return this.wg;
    }

    public boolean isAlive() {
        if (this.wg == -1) {
            return true;
        }
        return this.bcj <= this.wg;
    }

    public void a(qs_2 qs_22, int n2) {
        float f = (float)qs_22.vn().oZ();
        if (this.bck != f) {
            this.bck = f;
        }
        this.bI(n2);
        this.bcl.j(n2, this.bck);
    }

    public void bI(int n2) {
        this.bcj += n2;
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int ao() {
        return this.aX;
    }

    public void h(int n2) {
        this.aX = n2;
    }

    public Entity getEntity() {
        return null;
    }

    public void cleanUp() {
        this.bcl.qa();
    }

    public boolean El() {
        return false;
    }

    public void fW(int n2) {
        this.bcj = n2;
    }

    public int QI() {
        return this.bcj;
    }

    public void setDuration(int n2) {
        this.wg = n2;
    }

    public float QJ() {
        return this.bck;
    }

    /* synthetic */ gg_2(abS abS2, ce_0 ce_02) {
        this(abS2);
    }
}

