/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

public final class aFF
extends aqk_0
implements gx_2 {
    private static final String dHx = "prefix";
    private String prefix = null;
    private String OA = null;

    public aFF() {
    }

    public aFF(Reader reader) {
        super(reader);
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        int n2 = -1;
        if (this.OA != null && this.OA.length() == 0) {
            this.OA = null;
        }
        if (this.OA != null) {
            n2 = this.OA.charAt(0);
            this.OA = this.OA.substring(1);
            if (this.OA.length() == 0) {
                this.OA = null;
            }
        } else {
            this.OA = this.readLine();
            if (this.OA == null) {
                n2 = -1;
            } else {
                if (this.prefix != null) {
                    this.OA = this.prefix + this.OA;
                }
                return this.read();
            }
        }
        return n2;
    }

    public void setPrefix(String string) {
        this.prefix = string;
    }

    private String getPrefix() {
        return this.prefix;
    }

    public Reader b(Reader reader) {
        aFF aFF2 = new aFF(reader);
        aFF2.setPrefix(this.getPrefix());
        aFF2.bk(true);
        return aFF2;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (!dHx.equals(vj_0Array[j].getName())) continue;
                this.prefix = vj_0Array[j].getValue();
                break;
            }
        }
    }
}

