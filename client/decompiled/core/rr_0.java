/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from rr
 */
public class rr_0 {
    protected static Logger a = Logger.getLogger(rr_0.class);
    private final String agm;
    private final yi_0 agn;
    private boolean aer;
    private boolean ago;
    private boolean agp;
    private boolean agq;
    private boolean agr;
    private aip_2 ags;
    private zw_2 agt;
    private Xz agu;
    private ayh agv;
    private ayh agw;
    private int agx;
    private int agy;
    private long agz = va_0.arJ;
    private long agA = 0L;
    private int agB;
    private int agC;
    private int agD;
    private int agE;
    private final ArrayList agF;
    private static final int agG = 32;
    private static final int agH = 32;
    private static final int agI = 4096;
    private static final int agJ = 4096;

    public rr_0(String string, yi_0 yi_02) {
        this.agm = string;
        this.agn = yi_02;
        this.ago = false;
        this.aer = false;
        this.agp = false;
        this.agq = false;
        this.agB = -1;
        this.agC = -1;
        this.agr = true;
        this.agz = va_0.arJ;
        this.agA = 0L;
        this.agF = new ArrayList();
    }

    public rr_0(String string) {
        this(string, yi_0.aBp);
    }

    public void initialize() {
        if (!Xz.a(hb_2.bdq)) {
            throw new RuntimeException("l'installation de xuggler ne supporte pas la conversion de format \u00e0 la vol\u00e9e");
        }
        this.ags = aip_2.axP();
        try {
            if (this.ags.a(new URL(this.agm).openStream(), null) < 0) {
                throw new IllegalArgumentException("Impossible d'ouvrir le fichier video : " + this.agm);
            }
        }
        catch (IOException iOException) {
            a.error((Object)("Probl\u00e8me \u00e0 l'ouverture de la video : " + this.agm));
            return;
        }
        int n2 = this.ags.axM();
        this.agx = -1;
        this.agt = null;
        for (int j = 0; j < n2; ++j) {
            at_2 at_22 = this.ags.dG(j);
            zw_2 zw_22 = at_22.HL();
            if (zw_22.anE() != auc_0.cVO) continue;
            this.agx = j;
            this.agt = zw_22;
            break;
        }
        if (this.agx == -1 || this.agt == null) {
            throw new RuntimeException("Le fichier ne contient pas de stream video : " + this.agm);
        }
        if (this.agt.anO() < 0) {
            throw new RuntimeException("Impossible de trouver un decodeur video pour : " + this.agm);
        }
        this.wr();
        this.aer = true;
        this.agp = false;
        this.agq = false;
        this.agy = 0;
        this.agz = va_0.arJ;
        this.agA = System.currentTimeMillis();
    }

    private void wr() {
        int n2 = this.getWidth();
        int n3 = this.getHeight();
        this.agD = n2;
        this.agE = n3;
        int n4 = this.agt.getWidth();
        int n5 = this.agt.getHeight();
        float f = (float)n4 / (float)n5;
        if (this.agr) {
            float f2 = (float)this.agD / (float)this.agE;
            float f3 = f2 / f;
            if (f2 > f) {
                this.agD = Math.round((float)this.agD / f3);
            } else if (f2 < f) {
                this.agE = Math.round((float)this.agE * f3);
            }
        }
        this.agD = Math.min(Math.max(32, this.agD), 4096);
        this.agE = Math.min(Math.max(32, this.agE), 4096);
        this.agu = Xz.a(this.agD, this.agE, this.agn, this.agt.getWidth(), this.agt.getHeight(), this.agt.anJ());
        if (this.agu == null) {
            throw new RuntimeException("Impossible de cr\u00e9er un convertisseur de " + this.agt.anJ().name() + "=>" + this.agn.name() + " pour " + this.agm + " outputSize=(" + this.agD + ", " + this.agE + ")");
        }
        this.agv = ayh.a(this.agu.ale(), this.agu.wy(), this.agu.wz());
        this.agw = ayh.a(this.agt.anJ(), this.getWidth(), this.getHeight());
    }

    public void start() {
        if (!this.aer) {
            a.error((Object)"Lancement d'un stream video non initialis\u00e9");
        }
        this.ago = true;
        this.agp = false;
        this.agA = System.currentTimeMillis();
    }

