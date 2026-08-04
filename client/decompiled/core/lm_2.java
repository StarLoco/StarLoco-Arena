/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from lm
 */
class lm_2
implements Comparator {
    public static final lm_2 GU = new lm_2();

    private lm_2() {
    }

    public int a(air_1 air_12, air_1 air_13) {
        return air_13.getTreeDepth() - air_12.getTreeDepth();
    }
}

