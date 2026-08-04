/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.CellParticleSystem;
import org.apache.log4j.Logger;

public class aDv
extends alz {
    private static final Logger a = Logger.getLogger(aDv.class);
    CellParticleSystem dxp;
    int dxq = 0;
    byte ata = 0;
    byte dxr;
    byte dxs;
    byte dxt;
    byte bPN;

    public aDv() {
    }

    public aDv(byte by, byte by2, short s, int n2, byte by3, byte by4, byte by5, byte by6, byte by7) {
        super(by, by2, s);
        this.dxq = n2;
        this.ata = by3;
        this.dxr = by4;
        this.dxs = by5;
        this.dxt = by6;
        this.bPN = by7;
    }

    public void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/ParticleDef.load must not be null");
        }
        super.b(acf2);
        this.dxq = acf2.readInt();
        this.ata = acf2.readByte();
        this.dxr = acf2.readByte();
        this.dxs = acf2.readByte();
        this.dxt = acf2.readByte();
        this.bPN = acf2.readByte();
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/ParticleDef.save must not be null");
        }
        super.a(aij_12);
        aij_12.writeInt(this.dxq);
        aij_12.writeByte(this.ata);
        aij_12.writeByte(this.dxr);
        aij_12.writeByte(this.dxs);
        aij_12.writeByte(this.dxt);
        aij_12.writeByte(this.bPN);
    }

    public String toString() {
        return "ParticleDef{m_systemId=" + this.dxq + ", m_level=" + this.ata + "m_x=" + this.cFs + ", m_y=" + this.cFt + ", m_z=" + this.wp + ", m_offsetX=" + this.dxr + ", m_offsetY=" + this.dxs + ", m_offsetZ=" + this.dxt + ", m_lod=" + this.bPN + '}';
    }
}

