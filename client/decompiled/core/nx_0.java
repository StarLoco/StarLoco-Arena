/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

/*
 * Renamed from NX
 */
class nx_0
implements zD {
    final /* synthetic */ aij_1 bBd;
    final /* synthetic */ asu_0 bBe;

    nx_0(asu_0 asu_02, aij_1 aij_12) {
        this.bBe = asu_02;
        this.bBd = aij_12;
    }

    public boolean a(int n2, aBp aBp2) {
        try {
            this.bBd.writeInt(n2);
            this.bBd.writeInt(aBp2.size());
            qk qk2 = aBp2.aNm();
            while (qk2.hasNext()) {
                this.bBd.writeInt(qk2.next());
            }
        }
        catch (IOException iOException) {
            asu_0.a.error((Object)"Exception", (Throwable)iOException);
            return false;
        }
        return true;
    }
}

