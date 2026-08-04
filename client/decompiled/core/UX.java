/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class UX {
    public final short ayK;
    public final boolean bRY;
    public final float bRZ;
    public final float aaw;
    public final float bSa;

    public UX() {
        this(-1);
    }

    public UX(short s) {
        this(s, true, 0.01f, 1.0f, 0.01f);
    }

    public UX(short s, boolean bl2, float f, float f2, float f3) {
        this.ayK = s;
        this.bRY = bl2;
        this.bRZ = f;
        this.aaw = f2;
        this.bSa = f3;
    }

    public UX(ByteBuffer byteBuffer) {
        this.ayK = byteBuffer.getShort();
        this.bRY = byteBuffer.get() != 0;
        this.bRZ = byteBuffer.getFloat();
        this.aaw = byteBuffer.getFloat();
        this.bSa = byteBuffer.getFloat();
    }

    public void h(aij_1 aij_12) {
        aij_12.writeShort(this.ayK);
        aij_12.writeByte(this.bRY ? (byte)1 : 0);
        aij_12.writeFloat(this.bRZ);
        aij_12.writeFloat(this.aaw);
        aij_12.writeFloat(this.bSa);
    }
}

