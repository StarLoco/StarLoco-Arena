/*
 * Decompiled with CFR 0.152.
 */
public abstract class afB {
    public static final String crl = "Using an item with a reference counter < 0 is forbidden";
    private static final short crm = 10;
    private short crn = 0;
    private short cro = (short)10;

    public final boolean exists() {
        return this.crn >= 0;
    }

    public void HE() {
        assert (this.exists()) : "Using an item with a reference counter < 0 is forbidden";
        assert (this.crn < Short.MAX_VALUE) : "Too many references added";
        this.crn = (short)(this.crn + 1);
        this.cro = (short)10;
    }

    public void HF() {
        if (this.exists() && (this.crn = (short)(this.crn - 1)) < 0) {
            this.avf();
        }
    }

    public final short avb() {
        return this.crn;
    }

    public final short avc() {
        return this.cro;
    }

    public final void avd() {
        if (this.crn == 0 && this.cro > Short.MIN_VALUE) {
            this.cro = (short)(this.cro - 1);
        }
    }

    public final void ave() {
        this.cro = (short)10;
    }

    public void delete() {
    }

    protected void avf() {
        this.delete();
    }

    final void avg() {
        this.crn = 0;
    }
}

