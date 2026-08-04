/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class amh {
    final String name;
    private static final Map cGx = Collections.synchronizedMap(new HashMap());

    protected amh(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.name = string;
        amh.A(this.getClass()).put(string, this);
    }

    public final boolean equals(Object object) {
        return this == object;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    static Map A(Class clazz) {
        HashMap hashMap = (HashMap)cGx.get(clazz);
        if (hashMap != null) {
            return hashMap;
        }
        hashMap = new HashMap();
        cGx.put(clazz, hashMap);
        return hashMap;
    }

    protected static final amh g(String string, Class clazz) {
        amh amh2 = (amh)amh.A(clazz).get(string);
        if (amh2 == null) {
            throw new aar_0(string);
        }
        return amh2;
    }

    public String toString() {
        return this.name;
    }
}

