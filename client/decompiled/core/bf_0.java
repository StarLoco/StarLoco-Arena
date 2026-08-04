/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from BF
 */
public class bf_0
extends jw_1 {
    protected static Logger a = Logger.getLogger(bf_0.class);
    private String[] aJg;
    private byte[] aJh;
    private String[] aJi;

    public boolean a(arp_0 arp_02) {
        if (this.aJg == null) {
            return false;
        }
        int n2 = ej_0.am(100);
        for (int j = 0; j < this.aJg.length; ++j) {
            if (this.aJh[j] < n2 || this.aJg[j] == null) continue;
            if (this.aJg[j].length() == 0) break;
            return arp_02.aY(this.aJg[j]);
        }
        return false;
    }

    public void a(byte n2, acf acf2) {
        this.aJi = new String[n2];
        for (int j = 0; j < n2; ++j) {
            this.aJi[j] = acf2.readString();
        }
        this.aJg = new String[n2];
        this.aJh = new byte[n2];
        if (!this.a(this.aJi, this.aJg, this.aJh)) {
            this.aJg = null;
            this.aJh = null;
        }
    }

    public void a(aij_1 aij_12) {
    }

    public int getSize() {
        int n2 = 2;
        for (int j = 0; j < this.aJi.length; ++j) {
            n2 += this.aJi[j].length();
        }
        return super.getSize() + n2;
    }

    private boolean a(String[] stringArray, String[] stringArray2, byte[] byArray) {
        int n2;
        boolean bl2 = false;
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            String string = stringArray[n2];
            try {
                byte by = Byte.parseByte(string.trim());
                if (!bl2) {
                    a.error((Object)"deux pourcentage se suivent ");
                    return false;
                }
                if (by <= 0 || by >= 100 - stringArray.length / 2) {
                    a.error((Object)("pourcentage incorrect " + by));
                    return false;
                }
                byArray[n2 - 1] = by;
                n3 += by;
                --n4;
                bl2 = false;
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                ++n4;
                stringArray2[n2] = string;
                bl2 = true;
            }
        }
        n2 = (byte)((100 - n3) / n4);
        n3 = 0;
        for (int j = 0; j < stringArray.length - 1; ++j) {
            if (stringArray2[j] == null) continue;
            if (byArray[j] == 0) {
                byArray[j] = n2;
            }
            int n5 = j;
            byArray[n5] = (byte)(byArray[n5] + n3);
            n3 = byArray[j];
        }
        byArray[stringArray.length - 1] = 101;
        return true;
    }

    public aro ek() {
        return aro.cPv;
    }
}

