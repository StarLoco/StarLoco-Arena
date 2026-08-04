/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class ads
implements EV {
    private EV cmC = null;
    private ArrayList uA;
    protected boolean cmD = false;
    private boolean vd = false;
    private int bmF = 0;
    private Object dE;

    public ads(Object object) {
        this.dE = object;
    }

    public void a(ads ads2) {
        if (this.uA == null) {
            this.uA = new ArrayList();
        }
        if (!this.uA.contains(ads2)) {
            this.uA.add(ads2);
            ads2.cmC = this;
            ads2.aK(this.bmF + 1);
        }
    }

    public boolean hasChildren() {
        return this.uA != null && !this.uA.isEmpty();
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public EV OK() {
        return this.cmC;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void bo(boolean bl2) {
        this.cmD = bl2;
    }

    public boolean OL() {
        return this.cmD;
    }

    public void setSelected(boolean bl2) {
        this.vd = bl2;
    }

    public boolean isSelected() {
        return this.vd;
    }

    public int getDepth() {
        return this.bmF;
    }

    public void aK(int n2) {
        if (this.bmF == n2) {
            return;
        }
        this.bmF = n2;
        if (this.uA != null) {
            for (int j = this.uA.size() - 1; j >= 0; --j) {
                ((EV)this.uA.get(j)).aK(this.bmF + 1);
            }
        }
    }
}

