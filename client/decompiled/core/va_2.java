/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VA
 */
public class va_2 {
    int offset;
    va_2 bSE;
    va_2 bSF;
    static final int UNSET = -1;
    private final avo_0 amo;

    public va_2(avo_0 avo_02) {
        this.amo = avo_02;
        this.offset = -1;
        this.bSE = null;
        this.bSF = null;
    }

    public void set() {
        if (this.offset != -1) {
            throw new aHY("Cannot \"set()\" Offset more than once");
        }
        this.offset = avo_0.c((avo_0)this.amo).offset;
        this.bSE = avo_0.c((avo_0)this.amo).bSE;
        this.bSF = avo_0.c(this.amo);
        this.bSE.bSF = this;
        this.bSF.bSE = this;
    }

    public final avo_0 Gb() {
        return this.amo;
    }

    public String toString() {
        return avo_0.d(this.amo).aaB() + ": " + this.offset;
    }
}

