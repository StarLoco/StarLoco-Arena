/*
 * Decompiled with CFR 0.152.
 */
public abstract class UC {
    protected abstract byte lV();

    public abstract Object d(acf var1, float var2);

    protected abstract void a(aij_1 var1, Object var2, Object var3);

    protected abstract boolean equals(Object var1, Object var2);

    public final void b(aij_1 aij_12, Object object, Object object2) {
        aij_12.writeByte(this.lV());
        this.a(aij_12, object, object2);
    }

    public static void a(aij_1 aij_12, boolean bl2, int n2, int n3) {
        aij_12.writeShort((short)(n2 & 0xFFFF));
        if (bl2) {
            aij_12.writeShort((short)(n3 & 0xFFFF));
        }
    }

    public static int a(acf acf2, boolean bl2, float f) {
        short s = acf2.readShort();
        if (!bl2) {
            return s & 0xFFFF;
        }
        short s2 = acf2.readShort();
        return Math.round(ej_0.a((float)s, (float)s2, f)) & 0xFFFF;
    }

    public static void a(aij_1 aij_12, boolean bl2, float f, float f2) {
        aij_12.writeFloat(f);
        if (bl2) {
            aij_12.writeFloat(f2);
        }
    }

    public static float b(acf acf2, boolean bl2, float f) {
        float f2 = acf2.readFloat();
        if (!bl2) {
            return f2;
        }
        float f3 = acf2.readFloat();
        return ej_0.a(f2, f3, f);
    }

    public static void b(aij_1 aij_12, boolean bl2, int n2, int n3) {
        aij_12.writeInt(n2);
        if (bl2) {
            aij_12.writeInt(n3);
        }
    }

    public static int c(acf acf2, boolean bl2, float f) {
        int n2 = acf2.readInt();
        if (!bl2) {
            return n2;
        }
        int n3 = acf2.readInt();
        return (int)ej_0.a((float)n2, (float)n3, f);
    }
}

