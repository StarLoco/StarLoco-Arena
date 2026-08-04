/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from bs
 */
public final class bs_2 {
    public static final int fS = 4;
    public static final int fT = 1;
    public static final int fU = 2;
    public static final int fV = 4;
    private byte fW = (byte)32;
    private short fX = 0;
    private float fY = 25.0f;

    public final void b(acf acf2) {
        this.fW = acf2.readByte();
        this.fX = acf2.readShort();
        this.fY = acf2.readByte();
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeByte(this.fW);
        aij_12.writeShort(this.fX);
        aij_12.writeByte((byte)this.fY);
    }

    public final byte cG() {
        return this.fW;
    }

    public final short cH() {
        return this.fX;
    }

    public final float getFrameRate() {
        return this.fY;
    }

    public final boolean cI() {
        return (this.fW & 1) != 0;
    }

    public final boolean cJ() {
        return (this.fW & 2) != 0;
    }

    public final void cK() {
        this.fW = (byte)(this.fW | 2);
    }

    public final boolean cL() {
        return (this.fW & 4) == 4;
    }

    public final void cM() {
        this.fW = (byte)(this.fW | 4);
    }
}

