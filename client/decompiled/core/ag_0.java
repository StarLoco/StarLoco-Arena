/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from aG
 */
public class ag_0
extends te_1 {
    private Ur cE = null;

    public void a(String string, aji_1 aji_12, Ur ur) {
        this.cE = ur;
        this.c(string, aji_12);
    }

    protected void a(String[] stringArray, List list, List list2) {
        list.add(Ur.class);
        list2.add(this.cE);
        super.a(stringArray, list, list2);
    }

    public void a(ag_0 ag_02) {
        ag_02.a(this.nq, this.blb, this.cE);
    }

    public ag_0 bi() {
        ag_0 ag_02 = new ag_0();
        this.a(ag_02);
        return ag_02;
    }
}

