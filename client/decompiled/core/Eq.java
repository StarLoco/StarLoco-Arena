/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

public abstract class Eq {
    protected static Logger a = Logger.getLogger(Eq.class);
    public static final long aQS = Long.MIN_VALUE;
    public static final int aQT = -1;
    private ArrayList G = new ArrayList();
    private int apt;
    private int aQU;
    private int aQV;
    private long aQW = Long.MIN_VALUE;
    private long Bf = Long.MIN_VALUE;
    private boolean aQX = false;
    private int aQY = -1;

    public Eq(int n2, int n3, int n4) {
        this.apt = n2;
        this.aQU = n3;
        this.aQV = n4;
    }

    public abstract void run();

    public void a(acg_2 acg_22) {
        this.G.add(acg_22);
    }

    public void b(acg_2 acg_22) {
        this.G.remove(acg_22);
    }

    public int Ao() {
        return this.apt;
    }

    public void fp(int n2) {
        this.apt = n2;
    }

    public int Nk() {
        return this.aQU;
    }

    public void fq(int n2) {
        this.aQU = n2;
    }

    public int M() {
        return this.aQV;
    }

    public void fr(int n2) {
        this.aQV = n2;
    }

    public long Nl() {
        return this.aQW;
    }

    public void bB(long l2) {
        this.aQW = l2;
    }

    public int Nm() {
        return this.aQY;
    }

    public void fs(int n2) {
        this.aQY = n2;
    }

    public long mS() {
        return this.Bf;
    }

    public void bC(long l2) {
        this.Bf = l2;
    }

    protected void Nn() {
        this.ax();
        for (acg_2 acg_22 : this.G.toArray(new acg_2[this.G.size()])) {
            acg_22.a(this);
        }
    }

    public boolean No() {
        return this.aQX;
    }

    public void bl(boolean bl2) {
        this.aQX = bl2;
    }

    protected abstract void ax();

    public String toString() {
        return "{Action UID=" + this.Ao() + " id=" + this.M() + " type=" + this.Nk() + "}";
    }

    public static void main(String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        for (int j = 0; j < arrayList.toArray(new String[arrayList.size()]).length; ++j) {
            arrayList.remove("b");
            System.out.println(arrayList.toArray(new String[arrayList.size()])[j]);
        }
    }
}

