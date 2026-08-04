/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alC
 */
public class alc_0
extends apm_0 {
    private static final boolean DEBUG = false;
    private ClassLoader civ;

    public alc_0(ClassLoader classLoader) {
        super(null);
        if (classLoader == null) {
            throw new NullPointerException();
        }
        this.civ = classLoader;
        super.aYV();
    }

    public alc_0() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ClassLoader getClassLoader() {
        return this.civ;
    }

    protected asn dx(String string) {
        Class<?> clazz;
        try {
            clazz = Class.forName(sA.toClassName(string), false, this.civ);
        }
        catch (ClassNotFoundException classNotFoundException) {
            if (classNotFoundException.getException() == null) {
                return null;
            }
            throw classNotFoundException;
        }
        we_1 we_12 = new we_1(clazz, this);
        this.l(we_12);
        return we_12;
    }
}

