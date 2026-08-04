/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

/*
 * Renamed from alR
 */
public class alr_0
extends PT {
    public static final Map cFM = new HashMap();

    public alr_0() {
        this.bEC = new vc();
    }

    public Map acD() {
        return cFM;
    }

    public String f(tz_0 tz_02) {
        if (!this.isStarted()) {
            return "";
        }
        return this.ae(tz_02);
    }

    static {
        cFM.put("d", aiq_1.class.getName());
        cFM.put("date", aiq_1.class.getName());
        cFM.put("r", afh_2.class.getName());
        cFM.put("relative", afh_2.class.getName());
        cFM.put("level", ali_1.class.getName());
        cFM.put("le", ali_1.class.getName());
        cFM.put("p", ali_1.class.getName());
        cFM.put("t", wm_1.class.getName());
        cFM.put("thread", wm_1.class.getName());
        cFM.put("lo", ame_0.class.getName());
        cFM.put("logger", ame_0.class.getName());
        cFM.put("c", ame_0.class.getName());
        cFM.put("m", fo_0.class.getName());
        cFM.put("msg", fo_0.class.getName());
        cFM.put("message", fo_0.class.getName());
        cFM.put("C", afp_1.class.getName());
        cFM.put("class", afp_1.class.getName());
        cFM.put("M", alo_1.class.getName());
        cFM.put("method", alo_1.class.getName());
        cFM.put("L", ali_2.class.getName());
        cFM.put("line", ali_2.class.getName());
        cFM.put("F", pc_2.class.getName());
        cFM.put("file", pc_2.class.getName());
        cFM.put("X", wb_0.class.getName());
        cFM.put("mdc", wb_0.class.getName());
        cFM.put("ex", hu_0.class.getName());
        cFM.put("exception", hu_0.class.getName());
        cFM.put("throwable", hu_0.class.getName());
        cFM.put("xEx", cq_0.class.getName());
        cFM.put("xException", cq_0.class.getName());
        cFM.put("xThrowable", cq_0.class.getName());
        cFM.put("nopex", adx_0.class.getName());
        cFM.put("nopexception", adx_0.class.getName());
        cFM.put("cn", azq_0.class.getName());
        cFM.put("contextName", Dj.class.getName());
        cFM.put("caller", aMx.class.getName());
        cFM.put("marker", fe_0.class.getName());
        cFM.put("n", alc_1.class.getName());
    }
}

