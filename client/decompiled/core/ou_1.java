/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from OU
 */
public final class ou_1 {
    protected static final Logger a = Logger.getLogger(ou_1.class);

    public static int he(int n2) {
        if (n2 <= 0) {
            a.error((Object)("DiceRoll.roll appel\u00e9 avec une valeur de d\u00e9 de " + n2 + "\n" + bl_0.b(new RuntimeException("StackTrace de DiceRoll"))));
            return 1;
        }
        return jr_0.VF().nextInt(n2) + 1;
    }

    public static long ck(long l2) {
        if (l2 <= 0L) {
            a.error((Object)("DiceRoll.roll appel\u00e9 avec une valeur de d\u00e9 de " + l2 + "\n" + bl_0.b(new RuntimeException("StackTrace de DiceRoll"))));
            return 1L;
        }
        return jr_0.VF().bS(l2) + 1L;
    }

    public static int A(int n2, int n3, int n4) {
        if (n3 <= 0) {
            a.error((Object)("DiceRoll.roll appel\u00e9 avec une valeur de d\u00e9 de " + n3 + "\n" + bl_0.b(new RuntimeException("StackTrace de DiceRoll"))));
            return 1;
        }
        int n5 = n4 + n2;
        if (n3 > 0 && n2 > 0) {
            for (int j = n2; j > 0; --j) {
                n5 += jr_0.VF().nextInt(n3);
            }
        }
        return n5;
    }

    public static int aE(int n2, int n3) {
        assert (n2 > 0);
        assert (n3 >= n2);
        int n4 = n2;
        if (n2 > 0 && n3 > 0 && n3 - n2 > 0) {
            n4 = n2 + jr_0.VF().nextInt(n3 - n2 + 1);
        }
        return n4;
    }
}

