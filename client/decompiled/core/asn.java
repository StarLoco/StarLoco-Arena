/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class asn {
    private static final boolean DEBUG = false;
    public static final asn cRw = new sn("V");
    public static final asn cRx = new sn("B");
    public static final asn cRy = new sn("C");
    public static final asn cRz = new sn("D");
    public static final asn cRA = new sn("F");
    public static final asn cRB = new sn("I");
    public static final asn cRC = new sn("J");
    public static final asn cRD = new sn("S");
    public static final asn cRE = new sn("Z");
    private adi_0[] cRF = null;
    protected ff_2[] cRG = null;
    Map cRH = null;
    private ff_2[] cRI = null;
    public static final ff_2[] cRJ = new ff_2[0];
    private Map cRK = null;
    private asn[] cRL = null;
    private boolean cRM = false;
    private asn cRN = null;
    private boolean cRO = false;
    private asn cRP = null;
    private boolean cRQ = false;
    private asn cRR = null;
    private asn[] aKJ = null;
    private String descriptor = null;
    private boolean cRS = false;
    private asn cRT = null;
    private static final Set cRU = new HashSet();
    private asn cRV = null;
    private final Map cRW = new HashMap();
    private static final asn[] cRX;

    public final adi_0[] aFi() {
        if (this.cRF == null) {
            this.cRF = this.lK();
        }
        return this.cRF;
    }

    protected abstract adi_0[] lK();

    public final ff_2[] aFj() {
        if (this.cRG == null) {
            this.cRG = this.lL();
        }
        return this.cRG;
    }

    protected abstract ff_2[] lL();

    public final ff_2[] ju(String string) {
        ff_2[] ff_2Array;
        if (this.cRH == null) {
            Object object;
            String string2;
            Map.Entry entry;
            ff_2Array = new HashMap();
            ff_2[] ff_2Array2 = this.aFj();
            for (int j = 0; j < ff_2Array2.length; ++j) {
                entry = ff_2Array2[j];
                string2 = ((ff_2)((Object)entry)).getName();
                object = ff_2Array.get(string2);
                if (object == null) {
                    ff_2Array.put(string2, entry);
                    continue;
                }
                if (object instanceof ff_2) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    arrayList.add(object);
                    arrayList.add(entry);
                    ff_2Array.put(string2, arrayList);
                    continue;
                }
                ((List)object).add(entry);
            }
            Iterator iterator = ff_2Array.entrySet().iterator();
            while (iterator.hasNext()) {
                entry = iterator.next();
                string2 = entry.getValue();
                if (string2 instanceof ff_2) {
                    entry.setValue(new ff_2[]{(ff_2)((Object)string2)});
                    continue;
                }
                object = (List)((Object)string2);
                entry.setValue(object.toArray(new ff_2[object.size()]));
            }
            this.cRH = ff_2Array;
        }
        return (ff_2Array = (ff_2[])this.cRH.get(string)) == null ? cRJ : ff_2Array;
    }

    public final ff_2[] aFk() {
        if (this.cRI == null) {
            ArrayList arrayList = new ArrayList();
            this.q(arrayList);
            this.cRI = arrayList.toArray(new ff_2[arrayList.size()]);
        }
        return this.cRI;
    }

    private void q(List list) {
        asn[] asnArray;
        ff_2[] ff_2Array = this.aFj();
        for (int j = 0; j < ff_2Array.length; ++j) {
            asnArray = ff_2Array[j];
            String string = asnArray.getDescriptor();
            String string2 = asnArray.getName();
            boolean bl2 = false;
            for (int i2 = 0; i2 < list.size(); ++i2) {
                ff_2 ff_22 = (ff_2)list.get(i2);
                if (!string2.equals(ff_22.getName()) || !string.equals(ff_22.getDescriptor())) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            list.add(asnArray);
        }
        asn asn2 = this.aFq();
        if (asn2 != null) {
            asn2.q(list);
        }
        asnArray = this.aFr();
        for (int j = 0; j < asnArray.length; ++j) {
            asnArray[j].q(list);
        }
    }

    public final boolean a(String string, asn[] asnArray) {
        return this.b(string, asnArray) != null;
    }

    public final ff_2 b(String string, asn[] asnArray) {
        ff_2[] ff_2Array = this.ju(string);
        for (int j = 0; j < ff_2Array.length; ++j) {
            Object[] objectArray = ff_2Array[j].iy();
            if (!Arrays.equals(objectArray, asnArray)) continue;
            return ff_2Array[j];
        }
        return null;
    }

    public final jz_0[] aFl() {
        Collection collection = this.aFm().values();
        return collection.toArray(new jz_0[collection.size()]);
    }

    private Map aFm() {
        if (this.cRK == null) {
            jz_0[] jz_0Array = this.lM();
            HashMap<String, jz_0> hashMap = new HashMap<String, jz_0>();
            for (int j = 0; j < jz_0Array.length; ++j) {
                hashMap.put(jz_0Array[j].getName(), jz_0Array[j]);
            }
            this.cRK = hashMap;
        }
        return this.cRK;
    }

    public final jz_0 jv(String string) {
        return (jz_0)this.aFm().get(string);
    }

    protected void aFn() {
        this.cRK = null;
    }

    protected abstract jz_0[] lM();

    public jz_0[] aCO() {
        return new jz_0[0];
    }

    public final asn[] aFo() {
        if (this.cRL == null) {
            this.cRL = this.lN();
        }
        return this.cRL;
    }

    protected abstract asn[] lN();

    public final asn ic() {
        if (!this.cRM) {
            this.cRN = this.lO();
            this.cRM = true;
        }
        return this.cRN;
    }

    protected abstract asn lO();

    public final asn aFp() {
        if (!this.cRO) {
            this.cRP = this.lP();
            this.cRO = true;
        }
        return this.cRP;
    }

    protected abstract asn lP();

    public final asn aFq() {
        if (!this.cRQ) {
            this.cRR = this.lQ();
            this.cRQ = true;
            if (this.cRR != null && this.cRR.h(this)) {
                throw new ajy_2("Class circularity detected for \"" + sA.toClassName(this.getDescriptor()) + "\"", null);
            }
        }
        return this.cRR;
    }

    protected abstract asn lQ();

    public abstract amf ib();

    public abstract boolean isFinal();

    public final asn[] aFr() {
        if (this.aKJ == null) {
            this.aKJ = this.lR();
            for (int j = 0; j < this.aKJ.length; ++j) {
                if (!this.aKJ[j].i(this)) continue;
                throw new ajy_2("Interface circularity detected for \"" + sA.toClassName(this.getDescriptor()) + "\"", null);
            }
        }
        return this.aKJ;
    }

    protected abstract asn[] lR();

    public abstract boolean isAbstract();

    public final String getDescriptor() {
        if (this.descriptor == null) {
            this.descriptor = this.lS();
        }
        return this.descriptor;
    }

    protected abstract String lS();

    public static String[] a(asn[] asnArray) {
        String[] stringArray = new String[asnArray.length];
        for (int j = 0; j < asnArray.length; ++j) {
            stringArray[j] = asnArray[j].getDescriptor();
        }
        return stringArray;
    }

    public abstract boolean isInterface();

    public abstract boolean isArray();

    public abstract boolean isPrimitive();

    public abstract boolean lT();

    public final asn aFs() {
        if (!this.cRS) {
            this.cRT = this.lU();
            this.cRS = true;
        }
        return this.cRT;
    }

    protected abstract asn lU();

    public String toString() {
        return sA.toClassName(this.getDescriptor());
    }

    public boolean g(asn asn2) {
        if (this == asn2) {
            return true;
        }
        Object object = asn2.getDescriptor() + this.getDescriptor();
        if (((String)object).length() == 2 && cRU.contains(object)) {
            return true;
        }
        if (asn2.h(this)) {
            return true;
        }
        if (asn2.i(this)) {
            return true;
        }
        if (asn2 == cRw && !this.isPrimitive()) {
            return true;
        }
        if (asn2.isInterface() && this.getDescriptor().equals("Ljava/lang/Object;")) {
            return true;
        }
        if (asn2.isArray()) {
            if (this.getDescriptor().equals("Ljava/lang/Object;")) {
                return true;
            }
            if (this.getDescriptor().equals("Ljava/lang/Cloneable;")) {
                return true;
            }
            if (this.getDescriptor().equals("Ljava/io/Serializable;")) {
                return true;
            }
            if (this.isArray()) {
                object = this.aFs();
                asn asn3 = asn2.aFs();
                if (!((asn)object).isPrimitive() && ((asn)object).g(asn3)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean h(asn asn2) {
        for (asn asn3 = this.aFq(); asn3 != null; asn3 = asn3.aFq()) {
            if (asn3 != asn2) continue;
            return true;
        }
        return false;
    }

    public boolean i(asn asn2) {
        for (asn asn3 = this; asn3 != null; asn3 = asn3.aFq()) {
            asn[] asnArray = asn3.aFr();
            for (int j = 0; j < asnArray.length; ++j) {
                asn asn4 = asnArray[j];
                if (asn4 != asn2 && !asn4.i(asn2)) continue;
                return true;
            }
        }
        return false;
    }

    public asn a(int n2, asn asn2) {
        asn asn3 = this;
        for (int j = 0; j < n2; ++j) {
            asn3 = asn3.j(asn2);
        }
        return asn3;
    }

    public synchronized asn j(asn asn2) {
        if (this.cRV == null) {
            this.cRV = this.k(asn2);
        }
        return this.cRV;
    }

    private asn k(asn asn2) {
        asn asn3 = this;
        return new ja_2(this, asn2, asn3);
    }

    asn[] jw(String string) {
        asn[] asnArray = (asn[])this.cRW.get(string);
        if (asnArray == null) {
            HashSet hashSet = new HashSet();
            this.a(string, hashSet);
            asnArray = hashSet.isEmpty() ? cRX : hashSet.toArray(new asn[hashSet.size()]);
            this.cRW.put(string, asnArray);
        }
        return asnArray;
    }

    private void a(String string, Collection collection) {
        int n2;
        Object object;
        asn[] asnArray = this.aFo();
        if (string == null) {
            collection.addAll(Arrays.asList(asnArray));
        } else {
            object = sA.cb(sA.toClassName(this.getDescriptor()) + '$' + string);
            for (n2 = 0; n2 < asnArray.length; ++n2) {
                asn asn2 = asnArray[n2];
                if (!asn2.getDescriptor().equals(object)) continue;
                collection.add(asn2);
                return;
            }
        }
        object = this.aFq();
        if (object != null) {
            super.a(string, collection);
        }
        object = this.aFr();
        for (n2 = 0; n2 < ((asn[])object).length; ++n2) {
            object[n2].a(string, collection);
        }
        object = this.ic();
        asn asn3 = this.aFp();
        if (object != null) {
            super.a(string, collection);
        }
        if (asn3 != null && asn3 != object) {
            asn3.a(string, collection);
        }
    }

    static {
        String[] stringArray = new String[]{"BS", "BI", "SI", "CI", "BJ", "SJ", "CJ", "IJ", "BF", "SF", "CF", "IF", "JF", "BD", "SD", "CD", "ID", "JD", "FD"};
        for (int j = 0; j < stringArray.length; ++j) {
            cRU.add(stringArray[j]);
        }
        cRX = new asn[0];
    }
}

