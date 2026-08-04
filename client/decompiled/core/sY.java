/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class sY
extends Eq {
    private wq_2 amq;
    private short NC;

    public sY(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public void b(wq_2 wq_22) {
        this.amq = wq_22;
    }

    public void aa(short s) {
        this.NC = s;
    }

    public void run() {
        try {
            adu_0 adu_02 = apN.aDK().aDL();
            if (adu_02 != null) {
                adu_02.a(this.amq);
                azg_0 azg_02 = adu_02.ass();
                azg_02.bc(true);
                if (azg_02.JI() < this.NC) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(adu_02.Zz());
                    while (azg_02.JI() < this.NC) {
                        azg_02.nX();
                    }
                    adu_02.m(arrayList);
                    adu_02.asw();
                }
            }
        }
        catch (Exception exception) {
            a.error((Object)"Error : ", (Throwable)exception);
        }
        this.Nn();
    }

    protected void ax() {
    }
}

