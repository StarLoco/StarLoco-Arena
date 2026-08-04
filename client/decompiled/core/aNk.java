/*
 * Decompiled with CFR 0.152.
 */
public abstract class aNk {
    protected String value;
    private int index = -1;
    static Class bef;

    public abstract String[] getValues();

    protected aNk() {
    }

    public static aNk p(Class clazz, String string) {
        if (!(bef == null ? (bef = aNk.a("aNk")) : bef).isAssignableFrom(clazz)) {
            throw new eq_2("You have to provide a subclass from EnumeratedAttribut as clazz-parameter.");
        }
        aNk aNk2 = null;
        try {
            aNk2 = (aNk)clazz.newInstance();
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
        aNk2.setValue(string);
        return aNk2;
    }

    public final void setValue(String string) {
        int n2 = this.lI(string);
        if (n2 == -1) {
            throw new eq_2(string + " is not a legal value for this attribute");
        }
        this.index = n2;
        this.value = string;
    }

    public final boolean containsValue(String string) {
        return this.lI(string) != -1;
    }

    public final int lI(String string) {
        String[] stringArray = this.getValues();
        if (stringArray == null || string == null) {
            return -1;
        }
        for (int j = 0; j < stringArray.length; ++j) {
            if (!string.equals(stringArray[j])) continue;
            return j;
        }
        return -1;
    }

    public final String getValue() {
        return this.value;
    }

    public final int getIndex() {
        return this.index;
    }

    public String toString() {
        return this.getValue();
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

