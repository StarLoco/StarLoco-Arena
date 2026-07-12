/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;

public class Rect {
    private int m_xMin;
    private int m_xMax;
    private int m_yMin;
    private int m_yMax;

    public Rect(int xMin, int yMin, int xMax, int yMax) {
        this.m_xMin = xMin;
        this.m_yMin = yMin;
        this.m_xMax = xMax;
        this.m_yMax = yMax;
    }

    public Rect(InputBitStream stream) throws IOException {
        this.m_xMin = stream.readSI32();
        this.m_yMin = stream.readSI32();
        this.m_xMax = stream.readSI32();
        this.m_yMax = stream.readSI32();
        stream.align();
    }

    public void setXMax(int xMax) {
        this.m_xMax = xMax;
    }

    public void setXMin(int xMin) {
        this.m_xMin = xMin;
    }

    public void setYMax(int yMax) {
        this.m_yMax = yMax;
    }

    public void setYMin(int yMin) {
        this.m_yMin = yMin;
    }

    public int getXMax() {
        return this.m_xMax;
    }

    public int getXMin() {
        return this.m_xMin;
    }

    public int getYMax() {
        return this.m_yMax;
    }

    public int getYMin() {
        return this.m_yMin;
    }

    public int getWidth() {
        return this.m_xMax - this.m_xMin;
    }

    public int getHeight() {
        return this.m_yMax - this.m_yMin;
    }

    public void move(int dx, int dy) {
        this.m_xMin += dx;
        this.m_xMax += dx;
        this.m_yMin += dy;
        this.m_yMax += dy;
    }

    public void scale(float ratio) {
        this.m_xMin = (int)((float)this.m_xMin * ratio);
        this.m_xMax = (int)((float)this.m_xMax * ratio);
        this.m_yMin = (int)((float)this.m_yMin * ratio);
        this.m_yMax = (int)((float)this.m_yMax * ratio);
    }

    public boolean isEmpty() {
        return this.m_xMin == this.m_xMax || this.m_yMin == this.m_yMax;
    }

    public String toString() {
        return "Rect (" + this.m_xMin + ", " + this.m_yMin + ", " + this.m_xMax + ", " + this.m_yMax + ")";
    }

    public void write(OutputBitStream stream) throws IOException {
        stream.writeSI32(this.m_xMin);
        stream.writeSI32(this.m_yMin);
        stream.writeSI32(this.m_xMax);
        stream.writeSI32(this.m_yMax);
    }
}

