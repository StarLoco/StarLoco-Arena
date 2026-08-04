/*
 * Decompiled with CFR 0.152.
 */
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/*
 * Renamed from nJ
 */
public class nj_1
extends ii_2 {
    private static final Class[] OW = new Class[]{String.class};
    protected Object obj;
    protected Class OX;
    protected PropertyDescriptor[] OY;
    protected MethodDescriptor[] OZ;

    public nj_1(Object object) {
        this.obj = object;
        this.OX = object.getClass();
    }

    protected void introspect() {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.obj.getClass());
            this.OY = beanInfo.getPropertyDescriptors();
            this.OZ = beanInfo.getMethodDescriptors();
        }
        catch (IntrospectionException introspectionException) {
            this.eg("Failed to introspect " + this.obj + ": " + introspectionException.getMessage());
            this.OY = new PropertyDescriptor[0];
            this.OZ = new MethodDescriptor[0];
        }
    }

    public void setProperty(String string, String string2) {
        if (string2 == null) {
            return;
        }
        PropertyDescriptor propertyDescriptor = this.getPropertyDescriptor(string = Introspector.decapitalize(string));
        if (propertyDescriptor == null) {
            this.ef("No such property [" + string + "] in " + this.OX.getName() + ".");
        } else {
            try {
                this.setProperty(propertyDescriptor, string, string2);
            }
            catch (aqp_0 aqp_02) {
                this.d("Failed to set property [" + string + "] to value \"" + string2 + "\". ", aqp_02);
            }
        }
    }

    public void setProperty(PropertyDescriptor propertyDescriptor, String string, String string2) {
        Object object;
        Method method = propertyDescriptor.getWriteMethod();
        if (method == null) {
            throw new aqp_0("No setter for property [" + string + "].");
        }
        Class<?>[] classArray = method.getParameterTypes();
        if (classArray.length != 1) {
            throw new aqp_0("#params for setter != 1");
        }
        try {
            object = this.convertArg(string2, classArray[0]);
        }
        catch (Throwable throwable) {
            throw new aqp_0("Conversion to type [" + classArray[0] + "] failed. ", throwable);
        }
        if (object == null) {
            throw new aqp_0("Conversion to type [" + classArray[0] + "] failed.");
        }
        try {
            method.invoke(this.obj, object);
        }
        catch (Exception exception) {
            throw new aqp_0(exception);
        }
    }

    public rz_0 bk(String string) {
        Object object;
        String string2 = this.bn(string);
        Method method = this.bl(string2);
        if (method != null) {
            object = this.b(method);
            switch (aPu.cMd[((Enum)object).ordinal()]) {
                case 1: {
                    return rz_0.bKV;
                }
                case 2: {
                    return rz_0.bKY;
                }
                case 3: {
                    return rz_0.bKZ;
                }
            }
        }
        if ((object = this.bm(string)) != null) {
            return this.b((Method)object);
        }
        return rz_0.bKV;
    }

    private Method bl(String string) {
        string = this.bn(string);
        Method method = this.getMethod("add" + string);
        return method;
    }

    private Method bm(String string) {
        String string2 = Introspector.decapitalize(string);
        PropertyDescriptor propertyDescriptor = this.getPropertyDescriptor(string2);
        if (propertyDescriptor != null) {
            return propertyDescriptor.getWriteMethod();
        }
        return null;
    }

    private Class a(Method method) {
        if (method == null) {
            return null;
        }
        Class<?>[] classArray = method.getParameterTypes();
        if (classArray.length != 1) {
            return null;
        }
        return classArray[0];
    }

    private rz_0 b(Method method) {
        Class clazz = this.a(method);
        if (clazz == null) {
            return rz_0.bKV;
        }
        Package package_ = clazz.getPackage();
        if (clazz.isPrimitive()) {
            return rz_0.bKW;
        }
        if (package_ != null && "java.lang".equals(package_.getName())) {
            return rz_0.bKW;
        }
        if (this.h(clazz)) {
            return rz_0.bKW;
        }
        if (clazz.isEnum()) {
            return rz_0.bKW;
        }
        return rz_0.bKX;
    }

    public Class a(it_0 it_02) {
        rz_0 rz_02 = it_02.Va();
        switch (rz_02) {
            case bKX: {
                Method method = this.bm(it_02.Vc());
                Class clazz = this.a(method);
                if (clazz != null && this.g(clazz)) {
                    return clazz;
                }
                return null;
            }
            case bKZ: {
                Method method = this.bl(it_02.Vc());
                Class clazz = this.a(method);
                if (clazz != null && this.g(clazz)) {
                    return clazz;
                }
                return null;
            }
        }
        throw new IllegalArgumentException((Object)((Object)rz_02) + " is not valid type in this method");
    }

    private boolean g(Class clazz) {
        if (clazz.isInterface()) {
            return false;
        }
        try {
            Object t = clazz.newInstance();
            return t != null;
        }
        catch (InstantiationException instantiationException) {
            return false;
        }
        catch (IllegalAccessException illegalAccessException) {
            return false;
        }
    }

    public Class sG() {
        return this.OX;
    }

    public void e(String string, Object object) {
        Method method = this.bl(string);
        if (method != null) {
            Class[] classArray = method.getParameterTypes();
            if (!this.a(string, method, classArray, object)) {
                return;
            }
            this.a(method, object);
        } else {
            this.eg("Could not find method [add" + string + "] in class [" + this.OX.getName() + "].");
        }
    }

    void a(Method method, Object object) {
        Class<?> clazz = object.getClass();
        try {
            method.invoke(this.obj, object);
        }
        catch (Exception exception) {
            this.e("Could not invoke method " + method.getName() + " in class " + this.obj.getClass().getName() + " with parameter of type " + clazz.getName(), exception);
        }
    }

    public void f(String string, String string2) {
        Object object;
        if (string2 == null) {
            return;
        }
        Method method = this.bl(string = this.bn(string));
        if (method == null) {
            this.eg("No adder for property [" + string + "].");
            return;
        }
        Class[] classArray = method.getParameterTypes();
        this.a(string, method, classArray, string2);
        try {
            object = this.convertArg(string2, classArray[0]);
        }
        catch (Throwable throwable) {
            this.e("Conversion to type [" + classArray[0] + "] failed. ", throwable);
            return;
        }
        if (object != null) {
            this.a(method, (Object)string2);
        }
    }

    public void f(String string, Object object) {
        String string2 = Introspector.decapitalize(string);
        PropertyDescriptor propertyDescriptor = this.getPropertyDescriptor(string2);
        if (propertyDescriptor == null) {
            this.ef("Could not find PropertyDescriptor for [" + string + "] in " + this.OX.getName());
            return;
        }
        Method method = propertyDescriptor.getWriteMethod();
        if (method == null) {
            this.ef("Not setter method for property [" + string + "] in " + this.obj.getClass().getName());
            return;
        }
        Class[] classArray = method.getParameterTypes();
        if (!this.a(string, method, classArray, object)) {
            return;
        }
        try {
            this.a(method, object);
        }
        catch (Exception exception) {
            this.e("Could not set component " + this.obj + " for parent component " + this.obj, exception);
        }
    }

    private boolean a(String string, Method method, Class[] classArray, Object object) {
        Class<?> clazz = object.getClass();
        if (classArray.length != 1) {
            this.eg("Wrong number of parameters in setter method for property [" + string + "] in " + this.obj.getClass().getName());
            return false;
        }
        if (!classArray[0].isAssignableFrom(object.getClass())) {
            this.eg("A \"" + clazz.getName() + "\" object is not assignable to a \"" + classArray[0].getName() + "\" variable.");
            this.eg("The class \"" + classArray[0].getName() + "\" was loaded by ");
            this.eg("[" + classArray[0].getClassLoader() + "] whereas object of type ");
            this.eg("\"" + clazz.getName() + "\" was loaded by [" + clazz.getClassLoader() + "].");
            return false;
        }
        return true;
    }

    private String bn(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    protected Object convertArg(String string, Class clazz) {
        if (string == null) {
            return null;
        }
        String string2 = string.trim();
        if (String.class.isAssignableFrom(clazz)) {
            return string;
        }
        if (Integer.TYPE.isAssignableFrom(clazz)) {
            return new Integer(string2);
        }
        if (Long.TYPE.isAssignableFrom(clazz)) {
            return new Long(string2);
        }
        if (Float.TYPE.isAssignableFrom(clazz)) {
            return new Float(string2);
        }
        if (Double.TYPE.isAssignableFrom(clazz)) {
            return new Double(string2);
        }
        if (Boolean.TYPE.isAssignableFrom(clazz)) {
            if ("true".equalsIgnoreCase(string2)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(string2)) {
                return Boolean.FALSE;
            }
        } else {
            if (clazz.isEnum()) {
                return this.a(string, clazz);
            }
            if (this.h(clazz)) {
                return this.a(clazz, string);
            }
        }
        return null;
    }

    boolean h(Class clazz) {
        try {
            Method method = clazz.getMethod("valueOf", OW);
            int n2 = method.getModifiers();
            if (Modifier.isStatic(n2)) {
                return true;
            }
        }
        catch (SecurityException securityException) {
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        return false;
    }

    Object a(Class clazz, String string) {
        try {
            Method method = clazz.getMethod("valueOf", OW);
            return method.invoke(null, string);
        }
        catch (Exception exception) {
            this.eg("Failed to invoke valueOf{} method in class [" + clazz.getName() + "] with value [" + string + "]");
            return null;
        }
    }

    protected Object a(String string, Class clazz) {
        try {
            Method method = clazz.getMethod("valueOf", OW);
            return method.invoke(null, string);
        }
        catch (Exception exception) {
            this.e("Failed to convert value [" + string + "] to enum [" + clazz.getName() + "]", exception);
            return null;
        }
    }

    protected Method getMethod(String string) {
        if (this.OZ == null) {
            this.introspect();
        }
        for (int j = 0; j < this.OZ.length; ++j) {
            if (!string.equals(this.OZ[j].getName())) continue;
            return this.OZ[j].getMethod();
        }
        return null;
    }

    protected PropertyDescriptor getPropertyDescriptor(String string) {
        if (this.OY == null) {
            this.introspect();
        }
        for (int j = 0; j < this.OY.length; ++j) {
            if (!string.equals(this.OY[j].getName())) continue;
            return this.OY[j];
        }
        return null;
    }

    public Object getObj() {
        return this.obj;
    }

    Method a(String string, rz_0 rz_02) {
        Method method;
        String string2 = this.bn(string);
        if (rz_02 == rz_0.bKZ) {
            method = this.bl(string2);
        } else if (rz_02 == rz_0.bKX) {
            method = this.bm(string2);
        } else {
            throw new IllegalStateException((Object)((Object)rz_02) + " not allowed here");
        }
        return method;
    }

    Annotation a(String string, Class clazz, Method method) {
        if (method != null) {
            return method.getAnnotation(clazz);
        }
        return null;
    }

    String a(String string, Method method) {
        Class clazz;
        ajj_1 ajj_12 = (ajj_1)this.a(string, ajj_1.class, method);
        if (ajj_12 != null && (clazz = ajj_12.value()) != null) {
            return clazz.getName();
        }
        return null;
    }

    String b(String string, Method method) {
        Class clazz = this.a(method);
        if (clazz == null) {
            return null;
        }
        boolean bl2 = this.g(clazz);
        if (bl2) {
            return clazz.getName();
        }
        return null;
    }

    public String b(String string, rz_0 rz_02) {
        String string2;
        Method method = this.a(string, rz_02);
        if (method == null) {
            // empty if block
        }
        if ((string2 = this.a(string, method)) != null) {
            return string2;
        }
        return this.b(string, method);
    }
}

