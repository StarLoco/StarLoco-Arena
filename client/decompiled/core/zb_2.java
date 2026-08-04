/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from zb
 */
public final class zb_2 {
    private final ArrayList iM = new ArrayList();

    public final void a(akR akR2) {
        if (akR2 != null) {
            this.iM.add(new aHS(akR2));
        }
    }

    public final int FZ() {
        return this.iM.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[ Ambiance : ").append(this.FZ()).append(" effect(s)");
        return stringBuilder.toString();
    }

    public void update(int n2) {
        for (int j = 0; j < this.iM.size(); ++j) {
            ((aHS)this.iM.get(j)).update(n2);
        }
    }

    public void j(ArrayList arrayList) {
        arrayList.addAll(this.iM);
    }
}

