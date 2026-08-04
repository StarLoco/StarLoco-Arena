/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public final class Wq {
    private static final Wq bTZ = new Wq();
    private final lb_0 bUa = new lb_0();

    private Wq() {
    }

    public static final Wq ajf() {
        return bTZ;
    }

    public r_0 b(afP afP2) {
        Object object;
        if (afP2.B() && (object = this.ix(afP2.A())) != null) {
            int n2 = ((ArrayList)object).size();
            for (int j = 0; j < n2; ++j) {
                r_0 r_02 = (r_0)((ArrayList)object).get(j);
                if (r_02.getLevel() >= afP2.getLevel()) {
                    return null;
                }
                if (!r_02.B()) continue;
                r_02.C();
                ((ArrayList)object).remove(j);
                break;
            }
        }
        object = add_1.aOG().a(afP2.getMessage(), afP2.getTitle(), afP2.Sk(), afP2.VI());
        ((r_0)object).a(afP2);
        this.b((r_0)object);
        return object;
    }

    private void b(r_0 r_02) {
        ArrayList<r_0> arrayList = (ArrayList<r_0>)this.bUa.get(r_02.A());
        if (arrayList == null) {
            arrayList = new ArrayList<r_0>();
            this.bUa.c(r_02.A(), arrayList);
        }
        arrayList.add(r_02);
    }

    public void c(r_0 r_02) {
        ArrayList arrayList = (ArrayList)this.bUa.get(r_02.A());
        if (arrayList == null) {
            return;
        }
        arrayList.remove(r_02);
    }

    private ArrayList ix(int n2) {
        return (ArrayList)this.bUa.get(n2);
    }
}

