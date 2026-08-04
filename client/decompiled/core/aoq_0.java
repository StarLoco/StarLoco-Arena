/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from aoQ
 */
public class aoq_0
extends aja_1 {
    protected short[] cLr;
    protected int cLs;
    protected int cLt;
    protected int bJn;
    protected int bJo;
    protected short cLu;
    protected boolean cLv;
    protected short cLw;
    protected short cLx;
    protected short ayK;
    protected short bud;
    private final qg_0 cLy = new qg_0();
    private final qg_0 cLz = new qg_0();
    private final tl_2[] cLA = new tl_2[63];
    private boolean cLB;
    private byte[] cLC;
    private final pi_1 cLD = new pi_1();
    protected byte cLE = 0;
    protected byte cLF = 0;
    afj_0 cLG;
    public static final byte cLH = 62;
    protected static final int cLI = 64512;
    protected static final int cLJ = 512;
    protected static final int cLK = 256;
    protected static final int cLL = 128;
    protected static final int cLM = 63;
    protected static final int cLN = 10;
    protected static final int cLO = 0;
    protected static final int cLP = 65279;
    protected static final boolean cLQ = false;
    private static final Logger bGT = Logger.getLogger((String)"debug");
    protected static final akd_0[] cLR = new akd_0[32];
    private static final Logger a = Logger.getLogger(aoq_0.class);

    public void bC(int n2, int n3) {
        int n4;
        assert (n2 >= this.EN && n2 < this.EN + this.fb && n3 >= this.EO && n3 < this.EO + this.fc) : "Coords must be in the fightMap, you can call isInMap to do make sure that's the case";
        int n5 = n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        this.cLr[n5] = (short)(this.cLr[n5] | 0x200);
    }

    public boolean bD(int n2, int n3) {
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        if (n4 < 0 || n4 >= this.cLs) {
            a.info((Object)"trying to get information in a fightmap on a out of bounds cell");
            return true;
        }
        short s = this.cLr[n4];
        if ((s & 0x300) != 0) {
            return true;
        }
        if ((s & 0xFC00) == 64512) {
            return false;
        }
        int n5 = (s & 0xFC00) >>> 10;
        assert (this.aL((byte)n5)) : "obstacleId is out of bounds : " + n5;
        if (this.cLA[n5] == null) {
            a.error((Object)("Obstacle not found. " + n5));
            return false;
        }
        boolean bl2 = this.cLA[n5].PA();
        if (bl2 && this.cLy.contains((byte)n5)) {
            return false;
        }
        return bl2;
    }

    public boolean bE(int n2, int n3) {
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        if (n4 < 0 || n4 >= this.cLs) {
            a.info((Object)"trying to get information in a fightmap on a out of bounds cell");
            return true;
        }
        short s = this.cLr[n4];
        if ((s & 0x100) == 256) {
            return true;
        }
        if ((s & 0xFC00) == 64512) {
            return false;
        }
        int n5 = (s & 0xFC00) >>> 10;
        assert (this.aL((byte)n5)) : "obstacleId is out of bounds : " + n5;
        assert (this.cLA[n5] != null) : "No obstacle found for the given id";
        if (this.cLA[n5] == null) {
            a.error((Object)"Obstacle null", (Throwable)new Exception());
            return false;
        }
        boolean bl2 = this.cLA[n5].PB();
        if (bl2 && this.cLz.contains((byte)n5)) {
            return false;
        }
        return bl2;
    }

    public short[] aCT() {
        return this.cLr;
    }

    public int aCU() {
        return this.cLs;
    }

    public int aCV() {
        return this.cLt;
    }

    public byte aCW() {
        return this.cLE;
    }

    public byte aCX() {
        return this.cLF;
    }

    public boolean aCY() {
        return this.cLB;
    }

    public byte[] aCZ() {
        return this.cLC;
    }

    public boolean a(agf_2 agf_22, int n2, int n3, short s, int n4, int n5, short s2) {
        if (agf_22 == null) {
            return true;
        }
        Iterable iterable = agf_22.a(n2, n3, s, n4, n5, s2);
        if (iterable == null) {
            return true;
        }
        for (int[] nArray : iterable) {
            if (this.bH(nArray[0], nArray[1])) continue;
            return false;
        }
        return true;
    }

    public boolean bF(int n2, int n3) {
        if (!this.F(n2, n3)) {
            return false;
        }
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        return this.cLr[n4] != -257 && (this.cLr[n4] & 0x80) != 0;
    }

    public boolean bG(int n2, int n3) {
        if (!this.F(n2, n3)) {
            return false;
        }
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        return (this.cLr[n4] & 0x80) == 0;
    }

    public boolean bH(int n2, int n3) {
        if (!this.F(n2, n3)) {
            return false;
        }
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        return (this.cLr[n4] & 0xFFFFFEFF) != -257;
    }

    public tl_2[] aDa() {
        return this.cLA;
    }

    public tl_2 aK(byte by) {
        if (!this.aL(by)) {
            return null;
        }
        return this.cLA[by];
    }

    public boolean bI(int n2, int n3) {
        if (!this.F(n2, n3)) {
            return false;
        }
        return this.bH(n2, n3) || this.bG(n2 - 1, n3) || this.bG(n2, n3 - 1) || this.bG(n2 + 1, n3) || this.bG(n2, n3 + 1);
    }

    public byte bJ(int n2, int n3) {
        if (n2 < this.EN || n2 >= this.EN + this.fb || n3 < this.EO || n3 >= this.EO + this.fc) {
            return -1;
        }
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        int n5 = this.cLr[n4] & 0xFC00;
        if (n5 == 64512) {
            return -1;
        }
        byte by = (byte)(n5 >>> 10);
        if (by < 0 || by > 62) {
            return -1;
        }
        return by;
    }

    public tl_2 bK(int n2, int n3) {
        byte by = this.bJ(n2, n3);
        if (by < 0) {
            return null;
        }
        return this.cLA[by];
    }

    public void a(tl_2 tl_22) {
        assert (tl_22 != null) : "can't work on a null obstacle";
        this.cLy.x(tl_22.Py());
    }

    public void aDb() {
        this.cLy.clear();
    }

    public void b(tl_2 tl_22) {
        assert (tl_22 != null) : "can't work on a null obstacle";
        this.cLz.x(tl_22.Py());
    }

    public void aDc() {
        this.cLz.clear();
    }

    public void c(tl_2 tl_22) {
        if (tl_22 == null) {
            return;
        }
        if (!tl_22.Pz()) {
            return;
        }
        this.g(tl_22);
        if (this.F(tl_22.gn(), tl_22.go())) {
            this.b(tl_22, tl_22.gn(), tl_22.go());
        }
    }

    public void d(tl_2 tl_22) {
        if (tl_22 == null) {
            return;
        }
        if (!tl_22.Pz()) {
            return;
        }
        if (!this.aL(tl_22.Py())) {
            return;
        }
        if (this.cLA[tl_22.Py()] == tl_22) {
            this.cLA[tl_22.Py()] = null;
            if (this.F(tl_22.gn(), tl_22.go())) {
                this.e(tl_22);
            }
        }
        tl_22.Y((byte)-1);
    }

    public void a(tl_2 tl_22, boolean bl2) {
        int n2;
        if (tl_22 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/common/clientAndServer/game/fight/FightMap.modifyObstacle must not be null");
        }
        assert (tl_22.Pz());
        assert (this.cLA[tl_22.Py()] == tl_22) : "This obstacle must have been added with addObstacle";
        int n3 = tl_22.gn();
        if (!this.F(n3, n2 = tl_22.go())) {
            return;
        }
        if (bl2) {
            this.b(tl_22, n3, n2);
        } else {
            this.e(tl_22);
        }
    }

    public void a(tl_2 tl_22, int n2, int n3) {
        assert (tl_22 != null) : "can't work on a null obstacle";
        if (!tl_22.Pz()) {
            return;
        }
        if (this.F(tl_22.gn(), tl_22.go())) {
            this.e(tl_22);
        }
        if (this.F(n2, n3)) {
            this.b(tl_22, n2, n3);
        }
    }

    private void b(tl_2 tl_22, int n2, int n3) {
        if (tl_22 == null) {
            a.error((Object)"On passe un obstacle null");
            return;
        }
        if (!this.aL(tl_22.Py())) {
            a.error((Object)(" l'id de l'obstacle est invalide : " + tl_22.Py() + " : " + tl_22));
            return;
        }
        byte by = tl_22.ox();
        byte by2 = tl_22.Py();
        if (this.cLA[by2] == null) {
            this.cLA[by2] = tl_22;
        } else if (this.cLA[by2] != tl_22) {
            a.error((Object)"ATTENTION !!! On veut placer un obstacle dans la FightMap mais il existe deja un obstacle avec le meme ID");
            return;
        }
        if (by <= 0) {
            int n4;
            assert (n2 >= this.EN && n2 < this.EN + this.fb && n3 >= this.EO && n3 < this.EO + this.fc) : "Coords must be in the fightMap, you can call isInMap to do make sure that's the case";
            int n5 = n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
            this.cLr[n5] = (short)(this.cLr[n5] & 0xFFFF03FF);
            int n6 = n4;
            this.cLr[n6] = (short)(this.cLr[n6] | tl_22.Py() << 10);
        } else {
            for (int j = n2 - by; j <= n2 + by; ++j) {
                for (int i2 = n3 - by; i2 <= n3 + by; ++i2) {
                    int n7;
                    if (!this.F(j, i2)) continue;
                    int n8 = n7 = (i2 - this.EO) * this.fb + (j - this.EN);
                    this.cLr[n8] = (short)(this.cLr[n8] & 0xFFFF03FF);
                    int n9 = n7;
                    this.cLr[n9] = (short)(this.cLr[n9] | tl_22.Py() << 10);
                }
            }
        }
    }

    private void e(tl_2 tl_22) {
        if (tl_22 == null) {
            return;
        }
        int n2 = tl_22.gn();
        int n3 = tl_22.go();
        int n4 = tl_22.Py() << 10;
        byte by = tl_22.ox();
        if (by <= 0) {
            assert (n2 >= this.EN && n2 < this.EN + this.fb && n3 >= this.EO && n3 < this.EO + this.fc) : "Coords must be in the fightMap, you can call isInMap to do make sure that's the case";
            int n5 = (n3 - this.EO) * this.fb + (n2 - this.EN);
            if ((this.cLr[n5] & 0xFC00) != n4) {
                return;
            }
            int n6 = n5;
            this.cLr[n6] = (short)(this.cLr[n6] | 0xFC00);
        } else {
            for (int j = n2 - by; j <= n2 + by; ++j) {
                for (int i2 = n3 - by; i2 <= n3 + by; ++i2) {
                    if (!this.F(j, i2)) continue;
                    int n7 = (i2 - this.EO) * this.fb + (j - this.EN);
                    if ((this.cLr[n7] & 0xFC00) != n4) {
                        return;
                    }
                    int n8 = n7;
                    this.cLr[n8] = (short)(this.cLr[n8] | 0xFC00);
                }
            }
        }
    }

    public void f(tl_2 tl_22) {
        if (tl_22 == null) {
            return;
        }
        byte by = tl_22.Py();
        if (this.cLA[by] != tl_22) {
            this.e(this.cLA[by]);
            this.b(tl_22, tl_22.gn(), tl_22.go());
        }
        this.cLA[by] = tl_22;
    }

    private byte g(tl_2 tl_22) {
        assert (tl_22 != null);
        byte by = this.cLA.length;
        for (byte by2 = 0; by2 < by; by2 = (byte)((byte)(by2 + 1))) {
            if (this.cLA[by2] != null) continue;
            this.cLA[by2] = tl_22;
            tl_22.Y(by2);
            return by2;
        }
        return -1;
    }

    public void f(boolean bl2, boolean bl3) {
        for (int j = this.EN; j < this.EN + this.fb; ++j) {
            for (int i2 = this.EO; i2 < this.EO + this.fc; ++i2) {
                dc_0 dc_02;
                if (!this.bH(j, i2) || (dc_02 = auU.c(this.ayK, j, i2, bl3 ? this.bud : (short)0)) == null) continue;
                dc_02.c(j, i2, bl2);
            }
        }
    }

    public boolean aL(byte by) {
        return by >= 0 && by <= 62;
    }

    public short bL(int n2, int n3) {
        assert (n2 >= this.EN && n2 < this.EN + this.fb && n3 >= this.EO && n3 < this.EO + this.fc) : "Coords must be in the fightMap, you can call isInMap to do make sure that's the case";
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        int n5 = (this.cLr[n4] & 0x3F) >>> 0;
        int n6 = this.ari.size();
        for (int j = 0; j < n6; ++j) {
            dc_0 dc_02 = (dc_0)this.ari.get(j);
            acm_1 acm_12 = dc_02.Ls();
            if (!acm_12.F(n2, n3)) continue;
            int n7 = acm_12.a(n2, n3, cLR, 0);
            if (n5 >= n7) {
                return Short.MIN_VALUE;
            }
            return aoq_0.cLR[n5].wp;
        }
        return Short.MIN_VALUE;
    }

    public short aDd() {
        return this.cLw;
    }

    public short aDe() {
        return this.cLx;
    }

    public boolean aDf() {
        return this.cLv;
    }

    public short Em() {
        return this.ayK;
    }

    public void ai(short s) {
        this.ayK = s;
    }

    public short YF() {
        return this.bud;
    }

    public void aI(short s) {
        this.bud = s;
    }

    public void aM(byte by) {
        this.cLE = by;
    }

    public void aN(byte by) {
        this.cLF = by;
    }

    public void bP(short s) {
        this.cLx = s;
    }

    public void bQ(short s) {
        this.cLw = s;
    }

    public void Z(byte[] byArray) {
        this.cLC = byArray;
    }

    public void dM(boolean bl2) {
        this.cLB = bl2;
    }

    public void lF(int n2) {
        this.EN = n2;
    }

    public void lG(int n2) {
        this.EO = n2;
    }

    public void lH(int n2) {
        this.cLs = n2;
    }

    public void j(short[] sArray) {
        this.cLr = sArray;
    }

    public void setWidth(int n2) {
        this.fb = n2;
    }

    public void setHeight(int n2) {
        this.fc = n2;
    }

    public qa_2 u(byte by) {
        return this.cLD.u(by);
    }

    public void aDg() {
        for (int j = this.EN; j < this.EP; ++j) {
            for (int i2 = this.EO; i2 < this.EQ; ++i2) {
                byte by = this.bM(j, i2);
                if (by == -1) continue;
                this.cLD.a(by, j, i2);
            }
        }
    }

    public void a(ry[] ryArray, byte by) {
        int n2;
        int n3;
        assert (by != -1) : "Id can't be equal to -1 since this value is used when the cell is not a valid position";
        assert (this.cLs != 0) : "The fight map must be created before custom teams are added";
        assert (ryArray.length > 0) : "You can't add custom team with no start position";
        if (!this.cLB) {
            this.cLB = true;
            this.cLC = new byte[this.cLs];
            this.cLG = new afj_0();
            n3 = -1;
            for (n2 = 0; n2 < this.cLC.length; ++n2) {
                this.cLC[n2] = -1;
            }
        }
        n3 = 0;
        n2 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = ryArray.length;
        for (int j = 0; j < n8; ++j) {
            ry ry2 = ryArray[j];
            if (ry2 == null) continue;
            int n9 = ry2.getX();
            int n10 = ry2.getY();
            assert (this.bG(n9, n10)) : "The start position (" + n9 + "; " + n10 + ") is not in the fightMap";
            if (j == 0) {
                n3 = n9;
                n2 = n10;
                n4 = n9;
                n5 = n10;
                n6 = n9;
                n7 = n10;
            } else {
                if (n9 < n4) {
                    n4 = n9;
                }
                if (n10 < n5) {
                    n5 = n10;
                }
                if (n9 > n6) {
                    n6 = n9;
                }
                if (n10 > n7) {
                    n7 = n10;
                }
                int n11 = n6 - (n6 - n4) / 2;
                int n12 = n7 - (n7 - n5) / 2;
                if (Math.abs(n9 - n11) + Math.abs(n10 - n12) < Math.abs(n3 - n11) + Math.abs(n2 - n12)) {
                    n3 = n9;
                    n2 = n10;
                }
            }
            this.cLC[(n10 - this.EO) * this.fb + (n9 - this.EN)] = by;
            this.cLD.a(by, n9, n10);
        }
        this.cLG.b(by, new int[]{n3, n2});
    }

    public byte bM(int n2, int n3) {
        if (!this.bG(n2, n3)) {
            return -1;
        }
        if (this.cLB) {
            return this.cLC[(n3 - this.EO) * this.fb + (n2 - this.EN)];
        }
        if (this.fb >= this.fc) {
            int n4 = this.fb / 2;
            return n2 < this.EN + n4 ? this.cLE : (byte)(1 - this.cLE);
        }
        int n5 = this.fc / 2;
        return n3 < this.EO + n5 ? this.cLF : (byte)(1 - this.cLF);
    }

    public byte d(ry ry2, ry ry3) {
        if (this.fb >= this.fc) {
            return ry2.getX() <= ry3.getX() ? (byte)0 : 1;
        }
        return ry2.getY() <= ry3.getY() ? (byte)0 : 1;
    }

    public ry aDh() {
        return new ry(this.fb / 2 + this.EN, this.fc / 2 + this.EO, 0);
    }

    public final akd_0 F(int n2, int n3, short s) {
        if (!this.F(n2, n3)) {
            return null;
        }
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        int n5 = (this.cLr[n4] & 0x3F) >>> 0;
        int n6 = this.ari.size();
        for (int j = 0; j < n6; ++j) {
            dc_0 dc_02 = (dc_0)this.ari.get(j);
            acm_1 acm_12 = dc_02.Ls();
            if (!acm_12.F(n2, n3)) continue;
            int n7 = acm_12.a(n2, n3, cLR, 0);
            if (n5 >= n7) {
                return null;
            }
            if (s != aoq_0.cLR[n5].wp) {
                return null;
            }
            return cLR[n5];
        }
        return null;
    }

    public final boolean G(int n2, int n3, short s) {
        akd_0 akd_02 = this.F(n2, n3, s);
        if (akd_02 == null) {
            return false;
        }
        return akd_02.cCJ != -1;
    }

    public final boolean ak(int n2, int n3) {
        int n4 = (n3 - this.EO) * this.fb + (n2 - this.EN);
        if (n4 < 0 || n4 >= this.cLs) {
            return false;
        }
        short s = this.cLr[n4];
        return (s & 0x300) != 0;
    }

    public short bN(int n2, int n3) {
        int n4 = this.EN + this.fb - n2 > n2 - this.EN ? this.EN + this.fb - n2 : n2 - this.EN;
        int n5 = this.EO + this.fc - n2 > n3 - this.EO ? this.EO + this.fc - n3 : n3 - this.EO;
        return (short)(n4 > n5 ? n4 : n5);
    }

    public qc_0 aO(byte by) {
        if (this.fb >= this.fc) {
            return by == 0 ? qc_0.bEK : qc_0.bEO;
        }
        return by == 0 ? qc_0.bEM : qc_0.bEQ;
    }

    public void clear() {
        this.cLs = 0;
        this.ari.clear();
        this.cLC = null;
    }

    public void aDi() {
        boolean bl2;
        boolean bl3 = bl2 = this.bud == 0;
        if (!auU.aHK() && !bl2) {
            int n2 = hy_2.aO(this.EN);
            int n3 = hy_2.aP(this.EO);
            int n4 = (int)Math.ceil((float)(this.EN + this.fb) / 18.0f);
            int n5 = (int)Math.ceil((float)(this.EO + this.fc) / 18.0f);
            for (int j = n3; j < n5; ++j) {
                for (int i2 = n2; i2 < n4; ++i2) {
                    auU.d(this.ayK, (short)i2, (short)j, this.bud);
                }
            }
        }
    }

    public int getDataSize() {
        int n2 = 0;
        n2 += 4;
        n2 += 16;
        n2 += 4;
        n2 += 2;
        n2 += this.cLs * 2;
        ++n2;
        if (this.cLB) {
            n2 += this.cLC.length;
        }
        return n2 += 2;
    }

    public byte[] aDj() {
        int n2 = this.getDataSize();
        byte[] byArray = new byte[n2];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byteBuffer.putShort(this.ayK);
        byteBuffer.putShort(this.bud);
        byteBuffer.putInt(this.EN);
        byteBuffer.putInt(this.EO);
        byteBuffer.putInt(this.fb);
        byteBuffer.putInt(this.fc);
        byteBuffer.putShort(this.cLw);
        byteBuffer.putShort(this.cLx);
        byteBuffer.putShort((short)this.cLs);
        for (int j = 0; j < this.cLs; ++j) {
            byteBuffer.putShort((short)(this.cLr[j] | 0xFC00));
        }
        byteBuffer.put(this.cLB ? (byte)1 : 0);
        if (this.cLB) {
            byteBuffer.put(this.cLC);
        }
        byteBuffer.put(this.cLE);
        byteBuffer.put(this.cLF);
        assert (0 == byteBuffer.remaining()) : "Buffer is not full";
        return byArray;
    }

    public void f(ByteBuffer byteBuffer) {
        int n2;
        int n3 = byteBuffer.position();
        this.ayK = byteBuffer.getShort();
        this.bud = byteBuffer.getShort();
        this.EN = byteBuffer.getInt();
        this.EO = byteBuffer.getInt();
        this.fb = byteBuffer.getInt();
        this.fc = byteBuffer.getInt();
        this.cLw = byteBuffer.getShort();
        this.cLx = byteBuffer.getShort();
        this.cLs = byteBuffer.getShort();
        assert (byteBuffer.remaining() > this.cLs * 2);
        this.cLr = new short[this.cLs];
        for (n2 = 0; n2 < this.cLs; ++n2) {
            this.cLr[n2] = byteBuffer.getShort();
        }
        boolean bl2 = this.cLB = byteBuffer.get() == 1;
        if (this.cLB) {
            this.cLC = new byte[this.cLs];
            byteBuffer.get(this.cLC);
        }
        n2 = hy_2.aO(this.EN);
        int n4 = hy_2.aP(this.EO);
        int n5 = (int)Math.ceil((float)(this.EN + this.fb) / 18.0f);
        int n6 = (int)Math.ceil((float)(this.EO + this.fc) / 18.0f);
        if (auU.aHK()) {
            for (int j = n4; j < n6; ++j) {
                for (int i2 = n2; i2 < n5; ++i2) {
                    dc_0 dc_02 = auU.x((short)i2, (short)j);
                    if (dc_02 == null) continue;
                    this.ari.add(dc_02);
                }
            }
        } else {
            for (int j = n4; j < n6; ++j) {
                for (int i3 = n2; i3 < n5; ++i3) {
                    try {
                        auU.d(this.ayK, (short)i3, (short)j);
                        dc_0 dc_03 = auU.b(this.ayK, (short)i3, (short)j, this.bud);
                        if (dc_03 == null) continue;
                        this.ari.add(dc_03);
                        continue;
                    }
                    catch (IOException iOException) {
                        a.error((Object)("Unable to load map (" + (short)i3 + "; " + (short)j + ")"));
                    }
                }
            }
        }
        this.cLE = byteBuffer.get();
        this.cLF = byteBuffer.get();
        assert (byteBuffer.position() - n3 == this.getDataSize()) : "Unserialized data don't have the right size";
    }

    public boolean a(aOf aOf2, ry ry2) {
        int n2;
        int n3 = ry2.getX();
        if (!this.F(n3, n2 = ry2.getY())) {
            return false;
        }
        byte by = aOf2.ox();
        if (by == 0) {
            if (aOf2 instanceof tl_2) {
                this.a((tl_2)((Object)aOf2));
            }
            boolean bl2 = this.bD(n3, n2);
            if (aOf2 instanceof tl_2) {
                this.aDb();
            }
            return !bl2;
        }
        if (aOf2 instanceof tl_2) {
            this.a((tl_2)((Object)aOf2));
        }
        for (int j = n3 - by; j <= n3 + by; ++j) {
            for (int i2 = n2 - by; i2 <= n2 + by; ++i2) {
                if (this.F(n3, n2) && !this.bD(j, i2)) continue;
                if (aOf2 instanceof tl_2) {
                    this.aDb();
                }
                return false;
            }
        }
        if (aOf2 instanceof tl_2) {
            this.a((tl_2)((Object)aOf2));
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("boundingBox={(");
        stringBuilder.append(this.EN).append(", ").append(this.EO).append(") => (");
        stringBuilder.append(this.EP).append(", ").append(this.EQ).append(")");
        stringBuilder.append("}, numCells=");
        stringBuilder.append(this.cLs);
        stringBuilder.append(", width=").append(this.fb).append(", height=").append(this.fc);
        stringBuilder.append(", center=(").append(this.bJn).append(", ").append(this.bJo).append(", ").append(this.cLu).append(")");
        return stringBuilder.toString();
    }

    protected int bO(int n2, int n3) {
        int n4 = this.ari.size();
        for (int j = 0; j < n4; ++j) {
            acm_1 acm_12 = ((dc_0)this.ari.get(j)).Ls();
            int n5 = acm_12.aG;
            int n6 = acm_12.aH;
            if (n2 < n5 || n2 >= n5 + 18 || n3 < n6 || n3 >= n6 + 18) continue;
            return j;
        }
        return -1;
    }

    public void a(dc_0 dc_02) {
        this.ari.add(dc_02);
    }

    static {
        for (int j = 0; j < cLR.length; ++j) {
            aoq_0.cLR[j] = new akd_0();
        }
    }
}

