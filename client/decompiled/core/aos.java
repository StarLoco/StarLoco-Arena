/*
 * Decompiled with CFR 0.152.
 */
public abstract class aos
extends aGx {
    protected aos(int n2) {
        super(n2);
    }

    public final AV afY() {
        return AV.aIp;
    }

    public final int afZ() {
        return 0;
    }

    public abstract void a(aij_1 var1, String var2);

    protected static aeW[] iQ(String string) {
        String[] stringArray = string.split(";");
        aeW[] aeWArray = new aeW[stringArray.length];
        for (int j = 0; j < stringArray.length; ++j) {
            String[] stringArray2 = stringArray[j].split(":");
            aeWArray[j] = new aeW(stringArray2[0], stringArray2[1]);
        }
        return aeWArray;
    }
}

