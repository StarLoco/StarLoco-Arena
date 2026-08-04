/*
 * Decompiled with CFR 0.152.
 */
public abstract class KU {
    protected na_1 ade;

    public KU(na_1 na_12) {
        this.ade = na_12;
        this.Br();
    }

    public void Xq() {
        aji_1 aji_12 = this.ade.getElementMap();
        if (aji_12 == null) {
            return;
        }
        String string = aji_12.getId();
        String string2 = this.ade.getId();
        if (string == null) {
            return;
        }
        this.n(string, string2);
    }

    public void Xr() {
        aji_1 aji_12 = this.ade.getElementMap();
        if (aji_12 == null) {
            return;
        }
        String string = aji_12.getId();
        String string2 = this.ade.getId();
        if (string == null) {
            return;
        }
        this.o(string, string2);
    }

    protected abstract void n(String var1, String var2);

    protected abstract void o(String var1, String var2);

    public abstract void Bq();

    public abstract void Br();
}

