/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class alz
implements azi_0 {
    private static final Logger a = Logger.getLogger(alz.class);
    public byte cFs;
    public byte cFt;
    public short wp;

    public alz() {
    }

    public alz(byte by, byte by2, short s) {
        this.cFs = by;
        this.cFt = by2;
        this.wp = s;
        assert (this.cFs >= 0 && this.cFs < 18);
        assert (this.cFt >= 0 && this.cFt < 18);
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/alea/environment/ElementDef.load must not be null");
        }
        this.cFs = acf2.readByte();
        this.cFt = acf2.readByte();
        this.wp = acf2.readShort();
        assert (this.cFs >= 0 && this.cFs < 18);
        assert (this.cFt >= 0 && this.cFt < 18);
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/alea/environment/ElementDef.save must not be null");
        }
        assert (this.cFs >= 0 && this.cFs < 18);
        assert (this.cFt >= 0 && this.cFt < 18);
        aij_12.writeByte(this.cFs);
        aij_12.writeByte(this.cFt);
        aij_12.writeShort(this.wp);
    }

    public String toString() {
        return "ElementDef{m_x=" + this.cFs + ", m_y=" + this.cFt + ", m_z=" + this.wp + '}';
    }
}

