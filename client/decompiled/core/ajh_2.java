/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;

/*
 * Renamed from ajh
 */
public final class ajh_2 {
    public static final byte cAg = -1;

    public static void b(xw_0 xw_02) {
        xw_02.if(-1);
        xw_02.an((byte)-1);
    }

    public static boolean c(xw_0 xw_02) {
        return xw_02.Ge() == -1;
    }

    public static void a(xw_0 xw_02, DisplayedScreenElement displayedScreenElement) {
        if (displayedScreenElement == null) {
            xw_02.if(0);
            xw_02.an((byte)0);
        } else {
            xw_02.if(displayedScreenElement.Ge());
            xw_02.an(displayedScreenElement.atZ());
        }
    }
}

