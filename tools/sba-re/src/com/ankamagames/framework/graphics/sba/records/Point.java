/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;

public class Point {
    private int m_x;
    private int m_y;

    public Point(int x, int y) {
        this.m_x = x;
        this.m_y = y;
    }

    public Point(InputBitStream stream) throws IOException {
        this.m_x = stream.readSI32();
        this.m_y = stream.readSI32();
    }

    public void move(int dx, int dy) {
        this.m_x += dx;
        this.m_y += dy;
    }

    public void scale(float factor) {
        this.m_x = (int)((float)this.m_x * factor);
        this.m_y = (int)((float)this.m_y * factor);
    }

    public int getX() {
        return this.m_x;
    }

    public void setX(int x) {
        this.m_x = x;
    }

    public int getY() {
        return this.m_y;
    }

    public void setY(int y) {
        this.m_y = y;
    }

    public String toString() {
        return "Point (" + this.m_x + ", " + this.m_y + ")";
    }

    public void write(OutputBitStream stream) throws IOException {
        stream.writeSI32(this.m_x);
        stream.writeSI32(this.m_y);
    }
}

