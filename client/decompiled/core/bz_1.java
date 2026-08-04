/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from Bz
 */
public class bz_1 {
    private static Logger a = Logger.getLogger(bz_1.class);

    public static boolean b(Method method, Object object) {
        Class<?>[] classArray = method.getParameterTypes();
        if (classArray.length != 1) {
            return false;
        }
        Class<?> clazz = classArray[0];
        if (object == null) {
            return !clazz.isPrimitive();
        }
        Class<?> clazz2 = object.getClass();
        return !(clazz.isArray() && !clazz2.isArray() || !clazz.isArray() && clazz2.isArray());
    }

    public static boolean a(Method method, Object ... objectArray) {
        Class<?>[] classArray = method.getParameterTypes();
        if (objectArray.length != classArray.length) {
            return false;
        }
        for (int j = 0; j < classArray.length; ++j) {
            if (!(objectArray[j] == null ? classArray[j].isPrimitive() : !classArray[j].isAssignableFrom(objectArray[j].getClass()))) continue;
            return false;
        }
        return true;
    }

    public static void a(Method method, Object object, Object[] objectArray) {
        Serializable serializable;
        if (method == null || objectArray == null) {
            return;
        }
        Class<?>[] classArray = method.getParameterTypes();
        if (classArray.length != objectArray.length) {
            throw new Exception("nombre de param\u00e8tres attendus : " + classArray.length + ". Trouv\u00e9s : " + objectArray.length);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int j = 0; j < classArray.length; ++j) {
            serializable = classArray[j];
            Object object2 = objectArray[j];
            if (object2 == null && !((Class)serializable).isPrimitive() || object2 != null && ((Class)serializable).isAssignableFrom(object2.getClass())) {
                arrayList.add(object2);
                continue;
            }
            if (serializable.equals(String.class)) {
                arrayList.add(Gr.getString(object2));
                continue;
            }
            if (serializable.equals(Boolean.TYPE) || serializable.equals(Boolean.class)) {
                arrayList.add(Gr.getBoolean(object2));
                continue;
            }
            if (serializable.equals(Integer.TYPE) || serializable.equals(Integer.class)) {
                arrayList.add(Gr.R(object2));
                continue;
            }
            if (serializable.equals(Float.TYPE) || serializable.equals(Float.class)) {
                arrayList.add(Float.valueOf(Gr.getFloat(object2)));
                continue;
            }
            if (serializable.equals(Double.TYPE) || serializable.equals(Double.class)) {
                arrayList.add(Gr.getDouble(object2));
                continue;
            }
            if (serializable.equals(Long.TYPE) || serializable.equals(Long.class)) {
                arrayList.add(Gr.getLong(object2));
                continue;
            }
            if (object2.getClass().equals(String.class)) {
                arrayList.add(if_1.UG().c((Class)serializable, (String)object2));
                continue;
            }
            throw new Exception("Impossible de convertir la valeur donn\u00e9e");
        }
        try {
            method.invoke(object, arrayList.toArray());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("IllegalArgumentException : method=").append(method).append(", parametres=");
            for (int j = 0; j < arrayList.size(); ++j) {
                ((StringBuilder)serializable).append(arrayList.get(j));
                if (j == arrayList.size() - 1) break;
                ((StringBuilder)serializable).append(", ");
            }
            a.error((Object)serializable);
        }
    }

    public static void a(String string, na_1 na_12, sm_0 sm_02, int n2, String string2, jn_2 jn_22) {
        Object object;
        Object object2 = null;
        Object object3 = null;
        if ((object3 == null || jn_22 != null) && sm_02 != null) {
            object2 = string2 != null ? sm_02.getFieldValue(string2) : sm_02.getValue();
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (jn_22 != null) {
            object3 = jn_22.getResult(object2);
        }
        Object object4 = object = object3 != null ? object3 : object2;
        if (object instanceof String && yt_1.caS != n2 ? na_12.setXMLAttribute(n2, (String)object, if_1.UG()) : na_12.setPropertyAttribute(n2, object)) {
            return;
        }
        aLH aLH2 = ye_2.amJ().w(na_12.getClass());
        Method method = object3 != null ? aLH2.h(string, object3.getClass()) : (object2 != null ? aLH2.h(string, object2.getClass()) : aLH2.iX(string));
        if (method == null) {
            return;
        }
        Class<?>[] classArray = method.getParameterTypes();
        if (classArray.length != 1) {
            throw new Exception("La m\u00e9thode prend " + classArray.length + " param\u00e8tres");
        }
        Class clazz = classArray[0];
        if (clazz.isPrimitive()) {
            clazz = anh_0.B(clazz);
        }
        if (object3 != null && clazz.isAssignableFrom(object3.getClass())) {
            arrayList.add(object3);
        } else if (object2 == null || object2 != null && clazz.isAssignableFrom(object2.getClass())) {
            arrayList.add(object2);
        } else if (clazz.equals(String.class)) {
            arrayList.add(Gr.getString(object2));
        } else if (clazz.equals(Boolean.class) || clazz.equals(Boolean.TYPE)) {
            arrayList.add(Gr.getBoolean(object2));
        } else if (clazz.equals(Integer.class) || clazz.equals(Integer.TYPE)) {
            arrayList.add(Gr.R(object2));
        } else if (clazz.equals(Float.class) || clazz.equals(Float.TYPE)) {
            arrayList.add(Float.valueOf(Gr.getFloat(object2)));
        } else if (clazz.equals(Double.class) || clazz.equals(Double.TYPE)) {
            arrayList.add(Gr.getDouble(object2));
        } else if (clazz.equals(Long.class) || clazz.equals(Long.TYPE)) {
            arrayList.add(Gr.getLong(object2));
        } else if (object2.getClass().equals(String.class)) {
            arrayList.add(if_1.UG().c(clazz, (String)object2));
        } else {
            throw new Exception("Impossible de convertir la valeur donn\u00e9e (attendu = " + clazz + ", eu = " + object2.getClass() + ")");
        }
        try {
            method.invoke(na_12, arrayList.toArray());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IllegalArgumentException : method=").append(method).append(", parametres=");
            for (int j = 0; j < arrayList.size(); ++j) {
                stringBuilder.append(arrayList.get(j));
                if (j == arrayList.size() - 1) break;
                stringBuilder.append(", ");
            }
            a.error((Object)stringBuilder);
        }
    }
}

