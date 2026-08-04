/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Renamed from dO
 */
public abstract class do_1
extends apn_0 {
    private String m_name;
    protected short mS = 0;
    protected boolean mT = false;
    private atx mU = atx.cTX;
    protected boolean mV = false;
    protected boolean mW = false;
    protected boolean mX = true;
    protected boolean mY = false;
    protected int mZ = 0;
    protected boolean na = false;
    private final ArrayList nb = new ArrayList();
    protected FreeParticleSystem nc;
    protected static aod_2 nd;
    protected boolean ne = false;
    private final aea_0 nf = new RU(this);
    private final aea_0 ng = new rv_0(this, 3);

    public void P(String string) {
        this.cmX = string;
    }

    protected final aea_0 ga() {
        return this.nf;
    }

    protected aea_0 gb() {
        return this.ng;
    }

    protected aea_0 gd() {
        return aea_0.dBr;
    }

    public short ge() {
        return 4;
    }

    public final void a(avr_0 avr_02) {
        bd_2 bd_22 = new bd_2();
        bd_22.f(avr_02.aJg());
        bd_22.k(this.getId());
        apN.aDK().vJ().b(bd_22);
    }

    public boolean gf() {
        return this.na;
    }

    public ry gg() {
        return this.baN;
    }

    public void b() {
        this.cmW.clear();
    }

    public boolean b(avr_0 avr_02, aox_2 aox_22) {
        boolean bl2 = super.b(avr_02, aox_22);
        if (aox_22.getId() == apN.aDK().Ln().getId()) {
            aoj_2.aCR().d(new vi_0(this.getId(), avr_02.aJg()));
            xy_0 xy_02 = xy_0.bYl;
            apw_1.aDr().a(xy_02);
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.m_name = "";
        this.baN.reset();
        this.amP = 1;
        this.cmU = 0;
        this.aQv = true;
        this.agj = true;
        this.mX = true;
        this.na = false;
        this.nb.clear();
        this.mV = false;
        this.mW = false;
        this.mY = false;
        this.mZ = 0;
        this.mS = 0;
        this.mT = false;
        this.ne = false;
        this.mU = atx.cTX;
    }

    public void gh() {
        ArrayList arrayList = agm_2.bI(this.cmV);
        if (arrayList.contains(agm_2.ctZ)) {
            this.na = true;
            arrayList.remove(agm_2.ctZ);
        } else {
            this.na = false;
        }
        ry ry2 = new ry();
        this.nb.clear();
        auU.a((short)6, (byte)0, (short)4);
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            int[] nArray = qc_0.hf((this.L().getIndex() + ((agm_2)arrayList.get(j)).awn()) % 8).acJ();
            ry2.l(this.baN.getX() + nArray[0], this.baN.getY() + nArray[1], this.baN.wk());
            short s = auU.I(ry2.getX(), ry2.getY(), ry2.wk());
            if (s == Short.MIN_VALUE) continue;
            this.nb.add(new ry(ry2));
        }
    }

    public void gi() {
        if (this.nc != null) {
            this.nc.a(this.baN.getX(), this.baN.getY(), this.baN.wk());
            qd_1.uW().b(this.nc);
        }
    }

    public void gj() {
    }

    public List gk() {
        return this.nb;
    }

    public String getName() {
        return this.m_name;
    }

    public short gl() {
        return this.mS;
    }

    public FreeParticleSystem gm() {
        return this.nc;
    }

    public int gn() {
        return this.baN.getX();
    }

    public int go() {
        return this.baN.getY();
    }

    public short gp() {
        return this.baN.wk();
    }

    public final atx gq() {
        return this.mU;
    }

    protected final void a(atx atx2) {
        this.mU = atx2;
    }

    public boolean gr() {
        return this.mY;
    }

    public boolean gs() {
        return this.mX;
    }

    public boolean gt() {
        return this.mW;
    }

    public boolean gu() {
        return this.mV;
    }

    public boolean gv() {
        return this.ne;
    }

    public boolean gw() {
        return this.mT;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public void h(short s) {
        this.mS = s;
    }

    public void A(boolean bl2) {
        this.mY = bl2;
    }

    public void setSelectable(boolean bl2) {
        this.mX = bl2;
    }

    public final void i(short s) {
        this.amP = s;
    }

    public void B(boolean bl2) {
        this.mW = bl2;
    }

    public void C(boolean bl2) {
        this.mV = bl2;
    }

    public void D(boolean bl2) {
        this.ne = bl2;
    }

    public void E(boolean bl2) {
        this.mT = bl2;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
    }

    public void j(short s) {
        this.cmU = s;
    }

    public int gx() {
        return this.mZ;
    }

    public String gy() {
        return null;
    }

    public static do_1 a(long l2, byte[] byArray) {
        do_1 do_12 = (do_1)me_2.qR().eP(l2);
        if (do_12 == null) {
            a.error((Object)("Impossible de spawner l'\u00e9l\u00e9ment interactif instanceId=" + l2));
            return null;
        }
        do_12.ad(byArray);
        for (axu_0 axu_02 : do_12.aYW()) {
            if (!(axu_02 instanceof tp_1)) continue;
            tp_1 tp_12 = (tp_1)axu_02;
            GY.Ss().b(tp_12);
            ajh_2.b(tp_12);
        }
        do_12.aYY();
        return do_12;
    }

    public boolean a(amg_1 amg_12) {
        return !this.gf() && this.gk().contains(amg_12.aTI());
    }

    public final byte[] gz() {
        throw new UnsupportedOperationException("Le client ne s\u00e9rialise pas les donn\u00e9es persistantes");
    }

    public boolean gA() {
        return !apN.aDK().aDN();
    }

    public static boolean gB() {
        if (nd != null) {
            nd.aab();
            nd = null;
            return true;
        }
        return false;
    }

    public boolean gC() {
        return false;
    }

    public void gD() {
    }

    public xy_0 getCursorType() {
        return null;
    }

    static /* synthetic */ short a(do_1 do_12, short s) {
        do_12.cmU = s;
        return do_12.cmU;
    }

    static /* synthetic */ ry a(do_1 do_12) {
        return do_12.baN;
    }

    static /* synthetic */ ry b(do_1 do_12) {
        return do_12.baN;
    }

    static /* synthetic */ ry c(do_1 do_12) {
        return do_12.baN;
    }

    static /* synthetic */ short b(do_1 do_12, short s) {
        do_12.amP = s;
        return do_12.amP;
    }

    static /* synthetic */ boolean a(do_1 do_12, boolean bl2) {
        do_12.aQv = bl2;
        return do_12.aQv;
    }

    static /* synthetic */ boolean b(do_1 do_12, boolean bl2) {
        do_12.agj = bl2;
        return do_12.agj;
    }

    static /* synthetic */ void a(do_1 do_12, qc_0 qc_02) {
        do_12.b(qc_02);
    }

    static /* synthetic */ short c(do_1 do_12, short s) {
        do_12.cmV = s;
        return do_12.cmV;
    }

    static /* synthetic */ HashSet d(do_1 do_12) {
        return do_12.cmW;
    }

    static /* synthetic */ String a(do_1 do_12, String string) {
        do_12.cmX = string;
        return do_12.cmX;
    }

    static /* synthetic */ short d(do_1 do_12, short s) {
        do_12.amP = s;
        return do_12.amP;
    }

    static /* synthetic */ boolean c(do_1 do_12, boolean bl2) {
        do_12.aQv = bl2;
        return do_12.aQv;
    }
}

