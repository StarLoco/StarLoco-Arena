/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.util.Vector;

public final class xb
extends aqk_0
implements gx_2 {
    private static final String avX = "regexp";
    private static final String vb = "negate";
    private Vector avY = new Vector();
    private String DV = null;
    private boolean DW = false;

    public xb() {
    }

    public xb(Reader reader) {
        super(reader);
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        int n2 = -1;
        if (this.DV != null) {
            n2 = this.DV.charAt(0);
            this.DV = this.DV.length() == 1 ? null : this.DV.substring(1);
        } else {
            int n3 = this.avY.size();
            this.DV = this.readLine();
            while (this.DV != null) {
                boolean bl2 = true;
                for (int j = 0; bl2 && j < n3; ++j) {
                    acy_0 acy_02 = (acy_0)this.avY.elementAt(j);
                    axk axk2 = acy_02.U(this.TP());
                    bl2 = axk2.matches(this.DV);
                }
                if (bl2 ^ this.oN()) break;
                this.DV = this.readLine();
            }
            if (this.DV != null) {
                return this.read();
            }
        }
        return n2;
    }

    public void a(acy_0 acy_02) {
        this.avY.addElement(acy_02);
    }

    private void b(Vector vector) {
        this.avY = vector;
    }

    private Vector Dx() {
        return this.avY;
    }

    public Reader b(Reader reader) {
        xb xb2 = new xb(reader);
        xb2.b(this.Dx());
        xb2.L(this.oN());
        return xb2;
    }

    public void L(boolean bl2) {
        this.DW = bl2;
    }

    public boolean oN() {
        return this.DW;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (avX.equals(vj_0Array[j].getType())) {
                    String string = vj_0Array[j].getValue();
                    acy_0 acy_02 = new acy_0();
                    acy_02.setPattern(string);
                    this.avY.addElement(acy_02);
                    continue;
                }
                if (!vb.equals(vj_0Array[j].getType())) continue;
                this.L(UI.gh(vj_0Array[j].getValue()));
            }
        }
    }
}

