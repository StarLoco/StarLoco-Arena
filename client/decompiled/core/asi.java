/*
 * Decompiled with CFR 0.152.
 */
public enum asi implements ic_2
{
    cRe(1, (Cs)new mj_2()),
    cRf(2, (Cs)new aLf()),
    cRg(3, (Cs)new axt_0()),
    cRh(4, (Cs)new aBR()),
    cRi(5, (Cs)new fg_2()),
    cRj(6, (Cs)new hr_2()),
    cRk(9, (Cs)new gj_0()),
    cRl(7, (Cs)new amt_0()),
    cRm(8, (Cs)new abt_2()),
    cRn(10, (Cs)new ajr_0()),
    cRo(11, (Cs)new mn_1()),
    cRp(12, (Cs)new Vw()),
    cRq(13, (Cs)new awk_0()),
    cRr(14, (Cs)new ann_2()),
    cRs(15, (Cs)new rv_1());

    private final short uM;
    private final Cs cRt;

    /*
     * WARNING - void declaration
     */
    private asi() {
        void var4_2;
        void var3_1;
        this.uM = var3_1;
        this.cRt = var4_2;
    }

    public String cC() {
        return Short.toString(this.uM);
    }

    public String cD() {
        return this.toString();
    }

    public String cE() {
        return null;
    }

    public short lC() {
        return this.uM;
    }

    public Cs lD() {
        return this.cRt;
    }
}

