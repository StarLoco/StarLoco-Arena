/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.AL
 *  org.apache.log4j.Logger
 */
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.java.games.joal.AL;
import org.apache.log4j.Logger;

public class avE
implements JG,
xt_0,
nz {
    public static final int deS = 0;
    public static final int deT = 1;
    public static final int deU = 2;
    public static final int deV = 3;
    protected static final Logger a = Logger.getLogger(avE.class);
    protected static final boolean cR = false;
    protected static final int cq = 65536;
    protected static final int deW = 2;
    private long deX;
    protected int deY = 2;
    protected int[] deZ = new int[2];
    protected int[] dfa = new int[1];
    private final byte[] dfb = new byte[65536];
    protected AL cY;
    private int dfc;
    private int dfd;
    private boolean dfe = true;
    private int dff;
    private int[] dfg = new int[1];
    private float[] dfh = new float[3];
    private float bVZ = 1.0f;
    private float cyX = 1.0f;
    private float bCE = Float.MAX_VALUE;
    private boolean dfi = false;
    private int dfj = -1;
    private int dfk = -1;
    private int dfl = 0;
    protected volatile oa_1 dfm;
    private volatile boolean dfn = false;
    private aL bAT;
    private nu_1 dfo;
    protected volatile float dfp;
    protected volatile float bAO;
    protected volatile float Ov;
    protected volatile float bAN;
    protected volatile float bAP;
    protected volatile boolean dfq = false;
    protected volatile boolean dfr = false;
    protected volatile boolean dfs = false;
    protected volatile boolean cpj = false;
    protected volatile boolean dft = false;
    protected volatile boolean dfu = false;
    protected volatile boolean dfv = false;
    protected volatile long dfw = 0L;
    protected volatile long dfx = 0L;
    private volatile int dfy;
    private volatile boolean dfz = true;
    private volatile boolean dfA = false;
    private volatile boolean dfB = false;
    private volatile boolean dfC = false;

    public long alE() {
        return this.deX;
    }

    public void dV(long l2) {
        this.deX = l2;
    }

    public byte abc() {
        if (this.dfo != null) {
            return this.dfo.abc();
        }
        return 0;
    }

    public void c(aL aL2) {
        this.bAT = aL2;
    }

    public nu_1 aIN() {
        return this.dfo;
    }

    public oa_1 aIO() {
        return this.dfm;
    }

    public boolean aIP() {
        return this.dfn;
    }

    public void ep(boolean bl2) {
        this.dfn = bl2;
    }

    public boolean og() {
        return this.dfv;
    }

    public void eq(boolean bl2) {
        this.dfv = bl2;
    }

    public void b() {
        this.dfu = false;
        this.dfq = false;
        this.dfr = false;
        this.dfs = false;
        this.cpj = false;
        this.bAP = 0.0f;
        this.dfv = false;
        this.dfp = 0.0f;
        this.bAO = 1.0f;
        this.bAN = 1.0f;
        this.Ov = this.bAO;
        this.dfw = 0L;
        this.dfx = 0L;
        this.dfy = 1;
        this.dfz = true;
        this.dfh[2] = 0.0f;
        this.dfh[1] = 0.0f;
        this.dfh[0] = 0.0f;
        this.bVZ = 1.0f;
        this.cyX = 1.0f;
        this.bCE = Float.MAX_VALUE;
        this.dfB = false;
        this.dfk = -1;
        this.dfl = 0;
        this.dfi = false;
    }

    public void j() {
        this.cleanUp();
        if (this.bAT != null) {
            this.bAT.b(this.dfj, this.deX);
        }
        this.deY = 2;
    }

    public synchronized boolean mw(int n2) {
        boolean bl2 = true;
        try {
            int n3 = this.dfm.a(this.dfb, 0);
            boolean bl3 = n3 <= 0;
            n3 = Math.abs(n3);
            if (bl3) {
                if (this.dfv || --this.dfy > 0) {
                    this.dfm.reset();
                    n3 += Math.abs(this.dfm.a(this.dfb, n3));
                } else {
                    bl2 = false;
                }
            }
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.dfb, 0, n3);
            this.cY.alBufferData(n2, this.dfc, (Buffer)byteBuffer, n3, this.dff);
            this.cY.alSourceQueueBuffers(this.dfa[0], 1, this.dfg, 0);
        }
        catch (Exception exception) {
            a.error((Object)"Exeption lev\u00e9e", (Throwable)exception);
            return false;
        }
        return bl2;
    }

    public synchronized boolean aIQ() {
        if (!this.aIR()) {
            return false;
        }
        this.dfB = false;
        if (this.dfk != -1) {
            this.mx(this.dfk);
        }
        if (this.dfl != 0) {
            this.my(this.dfl);
        }
        this.setMute(this.dfu);
        this.setMinGain(this.dfp);
        this.setMaxGain(this.bAO);
        this.setGain(this.Ov);
        this.setRolloffFactor(this.bVZ);
        this.setReferenceDistance(this.cyX);
        this.setMaxDistance(this.bCE);
        if (this.dfm != null) {
            this.dfm.zu();
        }
        return true;
    }

    private synchronized boolean aIR() {
        block8: {
            while (true) {
                if (this.dfo.k(this.dfd)) {
                    this.dfe = true;
                    break block8;
                }
                if (this.bAT.k(this.dfd)) {
                    this.dfe = false;
                    break block8;
                }
                avE avE2 = ahz_1.aUa().aUc();
                if (avE2 == null) {
                    a.debug((Object)"Il n'y a aucune source \u00e0 lib\u00e9rer, mais on n'arrive pas \u00e0 r\u00e9server de voix");
                    this.dfd = 0;
                    return false;
                }
                if (avE2.abc() <= this.dfo.abc()) break;
                avE2.aIZ();
            }
            a.debug((Object)"Impossible d'assigner suffisament de voix \u00e0 cette Source.");
            this.dfd = 0;
            return false;
        }
        try {
            this.cY.alGenBuffers(2, this.deZ, 0);
            this.bAT.check();
        }
        catch (Exception exception) {
            a.warn((Object)"Impossible de cr\u00e9er un buffer suppl\u00e9mentaire.", (Throwable)exception);
            return false;
        }
        try {
            this.cY.alGenSources(1, this.dfa, 0);
            this.bAT.check();
        }
        catch (Exception exception) {
            a.warn((Object)"Impossible de cr\u00e9er une source audio suppl\u00e9mentaire.");
            return false;
        }
        this.cY.alSourcei(this.dfa[0], 4103, 0);
        this.cY.alSourcef(this.dfa[0], 4099, 1.0f);
        this.cY.alSourcei(this.dfa[0], 514, 1);
        return true;
    }

    public synchronized boolean a(aL aL2, nu_1 nu_12, oa_1 oa_12) {
        this.bAT = aL2;
        this.dfo = nu_12;
        this.cY = this.bAT.bI();
        this.dfm = oa_12;
        if (this.dfm.getNumChannels() == 1) {
            this.dfd = 1;
            this.dfc = 4353;
        } else {
            this.dfd = 2;
            this.dfc = 4355;
        }
        this.dff = this.dfm.zv();
        if (!this.aIR()) {
            return false;
        }
        this.setMaxGain(1.0f);
        this.setMinGain(0.0f);
        this.aj(1.0f);
        this.setGain(this.getMaxGain());
        return true;
    }

    public synchronized void mx(int n2) {
        this.dfk = n2;
        this.cY.alSource3i(this.dfa[0], 131078, n2, 0, this.dfi ? this.dfl : 0);
    }

    public synchronized void my(int n2) {
        this.dfl = n2;
        if (this.dfa[0] != 0) {
            if (this.dfk != -1) {
                this.cY.alSource3i(this.dfa[0], 131078, this.dfk, 0, this.dfi ? this.dfl : 0);
            } else {
                this.cY.alSourcei(this.dfa[0], 131077, this.dfi ? this.dfl : 0);
            }
        }
    }

    public synchronized void er(boolean bl2) {
        if (this.dfi == bl2) {
            return;
        }
        this.dfi = bl2;
        if (this.dfi) {
            this.bAT.a(this.dfj, this.deX);
        } else {
            this.bAT.b(this.dfj, this.deX);
        }
        this.my(this.dfl);
    }

    public int zU() {
        return this.dfj;
    }

    public void mz(int n2) {
        if (this.dfj == n2) {
            return;
        }
        this.bAT.b(this.dfj, this.deX);
        this.dfj = n2;
        this.bAT.a(this.dfj, this.deX);
    }

    public synchronized void aIS() {
        this.aIT();
        if (this.dfm != null) {
            this.dfm.close();
        }
        this.dfB = true;
    }

    private synchronized void aIT() {
        int n2;
        if (this.dfa[0] != 0) {
            this.cY.alSourceStop(this.dfa[0]);
            this.aIU();
        }
        for (n2 = 0; n2 < this.dfa.length; ++n2) {
            if (this.dfa[n2] == 0) continue;
            this.cY.alDeleteSources(1, this.dfa, n2);
            this.dfa[n2] = 0;
        }
        for (n2 = 0; n2 < 2; ++n2) {
            if (this.deZ[n2] == 0) continue;
            this.cY.alDeleteBuffers(1, this.deZ, n2);
            this.deZ[n2] = 0;
        }
        if (this.dfd != 0) {
            if (this.dfe) {
                this.dfo.l(this.dfd);
            } else {
                this.bAT.l(this.dfd);
            }
        }
        this.cpj = false;
        this.dft = false;
        this.dfC = false;
    }

    private synchronized void cleanUp() {
        if (this.cY == null) {
            return;
        }
        this.aIT();
        if (this.dfm != null) {
            this.dfm.close();
            this.dfm.zE();
            this.dfm = null;
        }
        this.dfd = 0;
        this.dfu = false;
        this.bAP = 0.0f;
        this.Ov = this.bAO;
        this.dfo = null;
        this.cY = null;
    }

    private synchronized void aIU() {
        if (this.cY == null || this.dfB) {
            return;
        }
        this.cY.alGetSourcei(this.dfa[0], 4117, this.dfg, 0);
        int[] nArray = new int[1];
        while (this.dfg[0] > 0) {
            this.cY.alSourceUnqueueBuffers(this.dfa[0], 1, nArray, 0);
            this.dfg[0] = this.dfg[0] - 1;
        }
    }

    public synchronized void play() {
        if (this.cY == null) {
            return;
        }
        this.cpj = false;
    }

    public synchronized int dW(long l2) {
        if (this.cY == null) {
            return 1;
        }
        if (this.dfw > 0L && l2 > this.dfw) {
            this.stop();
            return 1;
        }
        if (this.cpj) {
            return 1;
        }
        if (this.dfA) {
            return 2;
        }
        boolean bl2 = false;
        if (!this.dft) {
            for (int j = 0; j < this.deY; ++j) {
                this.dfg[0] = this.deZ[j];
                this.mw(this.deZ[j]);
            }
            this.dfC = true;
            bl2 = true;
            this.dft = true;
        } else {
            this.cY.alGetSourcei(this.dfa[0], 4118, this.dfg, 0);
            int n2 = this.dfg[0];
            if (n2 > 0) {
                if (n2 > 4) {
                    a.error((Object)("Processed buffers > 4 : " + n2));
                    try {
                        this.bAT.check();
                    }
                    catch (Exception exception) {
                        a.error((Object)"Exception ", (Throwable)exception);
                    }
                } else {
                    for (int j = 0; j < n2; ++j) {
                        this.cY.alSourceUnqueueBuffers(this.dfa[0], 1, this.dfg, 0);
                        if (!this.dfz) continue;
                        this.dfz = this.mw(this.dfg[0]);
                        this.dfC = true;
                    }
                    bl2 = true;
                }
            } else {
                this.dfC = false;
            }
        }
        if (bl2 && !this.aIX()) {
            this.cY.alSourcePlay(this.dfa[0]);
        }
        if (this.dfx > 0L && l2 > this.dfx) {
            this.bAP = this.Ov / (float)(this.dfw - this.dfx);
        }
        if (this.bAP != 0.0f) {
            this.setGain(this.Ov + this.bAP);
        }
        if (this.bAP < 0.0f && this.Ov == this.dfp) {
            this.bAP = 0.0f;
        } else if (this.bAP > 0.0f && this.Ov == this.bAO) {
            this.bAP = 0.0f;
        }
        if ((this.dfq || this.dfr || this.dfs) && this.Ov == this.dfp) {
            this.bAP = 0.0f;
            if (this.dfq) {
                this.stop();
                return 1;
            }
            if (this.dfr) {
                this.pause();
                return 2;
            }
            this.aIS();
            return 3;
        }
        return this.dfz || this.aIX() ? 0 : 1;
    }

    private int aIV() {
        if (this.cY != null && !this.dfB) {
            this.cY.alGetSourcei(this.dfa[0], 4112, this.dfg, 0);
            return this.dfg[0];
        }
        return -1;
    }

    public synchronized boolean aIW() {
        return this.dft;
    }

    public synchronized boolean isActive() {
        return this.dfz;
    }

    public synchronized boolean ajU() {
        return this.dfB;
    }

    public synchronized boolean aIX() {
        return this.aIV() == 4114;
    }

    public synchronized void pause() {
        if (this.dfA) {
            return;
        }
        int n2 = this.aIV();
        if (!(n2 != 4114 && n2 != 4113 && n2 != 4115 || this.cY == null || this.dfB)) {
            this.cY.alSourceStop(this.dfa[0]);
        }
        this.dfA = true;
    }

    public synchronized void aIY() {
        this.dfA = false;
    }

    public synchronized void stop() {
        int n2 = this.aIV();
        if (n2 == 4114 || n2 == 4113 || n2 == 4115) {
            if (!this.dfB) {
                this.cY.alSourceStop(this.dfa[0]);
            }
            this.aIU();
        }
        this.dft = false;
        this.dfz = true;
        if (this.dfm != null) {
            this.dfm.reset();
        }
    }

    public synchronized void close() {
        if (this.dfm != null) {
            this.dfm.close();
        }
    }

    public synchronized void aIZ() {
        this.cleanUp();
    }

    public void ak(float f) {
        float f2 = this.getGain();
        this.setGain(f2 *= f);
    }

    public void al(float f) {
        float f2 = this.getGain();
        this.bg(f2 *= f);
    }

    public void abH() {
        this.setGain(this.getGain());
    }

    public void abI() {
        this.bg(this.getGain());
    }

    public synchronized void setMute(boolean bl2) {
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcef(this.dfa[0], 4106, bl2 ? 0.0f : this.Ov * this.bAN);
        }
        this.dfu = bl2;
    }

    public synchronized void setGain(float f) {
        this.Ov = Math.min(this.bAO, Math.max(this.dfp, f));
        if (this.cY != null && !this.dfB) {
            if (this.dfu) {
                this.cY.alSourcef(this.dfa[0], 4106, 0.0f);
            } else {
                this.cY.alSourcef(this.dfa[0], 4106, this.Ov * this.bAN);
            }
        }
    }

    public synchronized void bg(float f) {
        f = Math.min(this.bAO, Math.max(this.dfp, f));
        if (this.cY != null && !this.dfB) {
            if (this.dfu) {
                this.cY.alSourcef(this.dfa[0], 4106, 0.0f);
            } else {
                this.cY.alSourcef(this.dfa[0], 4106, f * this.bAN);
            }
        }
    }

    public int zw() {
        if (this.dfm != null) {
            return this.dfm.zw();
        }
        return 0;
    }

    public float getGain() {
        return this.Ov;
    }

    public void setMaxGain(float f) {
        this.bAO = Math.min(1.0f, Math.max(0.0f, f));
        if (this.bAO < this.dfp) {
            float f2 = this.bAO;
            this.bAO = this.dfp;
            this.dfp = f2;
        }
        this.setGain(this.Ov);
    }

    public void setMinGain(float f) {
        this.dfp = Math.min(1.0f, Math.max(0.0f, f));
        if (this.bAO < this.dfp) {
            float f2 = this.bAO;
            this.bAO = this.dfp;
            this.dfp = f2;
        }
    }

    public float getMinGain() {
        return this.dfp;
    }

    public float getMaxGain() {
        return this.bAO;
    }

    public float abf() {
        return this.bAN;
    }

    public void aj(float f) {
        this.bAN = f;
        this.setGain(this.getGain());
    }

    public synchronized void setReferenceDistance(float f) {
        this.cyX = f;
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcef(this.dfa[0], 4128, f);
        }
    }

    public synchronized void setMaxDistance(float f) {
        this.bCE = f;
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcef(this.dfa[0], 4131, f);
        }
    }

    public synchronized void setRolloffFactor(float f) {
        this.bVZ = f;
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcef(this.dfa[0], 4129, f);
        }
    }

    public synchronized void c(agv_0 agv_02) {
        this.dfh[0] = agv_02.getX();
        this.dfh[1] = agv_02.getY();
        this.dfh[2] = agv_02.id();
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcefv(this.dfa[0], 4100, this.dfh, 0);
        }
    }

    public synchronized void setPosition(float f, float f2, float f3) {
        this.dfh[0] = f;
        this.dfh[1] = f2;
        this.dfh[2] = f3;
        if (this.cY != null && !this.dfB) {
            this.cY.alSourcefv(this.dfa[0], 4100, this.dfh, 0);
            try {
                this.bAT.check();
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    public long zx() {
        if (this.dfm != null) {
            return this.dfm.zx();
        }
        return -1L;
    }

    public synchronized int aL(long l2) {
        if (this.dfm != null) {
            return this.dfm.aL(l2);
        }
        return -1;
    }

    public synchronized long es(boolean bl2) {
        if (this.dfm == null || this.cY == null || this.dfB) {
            return -1L;
        }
        this.cY.alGetSourcei(this.dfa[0], 4117, this.dfg, 0);
        return this.dfm.zy() - (long)(bl2 ? 65536 / this.dfm.zA() * this.dfg[0] : 0);
    }

    public long zy() {
        return this.es(true);
    }

    public synchronized int aK(long l2) {
        if (this.dfm != null) {
            return this.dfm.aK(l2);
        }
        return -1;
    }

    public float zz() {
        if (this.dfm == null) {
            return 0.0f;
        }
        return this.dfm.zz();
    }

    public synchronized int T(float f) {
        if (this.dfm != null) {
            return this.dfm.T(f);
        }
        return -1;
    }

    public boolean aJa() {
        return this.dfC;
    }

    public void bh(float f) {
        this.dfq = true;
        this.bk(f);
    }

    public void bi(float f) {
        this.dfr = true;
        this.bk(f);
    }

    public void bj(float f) {
        this.bAP = f;
    }

    public void j(float f, float f2) {
        if (this.getMaxGain() < f) {
            this.setMaxGain(f);
        }
        if (f2 > 0.0f) {
            this.bAP = (f - this.getGain()) * 10.0f / f2;
        } else {
            this.setGain(f);
        }
    }

    public void bk(float f) {
        this.bAP = -f;
    }

    public void dX(long l2) {
        this.dfw = l2;
    }

    public void dY(long l2) {
        this.dfx = l2;
    }

    public void mA(int n2) {
        this.dfy = n2;
    }

    public void et(boolean bl2) {
        this.dfq = bl2;
    }

    public void eu(boolean bl2) {
        this.dfr = bl2;
    }

    public void ev(boolean bl2) {
        this.dfs = bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("URL : ").append(this.dfm.getDescription());
        stringBuilder.append("\n\tSample Rate : ").append(this.dfm.zv()).append("Hz");
        stringBuilder.append("\n\tChannels : ").append(this.dfm.getNumChannels());
        return stringBuilder.toString();
    }
}

