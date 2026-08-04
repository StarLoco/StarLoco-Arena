/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class alX
extends ii_2
implements px_0,
af_2 {
    private static String aUt = "";
    ahu_0 cFW;
    MBeanServer mbs;
    ObjectName objectName;
    String cFX;
    boolean debug = true;
    boolean bgs = true;

    public alX(ahu_0 ahu_02, MBeanServer mBeanServer, ObjectName objectName) {
        this.Pb = ahu_02;
        this.cFW = ahu_02;
        this.mbs = mBeanServer;
        this.objectName = objectName;
        this.cFX = objectName.toString();
        if (this.aBd()) {
            this.eg("Previously registered JMXConfigurator named [" + this.cFX + "] in the logger context named [" + ahu_02.getName() + "]");
        } else {
            ahu_02.a(this);
        }
    }

    private boolean aBd() {
        List list = this.cFW.aUr();
        for (af_2 af_22 : list) {
            if (!(af_22 instanceof alX)) continue;
            alX alX2 = (alX)af_22;
            if (!this.objectName.equals(alX2.objectName)) continue;
            return true;
        }
        return false;
    }

    public void acf() {
        aha_2 aha_22 = new aha_2(this.cFW);
        URL uRL = aha_22.fb(true);
        this.d(uRL);
    }

    public void fA(String string) {
        File file = new File(string);
        if (file.exists() && file.isFile()) {
            try {
                URL uRL = file.toURI().toURL();
                this.d(uRL);
            }
            catch (MalformedURLException malformedURLException) {
                throw new RuntimeException("Unexpected MalformedURLException occured. See nexted cause.", malformedURLException);
            }
        } else {
            String string2 = "Could not find [" + string + "]";
            this.ee(string2);
            throw new FileNotFoundException(string2);
        }
    }

    void c(pm_1 pm_12) {
        Ju ju = this.cFW.ea();
        ju.a(pm_12);
    }

    void d(pm_1 pm_12) {
        Ju ju = this.cFW.ea();
        ju.b(pm_12);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void d(URL uRL) {
        ag_1 ag_12 = new ag_1();
        this.c(ag_12);
        this.ee("Resetting context: " + this.cFW.getName());
        this.cFW.reset();
        this.c(ag_12);
        try {
            aip_1 aip_12 = new aip_1();
            aip_12.a(this.cFW);
            aip_12.b(uRL);
            this.ee("Context: " + this.cFW.getName() + " reloaded.");
        }
        finally {
            this.d(ag_12);
            if (this.debug) {
                ape.p(ag_12.He());
            }
        }
    }

    public void setLoggerLevel(String string, String string2) {
        if (string == null) {
            return;
        }
        if (string2 == null) {
            return;
        }
        string = string.trim();
        string2 = string2.trim();
        this.ee("Trying to set level " + string2 + " to logger " + string);
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        arN arN2 = ahu_02.lw(string);
        if ("null".equalsIgnoreCase(string2)) {
            arN2.b((rl_2)null);
        } else {
            rl_2 rl_22 = rl_2.a(string2, null);
            if (rl_22 != null) {
                arN2.b(rl_22);
            }
        }
    }

    public String getLoggerLevel(String string) {
        if (string == null) {
            return aUt;
        }
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        arN arN2 = ahu_02.lx(string = string.trim());
        if (arN2 != null && arN2.agr() != null) {
            return arN2.agr().toString();
        }
        return aUt;
    }

    public String fB(String string) {
        if (string == null) {
            return aUt;
        }
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        arN arN2 = ahu_02.lx(string = string.trim());
        if (arN2 != null) {
            return arN2.aEP().toString();
        }
        return aUt;
    }

    public List acg() {
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (arN arN2 : ahu_02.acg()) {
            arrayList.add(arN2.getName());
        }
        return arrayList;
    }

    public List ach() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator iterator = this.Pb.ea().VS().iterator();
        while (iterator.hasNext()) {
            arrayList.add(((amb)iterator.next()).toString());
        }
        return arrayList;
    }

    public void c(ahu_0 ahu_02) {
        if (!this.bgs) {
            this.ee("onStop() method called on a stopped JMXActivator [" + this.cFX + "]");
            return;
        }
        if (this.mbs.isRegistered(this.objectName)) {
            try {
                this.ee("Unregistering mbean [" + this.cFX + "]");
                this.mbs.unregisterMBean(this.objectName);
            }
            catch (InstanceNotFoundException instanceNotFoundException) {
                this.e("Unable to find a verifiably registered mbean [" + this.cFX + "]", instanceNotFoundException);
            }
            catch (MBeanRegistrationException mBeanRegistrationException) {
                this.e("Failed to unregister [" + this.cFX + "]", mBeanRegistrationException);
            }
        } else {
            this.ee("mbean [" + this.cFX + "] was not in the mbean registry. This is OK.");
        }
        this.stop();
    }

    public void b(ahu_0 ahu_02) {
        this.ee("onReset() method called JMXActivator [" + this.cFX + "]");
    }

    public boolean bh() {
        return true;
    }

    private void aBe() {
        this.mbs = null;
        this.objectName = null;
        this.cFW = null;
    }

    private void stop() {
        this.bgs = false;
        this.aBe();
    }

    public void a(ahu_0 ahu_02) {
    }

    public String toString() {
        return this.getClass().getName() + "(" + this.Pb.getName() + ")";
    }
}

