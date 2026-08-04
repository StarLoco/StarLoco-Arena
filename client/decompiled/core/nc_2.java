/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nc
 */
public class nc_2
implements Comparable {
    private int NB;
    private short NC;
    private boolean ND;
    private static nc_2 NE = new nc_2(0, -1, false);

    public nc_2(int n2, short s, boolean bl2) {
        this.ND = bl2;
        this.NB = n2;
        this.NC = s;
    }

    public static nc_2 rW() {
        return NE;
    }

    public int a(nc_2 nc_22) {
        if (this == nc_22) {
            return 0;
        }
        if (nc_22 == null) {
            return -1;
        }
        int n2 = Integer.signum(this.NC - nc_22.NC);
        if (n2 != 0) {
            return n2;
        }
        int n3 = Integer.signum(this.NB - nc_22.NB);
        if (n3 != 0) {
            return n3;
        }
        return (this.ND ? 1 : 0) - (nc_22.ND ? 1 : 0);
    }
}

