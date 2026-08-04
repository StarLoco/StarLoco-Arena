/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBz
 */
public class abz_1 {
    public static float drK = 0.4f;
    public static float drL = 700.0f;
    private float drM = 0.0f;
    private float bAQ = drK;
    private float drN = drL;
    private nz drO;

    public abz_1(nz nz2) {
        this.drO = nz2;
    }

    public void aNv() {
        if (this.drO == null) {
            return;
        }
        this.drM = this.drO.getGain();
        this.drO.j(this.bAQ * this.drO.getGain(), this.drN);
    }

    public void ly() {
        if (this.drO == null) {
            return;
        }
        this.drO.j(this.drM, this.drN);
    }

    public void bA(float f) {
        this.bAQ = f;
    }

    public void bB(float f) {
        this.drN = f;
    }

    public void aNw() {
        this.bAQ = drK;
    }

    public void aNx() {
        this.drN = drL;
    }
}

