/*
 * Decompiled with CFR 0.152.
 */
public class Gr {
    public static String getString(Object object) {
        return String.valueOf(object);
    }

    public static boolean getBoolean(Object object) {
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof String) {
            return Boolean.valueOf((String)object);
        }
        return false;
    }

    public static int R(Object object) {
        return Gr.d(object, 0);
    }

    public static int d(Object object, int n2) {
        try {
            if (object instanceof Number) {
                return ((Number)object).intValue();
            }
            if (object instanceof String) {
                return Integer.valueOf((String)object);
            }
        }
        catch (Exception exception) {
            return n2;
        }
        return n2;
    }

    public static double getDouble(Object object) {
        return Gr.a(object, 0.0);
    }

    public static double a(Object object, double d) {
        try {
            if (object instanceof Number) {
                return ((Number)object).doubleValue();
            }
            if (object instanceof String) {
                return Double.valueOf((String)object);
            }
        }
        catch (Exception exception) {
            return d;
        }
        return d;
    }

    public static float getFloat(Object object) {
        return Gr.d(object, 0.0f);
    }

    public static float d(Object object, float f) {
        try {
            if (object instanceof Number) {
                return ((Number)object).floatValue();
            }
            if (object instanceof String) {
                return Float.valueOf((String)object).floatValue();
            }
        }
        catch (Exception exception) {
            return f;
        }
        return f;
    }

    public static long getLong(Object object) {
        return Gr.getLong(object, 0L);
    }

    public static long getLong(Object object, long l2) {
        try {
            if (object instanceof Number) {
                return ((Number)object).longValue();
            }
            if (object instanceof String) {
                return Long.valueOf((String)object);
            }
        }
        catch (Exception exception) {
            return l2;
        }
        return l2;
    }

    public static byte getByte(Object object) {
        return Gr.b(object, (byte)0);
    }

    public static byte b(Object object, byte by) {
        try {
            if (object instanceof Number) {
                return ((Number)object).byteValue();
            }
            if (object instanceof String) {
                return Byte.valueOf((String)object);
            }
        }
        catch (Exception exception) {
            return by;
        }
        return by;
    }

    public static short getShort(Object object) {
        return Gr.a(object, (short)0);
    }

    public static short a(Object object, short s) {
        try {
            if (object instanceof Number) {
                return ((Number)object).shortValue();
            }
            if (object instanceof String) {
                return Short.valueOf((String)object);
            }
        }
        catch (Exception exception) {
            return s;
        }
        return s;
    }
}

