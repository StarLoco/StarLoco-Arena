/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.texture.TextureCoords;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from ZL
 */
public class zl_1 {
    private static final Logger a = Logger.getLogger(zl_1.class);
    private static final short cdO = 16;
    private static final short cdP = 32;
    private static final short cdQ = 64;
    private static final short cdR = 128;
    private static final short cdS = 15;
    private final int aW;
    private final short cdT;
    private final short cdU;
    private final short cdV;
    private final short cdW;
    private final int Dr;
    private final byte cdX;
    private final byte cdY;
    private final byte cdZ;
    protected final afd_0 cea;
    private final TextureCoords ceb;
    private byte cec;
    private final byte ced;

    public zl_1(int n2, short s, short s2, short s3, short s4, int n3, boolean bl2, byte by, byte by2, boolean bl3, boolean bl4, boolean bl5, byte by3, byte by4, afd_0 afd_02, byte by5) {
        this.aW = n2;
        this.cdT = s;
        this.cdU = s2;
        this.cdV = s3;
        this.cdW = s4;
        this.Dr = n3;
        this.cdX = by2;
        assert (by <= 15);
        byte by6 = by;
        by6 = (byte)(by6 | (bl2 ? 16 : 0));
        by6 = (byte)(by6 | (bl3 ? 32 : 0));
        by6 = (byte)(by6 | (bl4 ? 64 : 0));
        this.ced = by6 = (byte)(by6 | (bl5 ? 128 : 0));
        this.cdY = by3;
        this.cdZ = by4;
        this.cea = afd_02;
        this.ceb = this.cea == null ? zl_1.d(this.cdV, this.cdW, this.aok()) : null;
        this.cec = by5;
    }

    private static TextureCoords d(int n2, int n3, boolean bl2) {
        float f = ej_0.aq(n2);
        float f2 = (float)ej_0.aq(n3) - 0.5f;
        float f3 = (float)n2 / f;
        float f4 = (float)n3 / f2;
        if (bl2) {
            return new TextureCoords(f3, f4, 0.0f, 0.0f);
        }
        return new TextureCoords(0.0f, f4, f3, 0.0f);
    }

    zl_1(ByteBuffer byteBuffer) {
        this.aW = byteBuffer.getInt();
        this.cdT = byteBuffer.getShort();
        this.cdU = byteBuffer.getShort();
        this.cdV = byteBuffer.getShort();
        this.cdW = byteBuffer.getShort();
        this.Dr = byteBuffer.getInt();
        this.ced = byteBuffer.get();
        this.cdX = byteBuffer.get();
        this.cdY = byteBuffer.get();
        this.cdZ = byteBuffer.get();
        boolean bl2 = this.aok();
        this.cea = vp_0.a(byteBuffer, bl2);
        this.ceb = this.cea == null ? zl_1.d(this.cdV, this.cdW, bl2) : null;
        this.cec = byteBuffer.get();
    }

    public void a(aij_1 aij_12) {
        aij_12.writeInt(this.aW);
        aij_12.writeShort(this.cdT);
        aij_12.writeShort(this.cdU);
        aij_12.writeShort(this.cdV);
        aij_12.writeShort(this.cdW);
        aij_12.writeInt(this.Dr);
        aij_12.writeByte(this.ced);
        aij_12.writeByte(this.cdX);
        aij_12.writeByte(this.cdY);
        aij_12.writeByte(this.cdZ);
        if (this.cea == null) {
            aij_12.writeByte((byte)0);
        } else {
            this.cea.h(aij_12);
        }
        aij_12.writeByte(this.cec);
    }

    public int getId() {
        return this.aW;
    }

    public int aog() {
        return this.cdT;
    }

    public int aoh() {
        return this.cdU;
    }

    public int aoi() {
        return this.cdV;
    }

    public int aoj() {
        return this.cdW;
    }

    public int oo() {
        return this.Dr;
    }

    public boolean aok() {
        return (this.ced & 0x10) == 16;
    }

    public byte aol() {
        return (byte)(this.ced & 0xF);
    }

    public int getVisualHeight() {
        return this.cdX;
    }

    public boolean aom() {
        return (this.ced & 0x20) == 32;
    }

    public byte aon() {
        return this.cec;
    }

    public boolean aoo() {
        return (this.ced & 0x40) == 64;
    }

    public boolean aop() {
        return (this.ced & 0x80) == 128;
    }

    public byte aoq() {
        return this.cdY;
    }

    public byte aor() {
        return this.cdZ;
    }

    public boolean aos() {
        return this.cea != null;
    }

    public TextureCoords bn(short s) {
        if (this.ceb != null) {
            assert (this.cea == null);
            return this.ceb;
        }
        return this.cea.bn(s);
    }
}

