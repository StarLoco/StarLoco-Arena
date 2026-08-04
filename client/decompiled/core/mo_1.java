/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from mo
 */
public class mo_1 {
    public static final short JG = Short.MIN_VALUE;
    protected static final Logger a = Logger.getLogger(mo_1.class);

    private mo_1() {
    }

    public static short a(int n2, int n3, akd_0[] akd_0Array, short s, int n4) {
        if (akd_0Array == null) {
            throw new IllegalArgumentException("Argument 2 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/world/topology/TopologyChecker.getHighestWalkableZ must not be null");
        }
        if (n4 <= 0) {
            a.error((Object)"no moverHeight defined");
        }
        if (n3 <= 0) {
            a.error((Object)"can't get highest height : no data for this element");
        }
        if (n2 + n3 > akd_0Array.length) {
            a.error((Object)"cell elements index and count are out of bounds");
        }
        if (n3 == 1) {
            akd_0 akd_02 = akd_0Array[n2];
            if (akd_02.cCI) {
                a.error((Object)"data invalid : we can move through, but this element is the only one");
            }
            if (akd_02.cCJ == -1) {
                return Short.MIN_VALUE;
            }
            if (akd_02.wp > s) {
                return Short.MIN_VALUE;
            }
            return akd_02.wp;
        }
        int n5 = Short.MAX_VALUE;
        for (int j = n2 + n3 - 1; j >= n2; --j) {
            akd_0 akd_03 = akd_0Array[j];
            if (akd_03.cCI) continue;
            if (akd_03.cCJ == -1) {
                n5 = (short)(akd_03.wp - akd_03.aba);
                continue;
            }
            if (akd_03.wp > s) {
                n5 = (short)(akd_03.wp - akd_03.aba);
                continue;
            }
            if (n4 > n5 - akd_03.wp) {
                n5 = (short)(akd_03.wp - akd_03.aba);
                continue;
            }
            return akd_03.wp;
        }
        return Short.MIN_VALUE;
    }

    public static boolean a(int n2, int n3, int n4, akd_0[] akd_0Array, int n5) {
        assert (n5 > 0) : "no moverHeight defined";
        assert (akd_0Array != null) : "cellData can't be null";
        assert (n3 >= 0 && n4 > 0 && n3 + n4 <= akd_0Array.length) : "invalid bounds : [" + n3 + ", " + (n3 + n4) + "]";
        assert (n2 >= n3 && n2 < n3 + n4) : "moverZIndex not within the given bounds";
        if (akd_0Array[n2].cCJ == -1 || akd_0Array[n2].cCI) {
            return false;
        }
        if (n2 == n3 + n4 - 1 && !akd_0Array[n2].cCI) {
            return true;
        }
        int n6 = akd_0Array[n2].wp + n5;
        for (int j = n2 + 1; j < n3 + n4; ++j) {
            akd_0 akd_02 = akd_0Array[j];
            int n7 = akd_02.wp - akd_02.aba;
            if (n7 >= n6) {
                return true;
            }
            if (akd_02.cCI) continue;
            return false;
        }
        return true;
    }

    public static short a(int n2, int n3, akd_0[] akd_0Array, short s) {
        assert (n3 > 0) : "can't get index from z : no data for this element";
        assert (akd_0Array != null) : "can't get index from z : no data array provided";
        assert (n2 + n3 <= akd_0Array.length) : "cell elements index and count are out of bounds";
        for (int j = n2 + n3 - 1; j >= n2; --j) {
            if (akd_0Array[j].wp != s) continue;
            return (short)(j - n2);
        }
        return Short.MIN_VALUE;
    }
}