    public void setPaused(boolean bl2) {
        this.ago = !bl2;
    }

    public boolean isPaused() {
        return !this.ago;
    }

    public void reset() {
        this.seek(0L);
        this.ago = true;
        this.aer = true;
        this.agp = false;
        this.agq = true;
        this.agz = va_0.arJ;
        this.agA = System.currentTimeMillis();
    }

    public void seek(long l2) {
        this.ags.a(this.agx, l2, l2, l2, 0);
        this.agq = true;
    }

    public void close() {
        if (this.ags != null) {
            this.ags.de();
            this.ags = null;
        }
        if (this.agt != null) {
            this.agt.de();
            this.agt = null;
        }
        this.ago = false;
    }

    public synchronized void aD(long l2) {
        if (!this.ago || !this.aer || this.agp) {
            return;
        }
        if (this.agz != va_0.arJ && this.agw.isComplete()) {
            long l3 = System.currentTimeMillis();
            long l4 = l3 - this.agA;
            long l5 = (this.agw.getTimeStamp() - this.agz) / 1000L;
            long l6 = 10L;
            long l7 = l5 - (l4 + 10L);
            if (l7 > 0L) {
                return;
            }
        }
        ala_1 ala_12 = ala_1.aAV();
        boolean bl2 = false;
        while (!bl2 && this.ags.b(ala_12) >= 0) {
            if (ala_12.aAT() != this.agx) continue;
            int n2 = 0;
            while (n2 < ala_12.getSize()) {
                int n3 = this.agt.a(this.agw, ala_12, n2);
                if (n3 < 0) {
                    throw new RuntimeException("got error decoding video in: " + this.agm);
                }
                n2 += n3;
                if (!this.agw.isComplete()) continue;
                if (this.agz == va_0.arJ) {
                    this.agz = this.agw.getTimeStamp();
                }
                if (this.agu.a(this.agv, this.agw) < 0) {
                    throw new RuntimeException("could not resample video from: " + this.agm);
                }
                if (this.agv.anJ() != this.agn) {
                    throw new RuntimeException("could not decode video as " + this.agn.name() + " data in: " + this.agm);
                }
                ++this.agy;
            }
            bl2 = true;
            this.agq = false;
        }
        if (!bl2 && !this.agp) {
            this.agp = true;
            this.wA();
        }
    }

    public boolean ws() {
        return this.agq;
    }

    public long wt() {
        at_2 at_22 = this.ags.dG(this.agx);
        return at_22.HO() - at_22.getStartTime();
    }

    public boolean isStarted() {
        return this.ago && !this.agp;
    }

    public int wu() {
        return this.agy;
    }

    public boolean wv() {
        return this.agp;
    }

    public synchronized ayh ww() {
        return this.agv;
    }

    public long getDuration() {
        return this.ags.dG(this.agx).getDuration();
    }

    public long getFrameCount() {
        return this.ags.dG(this.agx).HQ();
    }

    public int getWidth() {
        int n2 = this.agt.getWidth();
        return this.agB != -1 ? Math.min(this.agB, n2) : n2;
    }

    public int getHeight() {
        int n2 = this.agt.getHeight();
        return this.agC != -1 ? Math.min(this.agC, n2) : n2;
    }

    public boolean isInitialized() {
        return this.aer;
    }

    public void cZ(int n2) {
        if (n2 == this.agB) {
            return;
        }
        this.agB = n2;
        this.wr();
    }

    public void da(int n2) {
        if (n2 == this.agC) {
            return;
        }
        this.agC = n2;
        this.wr();
    }

    public boolean wx() {
        return this.agr;
    }

    public void setKeepAspectRatio(boolean bl2) {
        this.agr = bl2;
    }

    public int wy() {
        return this.agD;
    }

    public int wz() {
        return this.agE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void wA() {
        ArrayList arrayList = this.agF;
        synchronized (arrayList) {
            int n2 = this.agF.size();
            for (int j = 0; j < n2; ++j) {
                ((aAQ)this.agF.get(j)).aMY();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aAQ aAQ2) {
        ArrayList arrayList = this.agF;
        synchronized (arrayList) {
            if (!this.agF.contains(aAQ2)) {
                this.agF.add(aAQ2);
            }
        }
    }
}

