/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aLW
 */
public class alw_2
extends gp_0 {
    public alw_2(String string, of_0 ... of_0Array) {
        super(string, of_0Array);
    }

    public alw_2(of_0 ... of_0Array) {
        super(of_0Array);
    }

    public final of_0[] kp() {
        ArrayList<of_0> arrayList = new ArrayList<of_0>();
        for (int j = 0; j < this.ko(); ++j) {
            of_0 of_02 = this.aJ(j);
            arrayList.add(new of_0(of_02.getName()));
        }
        return arrayList.toArray(new of_0[this.ko()]);
    }
}

