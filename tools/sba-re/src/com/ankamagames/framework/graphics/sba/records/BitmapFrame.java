/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.image.AlphaBitmapData;
import com.ankamagames.framework.graphics.sba.records.Bitmap;
import com.ankamagames.framework.graphics.sba.records.Point;
import java.io.IOException;

public class BitmapFrame
extends Bitmap {
    public static final int INFINIT_DURATION = -1;
    private int m_duration;

    public BitmapFrame(Point hotPoint, AlphaBitmapData bitmapData, float quality, int duration) {
        super(hotPoint, bitmapData, quality);
        this.m_duration = duration;
    }

    public BitmapFrame(InputBitStream inStream, short sbaversion) throws IOException {
        this.m_hotPoint = new Point(inStream);
        this.m_duration = inStream.readUI16();
        this.readBitmapData(inStream, sbaversion);
    }

    public BitmapFrame() {
    }

    public int getDuration() {
        return this.m_duration;
    }

    public void setDuration(int duration) {
        this.m_duration = duration;
    }

    public String toString() {
        return "BitmapFrame";
    }

    public void write(OutputBitStream outStream) throws IOException {
        this.m_hotPoint.write(outStream);
        outStream.writeUI16(this.m_duration);
        this.writeBitmapData(outStream);
    }
}

