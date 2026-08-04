/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/*
 * Renamed from afk
 */
public class afk_1
extends cr_2
implements kd_1,
wb_2 {
    private static final String cqC = "cache.";
    private static final String cqD = "algorithm.";
    private static final String cqE = "comparator.";
    private ajp_2 cqF = null;
    private String cqG;
    private abq_1 cqH = null;
    private String cqI;
    private nU cqJ = null;
    private String cqK;
    private boolean cqL = true;
    private boolean cqM = true;
    private boolean cqN = true;
    private boolean cqO = true;
    private Comparator cqP = null;
    private er_0 cqQ = null;
    private aqD cqR = null;
    private int cqS = 0;
    private boolean cqT = false;
    private Vector cqU = new Vector();
    private Vector cqV = new Vector();
    private ClassLoader cqW = null;
    private bk_2 sK = null;
    static Class cqX;
    static Class cqY;
    static Class cqZ;

    public void dQ() {
        this.configure();
        if (this.cqR == null) {
            this.eC("Cache must be set.");
        } else if (this.cqQ == null) {
            this.eC("Algorithm must be set.");
        } else if (!this.cqR.isValid()) {
            this.eC("Cache must be proper configured.");
        } else if (!this.cqQ.isValid()) {
            this.eC("Algorithm must be proper configured.");
        }
    }

    public void configure() {
        vj_0 vj_02;
        if (this.cqT) {
            return;
        }
        this.cqT = true;
        UI uI = this.TP();
        String string = "cache.properties";
        File file = null;
        if (uI != null) {
            file = new File(uI.ahg(), string);
            this.TP().a(this);
        } else {
            file = new File(string);
            this.du(false);
        }
        wn_0 wn_02 = new wn_0(file);
        aw_1 aw_12 = new aw_1();
        auV auV2 = new auV();
        this.cqL = true;
        this.cqM = true;
        Iterator iterator = this.cqU.iterator();
        while (iterator.hasNext()) {
            vj_02 = (vj_0)iterator.next();
            if (vj_02.getName().indexOf(".") > 0) {
                this.cqV.add(vj_02);
                continue;
            }
            this.b(vj_02);
        }
        this.cqU = new Vector();
        if (this.cqH != null) {
            if ("hashvalue".equals(this.cqH.getValue())) {
                this.cqQ = new ep_1();
            } else if ("digest".equals(this.cqH.getValue())) {
                this.cqQ = new aw_1();
            } else if ("checksum".equals(this.cqH.getValue())) {
                this.cqQ = new aek_1();
            }
        } else {
            this.cqQ = this.cqI != null ? (er_0)this.a(this.cqI, "is not an Algorithm.", cqX == null ? (cqX = afk_1.a("Er")) : cqX) : aw_12;
        }
        if (this.cqF != null) {
            if ("propertyfile".equals(this.cqF.getValue())) {
                this.cqR = new wn_0();
            }
        } else {
            this.cqR = this.cqG != null ? (aqD)this.a(this.cqG, "is not a Cache.", cqY == null ? (cqY = afk_1.a("aqD")) : cqY) : wn_02;
        }
        if (this.cqJ != null) {
            if ("equal".equals(this.cqJ.getValue())) {
                this.cqP = new auV();
            } else if ("rule".equals(this.cqJ.getValue())) {
                throw new eq_2("RuleBasedCollator not yet supported.");
            }
        } else {
            this.cqP = this.cqK != null ? (Comparator)this.a(this.cqK, "is not a Comparator.", cqZ == null ? (cqZ = afk_1.a("java.util.Comparator")) : cqZ) : auV2;
        }
        iterator = this.cqV.iterator();
        while (iterator.hasNext()) {
            vj_02 = (vj_0)iterator.next();
            this.b(vj_02);
        }
        this.cqV = new Vector();
    }

    protected Object a(String string, String string2, Class clazz) {
        try {
            ClassLoader classLoader = this.getClassLoader();
            Class<?> clazz2 = null;
            clazz2 = classLoader != null ? classLoader.loadClass(string) : Class.forName(string);
            Object obj = clazz2.newInstance();
            if (!clazz.isInstance(obj)) {
                throw new eq_2("Specified class (" + string + ") " + string2);
            }
            return obj;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new eq_2("Specified class (" + string + ") not found.");
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
    }

    public boolean a(iv_1 iv_12) {
        if (iv_12.dE()) {
            ash_0 ash_02 = (ash_0)iv_12;
            File file = ash_02.getFile();
            String string = ash_02.getName();
            File file2 = ash_02.ahg();
            return this.a(file2, string, file);
        }
        try {
            ga_2 ga_22 = ga_2.Qo();
            File file = ga_22.a("modified-", ".tmp", (File)null, true, true);
            ash_0 ash_03 = new ash_0(file);
            ahu_1.a(iv_12, ash_03);
            boolean bl2 = this.a(file.getParentFile(), file.getName(), iv_12.lJ());
            return bl2;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            this.l("The resource '" + iv_12.getName() + "' does not provide an InputStream, so it is not checked. " + "Akkording to 'selres' attribute value it is " + (this.cqN ? "" : " not") + "selected.", 2);
            return this.cqN;
        }
        catch (Exception exception) {
            throw new eq_2(exception);
        }
    }

    public boolean a(File file, String string, File file2) {
        return this.a(file, string, file2.getAbsolutePath());
    }

    private boolean a(File file, String string, String string2) {
        String string3;
        boolean bl2;
        this.validate();
        File file2 = new File(file, string);
        if (file2.isDirectory()) {
            return this.cqM;
        }
        String string4 = String.valueOf(this.cqR.get(file2.getAbsolutePath()));
        boolean bl3 = bl2 = this.cqP.compare(string4, string3 = this.cqQ.b(file2)) != 0;
        if (this.cqL && bl2) {
            this.cqR.put(file2.getAbsolutePath(), string3);
            this.kl(this.auS() + 1);
            if (!this.auT()) {
                this.auR();
            }
        }
        return bl2;
    }

    protected void auR() {
        if (this.auS() > 0) {
            this.cqR.save();
            this.kl(0);
        }
    }

    public void hP(String string) {
        this.cqI = string;
    }

    public void hQ(String string) {
        this.cqK = string;
    }

    public void hR(String string) {
        this.cqG = string;
    }

    public void dr(boolean bl2) {
        this.cqL = bl2;
    }

    public void ds(boolean bl2) {
        this.cqM = bl2;
    }

    public void dt(boolean bl2) {
        this.cqN = bl2;
    }

    public int auS() {
        return this.cqS;
    }

    public void kl(int n2) {
        this.cqS = n2;
    }

    public boolean auT() {
        return this.cqO;
    }

    public void du(boolean bl2) {
        this.cqO = bl2;
    }

    public void h(bk_2 bk_22) {
        if (this.sK != null) {
            throw new eq_2("<classpath> can be set only once.");
        }
        this.sK = bk_22;
    }

    public ClassLoader getClassLoader() {
        if (this.cqW == null) {
            this.cqW = this.sK == null ? this.getClass().getClassLoader() : this.TP().g(this.sK);
        }
        return this.cqW;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.cqW = classLoader;
    }

    public void p(String string, Object object) {
        vj_0 vj_02 = new vj_0();
        vj_02.setName(string);
        vj_02.setValue(String.valueOf(object));
        this.cqU.add(vj_02);
    }

    public void a(vj_0 vj_02) {
        this.cqU.add(vj_02);
    }

    public void a(vj_0[] vj_0Array) {
        if (vj_0Array != null) {
            for (int j = 0; j < vj_0Array.length; ++j) {
                this.cqU.add(vj_0Array[j]);
            }
        }
    }

    public void b(vj_0 vj_02) {
        String string = vj_02.getName();
        String string2 = vj_02.getValue();
        if ("cache".equals(string)) {
            ajp_2 ajp_22 = new ajp_2();
            ajp_22.setValue(string2);
            this.a(ajp_22);
        } else if ("algorithm".equals(string)) {
            abq_1 abq_12 = new abq_1();
            abq_12.setValue(string2);
            this.a(abq_12);
        } else if ("comparator".equals(string)) {
            nU nU2 = new nU();
            nU2.setValue(string2);
            this.a(nU2);
        } else if ("update".equals(string)) {
            boolean bl2 = "true".equalsIgnoreCase(string2);
            this.dr(bl2);
        } else if ("delayupdate".equals(string)) {
            boolean bl3 = "true".equalsIgnoreCase(string2);
            this.du(bl3);
        } else if ("seldirs".equals(string)) {
            boolean bl4 = "true".equalsIgnoreCase(string2);
            this.ds(bl4);
        } else if (string.startsWith(cqC)) {
            String string3 = string.substring(cqC.length());
            this.a(this.cqR, string3, string2);
        } else if (string.startsWith(cqD)) {
            String string4 = string.substring(cqD.length());
            this.a(this.cqQ, string4, string2);
        } else if (string.startsWith(cqE)) {
            String string5 = string.substring(cqE.length());
            this.a(this.cqP, string5, string2);
        } else {
            this.eC("Invalid parameter " + string);
        }
    }

    protected void a(Object object, String string, String string2) {
        UI uI = this.TP() != null ? this.TP() : new UI();
        hm_2 hm_22 = hm_2.a(uI, object.getClass());
        try {
            hm_22.a(uI, object, string, string2);
        }
        catch (eq_2 eq_22) {
            // empty catch block
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{modifiedselector");
        stringBuffer.append(" update=").append(this.cqL);
        stringBuffer.append(" seldirs=").append(this.cqM);
        stringBuffer.append(" cache=").append(this.cqR);
        stringBuffer.append(" algorithm=").append(this.cqQ);
        stringBuffer.append(" comparator=").append(this.cqP);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void b(axv_0 axv_02) {
        if (this.auT()) {
            this.auR();
        }
    }

    public void f(axv_0 axv_02) {
        if (this.auT()) {
            this.auR();
        }
    }

    public void h(axv_0 axv_02) {
        if (this.auT()) {
            this.auR();
        }
    }

    public void a(axv_0 axv_02) {
    }

    public void e(axv_0 axv_02) {
    }

    public void g(axv_0 axv_02) {
    }

    public void i(axv_0 axv_02) {
    }

    public aqD auU() {
        return this.cqR;
    }

    public void a(ajp_2 ajp_22) {
        this.cqF = ajp_22;
    }

    public er_0 auV() {
        return this.cqQ;
    }

    public void a(abq_1 abq_12) {
        this.cqH = abq_12;
    }

    public Comparator getComparator() {
        return this.cqP;
    }

    public void a(nU nU2) {
        this.cqJ = nU2;
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

