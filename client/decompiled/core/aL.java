/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.ankamagames.framework.sound.openAL.LowPassTween
 *  net.java.games.joal.AL
 *  net.java.games.joal.ALException
 *  net.java.games.joal.ALFactory
 *  net.java.games.joal.util.ALut
 *  org.apache.log4j.Logger
 *  org.apache.log4j.PropertyConfigurator
 */
import com.ankamagames.framework.sound.openAL.LowPassTween;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import net.java.games.joal.AL;
import net.java.games.joal.ALException;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public abstract class aL
extends Thread
implements in_2 {
    private static aL cQ;
    protected static final Logger a;
    protected static final boolean cR = false;
    protected static final boolean cS = false;
    protected static final int cT = 131072;
    public static final int cU = -1;
    public static final long cV = 10L;
    private final ArrayList cW = new ArrayList();
    private boolean cX = false;
    protected AL cY;
    protected static int[] cZ;
    protected static int[] da;
    protected static int[] db;
    protected static int[] dc;
    private int dd = -1;
    private int de = -1;
    private LowPassTween df = null;
    private LowPassTween dg = null;
    private final ArrayList dh = new ArrayList();
    private final Object di = new Object();
    protected aCZ dj;
    private volatile boolean dk = false;
    private volatile boolean dl = false;
    private int dm = 0;
    private int dn = 0;
    protected asz do = new asz();
    private static int dp;
    private static int dq;

    public aL() {
        aL.a(this);
    }

    protected static void a(aL aL2) {
        cQ = aL2;
    }

    public static aL bH() {
        return cQ;
    }

    public final AL bI() {
        return this.cY;
    }

    public final boolean bJ() {
        return this.dk;
    }

    public final aCZ bK() {
        return this.dj;
    }

    public void a(aCZ aCZ2) {
        this.dj = aCZ2;
    }

    public final void f(boolean bl2) {
        this.cX = bl2;
    }

    public final boolean isRunning() {
        return this.cX;
    }

    public final void check() {
        int n2 = this.cY.alGetError();
        if (n2 != 0) {
            String string = "unknown error";
            switch (n2) {
                case 40961: {
                    string = "AL_INVALID_NAME";
                    break;
                }
                case 40962: {
                    string = "AL_INVALID_ENUM";
                    break;
                }
                case 40963: {
                    string = "AL_INVALID_VALUE";
                    break;
                }
                case 40964: {
                    string = "AL_INVALID_OPERATION";
                    break;
                }
                case 40965: {
                    string = "AL_OUT_OF_MEMORY";
                }
            }
            throw new ALException("OpenAL error : " + string);
        }
    }

    private void bL() {
        int n2;
        int[] nArray = new int[64];
        for (n2 = 0; n2 < nArray.length; ++n2) {
            try {
                this.cY.alGenSources(1, nArray, n2);
                this.check();
                continue;
            }
            catch (Exception exception) {
                break;
            }
        }
        this.dm = n2;
        this.cY.alDeleteSources(n2, nArray, 0);
    }

    public final synchronized boolean initialize() {
        if (!this.dk) {
            try {
                ALut.alutInit();
                this.cY = ALFactory.getAL();
                this.bL();
                this.cY.alDistanceModel(53252);
                this.dk = true;
            }
            catch (ALException aLException) {
                a.warn((Object)"Probl?me lors de SoundManager.initialize(). Impossible d'initialiser le SoundManager");
                this.dk = false;
                this.dl = true;
                return false;
            }
        }
        return !this.cX && this.bP();
    }

    public final synchronized void start() {
        if (!this.cX && this.dk && !this.dl) {
            this.setName("SoundManager");
            super.start();
            while (!this.cX) {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException interruptedException) {
                    a.error((Object)"Interrupted");
                }
            }
        } else if (this.cX) {
            a.error((Object)"SoundManager is already running");
        } else if (this.dl) {
            a.error((Object)"SoundManager failed to initialize");
        } else {
            a.error((Object)"Initialize SoundManager first");
        }
    }

    protected void h(long l2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void bM() {
        Object object = this.di;
        synchronized (object) {
            int n2 = this.dh.size();
            for (int j = 0; j < n2; ++j) {
                ((nu_1)this.dh.get(j)).bM();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        this.cX = true;
        ArrayList<nz> arrayList = new ArrayList<nz>();
        a.info((Object)"SoundManager running");
        while (this.cX) {
            nz nz2;
            int n2;
            int n3;
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {
                a.error((Object)"Exception", (Throwable)interruptedException);
            }
            long l2 = System.currentTimeMillis();
            this.h(l2);
            Object object = this.di;
            synchronized (object) {
                n3 = this.dh.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    nz2 = (nu_1)this.dh.get(n2);
                    try {
                        if (((nu_1)nz2).abg() && !((nu_1)nz2).abi()) continue;
                        ((nu_1)nz2).ay(l2);
                        continue;
                    }
                    catch (Exception exception) {
                        a.error((Object)"Exception", (Throwable)exception);
                    }
                }
            }
            object = this.cW;
            synchronized (object) {
                n3 = this.cW.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    nz2 = (avE)this.cW.get(n2);
                    switch (((avE)nz2).dW(l2)) {
                        case 1: 
                        case 3: {
                            arrayList.add(nz2);
                        }
                    }
                }
                n3 = arrayList.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    nz2 = (avE)arrayList.get(n2);
                    this.cW.remove(nz2);
                    this.a((avE)nz2);
                }
            }
            for (int j = this.do.size() - 1; j >= 0; --j) {
                ((aag_1)this.do.jx(j)).aD(l2);
            }
        }
        this.bQ();
        a.info((Object)"SoundManager stopped");
    }

    protected abstract boolean bN();

    private oa_1 a(auk auk2) {
        if (this.bN()) {
            ans_2 ans_22 = (ans_2)abu.chZ.get(auk2.getDescription());
            if (ans_22 != null) {
                return new tv_1(ans_22);
            }
            GV gV = new GV();
            if (!gV.b(auk2)) {
                return null;
            }
            try {
                if (auk2.length() < 131072L && (ans_22 = new ans_2(gV)).initialize()) {
                    abu.chZ.put(auk2.getDescription(), ans_22);
                    return new tv_1(ans_22);
                }
            }
            catch (IOException iOException) {
                return null;
            }
            return gV;
        }
        GV gV = new GV();
        gV.b(auk2);
        return gV;
    }

    public final avE a(auk auk2, nu_1 nu_12, long l2) {
        if (!this.dk) {
            try {
                auk2.close();
            }
            catch (IOException iOException) {
                a.error((Object)("Probl\u00e8me \u00e0 la fermeture " + auk2.getDescription()));
            }
            return null;
        }
        oa_1 oa_12 = this.a(auk2);
        if (oa_12 != null) {
            avE avE2 = ahz_1.aUa().ew(l2);
            if (avE2 != null) {
                try {
                    if (avE2.a(this, nu_12, oa_12)) {
                        oa_12.zD();
                        oa_12.aB(true);
                        return avE2;
                    }
                    if (avE2.aIP()) {
                        ahz_1.aUa().e(avE2);
                    }
                    oa_12.close();
                    return null;
                }
                catch (Exception exception) {
                    a.error((Object)("Erreur durant l'initialisation de la source : " + auk2.getDescription()));
                    if (avE2.aIP()) {
                        ahz_1.aUa().e(avE2);
                    }
                    oa_12.zE();
                }
            } else {
                a.error((Object)"error : source is null");
            }
        } else {
            a.error((Object)("Unable to initialize stream from URL : " + auk2.getDescription()));
        }
        try {
            auk2.close();
        }
        catch (IOException iOException) {
            a.error((Object)("Probl\u00e8me \u00e0 la fermeture du stream de " + auk2.getDescription()));
        }
        return null;
    }

    public final void a(avE avE2) {
        if (avE2 != null) {
            if (avE2.aIX()) {
                avE2.stop();
            }
            avE2.close();
            if (avE2.aIP()) {
                ahz_1.aUa().e(avE2);
            }
        }
    }

    public boolean k(int n2) {
        if (n2 > this.dm - this.dn) {
            return false;
        }
        this.dn += n2;
        return true;
    }

    public boolean l(int n2) {
        if (this.dn < n2) {
            return false;
        }
        this.dn -= n2;
        return true;
    }

    protected int bO() {
        return this.dn;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(nu_1 nu_12) {
        if (nu_12 == null) {
            return;
        }
        Object object = this.di;
        synchronized (object) {
            if (!this.dh.contains(nu_12)) {
                this.dh.add(nu_12);
                nu_12.c(this);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void b(nu_1 nu_12) {
        if (nu_12 == null) {
            return;
        }
        Object object = this.di;
        synchronized (object) {
            try {
                this.dh.remove(nu_12);
                nu_12.stop();
                nu_12.c((aL)null);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final nu_1 n(String string) {
        if (string == null) {
            return null;
        }
        Object object = this.di;
        synchronized (object) {
            for (nu_1 nu_12 : this.dh) {
                if (!nu_12.getName().equals(string)) continue;
                return nu_12;
            }
        }
        return null;
    }

    protected abstract boolean bP();

    protected abstract void bQ();

    public static void main(String[] stringArray) {
        int n2 = 10;
        PropertyConfigurator.configure((URL)aL.class.getResource("log4j.properties"));
        fg fg2 = new fg();
        if (!fg2.initialize()) {
            a.error((Object)"SoundManager initialization failed");
            return;
        }
        fg2.start();
        awb awb2 = new awb("sounds");
        awb2.setMute(false);
        awb2.setMaxGain(1.0f);
        fg2.a(awb2);
        awb awb3 = new awb("sounds2", 2);
        awb3.setMute(false);
        awb3.setMaxGain(1.0f);
        fg2.a(awb3);
        File file = new File("C:\\assets.audio.svn\\export\\APS");
        File[] fileArray = file.listFiles(new fi());
        if (fileArray == null || fileArray.length == 0) {
            System.out.println("Pas de fichiers son");
            System.exit(-1);
        }
        File file2 = fileArray[0];
        for (int j = 0; j < 35; ++j) {
            try {
                awb3.a(new ae_1(file2.toURI().toURL()), false, false, true, -1L);
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Failed to play sound : ", (Throwable)exception);
            }
        }
        File file3 = fileArray[1];
        for (int j = 0; j < 35; ++j) {
            try {
                awb2.a(new ae_1(file3.toURI().toURL()), false, false, true, -1L);
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Failed to play sound : ", (Throwable)exception);
            }
        }
    }

    public int bR() {
        return da[0];
    }

    public abstract or_1 a(amj_1 var1, int var2, int var3, int var4);

    public void m(int n2) {
        block9: {
            if (!this.isRunning()) {
                return;
            }
            if (!Mf.btd.a(amA.cHG)) {
                return;
            }
            if (this.dd == n2) {
                return;
            }
            this.dd = n2;
            zu_1 zu_12 = this.r(this.dd);
            if (da[0] != -1) {
                this.cY.alDeleteAuxiliaryEffectSlots(1, da, 0);
                aL.da[0] = -1;
            }
            if (cZ[0] != -1) {
                this.cY.alDeleteEffects(1, cZ, 0);
                aL.cZ[0] = -1;
            }
            if (zu_12 != null) {
                try {
                    this.cY.alGenAuxiliaryEffectSlots(1, da, 0);
                    this.cY.alGenEffects(1, cZ, 0);
                    this.cY.alEffecti(cZ[0], 32769, 1);
                    this.cY.alEffectf(cZ[0], 3, zu_12.GV());
                    this.cY.alEffectf(cZ[0], 5, zu_12.GW());
                    this.cY.alEffectf(cZ[0], 6, zu_12.GX());
                    this.cY.alEffectf(cZ[0], 9, zu_12.GY());
                    this.cY.alEffectf(cZ[0], 10, zu_12.GZ());
                    this.cY.alAuxiliaryEffectSloti(da[0], 1, cZ[0]);
                }
                catch (Exception exception) {
                    if (da[0] != -1) {
                        this.cY.alDeleteAuxiliaryEffectSlots(1, da, 0);
                        aL.da[0] = -1;
                    }
                    if (cZ[0] == -1) break block9;
                    this.cY.alDeleteEffects(1, cZ, 0);
                    aL.cZ[0] = -1;
                }
            }
        }
        this.s(da[0]);
    }

    public void n(int n2) {
        if (!this.isRunning()) {
            return;
        }
        if (this.de == n2) {
            return;
        }
        this.de = n2;
    }

    public void a(int n2, long l2) {
        if (!Mf.btd.a(amA.cHH)) {
            return;
        }
        aag_1 aag_12 = (aag_1)this.do.get(n2);
        if (aag_12 == null) {
            ald_0 ald_02 = this.q(this.de);
            if (ald_02 == null) {
                return;
            }
            aag_12 = new aag_1(ald_02.getGain(), ald_02.aWs(), n2, this, this.i(n2));
            try {
                aag_12.aMS();
            }
            catch (ALException aLException) {
                return;
            }
            this.do.put(n2, aag_12);
        }
        aag_12.ep(l2);
    }

    protected boolean i(long l2) {
        return true;
    }

    public void b(int n2, long l2) {
        if (!Mf.btd.a(amA.cHH)) {
            return;
        }
        aag_1 aag_12 = (aag_1)this.do.get(n2);
        if (aag_12 != null) {
            aag_12.eq(l2);
        }
    }

    public void o(int n2) {
        aag_1 aag_12 = (aag_1)this.do.remove(n2);
        if (aag_12 != null) {
            aag_12.aMT();
        }
    }

    public abstract ain_1 p(int var1);

    protected abstract ald_0 q(int var1);

    protected abstract zu_1 r(int var1);

    protected abstract void s(int var1);

    static {
        a = Logger.getLogger(aL.class);
        cZ = new int[]{-1};
        da = new int[]{-1};
        db = new int[]{0};
        dc = new int[]{0};
        dp = 0;
        dq = 0;
    }
}

