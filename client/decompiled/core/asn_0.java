/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from asN
 */
public class asn_0 {
    public static final Logger a = Logger.getLogger(asn_0.class);
    private final int fb;
    private final int fc;
    private final int cSt;
    private final int cSu;
    private final bm_2 cSv;
    private static final String cSw = "x";

    public asn_0(int n2, int n3, int n4, int n5, bm_2 bm_22) {
        this.fb = n2;
        this.fc = n3;
        this.cSt = n4;
        this.cSu = n5;
        this.cSv = bm_22;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public int aFJ() {
        return this.cSt;
    }

    public int getFrequency() {
        return this.cSu;
    }

    public bm_2 aFK() {
        return this.cSv;
    }

    public String toString() {
        return "{Resolution : " + this.fb + 'x' + this.fc + 'x' + this.cSt + ' ' + aey_0.capitalize(this.cSv.name()) + ' ' + this.cSu + "Hz}";
    }

    public boolean aFL() {
        return this.cSv != bm_2.aJp && (this.fb <= 0 || this.fc <= 0);
    }

    public boolean equals(Object object) {
        if (object instanceof asn_0) {
            asn_0 asn_02 = (asn_0)object;
            return asn_02.fb == this.fb && asn_02.fc == this.fc && asn_02.cSv == this.cSv && asn_02.cSu == this.cSu && (asn_02.cSt == this.cSt || asn_02.cSt == -1 || this.cSt == -1);
        }
        return super.equals(object);
    }

    public static asn_0 jy(String string) {
        if (string == null) {
            string = "";
        }
        String[] stringArray = string.split(cSw);
        int n2 = asn_0.a(stringArray, 0, 0);
        int n3 = asn_0.a(stringArray, 1, 0);
        int n4 = asn_0.a(stringArray, 2, -1);
        int n5 = asn_0.a(stringArray, 3, 0);
        int n6 = asn_0.a(stringArray, 4, bm_2.aJo.ordinal());
        bm_2 bm_22 = bm_2.aJo;
        for (bm_2 bm_23 : bm_2.values()) {
            if (bm_23.ordinal() != n6) continue;
            bm_22 = bm_23;
            break;
        }
        return new asn_0(n2, n3, n4, n5, bm_22);
    }

    private static int a(String[] stringArray, int n2, int n3) {
        if (stringArray == null || n2 >= stringArray.length) {
            return n3;
        }
        try {
            return Integer.parseInt(stringArray[n2]);
        }
        catch (NumberFormatException numberFormatException) {
            return n3;
        }
    }

    public final String Fk() {
        return this.fb + cSw + this.fc + cSw + this.cSt + cSw + this.cSu + cSw + this.cSv.ordinal();
    }
}

