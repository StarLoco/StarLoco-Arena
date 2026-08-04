/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import org.apache.log4j.Logger;

/*
 * Renamed from aDd
 */
public class add_1
implements amn_1,
anf_2,
ayy_0 {
    private static Logger a = Logger.getLogger(add_1.class);
    private static add_1 dvx = new add_1();
    public static final boolean dvy = false;
    public static final long dvz = 0L;
    public static final long dvA = 1L;
    public static final long dvB = 2L;
    public static final long dvC = 4L;
    public static final long dvD = 8L;
    public static final long dvE = 16L;
    public static final long dvF = 32L;
    public static final long dvG = 64L;
    public static final long dvH = 128L;
    public static final long dvI = 256L;
    public static final long dvJ = 512L;
    public static final long dvK = 1024L;
    public static final long dvL = 2048L;
    public static final long dvM = 4096L;
    public static final long dvN = 8192L;
    public static final long dvO = 16384L;
    public static final long dvP = 32768L;
    public static final long dvQ = 65536L;
    public static final int dvR = 0;
    public static final int dvS = 1;
    public static final int dvT = 25999;
    public static final int dvU = 26999;
    public static final int dvV = 27000;
    public static final int dvW = 29999;
    private hs_1 dvX = null;
    private agV dvY;
    private final DS dvZ = new DS();
    private final azs_0 dwa = azs_0.aLV();
    private final afq_1 cAN = new afq_1();
    private final afq_1 dwb = new afq_1();
    private final dq_0 dwc = new dq_0();
    private wa_0 dwd = null;
    private awo_0 dwe = null;
    private akr_2 dwf = null;
    private Tm dwg;
    public static final int dwh = Integer.MAX_VALUE;
    public static final int dwi = Integer.MIN_VALUE;
    private final ArrayList dwj = new ArrayList();
    private final ArrayList dwk = new ArrayList();
    private final ano_0 dwl = new ano_0();
    private final HashMap dwm;
    private URL bld = null;
    private boolean dwn = false;
    private int dwo = 0;
    private URL dwp = null;
    private Class dwq = null;
    private int dwr = 0;
    private URL dws = null;
    private Class dwt = null;
    private awC aCo = null;
    private int dwu = 0;
    private URL dwv = null;
    private Class dww = null;
    private ahz dwx = null;
    private boolean dwy = true;
    public String dwz;
    public String cFE;
    private final ArrayList dwA = new ArrayList();
    private final ArrayList dwB = new ArrayList();
    private final ArrayList dwC = new ArrayList();
    private final ArrayList dwD = new ArrayList();
    private final ArrayList dwE = new ArrayList();
    private final ArrayList dwF = new ArrayList();
    private final ArrayList dwG = new ArrayList();
    private final Stack dwH = new Stack();
    private final ArrayList dwI = new ArrayList();
    private vP dwJ = new vP(0, 0, 0, 0);
    private int IP;
    private boolean dwK = true;
    private amy_0 dwL;

    public static add_1 aOG() {
        return dvx;
    }

    private add_1() {
        this.dwm = new HashMap();
        this.l("xulor", qu_0.class);
    }

    public void kz(String string) {
        this.dwc.f(string, 1);
    }

    public void F(String string, int n2) {
        this.dwc.f(string, n2);
    }

    public void kA(String string) {
        this.dwc.Q(string);
    }

    public void aOH() {
        this.dwc.gE();
    }

    public void a(axq_0 axq_02) {
        if (axq_02 != null && !this.dwD.contains(axq_02)) {
            this.dwD.add(axq_02);
        }
    }

    public void b(axq_0 axq_02) {
        this.dwE.add(axq_02);
    }

    public void a(yr_0 yr_02) {
        if (yr_02 != null && !this.dwB.contains(yr_02)) {
            this.dwB.add(yr_02);
        }
    }

    public void b(yr_0 yr_02) {
        this.dwC.add(yr_02);
    }

    public void a(zh_0 zh_02) {
        if (zh_02 != null && !this.dwF.contains(zh_02)) {
            this.dwF.add(zh_02);
        }
    }

    public void b(zh_0 zh_02) {
        this.dwG.add(zh_02);
    }

    public void kB(String string) {
        if (string != null && !this.dwI.contains(string)) {
            this.dwI.add(string);
        }
    }

    public void kC(String string) {
        this.dwI.remove(string);
    }

    public void aOI() {
        this.dwI.clear();
    }

    public vP aOJ() {
        return this.dwJ;
    }

    public void f(vP vP2) {
        this.dwJ = vP2;
    }

    public awo_0 aOK() {
        return this.dwe;
    }

    public void a(awo_0 awo_02) {
        this.dwe = awo_02;
    }

    public void a(wa_0 wa_02) {
        this.dwd = wa_02;
    }

    public wa_0 aOL() {
        return this.dwd;
    }

    public void a(Tm tm) {
        this.dwg = tm;
    }

    public Tm aOM() {
        return this.dwg;
    }

    public DS yh() {
        return this.dvZ;
    }

    public afq_1 azj() {
        return this.cAN;
    }

    public void b(hs_1 hs_12) {
        this.dvX = hs_12;
    }

    public hs_1 YN() {
        return this.dvX;
    }

    public agV aON() {
        return this.dvY;
    }

    public void a(agV agV2) {
        this.dvY = agV2;
    }

    public amy_0 aOO() {
        return this.dwL;
    }

    public void a(amy_0 amy_02) {
        this.dwL = amy_02;
        this.aOP();
    }

    private void aOP() {
        abk_0 abk_02 = sr_0.zg();
        if (abk_02 != null) {
            if (abk_02.contains("tooltipsDisplay")) {
                this.dwy = abk_02.getBoolean("tooltipsDisplay");
            }
            if (abk_02.contains("tooltipsDuration")) {
                oz_0.hc(abk_02.getInt("tooltipsDuration"));
            }
        }
    }

    public abk_0 kD(String string) {
        return this.dwL.fd(string);
    }

    public void a(ajw ajw2) {
        String string = ajw2.getPropertyName();
        if (string.equalsIgnoreCase("tooltipsDisplay")) {
            this.dwy = (Boolean)ajw2.getNewValue();
        } else if (string.equalsIgnoreCase("tooltipsDuration")) {
            oz_0.hc((Integer)ajw2.getNewValue());
        }
    }

    public String kE(String string) {
        if (this.dwf != null) {
            return this.dwf.getString(string);
        }
        return string;
    }

    public void a(akr_2 akr_22) {
        this.dwf = akr_22;
    }

    public String aOQ() {
        StringBuilder stringBuilder = new StringBuilder("# XULOR INFOS #");
        stringBuilder.append('\n').append("- loadedElementCount = ").append(this.dwl.size());
        stringBuilder.append('\n').append("- loadedElements = \n");
        if (!this.dwl.isEmpty()) {
            this.dwl.b(new ss_1(this, stringBuilder));
        }
        return stringBuilder.toString();
    }

    public URL VJ() {
        return this.bld;
    }

    public void g(URL uRL) {
        this.bld = uRL;
    }

    public boolean kF(String string) {
        return this.dwm.containsKey(string);
    }

    public void l(String string, Class clazz) {
        this.dwm.put(string, clazz);
    }

    public void kG(String string) {
        this.dwm.remove(string);
    }

    public void aOR() {
        this.dwm.clear();
        this.l("xulor", qu_0.class);
    }

    public Class aOS() {
        return this.kH("xulor");
    }

    public Class kH(String string) {
        if (string == null) {
            return (Class)this.dwm.get("xulor");
        }
        if (!this.dwm.containsKey(string)) {
            a.error((Object)("Le package " + string + " est inconnue !"));
            return null;
        }
        return (Class)this.dwm.get(string);
    }

    public void kI(String string) {
        try {
            this.dwq = Class.forName(string);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            this.dwq = null;
            try {
                this.dwp = new URL(string);
                return;
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)("Le chemin '" + string + "' vers le fichier de d\u00e9finition des messageBox est invalide !"));
                this.dwp = null;
                return;
            }
        }
    }

    public r_0 kJ(String string) {
        return this.a(string, " ", 2L, 0, 0);
    }

    public r_0 h(String string, long l2) {
        return this.a(string, null, l2, 0, 0);
    }

    public r_0 a(String string, String string2, long l2) {
        return this.a(string, string2, null, l2, 0, 0);
    }

    public r_0 f(String string, int n2, int n3) {
        return this.a(string, " ", null, 2L, n2, n3);
    }

    public r_0 a(String string, long l2, int n2, int n3) {
        return this.a(string, " ", null, l2, n2, n3);
    }

    public r_0 a(String string, String string2, long l2, int n2, int n3) {
        return this.a(string, " ", string2, l2, n2, n3);
    }

    public r_0 a(String string, String string2, String string3, long l2, int n2, int n3) {
        return Wq.ajf().b(new afP(n2, n3, string, string2, string3, l2));
    }

    public r_0 a(String string, String string2, String string3, long l2) {
        r_0 r_02 = null;
        if (this.dwp != null || this.dwq != null) {
            String string4 = "MessageBox_" + this.dwo++;
            if (this.dwo > 0x7FFFFFFE) {
                this.dwo = 0;
            }
            r_02 = new r_0(string4);
            this.a(string, string2, r_02, string3, l2);
        }
        return r_02;
    }

    private void a(String string, String string2, r_0 r_02, String string3, long l2) {
        try {
            if (this.dwp != null || this.dwq != null) {
                aji_1 aji_12 = this.cAN.lf(r_02.x());
                this.cAN.d(aji_12);
                na_1 na_12 = null;
                if (this.dwp != null) {
                    na_12 = this.a(this.dwp, this.cAN, aji_12, false, null, null, null);
                } else if (this.dwq != null) {
                    na_12 = this.a(this.dwq, this.cAN, aji_12);
                }
                if (na_12 != null && na_12 instanceof aab_2) {
                    na_12.setElementMapRoot(true);
                    aab_2 aab_22 = (aab_2)na_12;
                    aab_22.setId(r_02.x());
                    aab_22.setModalLevel(amY.cIf);
                    this.a(aab_22.getId(), aab_22, ago_2.getInstance().getLayeredContainer(), 26000, 256L);
                    tA.a(aab_22, r_02, string, string2, string3, l2);
                    this.e(r_02);
                    aek.atD().atL();
                }
                this.dwn = true;
            }
        }
        catch (Exception exception) {
            a.error((Object)"Erreur lors du chargement de la messageBox", (Throwable)exception);
        }
    }

    public void kK(String string) {
        try {
            this.dwt = Class.forName(string);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            this.dwt = null;
            try {
                this.dws = new URL(string);
                return;
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)("Le chemin '" + string + "' vers le fichier de d\u00e9finition des popupMenu est invalide !"));
                this.dws = null;
                return;
            }
        }
    }

    public awC aOT() {
        awC awC2 = null;
        try {
            if (this.dws != null || this.dwt != null) {
                String string = "PopupMenu_" + this.dwr++;
                if (this.dwr > 0x7FFFFFFE) {
                    this.dwr = 0;
                }
                aji_1 aji_12 = this.cAN.lf(string);
                this.cAN.d(aji_12);
                na_1 na_12 = null;
                if (this.dws != null) {
                    na_12 = this.a(this.dws, this.cAN, aji_12, false, null, null, null);
                } else if (this.dwt != null) {
                    na_12 = this.a(this.dwt, this.cAN, aji_12);
                }
                if (na_12 != null && na_12 instanceof awC) {
                    na_12.setElementMapRoot(true);
                    awC2 = (awC)na_12;
                    awC2.setId(string);
                    awC2.setModalLevel(amY.cIg);
                    awC2.setVisible(false);
                    this.aON().getMasterRootContainer().getLayeredContainer().a(awC2, 30000);
                    this.dwl.put(string, awC2);
                }
                this.dwn = true;
            }
        }
        catch (Exception exception) {
            a.error((Object)exception.getMessage());
        }
        return awC2;
    }

    public void e(awC awC2) {
        this.aOU();
        this.aCo = awC2;
        awC2.show();
    }

    public void a(awC awC2, int n2, int n3) {
        this.aOU();
        this.aCo = awC2;
        awC2.br(n2, n3);
    }

    public void aOU() {
        if (this.aCo != null) {
            this.kO(this.aCo.getId());
        }
    }

    public void kL(String string) {
        try {
            this.dww = Class.forName(string);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            this.dww = null;
            try {
                this.dwv = new URL(string);
                return;
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)("Le chemin '" + string + "' vers le fichier de d\u00e9finition des mrus est invalide !"));
                this.dwv = null;
                return;
            }
        }
    }

    public void aOV() {
        ArrayList arrayList = new ArrayList();
        if (!this.dwl.isEmpty()) {
            this.dwl.a(new sc_2(this, arrayList));
        }
        for (na_1 na_12 : arrayList) {
            this.kO(na_12.getElementMap().getId());
        }
    }

    public ahz aOW() {
        ahz ahz2 = null;
        try {
            if (this.dwv != null || this.dww != null) {
                Object object;
                String string = "MRU";
                adg_2 adg_22 = (adg_2)this.dwl.get(string);
                if (adg_22 != null) {
                    object = new anh_1(adg_22);
                    ((ke)object).b();
                    adg_22.f((ke)object);
                    this.kO(string);
                }
                object = this.cAN.lf(string);
                this.cAN.d((aji_1)object);
                na_1 na_12 = null;
                if (this.dwv != null) {
                    na_12 = this.a(this.dwv, this.cAN, (aji_1)object, false, null, null, null);
                } else if (this.dww != null) {
                    na_12 = this.a(this.dww, this.cAN, (aji_1)object);
                }
                if (na_12 != null && na_12 instanceof ahz) {
                    na_12.setElementMapRoot(true);
                    ahz2 = (ahz)na_12;
                    ahz2.setId(string);
                    ahz2.setModalLevel(amY.cIg);
                    ahz2.setVisible(false);
                    this.aON().getMasterRootContainer().getLayeredContainer().a(ahz2, -39999);
                    this.dwl.put(string, ahz2);
                }
                this.dwn = true;
            }
        }
        catch (Exception exception) {
            a.error((Object)exception.getMessage());
        }
        return ahz2;
    }

    public void i(ahz ahz2) {
        this.aOX();
        this.dwx = ahz2;
        ahz2.show();
    }

    public void a(ahz ahz2, int n2, int n3) {
        this.aOX();
        this.dwx = ahz2;
        agV agV2 = add_1.aOG().aON();
        if (agV2.isScaled()) {
            n2 = agV2.kF(n2);
            n3 = agV2.kG(n3);
        }
        ahz2.br(n2, n3);
    }

    public void aOX() {
        if (this.dwx != null && !this.dwx.isUnloading()) {
            this.kO(this.dwx.getElementMap().getId());
        }
    }

    public boolean aOY() {
        return this.dwy;
    }

    public na_1 kM(String string) {
        return (na_1)this.dwl.get(string);
    }

    public void a(aea_2 aea_22) {
        if (this.dvY != null) {
            // empty if block
        }
    }

    public void aOZ() {
        if (this.dvY != null && this.dwy) {
            this.dvY.awF().setVisible(true);
        }
    }

    public void kN(String string) {
        int n2 = 0;
        int n3 = 0;
        if (this.dvY != null) {
            n2 = awS.aJG().getX();
            n3 = awS.aJG().getY();
        }
        this.g(string, n2, n3);
    }

    public void g(String string, int n2, int n3) {
        this.b(string, n2, n3, 3000);
    }

    public void b(String string, int n2, int n3, int n4) {
        this.a(string, n2, n3, n4, 0, 0);
    }

    public void a(String string, int n2, int n3, int n4, int n5, int n6) {
        if (this.dvY != null && this.dwy) {
            oz_0 oz_02 = this.dvY.awF();
            oz_02.r(n2 -= (int)(this.dvY.adF() / 2.0f), n3 += (int)(this.dvY.adG() / 2.0f));
            oz_02.setOffset(n5, n6);
            oz_02.setDuration(n4);
            oz_02.setText(string);
            oz_02.setVisible(true);
        }
    }

    public void aPa() {
        if (this.dvY != null) {
            this.dvY.awF().setVisible(false);
        }
    }

    public void a(String string, aFy aFy2) {
        this.dwj.add(new j_0(string, aFy2));
    }

    public void a(String string, String string2, auh auh2) {
        this.dvZ.a(new URL(string), string2, auh2);
    }

    public void b(lw_0 lw_02, afn_2 afn_22, String string) {
        this.dvZ.a(lw_02, afn_22, string);
    }

    public void ap(String string, String string2) {
        try {
            this.dvZ.b(new URL(string), string2);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors du chargement du Theme : " + exception.getMessage()));
        }
    }

    public void aLI() {
        agx_2.aTc().aTb();
        this.dvZ.bg(true);
    }

    public na_1 a(String string, String string2, short s) {
        return this.a(string, string2, 0L, s);
    }

    public na_1 a(String string, String string2, String string3, boolean bl2, short s) {
        return this.a(string, string2, string3, bl2, 0L, s);
    }

    public na_1 a(String string, String string2, String string3, boolean bl2, int n2, int n3, short s) {
        return this.a(string, string2, string3, bl2, n2, n3, 0L, s);
    }

    public na_1 a(String string, String string2, int n2, short s) {
        return this.a(string, string2, n2, 0L, s);
    }

    public na_1 a(String string, String string2, int n2, String string3, boolean bl2, short s) {
        return this.a(string, string2, n2, string3, bl2, 0L, s);
    }

    public na_1 a(String string, String string2, int n2, String string3, boolean bl2, int n3, int n4, short s) {
        return this.a(string, string2, n2, string3, bl2, n3, n4, 0L, s);
    }

    public na_1 a(String string, String string2, long l2, short s) {
        return this.a(string, string2, Integer.MAX_VALUE, l2, s);
    }

    public na_1 a(String string, String string2, String string3, boolean bl2, long l2, short s) {
        return this.a(string, string2, Integer.MAX_VALUE, string3, bl2, l2, s);
    }

    public na_1 a(String string, String string2, String string3, boolean bl2, int n2, int n3, long l2, short s) {
        return this.a(string, string2, Integer.MAX_VALUE, string3, bl2, n2, n3, l2, s);
    }

    public na_1 a(String string, String string2, int n2, long l2, short s) {
        return this.a(string, string2, null, null, null, null, null, false, false, 0, 0, null, n2, l2, s);
    }

    public na_1 a(String string, String string2, String string3, String string4, String string5, long l2, short s) {
        return this.a(string, string2, null, string3, string4, null, string5, false, false, 0, 0, null, Integer.MAX_VALUE, l2, s);
    }

    public na_1 a(String string, String string2, int n2, String string3, boolean bl2, long l2, short s) {
        return this.a(string, string2, null, null, null, string3, null, bl2, false, 0, 0, null, n2, l2, s);
    }

    public na_1 a(String string, String string2, int n2, String string3, boolean bl2, int n3, int n4, long l2, short s) {
        return this.a(string, string2, null, null, null, string3, null, bl2, true, n3, n4, null, n2, l2, s);
    }

    public na_1 a(String string, na_1 na_12, String string2, String string3, String string4, aji_1 aji_12, long l2, short s) {
        return this.a(null, string, na_12, string2, string3, null, string4, false, false, 0, 0, aji_12, Integer.MAX_VALUE, l2, s);
    }

    public na_1 a(String string, na_1 na_12, String string2, boolean bl2, aji_1 aji_12, long l2, short s) {
        return this.a(null, string, na_12, null, null, string2, null, bl2, false, 0, 0, aji_12, Integer.MAX_VALUE, l2, s);
    }

    public na_1 a(String string, na_1 na_12, String string2, boolean bl2, int n2, int n3, aji_1 aji_12, long l2, short s) {
        return this.a(null, string, na_12, null, null, string2, null, bl2, true, n2, n3, aji_12, Integer.MAX_VALUE, l2, s);
    }

    public na_1 a(String string, String string2, na_1 na_12, String string3, String string4, String string5, String string6, boolean bl2, boolean bl3, int n2, int n3, aji_1 aji_12, int n4, long l2, short s) {
        na_1 na_13 = this.dwc.R(string2);
        URL uRL = null;
        Class<?> clazz = null;
        if (na_13 == null) {
            try {
                clazz = Class.forName(string2);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            if (clazz == null) {
                try {
                    uRL = new URL(string2);
                }
                catch (MalformedURLException malformedURLException) {
                    // empty catch block
                }
                if (uRL == null) {
                    try {
                        uRL = an_2.a(this.bld, string2);
                    }
                    catch (MalformedURLException malformedURLException) {
                        // empty catch block
                    }
                }
                if (uRL != null) {
                    this.bld = uRL;
                }
            }
            if (uRL == null && clazz == null) {
                a.error((Object)("impossible de charger le dialog correspondant \u00e0 cette ressource : " + string2));
            }
        }
        na_1 na_14 = null;
        na_14 = this.a(string, na_13, uRL, clazz, na_12, string3, string4, string5, string6, bl2, bl3, n2, n3, aji_12, this.bld, l2, s);
        if (n4 != Integer.MAX_VALUE) {
            this.dwj.add(new adb(string, n4, 0L));
        }
        return na_14;
    }

    /*
     * WARNING - void declaration
     */
    private na_1 a(String string, na_1 na_12, URL uRL, Class clazz, na_1 jG, String string2, String string3, String string4, String string5, boolean bl2, boolean bl3, int n2, int n3, aji_1 aji_12, URL uRL2, long l2, short s) {
        Object object;
        boolean bl4;
        if (this.dwI.contains(string)) {
            void var21_19;
            int bl42 = this.dwB.size() - 1;
            while (var21_19 >= 0) {
                ((yr_0)this.dwB.get((int)var21_19)).cZ(string);
                --var21_19;
            }
        }
        if ((l2 & 0x10L) == 16L) {
            this.kP(string);
        }
        boolean bl5 = false;
        if (na_12 != null && aji_12 == null && (aji_12 = na_12.getElementMap()) != null) {
            aji_12.io(string);
        }
        if (string != null && aji_12 == null && !this.dwl.containsKey(string)) {
            aji_12 = this.cAN.lf(string);
            bl4 = true;
        }
        if (aji_12 != null) {
            try {
                aji_1 aji_13 = this.cAN.aRR();
                this.cAN.d(aji_12);
                object = this.bld;
                this.bld = uRL2;
                if (na_12 == null) {
                    if (uRL != null) {
                        na_12 = this.a(uRL, this.cAN, aji_12, false, null, null, null);
                    } else if (clazz != null) {
                        na_12 = this.a(clazz, this.cAN, aji_12);
                    } else {
                        return null;
                    }
                }
                if ((l2 & 0x40L) != 64L) {
                    JG jG2;
                    aht_1 aht_12 = null;
                    if (jG instanceof aht_1) {
                        aht_12 = (aht_1)jG;
                    } else if (na_12 instanceof adg_2) {
                        aht_12 = this.aON().getMasterRootContainer().getLayeredContainer();
                    }
                    if (aht_12 != null && (l2 & 0x1000L) == 4096L) {
                        if (na_12 instanceof adg_2) {
                            adg_2 adg_22 = (adg_2)na_12;
                            jG2 = aht_12.getLayoutManager();
                            if (jG2.isStandAlone()) {
                                jG2.a(aht_12, adg_22);
                            }
                        }
                    } else if (jG == null && na_12 instanceof adg_2) {
                        int n4;
                        jG2 = this.aON().getMasterRootContainer().getLayeredContainer();
                        if ((l2 & 0x100L) == 256L) {
                            n4 = 26000;
                            if (this.dwe != null) {
                                this.dwe.aJz();
                            }
                        } else {
                            n4 = (l2 & 0x10000L) == 65536L ? -40000 : ((l2 & 1L) == 1L ? 1 : ((l2 & 4L) == 4L ? 27000 : ((l2 & 2L) == 2L ? 26999 : ((l2 & 8L) == 8L ? 29999 : 0))));
                        }
                        ((eq_0)jG2).a((adg_2)na_12, n4);
                        jG = jG2;
                    } else if (jG != null) {
                        ((air_1)jG).f(na_12);
                    }
                }
                na_12.setElementMapRoot(bl4);
                if ((l2 & 0x100L) == 256L) {
                    amY.aBW().q(na_12);
                    this.dwA.add(string);
                }
                if ((l2 & 0x200L) == 512L) {
                    amY.aBW().p(na_12);
                    this.dwA.add(string);
                }
                this.bld = object;
                this.cAN.d(aji_13);
            }
            catch (Exception exception) {
                a.error((Object)("Le chargement de " + uRL + " a \u00e9chou\u00e9"), (Throwable)exception);
            }
            this.a(na_12, string, (na_1)jG, string2, string3, string4, string5, bl2, bl3, n2, n3, aji_12, uRL2, l2, s);
        }
        for (int j = this.dwF.size() - 1; j >= 0; --j) {
            object = (zh_0)this.dwF.get(j);
            if (this.dwG.contains(object)) continue;
            object.cZ(string);
        }
        return na_12;
    }

    private void a(na_1 na_12, String string, na_1 na_13, String string2, String string3, String string4, String string5, boolean bl2, boolean bl3, int n2, int n3, aji_1 aji_12, URL uRL, long l2, short s) {
        if (na_12 != null) {
            na_1 na_14;
            Object object;
            if (string != null) {
                this.dwl.put(string, na_12);
            }
            if ((l2 & 0x20L) != 32L) {
                this.dwn = true;
            }
            vf_0 vf_02 = null;
            if ((l2 & 0x400L) == 1024L) {
                if (vf_02 == null) {
                    vf_02 = new vf_0((adg_2)na_12);
                }
                vf_02.aM(true);
                vf_02.aN(true);
            }
            if ((l2 & 0x8000L) == 32768L) {
                if (vf_02 == null) {
                    vf_02 = new vf_0((adg_2)na_12);
                }
                vf_02.aM(true);
            }
            if ((l2 & 0x4000L) == 16384L) {
                if (vf_02 == null) {
                    vf_02 = new vf_0((adg_2)na_12);
                }
                vf_02.aN(true);
            }
            if (vf_02 != null) {
                na_12.setUserDefinedManager(vf_02);
                na_12.Xq();
                this.dwL.fd(string).a(na_12);
            }
            if (this.dwd != null) {
                if ((l2 & 0x2000L) == 8192L) {
                    this.dwd.gx(string);
                } else {
                    this.dwd.q(string, false);
                }
            }
            if (string3 != null) {
                object = (adg_2)na_12;
                if (!(((adg_2)object).getLayoutData() instanceof auW)) {
                    ((adg_2)object).setLayoutData(new auW());
                }
                na_14 = (auW)((adg_2)object).getLayoutData();
                ((auW)na_14).setReferentWidget((adg_2)this.dwl.get(string3));
                if (string2 != null) {
                    ((auW)na_14).setCascadeMethodEnabled(true);
                }
                ((auW)na_14).setControlGroup(string5);
            }
            if (string5 != null && (object = na_12.getParentOfType(ex_2.class)) != null) {
                na_14 = (ex_2)object;
                adg_2 adg_22 = (adg_2)na_12;
                String string6 = string5;
                String string7 = string2;
                ((ex_2)na_14).getWindowManager().d(adg_22, string5);
                if (string7 != null) {
                    ((ex_2)na_14).getWindowManager().f(adg_22, string5);
                }
                sz_2 sz_22 = new sz_2(this, adg_22, (ex_2)na_14, string6, string7);
                this.a(sz_22);
            }
            if (bl3 && na_12 instanceof adg_2) {
                if (n2 != Integer.MIN_VALUE) {
                    ((adg_2)na_12).setX(n2);
                }
                if (n3 != Integer.MIN_VALUE) {
                    ((adg_2)na_12).setY(n3);
                }
            }
        }
    }

    private void a(String string, na_1 na_12, na_1 na_13, int n2, long l2) {
        if (na_13 instanceof eq_0) {
            ((eq_0)na_13).a((adg_2)na_12, n2);
        } else {
            na_13.f(na_12);
        }
        if ((l2 & 0x100L) == 256L) {
            amY.aBW().q(na_12);
            this.dwA.add(string);
        }
        if ((l2 & 0x200L) == 512L) {
            amY.aBW().p(na_12);
            this.dwA.add(string);
        }
        this.dwl.put(string, na_12);
    }

    public na_1 a(URL uRL, afq_1 afq_12, aji_1 aji_12, boolean bl2, URL uRL2, String string, String string2) {
        aAN aAN2 = new aAN();
        aNe aNe2 = new aNe();
        aAN2.q(new BufferedInputStream(uRL.openStream()));
        aAN2.a(aNe2, new tf_2[0]);
        aAN2.close();
        return this.dvZ.a(aNe2, uRL, afq_12, aji_12, bl2, uRL2, string, string2);
    }

    public na_1 a(Class clazz, afq_1 afq_12, aji_1 aji_12) {
        na_1 na_12 = (na_1)((aGm)clazz.newInstance()).a(afq_12, aji_12);
        return na_12;
    }

    public void kO(String string) {
        this.kP(string);
    }

    public void w(String string, boolean bl2) {
        if (bl2) {
            this.kP(string);
        } else {
            this.dwj.add(new adb(string));
        }
    }

    private void kP(String string) {
        na_1 na_12;
        if (this.dwl.containsKey(string) && (na_12 = (na_1)this.dwl.get(string)) != null) {
            na_12.aab();
        }
    }

    public void kQ(String string) {
        if (this.dwl.containsKey(string)) {
            for (int j = this.dwD.size() - 1; j >= 0; --j) {
                axq_0 axq_02 = (axq_0)this.dwD.get(j);
                if (this.dwE.contains(axq_02)) continue;
                axq_02.aL(string);
            }
            na_1 na_12 = (na_1)this.dwl.remove(string);
            if (na_12 != null && this.dwA.contains(string)) {
                this.dwA.remove(string);
                amY.aBW().o(na_12);
            }
            if (this.dwd != null) {
                this.dwd.gz(string);
            }
        }
    }

    public void aPb() {
        this.aPc();
    }

    private void aPc() {
        if (!this.dwl.isEmpty()) {
            this.dwl.b(new sv_2(this));
        }
        amY.aBW().removeAllElements();
        this.dwA.clear();
        this.dvY.getMasterRootContainer().aSm();
        this.dwl.clear();
    }

    public boolean kR(String string) {
        na_1 na_12 = (na_1)this.dwl.get(string);
        return na_12 != null && !na_12.isUnloading();
    }

    public boolean aq(String string, String string2) {
        if (this.dwl.containsKey(string2)) {
            return false;
        }
        na_1 na_12 = (na_1)this.dwl.remove(string);
        this.dwl.put(string2, na_12);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aPd() {
        ArrayList arrayList = new ArrayList();
        while (true) {
            Object object;
            Object object2 = this.dwj;
            synchronized (object2) {
                object = this.dwk;
                synchronized (object) {
                    int n2 = this.dwk.size();
                    for (int j = 0; j < n2; ++j) {
                        adb adb2 = (adb)this.dwk.get(j);
                        if ((long)(this.IP - adb2.getDuration()) - adb2.getStartTime() <= 0L) continue;
                        this.dwj.add(this.dwk.remove(j));
                        --j;
                        --n2;
                    }
                }
                arrayList.addAll(this.dwj);
                this.dwj.clear();
            }
            while (!arrayList.isEmpty()) {
                object2 = (hY)arrayList.remove(0);
                if (object2 instanceof adb) {
                    object = (adb)object2;
                    if (((adb)object).asc()) {
                        this.aPc();
                        continue;
                    }
                    this.kP(((adb)object).getId());
                    continue;
                }
                if (object2 instanceof js_0) {
                    object = (js_0)object2;
                    if ((((js_0)object).VI() & 0x800L) == 2048L && this.kR(((js_0)object).getId())) continue;
                    if (((js_0)object).getDuration() != Integer.MAX_VALUE) {
                        this.dwk.add(new adb(((js_0)object).getId(), ((js_0)object).getDuration(), this.IP));
                    }
                    this.a(((js_0)object).getId(), null, ((js_0)object).VG(), null, ((js_0)object).getParent(), ((js_0)object).VL(), ((js_0)object).VM(), ((js_0)object).VN(), ((js_0)object).VO(), ((js_0)object).VK(), ((js_0)object).VP(), ((js_0)object).VQ(), ((js_0)object).VR(), ((js_0)object).getElementMap(), ((js_0)object).VJ(), ((js_0)object).VI(), ((js_0)object).VH());
                    continue;
                }
                if (object2 instanceof qb_0) {
                    object = (qb_0)object2;
                    this.a(((qb_0)object).rE, ((qb_0)object).ade, ((qb_0)object).adf, ((qb_0)object).adh, ((qb_0)object).adg);
                    continue;
                }
                if (object2 instanceof j_0) {
                    object = (j_0)object2;
                    na_1 na_12 = (na_1)this.dwl.get(((j_0)object).n());
                    if (na_12 instanceof abS) {
                        ((abS)na_12).setTarget(((j_0)object).getTarget());
                        continue;
                    }
                    a.error((Object)("Tentative de SetWatcherTarget avec une id invalide " + ((j_0)object).n()));
                    continue;
                }
                if (object2 instanceof anv_1) {
                    object = (anv_1)object2;
                    mb_0.Yl().setWidget(((anv_1)object).cKk);
                    mb_0.Yl().setXOffset(((anv_1)object).cKl);
                    mb_0.Yl().setYOffset(((anv_1)object).cKm);
                    mb_0.Yl().a(((anv_1)object).cKn);
                    mb_0.Yl().show();
                    continue;
                }
                if (object2 instanceof gW) {
                    mb_0.Yl().hide();
                    continue;
                }
                if (!(object2 instanceof Tc)) continue;
                object = (Tc)object2;
                this.a(((Tc)object).getMessage(), ((Tc)object).getTitle(), ((Tc)object).afN(), ((Tc)object).Sk(), ((Tc)object).VI());
            }
            if (this.dwn) {
                this.dvY.getMasterRootContainer().invalidate();
                this.dwn = false;
            }
            Thread.yield();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aiQ aiQ2, int n2) {
        this.IP += n2;
        if (this.dvZ.My()) {
            this.dvZ.Mx();
        }
        this.dwa.aLW();
        if (this.dwC.size() > 0) {
            this.dwB.removeAll(this.dwC);
            this.dwC.clear();
        }
        if (this.dwG.size() > 0) {
            this.dwF.removeAll(this.dwG);
            this.dwG.clear();
        }
        if (this.dwE.size() > 0) {
            this.dwD.removeAll(this.dwE);
            this.dwE.clear();
        }
        if (this.dwK) {
            ArrayList arrayList = this.dwj;
            synchronized (arrayList) {
                hY hY2;
                int n3 = this.dwk.size();
                for (int j = 0; j < n3; ++j) {
                    hY2 = (adb)this.dwk.get(j);
                    if ((long)(this.IP - ((adb)hY2).getDuration()) - ((adb)hY2).getStartTime() <= 0L) continue;
                    ((adb)hY2).asd();
                    this.dwj.add(this.dwk.remove(j));
                    --j;
                    --n3;
                }
                while (!this.dwj.isEmpty()) {
                    hY hY3 = (hY)this.dwj.remove(0);
                    if (hY3 instanceof adb) {
                        hY2 = (adb)hY3;
                        if (!((adb)hY2).is()) {
                            ((adb)hY2).dx(this.IP);
                            this.dwk.add(hY2);
                            continue;
                        }
                        if (((adb)hY2).asc()) {
                            this.aPc();
                            continue;
                        }
                        this.kP(((adb)hY2).getId());
                        continue;
                    }
                    if (hY3 instanceof js_0) {
                        hY2 = (js_0)hY3;
                        if ((((js_0)hY2).VI() & 0x800L) == 2048L && this.kR(((js_0)hY2).getId())) continue;
                        if (((js_0)hY2).getDuration() != Integer.MAX_VALUE) {
                            this.dwk.add(new adb(((js_0)hY2).getId(), ((js_0)hY2).getDuration(), this.IP));
                        }
                        this.a(((js_0)hY2).getId(), null, ((js_0)hY2).VG(), null, ((js_0)hY2).getParent(), ((js_0)hY2).VL(), ((js_0)hY2).VM(), ((js_0)hY2).VN(), ((js_0)hY2).VO(), ((js_0)hY2).VK(), ((js_0)hY2).VP(), ((js_0)hY2).VQ(), ((js_0)hY2).VR(), ((js_0)hY2).getElementMap(), ((js_0)hY2).VJ(), ((js_0)hY2).VI(), ((js_0)hY2).VH());
                        continue;
                    }
                    if (hY3 instanceof qb_0) {
                        hY2 = (qb_0)hY3;
                        this.a(((qb_0)hY2).rE, ((qb_0)hY2).ade, ((qb_0)hY2).adf, ((qb_0)hY2).adh, ((qb_0)hY2).adg);
                        continue;
                    }
                    if (hY3 instanceof j_0) {
                        hY2 = (j_0)hY3;
                        na_1 na_12 = (na_1)this.dwl.get(((j_0)hY2).n());
                        if (na_12 instanceof abS) {
                            ((abS)na_12).setTarget(((j_0)hY2).getTarget());
                            continue;
                        }
                        a.error((Object)("Tentative de SetWatcherTarget avec une id invalide " + ((j_0)hY2).n()));
                        continue;
                    }
                    if (hY3 instanceof anv_1) {
                        hY2 = (anv_1)hY3;
                        mb_0.Yl().setWidget(((anv_1)hY2).cKk);
                        mb_0.Yl().setXOffset(((anv_1)hY2).cKl);
                        mb_0.Yl().setYOffset(((anv_1)hY2).cKm);
                        mb_0.Yl().a(((anv_1)hY2).cKn);
                        mb_0.Yl().show();
                        continue;
                    }
                    if (hY3 instanceof gW) {
                        mb_0.Yl().hide();
                        continue;
                    }
                    if (!(hY3 instanceof Tc)) continue;
                    hY2 = (Tc)hY3;
                    this.a(((Tc)hY2).getMessage(), ((Tc)hY2).getTitle(), ((Tc)hY2).afN(), ((Tc)hY2).Sk(), ((Tc)hY2).VI());
                }
            }
        }
    }

    public void a(aiQ aiQ2, int n2, int n3) {
        apt_1.aDo().a(this.dvY, n2, n3);
    }

    public void b(aiQ aiQ2) {
        this.dvY = (agV)aiQ2;
    }

    public void e(r_0 r_02) {
        this.dwH.push(r_02);
    }

    public void f(r_0 r_02) {
        this.dwH.remove(r_02);
    }

    public boolean aPe() {
        if (this.dwH.empty()) {
            return false;
        }
        r_0 r_02 = (r_0)this.dwH.pop();
        r_02.b(2048);
        return true;
    }

    public boolean aPf() {
        if (this.dwH.empty()) {
            return false;
        }
        r_0 r_02 = (r_0)this.dwH.pop();
        if ((r_02.z().VI() & 2L) != 0L) {
            r_02.b(2);
        } else {
            r_02.b(8);
        }
        return true;
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }

    static /* synthetic */ wa_0 a(add_1 add_12) {
        return add_12.dwd;
    }

    static /* synthetic */ ArrayList b(add_1 add_12) {
        return add_12.dwD;
    }

    static /* synthetic */ ArrayList c(add_1 add_12) {
        return add_12.dwE;
    }
}

