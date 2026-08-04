/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class PU
implements k_0 {
    private String m_name;
    private String ayx;
    private ArrayList bEE = new ArrayList();
    private ArrayList uA = new ArrayList();

    public PU(String string, String string2) {
        this.m_name = string != null ? string.intern() : null;
        this.ayx = string2 != null ? string2.intern() : null;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string != null ? string.intern() : null;
    }

    public int getId() {
        return 0;
    }

    public void f(int n2) {
    }

    public String getStringValue() {
        return this.ayx;
    }

    public boolean getBooleanValue() {
        return Boolean.parseBoolean(this.ayx);
    }

    public byte aj() {
        return Byte.parseByte(this.ayx);
    }

    public short ak() {
        return Short.parseShort(this.ayx);
    }

    public int getIntValue() {
        return Integer.parseInt(this.ayx);
    }

    public long getLongValue() {
        return Long.parseLong(this.ayx);
    }

    public float getFloatValue() {
        return Float.parseFloat(this.ayx);
    }

    public double getDoubleValue() {
        return Double.parseDouble(this.ayx);
    }

    public void b(String string) {
        this.ayx = string != null ? string.intern() : null;
    }

    public void b(boolean bl2) {
        this.ayx = bl2 ? "true" : "false";
    }

    public void a(byte by) {
        this.ayx = ("" + by).intern();
    }

    public void g(int n2) {
        this.ayx = ("" + n2).intern();
    }

    public void e(long l2) {
        this.ayx = ("" + l2).intern();
    }

    public void c(float f) {
        this.ayx = ("" + f).intern();
    }

    public void a(double d) {
        this.ayx = ("" + d).intern();
    }

    public k_0 f(String string) {
        if (this.bEE != null) {
            int n2 = this.bEE.size();
            for (int j = 0; j < n2; ++j) {
                zo_2 zo_22 = (zo_2)this.bEE.get(j);
                if (!zo_22.getName().equalsIgnoreCase(string)) continue;
                return zo_22;
            }
        }
        return null;
    }

    public void c(k_0 k_02) {
        if (!this.bEE.contains(k_02)) {
            this.bEE.add((zo_2)k_02);
        }
    }

    public void p(ArrayList arrayList) {
        for (k_0 k_02 : arrayList) {
            this.c(k_02);
        }
    }

    public void d(k_0 k_02) {
        this.bEE.remove(k_02);
    }

    public ArrayList al() {
        return this.bEE;
    }

    public k_0 c(String string) {
        if (this.m_name.equalsIgnoreCase(string)) {
            return this;
        }
        for (PU pU : this.uA) {
            k_0 k_02 = pU.c(string);
            if (k_02 == null) continue;
            return k_02;
        }
        return null;
    }

    public ArrayList d(String string) {
        ArrayList<PU> arrayList = new ArrayList<PU>();
        if (this.m_name.equalsIgnoreCase(string)) {
            arrayList.add(this);
        } else {
            for (PU pU : this.uA) {
                ArrayList arrayList2 = pU.d(string);
                if (arrayList2 == null) continue;
                arrayList.addAll(arrayList2);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        return arrayList;
    }

    public ArrayList e(String string) {
        ArrayList<PU> arrayList = new ArrayList<PU>();
        for (PU pU : this.uA) {
            if (!pU.getName().equalsIgnoreCase(string)) continue;
            arrayList.add(pU);
        }
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        return arrayList;
    }

    public void a(k_0 k_02) {
        if (!this.uA.contains(k_02)) {
            this.uA.add((PU)k_02);
        }
    }

    public void b(k_0 k_02) {
        this.uA.remove(k_02);
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public String toString() {
        return this.m_name + " " + this.ayx;
    }
}

