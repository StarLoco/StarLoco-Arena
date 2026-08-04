/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from Ak
 */
class ak_1
implements Comparator {
    public static ak_1 aGS = new ak_1();

    private ak_1() {
    }

    public int a(rA rA2, rA rA3) {
        return (int)(rA3.getHeight() * rA3.getWidth() - rA2.getHeight() * rA2.getWidth());
    }
}

