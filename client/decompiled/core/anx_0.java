/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from anX
 */
public class anx_0 {
    public static final int cKp = 86;
    public static final int cKq = 43;
    public static final int cKr = 10;
    private static final int cKs = 5;
    private static final int cKt = 7;
    private static final int cKu = 35;
    private static final Logger a = Logger.getLogger(anx_0.class);
    final asz cKv = new asz();
    private final ArrayList cKw = new ArrayList();
    private final yk_2 cKx = new yk_2();
    private float vy;
    private float vz;
    private final ng_0 cKy = new ng_0(null);

    public void initialize(int n2) {
        this.cKv.cM(n2);
    }

    public void clear() {
        for (kC kC2 : this.cKv) {
            if (kC2 == null) continue;
            kC2.clear();
        }
        this.cKv.clear();
        this.cKw.clear();
        this.cKx.clear();
        ng_0.a(this.cKy);
    }

    boolean aCD() {
        return ng_0.b(this.cKy);
    }

    float HC() {
        return this.vy;
    }

    float HD() {
        return this.vz;
    }

    void a(String string, int n2, int n3, int n4, int n5, aBp aBp2) {
        int n6;
        if (aBp2 == null) {
            throw new IllegalArgumentException("Argument 5 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/ScreenWorld.loadMaps must not be null");
        }
        if (!this.cKy.j(n2 = (int)Math.ceil((double)n2 / 576.0), n4 = (int)Math.floor((double)n4 / 576.0), n3 = (int)Math.floor((double)n3 / 1024.0), n5 = (int)Math.ceil((double)n5 / 1024.0))) {
            this.cKx.a(this.vy, this.vz, 35, this.cKw);
            return;
        }
        this.vy = (float)(n3 + n5) / 2.0f;
        this.vz = (float)(n2 + n4) / 2.0f;
        this.cKw.clear();
        if (n3 <= Short.MIN_VALUE || n5 >= Short.MAX_VALUE || n4 <= Short.MIN_VALUE || n2 >= Short.MAX_VALUE) {
            a.error((Object)("on ne devrait pas \u00eatre l\u00e0!! chargement de la map: " + n2 + "," + n3));
        } else {
            short s;
            int n7;
            n6 = n5 - n3;
            if (n6 > 5) {
                n7 = (n6 - 5) / 2;
                n5 -= n7;
                n3 += n7;
            }
            if ((n7 = n2 - n4) > 7) {
                int s2 = (n7 - 7) / 2;
                n2 -= s2;
                n4 += s2;
            }
            short s3 = (short)n4;
            while (s <= n2) {
                for (short s4 = (short)n3; s4 <= n5; s4 = (short)(s4 + 1)) {
                    int n8 = ej_0.a(s4, s);
                    if (!aBp2.contains(n8)) continue;
                    kC kC2 = (kC)this.cKx.ea(n8);
                    if (kC2 == null) {
                        kC2 = this.a(string, s4, s);
                        this.cKx.put(n8, kC2);
                    }
                    this.cKw.add(kC2);
                }
                s = (short)(s + true);
            }
        }
        this.cKv.clear();
        for (n6 = this.cKw.size() - 1; n6 >= 0; --n6) {
            kC kC3 = (kC)this.cKw.get(n6);
            int n9 = ej_0.a(kC3.EL, kC3.EM);
            this.cKv.put(n9, kC3);
        }
    }

    private kC a(String string, short s, short s2) {
        kC kC2 = new kC(s, s2);
        try {
            kC2.load(string);
        }
        catch (FileNotFoundException fileNotFoundException) {
            a.error((Object)("file not found to load map (" + s + "; " + s2 + ")"));
        }
        catch (IOException iOException) {
            a.error((Object)("Unable to load map (" + s + "; " + s2 + ")"), (Throwable)iOException);
        }
        return kC2;
    }

    public void aM(String string) {
        int n2 = 0;
        int n3 = 0;
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        int n6 = this.cKv.size();
        for (kC kC2 : this.cKv) {
            if (a.isInfoEnabled()) {
                a.info((Object)("Saving map " + n5 + "/" + n6 + " : (" + kC2.EL + "; " + kC2.EM + ")"));
            }
            kC2.aM(string);
            int n7 = kC2.EK.size();
            n2 += n7;
            if (n7 > n3) {
                n3 = n7;
            }
            if (n7 < n4) {
                n4 = n7;
            }
            ++n5;
        }
        a.info((Object)("exportPath = " + string));
        a.info((Object)("Num elements = " + n2));
        a.info((Object)("Avg elements by maps = " + (float)n2 / (float)this.cKv.size()));
        a.info((Object)("Max elements by maps = " + n3));
        a.info((Object)("Min elements by maps = " + n4));
    }

    public static int bB(int n2, int n3) {
        return (int)((float)((n2 - n3) * 86) / 2.0f);
    }

    public static int J(int n2, int n3, int n4) {
        return (int)((float)(-(n2 + n3)) * 21.5f) + n4 * 10;
    }

    public void a(short s, short s2, ScreenElement screenElement) {
        int n2 = ej_0.a(s, s2);
        kC kC2 = (kC)this.cKv.get(n2);
        if (kC2 == null) {
            kC2 = new kC(s, s2);
            this.cKv.put(n2, kC2);
        }
        kC2.a(screenElement);
    }
}

