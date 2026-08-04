/*
 * Decompiled with CFR 0.152.
 */
public class tL
extends so_0 {
    private final byte[] aeQ;

    public tL(byte[] byArray) {
        this.aeQ = byArray;
    }

    public byte[] encode() {
        byte[] byArray = this.aeQ;
        if (this.aeQ == null) {
            byArray = new byte[]{};
        }
        return this.a((byte)0, byArray);
    }

    public int getId() {
        return 3;
    }

    public void f(int n2) {
    }
}

