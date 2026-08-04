/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class aNe
implements qf_1 {
    private PU dYX;
    private ArrayList ckD = new ArrayList();

    public PU aXo() {
        return this.dYX;
    }

    public void a(PU pU) {
        this.dYX = pU;
    }

    public k_0 by(String string) {
        if (this.dYX != null) {
            return this.dYX.c(string);
        }
        return null;
    }

    public ArrayList bz(String string) {
        if (this.dYX != null) {
            return this.dYX.d(string);
        }
        return null;
    }

    public void a(vA vA2) {
        if (!this.ckD.contains(vA2)) {
            this.ckD.add(vA2);
        }
    }

    public void va() {
        for (vA vA2 : this.ckD) {
            vA2.a(this);
        }
    }

    public void vb() {
        for (vA vA2 : this.ckD) {
            vA2.b(this);
        }
    }

    public void bA(String string) {
        for (vA vA2 : this.ckD) {
            vA2.a(this, string);
        }
    }

    public void vc() {
        for (vA vA2 : this.ckD) {
            vA2.c(this);
        }
    }

    public void vd() {
        for (vA vA2 : this.ckD) {
            vA2.d(this);
        }
    }

    public void bB(String string) {
        for (vA vA2 : this.ckD) {
            vA2.b(this, string);
        }
    }
}

