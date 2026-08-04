/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/*
 * Renamed from adw
 */
public abstract class adw_0
extends aJj
implements ri_1 {
    public static final byte cmT = 0;
    protected long nD;
    private short uM;
    protected final ry baN = new ry();
    protected short cmU;
    protected short amP;
    protected boolean aQv;
    protected boolean agj;
    protected short cmV;
    protected final HashSet cmW = new HashSet();
    private qc_0 ak;
    protected String cmX;
    protected boolean cmY;
    protected boolean cmZ;
    protected byte cna = 0;
    private HashSet cnb = new HashSet();
    private ym_0 rI;
    private ArrayList cnc;

    protected adw_0() {
    }

    public final short asH() {
        return this.cmV;
    }

    public qc_0 L() {
        return this.ak;
    }

    public abstract int gn();

    public abstract int go();

    public abstract short gp();

    protected void b(qc_0 qc_02) {
        this.ak = qc_02;
    }

    public long getId() {
        return this.nD;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public void a(Ll ll) {
        this.cnb.add(ll);
    }

    public void c(Collection collection) {
        this.cnb.addAll(collection);
    }

    public final void d(Collection collection) {
        this.cnb.clear();
        if (collection != null) {
            this.cnb.addAll(collection);
        }
    }

    public void b(Ll ll) {
        this.cnb.remove(ll);
    }

    public void e(Collection collection) {
        this.cnb.removeAll(collection);
    }

    public boolean c(Ll ll) {
        return this.cnb.contains(ll);
    }

    public Iterator asI() {
        return this.cnb.iterator();
    }

    public int asJ() {
        return this.cnb.size();
    }

    public final short getState() {
        return this.amP;
    }

    public void i(short s) {
        this.amP = s;
    }

    public final short asK() {
        return this.cmU;
    }

    public void j(short s) {
        this.cmU = s;
    }

    public final ry asL() {
        return this.baN;
    }

    public final boolean asM() {
        return this.cmZ;
    }

    public void de(boolean bl2) {
        this.cmZ = bl2;
    }

    public boolean asN() {
        return this.cmY && this.aQv;
    }

    public final boolean isUsable() {
        return this.agj;
    }

    public void setUsable(boolean bl2) {
        this.agj = bl2;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
        if (!bl2) {
            this.df(false);
        }
    }

    public void P(String string) {
        this.cmX = string.intern();
    }

    protected void a(ym_0 ym_02) {
        this.rI = ym_02;
    }

    public final aea_0[] asO() {
        return new aea_0[]{this.gb(), this.gd()};
    }

    public void b() {
    }

    public void j() {
        this.nD = 0L;
        this.uM = 0;
        this.baN.reset();
        this.amP = 1;
        this.cmU = 0;
        this.aQv = false;
        this.agj = false;
        this.cmV = 0;
        this.cmW.clear();
        this.ak = qc_0.bEQ;
        this.cmX = null;
        this.cmZ = false;
        this.cmY = false;
        if (this.cnc != null) {
            this.cnc.clear();
        }
    }

    public final void release() {
        if (this.rI != null) {
            try {
                this.rI.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"Erreur lors du retour au pool", (Throwable)exception);
            }
            this.rI = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString() + " : " + bl_0.dH()));
            this.j();
        }
    }

    public final void a(aKj aKj2) {
        if (this.cnc == null) {
            this.cnc = new ArrayList(1);
        }
        this.cnc.add(aKj2);
    }

    public final void asP() {
        this.cnc.clear();
        this.cnc = null;
    }

    public final boolean y(ry ry2) {
        return this.cmW.contains(ry2);
    }

    public final void asQ() {
        if (this.cnc != null) {
            for (aKj aKj2 : this.cnc) {
                aKj2.a(this);
            }
        }
    }

    public aea_0[] Kl() {
        return new aea_0[]{this.asR(), this.ga(), this.gb(), this.gd(), this.asS()};
    }

    protected abstract aea_0 asR();

    protected abstract aea_0 ga();

    protected abstract aea_0 gb();

    protected abstract aea_0 gd();

    protected abstract aea_0 asS();

    public final void b(aKj aKj2) {
        if (this.cnc != null) {
            this.cnc.remove(aKj2);
        }
    }

    public void df(boolean bl2) {
        this.cmY = bl2;
    }

    public String toString() {
        return "[" + this.getClass().getSimpleName() + " id=" + this.getId() + "]";
    }

    protected abstract dc_0 asT();

    public void at(byte by) {
        this.cna = by;
    }

    public byte asU() {
        return this.cna;
    }

    public void y(short s) {
        this.uM = s;
    }

    public short wC() {
        return this.uM;
    }

    public boolean asV() {
        return false;
    }
}

