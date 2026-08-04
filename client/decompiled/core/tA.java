/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class tA {
    protected static final Logger a = Logger.getLogger(tA.class);
    private static final String ang = "messageBoxTitle";
    private static final String anh = "messageBoxImage";
    private static final String ani = "messageBoxTextView";
    private static final String anj = "messageBoxButtonsContainer";
    private static final String ank = "messageBoxButton";

    public static void a(aab_2 aab_22, r_0 r_02, String string, String string2, String string3, long l2) {
        Object object;
        Object object2;
        aji_1 aji_12 = aab_22.getElementMap();
        if (aji_12.iq(ang)) {
            OE oE = (OE)aji_12.R(ang);
            oE.setText(string2);
        }
        if (aji_12.iq(anh)) {
            azc_0 azc_02 = (azc_0)aji_12.R(anh);
            object2 = add_1.aOG().yh();
            if (object2 != null) {
                object = null;
                if (string3 != null && string3.length() > 0) {
                    object = cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string3), string3, new adz_1(), false);
                } else if ((l2 & 0x20L) == 32L) {
                    object = ((DS)object2).dK("messageBoxInfoIcon");
                } else if ((l2 & 0x40L) == 64L) {
                    object = ((DS)object2).dK("messageBoxErrorIcon");
                } else if ((l2 & 0x80L) == 128L) {
                    object = ((DS)object2).dK("messageBoxQuestionIcon");
                } else if ((l2 & 0x100L) == 256L) {
                    object = ((DS)object2).dK("messageBoxCautionIcon");
                }
                if (object != null) {
                    azc_02.setPixmap(new akq_1((ef_1)object));
                }
            }
        } else {
            throw new Exception("Aucun Label n'est r\u00e9f\u00e9renc\u00e9 sous l'id : messageBoxImage");
        }
        if (aji_12.iq(ani)) {
            object2 = (ps)aji_12.R(ani);
            ((ps)object2).setText(string);
            if ((l2 & 0x200L) == 512L) {
                ((YN)object2).setSelectable(true);
                ((yt_1)object2).setEnableShrinking(false);
            }
        } else {
            throw new Exception("Aucun textView n'est r\u00e9f\u00e9renc\u00e9 sous l'id : messageBoxTextView");
        }
        if (!aji_12.iq(anj)) {
            throw new Exception("Aucun container n'est r\u00e9f\u00e9renc\u00e9 sous l'id : messageBoxButtonsContainer");
        }
        if (!aji_12.iq(ank)) {
            throw new Exception("Aucun button n'est r\u00e9f\u00e9renc\u00e9 sous l'id : messageBoxButton");
        }
        object = (aht_1)aji_12.R(anj);
        aqq_0 aqq_02 = (aqq_0)aji_12.R(ank);
        ((aht_1)object).b(aqq_02);
        if ((l2 & 2L) == 2L) {
            tA.a(aqq_02, r_02, (aht_1)object, 2);
        }
        if ((l2 & 4L) == 4L) {
            tA.a(aqq_02, r_02, (aht_1)object, 4);
        }
        if ((l2 & 8L) == 8L) {
            tA.a(aqq_02, r_02, (aht_1)object, 8);
        }
        if ((l2 & 0x10L) == 16L) {
            tA.a(aqq_02, r_02, (aht_1)object, 16);
        }
        aqq_02.aab();
    }

    private static aqq_0 a(aqq_0 aqq_02, r_0 r_02, aht_1 aht_12, int n2) {
        aqq_0 aqq_03 = null;
        try {
            aqq_03 = (aqq_0)aqq_02.getClass().newInstance();
            aqq_03.b();
            aqq_02.a((air_1)aqq_03);
            aqq_03.setText(tA.dI(n2));
            aqq_03.setOnClick(new asx(r_02, n2));
            aqq_03.setElementMap(aqq_02.getElementMap());
            aqq_03.Aj();
            switch (n2) {
                case 2: 
                case 8: {
                    aqq_03.setClickSoundId(aek.atD().atO());
                    aqq_03.setFocusable(true);
                    aqq_03.setFocused(true);
                    break;
                }
                case 4: 
                case 16: {
                    aqq_03.setClickSoundId(aek.atD().atP());
                }
            }
            aht_12.a(aqq_03);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
        return aqq_03;
    }

    private static String dI(int n2) {
        switch (n2) {
            case 2: {
                return add_1.aOG().kE("ok");
            }
            case 4: {
                return add_1.aOG().kE("cancel");
            }
            case 8: {
                return add_1.aOG().kE("yes");
            }
            case 16: {
                return add_1.aOG().kE("no");
            }
        }
        return "";
    }
}

