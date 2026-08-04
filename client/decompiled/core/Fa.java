/*
 * Decompiled with CFR 0.152.
 */
public class Fa {
    public static final short LOCAL = -1;
    public short aUs;
    public String m_name;
    public int asw;

    public final void b(acf acf2) {
        this.m_name = acf2.readString();
        this.asw = acf2.readInt();
        this.aUs = acf2.readShort();
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeString(this.m_name);
        aij_12.writeInt(this.asw);
        aij_12.writeShort(this.aUs);
    }

    public final int getSize() {
        return 6 + this.m_name.length();
    }
}

