/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Renamed from Tr
 */
public abstract class tr_0
extends bf_2 {
    public List afX() {
        return null;
    }

    protected void a(aom_2 aom_22) {
        aom_22.a(new zf_0("configuration/property"), new Oo());
        aom_22.a(new zf_0("configuration/substitutionProperty"), new Oo());
        aom_22.a(new zf_0("configuration/contextProperty"), new ii_0());
        aom_22.a(new zf_0("configuration/conversionRule"), new aED());
        aom_22.a(new zf_0("configuration/statusListener"), new qb_2());
        aom_22.a(new zf_0("configuration/appender"), new xh_2());
        aom_22.a(new zf_0("configuration/appender/appender-ref"), new aou_1());
        aom_22.a(new zf_0("configuration/newRule"), new jp_2());
        aom_22.a(new zf_0("*/param"), new aoo_1());
    }

    protected void a(jh_1 jh_12) {
        vv vv2 = new vv();
        vv2.a(this.Pb);
        jh_12.a(vv2);
        ani_1 ani_12 = new ani_1();
        vv2.a(this.Pb);
        jh_12.a(ani_12);
    }

    protected void cp() {
        super.cp();
        Map map = this.fm.Vy().wc();
        map.put("APPENDER_BAG", new HashMap());
        map.put("FILTER_CHAIN_BAG", new HashMap());
    }

    public qq_0 Vx() {
        return this.fm.Vy();
    }
}

