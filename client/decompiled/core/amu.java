/*
 * Decompiled with CFR 0.152.
 */
public class amu {
    public static final int cGU = 0;
    public static final int cGV = 1;
    public static final int cGW = 2;
    public static final int cGX = 3;
    public static final int cGY = 4;
    public static final int cGZ = 5;
    public static final int cHa = 6;
    public static final int cHb = 7;
    public static final String[] cHc = new String[]{"UNKNOWN_EVENT", "BROADCAST_EVENT", "MAINTENANCE_EVENT", "TOURNAMENT_BEGIN", "TOURNAMENT_END", "TOURNAMENT_BEGIN_PERIOD", "TOURNAMENT_END_PERIOD", "TOURNAMENT_ANNOUNCEMENT"};
    public static final int cHd = 1;
    public static final int cHe = 2;
    public static final int cHf = 3;

    public static int lu(int n2) {
        switch (n2) {
            case 1: {
                return 1;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                return 2;
            }
            case 2: {
                return 3;
            }
        }
        return 0;
    }

    public static String toString(int n2) {
        return cHc[n2 < 0 || cHc.length <= n2 ? 0 : n2];
    }
}

