/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Dimension;
import java.awt.Insets;

public class awO
extends di_1 {
    public awO() {
        this.AD = new akq_1[1];
    }

    public akq_1 aJF() {
        return this.AD[0];
    }

    public void setPixmap(akq_1 akq_12) {
        this.AD[0] = akq_12;
        this.alD();
    }

    private void alD() {
        if (this.AD[0] == null) {
            this.AE = false;
            return;
        }
        this.AE = true;
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        this.AH.clear();
        if (this.AD[0] != null) {
            int n2;
            int n3 = this.AD[0].getWidth();
            int n4 = this.AD[0].getHeight();
            int n5 = dimension.width / n3 + (dimension.width % n3 > 0 ? 1 : 0);
            int n6 = dimension.height / n4 + (dimension.height % n4 > 0 ? 1 : 0);
            int n7 = insets.left;
            int n8 = n2 = dimension.height - insets.top;
            for (int j = 0; j < n6; ++j) {
                int n9 = n7;
                for (int i2 = 0; i2 < n5; ++i2) {
                    this.a(n9, n8, n3, n4, this.AD[0]);
                    n9 += n3;
                }
                n8 -= n4;
            }
        }
    }
}

