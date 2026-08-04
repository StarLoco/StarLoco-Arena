/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/*
 * Renamed from xF
 */
class xf_0
extends jz_0 {
    final Field field;
    private final we_1 azp;

    public xf_0(we_1 we_12, Field field) {
        super(we_12);
        this.azp = we_12;
        this.field = field;
    }

    public amf ib() {
        return we_1.ee(this.field.getModifiers());
    }

    public String getName() {
        return this.field.getName();
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.field.getModifiers());
    }

    public asn tF() {
        return we_1.a(this.azp, this.field.getType());
    }

    public String toString() {
        return sA.toString(this.ic().getDescriptor()) + "." + this.getName();
    }

    public Object getConstantValue() {
        int n2 = this.field.getModifiers();
        Class<?> clazz = this.field.getType();
        if (Modifier.isStatic(n2) && Modifier.isFinal(n2) && (clazz.isPrimitive() || clazz == (we_1.avl == null ? (we_1.avl = we_1.a("java.lang.String")) : we_1.avl))) {
            try {
                return this.field.get(null);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new ajy_2("Field \"" + this.field.getName() + "\" is not accessible", (lc_0)null);
            }
        }
        return null;
    }
}

