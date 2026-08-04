/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from SW
 */
public class sw_1
implements cn_1 {
    public static final acl_0 aU = new ym_0(new bj_2());
    private boolean bMa = false;
    public static final short bMb = -2;
    public static final short bMc = -3;
    public static final short bMd = -4;
    public static final short bMe = -5;
    public static final short bMf = -6;
    public static final short bMg = -7;
    public static final short bMh = -10;
    public static final short bMi = -20;
    public static final short bMj = -21;
    public static final short bMk = -22;
    public static final short bMl = -23;
    public static final short bMm = 99;
    public static final short bMn = 9999;
    public static final short bMo = 10000;
    public static final short bMp = 100;
    public static final sw_1 bMq = null;
    public static final short bMr = -1;
    private static final String NO_NAME = "";
    private static final short bMs = 0;
    public static final short bMt = 0;
    public static final int bMu = 0;
    public static final int bMv = 0;
    public static final int bMw = 0;
    public static final long bMx = -1L;
    public static final String bMy = "evolution";
    public static final String bMz = "graveyard";
    public static final String bMA = "legend";
    private short Gp = (short)-6;
    private short fL = (short)-1;
    private String m_name = "";
    private byte bMB;
    private byte bMC;
    private byte bMD;
    private byte bME;
    private short fA = 0;
    private short bMF = 0;
    private int bMG = 0;
    private int bMH = 0;
    private int bMI = 0;
    private aba_0 bMJ = new aba_0();
    private qa_2 bMK = new qa_2();
    protected static Logger a = Logger.getLogger(sw_1.class);

    public static sw_1 afp() {
        sw_1 sw_12;
        try {
            sw_12 = (sw_1)aU.adr();
            sw_12.bMa = true;
        }
        catch (Exception exception) {
            sw_12 = new sw_1();
            sw_12.b();
            a.error((Object)("Erreur lors du checkOut d'un " + sw_12.getClass().toString()), (Throwable)exception);
        }
        return sw_12;
    }

    public void release() {
        if (this.bMa) {
            try {
                aU.af(this);
            }
            catch (Exception exception) {
                a.error((Object)("Exception dans le release de " + this.getClass().toString() + " (normalement impossible...)"));
            }
        } else {
            this.j();
        }
    }

    public void b() {
    }

    public void j() {
        this.fL = 0;
        this.m_name = null;
        this.Gp = (short)-6;
        this.bMF = 0;
        this.fA = 0;
        this.bMG = 0;
        this.bMH = 0;
        this.bMI = 0;
        this.bMK.clear();
        this.bMJ.clear();
    }

    public void afq() {
        this.fL = 0;
        this.m_name = null;
        this.Gp = (short)-6;
        this.bMF = 0;
        this.fA = 0;
        this.bMG = 0;
        this.bMH = 0;
        this.bMI = 0;
        this.bMK = new qa_2();
        this.bMJ = new aba_0();
    }

    public sw_1 bd(short s) {
        sw_1 sw_12 = sw_1.afp();
        for (long l2 : this.bMK.adg()) {
            sw_12.bMK.ct(l2);
        }
        for (long l2 : this.bMJ.eJ()) {
            sw_12.bMJ.l(l2, this.afE().du(l2));
        }
        sw_12.fA = this.fA;
        sw_12.bMG = this.bMG;
        sw_12.bMH = this.bMH;
        sw_12.bMI = this.bMI;
        sw_12.Gp = this.Gp;
        sw_12.fL = s;
        sw_12.m_name = this.m_name;
        sw_12.bMF = this.bMF;
        return sw_12;
    }

    public static sw_1 cC(long l2) {
        sw_1 sw_12 = sw_1.afp();
        sw_12.bk((short)99);
        sw_12.M((short)1);
        sw_12.setName(bMy);
        sw_12.setType((short)-4);
        sw_12.cG(l2);
        return sw_12;
    }

    public static sw_1 cD(long l2) {
        sw_1 sw_12 = sw_1.afp();
        sw_12.bk((short)10000);
        sw_12.M((short)1);
        sw_12.setName(bMz);
        sw_12.setType((short)-4);
        sw_12.cG(l2);
        return sw_12;
    }

    public static sw_1 cE(long l2) {
        sw_1 sw_12 = sw_1.afp();
        sw_12.bk((short)9999);
        sw_12.M((short)1);
        sw_12.setName(bMA);
        sw_12.setType((short)-4);
        sw_12.cG(l2);
        return sw_12;
    }

    public static boolean be(short s) {
        return s == 99;
    }

    public static boolean bf(short s) {
        return s == 10000;
    }

    public static boolean bg(short s) {
        return s == 9999;
    }

    public static boolean bh(short s) {
        return sw_1.be(s) || sw_1.bf(s) || sw_1.bg(s);
    }

    public static boolean bi(short s) {
        return s != -1 && !sw_1.bh(s);
    }

    public boolean afr() {
        return sw_1.be(this.fL);
    }

    public boolean afs() {
        return sw_1.bf(this.fL);
    }

    public boolean aft() {
        return sw_1.bg(this.fL);
    }

    public boolean afu() {
        return sw_1.bh(this.fL);
    }

    public boolean afv() {
        return sw_1.bi(this.fL);
    }

    public short tI() {
        return this.fL;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public byte afw() {
        return this.bMB;
    }

    public void ah(byte by) {
        this.bMB = by;
    }

    public byte afx() {
        return this.bMC;
    }

    public void ai(byte by) {
        this.bMC = by;
    }

    public byte afy() {
        return this.bMD;
    }

    public void aj(byte by) {
        this.bMD = by;
    }

    public byte afz() {
        return this.bME;
    }

    public void ak(byte by) {
        this.bME = by;
    }

    public short afA() {
        return this.bMF;
    }

    public void bj(short s) {
        this.bMF = s;
    }

    public short cB() {
        return this.fA;
    }

    public int afB() {
        return this.bMG;
    }

    public int afC() {
        return this.bMH;
    }

    public int afD() {
        return this.bMI;
    }

    public void M(short s) {
        this.fA = s;
    }

    public void hM(int n2) {
        this.bMG = n2;
    }

    public void hN(int n2) {
        this.bMH = n2;
    }

    public void hO(int n2) {
        this.bMI = n2;
    }

    public aba_0 afE() {
        return this.bMJ;
    }

    public qa_2 cF(long l2) {
        qa_2 qa_22 = new qa_2();
        for (long l3 : this.bMJ.eJ()) {
            if (this.bMJ.du(l3) != l2 && this.bMJ.du(l3) != -1L) continue;
            qa_22.ct(l3);
        }
        return qa_22;
    }

    public void bk(short s) {
        this.fL = s;
    }

    public short getType() {
        return this.Gp;
    }

    public void setType(short s) {
        this.Gp = s;
    }

    public void cG(long l2) {
        if (!this.bMK.m(l2)) {
            this.bMK.ct(l2);
        }
    }

    public qa_2 afF() {
        return this.bMK;
    }

    public long afG() {
        if (!this.bMK.isEmpty()) {
            return this.bMK.get(0);
        }
        return -1L;
    }

    public boolean isEmpty() {
        return this.bMJ.isEmpty();
    }

    public boolean afH() {
        boolean bl2 = true;
        if (this.bMK.size() > 1) {
            for (int j = 0; j < this.bMK.size(); ++j) {
                bl2 &= !this.cF(this.bMK.hn(j)).isEmpty();
            }
        }
        return bl2;
    }

    public boolean afI() {
        return !this.bMJ.isEmpty();
    }

    public int size() {
        return this.bMJ.size();
    }

    public int afJ() {
        return this.bMJ.size();
    }

    public void clear() {
        this.bMJ.clear();
        this.bMK.clear();
    }

    public void j(long l2, long l3) {
        if (l2 == -1L) {
            a.error((Object)("Selection d'un combattant de l'\u00e9quipe d'id " + this.tI() + " de mode de jeu d'id " + this.cB() + " des coachs d'id " + this.afF() + " impossible : FighterInformationId \u00e9gal \u00e0 " + l2 + "."), (Throwable)new UnsupportedOperationException());
        } else {
            this.bMJ.l(l2, l3);
        }
    }

    public void cH(long l2) {
        this.bMJ.dv(l2);
    }

    public void l(long l2) {
        this.bMJ.dv(l2);
    }

    public void removeAll() {
        this.bMJ.clear();
    }

    public boolean m(long l2) {
        return this.bMJ.v(l2);
    }

    public boolean cI(long l2) {
        return this.bMJ.m(l2);
    }

    public boolean afK() {
        return this.bMK.size() <= 1;
    }

    public boolean afL() {
        return this.bMK.size() == 2;
    }

    public boolean afM() {
        if (this.afK()) {
            return true;
        }
        for (int j = 0; j < this.bMK.size(); ++j) {
            if (this.bMJ.dw(this.bMK.hn(j))) continue;
            return false;
        }
        return true;
    }

    public int nj() {
        byte[] byArray = aey_0.hH(this.m_name);
        return 7 + byArray.length + 1 + this.bMJ.size() * 16 + 1 + this.bMK.size() * 8;
    }

    public byte[] cd() {
        byte[] byArray = aey_0.hH(this.m_name);
        int n2 = 7 + byArray.length + 1 + this.bMJ.size() * 16 + 1 + this.bMK.size() * 8;
        if (this.Gp == -6 || this.Gp == -7 || this.Gp == -5) {
            n2 += 4;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort(this.Gp);
        byteBuffer.putShort(this.fL);
        byteBuffer.putShort(this.fA);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        if (this.Gp == -6 || this.Gp == -7 || this.Gp == -5) {
            byteBuffer.put(this.bMB);
            byteBuffer.put(this.bMC);
            byteBuffer.put(this.bMD);
            byteBuffer.put(this.bME);
        }
        byteBuffer.put((byte)this.bMJ.size());
        this.bMJ.a(new bl_2(this, byteBuffer));
        byteBuffer.put((byte)this.bMK.size());
        this.bMK.b(new bh_2(this, byteBuffer));
        return byteBuffer.array();
    }

    public void b(byte[] byArray) {
        this.b(ByteBuffer.wrap(byArray));
    }

    public boolean b(ByteBuffer byteBuffer) {
        try {
            int n2;
            if (!byteBuffer.hasRemaining()) {
                a.error((Object)("D\u00e9s\u00e9rialisation incompl\u00e8te d'un objet de type " + this.getClass()));
                return false;
            }
            this.Gp = byteBuffer.getShort();
            this.fL = byteBuffer.getShort();
            this.fA = byteBuffer.getShort();
            byte by = byteBuffer.get();
            if (by < 0) {
                a.error((Object)("D\u00e9s\u00e9rialisation incompl\u00e8te d'un objet de type " + this.getClass() + " : Taille de nom d'\u00e9quipe \u00e9gale \u00e0 " + by));
                return false;
            }
            byte[] byArray = new byte[by];
            byteBuffer.get(byArray);
            this.m_name = new String(byArray);
            if (this.Gp == -6 || this.Gp == -7 || this.Gp == -5) {
                this.bMB = byteBuffer.get();
                this.bMC = byteBuffer.get();
                this.bMD = byteBuffer.get();
                this.bME = byteBuffer.get();
            } else {
                this.bMB = 1;
                this.bMC = 1;
                this.bMD = 1;
                this.bME = 1;
                if (this.Gp == -3) {
                    this.Gp = (short)-6;
                } else if (this.Gp == -2) {
                    this.Gp = (short)-7;
                }
            }
            int n3 = byteBuffer.get();
            for (n2 = 0; n2 < n3; ++n2) {
                long l2 = byteBuffer.getLong();
                if (l2 == -1L) continue;
                this.j(l2, byteBuffer.getLong());
            }
            n2 = byteBuffer.get();
            for (int j = 0; j < n2; ++j) {
                this.bMK.ct(byteBuffer.getLong());
            }
            return true;
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un objet de type " + this.getClass() + " : " + exception));
            return false;
        }
    }

    public String toString() {
        return this.getName();
    }
}

