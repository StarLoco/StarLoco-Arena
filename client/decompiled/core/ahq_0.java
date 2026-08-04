/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from ahq
 */
public class ahq_0
implements aHq {
    private static ahq_0 cvs = new ahq_0();
    private ArrayList byS = new ArrayList();

    public static ahq_0 awW() {
        return cvs;
    }

    public void b(aMn aMn2) {
        this.byS.add(aMn2);
    }

    public void a(aba_2 aba_22, int n2) {
        int n3 = this.byS.size();
        for (int j = 0; j < n3; ++j) {
            ((aMn)this.byS.get(j)).bI(n2);
        }
    }

    public void a(aba_2 aba_22, float f, float f2) {
        Iterator iterator = this.byS.iterator();
        while (iterator.hasNext()) {
            aMn aMn2 = (aMn)iterator.next();
            if (!aMn2.isRemovable()) continue;
            iterator.remove();
        }
    }
}

