/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.util.Vector;

/*
 * Renamed from VD
 */
public final class vd_0
extends aqk_0
implements gx_2 {
    private static final String bSU = "comment";
    private Vector bSV = new Vector();
    private String DV = null;

    public vd_0() {
    }

    public vd_0(Reader reader) {
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
            this.DV = this.readLine();
            int n3 = this.bSV.size();
            while (this.DV != null) {
                for (int j = 0; j < n3; ++j) {
                    String string = (String)this.bSV.elementAt(j);
                    if (!this.DV.startsWith(string)) continue;
                    this.DV = null;
                    break;
                }
                if (this.DV != null) break;
                this.DV = this.readLine();
            }
            if (this.DV != null) {
                return this.read();
            }
        }
        return n2;
    }

    public void a(apr apr2) {
        this.bSV.addElement(apr2.getValue());
    }

    private void g(Vector vector) {
        this.bSV = vector;
    }

    private Vector aiI() {
        return this.bSV;
    }

    public Reader b(Reader reader) {
        vd_0 vd_02 = new vd_0(reader);
        vd_02.g(this.aiI());
        vd_02.bk(true);
        return vd_02;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (!bSU.equals(vj_0Array[j].getType())) continue;
                this.bSV.addElement(vj_0Array[j].getValue());
            }
        }
    }
}

