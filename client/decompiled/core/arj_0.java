/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from arJ
 */
public abstract class arj_0
extends ii_2
implements ei_2 {
    protected boolean bgs;
    String cQy;
    String cQz;
    String cQA;
    String cQB;

    public void a(vU vU2) {
        this.Pb = vU2;
    }

    public vU QK() {
        return this.Pb;
    }

    public void start() {
        this.bgs = true;
    }

    public void stop() {
        this.bgs = false;
    }

    public boolean isStarted() {
        return this.bgs;
    }

    public String hf() {
        return this.cQy;
    }

    public String hg() {
        return this.cQA;
    }

    public String hh() {
        return this.cQB;
    }

    public String hi() {
        return this.cQz;
    }

    public String getContentType() {
        return "text/plain";
    }

    public void jl(String string) {
        this.cQy = string;
    }

    public void jm(String string) {
        this.cQz = string;
    }

    public void jn(String string) {
        this.cQA = string;
    }

    public void jo(String string) {
        this.cQB = string;
    }
}

