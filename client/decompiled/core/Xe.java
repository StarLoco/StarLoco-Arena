/*
 * Decompiled with CFR 0.152.
 */
public class Xe {
    public byte bWQ;
    public byte bWR;
    public byte bWS;
    public short bWT;
    public short bWU;
    public byte bWV;
    public short fs;
    public short ft;
    public short adE;
    public short adF;
    public byte bWW;
    public byte bWX;

    public void n(acf acf2) {
        this.bWQ = acf2.readByte();
        this.bWR = acf2.readByte();
        this.bWS = acf2.readByte();
        this.bWT = acf2.readShort();
        this.bWU = acf2.readShort();
        this.bWV = acf2.readByte();
        this.fs = acf2.readShort();
        this.ft = acf2.readShort();
        this.adE = acf2.readShort();
        this.adF = acf2.readShort();
        this.bWW = acf2.readByte();
        this.bWX = acf2.readByte();
    }

    public void i(aij_1 aij_12) {
        aij_12.writeByte(this.bWQ);
        aij_12.writeByte(this.bWR);
        aij_12.writeByte(this.bWS);
        aij_12.writeShort(this.bWT);
        aij_12.writeShort(this.bWU);
        aij_12.writeByte(this.bWV);
        aij_12.writeShort(this.fs);
        aij_12.writeShort(this.ft);
        aij_12.writeShort(this.adE);
        aij_12.writeShort(this.adF);
        aij_12.writeByte(this.bWW);
        aij_12.writeByte(this.bWX);
    }
}

