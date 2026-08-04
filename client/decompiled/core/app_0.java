/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from apP
 */
public class app_0
implements aLH {
    private static Logger a = Logger.getLogger(app_0.class);
    public static final boolean cNx = true;
    public static final String cNy = "checkOut";
    private if_1 cNz;
    private final ArrayList cNA = new ArrayList();
    private final ArrayList cNB = new ArrayList();
    private final ArrayList cNC = new ArrayList();
    private final ArrayList cND = new ArrayList();
    private final Class cNE;
    private Method cNF = null;
    protected Class[] cNG = new Class[]{Object.class, String.class, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE, Long.TYPE, Byte.TYPE, Integer.TYPE};

    public app_0(Class clazz, if_1 if_12) {
        this.cNz = if_12;
        this.cNE = clazz;
        this.aDS();
        this.aDQ();
        this.aDT();
        this.aDU();
        this.aDR();
    }

    public app_0(Class clazz) {
        this(clazz, if_1.UG());
    }

    protected int D(Class clazz) {
        for (int j = 0; j < this.cNG.length; ++j) {
            if (!clazz.isAssignableFrom(this.cNG[j])) continue;
            return j;
        }
        return -1;
    }

    protected void aDQ() {
        Method[] methodArray = this.cNE.getMethods();
        for (int j = 0; j < methodArray.length; ++j) {
            String string = methodArray[j].getName();
            if (!string.startsWith("get")) continue;
            this.cNB.add(methodArray[j]);
        }
    }

    protected void aDR() {
        try {
            this.cNF = this.cNE.getDeclaredMethod(cNy, new Class[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void aDS() {
        this.d(this.cNA, "set");
    }

    protected void aDT() {
        this.d(this.cNC, "prepend");
    }

    protected void aDU() {
        this.d(this.cND, "append");
    }

    public air_1 aDV() {
        air_1 air_12;
        if (this.cNF != null) {
            air_12 = (air_1)this.cNF.invoke(null, new Object[0]);
        } else {
            air_12 = (air_1)this.cNE.newInstance();
            air_12.b();
        }
        return air_12;
    }

    public air_1 a(ur_0 ur_02, String string) {
        air_1 air_12;
        ur_02.j(this.cNE);
        if (this.cNF != null) {
            air_12 = (air_1)this.cNF.invoke(null, new Object[0]);
            ur_02.a(new aKI(this.cNE, string, this.cNE.getSimpleName() + ".checkOut()"));
        } else {
            air_12 = (air_1)this.cNE.newInstance();
            ur_02.a(new aKI(this.cNE, string, "new " + this.cNE.getSimpleName() + "()"));
            if (air_12 instanceof JG) {
                ur_02.a(new aza(null, "onCheckOut", string));
                air_12.b();
            }
        }
        return air_12;
    }

    public air_1 aE(Object object) {
        Class<?> clazz = object.getClass();
        Constructor<?>[] constructorArray = this.cNE.getConstructors();
        for (int j = 0; j < constructorArray.length; ++j) {
            Class<?>[] classArray = constructorArray[j].getParameterTypes();
            if (0 >= classArray.length || !classArray[0].isAssignableFrom(clazz)) continue;
            return (air_1)constructorArray[j].newInstance(object);
        }
        return (air_1)this.cNE.newInstance();
    }

    public air_1 h(Object ... objectArray) {
        if (objectArray != null) {
            int n2;
            Class[] classArray = new Class[objectArray.length];
            Constructor<?>[] constructorArray = this.cNE.getConstructors();
            Constructor<?> constructor = null;
            for (n2 = 0; n2 < classArray.length; ++n2) {
                classArray[n2] = objectArray[n2].getClass();
            }
            for (n2 = 0; constructor == null && n2 < constructorArray.length; ++n2) {
                Class<?>[] classArray2 = constructorArray[n2].getParameterTypes();
                if (classArray2.length != classArray.length) continue;
                constructor = constructorArray[n2];
                for (int j = 0; constructor != null && j < classArray2.length; ++j) {
                    if (classArray2[j].equals(Object.class)) {
                        if (classArray2[j].equals(classArray[j])) continue;
                        constructor = null;
                        continue;
                    }
                    if (classArray2[j].isAssignableFrom(classArray[j])) continue;
                    constructor = null;
                }
            }
            if (constructor != null) {
                return (air_1)constructor.newInstance(objectArray);
            }
            throw new IllegalArgumentException("Impossible de trouver de constructeur pour les types : " + classArray);
        }
        return (air_1)this.cNE.newInstance();
    }

    public Class abM() {
        return this.cNE;
    }

    public Method E(Class clazz) {
        Method method = null;
        int n2 = this.cNA.size();
        for (int j = 0; j < n2; ++j) {
            Method method2 = (Method)this.cNA.get(j);
            Class<?>[] classArray = method2.getParameterTypes();
            if (classArray == null || 0 >= classArray.length || !clazz.equals(classArray[0])) continue;
            method = method2;
            break;
        }
        return method;
    }

    public Method iV(String string) {
        return this.b(this.cNA, string);
    }

    public Method iW(String string) {
        return this.b(this.cNB, string);
    }

    public Method iX(String string) {
        return this.a(this.cNA, string, "set");
    }

    public Method h(String string, Class clazz) {
        return this.a(this.cNA, string, "set", clazz);
    }

    public Method iY(String string) {
        return this.a(this.cNB, string, "get");
    }

    public Method i(String string, Class clazz) {
        return this.a(this.cNB, string, "get", clazz);
    }

    public Method iZ(String string) {
        return this.a(this.cND, string, "append");
    }

    public Method j(String string, Class clazz) {
        return this.a(this.cND, string, "append", clazz);
    }

    public Method ja(String string) {
        return this.a(this.cNC, string, "prepend");
    }

    public Method k(String string, Class clazz) {
        return this.a(this.cNC, string, "prepend", clazz);
    }

    private Method b(ArrayList arrayList, String string) {
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Method method = (Method)arrayList.get(j);
            if (!method.getName().equals(string)) continue;
            return method;
        }
        return null;
    }

    private ArrayList c(ArrayList arrayList, String string) {
        ArrayList<Method> arrayList2 = new ArrayList<Method>();
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Method method = (Method)arrayList.get(j);
            if (!method.getName().equalsIgnoreCase(string)) continue;
            arrayList2.add(method);
        }
        return arrayList2;
    }

    private Method a(ArrayList arrayList, String string, String string2) {
        Method method = null;
        string = string2 + string;
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Method method2 = (Method)arrayList.get(j);
            if (!method2.getName().equalsIgnoreCase(string)) continue;
            method = method2;
            break;
        }
        return method;
    }

    private Method a(ArrayList arrayList, String string, String string2, Class clazz) {
        Method method = null;
        string = string2 + string;
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            Class<?>[] classArray;
            Method method2 = (Method)arrayList.get(j);
            if (!method2.getName().equalsIgnoreCase(string)) continue;
            method = method2;
            if (clazz == null || (classArray = method2.getParameterTypes()).length > 0 && classArray[0].isAssignableFrom(clazz)) break;
        }
        return method;
    }

    private void d(ArrayList arrayList, String string) {
        Method[] methodArray = this.cNE.getMethods();
        for (int j = 0; j < methodArray.length; ++j) {
            String string2 = methodArray[j].getName();
            if (!string2.startsWith(string) || methodArray[j].getParameterTypes().length != 1) continue;
            int n2 = arrayList.size();
            if (if_1.UG().n(methodArray[j].getParameterTypes()[0])) {
                n2 = 0;
            }
            if (arrayList.contains(methodArray[j])) {
                a.warn((Object)("La classe " + this.cNE.getSimpleName() + " poss\u00e8de plusieurs fonctions poss\u00e9dant le nom " + methodArray[j].getName()));
            }
            arrayList.add(n2, methodArray[j]);
        }
    }
}

