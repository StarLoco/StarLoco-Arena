/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInput;
import java.io.DataOutput;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.ByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 * Renamed from Py
 */
public class py_2
implements aFD {
    private final Bk ub = LD.p(this.getClass());
    public static final String bDZ = "xuggler";
    private static final boolean bEa = true;
    private static final boolean bEb = true;
    private ConcurrentMap bEc = new ConcurrentHashMap();
    private static final py_2 bEd = new py_2();

    py_2() {
    }

    static py_2 fC(String string) {
        arS arS2 = arS.aFa();
        arS2.a(string, bEd);
        return bEd;
    }

    public static py_2 aci() {
        return bEd;
    }

    public static String ab(Object object) {
        return py_2.a(object, null);
    }

    public static String a(Object object, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UUID.randomUUID().toString());
        if (object != null) {
            stringBuilder.append("-");
            stringBuilder.append(object.getClass().getName());
            stringBuilder.append("-");
            stringBuilder.append(Integer.toHexString(object.hashCode()));
        }
        if (string != null) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String a(bc_1 bc_12) {
        return py_2.a(py_2.ab(bc_12), bc_12, true);
    }

    public static String a(String string, bc_1 bc_12) {
        return py_2.a(string, bc_12, true);
    }

    public static String a(DataInput dataInput) {
        return py_2.a(py_2.ab(dataInput), dataInput, null, true, true);
    }

    public static String a(String string, DataInput dataInput) {
        return py_2.a(string, dataInput, null, true, true);
    }

    public static String a(DataOutput dataOutput) {
        return py_2.a(py_2.ab(dataOutput), null, dataOutput, true, true);
    }

    public static String a(String string, DataOutput dataOutput) {
        return py_2.a(string, null, dataOutput, true, true);
    }

    public static String a(RandomAccessFile randomAccessFile) {
        return py_2.a(py_2.ab(randomAccessFile), randomAccessFile, randomAccessFile, true, true);
    }

    public static String a(String string, RandomAccessFile randomAccessFile) {
        return py_2.a(string, randomAccessFile, randomAccessFile, true, true);
    }

    public static String a(ReadableByteChannel readableByteChannel) {
        return py_2.a(py_2.ab(readableByteChannel), readableByteChannel, null, true, true);
    }

    public static String a(String string, ReadableByteChannel readableByteChannel) {
        return py_2.a(string, readableByteChannel, null, true, true);
    }

    public static String a(WritableByteChannel writableByteChannel) {
        return py_2.a(py_2.ab(writableByteChannel), null, writableByteChannel, true, true);
    }

    public static String a(String string, WritableByteChannel writableByteChannel) {
        return py_2.a(string, null, writableByteChannel, true, true);
    }

    public static String a(ByteChannel byteChannel) {
        return py_2.a(py_2.ab(byteChannel), byteChannel, byteChannel, true, true);
    }

    public static String a(String string, ByteChannel byteChannel) {
        return py_2.a(string, byteChannel, byteChannel, true, true);
    }

    public static String i(InputStream inputStream) {
        return py_2.a(py_2.ab(inputStream), inputStream, null, true, true);
    }

    public static String a(String string, InputStream inputStream) {
        return py_2.a(string, inputStream, null, true, true);
    }

    public static String d(OutputStream outputStream) {
        return py_2.a(py_2.ab(outputStream), null, outputStream, true, true);
    }

    public static String a(String string, OutputStream outputStream) {
        return py_2.a(string, null, outputStream, true, true);
    }

    public static String a(String string, DataInput dataInput, DataOutput dataOutput, boolean bl2, boolean bl3) {
        return py_2.a(string, new kh_0(dataInput, dataOutput, bl3));
    }

    public static String a(String string, ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, boolean bl2, boolean bl3) {
        return py_2.a(string, new dd_0(readableByteChannel, writableByteChannel, bl3));
    }

    public static String a(String string, InputStream inputStream, OutputStream outputStream, boolean bl2, boolean bl3) {
        return py_2.a(string, new wm_2(inputStream, outputStream, bl3));
    }

    public static String a(String string, bc_1 bc_12, boolean bl2) {
        if (bEd.b(string, bc_12, bl2) != null) {
            throw new RuntimeException("url is already mapped: " + string);
        }
        return "xuggler:" + arS.js(string);
    }

    public static bc_1 fD(String string) {
        return bEd.fE(string);
    }

    public bc_1 b(String string, bc_1 bc_12, boolean bl2) {
        dT dT2;
        if (string == null || string.length() <= 0) {
            throw new IllegalArgumentException("must pass in non-zero url");
        }
        if (bc_12 == null) {
            throw new IllegalArgumentException("must pass in a non null handler");
        }
        String string2 = arS.js(string);
        dT dT3 = this.bEc.putIfAbsent(string2, dT2 = new dT(string2, bc_12, bl2));
        return dT3 == null ? null : dT3.gK();
    }

    public bc_1 fE(String string) {
        if (string == null || string.length() <= 0) {
            throw new IllegalArgumentException("must pass in non-zero url");
        }
        String string2 = arS.js(string);
        dT dT2 = (dT)this.bEc.remove(string2);
        return dT2 == null ? null : dT2.gK();
    }

    public bc_1 a(String string, String string2, int n2) {
        String string3 = arS.js(string2);
        dT dT2 = (dT)this.bEc.get(string3);
        if (dT2 != null) {
            bc_1 bc_12 = dT2.gK();
            if (dT2.gJ()) {
                bc_1 bc_13 = this.fE(dT2.getName());
                if (bc_12 != null && !bc_12.equals(bc_13)) {
                    this.ub.m("stream {} already unmapped; it was likely already opened", dT2.getName());
                    return null;
                }
            }
            return bc_12;
        }
        return null;
    }

    static {
        py_2.fC(bDZ);
    }
}

