/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.ByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from aip
 */
public class aip_2
extends alp
implements agk_1 {
    private volatile long hf;
    public static final int cxQ = XugglerJNI.IContainer_SEEK_FLAG_BACKWARDS_get();
    public static final int cxR = XugglerJNI.IContainer_SEEK_FLAG_BYTE_get();
    public static final int cxS = XugglerJNI.IContainer_SEEK_FLAG_ANY_get();
    public static final int cxT = XugglerJNI.IContainer_SEEK_FLAG_FRAME_get();

    private void noop() {
        di.a(null, 1);
    }

    protected aip_2(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIContainerUpcast(l2), bl2);
        this.hf = l2;
    }

    protected aip_2(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIContainerUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(aip_2 aip_22) {
        if (aip_22 == null) {
            return 0L;
        }
        return aip_22.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public aip_2 axG() {
        if (this.hf == 0L) {
            return null;
        }
        return new aip_2(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof aip_2) {
            bl2 = ((aip_2)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName() + "@" + this.hashCode() + "[");
        stringBuilder.append("url:" + this.getURL() + ";");
        stringBuilder.append("type:" + (Object)((Object)this.axL()) + ";");
        stringBuilder.append("format:" + this.axK() + ";");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int a(bc_1 bc_12, ds_0 ds_02, Sg sg) {
        return this.a(py_2.a(bc_12), ds_02, sg);
    }

    public int a(OutputStream outputStream, Sg sg) {
        return this.a(py_2.d(outputStream), ds_0.lF, sg);
    }

    public int a(InputStream inputStream, Sg sg) {
        return this.a(py_2.i(inputStream), ds_0.lE, sg);
    }

    public int a(DataOutput dataOutput, Sg sg) {
        return this.a(py_2.a(dataOutput), ds_0.lF, sg);
    }

    public int a(DataOutputStream dataOutputStream, Sg sg) {
        return this.a(py_2.d(dataOutputStream), ds_0.lF, sg);
    }

    public int a(DataInput dataInput, Sg sg) {
        return this.a(py_2.a(dataInput), ds_0.lE, sg);
    }

    public int a(DataInputStream dataInputStream, Sg sg) {
        return this.a(py_2.i(dataInputStream), ds_0.lE, sg);
    }

    public int a(RandomAccessFile randomAccessFile, ds_0 ds_02, Sg sg) {
        return this.a(py_2.a(randomAccessFile), ds_02, sg);
    }

    public int a(WritableByteChannel writableByteChannel, Sg sg) {
        return this.a(py_2.a(writableByteChannel), ds_0.lF, sg);
    }

    public int a(ReadableByteChannel readableByteChannel, Sg sg) {
        return this.a(py_2.a(readableByteChannel), ds_0.lE, sg);
    }

    public int a(ByteChannel byteChannel, ds_0 ds_02, Sg sg) {
        return this.a(py_2.a(byteChannel), ds_02, sg);
    }

    public int a(bc_1 bc_12, ds_0 ds_02, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(bc_12), ds_02, sg, bl2, bl3);
    }

    public int a(OutputStream outputStream, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.d(outputStream), ds_0.lF, sg, bl2, bl3);
    }

    public int a(InputStream inputStream, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.i(inputStream), ds_0.lE, sg, bl2, bl3);
    }

    public int a(DataOutput dataOutput, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(dataOutput), ds_0.lF, sg, bl2, bl3);
    }

    public int a(DataOutputStream dataOutputStream, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.d(dataOutputStream), ds_0.lF, sg, bl2, bl3);
    }

    public int a(DataInput dataInput, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(dataInput), ds_0.lE, sg, bl2, bl3);
    }

    public int a(DataInputStream dataInputStream, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.i(dataInputStream), ds_0.lE, sg, bl2, bl3);
    }

    public int a(RandomAccessFile randomAccessFile, ds_0 ds_02, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(randomAccessFile), ds_02, sg, bl2, bl3);
    }

    public int a(WritableByteChannel writableByteChannel, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(writableByteChannel), ds_0.lF, sg, bl2, bl3);
    }

    public int a(ReadableByteChannel readableByteChannel, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(readableByteChannel), ds_0.lE, sg, bl2, bl3);
    }

    public int a(ByteChannel byteChannel, ds_0 ds_02, Sg sg, boolean bl2, boolean bl3) {
        return this.a(py_2.a(byteChannel), ds_02, sg, bl2, bl3);
    }

    public String axH() {
        di di2 = di.a(null, 4096);
        int n2 = this.b(di2);
        if (n2 > 1) {
            byte[] byArray = new byte[n2 - 1];
            di2.a(0, byArray, 0, byArray.length);
            return new String(byArray);
        }
        return null;
    }

    public Collection ala() {
        LinkedList<String> linkedList = new LinkedList<String>();
        int n2 = this.alf();
        for (int j = 0; j < n2; ++j) {
            aoc_2 aoc_22 = this.iX(j);
            String string = aoc_22.getName();
            linkedList.add(string);
        }
        return linkedList;
    }

    public int dF(long l2) {
        return XugglerJNI.IContainer_setInputBufferLength(this.hf, this, l2);
    }

    public long axI() {
        return XugglerJNI.IContainer_getInputBufferLength(this.hf, this);
    }

    public boolean OL() {
        return XugglerJNI.IContainer_isOpened(this.hf, this);
    }

    public boolean axJ() {
        return XugglerJNI.IContainer_isHeaderWritten(this.hf, this);
    }

    public int a(String string, ds_0 ds_02, Sg sg) {
        return XugglerJNI.IContainer_open__SWIG_0(this.hf, this, string, ds_02.dZ(), Sg.a(sg), sg);
    }

    public int a(String string, ds_0 ds_02, Sg sg, boolean bl2, boolean bl3) {
        return XugglerJNI.IContainer_open__SWIG_1(this.hf, this, string, ds_02.dZ(), Sg.a(sg), sg, bl2, bl3);
    }

    public Sg axK() {
        long l2 = XugglerJNI.IContainer_getContainerFormat(this.hf, this);
        return l2 == 0L ? null : new Sg(l2, false);
    }

    public int de() {
        return XugglerJNI.IContainer_close(this.hf, this);
    }

    public ds_0 axL() {
        return ds_0.Y(XugglerJNI.IContainer_getType(this.hf, this));
    }

    public int axM() {
        return XugglerJNI.IContainer_getNumStreams(this.hf, this);
    }

    public at_2 dG(long l2) {
        long l3 = XugglerJNI.IContainer_getStream(this.hf, this, l2);
        return l3 == 0L ? null : new at_2(l3, false);
    }

    public at_2 kN(int n2) {
        long l2 = XugglerJNI.IContainer_addNewStream(this.hf, this, n2);
        return l2 == 0L ? null : new at_2(l2, false);
    }

    public int axN() {
        return XugglerJNI.IContainer_writeHeader(this.hf, this);
    }

    public int axO() {
        return XugglerJNI.IContainer_writeTrailer(this.hf, this);
    }

    public int b(ala_1 ala_12) {
        return XugglerJNI.IContainer_readNextPacket(this.hf, this, ala_1.d(ala_12), ala_12);
    }

    public int a(ala_1 ala_12, boolean bl2) {
        return XugglerJNI.IContainer_writePacket__SWIG_0(this.hf, this, ala_1.d(ala_12), ala_12, bl2);
    }

    public int c(ala_1 ala_12) {
        return XugglerJNI.IContainer_writePacket__SWIG_1(this.hf, this, ala_1.d(ala_12), ala_12);
    }

    public static aip_2 axP() {
        long l2 = XugglerJNI.IContainer_make();
        return l2 == 0L ? null : new aip_2(l2, false);
    }

    public int axQ() {
        return XugglerJNI.IContainer_queryStreamMetaData(this.hf, this);
    }

    public int a(int n2, long l2, int n3) {
        return XugglerJNI.IContainer_seekKeyFrame__SWIG_0(this.hf, this, n2, l2, n3);
    }

    public long getDuration() {
        return XugglerJNI.IContainer_getDuration(this.hf, this);
    }

    public long getStartTime() {
        return XugglerJNI.IContainer_getStartTime(this.hf, this);
    }

    public long axR() {
        return XugglerJNI.IContainer_getFileSize(this.hf, this);
    }

    public int anG() {
        return XugglerJNI.IContainer_getBitRate(this.hf, this);
    }

    public int alf() {
        return XugglerJNI.IContainer_getNumProperties(this.hf, this);
    }

    public aoc_2 iX(int n2) {
        long l2 = XugglerJNI.IContainer_getPropertyMetaData__SWIG_0(this.hf, this, n2);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public aoc_2 gI(String string) {
        long l2 = XugglerJNI.IContainer_getPropertyMetaData__SWIG_1(this.hf, this, string);
        return l2 == 0L ? null : new aoc_2(l2, false);
    }

    public int K(String string, String string2) {
        return XugglerJNI.IContainer_setProperty__SWIG_0(this.hf, this, string, string2);
    }

    public int a(String string, double d) {
        return XugglerJNI.IContainer_setProperty__SWIG_1(this.hf, this, string, d);
    }

    public int e(String string, long l2) {
        return XugglerJNI.IContainer_setProperty__SWIG_2(this.hf, this, string, l2);
    }

    public int r(String string, boolean bl2) {
        return XugglerJNI.IContainer_setProperty__SWIG_3(this.hf, this, string, bl2);
    }

    public int a(String string, xv_1 xv_12) {
        return XugglerJNI.IContainer_setProperty__SWIG_4(this.hf, this, string, xv_1.b(xv_12), xv_12);
    }

    public String gJ(String string) {
        return XugglerJNI.IContainer_getPropertyAsString(this.hf, this, string);
    }

    public double gK(String string) {
        return XugglerJNI.IContainer_getPropertyAsDouble(this.hf, this, string);
    }

    public long gL(String string) {
        return XugglerJNI.IContainer_getPropertyAsLong(this.hf, this, string);
    }

    public xv_1 gM(String string) {
        long l2 = XugglerJNI.IContainer_getPropertyAsRational(this.hf, this, string);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public boolean gN(String string) {
        return XugglerJNI.IContainer_getPropertyAsBoolean(this.hf, this, string);
    }

    public int getFlags() {
        return XugglerJNI.IContainer_getFlags(this.hf, this);
    }

    public void jo(int n2) {
        XugglerJNI.IContainer_setFlags(this.hf, this, n2);
    }

    public boolean a(abm_0 abm_02) {
        return XugglerJNI.IContainer_getFlag(this.hf, this, abm_02.dZ());
    }

    public void a(abm_0 abm_02, boolean bl2) {
        XugglerJNI.IContainer_setFlag(this.hf, this, abm_02.dZ(), bl2);
    }

    public String getURL() {
        return XugglerJNI.IContainer_getURL(this.hf, this);
    }

    public int axS() {
        return XugglerJNI.IContainer_flushPackets(this.hf, this);
    }

    public int axT() {
        return XugglerJNI.IContainer_getReadRetryCount(this.hf, this);
    }

    public void kO(int n2) {
        XugglerJNI.IContainer_setReadRetryCount(this.hf, this, n2);
    }

    public aow_1 axU() {
        long l2 = XugglerJNI.IContainer_getParameters(this.hf, this);
        return l2 == 0L ? null : new aow_1(l2, false);
    }

    public void a(aow_1 aow_12) {
        XugglerJNI.IContainer_setParameters(this.hf, this, aow_1.b(aow_12), aow_12);
    }

    public boolean axV() {
        return XugglerJNI.IContainer_canStreamsBeAddedDynamically(this.hf, this);
    }

    public aaw_1 HU() {
        long l2 = XugglerJNI.IContainer_getMetaData(this.hf, this);
        return l2 == 0L ? null : new aaw_1(l2, false);
    }

    public void a(aaw_1 aaw_12) {
        XugglerJNI.IContainer_setMetaData(this.hf, this, aaw_1.b(aaw_12), aaw_12);
    }

    public int b(di di2) {
        return XugglerJNI.IContainer_createSDPData(this.hf, this, di.a(di2), di2);
    }

    public int h(avh avh2) {
        return XugglerJNI.IContainer_setForcedAudioCodec(this.hf, this, avh2.dZ());
    }

    public int i(avh avh2) {
        return XugglerJNI.IContainer_setForcedVideoCodec(this.hf, this, avh2.dZ());
    }

    public int j(avh avh2) {
        return XugglerJNI.IContainer_setForcedSubtitleCodec(this.hf, this, avh2.dZ());
    }

    public int a(int n2, long l2, long l3, long l4, int n3) {
        return XugglerJNI.IContainer_seekKeyFrame__SWIG_1(this.hf, this, n2, l2, l3, l4, n3);
    }
}

