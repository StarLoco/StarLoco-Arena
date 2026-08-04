/*
 * Decompiled with CFR 0.152.
 */
public final class amf
extends amh {
    public static final amf cGq = new amf("private");
    public static final amf cGr = new amf("protected");
    public static final amf cGs = new amf("/*default*/");
    public static final amf cGt = new amf("public");
    static Class cGu;

    private amf(String string) {
        super(string);
    }

    public static amf iF(String string) {
        return (amf)amh.g(string, cGu == null ? (cGu = amf.a("amf")) : cGu);
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

