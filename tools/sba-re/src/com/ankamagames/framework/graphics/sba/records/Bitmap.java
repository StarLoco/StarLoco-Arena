/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.image.AlphaBitmapData;
import com.ankamagames.framework.graphics.sba.records.Point;
import java.io.IOException;

public class Bitmap {
    public static final float LOSSLESS_QUALITY = 1.0f;
    public static final float DEFAULT_JPEG_QUALITY = 0.7f;
    protected Point m_hotPoint;
    protected AlphaBitmapData m_bitmapData;
    private float m_quality;

    public Bitmap(Point hotPoint, AlphaBitmapData bitmapData, float quality) {
        this.m_hotPoint = hotPoint;
        this.setBitmapData(bitmapData);
        this.m_quality = quality;
    }

    public Bitmap(Point hotPoint, AlphaBitmapData bitmapData) {
        this(hotPoint, bitmapData, 1.0f);
    }

    public Bitmap(InputBitStream inStream, short version) throws IOException {
        this.m_hotPoint = new Point(inStream);
        this.readBitmapData(inStream, version);
    }

    public Bitmap() {
        this.m_hotPoint = new Point(0, 0);
    }

    public float getQuality() {
        return this.m_quality;
    }

    public void setQuality(float quality) {
        this.m_quality = quality;
    }

    public int getWidth() {
        if (this.m_bitmapData == null) {
            return 0;
        }
        return this.m_bitmapData.getWidth();
    }

    public int getHeight() {
        if (this.m_bitmapData == null) {
            return 0;
        }
        return this.m_bitmapData.getHeight();
    }

    public boolean isAlphaPremultiplied() {
        return this.m_bitmapData != null && this.m_bitmapData.isAlphaPremultiplied();
    }

    public Point getHotPoint() {
        return this.m_hotPoint;
    }

    public void setHotPoint(Point hotPoint) {
        this.m_hotPoint = hotPoint;
    }

    public AlphaBitmapData getBitmapData() {
        return this.m_bitmapData;
    }

    public void setBitmapData(AlphaBitmapData bitmapData) {
        this.m_bitmapData = bitmapData;
    }

    public void write(OutputBitStream outStream) throws IOException {
        this.m_hotPoint.write(outStream);
        this.writeBitmapData(outStream);
    }

    protected void writeBitmapData(OutputBitStream outStream) throws IOException {
        if (this.m_bitmapData != null) {
            outStream.writeUI8((short)(this.m_quality * 100.0f));
            OutputBitStream zStream = new OutputBitStream();
            zStream.enableCompression();
            this.m_bitmapData.write(zStream);
            byte[] zData = zStream.getData();
            outStream.writeUI16(zData.length);
            outStream.writeBytes(zData);
        }
    }

    protected void readBitmapData(InputBitStream inStream, short sbaversion) throws IOException {
        switch (sbaversion) {
            case 1: {
                this.read1(inStream, sbaversion);
                break;
            }
            case 2: {
                this.read2(inStream);
                break;
            }
            case 3: {
                this.read3(inStream);
                break;
            }
            default: {
                System.err.println("SBA Version inconnue:" + sbaversion + " courante:" + 3);
            }
        }
    }

    protected void read1(InputBitStream inStream, short sbaversion) throws IOException {
        short quality = inStream.readUI8();
        int dataLength = inStream.readUI16();
        if (dataLength > 0) {
            this.m_bitmapData = AlphaBitmapData.OldVersionReader.read1(inStream, dataLength);
        }
    }

    protected void read2(InputBitStream inStream) throws IOException {
        this.m_quality = (float)inStream.readUI8() / 100.0f;
        int dataLength = inStream.readUI16();
        if (dataLength > 0) {
            InputBitStream zStream = new InputBitStream(inStream.readBytes(dataLength));
            zStream.enableCompression();
            this.m_bitmapData = AlphaBitmapData.OldVersionReader.read2(zStream);
        }
    }

    protected void read3(InputBitStream inStream) throws IOException {
        this.m_quality = (float)inStream.readUI8() / 100.0f;
        int dataLength = inStream.readUI16();
        if (dataLength > 0) {
            InputBitStream zStream = new InputBitStream(inStream.readBytes(dataLength));
            zStream.enableCompression();
            this.m_bitmapData = new AlphaBitmapData(zStream);
        }
    }
}

