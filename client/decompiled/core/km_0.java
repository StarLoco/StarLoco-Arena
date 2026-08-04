/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.util.Vector;

/*
 * Renamed from km
 */
public final class km_0
extends aqk_0
implements gx_2 {
    private static final String DT = "contains";
    private static final String vb = "negate";
    private Vector DU = new Vector();
    private String DV = null;
    private boolean DW = false;

    public km_0() {
    }

    public km_0(Reader reader) {
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
            int n3 = this.DU.size();
            this.DV = this.readLine();
            while (this.DV != null) {
                boolean bl2 = true;
                for (int j = 0; bl2 && j < n3; ++j) {
                    String string = (String)this.DU.elementAt(j);
                    bl2 = this.DV.indexOf(string) >= 0;
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

    public void a(agp_0 agp_02) {
        this.DU.addElement(agp_02.getValue());
    }

    public void L(boolean bl2) {
        this.DW = bl2;
    }

    public boolean oN() {
        return this.DW;
    }

    private void a(Vector vector) {
        this.DU = vector;
    }

    private Vector oO() {
        return this.DU;
    }

    public Reader b(Reader reader) {
        km_0 km_02 = new km_0(reader);
        km_02.a(this.oO());
        km_02.L(this.oN());
        return km_02;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (DT.equals(vj_0Array[j].getType())) {
                    this.DU.addElement(vj_0Array[j].getValue());
                    continue;
                }
                if (!vb.equals(vj_0Array[j].getType())) continue;
                this.L(UI.gh(vj_0Array[j].getValue()));
            }
        }
    }
}

