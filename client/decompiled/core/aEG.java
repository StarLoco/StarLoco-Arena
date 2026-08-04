/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class aEG
implements azi_0 {
    private static final Logger a = Logger.getLogger(aEG.class);
    public long nD;
    public short Gp;
    public int[] dBG;
    public byte[] Fe;
    public boolean dBH;
    public short dBI = (short)-1;

    public void b(acf acf2) {
        int n2;
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/InteractiveElementDef.load must not be null");
        }
        this.nD = acf2.readLong();
        this.Gp = acf2.readShort();
        int n3 = acf2.readByte() & 0xFF;
        this.dBG = new int[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            this.dBG[n2] = acf2.readInt();
        }
        n2 = acf2.readShort() & 0xFFFF;
        this.Fe = acf2.jE(n2);
        this.dBH = acf2.aqE();
        this.dBI = acf2.readShort();
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/InteractiveElementDef.save must not be null");
        }
        aij_12.writeLong(this.nD);
        aij_12.writeShort(this.Gp);
        if (this.dBG == null) {
            aij_12.writeByte((byte)0);
        } else {
            aij_12.writeByte((byte)(this.dBG.length & 0xFF));
            for (int j = 0; j < this.dBG.length; ++j) {
                aij_12.writeInt(this.dBG[j]);
            }
        }
        aij_12.writeShort((short)(this.Fe.length & 0xFFFF));
        aij_12.writeBytes(this.Fe);
        aij_12.fe(this.dBH);
        aij_12.writeShort(this.dBI);
        aij_12.aVj();
    }
}

