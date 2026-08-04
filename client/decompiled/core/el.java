/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class el
extends lt {
    public static final String oc = "import ch.qos.logback.classic.Level;\r\n";
    public static final List od = new ArrayList();
    public static final List oe = new ArrayList();

    protected String hj() {
        return oc + this.getExpression();
    }

    protected String[] hk() {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.addAll(od);
        for (int j = 0; j < this.Hj.size(); ++j) {
            aaa_2 aaa_22 = (aaa_2)this.Hj.get(j);
            arrayList.add(aaa_22.getName());
        }
        return arrayList.toArray(kJ.Fw);
    }

    protected Class[] getParameterTypes() {
        ArrayList<Class<aaa_2>> arrayList = new ArrayList<Class<aaa_2>>();
        arrayList.addAll(oe);
        for (int j = 0; j < this.Hj.size(); ++j) {
            arrayList.add(aaa_2.class);
        }
        return arrayList.toArray(kJ.Fx);
    }

    protected Object[] a(tz_0 tz_02) {
        int n2 = this.Hj.size();
        int n3 = 0;
        Object[] objectArray = new Object[od.size() + n2];
        objectArray[n3++] = rl_2.afV;
        objectArray[n3++] = rl_2.afU;
        objectArray[n3++] = rl_2.afT;
        objectArray[n3++] = rl_2.afS;
        objectArray[n3++] = tz_02;
        objectArray[n3++] = tz_02.getMessage();
        objectArray[n3++] = tz_02.agu();
        objectArray[n3++] = tz_02.agr().toInteger();
        objectArray[n3++] = new Long(tz_02.getTimeStamp());
        objectArray[n3++] = tz_02.agw();
        objectArray[n3++] = tz_02.agy();
        objectArray[n3++] = tz_02.ags() != null ? tz_02.ags().getThrowable() : null;
        for (int j = 0; j < n2; ++j) {
            objectArray[n3++] = (aaa_2)this.Hj.get(j);
        }
        return objectArray;
    }

    static {
        od.add("DEBUG");
        od.add("INFO");
        od.add("WARN");
        od.add("ERROR");
        od.add("event");
        od.add("message");
        od.add("logger");
        od.add("level");
        od.add("timeStamp");
        od.add("marker");
        od.add("mdc");
        od.add("throwable");
        oe.add(Integer.TYPE);
        oe.add(Integer.TYPE);
        oe.add(Integer.TYPE);
        oe.add(Integer.TYPE);
        oe.add(tz_0.class);
        oe.add(String.class);
        oe.add(hK.class);
        oe.add(Integer.TYPE);
        oe.add(Long.TYPE);
        oe.add(axe.class);
        oe.add(Map.class);
        oe.add(Throwable.class);
    }
}

