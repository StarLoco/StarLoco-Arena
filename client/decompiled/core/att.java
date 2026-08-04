/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Dimension;
import java.awt.Insets;

public class att
extends jy_1 {
    public att() {
        this.AD = new akq_1[8];
    }

    public void setInsets(Insets insets) {
    }

    public akq_1 alu() {
        return this.AD[4];
    }

    public void b(akq_1 akq_12) {
        this.AD[4] = akq_12;
        this.alD();
    }

    public akq_1 alv() {
        return this.AD[1];
    }

    public void c(akq_1 akq_12) {
        this.AD[1] = akq_12;
        this.alD();
    }

    public akq_1 alw() {
        return this.AD[2];
    }

    public void d(akq_1 akq_12) {
        this.AD[2] = akq_12;
        this.alD();
    }

    public akq_1 alx() {
        return this.AD[0];
    }

    public void e(akq_1 akq_12) {
        this.AD[0] = akq_12;
        this.alD();
    }

    public akq_1 aly() {
        return this.AD[6];
    }

    public void f(akq_1 akq_12) {
        this.AD[6] = akq_12;
        this.alD();
    }

    public akq_1 alz() {
        return this.AD[7];
    }

    public void g(akq_1 akq_12) {
        this.AD[7] = akq_12;
        this.alD();
    }

    public akq_1 alA() {
        return this.AD[5];
    }

    public void h(akq_1 akq_12) {
        this.AD[5] = akq_12;
        this.alD();
    }

    public akq_1 alB() {
        return this.AD[3];
    }

    public void i(akq_1 akq_12) {
        this.AD[3] = akq_12;
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19) {
        this.AD[0] = akq_12;
        this.AD[1] = akq_13;
        this.AD[2] = akq_14;
        this.AD[3] = akq_15;
        this.AD[4] = akq_16;
        this.AD[5] = akq_17;
        this.AD[6] = akq_18;
        this.AD[7] = akq_19;
        this.alD();
    }

    public boolean Gk() {
        return this.AE;
    }

    public void a(Insets insets) {
        if (insets != null) {
            insets.top = Math.max(this.AD[0].getHeight(), Math.max(this.AD[1].getHeight(), this.AD[2].getHeight()));
            insets.bottom = Math.max(this.AD[5].getHeight(), Math.max(this.AD[6].getHeight(), this.AD[7].getHeight()));
            insets.left = Math.max(this.AD[0].getWidth(), Math.max(this.AD[3].getWidth(), this.AD[5].getWidth()));
            insets.right = Math.max(this.AD[2].getWidth(), Math.max(this.AD[4].getWidth(), this.AD[7].getWidth()));
        }
    }

    private void alD() {
        for (int j = this.AD.length - 1; j >= 0; --j) {
            if (this.AD[j] != null) continue;
            this.AE = false;
            return;
        }
        this.AE = true;
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        int n2 = insets.left + insets2.left;
        int n3 = insets.right + insets2.right;
        int n4 = insets.top + insets2.top;
        int n5 = insets.bottom + insets2.bottom;
        this.AH.clear();
        int[] nArray = new int[3];
        int[] nArray2 = new int[3];
        int n6 = insets.left;
        int n7 = dimension.height - insets.top;
        nArray[0] = insets2.left;
        nArray[1] = dimension.width - n2 - n3;
        nArray[2] = insets2.right;
        nArray2[0] = insets2.top;
        nArray2[1] = dimension.height - n4 - n5;
        nArray2[2] = insets2.bottom;
        int n8 = n7;
        int n9 = 0;
        for (int j = 0; j < 3; ++j) {
            int n10 = n6;
            for (int i2 = 0; i2 < 3; ++i2) {
                if (i2 != 1 || j != 1) {
                    this.a(n10, n8, nArray[i2], nArray2[j], this.AD[n9]);
                    ++n9;
                }
                n10 += nArray[i2];
            }
            n8 -= nArray2[j];
        }
    }
}

