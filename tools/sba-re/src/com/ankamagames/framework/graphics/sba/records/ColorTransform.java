/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;

public class ColorTransform {
    private int m_redMultTerm = 256;
    private int m_greenMultTerm = 256;
    private int m_blueMultTerm = 256;
    private int m_alphaMultTerm = 256;
    private int m_redAddTerm = 0;
    private int m_greenAddTerm = 0;
    private int m_blueAddTerm = 0;
    private int m_alphaAddTerm = 0;
    private boolean m_hasMultTerms;
    private boolean m_hasAddTerms;

    public ColorTransform(boolean hasMultTerm, boolean hasAddTerm) {
        this.m_hasMultTerms = hasMultTerm;
        this.m_hasAddTerms = hasAddTerm;
    }

    public ColorTransform(InputBitStream stream) throws IOException {
        this.m_hasAddTerms = stream.readBooleanBit();
        this.m_hasMultTerms = stream.readBooleanBit();
        int nBits = (int)stream.readUnsignedBits(4);
        if (this.m_hasMultTerms) {
            this.m_redMultTerm = (int)stream.readSignedBits(nBits);
            this.m_greenMultTerm = (int)stream.readSignedBits(nBits);
            this.m_blueMultTerm = (int)stream.readSignedBits(nBits);
            this.m_alphaMultTerm = (int)stream.readSignedBits(nBits);
        }
        if (this.m_hasAddTerms) {
            this.m_redAddTerm = (int)stream.readSignedBits(nBits);
            this.m_greenAddTerm = (int)stream.readSignedBits(nBits);
            this.m_blueAddTerm = (int)stream.readSignedBits(nBits);
            this.m_alphaAddTerm = (int)stream.readSignedBits(nBits);
        }
        stream.align();
    }

    public void setAddTerms(int redAddTerm, int greenAddTerm, int blueAddTerm, int alphaAddTerm) {
        if (redAddTerm == 0 && greenAddTerm == 0 && blueAddTerm == 0 && alphaAddTerm == 0) {
            this.m_hasAddTerms = false;
        } else {
            this.m_redAddTerm = redAddTerm;
            this.m_greenAddTerm = greenAddTerm;
            this.m_blueAddTerm = blueAddTerm;
            this.m_alphaAddTerm = alphaAddTerm;
            this.m_hasAddTerms = true;
        }
    }

    public void setMultTerms(int redMultTerm, int greenMultTerm, int blueMultTerm, int alphaMultTerm) {
        if (redMultTerm == 256 && greenMultTerm == 256 && blueMultTerm == 256 && alphaMultTerm == 256) {
            this.m_hasMultTerms = false;
        } else {
            this.m_redMultTerm = redMultTerm;
            this.m_greenMultTerm = greenMultTerm;
            this.m_blueMultTerm = blueMultTerm;
            this.m_alphaMultTerm = alphaMultTerm;
            this.m_hasMultTerms = true;
        }
    }

    public int getAlphaAddTerm() {
        return this.m_alphaAddTerm;
    }

    public int getAlphaMultTerm() {
        return this.m_alphaMultTerm;
    }

    public int getBlueAddTerm() {
        return this.m_blueAddTerm;
    }

    public int getBlueMultTerm() {
        return this.m_blueMultTerm;
    }

    public int getGreenAddTerm() {
        return this.m_greenAddTerm;
    }

    public int getGreenMultTerm() {
        return this.m_greenMultTerm;
    }

    public int getRedAddTerm() {
        return this.m_redAddTerm;
    }

    public int getRedMultTerm() {
        return this.m_redMultTerm;
    }

    public boolean hasAddTerms() {
        return this.m_hasAddTerms;
    }

    public boolean hasMultTerms() {
        return this.m_hasMultTerms;
    }

    public String toString() {
        String mult = "no multiplication transformation";
        String add = "no addition transformation";
        if (this.hasMultTerms()) {
            mult = String.format("redMultTerm=%d greenMultTerm=%d blueMultTerm=%d alphaMultTerm=%d ", this.m_redMultTerm, this.m_greenMultTerm, this.m_blueMultTerm, this.m_alphaMultTerm);
        }
        if (this.hasAddTerms()) {
            add = String.format("redAddTerm=%d greenAddTerm=%d blueAddTerm=%d alphaAddTerm=%d", this.m_redAddTerm, this.m_greenAddTerm, this.m_blueAddTerm, this.m_alphaAddTerm);
        }
        return String.format("ColorTransform( %s  ;  %s)", mult, add);
    }

    public void write(OutputBitStream stream) throws IOException {
        stream.writeBooleanBit(this.m_hasAddTerms);
        stream.writeBooleanBit(this.m_hasMultTerms);
        int nBits = 0;
        if (this.m_hasAddTerms) {
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_redAddTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_greenAddTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_blueAddTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_alphaAddTerm));
        }
        if (this.m_hasMultTerms) {
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_redMultTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_greenMultTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_blueMultTerm));
            nBits = Math.max(nBits, OutputBitStream.getSignedBitsLength(this.m_alphaMultTerm));
        }
        stream.writeUnsignedBits(nBits, 4);
        if (this.m_hasMultTerms) {
            stream.writeSignedBits(this.m_redMultTerm, nBits);
            stream.writeSignedBits(this.m_greenMultTerm, nBits);
            stream.writeSignedBits(this.m_blueMultTerm, nBits);
            stream.writeSignedBits(this.m_alphaMultTerm, nBits);
        }
        if (this.m_hasAddTerms) {
            stream.writeSignedBits(this.m_redAddTerm, nBits);
            stream.writeSignedBits(this.m_greenAddTerm, nBits);
            stream.writeSignedBits(this.m_blueAddTerm, nBits);
            stream.writeSignedBits(this.m_alphaAddTerm, nBits);
        }
        stream.align();
    }

    public boolean equals(ColorTransform color) {
        return color.getRedAddTerm() == this.m_redAddTerm && color.getGreenAddTerm() == this.m_greenAddTerm && color.getBlueAddTerm() == this.m_blueAddTerm && color.getAlphaAddTerm() == this.m_alphaAddTerm && color.getRedMultTerm() == this.m_redMultTerm && color.getGreenMultTerm() == this.m_greenMultTerm && color.getBlueMultTerm() == this.m_blueMultTerm && color.getAlphaMultTerm() == this.m_alphaMultTerm;
    }
}

