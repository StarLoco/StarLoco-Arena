/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aOr
 */
public class aor_1
implements zw {
    private static final aor_1 elO = new aor_1();
    private zw elP;

    private aor_1() {
    }

    public static aor_1 aYh() {
        return elO;
    }

    public void a(zw zw2) {
        this.elP = zw2;
    }

    public void a(aho_0 aho_02, String ... stringArray) {
        if (this.elP != null) {
            this.elP.a(aho_02, stringArray);
        }
    }

    public void g(String string, Object object) {
        if (this.elP != null) {
            this.elP.g(string, object);
        }
    }
}

