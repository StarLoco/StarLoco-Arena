/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.Map;

public abstract class PT
extends arj_0 {
    akn_1 bEB;
    String pattern;
    protected tQ bEC;
    Map bED = new HashMap();

    public abstract Map acD();

    public Map acE() {
        Map map;
        vU vU2;
        HashMap hashMap = new HashMap();
        Map map2 = this.acD();
        if (map2 != null) {
            hashMap.putAll(map2);
        }
        if ((vU2 = this.QK()) != null && (map = (Map)vU2.getObject("PATTERN_RULE_REGISTRY")) != null) {
            hashMap.putAll(map);
        }
        hashMap.putAll(this.bED);
        return hashMap;
    }

    public void start() {
        try {
            zx zx2 = new zx(this.pattern);
            if (this.QK() != null) {
                zx2.a(this.QK());
            }
            ki_1 ki_12 = zx2.Gt();
            this.bEB = zx2.a(ki_12, this.acE());
            if (this.bEC != null) {
                this.bEC.a(this.bEB);
            }
            this.f(this.bEB);
            yi_2.d(this.bEB);
            super.start();
        }
        catch (fe fe2) {
            Ju ju = this.QK().ea();
            ju.c(new aIX("Failed to parse pattern \"" + this.getPattern() + "\".", this, fe2));
        }
    }

    public void a(tQ tQ2) {
        this.bEC = tQ2;
    }

    protected void f(akn_1 akn_12) {
        vU vU2 = this.QK();
        for (akn_1 akn_13 = akn_12; akn_13 != null; akn_13 = akn_13.azY()) {
            if (!(akn_13 instanceof aaa_1)) continue;
            ((aaa_1)((Object)akn_13)).a(vU2);
        }
    }

    protected String ae(Object object) {
        StringBuffer stringBuffer = new StringBuffer(128);
        for (akn_1 akn_12 = this.bEB; akn_12 != null; akn_12 = akn_12.azY()) {
            akn_12.a(stringBuffer, object);
        }
        return stringBuffer.toString();
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String string) {
        this.pattern = string;
    }

    public String toString() {
        return this.getClass().getName() + "(" + this.getPattern() + ")";
    }

    public Map acF() {
        return this.bED;
    }
}

