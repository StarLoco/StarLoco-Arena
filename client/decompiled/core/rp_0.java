/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
 * Renamed from Rp
 */
public class rp_0
extends auT
implements afs {
    protected boolean[] bIM = null;
    protected Class[] bIN = null;
    protected String[] bIO = null;
    protected String[][] bIP = null;
    protected Class[][] bIQ = null;
    protected Class[][] bIR = null;
    private Method[] bIS = null;

    public rp_0(String string) {
        this.gk(string);
    }

    public rp_0(String string, Class clazz) {
        this.s(clazz);
        this.gk(string);
    }

    public rp_0(String string, Class clazz, String[] stringArray, Class[] classArray) {
        this.s(clazz);
        this.a(stringArray, classArray);
        this.gk(string);
    }

    public rp_0(String string, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2) {
        this.s(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.gk(string);
    }

    public rp_0(String string, InputStream inputStream, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2, ClassLoader classLoader) {
        this.s(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.e(classLoader);
        this.b(string, inputStream);
    }

    public rp_0(String string, Reader reader, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2, ClassLoader classLoader) {
        this.s(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.e(classLoader);
        this.a(string, reader);
    }

    public rp_0(ahr_1 ahr_12, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2, ClassLoader classLoader) {
        this.s(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public rp_0(ahr_1 ahr_12, Class clazz, Class[] classArray, Class clazz2, String[] stringArray, Class[] classArray2, Class[] classArray3, ClassLoader classLoader) {
        this.x(clazz);
        this.e(classArray);
        this.s(clazz2);
        this.a(stringArray, classArray2);
        this.c(classArray3);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public rp_0(ahr_1 ahr_12, String string, Class clazz, Class[] classArray, boolean bl2, Class clazz2, String string2, String[] stringArray, Class[] classArray2, Class[] classArray3, ClassLoader classLoader) {
        this.setClassName(string);
        this.x(clazz);
        this.e(classArray);
        this.ck(bl2);
        this.s(clazz2);
        this.setMethodName(string2);
        this.a(stringArray, classArray2);
        this.c(classArray3);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public rp_0() {
    }

    public void ck(boolean bl2) {
        this.a(new boolean[]{bl2});
    }

    public void s(Class clazz) {
        this.d(new Class[]{clazz});
    }

    public void setMethodName(String string) {
        this.o(new String[]{string});
    }

    public void a(String[] stringArray, Class[] classArray) {
        this.a(new String[][]{stringArray}, new Class[][]{classArray});
    }

    public void c(Class[] classArray) {
        this.a(new Class[][]{classArray});
    }

    public final void a(ahr_1 ahr_12) {
        this.a(new ahr_1[]{ahr_12});
    }

    public Object b(Object[] objectArray) {
        return this.a(0, objectArray);
    }

    public Method getMethod() {
        return this.hu(0);
    }

    public void a(boolean[] blArray) {
        this.aIo();
        this.bIM = (boolean[])blArray.clone();
    }

    public void d(Class[] classArray) {
        this.aIo();
        this.bIN = (Class[])classArray.clone();
    }

    public void o(String[] stringArray) {
        this.aIo();
        this.bIO = (String[])stringArray.clone();
    }

    public void a(String[][] stringArray, Class[][] classArray) {
        this.aIo();
        this.bIP = (String[][])stringArray.clone();
        this.bIQ = (Class[][])classArray.clone();
    }

    public void a(Class[][] classArray) {
        this.aIo();
        this.bIR = (Class[][])classArray.clone();
    }

    public final void a(ahr_1[] ahr_1Array) {
        GenericDeclaration genericDeclaration;
        int n2;
        Object object;
        Method[] methodArray;
        int n3;
        String[] stringArray;
        if (ahr_1Array == null) {
            throw new NullPointerException();
        }
        int n4 = ahr_1Array.length;
        if (this.bIO != null && this.bIO.length != n4) {
            throw new IllegalStateException("methodName");
        }
        if (this.bIP != null && this.bIP.length != n4) {
            throw new IllegalStateException("parameterNames");
        }
        if (this.bIQ != null && this.bIQ.length != n4) {
            throw new IllegalStateException("parameterTypes");
        }
        if (this.bIN != null && this.bIN.length != n4) {
            throw new IllegalStateException("returnTypes");
        }
        if (this.bIM != null && this.bIM.length != n4) {
            throw new IllegalStateException("staticMethod");
        }
        if (this.bIR != null && this.bIR.length != n4) {
            throw new IllegalStateException("thrownExceptions");
        }
        this.aIn();
        kh_1 kh_12 = this.f(n4 == 1 ? ahr_1Array[0] : null);
        ayp_0 ayp_02 = this.a(ahr_1Array[0].RR(), kh_12);
        if (this.bIO == null) {
            stringArray = new String[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                stringArray[n3] = "eval" + n3;
            }
        } else {
            stringArray = this.bIO;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            methodArray = ahr_1Array[n3];
            object = this.a(n3, (ahr_1)methodArray);
            n2 = this.bIM == null || this.bIM[n3] ? 1 : 0;
            genericDeclaration = this.bIN == null ? this.adL() : this.bIN[n3];
            String[] stringArray2 = this.bIP == null ? new String[]{} : this.bIP[n3];
            Class[] classArray = this.bIQ == null ? new Class[]{} : this.bIQ[n3];
            Class[] classArray2 = this.bIR == null ? new Class[]{} : this.bIR[n3];
            ayp_02.c(this.a(methodArray.RR(), n2 != 0, (Class)genericDeclaration, stringArray[n3], classArray, stringArray2, classArray2, (List)object));
        }
        Class clazz = this.a(kh_12, this.className);
        this.bIS = new Method[n4];
        if (n4 <= 10) {
            for (int j = 0; j < n4; ++j) {
                try {
                    this.bIS[j] = clazz.getDeclaredMethod(stringArray[j], this.bIQ == null ? new Class[]{} : this.bIQ[j]);
                    continue;
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    throw new aHY("SNO: Loaded class does not declare method \"" + stringArray[j] + "\"");
                }
            }
        } else {
            methodArray = clazz.getDeclaredMethods();
            object = new HashMap(2 * n4);
            for (n2 = 0; n2 < methodArray.length; ++n2) {
                genericDeclaration = methodArray[n2];
                object.put(new ack_0(this, ((Method)genericDeclaration).getName(), ((Method)genericDeclaration).getParameterTypes()), genericDeclaration);
            }
            for (n2 = 0; n2 < n4; ++n2) {
                genericDeclaration = (Method)object.get(new ack_0(this, stringArray[n2], this.bIQ == null ? new Class[]{} : this.bIQ[n2]));
                if (genericDeclaration == null) {
                    throw new aHY("SNO: Loaded class does not declare method \"" + stringArray[n2] + "\"");
                }
                this.bIS[n2] = genericDeclaration;
            }
        }
    }

    public final void a(Reader[] readerArray) {
        this.a(new String[readerArray.length], readerArray);
    }

    public final void a(String[] stringArray, Reader[] readerArray) {
        ahr_1[] ahr_1Array = new ahr_1[readerArray.length];
        for (int j = 0; j < readerArray.length; ++j) {
            ahr_1Array[j] = new ahr_1(stringArray == null ? null : stringArray[j], readerArray[j]);
        }
        this.a(ahr_1Array);
    }

    public final void p(String[] stringArray) {
        this.a(null, stringArray);
    }

    public final void a(String[] stringArray, String[] stringArray2) {
        Reader[] readerArray = new Reader[stringArray2.length];
        for (int j = 0; j < stringArray2.length; ++j) {
            readerArray[j] = new StringReader(stringArray2[j]);
        }
        try {
            this.a(stringArray, readerArray);
        }
        catch (IOException iOException) {
            throw new aHY("SNO: IOException despite StringReader");
        }
    }

    protected Class adL() {
        return Void.TYPE;
    }

    protected List a(int n2, ahr_1 ahr_12) {
        ArrayList<TK> arrayList = new ArrayList<TK>();
        GN gN = new GN(ahr_12);
        while (!ahr_12.awY().isEOF()) {
            arrayList.add(gN.Rb());
        }
        return arrayList;
    }

    protected void a(kh_1 kh_12, String[] stringArray, Class[][] classArray) {
        Class clazz = this.a(kh_12, this.className);
        this.bIS = new Method[stringArray.length];
        for (int j = 0; j < this.bIS.length; ++j) {
            try {
                this.bIS[j] = clazz.getMethod(stringArray[j], classArray[j]);
                continue;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new aHY("SNO: Loaded class does not declare method \"" + this.bIO[j] + "\"");
            }
        }
    }

    protected kc_0 a(lc_0 lc_02, boolean bl2, Class clazz, String string, Class[] classArray, String[] stringArray, Class[] classArray2, List list) {
        if (stringArray.length != classArray.length) {
            throw new aHY("Lengths of \"parameterNames\" (" + stringArray.length + ") and \"parameterTypes\" (" + classArray.length + ") do not match");
        }
        anb_1[] anb_1Array = new anb_1[stringArray.length];
        for (int j = 0; j < anb_1Array.length; ++j) {
            anb_1Array[j] = new anb_1(lc_02, true, this.a(lc_02, classArray[j]), stringArray[j]);
        }
        return new kc_0(lc_02, null, bl2 ? (short)9 : 1, this.a(lc_02, clazz), string, anb_1Array, this.a(lc_02, classArray2), list);
    }

    public static Object b(String string, Class clazz, String[] stringArray) {
        rp_0 rp_02 = new rp_0();
        return rp_02.a(string, clazz, stringArray);
    }

    public static Object a(ahr_1 ahr_12, Class clazz, String[] stringArray, ClassLoader classLoader) {
        rp_0 rp_02 = new rp_0();
        rp_02.e(classLoader);
        return rp_02.a(ahr_12, clazz, stringArray);
    }

    public static Object a(ahr_1 ahr_12, String string, Class clazz, Class clazz2, String[] stringArray, ClassLoader classLoader) {
        rp_0 rp_02 = new rp_0();
        rp_02.setClassName(string);
        rp_02.x(clazz);
        rp_02.e(classLoader);
        return rp_02.a(ahr_12, clazz2, stringArray);
    }

    public static Object a(ahr_1 ahr_12, String[] stringArray, String string, Class clazz, Class clazz2, String[] stringArray2, ClassLoader classLoader) {
        rp_0 rp_02 = new rp_0();
        rp_02.s(stringArray);
        rp_02.setClassName(string);
        rp_02.x(clazz);
        rp_02.e(classLoader);
        return rp_02.a(ahr_12, clazz2, stringArray2);
    }

    public final Object g(Reader reader) {
        throw new UnsupportedOperationException("createInstance");
    }

    public Object a(Reader reader, Class clazz, String[] stringArray) {
        return this.a(new ahr_1(null, reader), clazz, stringArray);
    }

    public Object a(String string, Class clazz, String[] stringArray) {
        try {
            return this.a(new StringReader(string), clazz, stringArray);
        }
        catch (IOException iOException) {
            throw new aHY("IOException despite StringReader");
        }
    }

    public Object a(ahr_1 ahr_12, Class clazz, String[] stringArray) {
        if (!clazz.isInterface()) {
            throw new aHY("\"" + clazz + "\" is not an interface");
        }
        Method[] methodArray = clazz.getDeclaredMethods();
        if (methodArray.length != 1) {
            throw new aHY("Interface \"" + clazz + "\" must declare exactly one method");
        }
        Method method = methodArray[0];
        this.e(new Class[]{clazz});
        this.ck(false);
        this.s(method.getReturnType());
        this.setMethodName(method.getName());
        this.a(stringArray, (Class[])method.getParameterTypes());
        this.c(method.getExceptionTypes());
        this.a(ahr_12);
        Class<?> clazz2 = this.getMethod().getDeclaringClass();
        try {
            return clazz2.newInstance();
        }
        catch (InstantiationException instantiationException) {
            throw new aHY(instantiationException.toString());
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new aHY(illegalAccessException.toString());
        }
    }

    public static String[] b(ahr_1 ahr_12) {
        GN gN = new GN(ahr_12);
        while (ahr_12.awY().dN("import")) {
            gN.QO();
        }
        lo_2 lo_22 = new lo_2(ahr_12.RR());
        while (!ahr_12.awY().isEOF()) {
            lo_22.e(gN.Rb());
        }
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        new aiy_1(hashSet, hashSet2).c(lo_22);
        return hashSet2.toArray(new String[hashSet2.size()]);
    }

    public Object a(int n2, Object[] objectArray) {
        if (this.bIS == null) {
            throw new IllegalStateException("Must only be called after \"cook()\"");
        }
        try {
            return this.bIS[n2].invoke(null, objectArray);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new aHY(illegalAccessException.toString());
        }
    }

    public Method hu(int n2) {
        if (this.bIS == null) {
            throw new IllegalStateException("Must only be called after \"cook()\"");
        }
        return this.bIS[n2];
    }
}

