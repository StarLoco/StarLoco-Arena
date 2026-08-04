/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aMu
 */
public class amu_1
extends ag {
    private int dXR;
    private int dXS;
    private int dXT;
    private int dXU;

    public amu_1(int n2, int n3, int n4, int n5) {
        super(aBH.dss);
        this.dXR = n2;
        this.dXS = n3;
        this.dXT = n4;
        this.dXU = n5;
    }

    public int aXa() {
        return this.dXR;
    }

    public int aXb() {
        return this.dXS;
    }

    public int aXc() {
        return this.dXT;
    }

    public int aXd() {
        return this.dXU;
    }

    public String toString() {
        return '{' + this.getClass().getSimpleName() + " : OpenGL version " + this.dXR + '.' + this.dXS + " not available : " + this.dXT + '.' + this.dXU + " present on system}";
    }
}

