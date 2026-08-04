/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class azo
implements zD {
    final /* synthetic */ DataOutputStream dnw;
    final /* synthetic */ apl_1 Ge;

    azo(apl_1 apl_12, DataOutputStream dataOutputStream) {
        this.Ge = apl_12;
        this.dnw = dataOutputStream;
    }

    public boolean b(int n2, ArrayList arrayList) {
        for (int j = 0; j < arrayList.size(); ++j) {
            la_2 la_22 = (la_2)arrayList.get(j);
            try {
                this.dnw.writeInt(n2);
                this.dnw.writeInt(la_22.Gc);
                this.dnw.writeInt(la_22.size);
                this.dnw.writeInt(la_22.Gd);
                continue;
            }
            catch (IOException iOException) {
                ace_0.a.error((Object)iOException.getMessage(), (Throwable)iOException);
            }
        }
        return true;
    }
}

