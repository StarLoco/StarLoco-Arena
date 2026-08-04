/*
 * Decompiled with CFR 0.152.
 */
public class avT
extends ael_2 {
    private byte dgV = (byte)-1;
    private byte cSe = (byte)-1;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 2, true)) {
            return false;
        }
        this.cSe = byArray[0];
        this.dgV = byArray[1];
        return true;
    }

    public int getId() {
        return 9;
    }

    public void f(int n2) {
        throw new UnsupportedOperationException("Id fixe pour ce message. Ne peut \u00eatre chang\u00e9.");
    }

    public byte aJh() {
        return this.dgV;
    }

    public byte aFt() {
        return this.cSe;
    }
}

