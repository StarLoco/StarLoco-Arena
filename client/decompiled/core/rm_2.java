/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.impl.packrect.Rect;
import com.sun.opengl.impl.packrect.RectVisitor;
import java.util.List;

/*
 * Renamed from RM
 */
class rm_2
implements RectVisitor {
    final /* synthetic */ List bKv;
    final /* synthetic */ afg_0 bKu;

    rm_2(afg_0 afg_02, List list) {
        this.bKu = afg_02;
        this.bKv = list;
    }

    public void visit(Rect rect) {
        adh_2 adh_22 = (adh_2)rect.getUserData();
        if (adh_22.aPD()) {
            adh_22.aPF();
        } else {
            this.bKv.add(rect);
        }
    }
}

