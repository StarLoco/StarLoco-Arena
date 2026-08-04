/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from alm
 */
public abstract class alm_0 {
    private List aoz = null;

    public abstract int value();

    public abstract int atR();

    public abstract int max();

    public abstract int min();

    public abstract void set(int var1);

    public abstract int jZ(int var1);

    public abstract int ka(int var1);

    public abstract int kb(int var1);

    public abstract int kc(int var1);

    public abstract void at(int var1);

    public abstract void as(int var1);

    public abstract void atS();

    public boolean uI() {
        return this.value() > 0;
    }

    public boolean isNegative() {
        return this.value() < 0;
    }

    public boolean isZero() {
        return this.value() == 0;
    }

    public boolean aAD() {
        return this.value() != 0;
    }

    public boolean isMaximum() {
        return this.atR() >= this.max();
    }

    public boolean aAE() {
        return this.atR() <= this.min();
    }

    public void aAF() {
        this.set(this.max());
    }

    public void aAG() {
        this.set(this.min());
    }

    public abstract aiq_2 atT();

    public void o(List list) {
        if (list == null) {
            return;
        }
        if (this.aoz == null) {
            this.aoz = new ArrayList();
            this.aoz.addAll(list);
        } else {
            this.aAH();
            this.aoz.addAll(list);
        }
    }

    public void a(ph_2 ph_22) {
        if (this.aoz == null) {
            this.aoz = new ArrayList();
            this.aoz.add(ph_22);
        } else if (!this.aoz.contains(ph_22)) {
            this.aoz.add(ph_22);
        }
    }

    public void b(ph_2 ph_22) {
        if (this.aoz != null) {
            this.aoz.remove(ph_22);
        }
    }

    public List zY() {
        return this.aoz;
    }

    public void aAH() {
        if (this.aoz != null) {
            this.aoz.clear();
        }
    }

    public void aAI() {
        if (this.aoz != null && !this.aoz.isEmpty()) {
            for (ph_2 ph_22 : this.aoz) {
                ph_22.a(this);
            }
        }
    }
}

