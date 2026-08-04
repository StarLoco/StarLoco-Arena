/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Gw
 */
public final class gw_1
extends atu_0 {
    public int index;
    public static final int VOID = 0;
    public static final int bbd = 1;
    public static final int SHORT = 2;
    public static final int CHAR = 3;
    public static final int INT = 4;
    public static final int LONG = 5;
    public static final int FLOAT = 6;
    public static final int DOUBLE = 7;
    public static final int BOOLEAN = 8;

    public gw_1(lc_0 lc_02, int n2) {
        super(lc_02);
        this.index = n2;
    }

    public String toString() {
        switch (this.index) {
            case 0: {
                return "void";
            }
            case 1: {
                return "byte";
            }
            case 2: {
                return "short";
            }
            case 3: {
                return "char";
            }
            case 4: {
                return "int";
            }
            case 5: {
                return "long";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "double";
            }
            case 8: {
                return "boolean";
            }
        }
        throw new aHY("Invalid index " + this.index);
    }

    public void a(vb_0 vb_02) {
        vb_02.a(this);
    }

    public void a(Ax ax) {
        ax.a(this);
    }
}

