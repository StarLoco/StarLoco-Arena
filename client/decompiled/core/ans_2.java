/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from anS
 */
public class ans_2 {
    protected static Logger a = Logger.getLogger(ans_2.class);
    private boolean aK;
    private byte[] cKa;
    private GV cKb;

    public ans_2(GV gV) {
        this.cKb = gV;
        this.aK = false;
    }

    public boolean initialize() {
        int n2;
        long l2 = this.cKb.Sq();
        if (l2 > Integer.MAX_VALUE) {
            throw new UnsupportedOperationException("Impossible de charger un son de plus de 2147483647 octets");
        }
        this.cKa = new byte[(int)l2];
        int n3 = 0;
        do {
            n2 = this.cKb.a(this.cKa, n3);
            n3 += Math.abs(n2);
        } while (n2 > 0);
        this.cKb.close();
        this.aK = true;
        return true;
    }

    public void close() {
        this.cKb.close();
        this.cKa = null;
        this.cKb = null;
        this.aK = false;
    }

    public GV aCz() {
        return this.cKb;
    }

    public byte[] aCA() {
        return this.cKa;
    }

    public int aCB() {
        return this.cKa.length;
    }

    public boolean isInitialized() {
        return this.aK;
    }
}

