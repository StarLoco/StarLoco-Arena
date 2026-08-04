/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

class aEq
implements xy {
    final /* synthetic */ ArrayList dzM;
    final /* synthetic */ yb_2 dzL;
    final /* synthetic */ aoq_2 dzI;

    aEq(aoq_2 aoq_22, ArrayList arrayList, yb_2 yb_22) {
        this.dzI = aoq_22;
        this.dzM = arrayList;
        this.dzL = yb_22;
    }

    public boolean isValid() {
        for (int j = 0; j < this.dzM.size(); ++j) {
            if (((qs_2)this.dzM.get(j)).vy()) continue;
            return false;
        }
        this.dzL.b(this);
        return true;
    }
}

