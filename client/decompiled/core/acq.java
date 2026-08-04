/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class acq
extends rp_0
implements cy_2 {
    private Class[] cjM = null;
    static Class cjN;
    static Class cjO;

    public acq(String string, Class clazz, String[] stringArray, Class[] classArray) {
        this.a(clazz);
        this.a(stringArray, classArray);
        this.gk(string);
    }

    public acq(String string, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2, ClassLoader classLoader) {
        this.a(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.e(classLoader);
        this.gk(string);
    }

    public acq(String string, Class clazz, String[] stringArray, Class[] classArray, Class[] classArray2, Class clazz2, Class[] classArray3, ClassLoader classLoader) {
        this.a(clazz);
        this.a(stringArray, classArray);
        this.c(classArray2);
        this.x(clazz2);
        this.e(classArray3);
        this.e(classLoader);
        this.gk(string);
    }

    public acq(ahr_1 ahr_12, String string, Class clazz, Class[] classArray, boolean bl2, Class clazz2, String string2, String[] stringArray, Class[] classArray2, Class[] classArray3, ClassLoader classLoader) {
        this.setClassName(string);
        this.x(clazz);
        this.e(classArray);
        this.ck(bl2);
        this.a(clazz2);
        this.setMethodName(string2);
        this.a(stringArray, classArray2);
        this.c(classArray3);
        this.e(classLoader);
        this.a(ahr_12);
    }

    public acq() {
    }

    public void a(Class clazz) {
        this.a(new Class[]{clazz});
    }

    public void a(Class[] classArray) {
        this.aIo();
        this.cjM = classArray;
        Class[] classArray2 = new Class[classArray.length];
        for (int j = 0; j < classArray2.length; ++j) {
            Class clazz = classArray[j];
            classArray2[j] = clazz == cy_2.jf ? (cjN == null ? acq.a("java.lang.Object") : cjN) : clazz;
        }
        super.d(classArray2);
    }

    protected Class adL() {
        return cjN == null ? (cjN = acq.a("java.lang.Object")) : cjN;
    }

    protected List a(int n2, ahr_1 ahr_12) {
        Class clazz;
        ArrayList<akE> arrayList = new ArrayList<akE>();
        GN gN = new GN(ahr_12);
        jy_2 jy_22 = gN.Rx().aAr();
        Class clazz2 = clazz = this.cjM == null ? cy_2.jf : this.cjM[n2];
        if (clazz == Void.TYPE) {
            arrayList.add(new cr(jy_22));
        } else {
            if (clazz == cy_2.jf) {
                jy_22 = new La(ahr_12.RR(), new ft(ahr_12.RR(), new String[]{"org", "codehaus", "commons", "compiler", "PrimitiveWrapper"}), "wrap", new jy_2[]{jy_22});
                ank_2.ly(99);
                this.a(null, cjO == null ? (cjO = acq.a("ank")) : cjO);
            }
            arrayList.add(new jr_1(ahr_12.RR(), jy_22));
        }
        if (!ahr_12.awY().isEOF()) {
            throw new ajy_2("Unexpected token \"" + ahr_12.awY() + "\"", ahr_12.RR());
        }
        return arrayList;
    }

    public static Object a(String string, Class clazz, String[] stringArray, ClassLoader classLoader) {
        acq acq2 = new acq();
        acq2.e(classLoader);
        return acq2.a(string, clazz, stringArray);
    }

    public static Object b(ahr_1 ahr_12, String string, Class clazz, Class clazz2, String[] stringArray, ClassLoader classLoader) {
        acq acq2 = new acq();
        acq2.setClassName(string);
        acq2.x(clazz);
        acq2.e(classLoader);
        return acq2.a(ahr_12, clazz2, stringArray);
    }

    public static Object b(ahr_1 ahr_12, String[] stringArray, String string, Class clazz, Class clazz2, String[] stringArray2, ClassLoader classLoader) {
        acq acq2 = new acq();
        acq2.setClassName(string);
        acq2.x(clazz);
        acq2.s(stringArray);
        acq2.e(classLoader);
        return acq2.a(ahr_12, clazz2, stringArray2);
    }

    public static String[] b(ahr_1 ahr_12) {
        GN gN = new GN(ahr_12);
        while (ahr_12.awY().dN("import")) {
            gN.QO();
        }
        jy_2 jy_22 = gN.Rx().aAr();
        if (!ahr_12.awY().isEOF()) {
            throw new ajy_2("Unexpected token \"" + ahr_12.awY() + "\"", ahr_12.RR());
        }
        HashSet hashSet = new HashSet();
        jy_22.a((EO)new mw(hashSet).WZ());
        return hashSet.toArray(new String[hashSet.size()]);
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

