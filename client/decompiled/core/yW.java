/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.zip.CRC32;
import org.apache.log4j.Logger;

public final class yW {
    private ano_2 aEk;
    private lb_0 aEl = new lb_0();
    private final int aEm = 256;
    private static final Logger a = Logger.getLogger(yW.class);
    private static final yW aEn = new yW();

    public final ams_2 a(int n2, Class clazz) {
        kp_1 kp_12 = (kp_1)this.aEl.get(n2);
        if (kp_12 == null) {
            int n3 = this.aEk.get(n2);
            if (n3 == 0) {
                n3 = 256;
            }
            kp_12 = new kp_1(n3, clazz);
        }
        return kp_12.pw();
    }

    public final kp_1 b(int n2, Class clazz) {
        kp_1 kp_12 = (kp_1)this.aEl.get(n2);
        if (kp_12 == null) {
            kp_12 = new kp_1(256, clazz);
        }
        return kp_12;
    }

    public final kp_1[] FK() {
        Object[] objectArray = new kp_1[this.aEl.size()];
        return (kp_1[])this.aEl.a(objectArray);
    }

    public final void a(aNe aNe2) {
        CRC32 cRC32 = new CRC32();
        k_0 k_02 = aNe2.aXo().c("memoryObjectPools");
        if (k_02 == null) {
            a.warn((Object)"No pools configuration found");
            return;
        }
        ArrayList arrayList = k_02.d("pool");
        if (arrayList == null) {
            a.warn((Object)"No pools configuration found");
            return;
        }
        int n2 = arrayList.size();
        for (int j = 0; j < n2; ++j) {
            k_0 k_03 = (k_0)arrayList.get(j);
            k_0 k_04 = k_03.f("class");
            k_0 k_05 = k_03.f("size");
            String string = k_04.getStringValue();
            int n3 = k_05.getIntValue();
            cRC32.reset();
            cRC32.update(string.getBytes());
            this.af((int)cRC32.getValue(), n3);
        }
    }

    public final void dc(String string) {
        aAN aAN2 = aAN.aMW();
        aNe aNe2 = aAN2.aMX();
        try {
            aAN2.iJ(string);
            aAN2.a(aNe2, new tf_2[0]);
            aAN2.close();
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        this.a(aNe2);
    }

    public static yW FL() {
        return aEn;
    }

    final void b(kp_1 kp_12) {
        this.aEl.c(kp_12.it(), kp_12);
        wq_1.Dn().a(kp_12);
    }

    private void af(int n2, int n3) {
        this.aEk.bz(n2, n3);
    }

    private yW() {
        this.aEk = new ano_2();
    }
}

