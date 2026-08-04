/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class aGT
implements bg_1 {
    protected static final Logger a = Logger.getLogger(aGT.class);
    cp_2 dJL = new cp_2();
    we_2 dJM;
    byte dJN = (byte)-1;

    public aGT() {
    }

    public void a(we_2 we_22) {
        this.dJM = we_22;
    }

    public aGT(ahl_1 ahl_12) {
        this.dJM = new we_2(ahl_12);
    }

    public void a(long l2, short s) {
        this.j(l2, s);
        this.dJM.d(l2, this.dJN + 1);
    }

    public void b(long l2, short s) {
        this.j(l2, (short)(s + 1));
    }

    public void c(long l2, short s) {
        this.dJM.d(l2, this.dJN + 1);
    }

    public boolean m(long l2) {
        return this.dJL.v(l2);
    }

    private void j(long l2, short s) {
        alh_1 alh_12 = new alh_1(l2, s);
        this.dJL.a(l2, alh_12);
        this.dJM.aR(l2);
    }

    public void l(long l2) {
        if (!this.m(l2)) {
            a.error((Object)("On tente de retirer un fighter absent de la timeline (" + l2 + ')'));
            return;
        }
        if (this.du() && this.r(l2)) {
            this.aSY().aAx();
        }
        qa_2 qa_22 = this.do();
        int n2 = Math.min(this.dJN + 1, qa_22.size());
        for (int j = 0; j < n2; ++j) {
            if (qa_22.hn(j) != l2) continue;
            this.dJN = (byte)(this.dJN - 1);
        }
        this.dJM.l(l2);
        this.dJL.u(l2);
    }

    public void dl() {
        this.dJM.dl();
    }

    public void dm() {
        this.aSW();
        this.aSX();
    }

    private void aSW() {
        this.dJM.Cy();
    }

    public void dn() {
        this.dJM.Cz();
    }

    private void aSX() {
        this.dJN = (byte)-1;
    }

    public qa_2 do() {
        return this.dJM.CA();
    }

    public qa_2 dp() {
        return this.dJM.CB();
    }

    public boolean du() {
        return this.dJN >= 0 && this.dJN < this.do().size();
    }

    public long dh() {
        if (!this.du()) {
            this.aSZ();
            throw new IllegalStateException("currentFighter() sans hasCurrentFighter()");
        }
        return this.do().get(this.dJN);
    }

    public boolean r(long l2) {
        return this.du() && this.dh() == l2;
    }

    alh_1 aSY() {
        if (!this.du()) {
            this.aSZ();
            throw new IllegalStateException("currentNode() sans hasCurrentFighter()");
        }
        return (alh_1)this.dJL.t(this.dh());
    }

    public byte dg() {
        return this.dJN;
    }

    public boolean di() {
        return this.dJN + 1 < this.do().size();
    }

    public void dj() {
        if (!this.di()) {
            return;
        }
        this.dJN = (byte)(this.dJN + 1);
    }

    public long dk() {
        if (!this.di()) {
            throw new IllegalStateException("peekAtNextFighter() sans hasNextFighter()");
        }
        return this.do().get(this.dJN + 1);
    }

    public void dt() {
        if (this.du()) {
            this.aSY().aAw();
        }
    }

    public Iterator dr() {
        return this.aSY().dr();
    }

    public Iterator ds() {
        return this.aSY().ds();
    }

    void aSZ() {
        String string = this.toString();
        a.error((Object)(string + bl_0.d(1, 5)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[Timeline] ");
        stringBuilder.append("P:").append(this.dJN).append(' ');
        stringBuilder.append("T:[");
        qa_2 qa_22 = this.do();
        int n2 = qa_22.size();
        for (int j = 0; j < n2; ++j) {
            stringBuilder.append(qa_22.get(j)).append(',');
        }
        if (n2 > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        stringBuilder.append("] D:[");
        qa_2 qa_23 = this.dJM.CB();
        int n3 = qa_23.size();
        for (int j = 0; j < n3; ++j) {
            stringBuilder.append(qa_23.get(j)).append(',');
        }
        if (n3 > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        stringBuilder.append(']');
        long[] lArray = this.dJL.eJ();
        int n4 = lArray.length;
        stringBuilder.append(" N:[");
        for (int j = 0; j < n4; ++j) {
            long l2 = lArray[j];
            if (this.dJL.t(l2) == null) {
                stringBuilder.append('!');
            }
            stringBuilder.append(l2).append(',');
        }
        if (n4 > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public boolean a(akv_0 akv_02, short s) {
        long l2 = akv_02.K();
        alh_1 alh_12 = (alh_1)this.dJL.t(l2);
        if (alh_12 == null) {
            return akv_02.aVC() < s;
        }
        int n2 = akv_02.aVC() - alh_12.aAy();
        if (!akv_02.aEn() && this.r(l2)) {
            --n2;
        }
        return n2 < 0;
    }

    public short a(akv_0 akv_02) {
        long l2 = akv_02.K();
        alh_1 alh_12 = (alh_1)this.dJL.t(l2);
        if (alh_12 == null) {
            return -1;
        }
        int n2 = akv_02.aVC() - alh_12.aAy();
        if (akv_02.aEn() && !this.r(l2)) {
            ++n2;
        }
        return (short)n2;
    }

    public akv_0 a(atD atD2, arm_0 arm_02, boolean bl2) {
        long l2 = atD2.TH();
        alh_1 alh_12 = (alh_1)this.dJL.t(l2);
        if (alh_12 == null) {
            a.info((Object)("[Timeline] Tentative d'attacher un " + atD2.getClass().getSimpleName() + " au fighter " + l2 + " absent de la timeline."));
            return akv_0.aVB();
        }
        short s = (short)(alh_12.aAy() + arm_02.aEo());
        if (arm_02.aEn()) {
            s = (short)(s - 1);
        }
        if (bl2 && arm_02.aEl()) {
            s = (short)(s + 1);
        }
        akv_0 akv_02 = akv_0.eF(l2).pb(s).fi(arm_02.aEn());
        alh_12.a(atD2, s, arm_02.aEn());
        return akv_02;
    }

    public void a(atD atD2, long l2, short s, boolean bl2) {
        alh_1 alh_12 = (alh_1)this.dJL.t(l2);
        if (alh_12 == null) {
            return;
        }
        alh_12.a(atD2, s, bl2);
    }

    public void a(atD atD2, akv_0 akv_02) {
        alh_1 alh_12 = (alh_1)this.dJL.t(akv_02.K());
        alh_12.a(atD2, akv_02.aVC(), akv_02.aEn());
    }

    public ra_2 p(long l2) {
        alh_1 alh_12 = (alh_1)this.dJL.t(l2);
        return alh_12 == null ? null : alh_12.aAA();
    }

    public short q(long l2) {
        if (!this.m(l2)) {
            return -1;
        }
        return (short)(((alh_1)this.dJL.t(l2)).aAy() - 1);
    }

    public boolean n(long l2) {
        return this.di() && this.dk() == l2;
    }

    public boolean o(long l2) {
        return this.du() && this.dh() == l2;
    }

    public int w() {
        int n2 = 1 + this.dJM.w() + 1 + 8 * this.dJL.size();
        for (long l2 : this.dJL.eJ()) {
            n2 += ((alh_1)this.dJL.t(l2)).w();
        }
        return n2;
    }

    public void c(ByteBuffer byteBuffer) {
        this.dJM.c(byteBuffer);
        byteBuffer.put((byte)this.dJL.size());
        for (int j = 0; j < this.dJL.eJ().length; ++j) {
            long l2 = this.dJL.eJ()[j];
            byteBuffer.putLong(l2);
            ((alh_1)this.dJL.t(l2)).c(byteBuffer);
        }
        byteBuffer.put(this.dJN);
    }

    public void a(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        this.dJM.y(byteBuffer);
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            long l2 = byteBuffer.getLong();
            alh_1 alh_12 = alh_1.d(ahh_02, byteBuffer);
            alh_12.j(l2);
            this.dJL.a(l2, alh_12);
        }
        this.dJN = byteBuffer.get();
    }

    public static aGT f(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        aGT aGT2 = new aGT(ahh_02.aUe());
        aGT2.a(ahh_02, byteBuffer);
        return aGT2;
    }

    public void clear() {
        this.dJL.clear();
        if (this.dJM != null) {
            this.dJM.clear();
        }
        this.aSX();
    }

    public void dq() {
        Object[] objectArray = new alh_1[this.dJL.size()];
        this.dJL.a(objectArray);
        for (int j = 0; j < objectArray.length; ++j) {
            Object object = objectArray[j];
            ((alh_1)object).aAB();
        }
    }
}

