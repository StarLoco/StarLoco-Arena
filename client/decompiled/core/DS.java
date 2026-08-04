/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.zip.CRC32;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

public class DS {
    private static Logger a = Logger.getLogger(DS.class);
    private static final boolean DEBUG = false;
    private static final CRC32 qM = new CRC32();
    public static final String aOr = "id";
    public static final String aOs = "includeId";
    public static final String aOt = "path";
    public static final String aOu = "form";
    public static final String aOv = "include";
    public static final String aOw = "templateId";
    public static final String aOx = "templateRef";
    public static final String aOy = "template";
    public static final String aOz = "templateElement";
    public static final String aOA = "templateElementIgnore";
    public static final String aOB = "atlas";
    public static final String aOC = "ref";
    public static final String aOD = "init";
    public static final String aOE = "themeElement";
    public static final String aOF = "texture";
    public static final String aOG = "pixmap";
    public static final String aOH = "initAtOnce";
    public static final String aOI = "texture";
    public static final String aOJ = "color";
    public static final String aOK = "color";
    public static final String aOL = "cursor";
    public static final String aOM = "animatedCursor";
    public static final String aON = "cursorFrame";
    public static final String aOO = "tooltip";
    public static final String aOP = "textColor";
    public static final String aOQ = "borderColor";
    public static final String aOR = "borderWidth";
    public static final String aOS = "backgroundColor";
    public static final String aOT = "x";
    public static final String aOU = "y";
    public static final String aOV = "width";
    public static final String aOW = "height";
    public static final String aOX = "type";
    public static final String aOY = "delay";
    public static final String aOZ = "font";
    public static final String ATTR_NAME = "name";
    public static final String aPa = "font";
    public static final String aPb = "bordered";
    public static final String aPc = "permanent";
    private aji_1 aPd = null;
    private PrintWriter aPe;
    private ur_0 aPf = null;
    private JO aPg = null;
    private afn_2 aPh = null;
    private boolean aPi = false;
    private HashMap aPj;
    private HashMap aPk = new HashMap();
    private lw_0 aPl = null;
    private static final boolean aPm = false;
    private URL aPn = null;
    private URL aPo;
    private String aPp;
    private final ArrayList aPq = new ArrayList();
    private final HashMap aPr = new HashMap();
    private final HashMap aPs = new HashMap();
    private final HashMap aPt = new HashMap();
    private final HashMap aPu = new HashMap();
    private boolean aPv = false;
    private adg_2 aPw = null;

    public lw_0 Mp() {
        return this.aPl;
    }

    public void a(lw_0 lw_02) {
        this.aPl = lw_02;
    }

    public afn_2 Mq() {
        return this.aPh;
    }

    public void a(afn_2 afn_22) {
        this.aPh = afn_22;
    }

