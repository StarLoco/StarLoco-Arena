/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.fileFormat.tag.records;

import com.ankamagames.framework.fileFormat.io.InputBitStream;
import com.ankamagames.framework.fileFormat.io.OutputBitStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class TagDocumentHeader {
    public static final int HEADER_LENGTH = 8;
    private String m_uncompressedSignature = "TAG";
    private String m_compressedSignature = "tag";
    private short m_version;
    private long m_fileLength;
    private boolean m_compressed;

    public void setSignature(String signature) {
        if (signature == null) {
            System.err.println("Signature inexistante = null");
            return;
        }
        if (!Pattern.matches("[a-zA-Z]{3}", signature)) {
            System.err.println("Signature '" + signature + "'invalide. Seules les signatures de trois lettres sont autoris\u00e9es");
        }
        this.m_uncompressedSignature = signature.toLowerCase();
        this.m_compressedSignature = signature.toUpperCase();
    }

    public void reset() {
        this.m_version = 1;
        this.m_fileLength = 0L;
        this.m_compressed = true;
    }

    public boolean isCompressed() {
        return this.m_compressed;
    }

    public void setCompressed(boolean compressed) {
        this.m_compressed = compressed;
    }

    public long getFileLength() {
        return this.m_fileLength;
    }

    public void setFileLength(long fileLength) {
        this.m_fileLength = fileLength;
    }

    public short getVersion() {
        return this.m_version;
    }

    public void setVersion(short version) {
        this.m_version = version;
    }

    public void read(InputBitStream stream) throws IOException {
        String signature = new String(stream.readBytes(3));
        if (signature.equals(this.m_uncompressedSignature)) {
            this.m_compressed = false;
        } else if (signature.equals(this.m_compressedSignature)) {
            this.m_compressed = true;
        } else {
            throw new IOException("La signature '" + signature + "' du document est invalide!");
        }
        this.m_version = (byte)stream.readUI8();
        this.m_fileLength = stream.readUI32();
    }

    public void write(OutputBitStream outStream) throws IOException {
        if (this.isCompressed()) {
            outStream.writeBytes(this.m_compressedSignature.getBytes());
        } else {
            outStream.writeBytes(this.m_uncompressedSignature.getBytes());
        }
        outStream.writeUI8(this.m_version);
        outStream.writeUI32(this.m_fileLength);
    }
}

