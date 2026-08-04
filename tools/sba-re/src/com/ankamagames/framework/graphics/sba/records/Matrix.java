/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;

public class Matrix {
    private float m_scaleX = 1.0f;
    private float m_scaleY = 1.0f;
    private float m_rotateSkew0 = 0.0f;
    private float m_rotateSkew1 = 0.0f;
    private float m_translateX = 0.0f;
    private float m_translateY = 0.0f;
    private boolean m_hasTranslate;
    private boolean m_hasScale;
    private boolean m_hasRotateSkew;

    public Matrix(float translateX, float translateY) {
        this.m_translateX = translateX;
        this.m_translateY = translateY;
        this.m_hasTranslate = true;
    }

    public Matrix() {
    }

    public Matrix(InputBitStream stream) throws IOException {
        this.m_hasScale = stream.readBooleanBit();
        if (this.m_hasScale) {
            int nScaleBits = (int)stream.readUnsignedBits(5);
            this.m_scaleX = (float)stream.readFPBits(nScaleBits);
            this.m_scaleY = (float)stream.readFPBits(nScaleBits);
        }
        this.m_hasRotateSkew = stream.readBooleanBit();
        if (this.m_hasRotateSkew) {
            int nRotateBits = (int)stream.readUnsignedBits(5);
            this.m_rotateSkew0 = (float)stream.readFPBits(nRotateBits);
            this.m_rotateSkew1 = (float)stream.readFPBits(nRotateBits);
        }
        this.m_hasTranslate = stream.readBooleanBit();
        if (this.m_hasTranslate) {
            int nTranslateBits = (int)stream.readUnsignedBits(5);
            this.m_translateX = (float)stream.readFPBits(nTranslateBits);
            this.m_translateY = (float)stream.readFPBits(nTranslateBits);
        }
        stream.align();
    }

    public void setRotateSkew(float rotateSkew0, float rotateSkew1) {
        this.m_rotateSkew0 = rotateSkew0;
        this.m_rotateSkew1 = rotateSkew1;
        this.m_hasRotateSkew = true;
    }

    public float getRotateSkew0() {
        return this.m_rotateSkew0;
    }

    public float getRotateSkew1() {
        return this.m_rotateSkew1;
    }

    public void setScale(float scaleX, float scaleY) {
        this.m_scaleX = scaleX;
        this.m_scaleY = scaleY;
        this.m_hasScale = true;
    }

    public float getScaleX() {
        return this.m_scaleX;
    }

    public float getScaleY() {
        return this.m_scaleY;
    }

    public float getTranslateX() {
        return this.m_translateX;
    }

    public float getTranslateY() {
        return this.m_translateY;
    }

    public void setTranslate(float translateX, float translateY) {
        this.m_translateX = translateX;
        this.m_translateY = translateY;
        this.m_hasTranslate = true;
    }

    public boolean hasTranslate() {
        return this.m_hasTranslate;
    }

    public boolean hasRotateSkew() {
        return this.m_hasRotateSkew;
    }

    public boolean hasScale() {
        return this.m_hasScale;
    }

    public String toString() {
        return "Matrix (scaleX=" + this.m_scaleX + " scaleY=" + this.m_scaleY + " rotateSkew0=" + this.m_rotateSkew0 + " rotateSkew1=" + this.m_rotateSkew1 + " translateX=" + this.m_translateX + " translateY=" + this.m_translateY + ")";
    }

    public void write(OutputBitStream stream) throws IOException {
        stream.writeBooleanBit(this.m_hasScale);
        if (this.m_hasScale) {
            int nScaleBits = OutputBitStream.getFPBitsLength(this.m_scaleX);
            nScaleBits = Math.max(nScaleBits, OutputBitStream.getFPBitsLength(this.m_scaleY));
            stream.writeUnsignedBits(nScaleBits, 5);
            stream.writeFPBits(this.m_scaleX, nScaleBits);
            stream.writeFPBits(this.m_scaleY, nScaleBits);
        }
        stream.writeBooleanBit(this.m_hasRotateSkew);
        if (this.m_hasRotateSkew) {
            int nRotateBits = OutputBitStream.getFPBitsLength(this.m_rotateSkew0);
            nRotateBits = Math.max(nRotateBits, OutputBitStream.getFPBitsLength(this.m_rotateSkew1));
            stream.writeUnsignedBits(nRotateBits, 5);
            stream.writeFPBits(this.m_rotateSkew0, nRotateBits);
            stream.writeFPBits(this.m_rotateSkew1, nRotateBits);
        }
        stream.writeBooleanBit(this.m_hasTranslate);
        if (this.m_hasTranslate) {
            int nTranslateBits = OutputBitStream.getFPBitsLength(this.m_translateX);
            nTranslateBits = Math.max(nTranslateBits, OutputBitStream.getFPBitsLength(this.m_translateY));
            stream.writeUnsignedBits(nTranslateBits, 5);
            stream.writeFPBits(this.m_translateX, nTranslateBits);
            stream.writeFPBits(this.m_translateY, nTranslateBits);
        }
        stream.align();
    }

    public boolean scaleEquals(float sx, float sy) {
        return this.m_scaleX == sx && this.m_scaleY == sy;
    }

    public boolean translateEquals(float tx, float ty) {
        return this.m_translateX == tx && this.m_translateY == ty;
    }

    public boolean rotateSkewEquals(float rsx, float rsy) {
        return this.m_rotateSkew0 == rsx && this.m_rotateSkew1 == rsy;
    }
}

