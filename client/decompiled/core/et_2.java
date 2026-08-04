/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from Et
 */
public class et_2
extends at_0 {
    protected static final Logger a = Logger.getLogger(et_2.class);
    protected acl_0 uG;
    public static final acl_0 aU = new ym_0(new zp_0());
    public static final byte aRd = 1;
    public static final byte aRe = 2;
    public static final byte aRf = 0;
    public static final byte aRg = 1;
    public static final int aRh = 0;
    public static final byte aRi = -1;
    public static final byte aRj = -1;
    protected byte ey;
    protected byte aIm = 1;
    protected byte fp;
    protected int aRk = 0;
    protected String m_name = "";
    protected byte zu;
    protected byte zt;
    protected byte aRl;
    protected byte zv;
    protected byte[] aRm;
    protected byte[] aRn;
    protected short aRo = (short)600;
    public static final byte aRp = 0;
    public static final byte aRq = 1;
    public static final byte aRr = 2;
    public static final byte aRs = 3;
    public static final byte aRt = 4;
    public static final byte aRu = 5;
    protected int aRv;
    protected int aRw;
    protected byte aRx;
    protected byte aRy;
    protected byte aRz;
    protected short aRA;
    protected short aRB;
    protected int aRC;
    protected jg_0 aRD = new jg_0();
    protected vy_1 uk = new vy_1();
    protected jg_0 aRE = new jg_0();
    protected jg_0 aRF = new jg_0();
    protected adl_0 aRG = new adl_0();

    public static et_2 a(byte by, String string, byte by2, byte by3, byte by4, byte by5, byte[] byArray, byte[] byArray2, short s, int n2) {
        et_2 et_22 = et_2.Nr();
        et_22.fp = by;
        et_22.m_name = string;
        et_22.zu = by2;
        et_22.zt = by3;
        et_22.aRl = by4;
        et_22.zv = by5;
        et_22.aRm = byArray;
        et_22.aRn = byArray2;
        et_22.aRo = s;
        et_22.aRk = n2;
        return et_22;
    }

    public static et_2 a(byte[] byArray, boolean bl2) {
        try {
            et_2 et_22 = et_2.Nr();
            et_22.b(byArray, bl2);
            return et_22;
        }
        catch (Exception exception) {
            a.error((Object)"Erreur \u00e0 la d\u00e9serialisation d'un fighter", (Throwable)exception);
            return null;
        }
    }

    public static et_2 Nr() {
        et_2 et_22;
        try {
            et_22 = (et_2)aU.adr();
            et_22.uG = aU;
        }
        catch (Exception exception) {
            et_22 = new et_2();
            et_22.uG = null;
            et_22.b();
            a.error((Object)"Erreur lors d'un checkOut sur un FighterInformation : ", (Throwable)exception);
        }
        return et_22;
    }

    public void b() {
        this.fp = 0;
        this.aRk = 0;
        this.m_name = "";
        this.ey = (byte)-1;
        this.zv = 0;
        this.aRm = null;
        this.aRn = null;
        this.aRo = (short)600;
        this.aRv = 0;
        this.aRw = 0;
        this.aRx = 0;
        this.aRy = 0;
        this.aRz = 0;
        this.aRA = 0;
        this.aRB = 0;
        this.aRD.clear();
        this.uk.clear();
        this.aRF.clear();
        this.aRE.clear();
    }

    public void j() {
        this.fp = 0;
        this.aRk = 0;
        this.m_name = "";
        this.ey = (byte)-1;
        this.zv = 0;
        this.aRm = null;
        this.aRn = null;
        this.aRo = (short)600;
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"impossible de release l'objet");
            }
            this.uG = null;
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
            this.j();
        }
    }

    public boolean a(bs_1 bs_12, byte[] byArray, byte[] byArray2) {
        if (this.aIm != 2) {
            return true;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        while (byteBuffer.hasRemaining()) {
            int n2 = byteBuffer.getInt();
            if (this.aRE.contains(n2)) continue;
            return false;
        }
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byArray2);
        while (byteBuffer2.hasRemaining()) {
            byteBuffer2.getShort();
            int n3 = byteBuffer2.getInt();
            boolean bl2 = false;
            for (int j = 0; j < this.aRF.size(); ++j) {
                lb_0 lb_02 = bs_12.F(this.aRF.bu(j));
                if (!lb_02.bY(n3)) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            return false;
        }
        return true;
    }

    public boolean a(ajM ajM2, int n2) {
        if (!(ajM2.aus() <= this.aRv || this.aRD.contains(ajM2.getId()) && this.aRv >= ajM2.aus() / 10)) {
            return false;
        }
        if (!(this.aRD.contains(ajM2.getId()) || ajM2.auv().isEmpty() || ajM2.auv().contains(n2) || ajM2.auv().contains(-n2))) {
            return false;
        }
        if (ajM2.auv().isEmpty() && n2 != 0) {
            return false;
        }
        this.aRA = ajM2.aut();
        this.aRB = ajM2.auu();
        if (this.aRD.contains(ajM2.getId())) {
            this.aRv -= ajM2.aus() / 10;
        } else {
            this.aRv -= ajM2.aus();
            this.aRD.add(ajM2.getId());
            if (ajM2.el() > 0) {
                this.aRE.add(ajM2.el());
            }
            if (ajM2.azt() > 0) {
                this.aRF.add(ajM2.azt());
            }
        }
        return true;
    }

    public static byte a(byte by, long l2) {
        if (l2 == 0L) {
            return by;
        }
        double d = Math.max(0.0, Math.sqrt(by * 100 / nr_0.Pq) - Math.sqrt(Math.max(l2 - 1L, 0L)));
        return (byte)(d * d * (double)nr_0.Pq / 100.0);
    }

    public void c(byte by) {
        this.fp = by;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void b(byte by) {
        this.ey = by;
    }

    public void P(byte by) {
        this.zu = by;
    }

    public void Q(byte by) {
        this.zt = by;
    }

    public void R(byte by) {
        this.aRl = by;
    }

    public void S(byte by) {
        this.zv = by;
    }

    public byte cu() {
        return this.fp;
    }

    public String getName() {
        return this.m_name;
    }

    public byte cc() {
        return this.ey;
    }

    public byte lY() {
        return this.zu;
    }

    public byte lX() {
        return this.zt;
    }

    public byte Ns() {
        return this.aRl;
    }

    public byte lZ() {
        return this.zv;
    }

    public byte[] Nt() {
        return this.aRm;
    }

    public void E(byte[] byArray) {
        this.aRm = byArray;
    }

    public byte[] Nu() {
        return this.aRn;
    }

    public void F(byte[] byArray) {
        this.aRn = byArray;
    }

    public void av(short s) {
        this.aRo = s;
    }

    public int Nv() {
        return this.aRk;
    }

    public short Nw() {
        return this.aRo;
    }

    public byte getType() {
        return this.aIm;
    }

    public void setType(byte by) {
        this.aIm = by;
    }

    public int Nx() {
        return this.aRv;
    }

    public void ft(int n2) {
        if (n2 > 0 && this.aRv < 50000) {
            this.aRv += n2;
            this.aRw += n2;
        }
    }

    public void fu(int n2) {
        this.aRv -= n2;
    }

    public int Ny() {
        return this.aRw;
    }

    public void fv(int n2) {
        this.aRw = n2;
    }

    public byte Nz() {
        return this.aRx;
    }

    public void T(byte by) {
        this.aRx = (byte)Math.max(0, Math.min(by, nr_0.Pq));
    }

    public byte NA() {
        return this.aRy;
    }

    public void U(byte by) {
        this.aRy = (byte)Math.max(0, Math.min(by, nr_0.Pt));
    }

    public byte NB() {
        return this.aRz;
    }

    public void V(byte by) {
        this.aRz = by;
    }

    public short NC() {
        return this.aRA;
    }

    public void aw(short s) {
        this.aRA = s;
    }

    public short ND() {
        return this.aRB;
    }

    public void ax(short s) {
        this.aRB = s;
    }

    public jg_0 NE() {
        return this.aRD;
    }

    public vy_1 kh() {
        return this.uk;
    }

    public adl_0 NF() {
        return this.aRG;
    }

    public boolean NG() {
        return this.aRz == 0;
    }

    public int NH() {
        return this.aRC;
    }

    public void fw(int n2) {
        this.aRC = n2;
    }

    public jg_0 NI() {
        return this.aRE;
    }

    public void c(jg_0 jg_02) {
        this.aRE = jg_02;
    }

    public jg_0 NJ() {
        return this.aRF;
    }

    public void d(jg_0 jg_02) {
        this.aRF = jg_02;
    }

    public boolean NK() {
        return this.aIm == 2;
    }

    public int nj() {
        int n2;
        byte[] byArray = this.m_name.getBytes();
        int n3 = this.aRm != null ? this.aRm.length : 0;
        int n4 = n2 = this.aRn != null ? this.aRn.length : 0;
        int n5 = this.aIm == 2 ? 21 + (this.aRD != null ? 4 * this.aRD.size() : 0) + 2 + (this.aRE != null ? 4 * this.aRE.size() : 0) + 2 + (this.aRF != null ? 4 * this.aRF.size() : 0) + 1 + (this.uk != null ? 3 * this.uk.size() : 0) : 0;
        return 9 + (this.fp == xq.axE.lV() ? 4 : 0) + 1 + byArray.length + 2 + n3 + 2 + n2 + n5;
    }

    public byte[] cd() {
        int n2;
        byte[] byArray = this.m_name.getBytes();
        int n3 = this.aRm != null ? this.aRm.length : 0;
        int n4 = n2 = this.aRn != null ? this.aRn.length : 0;
        int n5 = this.aIm == 2 ? 21 + (this.aRD != null ? 4 * this.aRD.size() : 0) + 2 + (this.aRE != null ? 4 * this.aRE.size() : 0) + 2 + (this.aRF != null ? 4 * this.aRF.size() : 0) + 1 + (this.uk != null ? 3 * this.uk.size() : 0) : 0;
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + (this.fp == xq.axE.lV() ? 4 : 0) + 1 + byArray.length + 2 + n3 + 2 + n2 + n5);
        byteBuffer.put(this.aIm);
        byteBuffer.putShort(this.aRo);
        byteBuffer.put(this.fp);
        if (this.fp == xq.axE.lV()) {
            byteBuffer.putInt(this.aRk);
        }
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put(this.zv);
        byteBuffer.put(this.ey);
        byteBuffer.put(this.zu);
        byteBuffer.put(this.zt);
        byteBuffer.put(this.aRl);
        if (n3 > 0) {
            byteBuffer.putShort((short)n3);
            byteBuffer.put(this.aRm);
        } else {
            byteBuffer.putShort((short)0);
        }
        if (n2 > 0) {
            byteBuffer.putShort((short)n2);
            byteBuffer.put(this.aRn);
        } else {
            byteBuffer.putShort((short)0);
        }
        if (this.aIm == 2) {
            byteBuffer.putInt(this.aRC);
            byteBuffer.putInt(this.aRv);
            byteBuffer.putInt(this.aRw);
            byteBuffer.put(this.aRx);
            byteBuffer.put(this.aRy);
            byteBuffer.put(this.aRz);
            byteBuffer.putShort(this.aRA);
            byteBuffer.putShort(this.aRB);
            byteBuffer.putShort((short)this.aRD.size());
            int n6 = this.aRD.size();
            for (int j = 0; j < n6; ++j) {
                byteBuffer.putInt(this.aRD.bu(j));
            }
            byteBuffer.put((byte)this.uk.size());
            short[] sArray = this.uk.Gj();
            for (n6 = 0; n6 < sArray.length; ++n6) {
                byteBuffer.putShort(sArray[n6]);
                byteBuffer.put(this.uk.bp(sArray[n6]));
            }
            byteBuffer.putShort((short)this.aRE.size());
            int n7 = this.aRE.size();
            for (n6 = 0; n6 < n7; ++n6) {
                byteBuffer.putInt(this.aRE.bu(n6));
            }
            byteBuffer.putShort((short)this.aRF.size());
            n7 = this.aRF.size();
            for (n6 = 0; n6 < n7; ++n6) {
                byteBuffer.putInt(this.aRF.bu(n6));
            }
        }
        return byteBuffer.array();
    }

    public void b(byte[] byArray, boolean bl2) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aIm = byteBuffer.get();
        this.aRo = byteBuffer.getShort();
        this.fp = byteBuffer.get();
        if (this.fp == xq.axE.lV()) {
            this.aRk = byteBuffer.getInt();
        }
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.m_name = new String(byArray2);
        if (!(!bl2 || this.m_name.length() <= 16 && aet_0.dDK.matcher(this.m_name).matches() && avQ.jR(this.m_name))) {
            this.m_name = "Noob";
        }
        this.S(byteBuffer.get());
        if (this.zv != 0 && this.zv != 1) {
            this.zv = 0;
        }
        this.ey = byteBuffer.get();
        if (this.ey < 0) {
            this.zu = byteBuffer.get();
            this.zt = byteBuffer.get();
            this.aRl = byteBuffer.get();
        } else {
            this.zu = 1;
            this.zt = (byte)2;
            this.aRl = 1;
            this.ey = (byte)-1;
        }
        short s = byteBuffer.getShort();
        this.aRm = new byte[s];
        byteBuffer.get(this.aRm);
        s = byteBuffer.getShort();
        this.aRn = new byte[s];
        byteBuffer.get(this.aRn);
        if (this.aIm == 2) {
            try {
                int n2;
                this.aRC = byteBuffer.getInt();
                this.aRv = byteBuffer.getInt();
                this.aRw = byteBuffer.getInt();
                this.aRx = byteBuffer.get();
                this.aRy = byteBuffer.get();
                this.aRz = byteBuffer.get();
                this.aRA = byteBuffer.getShort();
                this.aRB = byteBuffer.getShort();
                int n3 = byteBuffer.getShort();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.aRD.add(byteBuffer.getInt());
                }
                n3 = byteBuffer.get();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.uk.b(byteBuffer.getShort(), byteBuffer.get());
                }
                n3 = byteBuffer.getShort();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.aRE.add(byteBuffer.getInt());
                }
                n3 = byteBuffer.getShort();
                for (n2 = 0; n2 < n3; ++n2) {
                    this.aRF.add(byteBuffer.getInt());
                }
            }
            catch (Exception exception) {
                this.aIm = 1;
                a.warn((Object)"Ancien fighter 2.28 qui aurait du merder d\u00e9tect\u00e9.");
            }
        }
    }

    public et_2 NL() {
        try {
            et_2 et_22 = et_2.Nr();
            et_22.fp = this.fp;
            et_22.m_name = new String(this.m_name);
            et_22.ey = this.ey;
            et_22.zv = this.zv;
            et_22.aRm = (byte[])this.aRm.clone();
            et_22.aRn = (byte[])this.aRn.clone();
            et_22.aRo = this.aRo;
            et_22.aRk = this.aRk;
            return et_22;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}

