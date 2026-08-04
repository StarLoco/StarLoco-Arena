/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from XM
 */
public class xm_1
extends di_1 {
    private static final short[] eX = new short[]{5, 4, 7, 6, 4, 11, 8, 7, 11, 10, 9, 8, 3, 2, 4, 5, 18, 19, 17, 16, 12, 13, 10, 11, 0, 1, 2, 3, 1, 15, 12, 2, 15, 14, 13, 12};
    private final int[] bZu = new int[40];
    private boolean aVo = false;

    public xm_1() {
        this.AD = new akq_1[9];
        this.AG = this.bZu;
        this.AF = eX;
    }

    public boolean isScaled() {
        return this.aVo;
    }

    public void setScaled(boolean bl2) {
        this.aVo = bl2;
    }

    public akq_1 alu() {
        return this.AD[5];
    }

    public void b(akq_1 akq_12) {
        this.AD[5] = akq_12;
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
        return this.AD[7];
    }

    public void f(akq_1 akq_12) {
        this.AD[7] = akq_12;
        this.alD();
    }

    public akq_1 alz() {
        return this.AD[8];
    }

    public void g(akq_1 akq_12) {
        this.AD[8] = akq_12;
        this.alD();
    }

    public akq_1 alA() {
        return this.AD[6];
    }

    public void h(akq_1 akq_12) {
        this.AD[6] = akq_12;
        this.alD();
    }

    public akq_1 alB() {
        return this.AD[3];
    }

    public void i(akq_1 akq_12) {
        this.AD[3] = akq_12;
        this.alD();
    }

    public akq_1 alC() {
        return this.AD[4];
    }

    public void j(akq_1 akq_12) {
        this.AD[4] = akq_12;
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110) {
        this.AD[0] = akq_12;
        this.AD[1] = akq_13;
        this.AD[2] = akq_14;
        this.AD[3] = akq_15;
        this.AD[4] = akq_16;
        this.AD[5] = akq_17;
        this.AD[6] = akq_18;
        this.AD[7] = akq_19;
        this.AD[8] = akq_110;
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12) {
        this.AD[4] = akq_12;
        this.alD();
    }

    public void setPixmaps(akq_1[] akq_1Array) {
        this.AD[0] = akq_1Array[0];
        this.AD[1] = akq_1Array[1];
        this.AD[2] = akq_1Array[2];
        this.AD[3] = akq_1Array[3];
        this.AD[4] = akq_1Array[4];
        this.AD[5] = akq_1Array[5];
        this.AD[6] = akq_1Array[6];
        this.AD[7] = akq_1Array[7];
        this.AD[8] = akq_1Array[8];
        this.alD();
    }

    private void alD() {
        if (this.AD[4] == null) {
            this.AE = false;
            return;
        }
        int n2 = 0;
        for (int j = this.AD.length - 1; j >= 0; --j) {
            if (this.AD[j] == null) continue;
            ++n2;
        }
        if (n2 != 1 && n2 != 9) {
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
        if (this.AD[0] == null) {
            this.a(dimension, n2, n3, n4, n5);
            return;
        }
        int[] nArray = new int[3];
        int[] nArray2 = new int[3];
        int[] nArray3 = new int[3];
        int[] nArray4 = new int[3];
        int n6 = insets.left;
        int n7 = dimension.height - insets.top;
        nArray[0] = this.AD[0].getWidth();
        nArray[2] = this.AD[2].getWidth();
        nArray[1] = dimension.width - (nArray[0] + nArray[2] + insets.left + insets.right);
        nArray2[0] = this.AD[0].getHeight();
        nArray2[2] = this.AD[6].getHeight();
        nArray2[1] = dimension.height - (nArray2[0] + nArray2[2] + insets.top + insets.bottom);
        nArray3[0] = n6;
        nArray3[1] = nArray3[0] + nArray[0];
        nArray3[2] = nArray3[1] + nArray[1];
        nArray4[0] = n7;
        nArray4[1] = nArray4[0] - nArray2[0];
        nArray4[2] = nArray4[1] - nArray2[1];
        for (int j = 0; j < 3; ++j) {
            for (int i2 = 0; i2 < 3; ++i2) {
                if (j == 1 && i2 == 1 && !this.aVo) {
                    akq_1 akq_12 = this.AD[j * 3 + i2];
                    int n8 = n6 + (dimension.width - (insets.left + insets.right) - akq_12.getWidth()) / 2;
                    int n9 = n7 - (dimension.height - (insets.top + insets.bottom) + akq_12.getHeight()) / 2;
                    this.a(n8, n9, akq_12.getWidth(), akq_12.getHeight(), akq_12);
                    continue;
                }
                this.a(nArray3[i2], nArray4[j], nArray[i2], nArray2[j], this.AD[j * 3 + i2]);
            }
        }
    }

    private void a(Dimension dimension, int n2, int n3, int n4, int n5) {
        if (this.aVo) {
            int n6 = dimension.width - n3 - n2;
            int n7 = dimension.height - n4 - n5;
            this.a(n2, dimension.height - n4, n6, n7, this.AD[4]);
        } else if (this.AD[4] != null) {
            int n8 = this.AD[4].getWidth();
            int n9 = this.AD[4].getHeight();
            int n10 = (dimension.width - n3 - n2 - n8) / 2;
            int n11 = (dimension.height - n4 - n5 - n9) / 2;
            this.a(n2 + n10, dimension.height - n4 - n11, n8, n9, this.AD[4]);
        }
    }
}

