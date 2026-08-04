/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aMh
 */
public class amh_1
implements Pq {
    private double dXw;
    private double dXx;
    private double dXy;
    private double dXz;
    private double dXA;
    private double dXB;
    private boolean aK = false;

    public void a(GL gL) {
        gL.glLoadIdentity();
        gL.glTranslated(this.dXw, this.dXx, this.dXy);
    }

    public void reset() {
    }

    public void R(double d) {
        this.dXw = d;
    }

    public void S(double d) {
        this.dXx = d;
    }

    public void T(double d) {
        this.dXy = d;
    }

    public double aWQ() {
        return this.dXz;
    }

    public void U(double d) {
        this.aK = true;
        this.dXz = d;
    }

    public double aWR() {
        return this.dXA;
    }

    public void V(double d) {
        this.aK = true;
        this.dXA = d;
    }

    public double aWS() {
        return this.dXB;
    }

    public void W(double d) {
        this.aK = true;
        this.dXB = d;
    }

    public boolean isInitialized() {
        return this.aK;
    }
}

