/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * Renamed from CV
 */
public class cv_2
extends fz_1 {
    String key;
    String value;

    cv_2(String string, String string2) {
        this.key = string;
        this.value = string2;
    }

    protected zf_0 co() {
        return new zf_0("configuration");
    }

    protected void a(aom_2 aom_22) {
        aom_22.a(new zf_0("configuration/appender"), new xh_2());
    }

    protected void cp() {
        super.cp();
        Map map = this.fm.Vy().wc();
        map.put("APPENDER_BAG", new HashMap());
        map.put("FILTER_CHAIN_BAG", new HashMap());
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(this.key, this.value);
        this.fm.f(hashMap);
    }

    public adr_0 Lo() {
        Map map = this.fm.Vy().wc();
        HashMap hashMap = (HashMap)map.get("APPENDER_BAG");
        Collection collection = hashMap.values();
        return (adr_0)collection.iterator().next();
    }
}

