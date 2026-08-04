/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aai
 */
public abstract class aai_1 {
    private int aW;
    private Object ceJ;

    protected aai_1() {
    }

    public aai_1(int n2, Object object, nv nv2) {
        this.aW = n2;
        this.ceJ = object;
        nv2.a(this);
    }

    public int getId() {
        return this.aW;
    }

    public Object getObject() {
        return this.ceJ;
    }

    public abstract String aoI();
}

