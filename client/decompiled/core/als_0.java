/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from als
 */
public class als_0
extends jw_1 {
    private static final Logger a = Logger.getLogger(als_0.class);
    private String[] cFj;
    private String[] cFk;
    private String cFl;

    public final boolean a(arp_0 arp_02) {
        String string = arp_02.aEX();
        for (int j = 0; j < this.cFj.length; ++j) {
            if (!string.endsWith(this.cFj[j])) continue;
            return arp_02.aY(this.cFk[j]);
        }
        return arp_02.aY(this.cFl);
    }

    public final void a(byte by, acf acf2) {
        int n2 = (by - 1) / 2;
        this.cFj = new String[n2];
        this.cFk = new String[n2];
        for (int j = 0; j < n2; ++j) {
            this.cFj[j] = acf2.readString();
            this.cFk[j] = acf2.readString();
        }
        if (by % 2 == 1) {
            this.cFl = acf2.readString();
        }
    }

    public void a(aij_1 aij_12) {
    }

    public int getSize() {
        int n2 = 2;
        for (int j = 0; j < this.cFj.length; ++j) {
            n2 += this.cFj[j].length();
            n2 += this.cFk[j].length();
        }
        return (n2 += this.cFl.length()) + super.getSize();
    }

    public aro ek() {
        return aro.cPz;
    }
}

