/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from vs
 */
public abstract class vs_2
extends UC {
    private static final Logger a = Logger.getLogger(vs_2.class);
    protected static final byte asI = 1;
    protected static final byte asJ = 2;

    public static ye_1 g(acf acf2, float f) {
        byte by = acf2.readByte();
        switch (by) {
            case 1: {
                return bk_0.aJn.i(acf2, f);
            }
            case 2: {
                return amc_2.cHN.r(acf2, f);
            }
        }
        a.error((Object)("type de condition inconnu " + by));
        return null;
    }

    protected boolean a(ye_1 ye_12, ye_1 ye_13) {
        return ye_12.KX == ye_13.KX && ye_12.KY == ye_13.KY && ye_12.KT == ye_13.KT && ye_12.KU == ye_13.KU && ye_12.aAh == ye_13.aAh && ye_12.aAf == ye_13.aAf && ye_12.aAg == ye_13.aAg && ye_12.aAe == ye_13.aAe && ye_12.aAi == ye_13.aAi && ye_12.aAl == ye_13.aAl && ye_12.aAm == ye_13.aAm && ye_12.aAn == ye_13.aAn && ye_12.aAo == ye_13.aAo && ye_12.aAp == ye_13.aAp && ye_12.aAq == ye_13.aAq && ye_12.aAr == ye_13.aAr && ye_12.aAs == ye_13.aAs;
    }
}

