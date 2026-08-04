/*
 * Decompiled with CFR 0.152.
 */
public abstract class OI
extends kB {
    protected transient byte[] bCp;
    protected static final byte bCq = 0;
    protected static final byte bCr = 1;
    protected static final byte bCs = 2;

    public OI() {
    }

    public OI(int n2) {
        this(n2, 0.5f);
    }

    public OI(int n2, float f) {
        this.EC = f;
        this.N((int)Math.ceil((float)n2 / f));
    }

    public Object clone() {
        OI oI = (OI)super.clone();
        oI.bCp = (byte[])this.bCp.clone();
        return oI;
    }

    protected int capacity() {
        return this.bCp.length;
    }

    protected void O(int n2) {
        this.bCp[n2] = 2;
        super.O(n2);
    }

    protected int N(int n2) {
        int n3 = super.N(n2);
        this.bCp = new byte[n3];
        return n3;
    }
}

