/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aLI
 */
public class ali_0 {
    private static final ali_0 dVZ = new ali_0();
    private ArrayList G = new ArrayList();
    private ge_0 dWa;

    private ali_0() {
    }

    public static ali_0 aWv() {
        return dVZ;
    }

    public void a(ge_0 ge_02) {
        this.G.add(ge_02);
    }

    public void a(ge_0 ge_02, boolean bl2) {
        this.G.remove(ge_02);
        if (bl2) {
            ge_02.clean();
        }
    }

    public void c(adg_2 adg_22, int n2, int n3) {
        for (ge_0 ge_02 : this.G) {
            if (!ge_02.b(adg_22, n2, n3)) continue;
            this.dWa = ge_02;
            this.dWa.select(n2, n3);
            return;
        }
    }

    public boolean d(adg_2 adg_22, int n2, int n3) {
        if (this.dWa != null) {
            this.dWa.a(n2, n3, adg_22);
            return true;
        }
        return false;
    }

    public void e(adg_2 adg_22, int n2, int n3) {
        if (this.dWa != null) {
            this.dWa.b(n2, n3, adg_22);
            this.dWa = null;
        }
    }
}

