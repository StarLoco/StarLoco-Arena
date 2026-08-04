/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from TY
 */
public class ty_1
extends alz {
    private static final Logger a = Logger.getLogger(ty_1.class);
    public int aW;
    public int Dr;
    public int rv;
    public byte bOX;

    public ty_1() {
    }

    public ty_1(int n2, int n3, int n4, byte by, byte by2, byte by3, short s) {
        super(by2, by3, s);
        this.aW = n2;
        this.Dr = n3;
        this.rv = n4;
        this.bOX = by;
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/DynamicElementDef.load must not be null");
        }
        super.b(acf2);
        this.aW = acf2.readInt();
        this.Dr = acf2.readInt();
        this.rv = acf2.readShort();
        this.bOX = acf2.readByte();
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/DynamicElementDef.save must not be null");
        }
        super.a(aij_12);
        aij_12.writeInt(this.aW);
        aij_12.writeInt(this.Dr);
        aij_12.writeShort((short)this.rv);
        aij_12.writeByte(this.bOX);
    }

    public String toString() {
        return "SoundDef{m_id=" + this.aW + ", m_gfxId=" + this.Dr + ", m_type=" + this.rv + ", m_direction=" + this.bOX + ", m_x=" + this.cFs + ", m_y=" + this.cFt + ", m_z=" + this.wp + '}';
    }
}

