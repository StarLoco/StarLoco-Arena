/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from wo
 */
public final class wo_2
extends aqk_0
implements gx_2 {
    private static final String auu = "\r\n";
    private static final String auv = "linebreaks";
    private String auw = "\r\n";

    public wo_2() {
    }

    public wo_2(Reader reader) {
        super(reader);
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        int n2 = this.in.read();
        while (n2 != -1 && this.auw.indexOf(n2) != -1) {
            n2 = this.in.read();
        }
        return n2;
    }

    public void cD(String string) {
        this.auw = string;
    }

    private String CG() {
        return this.auw;
    }

    public Reader b(Reader reader) {
        wo_2 wo_22 = new wo_2(reader);
        wo_22.cD(this.CG());
        wo_22.bk(true);
        return wo_22;
    }

    private void initialize() {
        String string = null;
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (!auv.equals(vj_0Array[j].getName())) continue;
                string = vj_0Array[j].getValue();
                break;
            }
        }
        if (string != null) {
            this.auw = string;
        }
    }
}

