/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;

/*
 * Renamed from ahK
 */
public abstract class ahk_2 {
    private static Logger a = Logger.getLogger(ahk_2.class);
    protected Map cwL = new HashMap();

    public ahk_2() {
        this.amK();
    }

    protected abstract void amK();

    public void f(String string, Class clazz) {
        this.a(string.toLowerCase(), new app_0(clazz));
    }

    public void a(String string, aLH aLH2) {
        if (!this.cwL.containsKey(string.toLowerCase())) {
            this.cwL.put(string.toLowerCase(), aLH2);
        } else {
            a.error((Object)("le tag (name=" + string + ") est d\u00e9j\u00e0 utilis\u00e9 !"));
        }
    }

    public boolean ii(String string) {
        return null != this.cwL.remove(string);
    }

    public Map axs() {
        return this.cwL;
    }

    public aLH ij(String string) {
        return (aLH)this.cwL.get(string.toLowerCase());
    }

    public aLH w(Class clazz) {
        aLH aLH2 = null;
        Iterator iterator = this.cwL.values().iterator();
        while (iterator != null && iterator.hasNext()) {
            aLH aLH3 = (aLH)iterator.next();
            if (!aLH3.abM().equals(clazz)) continue;
            aLH2 = aLH3;
            break;
        }
        return aLH2;
    }

    protected Method g(Class clazz, String string) {
        Method method = null;
        aLH aLH2 = this.ij(clazz.getName());
        if (aLH2 != null) {
            method = aLH2.iV(string);
        }
        return method;
    }

    protected Method h(Class clazz, String string) {
        Method method = null;
        aLH aLH2 = this.ij(clazz.getName());
        if (aLH2 != null) {
            method = aLH2.iX(string);
        }
        return method;
    }
}

