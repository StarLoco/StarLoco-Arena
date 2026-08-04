/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Wy
 */
public class wy_1 {
    public static final wy_1 bUn = null;
    private final qa_2 bUo = new qa_2();
    private final aku_2 bUp = new aku_2();
    private final aku_2 bUq = new aku_2();
    private final mm_0 bUr = new mm_0();
    private final aku_2 bUs = new aku_2();
    private final ArrayList bUt = new ArrayList();
    private final ArrayList bUu = new ArrayList();
    private final ArrayList bUv = new ArrayList();
    private final ArrayList bUw = new ArrayList();
    private final aku_2 bUx = new aku_2();

    public int getSize() {
        return this.bUo.size();
    }

    public void a(long l2, boolean bl2, byte by, short s, boolean bl3, int[] nArray, String string, String string2, String string3, byte by2) {
        this.bUo.ct(l2);
        this.bUp.add((byte)(bl2 ? 1 : 0));
        this.bUq.add(by);
        this.bUr.add(s);
        this.bUs.add((byte)(bl3 ? 1 : 0));
        this.bUt.add(nArray);
        this.bUu.add(string);
        this.bUv.add(string2);
        this.bUw.add(string3);
        this.bUx.add(by2);
    }

    public long iy(int n2) {
        return this.bUo.get(n2);
    }

    public byte iz(int n2) {
        return this.bUp.get(n2);
    }

    public byte iA(int n2) {
        return this.bUq.get(n2);
    }

    public short iB(int n2) {
        return this.bUr.get(n2);
    }

    public byte iC(int n2) {
        return this.bUs.get(n2);
    }

    public int[] iD(int n2) {
        return (int[])this.bUt.get(n2);
    }

    public String iE(int n2) {
        return (String)this.bUu.get(n2);
    }

    public String iF(int n2) {
        return (String)this.bUv.get(n2);
    }

    public String iG(int n2) {
        return (String)this.bUw.get(n2);
    }

    public byte iH(int n2) {
        return this.bUx.get(n2);
    }
}

