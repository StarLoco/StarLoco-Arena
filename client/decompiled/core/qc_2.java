/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from qc
 */
public final class qc_2
implements eG {
    public static final Logger a = Logger.getLogger(qc_2.class);

    public qc_2() {
        um_1.AF();
    }

    public pr_0 g(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.limit() - byteBuffer.position();
        byteBuffer.mark();
        if (n2 >= 7) {
            boolean bl2;
            int n3 = byteBuffer.getShort() & 0xFFFF;
            int n4 = byteBuffer.getInt();
            boolean bl3 = bl2 = byteBuffer.get() == 1;
            if (n3 < 7) {
                a.error((Object)"Message size < 7 bytes!!!");
                byteBuffer.reset();
                return null;
            }
            if (byteBuffer.remaining() < n3 - 7) {
                byteBuffer.reset();
                return null;
            }
            byte[] byArray = new byte[n3 - 7];
            byteBuffer.get(byArray);
            byte[] byArray2 = byArray;
            if (bl2) {
                byArray2 = um_1.y(byArray);
            }
            axX axX2 = null;
            switch (n4) {
                case 1: {
                    axX2 = ll_2.qb();
                    break;
                }
                case 3: {
                    axX2 = po_1.uf();
                    break;
                }
                case 2: {
                    axX2 = tI.zN();
                    break;
                }
                case 10: {
                    axX2 = ms_0.rz();
                    break;
                }
                case 11: {
                    axX2 = afy_2.avQ();
                    break;
                }
                case 12: {
                    axX2 = pn_2.acu();
                    break;
                }
                case 20: {
                    axX2 = rx_0.xU();
                    break;
                }
                default: {
                    a.error((Object)"Unknown message");
                }
            }
            if (axX2 != null) {
                axX2.b();
                long l2 = System.currentTimeMillis();
                axX2.a(byArray2);
                int n5 = (int)(System.currentTimeMillis() - l2);
                String string = axX2.getClass().getSimpleName();
                Dg.g(string, true).fd(n5);
                Dg.g(string, true).fe(byArray2.length);
            }
            return axX2;
        }
        return null;
    }
}

