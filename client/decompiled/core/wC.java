/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;

public class wC
extends aFH {
    private String IJ;
    private char[] avc;
    private int avd;
    private af_1 lY = null;
    private vg_2 ave = null;
    private Color avf = null;
    private boolean avg = false;
    private boolean avh = false;
    private boolean avi = true;
    private boolean avj = true;

    public wC() {
        this.a(nf_2.NN);
    }

    public int CX() {
        return this.IJ.length();
    }

    public String getText() {
        return this.IJ;
    }

    public char[] CY() {
        return this.avc;
    }

    public void setText(String string) {
        this.IJ = string;
        this.avc = (char[])(string != null ? this.IJ.toCharArray() : null);
    }

    public adv_0 CZ() {
        return (adv_0)this.dHz;
    }

    public af_1 Da() {
        if (this.lY == null && this.CZ() != null) {
            return this.CZ().Da();
        }
        return this.lY;
    }

    public void b(af_1 af_12) {
        this.lY = af_12;
    }

    public vg_2 Db() {
        return this.ave;
    }

    public void a(vg_2 vg_22) {
        this.ave = vg_22;
    }

    public int Dc() {
        return this.avd;
    }

    public void ec(int n2) {
        this.avd = n2;
    }

    public Color getColor() {
        if (this.avf == null && this.CZ() != null) {
            return this.CZ().getColor();
        }
        return this.avf;
    }

    public void setColor(Color color) {
        this.avf = color;
    }

    public boolean isUnderline() {
        if (this.avi && this.CZ() != null) {
            return this.CZ().isUnderline();
        }
        return this.avg;
    }

    public void setUnderline(boolean bl2) {
        this.avg = bl2;
        this.avi = false;
    }

    public boolean Dd() {
        if (this.avj && this.CZ() != null) {
            return this.CZ().Dd();
        }
        return this.avh;
    }

    public void aR(boolean bl2) {
        this.avh = bl2;
        this.avj = false;
    }

    public int a(af_1 af_12, int n2) {
        double d = 0.0;
        af_1 af_13 = this.Da();
        if (af_13 == null && af_12 != null) {
            af_13 = af_12;
        }
        if (af_13 != null) {
            String string = this.IJ;
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                int n3 = af_13.a(c);
                if (d + (double)n3 >= (double)n2) {
                    return j;
                }
                d += (double)n3;
            }
        }
        return -1;
    }

    public int b(af_1 af_12, int n2) {
        String string = this.IJ.substring(0, n2);
        af_1 af_13 = this.Da();
        if (af_13 == null && af_12 != null) {
            af_13 = af_12;
        }
        if (af_13 != null) {
            return af_13.g(string);
        }
        return 0;
    }

    public int c(af_1 af_12, int n2) {
        String string = this.IJ.substring(0, n2);
        af_1 af_13 = this.Da();
        if (af_13 == null && af_12 != null) {
            af_13 = af_12;
        }
        if (af_13 != null) {
            return af_13.g(string);
        }
        return 0;
    }
}

