/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from CD
 */
public final class cd_0
extends aqk_0
implements gx_2 {
    private static final int aMb = 8;
    private static final String aMc = "tablength";
    private int aMd = 8;
    private int aMe = 0;

    public cd_0() {
    }

    public cd_0(Reader reader) {
        super(reader);
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        int n2 = -1;
        if (this.aMe > 0) {
            --this.aMe;
            n2 = 32;
        } else {
            n2 = this.in.read();
            if (n2 == 9) {
                this.aMe = this.aMd - 1;
                n2 = 32;
            }
        }
        return n2;
    }

    public void eR(int n2) {
        this.aMd = n2;
    }

    private int Kn() {
        return this.aMd;
    }

    public Reader b(Reader reader) {
        cd_0 cd_02 = new cd_0(reader);
        cd_02.eR(this.Kn());
        cd_02.bk(true);
        return cd_02;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (vj_0Array[j] == null || !aMc.equals(vj_0Array[j].getName())) continue;
                this.aMd = Integer.parseInt(vj_0Array[j].getValue());
                break;
            }
        }
    }
}

