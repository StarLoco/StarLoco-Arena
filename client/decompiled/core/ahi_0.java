/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from aHi
 */
public class ahi_0
extends jy_1 {
    private akq_1[] AD = new akq_1[16];
    private boolean AE;

    public void a(akq_1 akq_12, ajn_1 ajn_12) {
        switch (ajn_12) {
            case dSm: {
                this.AD[0] = akq_12;
                break;
            }
            case dSn: {
                this.AD[1] = akq_12;
                break;
            }
            case dSo: {
                this.AD[2] = akq_12;
                break;
            }
            case dSp: {
                this.AD[3] = akq_12;
                break;
            }
            case dSq: {
                this.AD[4] = akq_12;
                break;
            }
            case dSr: {
                this.AD[5] = akq_12;
                break;
            }
            case dSs: {
                this.AD[6] = akq_12;
                break;
            }
            case dSt: {
                this.AD[7] = akq_12;
                break;
            }
            case dSv: {
                this.AD[8] = akq_12;
                break;
            }
            case dSw: {
                this.AD[9] = akq_12;
                break;
            }
            case dSx: {
                this.AD[10] = akq_12;
                break;
            }
            case dSy: {
                this.AD[11] = akq_12;
                break;
            }
            case dSz: {
                this.AD[12] = akq_12;
                break;
            }
            case dSA: {
                this.AD[13] = akq_12;
                break;
            }
            case dSB: {
                this.AD[14] = akq_12;
                break;
            }
            case dSC: {
                this.AD[15] = akq_12;
            }
        }
        this.alD();
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110, akq_1 akq_111, akq_1 akq_112, akq_1 akq_113, akq_1 akq_114, akq_1 akq_115, akq_1 akq_116, akq_1 akq_117) {
        this.AD[0] = akq_12;
        this.AD[1] = akq_13;
        this.AD[2] = akq_14;
        this.AD[3] = akq_15;
        this.AD[4] = akq_16;
        this.AD[5] = akq_17;
        this.AD[6] = akq_18;
        this.AD[7] = akq_19;
        this.AD[8] = akq_110;
        this.AD[9] = akq_111;
        this.AD[10] = akq_112;
        this.AD[11] = akq_113;
        this.AD[12] = akq_114;
        this.AD[13] = akq_115;
        this.AD[14] = akq_116;
        this.AD[15] = akq_117;
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
        this.AD[9] = akq_1Array[9];
        this.AD[10] = akq_1Array[10];
        this.AD[11] = akq_1Array[11];
        this.AD[12] = akq_1Array[12];
        this.AD[13] = akq_1Array[13];
        this.AD[14] = akq_1Array[14];
        this.AD[15] = akq_1Array[15];
        this.alD();
    }

    public final boolean Gk() {
        return this.AE;
    }

    public void a(Insets insets) {
        if (insets != null) {
            insets.top = Math.max(this.AD[0].getHeight(), Math.max(this.AD[2].getHeight(), Math.max(this.AD[4].getHeight(), Math.max(this.AD[1].getHeight(), this.AD[3].getHeight()))));
            insets.bottom = Math.max(this.AD[11].getHeight(), Math.max(this.AD[13].getHeight(), Math.max(this.AD[15].getHeight(), Math.max(this.AD[12].getHeight(), this.AD[14].getHeight()))));
            insets.left = Math.max(this.AD[0].getWidth(), Math.max(this.AD[7].getWidth(), Math.max(this.AD[11].getWidth(), Math.max(this.AD[5].getWidth(), this.AD[9].getWidth()))));
            insets.right = Math.max(this.AD[4].getWidth(), Math.max(this.AD[8].getWidth(), Math.max(this.AD[15].getWidth(), Math.max(this.AD[6].getWidth(), this.AD[10].getWidth()))));
        }
    }

    public final void j() {
        this.AH.HF();
        this.AH = null;
    }

    public final void b() {
        this.alD();
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        if (!this.AE) {
            return;
        }
        this.AH.clear();
        for (int j = 0; j < this.AD.length; ++j) {
            int n2 = this.a(dimension, insets, insets2, j);
            int n3 = this.b(dimension, insets, insets2, j);
            int n4 = this.c(dimension, insets, insets2, j);
            int n5 = this.d(dimension, insets, insets2, j);
            this.a(n2, n3, n4, n5, this.AD[j]);
        }
    }

    private int a(Dimension dimension, Insets insets, Insets insets2, int n2) {
        if (n2 == 0 || n2 == 5 || n2 == 7 || n2 == 9 || n2 == 11) {
            return insets.left;
        }
        if (n2 == 1 || n2 == 12) {
            return insets.left + insets2.left;
        }
        if (n2 == 2 || n2 == 13) {
            return insets.left + insets2.left + this.AD[n2 - 1].getWidth();
        }
        if (n2 == 3 || n2 == 14) {
            return dimension.width - insets.left - insets.right - insets2.right - this.AD[n2].getWidth();
        }
        if (n2 == 4 || n2 == 6 || n2 == 8 || n2 == 10 || n2 == 15) {
            return dimension.width - insets.left - insets.right - insets2.right;
        }
        assert (false) : "We should never end here";
        return 0;
    }

    private int b(Dimension dimension, Insets insets, Insets insets2, int n2) {
        if (n2 == 0 || n2 == 1 || n2 == 2 || n2 == 3 || n2 == 4) {
            return dimension.height - insets.top;
        }
        if (n2 == 5 || n2 == 6) {
            return dimension.height - (insets.top + insets2.top);
        }
        if (n2 == 7 || n2 == 8) {
            return dimension.height - (insets.top + insets2.top + this.AD[n2 - 2].getHeight());
        }
        if (n2 == 9 || n2 == 10) {
            return insets.bottom + insets2.bottom + this.AD[n2].getHeight();
        }
        if (n2 == 11 || n2 == 12 || n2 == 13 || n2 == 14 || n2 == 15) {
            return insets.bottom + insets2.bottom;
        }
        assert (false) : "We should never end here";
        return 0;
    }

    private int c(Dimension dimension, Insets insets, Insets insets2, int n2) {
        if (n2 == 0 || n2 == 5 || n2 == 7 || n2 == 9 || n2 == 11) {
            return insets2.left;
        }
        if (n2 == 1 || n2 == 12) {
            return this.AD[n2].getWidth();
        }
        if (n2 == 2) {
            return dimension.width - insets.left - insets.right - insets2.left - insets2.right - this.AD[1].getWidth() - this.AD[3].getWidth();
        }
        if (n2 == 13) {
            return dimension.width - insets.left - insets.right - insets2.left - insets2.right - this.AD[12].getWidth() - this.AD[14].getWidth();
        }
        if (n2 == 3 || n2 == 14) {
            return this.AD[n2].getWidth();
        }
        if (n2 == 4 || n2 == 6 || n2 == 8 || n2 == 10 || n2 == 15) {
            return insets2.right;
        }
        assert (false) : "We should never end here";
        return 0;
    }

    private int d(Dimension dimension, Insets insets, Insets insets2, int n2) {
        if (n2 == 0 || n2 == 1 || n2 == 2 || n2 == 3 || n2 == 4) {
            return insets2.top;
        }
        if (n2 == 5 || n2 == 6) {
            return this.AD[n2].getHeight();
        }
        if (n2 == 7) {
            return dimension.height - insets.top - insets.bottom - insets2.top - insets2.bottom - this.AD[5].getHeight() - this.AD[9].getHeight();
        }
        if (n2 == 8) {
            return dimension.height - insets.top - insets.bottom - insets2.top - insets2.bottom - this.AD[6].getHeight() - this.AD[10].getHeight();
        }
        if (n2 == 9 || n2 == 10) {
            return this.AD[n2].getHeight();
        }
        if (n2 == 11 || n2 == 12 || n2 == 13 || n2 == 14 || n2 == 15) {
            return insets2.bottom;
        }
        assert (false) : "We should never end here";
        return 0;
    }

    private void alD() {
        for (int j = this.AD.length - 1; j >= 0; --j) {
            if (this.AD[j] != null) continue;
            this.AE = false;
            return;
        }
        this.AE = true;
    }
}

