/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class OutputBitStream {
    private OutputStream m_stream;
    private ByteArrayOutputStream m_memoryStream;
    private int m_bitBuffer;
    private int m_bitCursor;
    private boolean m_compressed = false;
    private long m_offset;
    private boolean m_isMemoryStream;

    public OutputBitStream(OutputStream stream) {
        this.m_stream = stream;
    }

    public OutputBitStream() {
        this.m_memoryStream = new ByteArrayOutputStream();
        this.m_stream = this.m_memoryStream;
        this.m_isMemoryStream = true;
    }

    public byte[] getData() {
        if (!this.m_isMemoryStream) {
            throw new IllegalStateException("Use this method only with memory streams!");
        }
        try {
            this.m_stream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return this.m_memoryStream.toByteArray();
    }

    public static int getFPBitsLength(double value) {
        if (value == 0.0) {
            return 1;
        }
        long fpBits = (long)(value * 65536.0);
        return OutputBitStream.getSignedBitsLength(fpBits);
    }

    public long getOffset() {
        return this.m_offset;
    }

    public static int getSignedBitsLength(long value) {
        int nBits = value == 0L ? 0 : (int)(Math.floor(Math.log(Math.abs(value)) / Math.log(2.0)) + 2.0);
        return nBits;
    }

    public static int getUnsignedBitsLength(long value) {
        if (value < 1L) {
            return 0;
        }
        return (int)(Math.floor(Math.log(value) / Math.log(2.0)) + 1.0);
    }

    public void align() throws IOException {
        if (this.m_bitCursor > 0) {
            this.m_stream.write(this.m_bitBuffer);
            ++this.m_offset;
            this.m_bitCursor = 0;
            this.m_bitBuffer = 0;
        }
    }

    public void close() throws IOException {
        this.align();
        this.m_stream.close();
    }

    public void enableCompression() {
        if (!this.m_compressed) {
            this.m_stream = new BufferedOutputStream(new DeflaterOutputStream(this.m_stream, new Deflater(9)));
            this.m_compressed = true;
        }
    }

    public void flush() throws IOException {
        this.m_stream.flush();
    }

    public void writeBooleanBit(boolean value) throws IOException {
        this.writeUnsignedBits(value ? 1 : 0, 1);
    }

    public void writeBytes(byte[] buffer) throws IOException {
        this.align();
        if (buffer == null) {
            return;
        }
        this.m_stream.write(buffer);
        this.m_offset += (long)buffer.length;
    }

    public void writeDouble(double value) throws IOException {
        long longBits = Double.doubleToLongBits(value);
        byte[] buffer = new byte[]{(byte)(longBits >> 32), (byte)(longBits >> 40), (byte)(longBits >> 48), (byte)(longBits >> 56), (byte)longBits, (byte)(longBits >> 8), (byte)(longBits >> 16), (byte)(longBits >> 24)};
        this.writeBytes(buffer);
    }

    public void writeFP16(double value) throws IOException {
        this.writeSI16((short)(value * 256.0));
    }

    public void writeFP32(double value) throws IOException {
        this.writeSI32((int)(value * 65536.0));
    }

    public void writeFPBits(double value, int nBits) throws IOException {
        long fpBits = (long)(value * 65536.0);
        this.writeSignedBits(fpBits, nBits);
    }

    public void writeFloat(float value) throws IOException {
        this.writeSI32(Float.floatToIntBits(value));
    }

    public void writeFloat16(float value) throws IOException {
        int bits32 = Float.floatToIntBits(value);
        int sign = Math.abs((bits32 & Integer.MIN_VALUE) >> 31);
        int exponent32 = (bits32 & 0x7F800000) >> 23;
        int mantissa32 = bits32 & 0x7FFFFF;
        int exponent16 = 0;
        if (exponent32 != 0) {
            exponent16 = exponent32 == 255 ? 31 : exponent32 - 127 + 15;
        }
        int mantissa16 = 0;
        if (exponent16 < 0) {
            exponent16 = 0;
        } else if (exponent16 > 31) {
            exponent16 = 31;
        } else {
            mantissa16 = mantissa32 >> 13;
        }
        int bits16 = sign << 15;
        bits16 |= exponent16 << 10;
        this.writeUI16(bits16 |= mantissa16);
    }

    public void writeSI16(short value) throws IOException {
        this.align();
        this.m_stream.write(value & 0xFF);
        this.m_stream.write(value >> 8);
        this.m_offset += 2L;
    }

    public void writeSI32(int value) throws IOException {
        this.align();
        this.m_stream.write(value & 0xFF);
        this.m_stream.write(value >> 8);
        this.m_stream.write(value >> 16);
        this.m_stream.write(value >> 24);
        this.m_offset += 4L;
    }

    public void writeSI8(byte value) throws IOException {
        this.align();
        this.m_stream.write(value);
        ++this.m_offset;
    }

    public void writeSignedBits(long value, int nBits) throws IOException {
        int bitsNeeded = OutputBitStream.getSignedBitsLength(value);
        if (nBits < bitsNeeded) {
            throw new IOException("At least " + bitsNeeded + " bits needed for representation of " + value);
        }
        this.writeInteger(value, nBits);
    }

    public void writeString(String string) throws IOException {
        this.writeBytes(string.getBytes("UTF-8"));
        this.m_stream.write(0);
        ++this.m_offset;
    }

    public void writeUI16(int value) throws IOException {
        this.align();
        this.m_stream.write(value & 0xFF);
        this.m_stream.write(value >> 8);
        this.m_offset += 2L;
    }

    public void writeUI32(long value) throws IOException {
        this.align();
        this.m_stream.write((int)(value & 0xFFL));
        this.m_stream.write((int)(value >> 8));
        this.m_stream.write((int)(value >> 16));
        this.m_stream.write((int)(value >> 24));
        this.m_offset += 4L;
    }

    public void writeUI8(short value) throws IOException {
        this.align();
        this.m_stream.write(value);
        ++this.m_offset;
    }

    public void writeUnsignedBits(long value, int nBits) throws IOException {
        int bitsNeeded = OutputBitStream.getUnsignedBitsLength(value);
        if (nBits < bitsNeeded) {
            throw new IOException("At least " + bitsNeeded + " bits needed for representation of " + value + ". Used bits: " + nBits);
        }
        this.writeInteger(value, nBits);
    }

    private void writeInteger(long value, int nBits) throws IOException {
        int bitsLeft = nBits;
        while (bitsLeft > 0) {
            ++this.m_bitCursor;
            if ((1L << bitsLeft - 1 & value) != 0L) {
                this.m_bitBuffer |= 1 << 8 - this.m_bitCursor;
            }
            if (this.m_bitCursor == 8) {
                this.m_stream.write(this.m_bitBuffer);
                ++this.m_offset;
                this.m_bitCursor = 0;
                this.m_bitBuffer = 0;
            }
            --bitsLeft;
        }
    }
}

