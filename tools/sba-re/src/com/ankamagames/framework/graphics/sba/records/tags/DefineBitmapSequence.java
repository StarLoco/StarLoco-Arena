/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.graphics.sba.records.BitmapFrame;
import com.ankamagames.framework.graphics.sba.records.tags.DefineSequence;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DefineBitmapSequence
extends DefineSequence {
    private ArrayList<BitmapFrame> m_bitmapFrames;
    private float m_invertScalingValue;

    public DefineBitmapSequence(int identifier, float invertScaleFactor) {
        this.m_code = (short)3;
        this.m_identifier = identifier;
        this.m_invertScalingValue = invertScaleFactor;
        this.setLoopCount((short)0);
        this.m_bitmapFrames = new ArrayList();
    }

    public DefineBitmapSequence(int identifier) {
        this.m_code = (short)3;
        this.m_identifier = identifier;
        this.setLoopCount((short)0);
        this.m_bitmapFrames = new ArrayList();
    }

    DefineBitmapSequence() {
    }

    public float getInvertScalingValue() {
        return this.m_invertScalingValue;
    }

    public void setInvertScalingValue(float invertScaleFactor) {
        this.m_invertScalingValue = invertScaleFactor;
    }

    @Override
    public int getFrameCount() {
        return this.m_bitmapFrames.size();
    }

    public ArrayList<BitmapFrame> getBitmapFrames() {
        return this.m_bitmapFrames;
    }

    public void addBitmapFrame(BitmapFrame bitmapFrame) {
        this.m_bitmapFrames.add(bitmapFrame);
    }

    public void addBitmapFrames(ArrayList<BitmapFrame> bitmapFrames) {
        this.m_bitmapFrames.addAll(bitmapFrames);
    }

    public boolean removeBitmapFrame(BitmapFrame bitmapFrame) {
        return this.m_bitmapFrames.remove(bitmapFrame);
    }

    public BitmapFrame removeBitmapFrame(int index) {
        return this.m_bitmapFrames.remove(index);
    }

    @Override
    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = this.readDefinitionSequenceTagHeader(data);
        this.m_invertScalingValue = inStream.readFloat16();
        int numFrames = inStream.readUI16();
        this.m_bitmapFrames = new ArrayList();
        int i = 0;
        while (i < numFrames) {
            this.m_bitmapFrames.add(new BitmapFrame(inStream, version));
            ++i;
        }
    }

    @Override
    protected void writeData(OutputBitStream outStream) throws IOException {
        super.writeData(outStream);
        outStream.writeFloat16(this.m_invertScalingValue);
        int numFrames = this.m_bitmapFrames.size();
        outStream.writeUI16(this.m_bitmapFrames.size());
        int i = 0;
        while (i < numFrames) {
            BitmapFrame frame = this.m_bitmapFrames.get(i);
            frame.write(outStream);
            ++i;
        }
    }
}

