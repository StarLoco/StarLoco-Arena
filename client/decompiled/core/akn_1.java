/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akN
 */
public abstract class akn_1 {
    akn_1 cDW;

    public abstract String h(Object var1);

    public void a(StringBuffer stringBuffer, Object object) {
        stringBuffer.append(this.h(object));
    }

    public final void g(akn_1 akn_12) {
        if (this.cDW != null) {
            throw new IllegalStateException("Next converter has been already set");
        }
        this.cDW = akn_12;
    }

    public final akn_1 azY() {
        return this.cDW;
    }
}

