/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aaT
 */
public abstract class aat_0
implements Cloneable {
    protected UI hL;
    protected axc_0 pW = axc_0.diY;
    protected String description;

    public void l(UI uI) {
        this.hL = uI;
    }

    public UI TP() {
        return this.hL;
    }

    public axc_0 hW() {
        return this.pW;
    }

    public void a(axc_0 axc_02) {
        this.pW = axc_02;
    }

    public void setDescription(String string) {
        this.description = string;
    }

    public String getDescription() {
        return this.description;
    }

    public void log(String string) {
        this.l(string, 2);
    }

    public void l(String string, int n2) {
        if (this.TP() != null) {
            this.TP().l(string, n2);
        } else if (n2 <= 2) {
            System.err.println(string);
        }
    }

    public Object clone() {
        aat_0 aat_02 = (aat_0)super.clone();
        aat_02.a(this.hW());
        aat_02.l(this.TP());
        return aat_02;
    }
}

