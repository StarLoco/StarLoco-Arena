/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.graphics.sba.records.ColorTransform;
import com.ankamagames.framework.graphics.sba.records.Matrix;
import java.io.IOException;

public class PlaceObject
extends Tag {
    public static final int INVALID_ID = 0;
    private int m_identifier;
    private int m_depth;
    private Matrix m_matrix;
    private ColorTransform m_colorTransform;

    public PlaceObject(int identifier, int depth, Matrix matrix) {
        this.m_code = (short)5;
        this.m_identifier = identifier;
        this.m_depth = depth;
        this.m_matrix = matrix;
    }

    public PlaceObject() {
    }

    public int getDepth() {
        return this.m_depth;
    }

    public void setDepth(int depth) {
        this.m_depth = depth;
    }

    public int getIdentifier() {
        return this.m_identifier;
    }

    public void setIdentifier(int identifier) {
        this.m_identifier = identifier;
    }

    public boolean hasCharacterId() {
        return this.m_identifier != 0;
    }

    public Matrix getMatrix() {
        return this.m_matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.m_matrix = matrix;
    }

    public ColorTransform getColorTransform() {
        return this.m_colorTransform;
    }

    public void setColorTransform(ColorTransform colorTransform) {
        this.m_colorTransform = colorTransform;
    }

    public void setData(byte[] data, short version) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        this.m_identifier = inStream.readUI16();
        this.m_depth = inStream.readUI16();
        if (inStream.readBooleanBit()) {
            this.m_matrix = new Matrix(inStream);
        }
        if (inStream.readBooleanBit()) {
            this.m_colorTransform = new ColorTransform(inStream);
        }
    }

    protected void writeData(OutputBitStream outStream) throws IOException {
        outStream.writeUI16(this.m_identifier);
        outStream.writeUI16(this.m_depth);
        if (this.m_matrix != null) {
            outStream.writeBooleanBit(true);
            this.m_matrix.write(outStream);
        } else {
            outStream.writeBooleanBit(false);
        }
        if (this.m_colorTransform != null) {
            outStream.writeBooleanBit(true);
            this.m_colorTransform.write(outStream);
        } else {
            outStream.writeBooleanBit(false);
        }
        outStream.align();
    }
}

