/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import org.xml.sax.Locator;

/*
 * Renamed from qQ
 */
public class qq_0
extends ii_2
implements ms_1 {
    Stack afu;
    Map hZ;
    Map afv;
    jh_1 afw;
    final List afx = new ArrayList();

    public qq_0(vU vU2, jh_1 jh_12) {
        this.Pb = vU2;
        this.afw = jh_12;
        this.afu = new Stack();
        this.hZ = new HashMap(5);
        this.afv = new HashMap(5);
    }

    void c(Map map) {
        this.afv = map;
    }

    String bF(String string) {
        Locator locator = this.afw.getLocator();
        if (locator != null) {
            return string + locator.getLineNumber() + ":" + locator.getColumnNumber();
        }
        return string;
    }

    public Locator getLocator() {
        return this.afw.getLocator();
    }

    public jh_1 vY() {
        return this.afw;
    }

    public Stack vZ() {
        return this.afu;
    }

    public boolean isEmpty() {
        return this.afu.isEmpty();
    }

    public Object wa() {
        return this.afu.peek();
    }

    public void C(Object object) {
        this.afu.push(object);
    }

    public Object wb() {
        return this.afu.pop();
    }

    public Object getObject(int n2) {
        return this.afu.get(n2);
    }

    public Map wc() {
        return this.hZ;
    }

    public void g(String string, String string2) {
        if (string == null || string2 == null) {
            return;
        }
        string2 = string2.trim();
        this.Pb.c(string, string2);
    }

    public void a(Properties properties) {
        if (properties == null) {
            return;
        }
        for (String string : properties.keySet()) {
            this.g(string, properties.getProperty(string));
        }
    }

    public void d(Map map) {
        if (map == null) {
            return;
        }
        for (String string : map.keySet()) {
            this.g(string, (String)map.get(string));
        }
    }

    public String getProperty(String string) {
        String string2 = (String)this.afv.get(string);
        if (string2 != null) {
            return string2;
        }
        return this.Pb.getProperty(string);
    }

    public String subst(String string) {
        if (string == null) {
            return null;
        }
        return dh_2.a(string, this);
    }

    public void a(afI afI2) {
        if (this.afx.contains(afI2)) {
            this.ef("InPlayListener " + afI2 + " has been already registered");
        } else {
            this.afx.add(afI2);
        }
    }

    public boolean b(afI afI2) {
        return this.afx.remove(afI2);
    }

    void a(xg_0 xg_02) {
        for (afI afI2 : this.afx) {
            afI2.b(xg_02);
        }
    }
}

