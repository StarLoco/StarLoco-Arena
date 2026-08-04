/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.image.AlphaBitmapData;
import com.ankamagames.framework.graphics.sba.records.Bitmap;
import com.ankamagames.framework.graphics.sba.records.Point;
import com.ankamagames.framework.graphics.sba.records.tags.DefinitionTag;
import java.io.IOException;

public class DefineBitmap
extends DefinitionTag {
    private Bitmap m_bitmap;
    private float m_invertScalingValue;

    public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData, float quality, float invertScalingValue) {
        this(identifier);
        this.set(hotPoint, bitmapData, quality, invertScalingValue);
    }

    public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData, float quality) {
        this(identifier);
        this.set(hotPoint, bitmapData, quality, 1.0f);
    }

    public DefineBitmap(int identifier, Point hotPoint, AlphaBitmapData bitmapData) {
        this(identifier, hotPoint, bitmapData, 1.0f, 1.0f);
    }

    public DefineBitmap(int identifier) {
        this.m_code = (short)2;
        this.m_identifier = identifier;
    }

    DefineBitmap() {
    }

    public void set(Point hotPoint, AlphaBitmapData alphaBitmapData, float quality, float invertScaleFactor) {
        this.m_bitmap = new Bitmap(hotPoint, alphaBitmapData, quality);
        this.m_invertScalingValue = invertScaleFactor;
    }

    public float getQuality() {
        return this.m_bitmap.getQuality();
    }

    public float getInvertScalingValue() {
        return this.m_invertScalingValue;
    }

    public int getWidth() {
        return this.m_bitmap.getWidth();
    }

    public int getHeight() {
        return this.m_bitmap.getHeight();
    }

    public Point getHotPoint() {
        return this.m_bitmap.getHotPoint();
    }

    public AlphaBitmapData getBitmapData() {
        return this.m_bitmap.getBitmapData();
    }

    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = this.readDefinitionTagHeader(data);
        this.m_invertScalingValue = inStream.readFloat16();
        this.m_bitmap = new Bitmap(inStream, version);
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        super.writeData(outStream);
        outStream.writeFloat16(this.m_invertScalingValue);
        if (this.m_bitmap != null) {
            this.m_bitmap.write(outStream);
        }
    }

    public boolean getAlphaPremultiplied() {
        if (this.m_bitmap != null) {
            return this.m_bitmap.isAlphaPremultiplied();
        }
        return false;
    }
}

