/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from sm
 */
public class sm_0 {
    private static Logger a = Logger.getLogger(sm_0.class);
    private Object dE;
    private afl_0 aiV;

    private sm_0(Object object) {
        this.dE = object;
    }

    public static sm_0 a(Object object, String string, aji_1 aji_12, qa_1 qa_12) {
        if (object instanceof aho_0) {
            afl_0 afl_02 = null;
            if (aji_12 == null) {
                afl_02 = azs_0.aLV().getProperty(string);
            }
            if (afl_02 == null) {
                afl_02 = new afl_0(string, aji_12, true);
                azs_0.aLV().b(afl_02);
            }
            afl_02.setValue(object);
            sm_0 sm_02 = new sm_0(object);
            sm_02.a(afl_02);
            afl_02.c(sm_02);
            return sm_02;
        }
        return new sm_0(object);
    }

    public static void b(sm_0 sm_02) {
        if (sm_02 == null || sm_02.yp() == null) {
            return;
        }
        sm_02.yp().d(sm_02);
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void a(afl_0 afl_02) {
        this.aiV = afl_02;
    }

    public afl_0 yp() {
        return this.aiV;
    }

    public Object getFieldValue(String string) {
        if (this.dE != null && this.dE instanceof aho_0 && string != null) {
            return afl_0.c(this.dE, string);
        }
        return null;
    }
}

