/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;

/*
 * Renamed from afL
 */
public class afl_0
implements yw_1 {
    private static Logger a = Logger.getLogger(afl_0.class);
    private static boolean DEBUG = false;
    public static final String crH = "/";
    private String m_name;
    private Object dE = null;
    private boolean dF = false;
    private final List crI = Collections.synchronizedList(new ArrayList());
    private ConcurrentLinkedQueue crJ = new ConcurrentLinkedQueue();
    private ArrayList crK = new ArrayList();
    private afl_0 crL = null;
    private boolean crM = false;
    private boolean crN = false;
    private int crO;
    private String crP = null;
    private aji_1 blb = null;
    private ArrayList crQ = new ArrayList();
    public static final String crR = "#";

    public afl_0(String string, aji_1 aji_12) {
        this(string, aji_12, false);
    }

    public afl_0(String string, aji_1 aji_12, boolean bl2) {
        this.m_name = string;
        this.crM = bl2;
        this.blb = aji_12;
        if (this.blb != null) {
            this.blb.b(this);
        }
    }

    public afl_0(String string, afl_0 afl_02, String string2, aji_1 aji_12) {
        this.m_name = string;
        this.crM = true;
        this.crP = string2;
        this.blb = aji_12;
        if (this.blb != null) {
            this.blb.b(this);
        }
        if (afl_02 != null) {
            afl_02.c(this);
        }
    }

    public afl_0(String string, afl_0 afl_02, int n2, aji_1 aji_12) {
        this.m_name = string;
        this.crM = true;
        this.crL = afl_02;
        this.crN = true;
        this.crO = n2;
        this.blb = aji_12;
        if (this.blb != null) {
            this.blb.b(this);
        }
    }

    public String getName() {
        return this.m_name;
    }

    public String avm() {
        return this.crP;
    }

    public void c(sm_0 sm_02) {
        this.crQ.add(sm_02);
    }

    public void d(sm_0 sm_02) {
        this.crQ.remove(sm_02);
        if (this.crQ.size() == 0 && this.crI.size() == 0 && this.crK.size() == 0) {
            azs_0.aLV().e(this);
        }
    }

    public void setElementMap(aji_1 aji_12) {
        this.blb = aji_12;
    }

    public boolean isLocal() {
        return this.blb != null;
    }

    public boolean avn() {
        return this.dF;
    }

    public aji_1 getElementMap() {
        return this.blb;
    }

    public Object getValue() {
        return this.dE;
    }

    public String getString() {
        return Gr.getString(this.dE);
    }

    public boolean getBoolean() {
        return Gr.getBoolean(this.dE);
    }

    public int getInt() {
        return Gr.R(this.dE);
    }

    public short getShort() {
        return Gr.getShort(this.dE);
    }

    public double getDouble() {
        return Gr.getDouble(this.dE);
    }

    public float getFloat() {
        return Gr.getFloat(this.dE);
    }

    public long getLong() {
        return Gr.getLong(this.dE);
    }

    public boolean isEmpty() {
        if (this.dE instanceof String) {
            return this.dE.equals("");
        }
        return this.dE == null;
    }

    public static Object c(Object object, String string) {
        if (object instanceof aho_0 && string != null) {
            pf_0 pf_02 = afl_0.d(object, string);
            if (pf_02.getFirst() instanceof aho_0 && pf_02.acl() != null) {
                aho_0 aho_02 = (aho_0)pf_02.getFirst();
                return aho_02.getFieldValue((String)pf_02.acl());
            }
            return null;
        }
        return object;
    }

    public static pf_0 d(Object object, String string) {
        pf_0 pf_02 = new pf_0(object, null);
        String[] stringArray = string.split(crH);
        for (int j = 0; j < stringArray.length - 1; ++j) {
            if (!(object instanceof aho_0) || stringArray[j] == null) {
                pf_02.ad(null);
                return pf_02;
            }
            object = ((aho_0)object).getFieldValue(stringArray[j]);
            pf_02.ac(object);
        }
        pf_02.ad(stringArray[stringArray.length - 1]);
        return pf_02;
    }

    private Object getValue(String string) {
        if (this.dE instanceof aho_0 && string != null) {
            aho_0 aho_02 = (aho_0)this.dE;
            return aho_02.getFieldValue(string);
        }
        return this.dE;
    }

    public Object hU(String string) {
        return this.getValue(string);
    }

    public String hV(String string) {
        return Gr.getString(this.getValue(string));
    }

    public boolean hW(String string) {
        return Gr.getBoolean(this.getValue(string));
    }

    public int hX(String string) {
        return Gr.R(this.getValue(string));
    }

    public long hY(String string) {
        return Gr.getLong(this.getValue(string));
    }

    public double hZ(String string) {
        return Gr.getDouble(this.getValue(string));
    }

    public float ia(String string) {
        return Gr.getFloat(this.getValue(string));
    }

    public void avo() {
        aho_0[] aho_0Array = null;
        if (this.dE instanceof aho_0[]) {
            aho_0Array = (aho_0[])this.dE;
        }
        for (int j = 0; j < this.crK.size(); ++j) {
            afl_0 afl_02 = (afl_0)this.crK.get(j);
            if (afl_02.crN && aho_0Array != null && aho_0Array.length > afl_02.crO) {
                afl_02.setValue(aho_0Array[afl_02.crO]);
                continue;
            }
            afl_02.setValue(this.getValue(afl_02.avm()));
        }
    }

    public boolean avp() {
        return !this.crJ.isEmpty();
    }

    public void avq() {
        aiw aiw2 = (aiw)this.crJ.poll();
        while (aiw2 != null) {
            aiw2.cyJ.a(null);
            if (apv_1.epm.equals((Object)aiw2.cyK)) {
                this.d(aiw2.cyJ, aiw2.dE);
            } else if (apv_1.epo.equals((Object)aiw2.cyK)) {
                this.e(aiw2.cyJ, aiw2.dE);
            } else if (apv_1.epn.equals((Object)aiw2.cyK)) {
                this.f(aiw2.cyJ, aiw2.dE);
            }
            aiw2 = (aiw)this.crJ.poll();
        }
    }

    public void a(ahb_0 ahb_02, boolean bl2) {
        String string = ahb_02.getFieldName().substring(0, ahb_02.getFieldName().indexOf(crH));
        afl_0 afl_02 = null;
        for (afl_0 afl_03 : this.crK) {
            if (!afl_03.avm().equalsIgnoreCase(string)) continue;
            afl_02 = afl_03;
            break;
        }
        if (afl_02 == null) {
            afl_02 = new afl_0(afl_0.a(this, string), this, string, this.blb);
            afl_02.setValue(this.hU(string));
            azs_0.aLV().b(afl_02);
        }
        ahb_02.id(ahb_02.getFieldName().substring(ahb_02.getFieldName().indexOf(crH) + 1));
        afl_02.b(ahb_02, bl2);
    }

    public void a(ahb_0 ahb_02) {
        this.b(ahb_02, false);
    }

    public void b(ahb_0 ahb_02, boolean bl2) {
        Object object;
        if (ahb_02.getFieldName() != null && ahb_02.getFieldName().contains(crH)) {
            this.a(ahb_02, bl2);
            return;
        }
        if (!this.crI.contains(ahb_02)) {
            this.crI.add(ahb_02);
            ((air_1)ahb_02.getElement()).b(this);
        } else {
            a.error((Object)("Ajout d'un client \u00e0 une propri\u00e9t\u00e9 qui le contient d\u00e9j\u00e0 : " + ahb_02));
        }
        if (ahb_02.getResultProvider() != null) {
            ahb_02.getResultProvider().setResultProviderParent(this);
        }
        if (ahb_02.awN()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.m_name);
            if (ahb_02.getFieldName() != null) {
                ((StringBuilder)object).append(crH).append(ahb_02.getFieldName());
            }
            ((Fc)ahb_02.getElement()).setContentProperty(((StringBuilder)object).toString(), this.blb);
        }
        if (this.dF) {
            object = null;
            object = this.dE instanceof aho_0 && ahb_02.getFieldName() != null ? ((aho_0)this.dE).getFieldValue(ahb_02.getFieldName()) : this.dE;
            if (bl2) {
                if (ahb_02.getResultProvider() != null) {
                    object = ahb_02.getResultProvider().getResult(object);
                }
                this.d(ahb_02, object);
            } else {
                this.a(ahb_02, object);
            }
        }
    }

    public void i(air_1 air_12) {
        if (air_12 == null) {
            return;
        }
        Iterator iterator = this.crJ.iterator();
        while (iterator.hasNext()) {
            if (((aiw)iterator.next()).cyJ.getElement() != air_12) continue;
            iterator.remove();
        }
        ArrayList<ahb_0> arrayList = new ArrayList<ahb_0>();
        for (ahb_0 ahb_02 : this.crI) {
            if (!ahb_02.getElement().equals(air_12)) continue;
            arrayList.add(ahb_02);
            if (!ahb_02.awN()) continue;
            ((Fc)ahb_02.getElement()).setContentProperty(null, null);
        }
        this.crI.removeAll(arrayList);
        air_12.e(this);
        if (this.crM && this.crQ.size() == 0 && this.crI.size() == 0 && this.crK.size() == 0) {
            if (this.crL != null) {
                this.crL.d(this);
            }
            azs_0.aLV().e(this);
        }
    }

    public ahb_0 a(air_1 air_12, String string) {
        if (string == null) {
            return null;
        }
        for (ahb_0 ahb_02 : this.crI) {
            if (air_12 != ahb_02.getElement() || !string.equalsIgnoreCase(ahb_02.getAttribute())) continue;
            return ahb_02;
        }
        return null;
    }

    public void c(afl_0 afl_02) {
        afl_02.crL = this;
        this.crK.add(afl_02);
    }

    public void d(afl_0 afl_02) {
        this.crK.remove(afl_02);
        if (this.crM && this.crI.size() == 0 && this.crK.size() == 0 && this.crL != null) {
            this.crL.d(this);
        }
    }

    public afl_0 ib(String string) {
        assert (string != null) : "On essaye de r\u00e9cup\u00e9rer une propri\u00e9t\u00e9 enfant avec un nom null !";
        for (int j = this.crK.size() - 1; j >= 0; --j) {
            afl_0 afl_02 = (afl_0)this.crK.get(j);
            if (!string.equals(afl_02.avm())) continue;
            return afl_02;
        }
        return null;
    }

    protected void a(ahb_0 ahb_02, Object object) {
        if (DEBUG) {
            this.d(ahb_02, object);
        } else {
            aiw aiw2 = ahb_02.awS();
            if (aiw2 != null) {
                if (aiw2.cyJ == ahb_02 && aiw2.cyK != null && aiw2.cyK.equals((Object)apv_1.epm)) {
                    aiw2.dE = aiw2.cyJ.getResultProvider() != null ? aiw2.cyJ.getResultProvider().getResult(object) : object;
                }
            } else {
                this.crJ.offer(new aiw(this, ahb_02, object, apv_1.epm));
            }
            azs_0.aLV().f(this);
        }
    }

    protected void b(ahb_0 ahb_02, Object object) {
        if (DEBUG) {
            this.f(ahb_02, object);
        } else {
            this.crJ.offer(new aiw(this, ahb_02, object, apv_1.epn));
            azs_0.aLV().f(this);
        }
    }

    protected void c(ahb_0 ahb_02, Object object) {
        if (DEBUG) {
            this.e(ahb_02, object);
        } else {
            this.crJ.offer(new aiw(this, ahb_02, object, apv_1.epo));
            azs_0.aLV().f(this);
        }
    }

    private void d(ahb_0 ahb_02, Object object) {
        int n2 = ahb_02.getAttributeHash();
        if (object instanceof String && yt_1.caS != n2 && alt_0.dL != n2 && alt_0.cFq != n2 ? ((air_1)ahb_02.getElement()).setXMLAttribute(ahb_02.getAttribute(), (String)object) : ((air_1)ahb_02.getElement()).setPropertyAttribute(ahb_02.getAttribute(), object)) {
            return;
        }
        Method method = ahb_02.awO();
        if (method == null || !bz_1.b(method, object)) {
            method = ahb_02.awM().h(ahb_02.getAttribute(), object == null ? null : object.getClass());
        }
        if (method != null) {
            this.a(method, ahb_02, object);
        } else {
            a.error((Object)("[" + this.m_name + "]Impossible de trouver la m\u00e9thode set" + ahb_02.getAttribute() + " dans " + ahb_02.getElement() + " avec la classe " + (object == null ? null : object.getClass())));
        }
    }

    private void e(ahb_0 ahb_02, Object object) {
        int n2 = ahb_02.getAttributeHash();
        if (object instanceof String && yt_1.caS != n2 && alt_0.dL != n2 && alt_0.cFq != n2 ? ((air_1)ahb_02.getElement()).W(ahb_02.getAttribute(), (String)object) : ((air_1)ahb_02.getElement()).r(ahb_02.getAttribute(), object)) {
            return;
        }
        Method method = ahb_02.awQ();
        if (!bz_1.b(method, object)) {
            method = ahb_02.awM().k(ahb_02.getAttribute(), object == null ? null : object.getClass());
        }
        if (method != null) {
            this.a(method, ahb_02, object);
        } else {
            a.error((Object)("Impossible de trouver la m\u00e9thode prepend" + ahb_02.getAttribute() + " avec la classe " + (object == null ? null : object.getClass())));
        }
    }

    private void f(ahb_0 ahb_02, Object object) {
        int n2 = ahb_02.getAttributeHash();
        if (object instanceof String && yt_1.caS != n2 && alt_0.dL != n2 && alt_0.cFq != n2 ? ((air_1)ahb_02.getElement()).V(ahb_02.getAttribute(), (String)object) : ((air_1)ahb_02.getElement()).q(ahb_02.getAttribute(), object)) {
            return;
        }
        Method method = ahb_02.awP();
        if (!bz_1.b(method, object)) {
            method = ahb_02.awM().j(ahb_02.getAttribute(), object == null ? null : object.getClass());
        }
        if (method != null) {
            this.a(method, ahb_02, object);
        } else {
            a.error((Object)("Impossible de trouver la m\u00e9thode append" + ahb_02.getAttribute() + " dans " + ahb_02.getElement() + " avec la classe " + (object == null ? null : object.getClass())));
        }
    }

    protected void a(Method method, ahb_0 ahb_02, Object object) {
        try {
            if (method.getParameterTypes().length == 0) {
                return;
            }
            bz_1.a(method, ahb_02.getElement(), new Object[]{object});
        }
        catch (IllegalArgumentException illegalArgumentException) {
            a.error((Object)("Exception illegalArgument : " + illegalArgumentException));
        }
        catch (Exception exception) {
            a.error((Object)("[" + exception.getClass().getSimpleName() + "] Erreur lors du InvokeMethodAccessor - Method=" + (method == null ? "null" : method.getName()) + " - PropertyClientData = " + ahb_02 + " - Value = " + object));
        }
    }

    public void setValue(Object object) {
        if (!this.dF) {
            this.dF = true;
            azs_0.aLV().a(avi.ddK, this);
        }
        if (object == this.dE) {
            return;
        }
        this.dE = object;
        this.avo();
        for (ahb_0 ahb_02 : this.crI) {
            String string = ahb_02.getFieldName();
            if (object instanceof aho_0 && string != null) {
                aho_0 aho_02 = (aho_0)object;
                Object object2 = aho_02.getFieldValue(string);
                this.a(ahb_02, object2);
                continue;
            }
            this.a(ahb_02, object);
        }
    }

    public void ax(Object object) {
        for (ahb_0 ahb_02 : this.crI) {
            this.c(ahb_02, object);
        }
    }

    public void ay(Object object) {
        for (ahb_0 ahb_02 : this.crI) {
            this.b(ahb_02, object);
        }
    }

    public void a(String string, Object object) {
        if (this.dE instanceof aho_0 && string != null) {
            aho_0 aho_02 = (aho_0)this.dE;
            aho_02.a(string, object);
            for (ahb_0 ahb_02 : this.crI) {
                String string2 = ahb_02.getFieldName();
                if (string2 == null || !string2.equals(string)) continue;
                this.a(ahb_02, object);
            }
        }
    }

    public void a(String string, asz asz2) {
        if (this.dE != null && this.dE instanceof aho_0) {
            aho_0 aho_02 = (aho_0)this.dE;
            ahb_0[] ahb_0Array = new ahb_0[this.crI.size()];
            this.crI.toArray(ahb_0Array);
            int n2 = string != null ? string.hashCode() : 0;
            Object object = asz2 != null ? asz2.get(n2) : null;
            boolean bl2 = object != null;
            for (ahb_0 ahb_02 : ahb_0Array) {
                if (string == null || !string.equals(ahb_02.getFieldName())) continue;
                if (!bl2) {
                    object = aho_02.getFieldValue(string);
                    if (asz2 != null) {
                        asz2.put(n2, object);
                    }
                    bl2 = true;
                }
                this.a(ahb_02, object);
            }
            this.avo();
        }
    }

    public void avr() {
        if (this.dE instanceof aho_0) {
            aho_0 aho_02 = (aho_0)this.dE;
            for (ahb_0 ahb_02 : this.crI) {
                String string = ahb_02.getFieldName();
                if (string != null) {
                    this.a(ahb_02, aho_02.getFieldValue(string));
                    continue;
                }
                this.a(ahb_02, this.dE);
            }
            this.avo();
        } else if (this.dF) {
            for (ahb_0 ahb_03 : this.crI) {
                this.a(ahb_03, this.dE);
            }
        }
    }

    public void a(jn_2 jn_22) {
        if (this.dE instanceof aho_0) {
            aho_0 aho_02 = (aho_0)this.dE;
            for (ahb_0 ahb_02 : this.crI) {
                if (ahb_02.getResultProvider() != jn_22) continue;
                String string = ahb_02.getFieldName();
                if (string != null) {
                    this.a(ahb_02, aho_02.getFieldValue(string));
                    continue;
                }
                this.a(ahb_02, this.dE);
            }
            this.avo();
        } else if (this.dF) {
            for (ahb_0 ahb_03 : this.crI) {
                if (ahb_03.getResultProvider() != jn_22) continue;
                this.a(ahb_03, this.dE);
            }
        }
    }

    public void c(String string, Object object) {
        if (this.dE != null && this.dE instanceof aho_0) {
            aho_0 aho_02 = (aho_0)this.dE;
            aho_02.c(string, object);
            for (ahb_0 ahb_02 : this.crI) {
                if (!ahb_02.getFieldName().equals(string)) continue;
                this.c(ahb_02, object);
            }
        }
    }

    public void b(String string, Object object) {
        if (this.dE != null && this.dE instanceof aho_0) {
            aho_0 aho_02 = (aho_0)this.dE;
            aho_02.b(string, object);
            for (ahb_0 ahb_02 : this.crI) {
                if (!ahb_02.getFieldName().equals(string)) continue;
                this.b(ahb_02, object);
            }
        }
    }

    public void avs() {
        if (this.dE != null && this.dE instanceof aho_0) {
            this.avt();
        } else {
            this.avu();
        }
    }

    private void avt() {
        if (this.dE != null) {
            String[] stringArray;
            aho_0 aho_02 = (aho_0)this.dE;
            for (String string : stringArray = aho_02.getFields()) {
                if (string == null) continue;
                Object object = null;
                if (!aho_02.l(string)) {
                    object = aho_02.getFieldValue(string);
                }
                for (int j = this.crI.size() - 1; j >= 0; --j) {
                    ahb_0 ahb_02 = (ahb_0)this.crI.get(j);
                    String string2 = ahb_02.getFieldName();
                    if (string2 == null || !string2.equals(string)) continue;
                    if (object == null) {
                        Method method = ahb_02.awM().i(ahb_02.getAttribute(), object == null ? null : object.getClass());
                        try {
                            object = method.invoke(ahb_02.getElement(), new Object[0]);
                            aho_02.a(string, object);
                        }
                        catch (Exception exception) {
                            if (ahb_02.getElement() == null) {
                                a.error((Object)("[fieldProviderSynchronize] PropertyClientData avec un element null : field = " + ahb_02.getFieldName()));
                                continue;
                            }
                            if (method == null) {
                                a.error((Object)("[fieldProviderSynchronize] La m\u00e9thode " + ahb_02.getElement().getClass().getName() + ".get" + ahb_02.getAttribute() + "() n'existe pas, impossible de la charger"));
                                continue;
                            }
                            a.error((Object)"Exception", (Throwable)exception);
                        }
                        continue;
                    }
                    this.a(ahb_02, object);
                }
            }
        }
    }

    private void avu() {
        if (this.crI.size() == 0) {
            return;
        }
        ahb_0 ahb_02 = (ahb_0)this.crI.get(this.crI.size() - 1);
        aLH aLH2 = ahb_02.awM();
        Method method = aLH2.i(ahb_02.getAttribute(), this.dE == null ? null : this.dE.getClass());
        if (method == null) {
            return;
        }
        try {
            this.dE = method.invoke(ahb_02.getElement(), new Object[0]);
            if (!this.dF) {
                this.dF = true;
                azs_0.aLV().a(avi.ddK, this);
            }
            this.avo();
            for (int j = 0; j < this.crI.size() - 1; ++j) {
                ahb_0 ahb_03 = (ahb_0)this.crI.get(j);
                this.a(ahb_03, this.dE);
            }
        }
        catch (Exception exception) {
            a.error((Object)("Impossible de synchroniser la propri\u00e9t\u00e9 " + this.m_name + " avec " + ahb_02.getElement().getClass().getName() + ", l'attribut " + ahb_02.getAttribute() + " est incompatible !"));
        }
    }

    public boolean a(Class clazz, aLH aLH2) {
        Class clazz2 = this.a(aLH2);
        return clazz2 != null && clazz2.isAssignableFrom(clazz);
    }

    public Class a(aLH aLH2) {
        Method method = aLH2.h(this.m_name, this.dE == null ? null : this.dE.getClass());
        if (method != null) {
            return method.getDeclaringClass();
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Property name=").append(this.m_name);
        if (this.blb != null) {
            stringBuilder.append(" elementMap=").append(this.blb.getId());
        }
        stringBuilder.append(" value=").append(this.dE);
        return stringBuilder.toString();
    }

    private static String a(afl_0 afl_02, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(afl_02.m_name).append(crR).append(string);
        return stringBuilder.toString();
    }
}

