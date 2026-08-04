/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from Jb
 */
public abstract class jb_2
implements Pi,
akU {
    protected static final Logger a = Logger.getLogger(jb_2.class);
    private final acy bja = new acy();
    private final acy bjb = new acy();
    protected final int aW;
    protected final int bjc;
    protected final boolean bjd;
    protected final int apC;
    protected final int apB;
    protected final boolean bje;
    protected final boolean bjf;
    protected final int r;
    protected final boolean bjg;
    protected final boolean bjh;
    protected boolean bji;
    private final vi_1 bjj;

    protected jb_2(int n2, vi_1 vi_12, int n3, boolean bl2, int n4, int n5, boolean bl3, boolean bl4, int n6, boolean bl5, boolean bl6) {
        this.aW = n2;
        this.bjj = vi_12;
        this.bjc = n3;
        this.bjd = bl2;
        this.apC = n4;
        this.apB = n5;
        this.bje = bl3;
        this.bjf = bl4;
        this.r = n6;
        this.bjg = bl5;
        this.bjh = bl6;
        this.bji = false;
    }

    public int getId() {
        return this.aW;
    }

    public vi_1 Vk() {
        return this.bjj;
    }

    public int getValue() {
        return this.r;
    }

    public void release() {
    }

    public long je() {
        return this.aW;
    }

    public long iO() {
        return this.aW;
    }

    public int jf() {
        return this.aW;
    }

    public boolean b(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("AbstractFighterCard is static and can't be unserialized. Need to be get from a provider.");
    }

    public void b(byte[] byArray) {
        throw new UnsupportedOperationException("AbstractFighterCard is static and can't be unserialized. Need to be get from a provider.");
    }

    public short hG() {
        return 1;
    }

    public void q(short s) {
        throw new UnsupportedOperationException("FIghterCard can't be stacked");
    }

    public void w(short s) {
        throw new UnsupportedOperationException("FIghterCard can't be stacked");
    }

    public short jg() {
        return 1;
    }

    public boolean e(uh_1 uh_12) {
        return false;
    }

    public void a(xj_0 xj_02) {
        if ("FIGHTER_CARD_USE".equals(xj_02.alq().trim())) {
            this.bja.add(xj_02);
            return;
        }
        if ("FIGHTER_CARD_EQUIP".equals(xj_02.alq().trim())) {
            this.bjb.add(xj_02);
            return;
        }
        a.error((Object)("Impossible d'ajouter un effet pour la carte " + this.aW + " : type de parent invalide : " + xj_02.alq()));
    }

    public void a(xj_0[] xj_0Array) {
        for (xj_0 xj_02 : xj_0Array) {
            this.a(xj_02);
        }
    }

    public int iP() {
        return 12;
    }

    public Iterator iterator() {
        return new bm_0(this.bja.iterator(), this.bjb.iterator());
    }

    public boolean isUsable() {
        return this.bja.size() > 0;
    }

    public static int Vl() {
        return 4;
    }

    public Iterable Vm() {
        return this.bjb;
    }

    public Iterable Vn() {
        return this.bja;
    }

    public int Vo() {
        return this.bjc;
    }

    public boolean Vp() {
        return this.bjd;
    }

    public int AA() {
        return this.apC;
    }

    public int Az() {
        return this.apB;
    }

    public boolean iW() {
        return this.bje;
    }

    public boolean Vq() {
        return this.bjf;
    }

    public boolean Vr() {
        return this.bjg;
    }

    public boolean Vs() {
        return this.bjh;
    }

    public acy Vt() {
        return this.bja;
    }

    public acy Vu() {
        return this.bjb;
    }

    public uh_1 G(boolean bl2) {
        try {
            return (jb_2)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("Unable to copy AbstractFighterCard", cloneNotSupportedException);
        }
    }

    public uh_1 jh() {
        try {
            return (jb_2)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("Unable to clone AbstractFighterCard", cloneNotSupportedException);
        }
    }

    public boolean ji() {
        return true;
    }

    public String toString() {
        return "(" + this.aW + ", " + this.bjj + ", " + this.bjc + ")";
    }
}

