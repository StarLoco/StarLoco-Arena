/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from HN
 */
public class hn_2 {
    public short fL;
    String m_name;
    int asw;

    public final void b(acf acf2) {
        this.fL = acf2.readShort();
        this.m_name = acf2.readString().intern();
        this.asw = acf2.readInt();
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeShort(this.fL);
        aij_12.writeInt(this.asw);
    }

    public final int getSize() {
        return 6;
    }
}

