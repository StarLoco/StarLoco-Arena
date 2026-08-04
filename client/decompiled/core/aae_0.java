/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.java.games.joal.ALException
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import net.java.games.joal.ALException;

/*
 * Renamed from aaE
 */
public class aae_0
extends nu_1 {
    private static final boolean DEBUG = false;
    public static final byte cfV = 4;
    public static final int cfW = 8000;
    public static final int cfX = 8000;
    private avE cfY;
    private avE cfZ;
    private avE cga;
    private boolean bKE = false;
    private avE cgb;
    private xc_1 cgc = xc_1.azb;
    private xc_1 cgd;
    private ArrayList cge = null;
    private int cgf = 0;
    private int cgg = 0;
    private int cgh = 0;
    private int cgi = 0;
    private int cgj = 0;
    private long cgk = 0L;
    private hD cgl = null;
    private float cgm = 0.0f;
    private boolean cgn = false;
    private boolean cgo = false;
    private int cgp = 8000;
    private int cgq = 8000;
    private final Object cgr = new Object();

    public aae_0(String string) {
        this(string, 0);
    }

    public aae_0(String string, byte by) {
        super(string, by);
        this.bW(true);
        this.cfY = null;
        this.cfZ = null;
        this.cga = null;
    }

    public final avE ape() {
        return this.cfY;
    }

    public final void ju(int n2) {
        this.cgp = n2;
    }

    public final void jb(int n2) {
        this.cgq = n2;
    }

    public final synchronized avE a(auk auk2, float f) {
        return this.a(auk2, f, false);
    }

    public synchronized void cO(boolean bl2) {
        if (this.cgl != null && this.cgn != bl2) {
            this.cgo = true;
        }
        this.cgn = bl2;
    }

    public void bM() {
    }

    public final synchronized avE c(long l2, float f) {
        return this.a(l2, f, false);
    }

    public final synchronized void cP(boolean bl2) {
        if (this.bKE == bl2) {
            return;
        }
        this.bKE = bl2;
        if (this.bKE) {
            this.cgb = this.cfY;
            if (this.cgb != null) {
                this.cgb.j(0.0f, this.cgq);
                this.cgb.eu(true);
            }
            this.cgd = this.cgc;
            this.cgc = xc_1.azh;
            this.cfY = null;
        } else {
            if (this.cgb != null) {
                this.cgb.aIY();
                this.d(this.cgb);
            } else if (this.cfY != null) {
                this.cfY.j(0.0f, this.cgq);
                this.cfY.et(true);
            }
            this.cgc = this.cgd;
            this.cgb = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void ay(long l2) {
        super.ay(l2);
        Object object = this.cgr;
        synchronized (object) {
            agv_0 agv_02 = agv_0.dIL;
            try {
                if (this.cfY != null) {
                    this.cfY.c(agv_02);
                    try {
                        switch (this.cfY.dW(l2)) {
                            case 1: 
                            case 3: {
                                this.bAT.a(this.cfY);
                                this.cfY = null;
                                if (this.cgc != xc_1.azd) break;
                                this.cgc = xc_1.azf;
                            }
                        }
                    }
                    catch (ALException aLException) {
                        a.error((Object)"Exception", (Throwable)aLException);
                        this.bAT.a(this.cfY);
                        this.cfY = null;
                    }
                }
                if (this.cfZ != null) {
                    this.cfZ.c(agv_02);
                    try {
                        switch (this.cfZ.dW(l2)) {
                            case 1: 
                            case 3: {
                                this.bAT.a(this.cfZ);
                                this.cfZ = null;
                            }
                        }
                    }
                    catch (ALException aLException) {
                        a.error((Object)"Exception", (Throwable)aLException);
                        this.bAT.a(this.cfZ);
                        this.cfZ = null;
                    }
                }
                if (this.cgb != null) {
                    this.cgb.c(agv_02);
                    try {
                        switch (this.cgb.dW(l2)) {
                            case 1: 
                            case 3: {
                                this.bAT.a(this.cgb);
                                this.cgb = null;
                            }
                        }
                    }
                    catch (ALException aLException) {
                        a.error((Object)"Exception", (Throwable)aLException);
                        this.bAT.a(this.cgb);
                        this.cgb = null;
                    }
                }
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
            if (this.cfZ != null && this.cfY == null) {
                this.cfY = this.cfZ;
                this.cfZ = null;
            }
            if (this.cfZ == null && this.cga != null) {
                avE avE2 = this.cga;
                this.cga = null;
                this.d(avE2);
            }
            switch (this.cgc) {
                case azb: {
                    break;
                }
                case azc: {
                    this.cgf = 0;
                    if (this.cge == null) break;
                    this.cgl = (hD)this.cge.get(this.cgf);
                    long l3 = this.cgn ? this.cgl.kK() : this.cgl.kJ();
                    this.a(l3, (float)this.cgl.kL() / 100.0f, true);
                    this.cgg = this.cgl.getDuration() == -1 ? -1 : this.cgl.getDuration() * 1000;
                    this.cgh = 0;
                    this.cgc = xc_1.azd;
                    break;
                }
                case azd: {
                    this.cgh = (int)((long)this.cgh + (l2 - this.cgk));
                    if (this.cgg != -1 && this.cgh + this.cgq > this.cgg) {
                        if (this.cfY != null) {
                            this.cfY.j(0.0f, this.cgq);
                            this.cfY.et(true);
                        }
                        this.cgc = xc_1.aze;
                        break;
                    }
                    if (!this.cgo) break;
                    long l4 = 0L;
                    if (!this.cfY.aJa()) break;
                    if (this.cfY != null) {
                        l4 = this.cfY.zy();
                    }
                    long l5 = this.cgn ? this.cgl.kK() : this.cgl.kJ();
                    int n2 = this.cgq;
                    int n3 = this.cgp;
                    this.cgp = 200;
                    this.cgq = 250;
                    avE avE3 = this.a(l5, (float)this.cgl.kL() / 100.0f, true);
                    this.cgp = n3;
                    this.cgq = n2;
                    if (avE3 != null) {
                        avE3.aK(l4);
                    }
                    this.cgo = false;
                    break;
                }
                case aze: {
                    break;
                }
                case azf: {
                    if (this.cge == null) break;
                    short s = ((hD)this.cge.get(this.cgf)).kM();
                    this.cgl = null;
                    this.cgc = xc_1.azg;
                    this.cgi = s * 1000;
                    this.cgj = 0;
                    break;
                }
                case azg: {
                    if (this.cge == null) break;
                    this.cgj = (int)((long)this.cgj + (l2 - this.cgk));
                    if (this.cgj <= this.cgi) break;
                    this.cgj = 0;
                    this.cgi = 0;
                    ++this.cgf;
                    if (this.cgf == this.cge.size()) {
                        this.cge = null;
                        this.cgc = xc_1.azb;
                        break;
                    }
                    this.cgl = (hD)this.cge.get(this.cgf);
                    long l6 = this.cgn ? this.cgl.kK() : this.cgl.kJ();
                    this.a(l6, (float)this.cgl.kL() / 100.0f, true);
                    this.cgg = this.cgl.getDuration() * 1000;
                    this.cgh = 0;
                    this.cgc = xc_1.azd;
                    break;
                }
                case azh: {
                    break;
                }
            }
            this.cgk = l2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void stop() {
        Object object = this.cgr;
        synchronized (object) {
            if (this.cfY != null) {
                this.bAT.a(this.cfY);
                this.cfY = null;
            }
            if (this.cfZ != null) {
                this.bAT.a(this.cfZ);
                this.cfZ = null;
            }
            if (this.cga != null) {
                this.bAT.a(this.cga);
                this.cga = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void c(avE avE2) {
        Object object = this.cgr;
        synchronized (object) {
            if (this.cfY == avE2) {
                this.bAT.a(this.cfY);
                this.cfY = null;
            }
            if (this.cfZ == avE2) {
                this.bAT.a(this.cfZ);
                this.cfZ = null;
            }
            if (this.cga == avE2) {
                this.bAT.a(this.cga);
                this.cga = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void pause() {
        Object object = this.cgr;
        synchronized (object) {
            if (this.cfY != null) {
                this.cfY.stop();
            }
            if (this.cfZ != null) {
                this.cfZ.stop();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void restart() {
        Object object = this.cgr;
        synchronized (object) {
            if (this.cfY != null) {
                try {
                    this.cfY.play();
                }
                catch (Exception exception) {
                    a.warn((Object)"Impossible de red\u00e9marrer la musique");
                }
            }
            if (this.cfZ != null) {
                try {
                    this.cfZ.play();
                }
                catch (Exception exception) {
                    a.warn((Object)"Impossible de red\u00e9marrer la musique");
                }
            }
        }
    }

    public boolean cL(int n2) {
        assert (false) : "On peut pas appliquer de reverb sur de la musique";
        return true;
    }

    public Collection uZ() {
        assert (false) : "Ne doit pas etre appel\u00e9";
        return null;
    }

    public avE a(auk auk2, boolean bl2, boolean bl3, boolean bl4, long l2) {
        assert (false) : "Ne doit pas \u00eatre appel\u00e9";
        return null;
    }

    public void b(avE avE2) {
        assert (false) : "Ne doit pas \u00eatre appel\u00e9";
    }

    public final synchronized void G(float f) {
        if (this.cfY != null) {
            this.cfY.aj(this.getGain());
        }
        if (this.cfZ != null) {
            this.cfZ.aj(this.getGain());
        }
        if (this.cga != null) {
            this.cga.aj(this.getGain());
        }
    }

    public final void n(float f, float f2) {
    }

    public final void o(float f, float f2) {
    }

    public final synchronized void c(boolean bl2, boolean bl3) {
        if (this.cfY != null) {
            this.cfY.setMute(bl3);
        }
        if (this.cfZ != null) {
            this.cfZ.setMute(bl3);
        }
        if (this.cga != null) {
            this.cga.setMute(bl3);
        }
    }

    public final xc_1 apf() {
        return this.cgc;
    }

    public final synchronized void s(ArrayList arrayList) {
        if (this.cge == arrayList) {
            return;
        }
        xc_1 xc_12 = arrayList == null ? xc_1.azb : xc_1.azc;
        if (this.cgc == xc_1.azh) {
            this.cgd = xc_12;
        } else {
            this.cgc = xc_12;
        }
        if (this.cgc == xc_1.azb && this.cfY != null) {
            this.cfY.j(0.0f, this.cgq);
            this.cfY.et(true);
        }
        this.cge = arrayList;
    }

    public final synchronized void aI(float f) {
        if (this.cfY != null) {
            this.cfY.j(0.0f, f);
            this.cfY.et(true);
        }
        if (this.cfZ != null) {
            this.cfZ.j(0.0f, f);
            this.cfZ.et(true);
        }
        if (this.cgb != null) {
            this.cgb.j(0.0f, f);
            this.cgb.et(true);
        }
        if (this.cga != null) {
            this.bAT.a(this.cga);
            this.cga = null;
        }
    }

    public final synchronized void apg() {
        this.aI(this.cgq);
    }

    private avE a(long l2, float f, boolean bl2) {
        if (this.bAU != null) {
            auk auk2;
            try {
                auk2 = this.bAU.aJ(l2);
            }
            catch (IOException iOException) {
                a.error((Object)("Impossible de charger le son d'id " + l2));
                return null;
            }
            if (auk2 != null) {
                return this.a(auk2, f, bl2);
            }
        } else {
            a.error((Object)"AudioResourceHelper non sp\u00e9cifi\u00e9.");
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private avE a(auk auk2, float f, boolean bl2) {
        Object object = this.cgr;
        synchronized (object) {
            avE avE2;
            if (this.cfY != null && this.cfY.aIX() && this.cfY.getGain() > 0.0f) {
                if (this.cfY.aIO().getDescription().equals(auk2.getDescription())) {
                    return this.cfY;
                }
            } else if (this.cfZ != null && this.cfZ.aIX() && this.cfZ.getGain() > 0.0f && this.cfZ.aIO().getDescription().equals(auk2.getDescription())) {
                return this.cfZ;
            }
            if ((avE2 = this.a(auk2, -1L)) != null) {
                avE2.eq(true);
                avE2.setMaxGain(f);
                avE2.aj(this.getGain());
                this.d(avE2);
                if (!bl2) {
                    this.aph();
                }
            }
            return avE2;
        }
    }

    private void d(avE avE2) {
        if (avE2 == null) {
            return;
        }
        if (this.cfZ == null) {
            if (this.cfY != null) {
                this.cfY.j(0.0f, this.cgq);
                this.cfY.et(true);
            }
        } else {
            if (this.cga != null) {
                this.bAT.a(this.cga);
            }
            this.cga = avE2;
            return;
        }
        this.cfZ = this.cfY;
        this.cfY = avE2;
        avE2.setMute(this.abg());
        try {
            avE2.setGain(0.0f);
            avE2.play();
            avE2.j(avE2.getMaxGain(), this.cgp);
        }
        catch (Exception exception) {
            a.error((Object)"Exception lev\u00e9e durant le crossfading :", (Throwable)exception);
            this.bAT.a(avE2);
            this.cfY = null;
            this.cga = null;
            this.cfZ = null;
        }
    }

    private void aph() {
        this.cgc = xc_1.azb;
        this.cge = null;
    }
}

