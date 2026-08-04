/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;

/*
 * Renamed from kA
 */
public final class ka_1 {
    private static final Random Ey = new Random();

    public static int bJ(int n2) {
        if (n2 <= 1) {
            return 1;
        }
        if (n2 >= 2 && n2 <= 10) {
            return n2 * 10;
        }
        if (n2 >= 11 && n2 <= 19) {
            return 200 + (n2 - 11) * 400;
        }
        if (n2 == 20) {
            return 4000;
        }
        if (n2 >= 21 && n2 <= 29) {
            return 5000 + (n2 - 21) * 3000;
        }
        if (n2 >= 30 && n2 <= 50) {
            return 30000 + (Math.min(50, n2) - 30) * 1000;
        }
        return 50001;
    }

    public static int bK(int n2) {
        if (n2 <= 0) {
            return 0;
        }
        if (n2 >= 50000) {
            return 50;
        }
        if (n2 < 10) {
            return 1;
        }
        if (n2 < 100) {
            return n2 / 10;
        }
        if (n2 < 200) {
            return 10;
        }
        if (n2 < 3400) {
            return (n2 - 200) / 400 + 11;
        }
        if (n2 < 4000) {
            return 19;
        }
        if (n2 < 29000) {
            return (n2 - 2000) / 3000 + 20;
        }
        return n2 / 1000;
    }

    public static int bL(int n2) {
        if (n2 < 2) {
            return 0;
        }
        return 1 + (Ey.nextInt(100) <= Math.min(100, (n2 - 2) * 10) ? 1 : 0);
    }

    public static int E(int n2, int n3) {
        int n4 = 20 + n2 * 2 + n3;
        return Ey.nextInt(100) <= n4 ? 1 : 0;
    }

    public static boolean bM(int n2) {
        boolean bl2 = false;
        if (n2 >= 2) {
            bl2 = Ey.nextInt(100) <= Math.min(25, n2 - 1);
        }
        return bl2;
    }
}

