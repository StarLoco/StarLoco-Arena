/*
 * Decompiled with CFR 0.152.
 */
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*
 * Renamed from aFc
 */
public class afc_2 {
    private UI hL;
    private afc_2 dEV;
    private Hashtable dEW = new Hashtable();
    private Hashtable dEX = new Hashtable();
    private Hashtable dEY = new Hashtable();

    protected afc_2() {
    }

    public void l(UI uI) {
        this.hL = uI;
    }

    public UI TP() {
        return this.hL;
    }

    public void a(afc_2 afc_22) {
        this.dEV = afc_22;
    }

    public afc_2 aRq() {
        return this.dEV;
    }

    public static synchronized afc_2 W(UI uI) {
        afc_2 afc_22 = (afc_2)uI.gi("ant.PropertyHelper");
        if (afc_22 != null) {
            return afc_22;
        }
        afc_22 = new afc_2();
        afc_22.l(uI);
        uI.o("ant.PropertyHelper", afc_22);
        return afc_22;
    }

    public boolean a(String string, String string2, Object object, boolean bl2, boolean bl3, boolean bl4) {
        boolean bl5;
        return this.aRq() != null && (bl5 = this.aRq().a(string, string2, object, bl2, bl3, bl4));
    }

    public Object i(String string, String string2, boolean bl2) {
        Object object;
        if (this.aRq() != null && (object = this.aRq().i(string, string2, bl2)) != null) {
            return object;
        }
        if (string2.startsWith("toString:")) {
            object = this.hL.gi(string2 = string2.substring("toString:".length()));
            return object == null ? null : object.toString();
        }
        return null;
    }

    public void a(String string, Vector vector, Vector vector2) {
        afc_2.b(string, vector, vector2);
    }

    public String a(String string, String string2, Hashtable hashtable) {
        if (string2 == null || string2.indexOf(36) == -1) {
            return string2;
        }
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        this.a(string2, vector, vector2);
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration enumeration = vector.elements();
        Enumeration enumeration2 = vector2.elements();
        while (enumeration.hasMoreElements()) {
            String string3 = (String)enumeration.nextElement();
            if (string3 == null) {
                String string4 = (String)enumeration2.nextElement();
                Object object = null;
                if (hashtable != null) {
                    object = hashtable.get(string4);
                }
                if (object == null) {
                    object = this.getProperty(string, string4);
                }
                if (object == null) {
                    this.hL.l("Property \"" + string4 + "\" has not been set", 3);
                }
                string3 = object != null ? object.toString() : "${" + string4 + "}";
            }
            stringBuffer.append(string3);
        }
        return stringBuffer.toString();
    }

    public synchronized boolean a(String string, String string2, Object object, boolean bl2) {
        if (null != this.dEX.get(string2)) {
            if (bl2) {
                this.hL.l("Override ignored for user property \"" + string2 + "\"", 3);
            }
            return false;
        }
        boolean bl3 = this.a(string, string2, object, false, false, false);
        if (bl3) {
            return true;
        }
        if (null != this.dEW.get(string2) && bl2) {
            this.hL.l("Overriding previous definition of property \"" + string2 + "\"", 3);
        }
        if (bl2) {
            this.hL.l("Setting project property: " + string2 + " -> " + object, 4);
        }
        if (string2 != null && object != null) {
            this.dEW.put(string2, object);
        }
        return true;
    }

    public synchronized void d(String string, String string2, Object object) {
        if (null != this.dEW.get(string2)) {
            this.hL.l("Override ignored for property \"" + string2 + "\"", 3);
            return;
        }
        boolean bl2 = this.a(string, string2, object, false, false, true);
        if (bl2) {
            return;
        }
        this.hL.l("Setting project property: " + string2 + " -> " + object, 4);
        if (string2 != null && object != null) {
            this.dEW.put(string2, object);
        }
    }

    public synchronized void e(String string, String string2, Object object) {
        this.hL.l("Setting ro project property: " + string2 + " -> " + object, 4);
        this.dEX.put(string2, object);
        boolean bl2 = this.a(string, string2, object, false, true, false);
        if (bl2) {
            return;
        }
        this.dEW.put(string2, object);
    }

    public synchronized void f(String string, String string2, Object object) {
        this.dEY.put(string2, object);
        this.hL.l("Setting ro project property: " + string2 + " -> " + object, 4);
        this.dEX.put(string2, object);
        boolean bl2 = this.a(string, string2, object, true, false, false);
        if (bl2) {
            return;
        }
        this.dEW.put(string2, object);
    }

    public synchronized Object getProperty(String string, String string2) {
        if (string2 == null) {
            return null;
        }
        Object object = this.i(string, string2, false);
        if (object != null) {
            return object;
        }
        return this.dEW.get(string2);
    }

    public synchronized Object as(String string, String string2) {
        if (string2 == null) {
            return null;
        }
        Object object = this.i(string, string2, true);
        if (object != null) {
            return object;
        }
        return this.dEX.get(string2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Hashtable ahc() {
        Hashtable hashtable = this.dEW;
        synchronized (hashtable) {
            return new Hashtable(this.dEW);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Hashtable ahd() {
        Hashtable hashtable = this.dEX;
        synchronized (hashtable) {
            return new Hashtable(this.dEX);
        }
    }

    protected Hashtable aRr() {
        return this.dEW;
    }

    protected Hashtable aRs() {
        return this.dEX;
    }

    protected Hashtable aRt() {
        return this.dEY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void z(UI uI) {
        Hashtable hashtable = this.dEY;
        synchronized (hashtable) {
            Enumeration enumeration = this.dEY.keys();
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement().toString();
                if (uI.ga(string) != null) continue;
                Object v = this.dEY.get(string);
                uI.F(string, v.toString());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void y(UI uI) {
        Hashtable hashtable = this.dEX;
        synchronized (hashtable) {
            Enumeration enumeration = this.dEX.keys();
            while (enumeration.hasMoreElements()) {
                Object k2 = enumeration.nextElement();
                if (this.dEY.containsKey(k2)) continue;
                Object v = this.dEX.get(k2);
                uI.E(k2.toString(), v.toString());
            }
        }
    }

    static void b(String string, Vector vector, Vector vector2) {
        int n2;
        int n3 = 0;
        while ((n2 = string.indexOf("$", n3)) >= 0) {
            if (n2 > 0) {
                vector.addElement(string.substring(n3, n2));
            }
            if (n2 == string.length() - 1) {
                vector.addElement("$");
                n3 = n2 + 1;
                continue;
            }
            if (string.charAt(n2 + 1) != '{') {
                if (string.charAt(n2 + 1) == '$') {
                    vector.addElement("$");
                    n3 = n2 + 2;
                    continue;
                }
                vector.addElement(string.substring(n2, n2 + 2));
                n3 = n2 + 2;
                continue;
            }
            int n4 = string.indexOf(125, n2);
            if (n4 < 0) {
                throw new eq_2("Syntax error in property: " + string);
            }
            String string2 = string.substring(n2 + 2, n4);
            vector.addElement(null);
            vector2.addElement(string2);
            n3 = n4 + 1;
        }
        if (n3 < string.length()) {
            vector.addElement(string.substring(n3));
        }
    }
}

