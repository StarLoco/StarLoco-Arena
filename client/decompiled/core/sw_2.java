/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from sW
 */
class sw_2
extends tv_0 {
    private final va_2 aml;
    private final va_2 amm;
    private final va_2 amn;
    private final avo_0 amo;

    public sw_2(avo_0 avo_02, va_2 va_22, va_2 va_23, va_2 va_24) {
        super(avo_02, null);
        this.amo = avo_02;
        this.aml = va_22;
        this.amm = va_23;
        this.amn = va_24;
    }

    public boolean zi() {
        if (this.amm.offset == -1 || this.amn.offset == -1) {
            throw new aHY("Cannot relocate offset branch to unset destination offset");
        }
        int n2 = this.amn.offset - this.amm.offset;
        byte[] byArray = new byte[]{(byte)(n2 >> 24), (byte)(n2 >> 16), (byte)(n2 >> 8), (byte)n2};
        System.arraycopy(byArray, 0, avo_0.b(this.amo), this.aml.offset, 4);
        return true;
    }
}

