/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kW
 */
public class kw_0 {
    public static void a(vU vU2, amb amb2) {
        if (vU2 == null) {
            return;
        }
        Ju ju = vU2.ea();
        if (ju != null) {
            ju.c(amb2);
        }
    }

    public static void a(vU vU2, Object object, String string) {
        kw_0.a(vU2, new jP(string, object));
    }

    public static void b(vU vU2, Object object, String string) {
        kw_0.a(vU2, new apQ(string, object));
    }

    public static void a(vU vU2, Object object, String string, Throwable throwable) {
        kw_0.a(vU2, new aIX(string, object, throwable));
    }
}

