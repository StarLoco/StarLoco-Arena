/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aqf
 */
public class aqf_0
extends vn_2 {
    private ArrayList cNQ;

    public aij_2 Ce() {
        return aij_2.cxH;
    }

    public Enum eN() {
        return cr_1.jX;
    }

    public qa_2 c(Object object, Object object2, Object object3, Object object4) {
        qa_2 qa_22 = new qa_2();
        for (NY nY : this.cNQ) {
            qa_22.ct(nY.e(object, object2, object3, object4));
        }
        return qa_22;
    }

    public aqf_0(ArrayList arrayList) {
        this(arrayList, false);
    }

    public aqf_0(ArrayList arrayList, boolean bl2) {
        if (bl2) {
            this.cNQ = arrayList;
        } else {
            this.cNQ = new ArrayList();
            for (ayp ayp2 : arrayList) {
                if (ayp2.Ce() == aij_2.cxF) {
                    this.cNQ.add((NY)ayp2);
                    continue;
                }
                throw new ze_2("On essaie d'ajouter " + ayp2 + " de type " + ayp2.Ce().name() + " \u00e0 une liste de valeur num\u00e9rique");
            }
        }
    }

    public int getSize() {
        return this.cNQ.size();
    }
}

