/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from FP
 */
public abstract class fp_0
implements eG {
    protected static Logger a = Logger.getLogger(fp_0.class);

    public pr_0 g(ByteBuffer byteBuffer) {
        byteBuffer.mark();
        int n2 = byteBuffer.remaining();
        if (n2 < 2) {
            byteBuffer.reset();
            return null;
        }
        int n3 = byteBuffer.getShort() & 0xFFFF;
        if (n3 < 4) {
            a.error((Object)("D\u00e9codage impossible car taille trop petite (taille = " + n3 + ", minimum = 6"));
            return null;
        }
        if (byteBuffer.remaining() < 2) {
            byteBuffer.reset();
            return null;
        }
        short s = byteBuffer.getShort();
        if (byteBuffer.remaining() < n3 - 4) {
            byteBuffer.reset();
            return null;
        }
        int n4 = byteBuffer.position() + n3 - 4;
        pr_0 pr_02 = null;
        switch (s) {
            case 100: {
                pr_02 = new es_0();
                break;
            }
            case 102: {
                pr_02 = new ja_0();
                break;
            }
            case 105: {
                pr_02 = new Ve();
                break;
            }
            case 103: {
                pr_02 = new dm_0();
                break;
            }
            case 106: {
                pr_02 = new lo();
                break;
            }
            case 108: {
                pr_02 = new abj_0();
                break;
            }
            case 200: {
                pr_02 = new rz_2();
                break;
            }
            case 202: {
                pr_02 = new tt_2();
                break;
            }
            case 204: {
                pr_02 = new il_0();
                break;
            }
            case 206: {
                pr_02 = new acc_2();
                break;
            }
            case 8: {
                pr_02 = new oq_1();
                break;
            }
            case 2: {
                pr_02 = new apg_1();
                break;
            }
            case 4: {
                pr_02 = new nu_2();
                break;
            }
            case 6: {
                pr_02 = new asu();
                break;
            }
            case 9: {
                pr_02 = new avT();
                break;
            }
            default: {
                pr_02 = this.fD(s);
            }
        }
        if (pr_02 == null) {
            byteBuffer.position(n4);
            a.error((Object)("Le message type=" + s + " inconnu du d\u00e9codeur !"));
        }
        if (pr_02 != null && byteBuffer.remaining() != 0) {
            byte[] byArray = new byte[n3 - 4];
            byteBuffer.get(byArray);
            try {
                long l2 = System.currentTimeMillis();
                pr_02.a(byArray);
                int n5 = (int)(System.currentTimeMillis() - l2);
                String string = pr_02.getClass().getSimpleName();
                Dg.g(string, true).fd(n5);
                Dg.g(string, true).fe(byArray.length);
            }
            catch (Throwable throwable) {
                a.error((Object)"Exception", throwable);
            }
        }
        return pr_02;
    }

    protected abstract pr_0 fD(int var1);
}

