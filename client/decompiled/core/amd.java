/*
 * Decompiled with CFR 0.152.
 */
public final class amd {
    private amd() {
    }

    public static int a(aGf aGf2, aGf aGf3) {
        return Math.max(0, amd.c(aGf2, aGf3) - 1);
    }

    public static int b(aGf aGf2, aGf aGf3) {
        return Math.abs(aGf2.gn() - aGf3.gn()) + Math.abs(aGf2.go() - aGf3.go());
    }

    public static int c(aGf aGf2, aGf aGf3) {
        int n2 = aGf2.ox() + aGf3.ox();
        int n3 = Math.max(0, Math.abs(aGf2.gn() - aGf3.gn()) - n2);
        int n4 = Math.max(0, Math.abs(aGf2.go() - aGf3.go()) - n2);
        return n3 + n4;
    }

    public static int a(aGf aGf2, ry ry2) {
        return Math.max(0, amd.c(aGf2, ry2) - 1);
    }

    public static int b(aGf aGf2, ry ry2) {
        return Math.abs(aGf2.gn() - ry2.getX()) + Math.abs(aGf2.go() - ry2.getY());
    }

    public static int c(aGf aGf2, ry ry2) {
        int n2 = Math.max(0, Math.abs(aGf2.gn() - ry2.getX()) - aGf2.ox());
        int n3 = Math.max(0, Math.abs(aGf2.go() - ry2.getY()) - aGf2.ox());
        return n2 + n3;
    }

    public static int a(aGf aGf2, int n2, int n3) {
        int n4 = Math.max(0, Math.abs(aGf2.gn() - n2) - aGf2.ox());
        int n5 = Math.max(0, Math.abs(aGf2.go() - n3) - aGf2.ox());
        return n4 + n5;
    }
}

