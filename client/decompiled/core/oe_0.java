/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*
 * Renamed from Oe
 */
class oe_0
extends ff_2 {
    final Method method;
    private final we_1 azp;

    public oe_0(we_1 we_12, Method method) {
        super(we_12);
        this.azp = we_12;
        this.method = method;
    }

    public amf ib() {
        return we_1.ee(this.method.getModifiers());
    }

    public String getName() {
        return this.method.getName();
    }

    public asn[] iy() {
        return we_1.a(this.azp, this.method.getParameterTypes());
    }

    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.method.getModifiers());
    }

    public asn ix() {
        return we_1.a(this.azp, this.method.getReturnType());
    }

    public asn[] iz() {
        return we_1.a(this.azp, this.method.getExceptionTypes());
    }
}

