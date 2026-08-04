/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Hr
 */
public class hr_0
implements apG {
    private Class ach = Enum.class;

    public Enum ew(String string) {
        return null;
    }

    public Enum d(Class clazz, String string) {
        Object var3_3 = null;
        try {
            var3_3 = Enum.valueOf(clazz, string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (var3_3 != null) {
            return var3_3;
        }
        if (((Enum[])clazz.getEnumConstants()).length > 0) {
            return ((Enum[])clazz.getEnumConstants())[0];
        }
        return null;
    }

    public Class uk() {
        return this.ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        zp_12.j(clazz);
        Enum enum_ = null;
        try {
            enum_ = (Enum)Enum.valueOf(clazz, string.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (enum_ == null && ((Enum[])clazz.getEnumConstants()).length > 0) {
            enum_ = ((Enum[])clazz.getEnumConstants())[0];
        }
        Enum enum_2 = enum_;
        return clazz.getSimpleName() + "." + enum_2.toString();
    }
}

