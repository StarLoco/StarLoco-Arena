/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Renamed from fI
 */
public class fi_0 {
    public static final void g(Object object) {
        block19: {
            String string = "execute";
            zp zp2 = null;
            try {
                Object object2;
                Object object3;
                if (object instanceof zp) {
                    zp2 = (zp)object;
                } else if (object instanceof rs_0 && (object3 = ((rs_0)(object2 = (rs_0)object)).adO()) != null && object3 instanceof zp && object3 instanceof dm_1) {
                    zp2 = (zp)object3;
                }
                if (zp2 != null) {
                    object2 = null;
                    try {
                        object3 = zp2.Go();
                        if (object3 != null && ((String)object3).trim().length() > 0) {
                            Class<?> clazz;
                            Method method;
                            object2 = "get" + ((String)object3).trim().substring(0, 1).toUpperCase();
                            if (((String)object3).length() > 1) {
                                object2 = (String)object2 + ((String)object3).substring(1);
                            }
                            if ((method = (clazz = zp2.getClass()).getMethod((String)object2, new Class[0])) == null) break block19;
                            Object object4 = method.invoke(zp2, null);
                            if (object4 != null) {
                                String string2 = object4.toString();
                                if (string2 != null && string2.trim().length() > 0) {
                                    string = string2.trim();
                                    Method method2 = null;
                                    method2 = zp2.getClass().getMethod(string, new Class[0]);
                                    if (method2 == null) {
                                        throw new eq_2("No public " + string + "() in " + zp2.getClass());
                                    }
                                    method2.invoke(zp2, null);
                                    if (object instanceof rs_0) {
                                        ((rs_0)object).ai(null);
                                    }
                                    break block19;
                                }
                                throw new eq_2("Dispatchable Task attribute '" + ((String)object3).trim() + "' not set or value is empty.");
                            }
                            throw new eq_2("Dispatchable Task attribute '" + ((String)object3).trim() + "' not set or value is empty.");
                        }
                        throw new eq_2("Action Parameter Name must not be empty for Dispatchable Task.");
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        throw new eq_2("No public " + (String)object2 + "() in " + object.getClass());
                    }
                }
                object2 = null;
                object2 = object.getClass().getMethod(string, new Class[0]);
                if (object2 == null) {
                    throw new eq_2("No public " + string + "() in " + object.getClass());
                }
                ((Method)object2).invoke(object, null);
                if (object instanceof rs_0) {
                    ((rs_0)object).ai(null);
                }
            }
            catch (InvocationTargetException invocationTargetException) {
                Throwable throwable = invocationTargetException.getTargetException();
                if (throwable instanceof eq_2) {
                    throw (eq_2)throwable;
                }
                throw new eq_2(throwable);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new eq_2(noSuchMethodException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new eq_2(illegalAccessException);
            }
        }
    }
}

