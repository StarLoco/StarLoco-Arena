/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * Renamed from PP
 */
public class pp_2
implements all_1 {
    Map map = new HashMap();
    anf_1 bEq;
    anf_1 bEr = this.bEq = new anf_1(this, null, null, 0L);
    long bEs = 0L;

    pp_2() {
    }

    public synchronized void a(String string, adr_0 adr_02, long l2) {
        anf_1 anf_12 = (anf_1)this.map.get(string);
        if (anf_12 == null) {
            anf_12 = new anf_1(this, string, adr_02, l2);
            this.map.put(string, anf_12);
        }
        this.a(anf_12);
    }

    public synchronized adr_0 d(String string, long l2) {
        anf_1 anf_12 = (anf_1)this.map.get(string);
        if (anf_12 == null) {
            return null;
        }
        anf_12.eR(l2);
        this.a(anf_12);
        return anf_12.dZF;
    }

    public synchronized void cp(long l2) {
        if (this.bEs + 1000L > l2) {
            return;
        }
        this.bEs = l2;
        while (this.bEq.dZF != null && this.a(this.bEq, l2)) {
            adr_0 adr_02 = this.bEq.dZF;
            adr_02.stop();
            this.acw();
        }
    }

    public List acv() {
        LinkedList<String> linkedList = new LinkedList<String>();
        anf_1 anf_12 = this.bEq;
        while (anf_12 != this.bEr) {
            linkedList.add(anf_12.key);
            anf_12 = anf_12.dZD;
        }
        return linkedList;
    }

    private final boolean a(anf_1 anf_12, long l2) {
        return anf_12.timestamp + 1800000L < l2;
    }

    private void acw() {
        this.map.remove(this.bEq.key);
        this.bEq = this.bEq.dZD;
        this.bEq.dZE = null;
    }

    private void a(anf_1 anf_12) {
        this.b(anf_12);
        this.c(anf_12);
    }

    private void b(anf_1 anf_12) {
        if (anf_12.dZE != null) {
            anf_12.dZE.dZD = anf_12.dZD;
        }
        if (anf_12.dZD != null) {
            anf_12.dZD.dZE = anf_12.dZE;
        }
        if (this.bEq == anf_12) {
            this.bEq = anf_12.dZD;
        }
    }

    private void c(anf_1 anf_12) {
        anf_1 anf_13;
        if (this.bEq == this.bEr) {
            this.bEq = anf_12;
        }
        if ((anf_13 = this.bEr.dZE) != null) {
            anf_13.dZD = anf_12;
        }
        anf_12.dZE = anf_13;
        anf_12.dZD = this.bEr;
        this.bEr.dZE = anf_12;
    }

    public void dump() {
        anf_1 anf_12 = this.bEq;
        System.out.print("N:");
        while (anf_12 != null) {
            System.out.print(anf_12.key + ", ");
            anf_12 = anf_12.dZD;
        }
        System.out.println();
    }

    public List acx() {
        LinkedList<adr_0> linkedList = new LinkedList<adr_0>();
        anf_1 anf_12 = this.bEq;
        while (anf_12 != this.bEr) {
            linkedList.add(anf_12.dZF);
            anf_12 = anf_12.dZD;
        }
        return linkedList;
    }
}

