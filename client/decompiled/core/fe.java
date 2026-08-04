/*
 * Decompiled with CFR 0.152.
 */
public class fe
extends Exception {
    private static final long serialVersionUID = -3132040414328475658L;
    Throwable pV;

    public fe(String string) {
        super(string);
    }

    public fe(String string, Throwable throwable) {
        super(string);
        this.pV = throwable;
    }

    public Throwable getCause() {
        return this.pV;
    }
}

