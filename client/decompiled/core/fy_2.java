/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.xml.sax.AttributeList;
import org.xml.sax.helpers.AttributeListImpl;

/*
 * Renamed from Fy
 */
public class fy_2
implements Serializable {
    private static final Hashtable aUQ = new Hashtable(0);
    private String aUR = null;
    private List aUS = null;
    private transient Object aUT = null;
    private transient ud_0 aUU;
    private transient AttributeList aUV;
    private List aUW = null;
    private Map aUX = null;
    private StringBuffer aUY = null;
    private boolean aUZ = false;
    private String aqI = null;
    private String id = null;

    public fy_2(Object object, String string) {
        this.P(object);
        this.dT(string);
        if (object instanceof dm_1) {
            ((dm_1)object).a(this);
        }
    }

    public synchronized void P(Object object) {
        this.aUT = object;
        this.aUZ = false;
    }

    synchronized void a(ud_0 ud_02) {
        this.aUU = ud_02;
    }

    public synchronized Object OV() {
        return this.aUT;
    }

    public synchronized String getId() {
        return this.id;
    }

    public synchronized String OW() {
        return this.aqI;
    }

    public synchronized void cy(String string) {
        this.aqI = string;
    }

    public synchronized void a(AttributeList attributeList) {
        this.aUV = new AttributeListImpl(attributeList);
        for (int j = 0; j < attributeList.getLength(); ++j) {
            this.setAttribute(attributeList.getName(j), attributeList.getValue(j));
        }
    }

    public synchronized void setAttribute(String string, String string2) {
        if (string.equalsIgnoreCase("ant-type")) {
            this.aqI = string2;
        } else {
            if (this.aUW == null) {
                this.aUW = new ArrayList();
                this.aUX = new HashMap();
            }
            if (string.toLowerCase(Locale.US).equals("refid")) {
                this.aUW.add(0, string);
            } else {
                this.aUW.add(string);
            }
            this.aUX.put(string, string2);
            if (string.equals("id")) {
                this.id = string2;
            }
        }
    }

    public synchronized void removeAttribute(String string) {
        this.aUW.remove(string);
        this.aUX.remove(string);
    }

    public synchronized Hashtable OX() {
        return this.aUX == null ? aUQ : new Hashtable(this.aUX);
    }

    public synchronized AttributeList OY() {
        return this.aUV;
    }

    public synchronized void b(fy_2 fy_22) {
        this.aUS = this.aUS == null ? new ArrayList() : this.aUS;
        this.aUS.add(fy_22);
    }

    synchronized fy_2 fC(int n2) {
        return (fy_2)this.aUS.get(n2);
    }

    public synchronized Enumeration OZ() {
        return this.aUS == null ? new jj_2() : Collections.enumeration(this.aUS);
    }

    public synchronized void addText(String string) {
        if (string.length() == 0) {
            return;
        }
        this.aUY = this.aUY == null ? new StringBuffer(string) : this.aUY.append(string);
    }

    public synchronized void b(char[] cArray, int n2, int n3) {
        if (n3 == 0) {
            return;
        }
        this.aUY = (this.aUY == null ? new StringBuffer(n3) : this.aUY).append(cArray, n2, n3);
    }

    public synchronized StringBuffer Pa() {
        return this.aUY == null ? new StringBuffer(0) : this.aUY;
    }

    public synchronized void dT(String string) {
        this.aUR = string;
    }

    public synchronized String Pb() {
        return this.aUR;
    }

    public void m(UI uI) {
        this.b(uI, true);
    }

    public synchronized void b(UI uI, boolean bl2) {
        if (this.aUZ) {
            return;
        }
        Object object = this.aUT instanceof akm ? ((akm)this.aUT).OV() : this.aUT;
        hm_2 hm_22 = hm_2.a(uI, object.getClass());
        if (this.aUW != null) {
            for (int j = 0; j < this.aUW.size(); ++j) {
                String string = (String)this.aUW.get(j);
                String string2 = (String)this.aUX.get(string);
                string2 = uI.fZ(string2);
                try {
                    hm_22.a(uI, object, string, string2);
                    continue;
                }
                catch (tv_2 tv_22) {
                    if (string.equals("id")) continue;
                    if (this.Pb() == null) {
                        throw tv_22;
                    }
                    throw new eq_2(this.Pb() + " doesn't support the \"" + tv_22.getAttribute() + "\" attribute", tv_22);
                }
                catch (eq_2 eq_22) {
                    if (string.equals("id")) continue;
                    throw eq_22;
                }
            }
        }
        if (this.aUY != null) {
            es_2.b(uI, this.aUT, this.aUY.substring(0));
        }
        if (this.id != null) {
            uI.o(this.id, this.aUT);
        }
        this.aUZ = true;
    }

    public void n(UI uI) {
        this.aUZ = false;
        this.m(uI);
    }

    public void c(fy_2 fy_22) {
        Object object;
        if (fy_22.aUX != null) {
            object = fy_22.aUX.keySet().iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                if (this.aUX != null && this.aUX.get(string) != null) continue;
                this.setAttribute(string, (String)fy_22.aUX.get(string));
            }
        }
        String string = this.aqI = this.aqI == null ? fy_22.aqI : this.aqI;
        if (fy_22.aUS != null) {
            object = new ArrayList();
            object.addAll(fy_22.aUS);
            if (this.aUS != null) {
                object.addAll(this.aUS);
            }
            this.aUS = object;
        }
        if (fy_22.aUY != null && (this.aUY == null || this.aUY.toString().trim().length() == 0)) {
            this.aUY = new StringBuffer(fy_22.aUY.toString());
        }
    }
}

