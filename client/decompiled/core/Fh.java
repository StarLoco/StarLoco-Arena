/*
 * Decompiled with CFR 0.152.
 */
class Fh
extends tv_0 {
    private boolean expanded;
    private final int aUw;
    private final aNc aUx;
    private final va_2 amn;
    private final avo_0 amo;

    public Fh(avo_0 avo_02, int n2, va_2 va_22) {
        super(avo_02, null);
        this.amo = avo_02;
        this.aUw = n2;
        this.aUx = avo_02.aIw();
        this.amn = va_22;
        this.expanded = n2 == -55 || n2 == -56;
    }

    public boolean zi() {
        if (this.amn.offset == -1) {
            throw new aHY("Cannot relocate branch to unset destination offset");
        }
        int n2 = this.amn.offset - this.aUx.offset;
        if (!(this.expanded || n2 <= Short.MAX_VALUE && n2 >= Short.MIN_VALUE)) {
            int n3 = this.aUx.offset;
            this.amo.a(this.aUx);
            this.amo.d((short)-1, this.aUw == -89 ? 2 : (this.aUw == -88 ? 2 : 5));
            this.amo.aIy();
            this.aUx.offset = n3;
            this.expanded = true;
            return false;
        }
        byte[] byArray = !this.expanded ? new byte[]{(byte)this.aUw, (byte)(n2 >> 8), (byte)n2} : (this.aUw == -89 || this.aUw == -88 ? new byte[]{(byte)(this.aUw + 33), (byte)(n2 >> 24), (byte)(n2 >> 16), (byte)(n2 >> 8), (byte)n2} : new byte[]{avo_0.aZ((byte)this.aUw), 0, 8, -56, (byte)((n2 -= 3) >> 24), (byte)(n2 >> 16), (byte)(n2 >> 8), (byte)n2});
        System.arraycopy(byArray, 0, avo_0.b(this.amo), this.aUx.offset, byArray.length);
        return true;
    }
}

