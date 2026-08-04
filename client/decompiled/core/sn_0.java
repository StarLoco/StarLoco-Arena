/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from sN
 */
public class sn_0 {
    private ArrayList alX;
    private ie alY = null;
    private static final ie alZ = new vk_0();
    private ArrayList dS = new ArrayList();

    public sn_0(ArrayList arrayList) {
        this.g(arrayList);
    }

    public sn_0() {
    }

    public void f(qa_1 qa_12) {
        this.dS.add(qa_12);
    }

    public void g(qa_1 qa_12) {
        this.dS.remove(qa_12);
    }

    public void clear() {
        this.dS.clear();
    }

    public void yW() {
        for (int j = this.dS.size() - 1; j >= 0; --j) {
            ((qa_1)this.dS.get(j)).uQ();
        }
    }

    protected void yX() {
        this.alY = null;
        if (this.alX == null || this.alX.size() == 0) {
            return;
        }
        if (this.alY == null) {
            this.alY = (ie)this.alX.get(0);
        }
    }

    public boolean h(qa_1 qa_12) {
        if (this.alY == null || qa_12 == null) {
            return false;
        }
        ie ie2 = null;
        for (ie ie3 : this.alX) {
            if (!ie3.isRenderableCompatible(qa_12)) continue;
            ie2 = ie3;
            break;
        }
        if (ie2 == null) {
            ie2 = alZ.isRenderableCompatible(qa_12) ? alZ : this.alY;
        }
        if (qa_12.getRenderer() != ie2) {
            qa_12.setRenderer(ie2);
            return true;
        }
        return false;
    }

    public ArrayList yY() {
        return this.alX;
    }

    public void g(ArrayList arrayList) {
        this.alX = arrayList;
        if (this.alX != null) {
            for (int j = this.alX.size() - 1; j >= 0; --j) {
                ((ie)this.alX.get(j)).setManager(this);
            }
        }
        this.yX();
    }

    public void a(ie ie2) {
        if (this.alX == null) {
            this.alX = new ArrayList();
        }
        this.alX.add(ie2);
        ie2.setManager(this);
        this.yX();
    }
}

