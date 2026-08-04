/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class di
extends alp {
    private volatile long hf;

    public di(long l2, boolean bl2) {
        super(FerryJNI.SWIGIBufferUpcast(l2), bl2);
        this.hf = l2;
    }

    protected di(long l2, boolean bl2, AtomicLong atomicLong) {
        super(FerryJNI.SWIGIBufferUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long a(di di2) {
        if (di2 == null) {
            return 0L;
        }
        return di2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public void delete() {
        super.delete();
    }

    public di fE() {
        if (this.hf == 0L) {
            return null;
        }
        return new di(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof di) {
            bl2 = ((di)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    private void a(Object object, int n2, int n3, int n4, int n5, int n6) {
        if (n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n5 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n6 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n3 + n6 > n2) {
            throw new IndexOutOfBoundsException();
        }
        if (n5 + n6 > n4) {
            throw new IndexOutOfBoundsException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(byte[] byArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            byteBuffer.clear();
            this.a(byArray, byArray.length, n2, byteBuffer.limit(), n3, n4);
            byteBuffer.position(n3);
            byteBuffer.put(byArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, byte[] byArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            this.a(byArray, byArray.length, n3, byteBuffer.limit(), n2, n4);
            byteBuffer.position(n2);
            byteBuffer.get(byArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(short[] sArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            shortBuffer.clear();
            this.a(sArray, sArray.length, n2, shortBuffer.limit(), n3, n4);
            shortBuffer.position(n3);
            shortBuffer.put(sArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, short[] sArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
            this.a(sArray, sArray.length, n3, shortBuffer.limit(), n2, n4);
            shortBuffer.position(n2);
            shortBuffer.get(sArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int[] nArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.clear();
            this.a(nArray, nArray.length, n2, intBuffer.limit(), n3, n4);
            intBuffer.position(n3);
            intBuffer.put(nArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, int[] nArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            this.a(nArray, nArray.length, n3, intBuffer.limit(), n2, n4);
            intBuffer.position(n2);
            intBuffer.get(nArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(long[] lArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            LongBuffer longBuffer = byteBuffer.asLongBuffer();
            longBuffer.clear();
            this.a(lArray, lArray.length, n2, longBuffer.limit(), n3, n4);
            longBuffer.position(n3);
            longBuffer.put(lArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, long[] lArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            LongBuffer longBuffer = byteBuffer.asLongBuffer();
            this.a(lArray, lArray.length, n3, longBuffer.limit(), n2, n4);
            longBuffer.position(n2);
            longBuffer.get(lArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(double[] dArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            doubleBuffer.clear();
            this.a(dArray, dArray.length, n2, doubleBuffer.limit(), n3, n4);
            doubleBuffer.position(n3);
            doubleBuffer.put(dArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, double[] dArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            this.a(dArray, dArray.length, n3, doubleBuffer.limit(), n2, n4);
            doubleBuffer.position(n2);
            doubleBuffer.get(dArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(float[] fArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
            floatBuffer.clear();
            this.a(fArray, fArray.length, n2, floatBuffer.limit(), n3, n4);
            floatBuffer.position(n3);
            floatBuffer.put(fArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, float[] fArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
            this.a(fArray, fArray.length, n3, floatBuffer.limit(), n2, n4);
            floatBuffer.position(n2);
            floatBuffer.get(fArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void b(char[] cArray, int n2, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            charBuffer.clear();
            this.a(cArray, cArray.length, n2, charBuffer.limit(), n3, n4);
            charBuffer.position(n3);
            charBuffer.put(cArray, n2, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int n2, char[] cArray, int n3, int n4) {
        AtomicReference atomicReference = new AtomicReference();
        ByteBuffer byteBuffer = this.a(0, this.getBufferSize(), atomicReference);
        try {
            if (byteBuffer == null) {
                return;
            }
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            this.a(cArray, cArray.length, n3, charBuffer.limit(), n2, n4);
            charBuffer.position(n2);
            charBuffer.get(cArray, n3, n4);
            return;
        }
        finally {
            if (atomicReference.get() != null) {
                ((pu_2)atomicReference.get()).delete();
            }
        }
    }

    public ByteBuffer i(int n2, int n3) {
        return this.a(n2, n3, null);
    }

    public ByteBuffer a(int n2, int n3, AtomicReference atomicReference) {
        ByteBuffer byteBuffer = this.j(n2, n3);
        if (byteBuffer != null) {
            AtomicLong atomicLong = this.aAJ();
            atomicLong.incrementAndGet();
            pu_2 pu_22 = pu_2.b(this, byteBuffer, this.hf, atomicLong);
            if (atomicReference != null) {
                atomicReference.set(pu_22);
            }
            byteBuffer.order(ByteOrder.nativeOrder());
        }
        return byteBuffer;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[");
        stringBuilder.append("bytes=").append(this.getBufferSize()).append(";");
        stringBuilder.append("type=").append((Object)this.fF()).append(";");
        stringBuilder.append("size=").append(this.getSize()).append(";");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int getBufferSize() {
        return FerryJNI.IBuffer_getBufferSize(this.hf, this);
    }

    public static di a(alp alp2, int n2) {
        long l2 = FerryJNI.IBuffer_make__SWIG_0(alp.a(alp2), alp2, n2);
        return l2 == 0L ? null : new di(l2, false);
    }

    public amy_2 fF() {
        return amy_2.lv(FerryJNI.IBuffer_getType(this.hf, this));
    }

    public void a(amy_2 amy_22) {
        FerryJNI.IBuffer_setType(this.hf, this, amy_22.dZ());
    }

    public static int b(amy_2 amy_22) {
        return FerryJNI.IBuffer_getTypeSize(amy_22.dZ());
    }

    public int getSize() {
        return FerryJNI.IBuffer_getSize(this.hf, this);
    }

    public static di a(alp alp2, amy_2 amy_22, int n2, boolean bl2) {
        long l2 = FerryJNI.IBuffer_make__SWIG_1(alp.a(alp2), alp2, amy_22.dZ(), n2, bl2);
        return l2 == 0L ? null : new di(l2, false);
    }

    public ByteBuffer j(int n2, int n3) {
        return FerryJNI.IBuffer_java_getByteBuffer(this.hf, this, n2, n3);
    }

    public byte[] k(int n2, int n3) {
        return FerryJNI.IBuffer_getByteArray(this.hf, this, n2, n3);
    }

    public static di a(alp alp2, byte[] byArray, int n2, int n3) {
        long l2 = FerryJNI.IBuffer_make__SWIG_2(alp.a(alp2), alp2, byArray, n2, n3);
        return l2 == 0L ? null : new di(l2, false);
    }

    public static di a(alp alp2, ByteBuffer byteBuffer, int n2, int n3) {
        long l2 = FerryJNI.IBuffer_make__SWIG_3(alp.a(alp2), alp2, byteBuffer, n2, n3);
        return l2 == 0L ? null : new di(l2, false);
    }
}

