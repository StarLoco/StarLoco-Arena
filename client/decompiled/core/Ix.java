/*
 * Decompiled with CFR 0.152.
 */
class Ix
extends sP {
    final /* synthetic */ Ky bhy;

    private Ix(Ky ky, String string) {
        this.bhy = ky;
        super(ky, string);
    }

    public long ef() {
        try {
            return ((String)this.amb).length();
        }
        catch (NullPointerException nullPointerException) {
            return 0L;
        }
    }

    /* synthetic */ Ix(Ky ky, String string, xq_1 xq_12) {
        this(ky, string);
    }
}

