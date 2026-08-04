/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records.tags;

import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;

public abstract class Tag {
    protected boolean m_forceLongHeader;
    private byte[] m_outData;
    protected short m_code;
    protected int m_length;

    public int getCode() {
        return this.m_code;
    }

    void setCode(short code) {
        this.m_code = code;
    }

    public int getLength() {
        return this.m_length;
    }

    public void setLength(int length) {
        this.m_length = length;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }

    protected abstract void writeData(OutputBitStream var1) throws IOException;

    public abstract void setData(byte[] var1, short var2) throws IOException;

    void write(OutputBitStream stream) throws IOException {
        this.initData(stream);
        stream.writeBytes(this.getHeaderData());
        stream.writeBytes(this.m_outData);
    }

    private byte[] getHeaderData() throws IOException {
        OutputBitStream headerStream = new OutputBitStream();
        int typeAndLength = this.m_code << 6;
        this.m_length = this.m_outData.length;
        if (this.m_forceLongHeader || this.m_length >= 63) {
            headerStream.writeUI16(typeAndLength |= 0x3F);
            headerStream.writeUI32(this.m_length);
        } else {
            headerStream.writeUI16(typeAndLength |= this.m_length);
        }
        return headerStream.getData();
    }

    private void initData(OutputBitStream parentStream) throws IOException {
        OutputBitStream outStream = new OutputBitStream();
        this.writeData(outStream);
        this.m_outData = outStream.getData();
    }
}

