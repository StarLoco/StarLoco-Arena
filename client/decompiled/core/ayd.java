/*
 * Decompiled with CFR 0.152.
 */
import java.util.LinkedHashMap;

public class ayd {
    protected final LinkedHashMap dkD;
    private int dkE;
    private int dkF = 0;
    private int dkG = 1;

    public ayd(int n2) {
        this.dkE = n2;
        this.dkD = new Wd(this, n2, 0.75f, true);
    }

    public Object get(Object object) {
        return this.dkD.get(object);
    }

    public void put(Object object, Object object2) {
        this.dkD.put(object, object2);
    }

    public void remove(Object object) {
        this.dkD.remove(object);
    }

    public void clear() {
        this.dkD.clear();
    }

    public int aKK() {
        return this.dkE;
    }

    public void mL(int n2) {
        this.dkE = n2;
        this.dkG = 1;
        this.dkF = 0;
    }

    public int aKL() {
        return this.dkF;
    }

    public int aKM() {
        return this.dkG;
    }

    public int aKN() {
        return this.dkD.size() * 100 / this.dkE;
    }

    public int size() {
        return this.dkD.size();
    }

    static /* synthetic */ int a(ayd ayd2) {
        return ayd2.dkE;
    }
}

