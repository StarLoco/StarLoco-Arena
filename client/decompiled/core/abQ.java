/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Font;

public class abQ
implements ma_1 {
    Font ciH;
    boolean ciI;
    boolean ciJ;

    public abQ(Font font, boolean bl2, boolean bl3) {
        this.ciH = font;
        this.ciI = bl2;
        this.ciJ = bl3;
    }

    public ma_1 b(int n2, float f) {
        Font font = this.ciH.deriveFont(n2, f);
        return new abQ(font, this.isAntiAliased(), this.aD());
    }

    public float getSize() {
        return this.ciH.getSize2D();
    }

    public int getStyle() {
        int n2 = 0;
        if (this.ciH.isBold()) {
            n2 |= 1;
        }
        if (this.ciH.isItalic()) {
            n2 |= 2;
        }
        return n2;
    }

    public short qL() {
        return 0;
    }

    public final Font aqw() {
        return this.ciH;
    }

    public final boolean isAntiAliased() {
        return this.ciI;
    }

    public boolean aD() {
        return this.ciJ;
    }

    public int hashCode() {
        return (this.ciH.toString() + this.ciI + this.ciJ).hashCode();
    }

    public boolean equals(Object object) {
        return object instanceof abQ && this.hashCode() == object.hashCode();
    }
}

