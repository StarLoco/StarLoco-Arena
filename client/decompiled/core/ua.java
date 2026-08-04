/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class ua {
    public static final String aos = "subPipe";
    public static final int aot = -1;
    private static int aou = 100;
    private final int aW;
    private String aov = null;
    private boolean aow = false;
    private String m_name = null;
    private akh_2 aox;
    private LinkedList aoy;
    protected List aoz;
    protected float[] aaV;
    protected HashMap aoA = new HashMap();

    public ua(int n2, String string, float[] fArray, String string2, boolean bl2) {
        this.aW = n2;
        this.aov = string;
        this.aaV = fArray;
        this.m_name = string2;
        this.aow = bl2;
    }

    public String zV() {
        return this.aov;
    }

    public LinkedList zW() {
        return this.aoy;
    }

    public akh_2 zX() {
        return this.aox;
    }

    public void a(String string, ua ua2) {
        if (this.aoA == null) {
            this.aoA = new HashMap();
        }
        if (!this.aoA.containsKey(string)) {
            this.aoA.put(string, ua2);
        } else {
            ua ua3 = (ua)this.aoA.get(string);
            for (Sz sz : ua2.zY()) {
                ua3.a(sz);
            }
        }
    }

    public void a(zc_0 zc_02) {
        if (this.aoy == null) {
            this.aoy = new LinkedList();
        }
        if (this.aoy.size() > aou) {
            this.aoy.removeLast();
        }
        this.aoy.addFirst(zc_02);
        if (this.aoz != null) {
            for (Sz sz : this.aoz) {
                sz.d(zc_02);
            }
        }
    }

    public void a(zc_0 zc_02, String string) {
        ua ua2 = (ua)this.aoA.get(string);
        if (ua2 != null) {
            ua2.a(zc_02);
        } else if (this.aoz != null) {
            for (Sz sz : this.aoz) {
                sz.d(zc_02);
            }
        }
    }

    protected void cu(String string) {
    }

    public void a(Sz sz) {
        if (this.aoz == null) {
            this.aoz = new ArrayList();
        }
        if (!this.aoz.contains(sz)) {
            this.aoz.add(sz);
        }
        for (ua ua2 : this.zZ().values()) {
            ua2.a(sz);
        }
    }

    public void b(Sz sz) {
        if (this.aoz != null) {
            this.aoz.remove(sz);
        }
    }

    public List zY() {
        return this.aoz;
    }

    public ua cv(String string) {
        return (ua)this.aoA.get(string);
    }

    public HashMap zZ() {
        return this.aoA;
    }

    public float[] Aa() {
        return this.aaV;
    }

    public String getName() {
        return this.m_name;
    }

    public boolean Ab() {
        return this.aow;
    }

    public int getId() {
        return this.aW;
    }

    public void c(float f, float f2, float f3) {
        this.aaV = new float[]{f, f2, f3};
    }

    public void clean() {
        for (ua ua2 : this.aoA.values()) {
            ua2.clean();
        }
        this.aoA.clear();
        if (this.aoz != null) {
            this.aoz.clear();
        }
    }

    public boolean Ac() {
        return this.aow;
    }
}

