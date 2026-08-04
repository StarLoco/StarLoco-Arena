/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from rX
 */
public class rx_0
extends axX {
    private static final acl_0 aU = new ym_0(new afl_2());
    private static final byte JR = 0;
    private static final byte JS = 1;
    private static final byte JT = 2;
    private static final byte JU = 3;
    private static final byte JW = 4;
    private static final byte JV = 5;
    private static final byte JX = 6;
    private final qg_0 aiH = new qg_0();
    private List aiI = null;
    private int aiJ;

    public static rx_0 xU() {
        try {
            rx_0 rx_02 = (rx_0)aU.adr();
            rx_02.a(aU);
            return rx_02;
        }
        catch (Exception exception) {
            a.error((Object)bl_0.b(exception));
            return new rx_0();
        }
    }

    private rx_0() {
    }

    public int xV() {
        return this.aiJ;
    }

    public void dw(int n2) {
        this.aiJ = n2;
    }

    public void z(byte by) {
        this.aiH.x((byte)0);
        this.aiH.x(by);
    }

    public void Z(short s) {
        this.aiH.x((byte)1);
        this.aiH.S(s);
    }

    public void dx(int n2) {
        this.aiH.x((byte)2);
        this.aiH.putInt(n2);
    }

    public void aG(long l2) {
        this.aiH.x((byte)3);
        this.aiH.aB(l2);
    }

    public void P(float f) {
        this.aiH.x((byte)4);
        this.aiH.N(f);
    }

    public void j(double d) {
        this.aiH.x((byte)5);
        this.aiH.h(d);
    }

    public void bS(String string) {
        this.aiH.x((byte)6);
        byte[] byArray = aey_0.hH(string);
        this.aiH.x((byte)byArray.length);
        this.aiH.t(byArray);
    }

    public Object[] getParameters() {
        if (this.aiI != null) {
            return this.aiI.toArray(new Object[this.aiI.size()]);
        }
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public byte[] encode() {
        byte[] byArray = this.aiH.toArray();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + byArray.length);
        byteBuffer.putInt(this.aiJ);
        byteBuffer.put(byArray);
        return this.x(byteBuffer.array());
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aiJ = byteBuffer.getInt();
        this.aiI = new ArrayList();
        while (byteBuffer.remaining() > 0) {
            switch (byteBuffer.get()) {
                case 0: {
                    this.aiI.add(byteBuffer.get());
                    break;
                }
                case 1: {
                    this.aiI.add(byteBuffer.getShort());
                    break;
                }
                case 2: {
                    this.aiI.add(byteBuffer.getInt());
                    break;
                }
                case 3: {
                    this.aiI.add(byteBuffer.getLong());
                    break;
                }
                case 4: {
                    this.aiI.add(Float.valueOf(byteBuffer.getFloat()));
                    break;
                }
                case 5: {
                    this.aiI.add(byteBuffer.getDouble());
                    break;
                }
                case 6: {
                    byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
                    this.aiI.add(aey_0.V(byArray2));
                }
            }
        }
        return true;
    }

    public int getId() {
        return 20;
    }

    /* synthetic */ rx_0(afl_2 afl_22) {
        this();
    }
}

