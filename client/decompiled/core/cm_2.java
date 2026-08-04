/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from cm
 */
public class cm_2
extends jw_1 {
    private String ip;
    private int iq = 100;

    public final boolean a(arp_0 arp_02) {
        if (ej_0.am(100) > this.iq) {
            return false;
        }
        arp_02.aY(this.ip);
        return true;
    }

    public final void a(byte by, acf acf2) {
        this.ip = acf2.readString();
        if (by == 2) {
            this.iq = acf2.readByte();
        }
    }

    public void a(aij_1 aij_12) {
    }

    public int getSize() {
        return 2 + this.ip.length() + (this.iq < 100 ? 1 : 0) + super.getSize();
    }

    public aro ek() {
        return aro.cPs;
    }
}

