/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public abstract class aJj {
    protected static final Logger a = Logger.getLogger(aJj.class);

    public abstract aea_0[] Kl();

    int a(aea_0 aea_02) {
        aea_0[] aea_0Array = this.Kl();
        if (aea_0Array != null) {
            for (int j = 0; j < aea_0Array.length; ++j) {
                if (aea_0Array[j] != aea_02) continue;
                return j;
            }
        }
        return -1;
    }

    public final byte[] a(aea_0 ... aea_0Array) {
        if (aea_0Array != null && aea_0Array.length > 0) {
            return ajl_2.aza().a(this, aea_0Array);
        }
        throw new RuntimeException("Unable to serialize content without parts");
    }

    public final byte[] L(int ... nArray) {
        if (nArray != null && nArray.length > 0) {
            aea_0[] aea_0Array = new aea_0[nArray.length];
            aea_0[] aea_0Array2 = this.Kl();
            int n2 = 0;
            for (int n3 : nArray) {
                aea_0Array[n2++] = aea_0Array2[n3];
            }
            return ajl_2.aza().a(this, aea_0Array);
        }
        throw new RuntimeException("Unable to serialize content without parts");
    }

    public final void ad(byte[] byArray) {
        aea_0[] aea_0Array = this.Kl();
        if (aea_0Array != null && aea_0Array.length > 0) {
            int n2;
            ArrayList<aea_0> arrayList = new ArrayList<aea_0>(aea_0Array.length);
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            int n3 = byteBuffer.get();
            byte[] byArray2 = new byte[n3];
            int[] nArray = new int[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                byArray2[n2] = byteBuffer.get();
                nArray[n2] = byteBuffer.getInt();
            }
            for (n2 = 0; n2 < n3; ++n2) {
                byte by = byArray2[n2];
                int n4 = nArray[n2];
                int n5 = n2 < n3 - 1 ? nArray[n2 + 1] - n4 - 1 : byteBuffer.limit() - n4 - 1;
                if (n5 > 0) {
                    aex_1 aex_12 = ajl_2.aza().kZ(n5);
                    ByteBuffer byteBuffer2 = aex_12.aPW();
                    byteBuffer.position(n4 + 1);
                    byteBuffer2.limit(n5);
                    byteBuffer.get(byteBuffer2.array(), 0, n5);
                    if (by >= 0 && by < aea_0Array.length) {
                        aea_0 aea_02 = aea_0Array[by];
                        if (aea_02 == aea_0.dBr) {
                            a.warn((Object)("Don't know how to unserialise part #" + by + " (EMPTY)."));
                        } else if (aea_02 != null) {
                            try {
                                aea_02.clearError();
                                aea_02.f(byteBuffer2);
                                if (!aea_02.hasError()) {
                                    arrayList.add(aea_02);
                                }
                            }
                            catch (Exception exception) {
                                aea_02.a("Exception lev\u00e9e lors de la d\u00e9serialisation de la part :" + by, exception);
                            }
                            if (byteBuffer2.remaining() > 0) {
                                a.warn((Object)("Part " + by + " still have " + byteBuffer2.remaining() + " byte(s) left !"));
                            }
                        } else {
                            a.error((Object)("Part " + by + " is null"));
                        }
                    }
                    aex_12.N(byteBuffer2);
                    continue;
                }
                a.warn((Object)("Part " + by + "(offset=" + n4 + ") is empty ! : "));
            }
            for (aea_0 aea_03 : arrayList) {
                aea_03.aQz();
            }
        }
    }

    public final void a(aea_0 aea_02, byte[] byArray) {
        aea_0[] aea_0Array = this.Kl();
        if (aea_02 != null && aea_0Array != null && aea_0Array.length > 0) {
            int n2 = this.a(aea_02);
            if (n2 >= 0) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
                int n3 = byteBuffer.get();
                for (int j = 0; j < n3; ++j) {
                    byte by = byteBuffer.get();
                    int n4 = byteBuffer.getInt();
                    if (by != n2) continue;
                    byteBuffer.position(n4 + 1);
                    try {
                        aea_02.clearError();
                        aea_02.f(byteBuffer);
                        if (!aea_02.hasError()) {
                            aea_02.aQz();
                        }
                    }
                    catch (Exception exception) {
                        a.error((Object)("Exception lev\u00e9e lors de la d\u00e9serialisation de la part :" + n2), (Throwable)exception);
                    }
                    return;
                }
                throw new RuntimeException("Part (" + n2 + ")doesnt exist in BinarSerial class : " + this.getClass().getSimpleName());
            }
            throw new RuntimeException("Unable to find part in BinarSerial class : " + this.getClass().getSimpleName());
        }
    }
}

