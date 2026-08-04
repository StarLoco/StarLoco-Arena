/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import org.apache.log4j.Logger;

/*
 * Renamed from alO
 */
public class alo_2 {
    private static final Logger a = Logger.getLogger(alo_2.class);
    private static final int ait = 1;
    public static final int cFA = 1;
    public static final int cFB = 100;

    public static short aBb() {
        return 20481;
    }

    public static void a(String string, ParticleSystem particleSystem, int n2) {
        acf acf2 = acf.T(vq_2.readFile(string));
        short s = acf2.readShort();
        if (s != alo_2.aBb()) {
            throw new Exception("fichier incorrect " + string);
        }
        if (n2 <= 1) {
            n2 = 0;
        } else if (n2 > 100) {
            n2 = 100;
        }
        alo_2.a(particleSystem, n2, acf2);
        float f = (float)n2 / 100.0f;
        int n3 = acf2.readByte();
        for (int j = 0; j < n3; ++j) {
            mp_1 mp_12 = alo_2.q(acf2, f);
            particleSystem.a(mp_12);
        }
        acf2.close();
    }

    private static void a(ParticleSystem particleSystem, int n2, acf acf2) {
        boolean bl2 = acf2.aqE();
        boolean bl3 = acf2.aqE();
        boolean bl4 = acf2.aqE();
        int n3 = acf2.readInt();
        int n4 = acf2.readInt();
        long l2 = acf2.readLong();
        int n5 = UC.a(acf2, bl2, n2);
        byte by = acf2.readByte();
        particleSystem.cF(bl3);
        particleSystem.setDuration(n5);
        particleSystem.G(by);
        particleSystem.bZF = bl4;
        particleSystem.bZD = l2;
        particleSystem.b(air.kP(n3), air.kP(n4));
    }

    private static mp_1 q(acf acf2, float f) {
        int n2;
        int n3;
        mp_1 mp_12 = (mp_1)aDk.a(acf2, f);
        int n4 = acf2.readByte();
        for (n3 = 0; n3 < n4; ++n3) {
            ye_1 ye_12 = bk_0.g(acf2, f);
            mp_12.a(ye_12);
        }
        alo_2.a(acf2, f, mp_12);
        n3 = acf2.readByte();
        for (n2 = 0; n2 < n3; ++n2) {
            apI apI2 = (apI)afv_0.a(acf2, f);
            alo_2.a(acf2, f, apI2);
            mp_12.a(apI2);
        }
        n2 = acf2.readByte();
        for (int j = 0; j < n2; ++j) {
            mp_1 mp_13 = alo_2.q(acf2, f);
            mp_12.a(mp_13);
        }
        return mp_12;
    }

    private static void a(acf acf2, float f, agu_1 agu_12) {
        int n2 = acf2.readByte();
        for (int j = 0; j < n2; ++j) {
            ua_0 ua_02 = gg_0.b(acf2, f);
            int n3 = acf2.readByte();
            for (int i2 = 0; i2 < n3; ++i2) {
                adp_0 adp_02 = auw_0.t(acf2, f);
                ua_02.a(adp_02);
            }
            if (ua_02.vI()) {
                agu_12.c(ua_02);
                continue;
            }
            agu_12.a(ua_02);
        }
    }
}

