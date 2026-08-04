/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from azz
 */
public class azz_0
extends qs_0 {
    public static long dnT = 0L;

    public azz_0(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public long oS() {
        return dnT;
    }

    protected void ax() {
        try {
            adu_0 adu_02 = apN.aDK().aDL();
            if (adu_02 != null) {
                ee_2 ee_22 = (ee_2)adu_02.eg(this.Nl());
                if (ee_22 != null) {
                    adu_02.d(ee_22);
                } else {
                    a.error((Object)"D\u00e9but de tour demand\u00e9 pour un fighter inexistant ??");
                }
            }
        }
        catch (Exception exception) {
            a.error((Object)"Error : ", (Throwable)exception);
        }
        dnT = 0L;
    }
}

