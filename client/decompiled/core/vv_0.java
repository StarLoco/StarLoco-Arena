/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/*
 * Renamed from Vv
 */
public class vv_0 {
    public static final vv_0 bSA = new vv_0();
    protected static final Logger a = Logger.getLogger(vv_0.class);

    public static vv_0 aiq() {
        return bSA;
    }

    private vv_0() {
    }

    public Iterable a(aOf aOf2, aii_0 aii_02, agf_2 agf_22, int n2, int n3, short s) {
        if (agf_22 == null || aii_02 == null) {
            return new ts_1();
        }
        int n4 = 0;
        int n5 = 0;
        short s2 = 0;
        if (aOf2 != null) {
            n4 = aOf2.gn();
            n5 = aOf2.go();
            s2 = aOf2.gp();
        }
        ye_0 ye_02 = aOf2 == null ? qc_0.bET : aOf2.Qk();
        return agf_22.a(n4, n5, s2, n2, n3, s, ye_02, aii_02.agn());
    }

    public Iterable a(aOf aOf2, aii_0 aii_02, agf_2 agf_22, int n2, int n3, short s, ahl_2 ahl_22) {
        if (ahl_22 == null) {
            return this.a(aOf2, aii_02, agf_22, n2, n3, s);
        }
        if (agf_22 == null || aii_02 == null) {
            return new ts_1();
        }
        LinkedList<aOf> linkedList = new LinkedList<aOf>();
        Iterator iterator = aii_02.agn();
        block5: while (iterator.hasNext()) {
            aOf aOf3 = (aOf)iterator.next();
            pf_0 pf_02 = ahl_22.a(aOf3, aOf2);
            switch ((ahf_2)((Object)pf_02.getFirst())) {
                case dMN: {
                    linkedList.add(aOf3);
                    break;
                }
                case dMR: {
                    int n4 = 0;
                    int n5 = 0;
                    short s2 = 0;
                    if (aOf2 != null) {
                        n4 = aOf2.gn();
                        n5 = aOf2.go();
                        s2 = aOf2.gp();
                    }
                    if (!agf_22.b(n2, n3, s, n4, n5, s2, aOf2.Qk(), aOf3.gn(), aOf3.go(), aOf3.gp(), aOf3.ox())) break;
                    linkedList.add(aOf3);
                    break;
                }
                case dMO: {
                    for (aOf aOf4 : (ArrayList)pf_02.acl()) {
                        linkedList.add(aOf4);
                    }
                    continue block5;
                }
            }
        }
        return linkedList;
    }
}

