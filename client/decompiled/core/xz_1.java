/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from xz
 */
public final class xz_1
extends aqk_0
implements gx_2 {
    private static final String ayT = "lines";
    private static final String ayU = "skip";
    private long ayV = 0L;
    private static final int ayW = 10;
    private long ayX = 10L;
    private long ayY = 0L;
    private ia_0 ayZ = null;
    private String DV = null;
    private int anr = 0;

    public xz_1() {
    }

    public xz_1(Reader reader) {
        super(reader);
        this.ayZ = new ia_0();
        this.ayZ.x(true);
    }

    public int read() {
        if (!this.aCg()) {
            this.initialize();
            this.bk(true);
        }
        while (this.DV == null || this.DV.length() == 0) {
            this.DV = this.ayZ.a(this.in);
            if (this.DV == null) {
                return -1;
            }
            this.DV = this.cR(this.DV);
            this.anr = 0;
        }
        char c = this.DV.charAt(this.anr);
        ++this.anr;
        if (this.anr == this.DV.length()) {
            this.DV = null;
        }
        return c;
    }

    public void aU(long l2) {
        this.ayX = l2;
    }

    private long En() {
        return this.ayX;
    }

    public void aV(long l2) {
        this.ayY = l2;
    }

    private long Eo() {
        return this.ayY;
    }

    public Reader b(Reader reader) {
        xz_1 xz_12 = new xz_1(reader);
        xz_12.aU(this.En());
        xz_12.aV(this.Eo());
        xz_12.bk(true);
        return xz_12;
    }

    private void initialize() {
        vj_0[] vj_0Array = this.JT();
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                if (ayT.equals(vj_0Array[j].getName())) {
                    this.ayX = Long.parseLong(vj_0Array[j].getValue());
                    continue;
                }
                if (!ayU.equals(vj_0Array[j].getName())) continue;
                this.ayY = Long.parseLong(vj_0Array[j].getValue());
            }
        }
    }

    private String cR(String string) {
        ++this.ayV;
        if (this.ayY > 0L && this.ayV - 1L < this.ayY) {
            return null;
        }
        if (this.ayX > 0L && this.ayV > this.ayX + this.ayY) {
            return null;
        }
        return string;
    }
}

