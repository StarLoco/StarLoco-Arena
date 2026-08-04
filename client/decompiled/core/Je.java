/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;

public class Je
extends Iy {
    private static final Je bjs = new Je();

    public static Je Vv() {
        return bjs;
    }

    public Je() {
        super("AdminServerInstance");
    }

    public void b(String string, String string2, String string3, String string4) {
        um_1.AF().b(string, string2, string3, string4);
    }

    public void a(URL uRL, String string, String string2, String string3) {
        um_1.AF().a(uRL, string, string2, string3);
    }

    public boolean o(String string, int n2) {
        qc_2 qc_22 = new qc_2();
        this.a(qc_22);
        this.a(new axo());
        try {
            this.n(string, n2);
            this.start();
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return false;
        }
        return true;
    }
}

