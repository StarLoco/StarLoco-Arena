/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aBE
 */
public class abe_1 {
    protected static Logger a = Logger.getLogger(abe_1.class);
    public static final abe_1 dsk = null;
    public static final abe_1 dsl = new abe_1();
    private static final long dsm = 0L;
    private static final byte dsn = 0;
    private static final byte dso = 0;
    private static final byte dsp = 0;
    private long nD;
    private short UH;
    private byte UI;
    private byte cAH;
    private String dsq;

    public abe_1(long l2, short s, byte by, byte by2) {
        this.nD = l2;
        this.UH = s;
        this.UI = by;
        this.cAH = by2;
        this.dsq = "(" + this.nD + ", " + this.UH + ", " + this.UI + ", " + this.cAH + ")";
    }

    public abe_1(ajd_0 ajd_02) {
        this(ajd_02.getId(), ajd_02.tz(), ajd_02.tA(), ajd_02.azi());
    }

    public abe_1() {
        this(0L, 0, 0, 0);
    }

    public long getId() {
        return this.nD;
    }

    public short tz() {
        return this.UH;
    }

    public byte tA() {
        return this.UI;
    }

    public byte azi() {
        return this.cAH;
    }

    public String toString() {
        return this.dsq;
    }
}

