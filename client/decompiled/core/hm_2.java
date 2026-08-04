/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/*
 * Renamed from Hm
 */
public final class hm_2 {
    private static final Map bdF = new Hashtable();
    private static final Map bdG = new HashMap(8);
    private static final int bdH = 20;
    private static final String bdI = "...";
    private Hashtable bdJ = new Hashtable();
    private Hashtable bdK = new Hashtable();
    private Hashtable bdL = new Hashtable();
    private Hashtable bdM = new Hashtable();
    private List bdN = new ArrayList();
    private Method bdO = null;
    private Class bdP;
    static Class bdQ;
    static Class bdR;
    static Class bdS;
    static Class bdT;
    static Class bdU;
    static Class bdV;
    static Class bdW;
    static Class bdX;
    static Class bdY;
    static Class bdZ;
    static Class avl;
    static Class OR;
    static Class bea;
    static Class beb;
    static Class bec;
    static Class bed;
    static Class bee;
    static Class bbt;
    static Class bef;

    private hm_2(Class clazz) {
        this.bdP = clazz;
        Method[] methodArray = clazz.getMethods();
        for (int j = 0; j < methodArray.length; ++j) {
            Object object;
            Object object2;
            Method method = methodArray[j];
            String string = method.getName();
            Class<?> clazz2 = method.getReturnType();
            Class<?>[] classArray = method.getParameterTypes();
            if (classArray.length == 1 && Void.TYPE.equals(clazz2) && ("add".equals(string) || "addConfigured".equals(string))) {
                this.c(method);
                continue;
            }
            if ((bdY == null ? hm_2.a("aaT") : bdY).isAssignableFrom(clazz) && classArray.length == 1 && this.b(string, classArray[0]) || this.SI() && classArray.length == 1 && "addTask".equals(string) && (bdZ == null ? hm_2.a("Dm") : bdZ).equals(classArray[0])) continue;
            if ("addText".equals(string) && Void.TYPE.equals(clazz2) && classArray.length == 1 && (avl == null ? hm_2.a("java.lang.String") : avl).equals(classArray[0])) {
                this.bdO = methodArray[j];
                continue;
            }
            if (string.startsWith("set") && Void.TYPE.equals(clazz2) && classArray.length == 1 && !classArray[0].isArray()) {
                object2 = hm_2.v(string, "set");
                if (this.bdK.get(object2) != null && (avl == null ? hm_2.a("java.lang.String") : avl).equals(classArray[0]) || (object = this.a(method, classArray[0], (String)object2)) == null) continue;
                this.bdJ.put(object2, classArray[0]);
                this.bdK.put(object2, object);
                continue;
            }
            if (string.startsWith("create") && !clazz2.isArray() && !clazz2.isPrimitive() && classArray.length == 0) {
                object2 = hm_2.v(string, "create");
                if (this.bdM.get(object2) != null) continue;
                this.bdL.put(object2, clazz2);
                this.bdM.put(object2, new akd_1(method));
                continue;
            }
            if (string.startsWith("addConfigured") && Void.TYPE.equals(clazz2) && classArray.length == 1 && !(avl == null ? hm_2.a("java.lang.String") : avl).equals(classArray[0]) && !classArray[0].isArray() && !classArray[0].isPrimitive()) {
                try {
                    object2 = null;
                    try {
                        object2 = classArray[0].getConstructor(new Class[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        object2 = classArray[0].getConstructor(OR == null ? hm_2.a("UI") : OR);
                    }
                    object = hm_2.v(string, "addConfigured");
                    this.bdL.put(object, classArray[0]);
                    this.bdM.put(object, new alz_0(method, (Constructor)object2, 2));
                }
                catch (NoSuchMethodException noSuchMethodException) {}
                continue;
            }
            if (!string.startsWith("add") || !Void.TYPE.equals(clazz2) || classArray.length != 1 || (avl == null ? hm_2.a("java.lang.String") : avl).equals(classArray[0]) || classArray[0].isArray() || classArray[0].isPrimitive()) continue;
            try {
                object2 = null;
                try {
                    object2 = classArray[0].getConstructor(new Class[0]);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    object2 = classArray[0].getConstructor(OR == null ? hm_2.a("UI") : OR);
                }
                object = hm_2.v(string, "add");
                if (this.bdL.get(object) != null) continue;
                this.bdL.put(object, classArray[0]);
                this.bdM.put(object, new alz_0(method, (Constructor)object2, 1));
                continue;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
    }

    private boolean b(String string, Class clazz) {
        if ("setLocation".equals(string) && (bea == null ? (bea = hm_2.a("axc")) : bea).equals(clazz)) {
            return true;
        }
        return "setTaskType".equals(string) && (avl == null ? (avl = hm_2.a("java.lang.String")) : avl).equals(clazz);
    }

    public static synchronized hm_2 m(Class clazz) {
        return hm_2.a(null, clazz);
    }

    public static hm_2 a(UI uI, Class clazz) {
        hm_2 hm_22 = (hm_2)bdF.get(clazz.getName());
        if (hm_22 == null || hm_22.bdP != clazz) {
            hm_22 = new hm_2(clazz);
            if (uI != null) {
                bdF.put(clazz.getName(), hm_22);
            }
        }
        return hm_22;
    }

    public void a(UI uI, Object object, String string, String string2) {
        rK rK2 = (rK)this.bdK.get(string.toLowerCase(Locale.US));
        if (rK2 == null) {
            if (object instanceof ab_2) {
                ab_2 ab_22 = (ab_2)object;
                String string3 = es_2.dP(string);
                String string4 = es_2.dP(string3);
                String string5 = es_2.dQ(string);
                String string6 = "".equals(string4) ? string5 : string4 + ":" + string5;
                ab_22.a(string4, string5, string6, string2);
                return;
            }
            if (object instanceof uf_1) {
                uf_1 uf_12 = (uf_1)object;
                uf_12.C(string.toLowerCase(Locale.US), string2);
                return;
            }
            if (string.indexOf(58) != -1) {
                return;
            }
            String string7 = this.b(uI, object) + " doesn't support the \"" + string + "\" attribute.";
            throw new tv_2(string7, string);
        }
        try {
            rK2.a(uI, object, string2);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.a(invocationTargetException);
        }
    }

    public void b(UI uI, Object object, String string) {
        if (this.bdO == null) {
            if ((string = string.trim()).length() == 0) {
                return;
            }
            throw new eq_2(uI.as(object) + " doesn't support nested text data (\"" + this.ev(string) + "\").");
        }
        try {
            this.bdO.invoke(object, string);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.a(invocationTargetException);
        }
    }

    public void c(UI uI, Object object, String string) {
        String string2 = uI.as(object) + " doesn't support the nested \"" + string + "\" element.";
        throw new anr_2(string2, string);
    }

    private amw_2 a(UI uI, String string, Object object, String string2, rs_0 rs_02) {
        Object object2;
        Object object3;
        Object object4;
        String string3 = es_2.dP(string2);
        String string4 = es_2.dQ(string2);
        if (string3.equals("antlib:org.apache.tools.ant")) {
            string3 = "";
        }
        if (string.equals("antlib:org.apache.tools.ant")) {
            string = "";
        }
        amw_2 amw_22 = null;
        if (string3.equals(string) || string3.length() == 0) {
            amw_22 = (amw_2)this.bdM.get(string4.toLowerCase(Locale.US));
        }
        if (amw_22 == null) {
            amw_22 = this.e(uI, object, string2);
        }
        if (amw_22 == null && object instanceof fv_0 && (object4 = (object3 = (fv_0)object).a(rs_02 == null ? "" : rs_02.getNamespace(), string4, (String)(object2 = rs_02 == null ? string4 : rs_02.DD()))) != null) {
            amw_22 = new ari(this, null, object4);
        }
        if (amw_22 == null && object instanceof wc_1 && (object2 = (object3 = (wc_1)object).cC(string4.toLowerCase(Locale.US))) != null) {
            amw_22 = new arj(this, null, object2);
        }
        if (amw_22 == null) {
            this.c(uI, object, string2);
        }
        return amw_22;
    }

    public Object d(UI uI, Object object, String string) {
        amw_2 amw_22 = this.a(uI, "", object, string, null);
        try {
            Object object2 = amw_22.a(uI, object, null);
            if (uI != null) {
                uI.at(object2);
            }
            return object2;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.a(invocationTargetException);
        }
    }

    public ud_0 b(UI uI, String string, Object object, String string2, rs_0 rs_02) {
        amw_2 amw_22 = this.a(uI, string, object, string2, rs_02);
        return new ud_0(uI, object, amw_22, null);
    }

    public boolean isDynamic() {
        return (beb == null ? (beb = hm_2.a("wc")) : beb).isAssignableFrom(this.bdP) || (bec == null ? (bec = hm_2.a("fV")) : bec).isAssignableFrom(this.bdP);
    }

    public boolean SI() {
        return (bed == null ? (bed = hm_2.a("Cf")) : bed).isAssignableFrom(this.bdP);
    }

    public boolean eq(String string) {
        return this.u("", string);
    }

    public boolean u(String string, String string2) {
        if (this.isDynamic() || this.bdN.size() > 0) {
            return true;
        }
        String string3 = es_2.dQ(string2);
        if (!this.bdM.containsKey(string3.toLowerCase(Locale.US))) {
            return false;
        }
        String string4 = es_2.dP(string2);
        if (string4.equals("antlib:org.apache.tools.ant")) {
            string4 = "";
        }
        if ("".equals(string4)) {
            return true;
        }
        if (string.equals("antlib:org.apache.tools.ant")) {
            string = "";
        }
        return string4.equals(string);
    }

    public void b(UI uI, Object object, Object object2, String string) {
        if (string == null) {
            return;
        }
        amw_2 amw_22 = (amw_2)this.bdM.get(string.toLowerCase(Locale.US));
        if (amw_22 == null) {
            return;
        }
        try {
            amw_22.j(object, object2);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.a(invocationTargetException);
        }
    }

    private static eq_2 a(InvocationTargetException invocationTargetException) {
        Throwable throwable = invocationTargetException.getTargetException();
        if (throwable instanceof eq_2) {
            return (eq_2)throwable;
        }
        return new eq_2(throwable);
    }

    public Class er(String string) {
        Class clazz = (Class)this.bdL.get(string);
        if (clazz == null) {
            throw new anr_2("Class " + this.bdP.getName() + " doesn't support the nested \"" + string + "\" element.", string);
        }
        return clazz;
    }

    public Class es(String string) {
        Class clazz = (Class)this.bdJ.get(string);
        if (clazz == null) {
            throw new tv_2("Class " + this.bdP.getName() + " doesn't support the \"" + string + "\" attribute.", string);
        }
        return clazz;
    }

    public Method SJ() {
        if (!this.SK()) {
            throw new eq_2("Class " + this.bdP.getName() + " doesn't support nested text data.");
        }
        return this.bdO;
    }

    public Method et(String string) {
        Object v = this.bdM.get(string);
        if (v == null) {
            throw new anr_2("Class " + this.bdP.getName() + " doesn't support the nested \"" + string + "\" element.", string);
        }
        return amw_2.a((amw_2)v);
    }

    public Method eu(String string) {
        Object v = this.bdK.get(string);
        if (v == null) {
            throw new tv_2("Class " + this.bdP.getName() + " doesn't support the \"" + string + "\" attribute.", string);
        }
        return rK.a((rK)v);
    }

    public boolean SK() {
        return this.bdO != null;
    }

    public Enumeration SL() {
        return this.bdK.keys();
    }

    public Map SM() {
        return this.bdJ.isEmpty() ? Collections.EMPTY_MAP : Collections.unmodifiableMap(this.bdJ);
    }

    public Enumeration SN() {
        return this.bdL.keys();
    }

    public Map SO() {
        return this.bdL.isEmpty() ? Collections.EMPTY_MAP : Collections.unmodifiableMap(this.bdL);
    }

    public List SP() {
        return this.bdN.isEmpty() ? Collections.EMPTY_LIST : Collections.unmodifiableList(this.bdN);
    }

    private rK a(Method method, Class clazz, String string) {
        boolean bl2;
        Constructor constructor;
        Class clazz2;
        if ((avl == null ? (avl = hm_2.a("java.lang.String")) : avl).equals(clazz2 = bdG.containsKey(clazz) ? (Class)bdG.get(clazz) : clazz)) {
            return new are(this, method, method);
        }
        if ((bdS == null ? (bdS = hm_2.a("java.lang.Character")) : bdS).equals(clazz2)) {
            return new arh(this, method, string, method);
        }
        if ((bdQ == null ? (bdQ = hm_2.a("java.lang.Boolean")) : bdQ).equals(clazz2)) {
            return new arc(this, method, method);
        }
        if ((bee == null ? (bee = hm_2.a("java.lang.Class")) : bee).equals(clazz2)) {
            return new ard(this, method, method);
        }
        if ((bbt == null ? (bbt = hm_2.a("java.io.File")) : bbt).equals(clazz2)) {
            return new ara(this, method, method);
        }
        if ((bef == null ? (bef = hm_2.a("aNk")) : bef).isAssignableFrom(clazz2)) {
            return new arb(this, method, clazz2, method);
        }
        Class<?> clazz3 = null;
        try {
            clazz3 = Class.forName("java.lang.Enum");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        if (clazz3 != null && clazz3.isAssignableFrom(clazz2)) {
            return new aqY(this, method, method, clazz2);
        }
        if ((bdV == null ? (bdV = hm_2.a("java.lang.Long")) : bdV).equals(clazz2)) {
            return new fl_1(this, method, method);
        }
        try {
            constructor = clazz2.getConstructor(OR == null ? (OR = hm_2.a("UI")) : OR, avl == null ? (avl = hm_2.a("java.lang.String")) : avl);
            bl2 = true;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            try {
                constructor = clazz2.getConstructor(avl == null ? (avl = hm_2.a("java.lang.String")) : avl);
                bl2 = false;
            }
            catch (NoSuchMethodException noSuchMethodException2) {
                return null;
            }
        }
        boolean bl3 = bl2;
        Constructor constructor2 = constructor;
        return new fn_2(this, method, bl3, constructor2, method);
    }

    private String b(UI uI, Object object) {
        return uI.as(object);
    }

    private static String v(String string, String string2) {
        return string.substring(string2.length()).toLowerCase(Locale.US);
    }

    public static void clearCache() {
        bdF.clear();
    }

    private amw_2 e(UI uI, Object object, String string) {
        if (this.bdN.size() == 0) {
            return null;
        }
        abm_1 abm_12 = abm_1.D(uI);
        Object object2 = null;
        Method method = null;
        Class clazz = abm_12.hq(string);
        if (clazz == null) {
            return null;
        }
        method = this.a(clazz, this.bdN);
        if (method == null) {
            return null;
        }
        object2 = abm_12.hp(string);
        if (object2 == null) {
            return null;
        }
        Object object3 = object2;
        if (object2 instanceof cc_0) {
            object3 = ((cc_0)object2).i(uI);
        }
        Object object4 = object2;
        Object object5 = object3;
        return new fr_2(this, method, object5, object4);
    }

    private void c(Method method) {
        Class<?> clazz = method.getParameterTypes()[0];
        for (int j = 0; j < this.bdN.size(); ++j) {
            Method method2 = (Method)this.bdN.get(j);
            if (method2.getParameterTypes()[0].equals(clazz)) {
                if (method.getName().equals("addConfigured")) {
                    this.bdN.set(j, method);
                }
                return;
            }
            if (!method2.getParameterTypes()[0].isAssignableFrom(clazz)) continue;
            this.bdN.add(j, method);
            return;
        }
        this.bdN.add(method);
    }

    private Method a(Class clazz, List list) {
        Class<?> clazz2 = null;
        Method method = null;
        for (int j = 0; j < list.size(); ++j) {
            Method method2 = (Method)list.get(j);
            Class<?> clazz3 = method2.getParameterTypes()[0];
            if (!clazz3.isAssignableFrom(clazz)) continue;
            if (clazz2 == null) {
                clazz2 = clazz3;
                method = method2;
                continue;
            }
            if (clazz3.isAssignableFrom(clazz2)) continue;
            throw new eq_2("ambiguous: types " + clazz2.getName() + " and " + clazz3.getName() + " match " + clazz.getName());
        }
        return method;
    }

    private String ev(String string) {
        if (string.length() <= 20) {
            return string;
        }
        int n2 = (20 - bdI.length()) / 2;
        return new StringBuffer(string).replace(n2, string.length() - n2, bdI).toString();
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }

    static eq_2 b(InvocationTargetException invocationTargetException) {
        return hm_2.a(invocationTargetException);
    }

    static {
        Class[] classArray = new Class[]{Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};
        Class[] classArray2 = new Class[]{bdQ == null ? (bdQ = hm_2.a("java.lang.Boolean")) : bdQ, bdR == null ? (bdR = hm_2.a("java.lang.Byte")) : bdR, bdS == null ? (bdS = hm_2.a("java.lang.Character")) : bdS, bdT == null ? (bdT = hm_2.a("java.lang.Short")) : bdT, bdU == null ? (bdU = hm_2.a("java.lang.Integer")) : bdU, bdV == null ? (bdV = hm_2.a("java.lang.Long")) : bdV, bdW == null ? (bdW = hm_2.a("java.lang.Float")) : bdW, bdX == null ? (bdX = hm_2.a("java.lang.Double")) : bdX};
        for (int j = 0; j < classArray.length; ++j) {
            bdG.put(classArray[j], classArray2[j]);
        }
    }
}