    public na_1 a(aNe aNe2, URL uRL, afq_1 afq_12, aji_1 aji_12, boolean bl2, URL uRL2, String string, String string2) {
        Object object;
        this.a(aNe2, uRL);
        if (bl2) {
            this.aPd = null;
            object = null;
            try {
                object = an_2.a(uRL2, string + ".java");
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)"Exception", (Throwable)malformedURLException);
            }
            if (object != null) {
                try {
                    this.aPe = new PrintWriter(new FileOutputStream(new File(((URL)object).getFile())));
                }
                catch (FileNotFoundException fileNotFoundException) {
                    a.error((Object)"Exception", (Throwable)fileNotFoundException);
                }
                this.aPf = new ur_0(this.aPe, string, string2, aNe2.aXo());
            }
        }
        Stack<aji_1> stack = new Stack<aji_1>();
        stack.push(aji_12);
        if (bl2) {
            object = (na_1)this.a(this.aPf.ahV(), this.aPf.ahU(), afq_12, stack);
            this.aPf.yj();
            this.aPf = null;
        } else {
            object = (na_1)this.a(aNe2.aXo(), null, afq_12, stack);
        }
        return object;
    }

    private air_1 a(k_0 k_02, na_1 na_12, afq_1 afq_12, Stack stack) {
        na_1 na_13;
        aLH aLH2 = ye_2.amJ().ij(k_02.getName());
        if (aLH2 == null) {
            a.error((Object)("Tag Inconnu : " + k_02.getName()));
            return null;
        }
        try {
            na_13 = (na_1)aLH2.newInstance();
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors de l'instanciation du tag " + k_02.getName() + "."));
            return null;
        }
        na_13.a(k_02, na_12, stack, afq_12);
        na_13.s(k_02);
        na_13.c(k_02, na_12, stack, afq_12);
        na_13.e(k_02, na_12, stack, afq_12);
        return na_13;
    }

    private na_1 b(k_0 k_02, na_1 object, afq_1 afq_12, Stack stack) {
        Object object2;
        na_1 na_12;
        aji_1 aji_12;
        Object object3;
        boolean bl2 = false;
        k_0 k_03 = k_02;
        if ((k_02 = this.g(k_02)) == k_03) {
            k_03 = null;
        }
        String string = null;
        if (k_02.getName().equalsIgnoreCase(aOE)) {
            object3 = k_02.f(ATTR_NAME);
            if (object3 != null) {
                string = object3.getStringValue();
            }
            bl2 = true;
        }
        if (string != null && object != null && (object3 = object instanceof adg_2 ? (adg_2)object : (adg_2)((na_1)object).getParentOfType(adg_2.class)) != null) {
            if ((object3 = ((adg_2)object3).getWidgetByThemeElementName(string, false)) != null) {
                object = object3;
            } else if (!(object instanceof adg_2)) {
                return null;
            }
        }
        object3 = k_02.getChildren();
        if (bl2) {
            Object object4 = object3;
            for (int j = 0; j < ((ArrayList)object4).size(); ++j) {
                k_0 k_04 = (k_0)((ArrayList)object4).get(j);
                if (k_04.getName().equals("#text") || k_04.getName().equals("#comment")) continue;
                this.b(k_04, (na_1)object, afq_12, stack);
            }
            if (k_03 != null) {
                ArrayList arrayList = k_03.getChildren();
                for (int j = 0; j < arrayList.size(); ++j) {
                    k_0 k_05 = (k_0)arrayList.get(j);
                    if (k_05.getName().equals("#text") || k_05.getName().equals("#comment")) continue;
                    this.b(k_05, (na_1)object, afq_12, stack);
                }
            }
            return null;
        }
        String string2 = k_02.f(aOr) != null && !this.aPi ? k_02.f(aOr).getStringValue().trim() : null;
        aji_1 aji_13 = aji_12 = stack != null ? (aji_1)stack.peek() : null;
        if (aji_12 == null && object != null) {
            aji_12 = ((na_1)object).getElementMap();
        }
        String string3 = aji_12 == null ? "" : aji_12.getId();
        aLH aLH2 = ye_2.amJ().ij(k_02.getName());
        if (aLH2 == null) {
            a.error((Object)("Tag Inconnu : " + k_02.getName()));
            return null;
        }
        try {
            na_12 = (na_1)aLH2.newInstance();
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors de l'instanciation du tag " + k_02.getName() + "."));
            return null;
        }
        if (na_12 instanceof qr && (object2 = k_02.f(aOC)) != null) {
            ((qr)na_12).setRenderer((af_1)this.aPt.get(object2.getStringValue()));
        }
        if (na_12 instanceof Zb && object != null && (object2 = object instanceof adg_2 ? (adg_2)object : (adg_2)((na_1)object).getParentOfType(adg_2.class)) != null && ((adg_2)object2).getAppearance() != null) {
            na_12.aab();
            na_12 = ((adg_2)object2).getAppearance();
        }
        na_12.setElementMap(aji_12);
        if (aji_12 != null && string2 != null) {
            aji_12.a(string2, na_12);
        }
        if (k_02.getName().equalsIgnoreCase(aOu)) {
            object2 = null;
            k_0 k_06 = k_02.f(aOr);
            if (k_06 != null) {
                object2 = k_06.getStringValue();
            } else {
                a.warn((Object)"Attention : l'id du formulaire est nulle.");
            }
            afq_12.a(string3 + "." + (String)object2, (Ur)na_12);
        }
        object2 = null;
        this.a(na_12, null, null, aLH2, k_02.al(), false, afq_12);
        if (object != null) {
            if (((na_1)object).isValidAdd(na_12)) {
                ((air_1)object).f(na_12);
            } else if (na_12.getParent() == null) {
                na_12.aab();
                return null;
            }
        }
        na_12.Ak();
        int n2 = ((ArrayList)object3).size();
        for (int j = 0; j < n2; ++j) {
            k_0 k_07 = (k_0)((ArrayList)object3).get(j);
            if (k_07.getName().equals("#text") || k_07.getName().equals("#comment")) continue;
            if (k_07.f(aOv) != null) {
                String string4 = k_07.f(aOs).getStringValue();
                if (string4 == null) {
                    a.error((Object)"Pas d'id pour le tag Include, impossible de l'ajouter");
                    continue;
                }
                aji_1 aji_14 = afq_12.lf(string3 + "." + string4);
                aji_14.c(aji_12);
                stack.push(aji_14);
                this.b(k_07, na_12, afq_12, stack);
                stack.pop();
                continue;
            }
            this.b(k_07, na_12, afq_12, stack);
        }
        na_12.Aj();
        if (k_02.getName().equals(aOu)) {
            afq_12.lj(string3 + "." + k_02.f(aOr).getStringValue());
        }
        return na_12;
    }

    private air_1 a(String string, String string2, afq_1 afq_12, Stack stack) {
        Object object7;
        Object object2;
        String string3;
        Object object3;
        air_1 air_12;
        aLH aLH2;
        aji_1 aji_12;
        Object object4;
        Object object52;
        k_0 k_02 = (k_0)this.aPf.dn(string);
        Object object6 = (air_1)this.aPf.dn(string2);
        String string4 = string2;
        boolean bl2 = this.aPf.GR();
        if (bl2) {
            this.aPf.a(k_02, (air_1)object6, string2);
        }
        this.aPf.j(String.class);
        this.aPf.j(afq_1.class);
        this.aPf.j(aji_1.class);
        this.aPf.j(ye_2.class);
        this.aPf.j(adg_2.class);
        this.aPf.j(aLH.class);
        this.aPf.j(add_1.class);
        this.aPf.j(qr.class);
        boolean bl3 = false;
        k_0 k_03 = k_02;
        k_02 = this.g(k_02);
        if (k_02 == k_03) {
            k_03 = null;
        }
        String string5 = null;
        if (k_02.getName().equalsIgnoreCase(aOE)) {
            k_0 k_04 = k_02.f(ATTR_NAME);
            if (k_04 != null) {
                string5 = k_04.getStringValue();
            }
            bl3 = true;
        }
        boolean bl4 = false;
        if (string5 != null && object6 != null) {
            this.aPf.mark();
            object52 = this.aPf.GQ();
            if (object6 instanceof adg_2) {
                object4 = (adg_2)object6;
                this.aPf.a(new aKI(adg_2.class, (String)object52, string2));
            } else {
                object4 = ((air_1)object6).getWidgetParent();
                this.aPf.a(new aKI(adg_2.class, (String)object52, string2 + ".getWidgetParent()"));
            }
            if (object4 != null) {
                if ((object4 = ((adg_2)object4).getWidgetByThemeElementName(string5, true)) != null) {
                    bl4 = true;
                    object6 = object4;
                    string2 = this.aPf.GQ();
                    this.aPf.h(string2, object4);
                    this.aPf.a(new aKI(na_1.class, string2, (String)object52 + ".getWidgetByThemeElementName(\"" + string5 + "\", false)"));
                    this.aPf.a(new azw("if (" + string2 + " != null) {"));
                } else if (!(object6 instanceof adg_2)) {
                    this.aPf.GS();
                    this.aPf.resetMark();
                    if (bl2) {
                        this.aPf.yi();
                    }
                    return null;
                }
            }
            this.aPf.resetMark();
        }
        if (bl3) {
            String string6;
            for (Object object52 : k_02.getChildren()) {
                if (object52.getName().equals("#text") || object52.getName().equals("#comment")) continue;
                string6 = this.aPf.GQ();
                this.aPf.h(string6, object52);
                this.a(string6, string2, afq_12, stack);
            }
            if (k_03 != null) {
                for (Object object52 : k_03.getChildren()) {
                    if (object52.getName().equals("#text") || object52.getName().equals("#comment")) continue;
                    string6 = this.aPf.GQ();
                    this.aPf.h(string6, object52);
                    this.a(string6, string2, afq_12, stack);
                }
            }
            if (bl4) {
                this.aPf.a(new azw("}"));
            }
            if (bl2) {
                this.aPf.yi();
            }
            return null;
        }
        object4 = k_02.f(aOr) != null ? k_02.f(aOr).getStringValue().trim() : null;
        object52 = null;
        aji_1 aji_13 = aji_12 = stack != null ? (aji_1)stack.peek() : null;
        if (aji_12 == null && object6 != null) {
            aji_12 = ((air_1)object6).getEventDispatcherParent().getElementMap();
        }
        String string7 = aji_12 == null ? "" : aji_12.getId();
        if (object4 != null) {
            object52 = this.aPf.GQ();
            this.aPf.a(new aKI(String.class, (String)object52, "\"" + (String)object4 + "\""));
        }
        if ((aLH2 = ye_2.amJ().ij(k_02.getName())) == null) {
            a.error((Object)("Tag Inconnu : " + k_02.getName()));
            if (bl2) {
                this.aPf.yi();
            }
            return null;
        }
        String string8 = this.aPf.GQ();
        try {
            this.aPf.mark();
            air_12 = (air_1)aLH2.b(this.aPf, string8);
        }
        catch (Exception exception) {
            a.error((Object)("Erreur lors de l'instanciation du tag " + k_02.getName() + "."), (Throwable)exception);
            this.aPf.resetMark();
            if (bl2) {
                this.aPf.yi();
            }
            return null;
        }
        if (air_12 instanceof Zb && object6 != null) {
            object3 = (Zb)air_12;
            string3 = this.aPf.GQ();
            object2 = null;
            if (object6 instanceof adg_2) {
                object7 = (adg_2)object6;
                object2 = new aKI(adg_2.class, string3, string2);
            } else {
                object7 = ((air_1)object6).getWidgetParent();
                object2 = new aKI(adg_2.class, string3, string2 + ".getWidgetParent()");
            }
            if (object7 != null && ((adg_2)object7).getAppearance() != null) {
                ((na_1)object3).aab();
                air_12 = ((adg_2)object7).getAppearance();
                this.aPf.GS();
                this.aPf.resetMark();
                if (object2 != null) {
                    this.aPf.a((oy_0)object2);
                    this.aPf.a(new aKI(Zb.class, string8, string3 + ".getAppearance()"));
                }
            }
        }
        this.aPf.resetMark();
        if (air_12 instanceof qr && (object3 = k_02.f(aOC)) != null) {
            this.aPf.a(new azw("((FontElement)" + string8 + ").setRenderer(Xulor.getInstance().getDocumentParser().getFont(\"" + object3.getStringValue() + "\"));"));
            ((qr)air_12).setRenderer(add_1.aOG().yh().dL(object3.getStringValue()));
        }
        if (air_12.getElementType() == eh_0.aTF) {
            object3 = (na_1)air_12;
            this.aPf.a(new aza(null, "setElementMap", string8, "elementMap"));
            ((na_1)object3).setElementMap(aji_12);
            if (aji_12 != null && object4 != null) {
                aji_12.a((String)object4, (na_1)object3);
            }
            if (object4 != null) {
                this.aPf.a(new azw("if (elementMap != null && " + (String)object52 + " != null)"));
                this.aPf.a(new aza(null, "add", "elementMap", new String[]{object52, string8}));
            }
        }
        if (k_02.getName().equalsIgnoreCase(aOu)) {
            object3 = null;
            object7 = k_02.f(aOr);
            if (object7 != null) {
                object3 = object7.getStringValue();
            } else {
                a.warn((Object)"Attention : l'id du formulaire est nulle.");
            }
            string3 = string7 + "." + (String)object3;
            this.aPf.a(new azw("env.openForm((elementMap != null ? elementMap.getId() : \"\") + \"." + (String)object3 + "\", (Form) " + string8 + ");"));
            afq_12.a(string3, (Ur)air_12);
        }
        this.a(air_12, string8, k_02.getName(), aLH2, k_02.al(), true, afq_12);
        if (object6 != null) {
            if (!(object6 instanceof na_1) || ((na_1)object6).isValidAdd(air_12)) {
                this.aPf.a(new aza(null, "addBasicElement", string2, string8));
                ((air_1)object6).j(air_12);
            } else if (air_12.getBasicParent() == null) {
                this.aPf.a(new aza(null, "release", string8));
                air_12.release();
                if (bl2) {
                    this.aPf.yi();
                }
                return null;
            }
        }
        air_12.Ak();
        this.aPf.a(new aza(null, "onAttributesInitialized", string8));
        this.aPf.h(string8, air_12);
        for (Object object7 : k_02.getChildren()) {
            if (object7.getName().equals("#text") || object7.getName().equals("#comment")) continue;
            string3 = this.aPf.L(object7);
            if (object7.f(aOv) != null) {
                object2 = object7.f(aOs).getStringValue();
                if (object2 == null) {
                    a.error((Object)"Pas d'id pour le tag Include, impossible de l'ajouter");
                    continue;
                }
                String string9 = this.aPf.GQ();
                this.aPf.a(new aKI(aji_1.class, string9, "elementMap"));
                aji_1 aji_14 = afq_12.lf(string7 + "." + (String)object2);
                this.aPf.a(new aKI(aji_1.class, "elementMap", "env.createElementMap((elementMap != null ? elementMap.getId() : \"\") + \"." + (String)object2 + "\")"));
                aji_14.c(aji_12);
                this.aPf.a(new aza(null, "setParentElementMap", "elementMap", string9));
                stack.push(aji_14);
                this.aPf.a(new aza(null, "push", "elementMaps", "elementMap"));
                this.a(string3, string8, afq_12, stack);
                stack.pop();
                this.aPf.a(new aza(null, "pop", "elementMaps"));
                this.aPf.a(new aKI(aji_1.class, "elementMap", "elementMaps.peek()"));
                continue;
            }
            this.a(string3, string8, afq_12, stack);
        }
        air_12.Aj();
        this.aPf.a(new aza(null, "onChildrenAdded", string8));
        if (k_02.getName().equals(aOu)) {
            object3 = string7 + "." + k_02.f(aOr).getStringValue();
            this.aPf.a(new azw("env.closeForm((elementMap != null ? elementMap.getId() : \"\") + \"." + k_02.f(aOr).getStringValue() + "\");"));
            afq_12.lj((String)object3);
        }
        if (bl2) {
            this.aPf.yi();
        }
        return air_12;
    }

    public static void a(air_1 air_12, aLH aLH2, String string, String string2) {
        Method method = aLH2.iX(string);
        if (method != null) {
            Class<?> clazz = method.getParameterTypes()[0];
            Object object = null;
            try {
                object = if_1.UG().c(clazz, string2);
                method.invoke(air_12, object);
            }
            catch (Exception exception) {
                a.error((Object)("Probl\u00e8me \u00e0 l'invoke :" + method.getName() + ":" + object), (Throwable)exception);
            }
        }
    }

    private void a(air_1 air_12, String string, String string2, aLH aLH2, List list, boolean bl2, afq_1 afq_12) {
        if (bl2) {
            this.aPf.j(Class.class);
            this.aPf.j(Method.class);
            this.aPf.j(apG.class);
            this.aPf.j(if_1.class);
        }
        boolean bl3 = air_12 instanceof ur_1;
        String string3 = null;
        int n2 = list.size();
        for (int j = 0; j < n2; ++j) {
            boolean bl4;
            k_0 k_02 = (k_0)list.get(j);
            String string4 = k_02.getName();
            if (aOr.equals(string4) || aOC.equals(string4) || aOw.equals(string4) || aOx.equals(string4)) continue;
            if (bl3 && "texture".equalsIgnoreCase(string4)) {
                string3 = k_02.getStringValue();
            }
            if ((bl4 = air_12.setXMLAttribute(string4, k_02.getStringValue())) && !bl2) continue;
            Method method = aLH2.iX(string4);
            String string5 = null;
            if (method != null) {
                Class<?> clazz = method.getParameterTypes()[0];
                String string6 = null;
                apG apG2 = if_1.UG().o(clazz);
                String string7 = null;
                if (apG2 == null) continue;
                if (bl2 && !apG2.ul()) {
                    String string8 = this.aPf.GQ();
                    this.aPf.a(new aKI(aLH.class, string8, "XulorTagLibrary.getInstance().getFactory(\"" + string2 + "\")"));
                    string5 = this.aPf.GQ();
                    this.aPf.a(new aKI(Method.class, string5, string8 + ".guessSetter(\"" + string4 + "\")"));
                    string7 = this.aPf.GQ();
                    string6 = this.aPf.GQ();
                    this.aPf.a(new aKI(Class.class, string6, string5 + ".getParameterTypes()[0]"));
                    this.aPf.a(new aKI(apG.class, string7, "ConverterLibrary.getInstance().getConverter(" + string6 + ")"));
                }
                Object object = null;
                try {
                    if (bl2) {
                        String string9 = null;
                        if (apG2.ul()) {
                            string9 = apG2.a(this.aPf, this, clazz, k_02.getStringValue(), afq_12);
                        } else {
                            object = apG2.c(clazz, k_02.getStringValue());
                            string9 = this.aPf.GQ();
                            this.aPf.a(new aKI(clazz, string9, string7 + ".convert(" + string6 + ", \"" + k_02.getStringValue() + "\")"));
                        }
                        this.aPf.a(new aza(method.getDeclaringClass(), method.getName(), string, string9));
                    }
                    object = apG2.c(clazz, k_02.getStringValue());
                    method.invoke(air_12, object);
                }
                catch (Exception exception) {
                    a.error((Object)("Probl\u00e8me \u00e0 l'invoke :" + method.getName() + ":" + object), (Throwable)exception);
                }
                continue;
            }
            if (!bl2 || string4.equals(aOB) || string4.equals(aOs) || string4.equals(aOv)) continue;
            throw new eq_2("Impossible de trouver l'attribut " + string4);
        }
    }

    public void a(aNe aNe2, URL uRL) {
        PU pU = aNe2.aXo();
        Stack<URL> stack = new Stack<URL>();
        stack.push(uRL);
        k_0 k_02 = this.a((k_0)pU, stack);
        if (k_02 != null) {
            aNe2.a((PU)k_02);
        }
    }

    public k_0 a(k_0 k_02, Stack stack) {
        int n2;
        k_0 k_03;
        Object object;
        Object object2;
        if (k_02 == null) {
            return null;
        }
        Object object3 = k_02;
        int n3 = stack.size();
        if (aOy.equalsIgnoreCase(k_02.getName())) {
            object2 = k_02.f(aOt);
            if (object2 != null) {
                object = k_02.d(aOz);
                k_03 = this.a(object2.getStringValue(), stack, false);
                this.a(k_03, k_02, (ArrayList)object);
                object3 = k_03;
            }
        } else if (aOv.equalsIgnoreCase(k_02.getName()) && (object2 = k_02.f(aOt)) != null) {
            object = this.a(object2.getStringValue(), stack, true);
            k_03 = k_02.f(aOr);
            object3 = object;
            object3.c(new zo_2(aOv, ""));
            if (k_03 != null) {
                object3.c(new zo_2(aOs, k_03.getStringValue()));
            }
        }
        object2 = new ArrayList();
        for (n2 = object3.getChildren().size() - 1; n2 >= 0; --n2) {
            k_03 = (k_0)object3.getChildren().get(n2);
            ((ArrayList)object2).add(k_03);
        }
        for (n2 = ((ArrayList)object2).size() - 1; n2 >= 0; --n2) {
            object3.b((k_0)((ArrayList)object2).get(n2));
        }
        for (n2 = ((ArrayList)object2).size() - 1; n2 >= 0; --n2) {
            k_03 = (k_0)((ArrayList)object2).get(n2);
            if (k_03.getName().equals("#text") || k_03.getName().equals("#comment")) continue;
            k_0 k_04 = this.a(k_03, stack);
            if (k_04 != null) {
                object3.a(k_04);
                continue;
            }
            object3.a(k_03);
        }
        while (stack.size() > n3) {
            stack.pop();
        }
        if (object3 == k_02) {
            return null;
        }
        return object3;
    }

    public void a(k_0 k_02, k_0 k_03, ArrayList arrayList) {
        int n2;
        Object object;
        if (k_02 == null || arrayList == null || arrayList.isEmpty()) {
            return;
        }
        k_0 k_04 = k_02.f(aOw);
        if (k_04 != null) {
            Object object2;
            object = null;
            n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                object = (k_0)arrayList.get(j);
                object2 = object.f(aOx);
                if (k_04.getStringValue().equalsIgnoreCase(object2.getStringValue())) break;
                object = null;
            }
            if (object != null) {
                int n3;
                arrayList.remove(object);
                k_0 k_05 = object.f(aOA);
                if (k_05 != null && k_05.getBooleanValue()) {
                    k_03.b(k_02);
                    return;
                }
                object2 = object.getChildren();
                int n4 = ((ArrayList)object2).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    k_02.a((k_0)((ArrayList)object2).get(n3));
                }
                object2 = object.al();
                n4 = ((ArrayList)object2).size();
                for (n3 = 0; n3 < n4; ++n3) {
                    k_0 k_06 = (k_0)((ArrayList)object2).get(n3);
                    String string = k_06.getName();
                    if (string.equals("#text") || string.equals("#comment") || aOx.equalsIgnoreCase(string)) continue;
                    k_0 k_07 = k_02.f(string);
                    if (k_07 != null) {
                        k_02.d(k_07);
                    }
                    k_02.c(k_06);
                }
            }
            k_02.d(k_04);
        }
        object = k_02.getChildren();
        for (n2 = ((ArrayList)object).size() - 1; n2 >= 0; --n2) {
            k_0 k_08 = (k_0)((ArrayList)object).get(n2);
            if (k_08.getName().equals("#text") || k_08.getName().equals("#comment")) continue;
            this.a(k_08, k_02, arrayList);
        }
    }

    public k_0 a(String string, Stack stack, boolean bl2) {
        URL uRL = null;
        aNe aNe2 = null;
        try {
            uRL = an_2.a((URL)stack.peek(), string);
            if (bl2) {
                stack.push(uRL);
            }
            aAN aAN2 = new aAN();
            aNe2 = new aNe();
            aAN2.q(new BufferedInputStream(uRL.openStream()));
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)("Impossible de charger le template d'url : " + stack.peek() + string));
        }
        if (aNe2 != null) {
            return aNe2.aXo();
        }
        return null;
    }

    public String Mr() {
        return this.aPp;
    }

    public k_0 g(k_0 k_02) {
        if (k_02 == null) {
            return null;
        }
        k_0 k_03 = k_02.f(aOC);
        k_0 k_04 = null;
        if (k_03 != null) {
            k_04 = (k_0)this.aPr.get(k_03.getStringValue().toUpperCase());
        }
        if (k_04 == null) {
            return k_02;
        }
        return k_04;
    }

    public k_0 a(k_0 k_02, String string) {
        if (k_02 == null) {
            a.error((Object)"Probl\u00e8me lors de la recherche de ThemeElement : entry est null");
            return null;
        }
        if (string == null) {
            a.error((Object)"Probl\u00e8me lors de la recherche de ThemeElement : name est null");
            return null;
        }
        ArrayList arrayList = this.c(k_02 = this.g(k_02), aOE);
        if (arrayList != null) {
            for (k_0 k_03 : arrayList) {
                k_0 k_04 = (k_03 = this.g(k_03)).f(ATTR_NAME);
                if (k_04 == null || !string.equalsIgnoreCase(k_04.getStringValue())) continue;
                return k_03;
            }
        }
        return null;
    }

    private ArrayList b(k_0 k_02, String string) {
        ArrayList arrayList = new ArrayList();
        this.a(k_02, arrayList, string);
        return arrayList;
    }

    private void a(k_0 k_02, ArrayList arrayList, String string) {
        if (k_02 == null) {
            a.error((Object)"Probl\u00e8me lors de la recherche de ThemeElement : entry est null");
            return;
        }
        k_0 k_03 = (k_02 = this.g(k_02)).f(aOX);
        if (k_03 == null) {
            a.warn((Object)("type inconnu pour " + string));
            return;
        }
        aLH aLH2 = ye_2.amJ().ij(k_03.getStringValue());
        air_1 air_12 = null;
        try {
            air_12 = (air_1)aLH2.newInstance();
        }
        catch (Exception exception) {
            a.warn((Object)("Probl\u00e8me \u00e0 la g\u00e9n\u00e9ration de " + k_03.getStringValue()));
            return;
        }
        arrayList.add(new atI(air_12, string, k_02, null));
        ArrayList arrayList2 = this.c(k_02, aOE);
        if (arrayList2 != null) {
            for (k_0 k_04 : arrayList2) {
                k_0 k_05;
                k_0 k_06 = this.g(k_04);
                if (k_06 == k_02 || (k_05 = k_06.f(ATTR_NAME)) == null) continue;
                String string2 = k_05.getStringValue();
                this.a(k_06, arrayList, string + "$" + string2.substring(0, 1).toUpperCase() + string2.substring(1, string2.length()));
            }
        }
    }

    public ArrayList c(k_0 k_02, String string) {
        ArrayList<k_0> arrayList = new ArrayList<k_0>();
        if (k_02.getName().equalsIgnoreCase(string)) {
            arrayList.add(k_02);
        }
        for (k_0 k_03 : k_02.getChildren()) {
            ArrayList arrayList2 = k_03.d(string);
            if (arrayList2 == null) continue;
            arrayList.addAll(arrayList2);
        }
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        return arrayList;
    }

    public void Ms() {
        this.Mt();
        this.Mu();
    }

    public void Mt() {
        this.aPq.clear();
        this.aPr.clear();
        this.aPs.clear();
        this.aPt.clear();
        this.aPu.clear();
    }

    private void Mu() {
        if (this.aPh == null || this.aPl == null) {
            aAN aAN2 = new aAN();
            aNe aNe2 = new aNe();
            try {
                aAN2.q(new BufferedInputStream(this.aPo.openStream()));
                aAN2.a(aNe2, new tf_2[0]);
                aAN2.close();
            }
            catch (Exception exception) {
                a.error((Object)("Probl\u00e8me lors du chargement du theme " + exception.getMessage()));
            }
            PU pU = aNe2.aXo();
            this.i(pU);
        } else {
            this.Mw();
        }
    }

    public void a(lw_0 lw_02, afn_2 afn_22, String string) {
        this.aPl = lw_02;
        this.aPh = afn_22;
        this.aPp = string;
        this.aPq.clear();
        this.Mu();
    }

    public void a(URL uRL, String string, auh auh2) {
        this.aPo = uRL;
        this.aPp = string;
        this.aPq.clear();
        aAN aAN2 = new aAN();
        aNe aNe2 = new aNe();
        try {
            aAN2.q(new BufferedInputStream(this.aPo.openStream()));
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)("Probl\u00e8me lors du chargement du theme " + exception.getMessage()));
        }
        PU pU = aNe2.aXo();
        this.i(pU);
        this.a((k_0)pU, auh2);
    }

    public void b(URL uRL, String string) {
        this.aPo = uRL;
        this.aPp = string;
        this.aPq.clear();
        this.Mu();
    }

    public adg_2 Mv() {
        return this.aPw;
    }

    public void a(adg_2 adg_22, String string) {
        if (this.aPl != null) {
            this.c(adg_22, string);
        } else {
            this.b(adg_22, string);
        }
    }

    private void b(adg_2 adg_22, String string) {
        k_0 k_02;
        String[] stringArray = string.split("\\$");
        if (stringArray.length == 1) {
            stringArray[0] = adg_22.getTag() + stringArray[0];
        }
        if ((k_02 = (k_0)this.aPs.get(stringArray[0].toUpperCase())) == null) {
            k_02 = (k_0)this.aPs.get(adg_22.getTag().toUpperCase());
        }
        if (k_02 == null) {
            return;
        }
        k_0 k_03 = k_02.c(aOE);
        for (int j = 1; j < stringArray.length; ++j) {
            k_03 = this.a(k_03, stringArray[j]);
        }
        if (k_03 != null) {
            Stack<aji_1> stack = new Stack<aji_1>();
            stack.push(adg_22.getElementMap());
            this.aPi = true;
            this.b(k_03, adg_22, adg_22.getElementMap().azj(), stack);
            this.aPi = false;
        }
    }

    private void c(adg_2 adg_22, String string) {
        assert (this.aPl != null) : "m_styleProvider est null !";
        string = string.contains("$") ? string.toUpperCase() : adg_22.getTag().toUpperCase() + string.toUpperCase();
        vf_2 vf_22 = this.aPl.aS(string);
        if (vf_22 == null && (vf_22 = this.aPl.aS(string = adg_22.getTag().toUpperCase())) == null) {
            return;
        }
        vf_22.a(adg_22.getElementMap(), this, adg_22);
    }

    public void h(k_0 k_02) {
        if (this.aPh == null || this.aPl == null) {
            this.i(k_02);
        } else {
            this.Mw();
        }
    }

    private void Mw() {
        assert (this.aPh != null && this.aPl != null) : "loadInitDirect : variable mal initialis\u00e9e !";
        this.aPh.c(this);
    }

    private void i(k_0 k_02) {
        for (k_0 k_03 : k_02.getChildren()) {
            if (k_03.getName().equals("#text") || k_03.getName().equals("#comment")) continue;
            if (k_03.getName().equalsIgnoreCase(aOD)) {
                for (k_0 k_04 : k_03.getChildren()) {
                    if (k_04.getName().equals("#text") || k_04.getName().equals("#comment")) continue;
                    if (k_04.getName().equalsIgnoreCase("font")) {
                        this.k(k_04);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase("texture")) {
                        this.aPq.add(k_04);
                        this.o(k_04);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase(aOL)) {
                        this.m(k_04);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase(aOM)) {
                        this.n(k_04);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase(aOO)) {
                        this.j(k_04);
                        continue;
                    }
                    String string = k_04.f(aOr) != null ? k_04.f(aOr).getStringValue() : null;
                    if (string == null) continue;
                    this.aPr.put(string.toUpperCase(), k_04);
                }
                continue;
            }
            this.aPs.put(k_03.getName().toUpperCase(), k_03);
        }
    }

    private void a(k_0 k_02, auh auh2) {
        PrintWriter printWriter;
        File file = new File(auh2.aHp() + "\\" + auh2.aHn() + ".java");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {
                a.error((Object)"Exception", (Throwable)iOException);
            }
        }
        try {
            printWriter = new PrintWriter(file);
        }
        catch (FileNotFoundException fileNotFoundException) {
            return;
        }
        sf_1 sf_12 = new sf_1(printWriter, auh2.aHn(), auh2.aHm(), this);
        File file2 = new File(auh2.aHp() + "\\" + auh2.aHo() + ".java");
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            }
            catch (IOException iOException) {
                a.error((Object)"Exception", (Throwable)iOException);
            }
        }
        try {
            printWriter = new PrintWriter(file2);
        }
        catch (FileNotFoundException fileNotFoundException) {
            return;
        }
        this.aPg = new JO(printWriter, auh2.aHo(), auh2.aHm());
        for (k_0 k_03 : k_02.getChildren()) {
            if (k_03.getName().equals("#text") || k_03.getName().equals("#comment")) continue;
            if (k_03.getName().equalsIgnoreCase(aOD)) {
                for (k_0 k_04 : k_03.getChildren()) {
                    if (k_04.getName().equals("#text") || k_04.getName().equals("#comment")) continue;
                    if (k_04.getName().equalsIgnoreCase("font")) {
                        new fF(k_04).a(sf_12);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase("texture")) {
                        new arL(k_04).a(sf_12);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase(aOL)) {
                        new G(k_04).a(sf_12);
                        continue;
                    }
                    if (k_04.getName().equalsIgnoreCase(aOM)) {
                        new yf_1(k_04).a(sf_12);
                        continue;
                    }
                    if (!k_04.getName().equalsIgnoreCase(aOO)) continue;
                    new jq_1(k_04, this).a(sf_12);
                }
                continue;
            }
            this.b(k_03, auh2);
        }
        sf_12.yj();
        this.aPg.yj();
    }

    private void b(k_0 k_02, auh auh2) {
        URL uRL = null;
        try {
            uRL = new URL("file:" + auh2.aHp());
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        String string = k_02.getName().substring(0, 1).toUpperCase() + k_02.getName().substring(1);
        this.aPd = null;
        URL uRL2 = null;
        acx_0 acx_02 = (acx_0)if_1.UG().o(ef_1.class);
        acx_02.eK(true);
        k_0 k_03 = k_02.c(aOE);
        ArrayList arrayList = this.b(k_03, string);
        for (atI atI2 : arrayList) {
            string = atI2.aGN();
            try {
                uRL2 = an_2.a(uRL, string + ".java");
            }
            catch (MalformedURLException malformedURLException) {
                a.error((Object)"Exception", (Throwable)malformedURLException);
            }
            if (uRL2 == null) continue;
            try {
                this.aPe = new PrintWriter(new FileOutputStream(new File(uRL2.getFile())));
            }
            catch (FileNotFoundException fileNotFoundException) {
                a.error((Object)"Exception", (Throwable)fileNotFoundException);
            }
            this.aPf = new acs_1(this.aPe, string, auh2.aHm(), atI2.aGO(), atI2.aGM(), this);
            Stack<aji_1> stack = new Stack<aji_1>();
            aji_1 aji_12 = new aji_1("", new afq_1());
            stack.push(aji_12);
            this.a(this.aPf.ahV(), this.aPf.ahU(), aji_12.azj(), stack);
            this.aPf.yj();
            this.aPg.a(new azw("m_setters.put(\"" + string.toUpperCase() + "\", new " + string + "());"));
        }
        acx_02.eK(false);
    }

    private air_1 dJ(String string) {
        int n2 = string.length();
        for (int j = 0; j < n2; ++j) {
            aLH aLH2 = ye_2.amJ().ij(string.substring(0, j + 1));
            if (aLH2 == null) continue;
            try {
                return (air_1)aLH2.newInstance();
            }
            catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    public void a(float f, vP vP2, vP vP3, vP vP4, String string) {
        aNX.eab = f;
        aNX.dZY = vP2;
        aNX.dZX = vP3;
        aNX.dZZ = vP4;
        af_1 af_12 = (af_1)this.aPt.get(string);
        if (af_12 != null) {
            aNX.eaa = af_12.getFont();
        }
        if (aNX.eaa == null) {
            aNX.eaa = abw_1.kh(string);
        }
    }

    private void j(k_0 k_02) {
        Object object;
        Object object2;
        vP vP2;
        if (!k_02.getName().equalsIgnoreCase(aOO)) {
            return;
        }
        float f = aNX.eab;
        k_0 k_03 = k_02.f(aOR);
        if (k_03 != null) {
            f = k_03.getFloatValue();
        }
        vP vP3 = aNX.dZY;
        pa_2 pa_22 = (pa_2)if_1.UG().o(vP.class);
        k_03 = k_02.f(aOS);
        if (k_03 != null) {
            vP2 = vP3;
            vP3 = this.dM(k_03.getStringValue());
            if (vP3 == null) {
                vP3 = pa_22.b(vP.class, k_03.getStringValue());
            }
            if (vP3 == null) {
                vP3 = vP2;
            }
        }
        vP2 = aNX.dZX;
        k_03 = k_02.f(aOP);
        if (k_03 != null) {
            object2 = vP2;
            vP2 = this.dM(k_03.getStringValue());
            if (vP2 == null) {
                vP2 = pa_22.b(vP.class, k_03.getStringValue());
            }
            if (vP2 == null) {
                vP2 = object2;
            }
        }
        object2 = aNX.dZZ;
        k_03 = k_02.f(aOQ);
        if (k_03 != null) {
            object = object2;
            object2 = this.dM(k_03.getStringValue());
            if (object2 == null) {
                object2 = pa_22.b(vP.class, k_03.getStringValue());
            }
            if (object2 == null) {
                object2 = object;
            }
        }
        object = null;
        k_03 = k_02.f("font");
        if (k_03 != null) {
            object = k_03.getStringValue();
        }
        this.a(f, vP3, vP2, (vP)object2, (String)object);
    }

    public void a(String string, String string2, String string3, boolean bl2) {
        int n2;
        String string4 = "default";
        int n3 = (string2 = string2.toLowerCase()).indexOf(47);
        if (n3 >= 0) {
            ++n3;
        }
        if ((n2 = string2.lastIndexOf(".ttf")) < 0) {
            n2 = string2.length();
        }
        string4 = string2.substring(n3, n2) + string3;
        abw_1.kg(this.aPp + string2.substring(0, n3));
        String string5 = abw_1.getType(string4);
        int n4 = abw_1.ki(string4);
        int n5 = abw_1.kj(string4);
        if (bl2) {
            n4 |= 4;
        }
        ma_1 ma_12 = abw_1.e(string5, n4, n5);
        this.aPt.put(string, aFM.b(ma_12));
    }

    private void k(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("font") || k_02.f("font") == null || k_02.f(aOr) == null) {
            return;
        }
        boolean bl2 = false;
        if (k_02.f(aPb) != null) {
            bl2 = k_02.f(aPb).getBooleanValue();
        }
        k_0 k_03 = k_02.f(aOt);
        String string = k_02.f("font").getStringValue();
        String string2 = k_02.f(aOr).getStringValue();
        this.a(string2, k_03 != null ? k_03.getStringValue() : null, string, bl2);
    }

    public void a(String string, vP vP2) {
        this.aPk.put(string, vP2);
    }

    public void l(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("color") || k_02.f("color") == null || k_02.f(aOr) == null) {
            return;
        }
        k_0 k_03 = k_02.f("color");
        this.a(k_02.f(aOr).getStringValue(), if_1.UG().eK(k_03.getStringValue()));
    }

    public void a(String string, xy_0 xy_02, int n2, int n3) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(this.aPp + string));
            apw_1.aDr().a(xy_02, n2, n3, bufferedImage);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Impossible de cr\u00e9er le curseur");
            if (string != null) {
                stringBuilder.append(" : ").append(string);
            }
            a.error((Object)stringBuilder.toString(), (Throwable)exception);
        }
    }

    private void m(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase(aOL) || k_02.f(aOt) == null || k_02.f(aOr) == null) {
            return;
        }
        k_0 k_03 = k_02.f(aOt);
        if (k_03 != null) {
            k_0 k_04 = k_02.f(aOT);
            k_0 k_05 = k_02.f(aOU);
            k_0 k_06 = k_02.f(aOX);
            int n2 = k_04 == null ? 0 : k_04.getIntValue();
            int n3 = k_05 == null ? 0 : k_05.getIntValue();
            xy_0 xy_02 = k_06 == null ? xy_0.bYl : xy_0.valueOf(k_06.getStringValue().toUpperCase());
            this.a(k_03.getStringValue(), xy_02, n2, n3);
        }
    }

    public void a(xy_0 xy_02, int n2, int n3, int n4, ArrayList arrayList) {
        try {
            ArrayList<BufferedImage> arrayList2 = new ArrayList<BufferedImage>(arrayList.size());
            int n5 = arrayList.size();
            for (int j = 0; j < n5; ++j) {
                String string = (String)arrayList.get(j);
                arrayList2.add(ImageIO.read(new URL(this.aPp + string)));
            }
            apw_1.aDr().b(xy_02, n2, n3, n4, arrayList2);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Impossible de cr\u00e9er le curseur");
            a.error((Object)stringBuilder.toString(), (Throwable)exception);
        }
    }

    private void n(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase(aOM) || k_02.f(aOr) == null) {
            return;
        }
        try {
            k_0 k_03 = k_02.f(aOT);
            k_0 k_04 = k_02.f(aOU);
            k_0 k_05 = k_02.f(aOY);
            k_0 k_06 = k_02.f(aOX);
            int n2 = k_03 == null ? 0 : k_03.getIntValue();
            int n3 = k_04 == null ? 0 : k_04.getIntValue();
            int n4 = k_05 == null ? 500 : k_05.getIntValue();
            xy_0 xy_02 = k_06 == null ? xy_0.bYl : xy_0.valueOf(k_06.getStringValue().toUpperCase());
            ArrayList arrayList = k_02.d(aON);
            ArrayList<String> arrayList2 = new ArrayList<String>(arrayList.size());
            int n5 = arrayList.size();
            for (int j = 0; j < n5; ++j) {
                k_0 k_07 = (k_0)arrayList.get(j);
                k_0 k_08 = k_07.f(aOt);
                arrayList2.add(k_08.getStringValue());
            }
            this.a(xy_02, n2, n3, n4, arrayList2);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Impossible de cr\u00e9er le curseur");
            a.error((Object)stringBuilder.toString(), (Throwable)exception);
        }
    }

    public void a(String string, String string2, boolean bl2) {
        if (string == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/xulor2/core/DocumentParser.loadTexture must not be null");
        }
        if (string2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/xulor2/core/DocumentParser.loadTexture must not be null");
        }
        acx_0 acx_02 = (acx_0)if_1.UG().o(ef_1.class);
        try {
            String string3 = this.aPp + string2;
            if (bl2) {
                ef_1 ef_12 = acx_02.m(ef_1.class, string3);
                ef_12.HE();
            }
            this.aPu.put(string, string3);
        }
        catch (Exception exception) {
            a.error((Object)"Impossible de cr\u00e9er l'instance de texture", (Throwable)exception);
        }
    }

    private void o(k_0 k_02) {
        if (!k_02.getName().equalsIgnoreCase("texture") || k_02.f(aOt) == null || k_02.f(aOr) == null) {
            return;
        }
        acx_0 acx_02 = (acx_0)if_1.UG().o(ef_1.class);
        try {
            if (k_02.f(aOt) != null) {
                String string = this.aPp + k_02.f(aOt).getStringValue();
                k_0 k_03 = k_02.f(aPc);
                if (k_03 != null && k_03.getBooleanValue()) {
                    ef_1 ef_12 = acx_02.m(ef_1.class, string);
                    ef_12.HE();
                }
                this.aPu.put(k_02.f(aOr).getStringValue(), string);
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible de cr\u00e9er l'instance de texture", (Throwable)exception);
        }
    }

    public void Mx() {
        for (k_0 k_02 : this.aPq) {
            this.o(k_02);
        }
        this.aPv = false;
    }

    public boolean My() {
        return this.aPv;
    }

    public void bg(boolean bl2) {
        this.Mx();
    }

    public ef_1 dK(String string) {
        String string2 = (String)this.aPu.get(string);
        if (string2 == null) {
            return null;
        }
        qM.reset();
        qM.update(string2.getBytes());
        long l2 = qM.getValue();
        ef_1 ef_12 = cx_0.JY().bt(l2);
        if (ef_12 == null) {
            acx_0 acx_02 = (acx_0)if_1.UG().o(ef_1.class);
            ef_12 = acx_02.m(ef_1.class, string2);
        }
        return ef_12;
    }

    public af_1 dL(String string) {
        return (af_1)this.aPt.get(string);
    }

    public vP dM(String string) {
        k_0 k_02 = (k_0)this.aPr.get(string.toUpperCase());
        if (k_02 == null || !k_02.getName().equalsIgnoreCase("color")) {
            return null;
        }
        if ((k_02 = k_02.f("color")) == null) {
            return null;
        }
        apG apG2 = if_1.UG().o(vP.class);
        return (vP)apG2.c(vP.class, k_02.getStringValue());
    }

    public void Mz() {
    }

    public void MA() {
    }
}

