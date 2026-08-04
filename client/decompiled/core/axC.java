/*
 * Decompiled with CFR 0.152.
 */
public class axC
extends arf_0 {
    private final lc_0 djT;

    public axC(String string, lc_0 lc_02) {
        super(string);
        this.djT = lc_02;
    }

    public axC(String string, lc_0 lc_02, Throwable throwable) {
        super(string, throwable);
        this.djT = lc_02;
    }

    public String getMessage() {
        return this.djT == null ? super.getMessage() : this.djT.toString() + ": " + super.getMessage();
    }

    public lc_0 aP() {
        return this.djT;
    }
}

