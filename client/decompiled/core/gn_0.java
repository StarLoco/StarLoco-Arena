/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from Gn
 */
public abstract class gn_0
implements JG,
afJ,
alp_0 {
    protected static final Logger a = Logger.getLogger(gn_0.class);
    protected acl_0 uG;
    protected long nD;
    protected xq baJ;
    protected String m_name = "";
    protected byte ey;
    protected byte zu;
    protected byte zt;
    protected byte aRl;
    protected byte zv;
    protected byte baK;
    protected cl_1 baL;
    protected yg_0 Aw;
    protected qc_0 ak = qc_0.bEK;
    protected short baM;
    private final ry baN = new ry();
    protected final alf_1 baO = new alf_1();
    protected final ep_0 baP = new ep_0();
    protected final lb_0 baQ = new lb_0();
    protected final aLM baR = new aLM();
    protected final sH baS = new sH();
    protected boolean baT = false;
    private String baU;
    protected gn_0 baV;
    protected gn_0 baW;
    protected ArrayList baX = new ArrayList();
    protected jg_0 aRD = new jg_0();
    protected vy_1 uk = new vy_1();
    protected boolean baY;
    protected int aRw;
    protected ze_0 baZ = new ze_0(this);
    protected int bba = 0;
    private boolean bbb = false;

    public void b() {
        ++this.bba;
        this.nD = 0L;
        this.baJ = xq.axD;
        this.m_name = "";
        this.ey = (byte)-1;
        this.zu = 0;
        this.zt = (byte)8;
        this.aRl = (byte)24;
        this.zv = 0;
        this.baL = null;
        this.baO.clear();
        this.baN.reset();
        this.ak = qc_0.bEK;
        this.baM = 0;
        this.baP.reset();
        this.baS.reset();
        this.baT = false;
        this.baW = null;
        this.baV = null;
        this.baR.reset();
        this.bbb = false;
        this.baK = (byte)-1;
        this.baY = false;
        this.baX.clear();
    }

    public void j() {
        --this.bba;
        this.nD = 0L;
        this.baJ = xq.axD;
        this.m_name = "";
        this.ey = (byte)-1;
        this.zu = 0;
        this.zt = (byte)8;
        this.aRl = (byte)24;
        this.zv = 0;
        this.baL = null;
        this.baO.pG();
        this.baN.reset();
        this.ak = null;
        this.baM = 0;
        this.baP.reset();
        this.baS.reset();
        this.baT = false;
        this.baW = null;
        this.baV = null;
        this.baR.reset();
        this.bbb = false;
    }

    protected gn_0() {
        for (Lr lr : Lr.values()) {
            this.baQ.c(lr.lV(), new aeq_0(lr, lr.Xz(), lr.XA()));
        }
    }

    public void release() {
        if (this.uG != null) {
            if (this.bba < 1) {
                a.error((Object)("Old Stack Trace : " + this.getClass().toString()));
                a.error((Object)this.baU);
                a.error((Object)"Double Release", (Throwable)new Exception());
                return;
            }
            if (this.bba > 1) {
                a.error((Object)"Double checkout", (Throwable)new Exception());
                return;
            }
            Exception exception = new Exception();
            this.baU = "";
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                this.baU = this.baU + stackTraceElement.toString() + " \n";
            }
            try {
                this.uG.af(this);
            }
            catch (Exception exception2) {
                a.error((Object)"ne peut arriver normalement");
            }
            this.uG = null;
        } else {
            this.j();
        }
    }

    public long getId() {
        return this.nD;
    }

    public byte FG() {
        return 3;
    }

    public boolean Px() {
        return true;
    }

    public void Y(byte by) {
        this.baK = by;
    }

    public byte Py() {
        return this.baK;
    }

    public boolean Pz() {
        return true;
    }

    public boolean PA() {
        return true;
    }

    public boolean PB() {
        return !this.b(avx_0.deu);
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public cl_1 LQ() {
        return this.baL;
    }

    public void a(cl_1 cl_12) {
        this.baL = cl_12;
    }

    public cl_1 LR() {
        return this.baL;
    }

    public void LS() {
    }

    public abstract boolean Dk();

    public abstract gn_0 Ot();

    public int PC() {
        int n2 = 0;
        if (this.Oc() != null) {
            Iterator iterator = this.Oc().aKq();
            while (iterator.hasNext()) {
                gn_0 gn_02 = (gn_0)iterator.next();
                if (gn_02.Ot() != this || !gn_02.Dk()) continue;
                ++n2;
            }
        }
        return n2;
    }

    public abstract xq NY();

    public void W(byte by) {
        this.baJ = xq.ej(by);
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void bs(boolean bl2) {
        this.baY = bl2;
    }

    public boolean NK() {
        return this.baY;
    }

    public void fy(int n2) {
    }

    public void fz(int n2) {
    }

    public void fA(int n2) {
    }

    public byte cc() {
        return this.ey;
    }

    public void b(byte by) {
        this.ey = by;
    }

    public byte lY() {
        return this.zu;
    }

    public void P(byte by) {
        this.zu = by;
    }

    public byte lX() {
        return this.zt;
    }

    public void Q(byte by) {
        this.zt = by;
    }

    public byte Ns() {
        return this.aRl;
    }

    public void R(byte by) {
        this.aRl = by;
    }

    public byte lZ() {
        return this.zv;
    }

    public void S(byte by) {
        this.zv = by;
    }

    public void b(byte by, byte by2, byte by3) {
        this.ey = by;
        this.baJ = xq.ej(by2);
        this.zv = by3;
    }

    public void m(ry ry2) {
        this.m(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public void m(int n2, int n3, short s) {
        aoq_0 aoq_02;
        assert (s >= -512 && s <= 511) : "Altitude of the position is out of bounds : " + s;
        if (this.Oc() != null && (aoq_02 = this.Oc().gV()) != null) {
            aoq_02.a(this, n2, n3);
        }
        this.baN.l(n2, n3, s);
        if (this.baV != null) {
            this.baV.m(n2, n3, (short)(s + this.PE()));
        }
    }

    public ry gg() {
        return this.baN;
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

    public double getWorldX() {
        return this.gn();
    }

    public double getWorldY() {
        return this.go();
    }

    public double getAltitude() {
        return this.gp();
    }

    public qc_0 L() {
        return this.ak;
    }

    public void b(ye_0 ye_02) {
        this.ak = (qc_0)ye_02;
    }

    public byte PD() {
        return (byte)(6 + (this.baV != null ? (int)this.baV.PD() : 0));
    }

    public byte ox() {
        return 0;
    }

    public short PE() {
        return 6;
    }

    public short BP() {
        return 4;
    }

    public boolean PF() {
        int n2 = this.d(Lr.bqU);
        return ou_1.he(100) <= n2;
    }

    public boolean PG() {
        int n2 = this.d(Lr.bqV);
        return n2 > 0 && ou_1.he(100) <= n2;
    }

    public yg_0 PH() {
        return this.Aw;
    }

    public void a(yg_0 yg_02) {
        this.Aw = yg_02;
    }

    public boolean fE(int n2) {
        return false;
    }

    public void fF(int n2) {
    }

    public void PI() {
        int n2 = 0;
        xq xq2 = this.NY();
        if (xq2 != null) {
            n2 = (short)(n2 + xq2.getValue());
        }
        if (this.Oi() != null) {
            for (Pi pi : this.Oi()) {
                n2 = (short)(n2 + ((jb_2)pi).getValue());
            }
        }
        if (this.Oh() != null) {
            for (Pi pi : this.Oh()) {
                n2 = (short)(n2 + ((fv)pi).getValue());
            }
        }
        this.baM = (short)n2;
    }

    public short Oo() {
        return this.baM;
    }

    public alf_1 PJ() {
        return this.baO;
    }

    public void PK() {
        xq xq2 = this.NY();
        if (xq2 == null) {
            throw new IllegalArgumentException("Impossible d'initialiser un fighter : race d'id " + this.baJ + " inconnue");
        }
        ll_0 ll_02 = this.baQ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            alm_0 alm_02 = (alm_0)ll_02.value();
            alm_02.atS();
        }
        if (this.baY) {
            this.a(Lr.bqx).at(xq2.DV());
            this.a(Lr.bqz).at(xq2.DX());
            this.a(Lr.bqy).at(xq2.DW());
            this.a(Lr.brd).set(xq2.DY());
            this.a(Lr.bre).set(xq2.DZ());
            this.a(Lr.brb).kb(xq2.Ea());
            this.a(Lr.brb).jZ(xq2.Ea());
            this.a(Lr.bra).kb(xq2.Eb());
            this.a(Lr.bra).jZ(xq2.Eb());
            this.a(Lr.bqX).kb(xq2.Ec());
            this.a(Lr.bqX).jZ(xq2.Ec());
            this.a(Lr.brg).jZ(xq2.Ed());
            this.a(Lr.brh).jZ(xq2.Ee());
            this.a(Lr.bri).jZ(xq2.Ef());
            this.a(Lr.brj).jZ(xq2.Eg());
            this.a(Lr.brk).jZ(xq2.Eh());
        } else {
            this.a(Lr.bqx).at(xq2.ok());
            this.a(Lr.bqz).at(xq2.om());
            this.a(Lr.bqy).at(xq2.ol());
            this.a(Lr.brd).set(xq2.DT());
            this.a(Lr.bre).set(xq2.DU());
        }
        this.a(Lr.bqA).at(xq2.DK());
        this.a(Lr.bqA).aAF();
        this.a(Lr.bqU).set(xq2.DL());
        this.a(Lr.bqV).set(xq2.DM());
        this.a(Lr.bqy).aAF();
        this.a(Lr.bqx).aAF();
        this.a(Lr.bqz).aAF();
    }

    public alm_0 a(aiq_2 aiq_22) {
        return (alm_0)this.baQ.get(aiq_22.lV());
    }

    public boolean b(aiq_2 aiq_22) {
        return this.baQ.contains(aiq_22.lV());
    }

    public int c(aiq_2 aiq_22) {
        alm_0 alm_02 = (alm_0)this.baQ.get(aiq_22.lV());
        if (alm_02 != null) {
            return alm_02.atR();
        }
        throw new UnsupportedOperationException("caract\u00e9ristique inexistante");
    }

    public int d(aiq_2 aiq_22) {
        alm_0 alm_02 = (alm_0)this.baQ.get(aiq_22.lV());
        if (aiq_22 == Lr.bqz && (this.PL().b((aak_2)avx_0.dew) || this.PL().b((aak_2)avx_0.dex))) {
            return 0;
        }
        if (aiq_22 == Lr.bqy && this.PL().b((aak_2)avx_0.dew)) {
            return 0;
        }
        if (alm_02 != null) {
            return alm_02.value();
        }
        throw new UnsupportedOperationException("caract\u00e9ristique inexistante");
    }

    public static int a(int n2, Lr lr) {
        return Math.max(lr.XB(), Math.min(lr.XC(), n2));
    }

    public aLM PL() {
        return this.baR;
    }

    public ep_0 PM() {
        if (this.ak == null) {
            this.ak = qc_0.bEK;
        }
        this.baP.a(this.baN.getX(), this.baN.getY(), this.baN.wk(), this.ak);
        return this.baP;
    }

    public sH PN() {
        return this.baS;
    }

    public void a(fv fv2, int n2, short s) {
        this.baS.a(fv2, n2, s);
    }

    public abstract gn_0 d(long var1, ry var3, int var4);

    public abstract gn_0 b(long var1, ry var3);

    public abstract gn_0 c(long var1, ry var3, int var4);

    public void ay(short s) {
    }

    public abstract ajv_2 Oh();

    public abstract ajv_2 Oj();

    public abstract en_1 Oi();

    public boolean PO() {
        return !this.Od();
    }

    public void b(kc_2 kc_22) {
    }

    public void c(kc_2 kc_22) {
        throw new UnsupportedOperationException("not implemented in DA");
    }

    public void d(kc_2 kc_22) {
    }

    public boolean PP() {
        return this.Oc() != null && this.a(Lr.bqx).atR() <= 0 && !this.baT;
    }

    public boolean PQ() {
        return false;
    }

    public boolean PR() {
        return this.baT;
    }

    public boolean PS() {
        return this.PR();
    }

    public boolean PT() {
        return this.PR();
    }

    public void f(et_2 et_22) {
        this.setName(et_22.getName());
        this.bs(et_22.NK());
        this.fv(et_22.Ny());
        this.b(et_22.cc(), et_22.cu(), et_22.lZ());
        this.b(et_22.cc());
        this.P(et_22.lY());
        this.Q(et_22.lX());
        this.R(et_22.Ns());
        this.PK();
        this.Oh().d(et_22.Nt());
        this.Oi().d(et_22.Nu());
        this.a(et_22.NE(), et_22.kh());
        this.PI();
    }

    public boolean b(ByteBuffer byteBuffer) {
        boolean bl2 = false;
        try {
            int n2;
            this.c(byteBuffer.getLong());
            byte by = byteBuffer.get();
            byte[] byArray = new byte[byteBuffer.get()];
            byteBuffer.get(byArray);
            this.setName(new String(byArray));
            byte by2 = byteBuffer.get();
            byte by3 = byteBuffer.get();
            this.b(by3, by, by2);
            this.P(byteBuffer.get());
            this.Q(byteBuffer.get());
            this.R(byteBuffer.get());
            this.bs(byteBuffer.get() == 1);
            this.fv(byteBuffer.getInt());
            this.PK();
            byte[] byArray2 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray2);
            this.Oh().d(byArray2);
            byte[] byArray3 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray3);
            this.Oi().d(byArray3);
            byte[] byArray4 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray4);
            this.PN().b(byArray4);
            jg_0 jg_02 = new jg_0();
            int n3 = byteBuffer.getShort();
            for (int j = 0; j < n3; ++j) {
                jg_02.add(byteBuffer.getInt());
            }
            vy_1 vy_12 = new vy_1();
            n3 = byteBuffer.getShort();
            for (n2 = 0; n2 < n3; ++n2) {
                vy_12.b(byteBuffer.getShort(), (byte)1);
            }
            this.a(jg_02, vy_12);
            n2 = byteBuffer.getInt();
            this.a(Lr.bqx).set(this.a(Lr.bqx).max() - n2);
            int n4 = byteBuffer.getInt();
            this.a(Lr.bqz).set(this.a(Lr.bqz).max() - n4);
            this.a(Lr.brn).set(n4);
            int n5 = byteBuffer.getInt();
            this.a(Lr.bqy).set(this.a(Lr.bqy).max() - n5);
            this.a(Lr.brm).set(n5);
            this.PI();
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            a.error((Object)"pas assez de donn\u00e9es pour completer la cr\u00e9ation d'un Fighter");
            return false;
        }
        return !bl2;
    }

    public byte[] cd() {
        byte[] byArray = this.m_name.getBytes();
        byte[] byArray2 = this.Oh().cd();
        byte[] byArray3 = this.Oi().cd();
        byte[] byArray4 = this.PN().cd();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10 + byArray.length + 1 + 1 + 1 + 1 + 1 + 1 + 4 + 2 + byArray2.length + 2 + byArray3.length + 2 + byArray4.length + 2 + 4 * this.aRD.size() + 2 + 2 * this.uk.size() + 4 + 4 + 4);
        byteBuffer.putLong(this.getId());
        byteBuffer.put(this.baJ.lV());
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put(this.zv);
        byteBuffer.put(this.ey);
        byteBuffer.put(this.zu);
        byteBuffer.put(this.zt);
        byteBuffer.put(this.aRl);
        byteBuffer.put(this.baY ? (byte)1 : 0);
        byteBuffer.putInt(this.aRw);
        byteBuffer.putShort((short)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.putShort((short)byArray3.length);
        byteBuffer.put(byArray3);
        byteBuffer.putShort((short)byArray4.length);
        byteBuffer.put(byArray4);
        byteBuffer.putShort((short)this.aRD.size());
        int n2 = this.aRD.size();
        for (int j = 0; j < n2; ++j) {
            byteBuffer.putInt(this.aRD.bu(j));
        }
        byteBuffer.putShort((short)this.uk.size());
        short[] sArray = this.uk.Gj();
        int n3 = sArray.length;
        for (n2 = 0; n2 < n3; ++n2) {
            byteBuffer.putShort(sArray[n2]);
        }
        byteBuffer.putInt(this.a(Lr.bqx).max() - this.d(Lr.bqx));
        byteBuffer.putInt(this.a(Lr.bqz).max() - this.d(Lr.bqz));
        byteBuffer.putInt(this.a(Lr.bqy).max() - this.d(Lr.bqy));
        return byteBuffer.array();
    }

    public byte getType() {
        return 0;
    }

    public byte[] PU() {
        return ug_2.EMPTY_BYTE_ARRAY;
    }

    public void H(byte[] byArray) {
    }

    public int PV() {
        return 0;
    }

    public byte[] PW() {
        return ug_2.EMPTY_BYTE_ARRAY;
    }

    public void I(byte[] byArray) {
    }

    public int PX() {
        return 0;
    }

    private aoq_0 gV() {
        mv_1 mv_12 = this.Oc();
        return mv_12 == null ? null : mv_12.gV();
    }

    public abstract mv_1 Oc();

    protected void b(jb_2 jb_22) {
        if (jb_22 == null) {
            return;
        }
        ea_0 ea_02 = this.Oc() != null ? this.Oc().Np() : this.baZ;
        for (xj_0 xj_02 : jb_22.Vm()) {
            xj_02.a(jb_22, this, ea_02, mh_2.YJ(), this.gn(), this.go(), this.gp(), null, null);
        }
    }

    protected abstract void a(jg_0 var1, vy_1 var2);

    protected void a(jg_0 jg_02, vy_1 vy_12, ib_2 ib_22) {
        int n2;
        int n3;
        ArrayList arrayList;
        Pi pi;
        this.PJ().h(15, true);
        this.e(jg_02);
        if (jg_02 == null || jg_02.isEmpty()) {
            return;
        }
        ea_0 ea_02 = this.Oc() != null ? this.Oc().Np() : this.baZ;
        int n4 = jg_02.size();
        for (int j = 0; j < n4; ++j) {
            pi = ib_22.aV(jg_02.get(j));
            if (pi == null) continue;
            arrayList = ((ajM)pi).eC();
            n3 = arrayList.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((xj_0)arrayList.get(n2)).a(pi, this, ea_02, mh_2.YJ(), this.gn(), this.go(), this.gp(), this, null);
            }
        }
        this.a(vy_12);
        short[] sArray = vy_12.Gj();
        for (n4 = 0; n4 < sArray.length; ++n4) {
            pi = bf_1.df().g(sArray[n4]);
            if (pi == null) continue;
            arrayList = ((aiz_2)pi).eC();
            n3 = arrayList.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((xj_0)arrayList.get(n2)).a(pi, this, ea_02, mh_2.YJ(), this.gn(), this.go(), this.gp(), this, null);
            }
        }
    }

    protected void c(jb_2 jb_22) {
        if (jb_22 == null) {
            return;
        }
        this.PJ().a(jb_22, true);
    }

    public gn_0 PY() {
        return this.baV;
    }

    public void h(gn_0 gn_02) {
        this.baV = gn_02;
    }

    public void g(gn_0 gn_02) {
        this.baW = gn_02;
    }

    public gn_0 PZ() {
        return this.baW;
    }

    public boolean Qa() {
        return this.baV != null;
    }

    public boolean rD() {
        return this.baW != null;
    }

    public void d(ack_1 ack_12) {
        this.baX.remove(ack_12);
    }

    public void e(ack_1 ack_12) {
        this.baX.add(ack_12);
    }

    public ArrayList Qb() {
        return this.baX;
    }

    public boolean i(gn_0 gn_02) {
        if (this.Qa() || this.rD()) {
            return false;
        }
        if (gn_02 == null || gn_02 == this) {
            return false;
        }
        if (gn_02.b(avx_0.deA)) {
            return false;
        }
        if (gn_02.rD() || gn_02.PL().b((aak_2)avx_0.deA)) {
            return false;
        }
        if (this.PD() == Short.MAX_VALUE) {
            return false;
        }
        ry ry2 = this.gg();
        gn_02.m(ry2.getX(), ry2.getY(), (short)(ry2.wk() + this.PE()));
        this.h(gn_02);
        gn_02.g(this);
        gn_02.b(this.L());
        this.PL().a(avx_0.deA);
        this.PL().a(avx_0.deB);
        return true;
    }

    public boolean a(ry ry2, boolean bl2) {
        if (this.Qa()) {
            this.baV.m(ry2);
            this.baV.g(null);
            this.PL().b(avx_0.deA);
            this.PL().b(avx_0.deB);
            if (bl2) {
                this.gV().a(this, true);
            }
            this.h(null);
            return true;
        }
        return false;
    }

    protected boolean b(ry ry2, boolean bl2) {
        return this.a(ry2, bl2);
    }

    public void bm(boolean bl2) {
        if (this.Qa()) {
            this.a(this.gg(), bl2);
        }
    }

    public boolean a(ry ry2, qc_0 qc_02) {
        gn_0 gn_02 = this.PY();
        if (this.b(ry2, true)) {
            gn_02.b(qc_02);
            return true;
        }
        return false;
    }

    public boolean b(aak_2 aak_22) {
        if (this.baR != null) {
            return this.baR.b((aak_2)((avx_0)aak_22));
        }
        return false;
    }

    public byte c(aak_2 aak_22) {
        return this.baR.c((aak_2)((avx_0)aak_22));
    }

    public void d(aak_2 aak_22) {
        if (this.baR != null) {
            this.baR.a((avx_0)aak_22);
        }
    }

    public void e(aak_2 aak_22) {
        if (this.baR != null) {
            this.baR.b((avx_0)aak_22);
        }
    }

    public void f(aak_2 aak_22) {
        if (this.baR != null) {
            this.baR.c((avx_0)aak_22);
        }
    }

    public boolean a(BitSet bitSet, xb_2 xb_22, byte by) {
        if (this.PJ() != null) {
            return this.PJ().a(bitSet, xb_22, by);
        }
        return false;
    }

    public void a(axw axw2) {
    }

    public abstract void Ok();

    public void Qc() {
        this.baT = true;
    }

    public void Qd() {
        throw new UnsupportedOperationException("not implemented in DA");
    }

    public void Qe() {
    }

    public void Qf() {
    }

    public boolean Qg() {
        return !this.bbb;
    }

    public void bt(boolean bl2) {
        this.bbb = bl2;
    }

    public void o(int n2, int n3, short s) {
    }

    public void n(ry ry2) {
    }

    public int Pq() {
        return fl_2.rO;
    }

    public int Qh() {
        return fl_2.rR;
    }

    public int Qi() {
        return fl_2.rQ;
    }

    public jg_0 NE() {
        return this.aRD;
    }

    public void e(jg_0 jg_02) {
        this.aRD = jg_02;
    }

    public vy_1 kh() {
        return this.uk;
    }

    public void a(vy_1 vy_12) {
        this.uk = vy_12;
    }

    public int Ny() {
        return this.aRw;
    }

    public void fv(int n2) {
        this.aRw = n2;
    }

    private static String k(ry ry2) {
        return ry2 == null ? "null" : "(" + ry2.getX() + ", " + ry2.getY() + ", " + ry2.wk() + ")";
    }

    public String toString() {
        return "(" + this.nD + ", " + this.getName() + ", " + this.NY() + ", " + gn_0.k(this.baN) + ")";
    }
}

