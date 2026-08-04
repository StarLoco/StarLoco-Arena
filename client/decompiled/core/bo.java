/*
 * Decompiled with CFR 0.152.
 */
public enum bo implements rk_0
{
    fJ(1000, "Exploits associ\u00e9s aux personnages"),
    fK(1001, "Exploits associ\u00e9s \u00e0 nos amis les F\u00e9cas");

    private short fL;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private bo() {
        void var4_2;
        void var3_1;
        this.fL = var3_1;
        this.fM = var4_2;
    }

    public String cC() {
        return String.valueOf(this.fL);
    }

    public String cD() {
        return this.toString();
    }

    public String cE() {
        return this.fM;
    }
}

