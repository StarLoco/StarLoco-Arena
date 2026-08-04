/*
 * Decompiled with CFR 0.152.
 */
public final class aca {
    public static final abd_0 o(acf acf2) {
        short s = acf2.readShort();
        byte by = acf2.readByte();
        abd_0 abd_02 = null;
        switch (by) {
            case 0: {
                abd_02 = new abd_0();
                break;
            }
            case 1: {
                abd_02 = new ui_2();
                break;
            }
            case 2: {
                abd_02 = new uh_2();
                break;
            }
            case 3: {
                abd_02 = new aoN();
                break;
            }
            case 4: {
                abd_02 = new ub_1();
                break;
            }
            case 5: {
                abd_02 = new aoa_0();
                break;
            }
            case 6: {
                abd_02 = new aoq();
                break;
            }
            case 7: {
                abd_02 = new aLt();
                break;
            }
            case 8: {
                abd_02 = new uf_2();
                break;
            }
            case 9: {
                abd_02 = new aoz_0();
                break;
            }
            case 10: {
                abd_02 = new aia();
                break;
            }
            case 12: {
                abd_02 = new xp_2();
                break;
            }
            case 11: {
                abd_02 = new aLl();
                break;
            }
            case 13: {
                abd_02 = new ato();
                break;
            }
            case 14: {
                abd_02 = new dh();
                break;
            }
            case 15: {
                abd_02 = new azU();
                break;
            }
            case 49: {
                abd_02 = new ang_2();
                break;
            }
            case 82: {
                abd_02 = new anj_1();
                break;
            }
            case -77: {
                abd_02 = new ih_2();
                break;
            }
            default: {
                assert (false) : "shape type unsupported";
                break;
            }
        }
        abd_02.b(acf2);
        abd_02.bk(s);
        return abd_02;
    }
}

