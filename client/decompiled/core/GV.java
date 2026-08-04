/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jogg.Packet
 *  com.jcraft.jogg.Page
 *  com.jcraft.jogg.StreamState
 *  com.jcraft.jogg.SyncState
 *  com.jcraft.jorbis.Block
 *  com.jcraft.jorbis.Comment
 *  com.jcraft.jorbis.DspState
 *  com.jcraft.jorbis.Info
 *  org.apache.log4j.Logger
 */
import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

public class GV
implements oa_1 {
    protected static final Logger a = Logger.getLogger(GV.class);
    private static final int CHUNK_SIZE = 8500;
    private static final int gR = 0;
    private static final int gT = 1;
    private static final int bcw = -1;
    private static final int bcx = -2;
    private static final int bcy = -128;
    private static final int bcz = -129;
    private static final int bcA = -130;
    private int bcB;
    private boolean bcC = true;
    private AtomicInteger amW = new AtomicInteger(0);
    private int bcD = 8500;
    private boolean bcE = false;
    private final boolean bcF = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    private SyncState bcG;
    private StreamState bcH;
    private Page bcI;
    private Packet bcJ;
    private DspState bcK;
    private Block bcL;
    private int bcM = 0;
    private long bcN;
    private long[] bcO;
    private long[] bcP;
    private int[] bcQ;
    private long[] bcR;
    private Info[] bcS;
    private Comment[] bcT;
    private long bcU;
    private boolean bcV = false;
    private int bcW;
    private int bcX;
    private auk bcY;
    private static final int bcZ = 8192;
    private byte[] bda = new byte[8192];
    private ByteBuffer bdb = ByteBuffer.wrap(this.bda);
    private final byte[] bdc = new byte[8192];
    private final float[][][] bdd = new float[1][][];

    public String getDescription() {
        return this.bcY.getDescription();
    }

    private boolean Sl() {
        Info info = new Info();
        Comment comment = new Comment();
        this.bcD = Math.min(8500, (int)GV.c(this.bcY));
        Page page = new Page();
        int[] nArray = new int[1];
        int n2 = this.a(info, comment, nArray, null);
        int n3 = nArray[0];
        int n4 = (int)this.bcN;
        this.bcH.clear();
        if (n2 < 0) {
            return false;
        }
        GV.a(this.bcY, 0L, 1);
        this.bcN = GV.d(this.bcY);
        long l2 = this.a(page);
        if (page.serialno() != n3 ? this.a(0L, 0L, l2 + 1L, n3, 0) < 0 : this.a(0L, l2, l2 + 1L, n3, 0) < 0) {
            return false;
        }
        this.a(info, comment, n4);
        this.aL(this.bcP[0]);
        return true;
    }

    private boolean Sm() {
        this.bcM = 1;
        this.bcS = new Info[1];
        this.bcS[0] = new Info();
        this.bcT = new Comment[1];
        this.bcT[0] = new Comment();
        int[] nArray = new int[1];
        if (this.a(this.bcS[0], this.bcT[0], nArray, null) == -1) {
            return false;
        }
        this.bcW = nArray[0];
        this.So();
        return true;
    }

    private int Sn() {
        int n2;
        int n3 = this.bcG.buffer(this.bcD);
        if (n3 == -1) {
            a.debug((Object)("Stream corrompu : " + this.getDescription()));
            return -128;
        }
        byte[] byArray = this.bcG.data;
        try {
            n2 = this.bcY.read(byArray, n3, this.bcD);
        }
        catch (Exception exception) {
            return -128;
        }
        this.bcG.wrote(n2);
        if (n2 == -1) {
            n2 = 0;
        }
        return n2;
    }

    private void So() {
        this.bcK.synthesis_init(this.bcS[0]);
        this.bcL.init(this.bcK);
        this.bcV = true;
    }

    private void Sp() {
        this.bcH.clear();
        this.bcK.clear();
        this.bcL.clear();
        this.bcV = false;
    }

    private int a(Page page, long l2) {
        int n2;
        int n3;
        block6: {
            if (l2 > 0L) {
                l2 += this.bcN;
            }
            while (true) {
                if (l2 > 0L && this.bcN >= l2) {
                    return -1;
                }
                n3 = this.bcG.pageseek(page);
                if (n3 < 0) {
                    this.bcN -= (long)n3;
                    continue;
                }
                if (n3 != 0) break block6;
                if (l2 == 0L) {
                    return -1;
                }
                n2 = this.Sn();
                if (n2 == 0) {
                    return -2;
                }
                if (n2 < 0) break;
            }
            return -128;
        }
        n2 = (int)this.bcN;
        this.bcN += (long)n3;
        return n2;
    }

    private int a(Page page) {
        int n2;
        long l2 = this.bcN;
        int n3 = -1;
        while (n3 == -1) {
            if ((l2 -= (long)this.bcD) < 0L) {
                l2 = 0L;
            }
            this.bD(l2);
            while (this.bcN < l2 + (long)this.bcD) {
                n2 = this.a(page, l2 + (long)this.bcD - this.bcN);
                if (n2 == -128) {
                    return -128;
                }
                if (n2 < 0) {
                    if (n3 != -1) continue;
                    return -1;
                }
                n3 = n2;
            }
        }
        this.bD(n3);
        n2 = this.a(page, (long)this.bcD);
        if (n2 < 0) {
            return -129;
        }
        return n3;
    }

    private void bD(long l2) {
        GV.a(this.bcY, l2, 0);
        this.bcN = l2;
        this.bcG.reset();
    }

    private int a(long l2, long l3, long l4, int n2, int n3) {
        int n4;
        long l5 = l4;
        long l6 = l4;
        Page page = new Page();
        while (l3 < l5) {
            long l7 = l5 - l3 < (long)this.bcD ? l3 : (l3 + l5) / 2L;
            this.bD(l7);
            n4 = this.a(page, -1L);
            if (n4 == -128) {
                return -128;
            }
            if (n4 < 0 || page.serialno() != n2) {
                l5 = l7;
                if (n4 < 0) continue;
                l6 = n4;
                continue;
            }
            l3 = n4 + page.header_len + page.body_len;
        }
        this.bD(l6);
        n4 = this.a(page, -1L);
        if (n4 == -128) {
            return -128;
        }
        if (l3 >= l4 || n4 == -1) {
            this.bcM = n3 + 1;
            this.bcO = new long[n3 + 2];
            this.bcO[n3 + 1] = l3;
        } else {
            n4 = this.a(l6, this.bcN, l4, page.serialno(), n3 + 1);
            if (n4 == -128) {
                return -128;
            }
        }
        this.bcO[n3] = l2;
        return 0;
    }

    public int a(Info info, Comment comment, int[] nArray, Page page) {
        Packet packet = new Packet();
        if (page == null) {
            page = new Page();
            int n2 = this.a(page, (long)this.bcD);
            if (n2 == -128) {
                return -128;
            }
            if (n2 < 0) {
                return -130;
            }
        }
        if (nArray != null) {
            nArray[0] = page.serialno();
        }
        this.bcH.init(page.serialno());
        info.init();
        comment.init();
        int n3 = 0;
        while (n3 < 3) {
            int n4;
            this.bcH.pagein(page);
            while (n3 < 3 && (n4 = this.bcH.packetout(packet)) != 0) {
                if (n4 == -1) {
                    info.clear();
                    this.bcH.clear();
                    return -1;
                }
                if (info.synthesis_headerin(comment, packet) != 0) {
                    info.clear();
                    this.bcH.clear();
                    return -1;
                }
                ++n3;
            }
            if (n3 >= 3 || this.a(page, 1L) >= 0) continue;
            info.clear();
            this.bcH.clear();
            return -1;
        }
        return 0;
    }

    public Info[] getInfo() {
        return this.bcS;
    }

    private void a(Info info, Comment comment, int n2) {
        Page page = new Page();
        this.bcS = new Info[this.bcM];
        this.bcT = new Comment[this.bcM];
        this.bcP = new long[this.bcM];
        this.bcR = new long[this.bcM];
        this.bcQ = new int[this.bcM];
        block0: for (int j = 0; j < this.bcM; ++j) {
            if (info != null && comment != null && j == 0) {
                this.bcS[j] = info;
                this.bcT[j] = comment;
                this.bcP[j] = n2;
            } else {
                this.bD(this.bcO[j]);
                this.bcS[j] = new Info();
                this.bcT[j] = new Comment();
                if (this.a(this.bcS[j], this.bcT[j], null, null) == -1) {
                    this.bcP[j] = -1L;
                } else {
                    this.bcP[j] = this.bcN;
                    this.bcH.clear();
                }
            }
            long l2 = this.bcO[j + 1];
            this.bD(l2);
            do {
                int n3;
                if ((n3 = this.a(page)) != -1) continue;
                this.bcS[j].clear();
                continue block0;
            } while (page.granulepos() == -1L);
            this.bcQ[j] = page.serialno();
            this.bcR[j] = page.granulepos();
        }
    }

    public boolean zu() {
        return this.b(this.bcY);
    }

    public boolean b(auk auk2) {
        this.bcY = auk2;
        try {
            this.bcY.Hm();
        }
        catch (IOException iOException) {
            a.info((Object)("Probl\u00e8me \u00e0 l'ouverture du stream " + auk2.getDescription()));
            try {
                this.bcY.close();
            }
            catch (IOException iOException2) {
                a.info((Object)("Probl\u00e8me au nettoyage du stream " + auk2.getDescription()));
            }
            return false;
        }
        this.bcN = 0L;
        this.bcI = new Page();
        this.bcJ = new Packet();
        this.bcG = new SyncState();
        this.bcH = new StreamState();
        this.bcK = new DspState();
        this.bcL = new Block(this.bcK);
        this.bcG.init();
        if (auk2.Hn()) {
            return this.Sl();
        }
        return this.Sm();
    }

    private int fX(int n2) {
        while (true) {
            int n3;
            if (this.bcV && (n3 = this.bcH.packetout(this.bcJ)) > 0) {
                long l2 = this.bcJ.granulepos;
                if (this.bcL.synthesis(this.bcJ) == 0) {
                    this.bcK.synthesis_blockin(this.bcL);
                    if (l2 != -1L && this.bcJ.e_o_s == 0) {
                        int n4 = this.bcY.Hn() ? this.bcX : 0;
                        int n5 = this.bcK.synthesis_pcmout((float[][][])null, null);
                        l2 -= (long)n5;
                        for (int j = 0; j < n4; ++j) {
                            l2 += this.bcR[j];
                        }
                        this.bcU = l2;
                    }
                    return 1;
                }
            }
            if (n2 == 0) {
                return 0;
            }
            if (this.a(this.bcI, -1L) < 0) {
                return 0;
            }
            if (this.bcV && this.bcW != this.bcI.serialno()) {
                this.Sp();
            }
            if (!this.bcV) {
                if (this.bcY.Hn()) {
                    this.bcW = this.bcI.serialno();
                    for (n3 = 0; n3 < this.bcM && this.bcQ[n3] != this.bcW; ++n3) {
                    }
                    if (n3 == this.bcM) {
                        return -1;
                    }
                    this.bcX = n3;
                    this.bcH.init(this.bcW);
                    this.bcH.reset();
                } else {
                    int[] nArray = new int[1];
                    int n6 = this.a(this.bcS[0], this.bcT[0], nArray, this.bcI);
                    this.bcW = nArray[0];
                    if (n6 != 0) {
                        return n6;
                    }
                    ++this.bcX;
                }
                this.So();
            }
            this.bcH.pagein(this.bcI);
        }
    }

    public long fY(int n2) {
        if (!this.bcY.Hn() || n2 >= this.bcM) {
            return -1L;
        }
        if (n2 < 0) {
            long l2 = 0L;
            for (int j = 0; j < this.bcM; ++j) {
                l2 += this.fY(j);
            }
            return l2;
        }
        return this.bcO[n2 + 1] - this.bcO[n2];
    }

    public long fZ(int n2) {
        if (!this.bcY.Hn() || n2 >= this.bcM) {
            return -1L;
        }
        if (n2 < 0) {
            long l2 = 0L;
            for (int j = 0; j < this.bcM; ++j) {
                l2 += this.fZ(j);
            }
            return l2;
        }
        return this.bcR[n2];
    }

    public long Sq() {
        if (!this.bcY.Hn()) {
            return -1L;
        }
        long l2 = 0L;
        for (int j = 0; j < this.bcM; ++j) {
            l2 += this.bcR[j] * (long)this.bcS[j].channels * 2L;
        }
        return l2;
    }

    public float ga(int n2) {
        if (!this.bcY.Hn() || n2 >= this.bcM) {
            return -1.0f;
        }
        if (n2 < 0) {
            float f = 0.0f;
            for (int j = 0; j < this.bcM; ++j) {
                f += this.ga(j);
            }
            return f;
        }
        return (float)this.bcR[n2] / (float)this.bcS[n2].rate;
    }

    public int aL(long l2) {
        if (!this.bcY.Hn()) {
            return -1;
        }
        this.bcU = -1L;
        this.Sp();
        if (l2 < 0L || l2 > this.bcO[this.bcM]) {
            return -1;
        }
        this.bD(l2);
        switch (this.fX(1)) {
            case 0: {
                this.bcU = this.fZ(-1);
                return 0;
            }
            case -1: {
                this.bcU = -1L;
                this.Sp();
                return -1;
            }
        }
        while (true) {
            switch (this.fX(0)) {
                case 0: {
                    return 0;
                }
                case -1: {
                    this.bcU = -1L;
                    this.Sp();
                    return -1;
                }
            }
        }
    }

    public int aK(long l2) {
        int n2;
        if (!this.bcY.Hn()) {
            return -1;
        }
        long l3 = this.fZ(-1);
        if (l2 < 0L || l2 > l3) {
            this.bcU = -1L;
            this.Sp();
            return -1;
        }
        for (n2 = this.bcM - 1; n2 >= 0 && l2 < (l3 -= this.bcR[n2]); --n2) {
        }
        long l4 = l2 - l3;
        long l5 = this.bcO[n2 + 1];
        long l6 = this.bcO[n2];
        int n3 = (int)l6;
        Page page = new Page();
        while (l6 < l5) {
            long l7 = l5 - l6 < (long)this.bcD ? l6 : (l5 + l6) / 2L;
            this.bD(l7);
            int n4 = this.a(page, l5 - l7);
            if (n4 == -1) {
                l5 = l7;
                continue;
            }
            long l8 = page.granulepos();
            if (l8 < l4) {
                n3 = n4;
                l6 = this.bcN;
                continue;
            }
            l5 = l7;
        }
        if (this.aL(n3) != 0) {
            this.bcU = -1L;
            this.Sp();
            return -1;
        }
        if (this.bcU >= l2) {
            this.bcU = -1L;
            this.Sp();
            return -1;
        }
        if (l2 > this.fZ(-1)) {
            this.bcU = -1L;
            this.Sp();
            return -1;
        }
        while (this.bcU < l2) {
            int n5 = (int)(l2 - this.bcU);
            int[] nArray = new int[this.bcS[this.bcX].channels];
            int n6 = this.bcK.synthesis_pcmout(this.bdd, nArray);
            if (n6 > n5) {
                n6 = n5;
            }
            this.bcK.synthesis_read(n6);
            this.bcU += (long)n6;
            if (n6 >= n5 || this.fX(1) != 0) continue;
            this.bcU = this.fZ(-1);
        }
        return 0;
    }

    public int T(float f) {
        int n2;
        if (!this.bcY.Hn()) {
            return -1;
        }
        long l2 = this.fZ(-1);
        float f2 = this.ga(-1);
        if (f < 0.0f || f > f2) {
            this.bcU = -1L;
            this.Sp();
            return -1;
        }
        for (n2 = this.bcM - 1; n2 >= 0; --n2) {
            l2 -= this.bcR[n2];
            if (f >= (f2 -= this.ga(n2))) break;
        }
        long l3 = (long)((float)l2 + (f - f2) * (float)this.bcS[n2].rate);
        return this.aK(l3);
    }

    public long zx() {
        return this.bcN;
    }

    public long zy() {
        return this.bcU;
    }

    public float zz() {
        int n2 = -1;
        long l2 = 0L;
        float f = 0.0f;
        if (this.bcM < 1) {
            return 0.0f;
        }
        if (this.bcY.Hn()) {
            l2 = this.fZ(-1);
            f = this.ga(-1);
            for (n2 = this.bcM - 1; n2 >= 0; --n2) {
                f -= this.ga(n2);
                if (this.bcU >= (l2 -= this.bcR[n2])) break;
            }
        }
        return f + (float)(this.bcU - l2) / (float)this.bcS[n2].rate;
    }

    public int a(byte[] byArray, int n2) {
        this.bcC = false;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byteBuffer.position(n2);
        while (byteBuffer.remaining() > 0) {
            if (this.bcV) {
                Info info = this.bcS[this.bcX];
                int[] nArray = new int[info.channels];
                int n3 = this.bcK.synthesis_pcmout(this.bdd, nArray);
                float[][] fArray = this.bdd[0];
                if (n3 > 0) {
                    int n4;
                    int n5 = this.bcS[this.bcX].channels;
                    int n6 = n5 * 2;
                    n3 = Math.min(n3, byteBuffer.remaining() / n6);
                    n3 = Math.min(n3, 8192 / n6);
                    for (n4 = 0; n4 < info.channels; ++n4) {
                        int n7 = n4 * 2;
                        int n8 = nArray[n4];
                        for (int j = 0; j < n3; ++j) {
                            int n9 = (int)(fArray[n4][n8 + j] * 32767.0f);
                            if (n9 > Short.MAX_VALUE) {
                                n9 = Short.MAX_VALUE;
                            }
                            if (n9 < Short.MIN_VALUE) {
                                n9 = Short.MIN_VALUE;
                            }
                            if (n9 < 0) {
                                n9 |= 0x8000;
                            }
                            if (this.bcF) {
                                this.bdc[n7] = (byte)(n9 >>> 8 & 0xFF);
                                this.bdc[n7 + 1] = (byte)(n9 & 0xFF);
                            } else {
                                this.bdc[n7] = (byte)(n9 & 0xFF);
                                this.bdc[n7 + 1] = (byte)(n9 >>> 8 & 0xFF);
                            }
                            n7 += 2 * info.channels;
                        }
                    }
                    n4 = n3 * n6;
                    byteBuffer.put(this.bdc, 0, n4);
                    this.bcK.synthesis_read(n3);
                    this.bcU += (long)n3;
                }
            }
            switch (this.fX(1)) {
                case 0: {
                    return -(byteBuffer.position() - n2);
                }
                case -1: {
                    return -(byteBuffer.position() - n2);
                }
            }
        }
        return byteBuffer.position() - n2;
    }

    public int zA() {
        return this.bcS[this.bcX].channels * 2;
    }

    public int getNumChannels() {
        return this.bcS[0].channels;
    }

    public int zv() {
        return this.bcS[0].rate;
    }

    public void aB(boolean bl2) {
        this.bcE = bl2;
    }

    public int zw() {
        return (int)(this.ga(-1) * 1000.0f);
    }

    public void loop() {
        if (this.bcY.Hn()) {
            this.aL(this.bcP[0]);
        } else {
            this.reset();
        }
    }

    public void close() {
        if (this.bcY != null) {
            try {
                this.bcY.close();
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible de fermer le flux pour le stream " + this.bcY.getDescription()));
            }
        }
    }

    public void reset() {
        if (!this.bcC) {
            if (this.bcY.Hn()) {
                this.aL(this.bcP[0]);
            } else {
                this.bdb.rewind();
                this.bcH.clear();
                this.bcL.clear();
                this.bcK.clear();
                if (this.bcS != null) {
                    for (Info info : this.bcS) {
                        info.clear();
                    }
                }
                this.bcG.clear();
                try {
                    if (this.bcY != null) {
                        this.bcY.reset();
                    }
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
                this.bcM = 0;
                this.bcN = 0L;
                this.bcO = null;
                this.bcP = null;
                this.bcQ = null;
                this.bcR = null;
                this.bcS = null;
                this.bcT = null;
                this.bcU = 0L;
                this.bcV = false;
                this.bcW = 0;
                this.bcX = 0;
                this.b(this.bcY);
            }
            this.bcC = true;
        }
    }

    public int zB() {
        return this.bcB;
    }

    public void dF(int n2) {
        this.bcB = n2;
    }

    public int zC() {
        return this.amW.get();
    }

    public void zD() {
        this.amW.incrementAndGet();
    }

    public void zE() {
        this.amW.decrementAndGet();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(this.getClass().getSimpleName());
        stringBuffer.append(" : url=").append(this.bcY.getDescription());
        return stringBuffer.toString();
    }

    private static long c(auk auk2) {
        if (!auk2.Hn()) {
            return -1L;
        }
        try {
            return auk2.length();
        }
        catch (IOException iOException) {
            a.debug((Object)"Probl\u00e8me lors du length() sur le stream !", (Throwable)iOException);
            return -1L;
        }
    }

    private static int a(auk auk2, long l2, int n2) {
        if (!auk2.Hn()) {
            return -1;
        }
        try {
            if (n2 == 0) {
                auk2.seek(l2);
            } else if (n2 == 1) {
                auk2.seek(auk2.length() - l2);
            }
            return 0;
        }
        catch (IOException iOException) {
            a.debug((Object)"Probl\u00e8me lors du seek sur le stream !", (Throwable)iOException);
            return -1;
        }
    }

    private static long d(auk auk2) {
        if (!auk2.Hn()) {
            return 0L;
        }
        try {
            return auk2.tell();
        }
        catch (IOException iOException) {
            a.debug((Object)"Probl\u00e8me lors du tell sur le stream !", (Throwable)iOException);
            return 0L;
        }
    }

    public Comment Sr() {
        if (this.bcT == null || this.bcT.length == 0) {
            return null;
        }
        return this.bcT[0];
    }
}

