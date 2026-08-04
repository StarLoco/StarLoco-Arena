/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from anh
 */
public class anh_0
implements apG {
    public static final Class ach = Object.class;

    public Object bw(String string) {
        return null;
    }

    public Object c(Class clazz, String string) {
        if (Boolean.TYPE.equals(clazz) || Boolean.class.equals((Object)clazz)) {
            return Gr.getBoolean(string);
        }
        if (Integer.TYPE.equals(clazz) || Integer.class.equals((Object)clazz)) {
            return Gr.R(string);
        }
        if (Long.TYPE.equals(clazz) || Long.class.equals((Object)clazz)) {
            return Gr.getLong(string);
        }
        if (Float.TYPE.equals(clazz) || Float.class.equals((Object)clazz)) {
            return Float.valueOf(Gr.getFloat(string));
        }
        if (Double.TYPE.equals(clazz) || Double.class.equals((Object)clazz)) {
            return Gr.getDouble(string);
        }
        if (Byte.TYPE.equals(clazz) || Byte.class.equals((Object)clazz)) {
            return Gr.getByte(string);
        }
        if (Short.TYPE.equals(clazz) || Short.class.equals((Object)clazz)) {
            return Gr.getShort(string);
        }
        return null;
    }

    public Class uk() {
        return ach;
    }

    public static Class B(Class clazz) {
        if (clazz.equals(Boolean.TYPE)) {
            return Boolean.class;
        }
        if (clazz.equals(Double.TYPE)) {
            return Double.class;
        }
        if (clazz.equals(Float.TYPE)) {
            return Float.class;
        }
        if (clazz.equals(Short.TYPE)) {
            return Short.class;
        }
        if (clazz.equals(Integer.TYPE)) {
            return Integer.class;
        }
        if (clazz.equals(Long.TYPE)) {
            return Long.class;
        }
        if (clazz.equals(Character.TYPE)) {
            return Character.class;
        }
        if (clazz.equals(Byte.TYPE)) {
            return Byte.class;
        }
        if (clazz.equals(Void.TYPE)) {
            return Void.class;
        }
        return null;
    }

    public static Class C(Class clazz) {
        if (clazz.equals(Boolean.class)) {
            return Boolean.TYPE;
        }
        if (clazz.equals(Double.class)) {
            return Double.TYPE;
        }
        if (clazz.equals(Float.class)) {
            return Float.TYPE;
        }
        if (clazz.equals(Short.class)) {
            return Short.TYPE;
        }
        if (clazz.equals(Integer.class)) {
            return Integer.TYPE;
        }
        if (clazz.equals(Long.class)) {
            return Long.TYPE;
        }
        if (clazz.equals(Character.class)) {
            return Character.TYPE;
        }
        if (clazz.equals(Byte.class)) {
            return Byte.TYPE;
        }
        if (clazz.equals(Void.class)) {
            return Void.TYPE;
        }
        return null;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        Object object = this.c(clazz, string);
        if (object instanceof Number) {
            string = "(" + clazz.getName() + ")" + String.valueOf(object);
            if (clazz.equals(Float.class) || clazz.equals(Float.TYPE)) {
                string = string + "f";
            }
        } else {
            string = clazz.equals(Character.class) ? "'" + string + "'" : (object == null ? "null" : object.toString());
        }
        zp_12.j(clazz);
        return string;
    }
}

