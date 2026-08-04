/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nH
 */
public final class nh_1
extends aFA {
    private final Object value;
    private final ahr_1 yo;

    public nh_1(ahr_1 ahr_12, Object object) {
        super(ahr_12, null);
        this.yo = ahr_12;
        this.value = object;
    }

    public boolean isLiteral() {
        return true;
    }

    public Object sA() {
        return this.value;
    }

    public String toString() {
        return ahr_1.aA(this.value);
    }
}

