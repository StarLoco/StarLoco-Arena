/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/*
 * Renamed from aed
 */
public class aed_0
extends ClassLoader {
    private static final boolean DEBUG = false;
    private final Map cob;

    public aed_0(Map map) {
        this.cob = map;
    }

    public aed_0(Map map, ClassLoader classLoader) {
        super(classLoader);
        this.cob = map;
    }

    protected Class findClass(String string) {
        byte[] byArray = (byte[])this.cob.get(string);
        if (byArray == null) {
            throw new ClassNotFoundException(string);
        }
        return super.defineClass(string, byArray, 0, byArray.length, this.getClass().getProtectionDomain());
    }

    public boolean equals(Object object) {
        if (!(object instanceof aed_0)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        aed_0 aed_02 = (aed_0)object;
        Object object2 = this.getParent();
        Object object3 = aed_02.getParent();
        if (object2 == null ? object3 != null : !object2.equals(object3)) {
            return false;
        }
        if (this.cob.size() != aed_02.cob.size()) {
            return false;
        }
        object2 = this.cob.entrySet().iterator();
        while (object2.hasNext()) {
            object3 = (Map.Entry)object2.next();
            byte[] byArray = (byte[])aed_02.cob.get(object3.getKey());
            if (byArray == null) {
                return false;
            }
            if (Arrays.equals((byte[])object3.getValue(), byArray)) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        int n2 = this.getParent().hashCode();
        Iterator iterator = this.cob.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            n2 ^= entry.getKey().hashCode();
            byte[] byArray = (byte[])entry.getValue();
            for (int j = 0; j < byArray.length; ++j) {
                n2 = 31 * n2 ^ byArray[j];
            }
        }
        return n2;
    }
}

