/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public abstract class aMn {
    private boolean dXJ = false;
    private List aoz = null;
    protected final Du Ie;

    public aMn(Du du) {
        this.Ie = du;
    }

    public void a(se_0 se_02) {
        if (this.aoz == null) {
            this.aoz = new ArrayList();
        }
        this.aoz.add(se_02);
    }

    public void b(se_0 se_02) {
        if (this.aoz == null) {
            return;
        }
        this.aoz.remove(se_02);
    }

    public boolean isRemovable() {
        return this.dXJ;
    }

    public void aWV() {
        this.dXJ = true;
        if (this.aoz != null) {
            for (se_0 se_02 : this.aoz) {
                se_02.a(this);
            }
        }
    }

    public abstract double aJk();

    public abstract void bI(int var1);
}

