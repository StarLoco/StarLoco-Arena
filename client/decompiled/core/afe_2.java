/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aFE
 */
public class afe_2 {
    public int dHq;
    public String m_name;
    public int dHr;
    public boolean dHs;
    public int dHt;
    public byte dHu;
    public int dHv;
    public boolean dHw = false;

    public afe_2() {
    }

    public afe_2(int n2, String string, int n3, boolean bl2, int n4, byte by, int n5) {
        this.dHq = n2;
        this.m_name = string;
        this.dHr = n3;
        this.dHs = bl2;
        this.dHt = n4;
        this.dHu = by;
        this.dHv = n5;
    }

    final void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AmbienceData.load must not be null");
        }
        this.dHq = acf2.readInt();
        short s = acf2.readShort();
        byte[] byArray = acf2.jE(s);
        this.m_name = aey_0.V(byArray);
        this.dHr = acf2.readInt();
        this.dHs = acf2.aqE();
        this.dHt = acf2.readInt();
        this.dHu = acf2.readByte();
        this.dHv = acf2.readInt();
    }

    final void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/environment/AmbienceData.save must not be null");
        }
        aij_12.writeInt(this.dHq);
        byte[] byArray = aey_0.hH(this.m_name);
        aij_12.writeShort((short)byArray.length);
        aij_12.writeBytes(byArray);
        aij_12.writeInt(this.dHr);
        aij_12.fe(this.dHs);
        aij_12.writeInt(this.dHt);
        aij_12.writeByte(this.dHu);
        aij_12.writeInt(this.dHv);
    }
}

