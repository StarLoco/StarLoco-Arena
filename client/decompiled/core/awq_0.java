/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from awq
 */
public class awq_0 {
    private String dhN;
    private UI hL;

    public awq_0() {
    }

    public awq_0(String string) {
        this.jV(string);
    }

    public awq_0(UI uI, String string) {
        this.jV(string);
        this.l(uI);
    }

    public void jV(String string) {
        this.dhN = string;
    }

    public String aJC() {
        return this.dhN;
    }

    public void l(UI uI) {
        this.hL = uI;
    }

    public UI TP() {
        return this.hL;
    }

    public Object P(UI uI) {
        Object object;
        if (this.dhN == null) {
            throw new eq_2("No reference specified");
        }
        Object object2 = object = this.hL == null ? uI.gi(this.dhN) : this.hL.gi(this.dhN);
        if (object == null) {
            throw new eq_2("Reference " + this.dhN + " not found.");
        }
        return object;
    }

    public Object aJD() {
        if (this.hL == null) {
            throw new eq_2("No project set on reference to " + this.dhN);
        }
        return this.P(this.hL);
    }
}

