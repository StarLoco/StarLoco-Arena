/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.framework.graphics.sba;

import com.ankamagames.framework.graphics.sba.IndexedDefinitionTagBuffer;
import com.ankamagames.framework.kernel.core.common.collections.ByteArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.Logger;

public class IndexedDefinitionTagBufferManager {
    public static final int BUFFER_SIZE = 0x200000;
    protected static final Logger m_logger = Logger.getLogger(IndexedDefinitionTagBufferManager.class);
    private static final IndexedDefinitionTagBufferManager m_instance = new IndexedDefinitionTagBufferManager();

    private IndexedDefinitionTagBufferManager() {
    }

    public static IndexedDefinitionTagBufferManager getInstance() {
        return m_instance;
    }

    public IndexedDefinitionTagBuffer getIndexedBuffer(String fileName) {
        InputStream stream = null;
        try {
            URL jarUrl = new URL(fileName);
            stream = jarUrl.openStream();
        }
        catch (Exception e) {
            File file = new File(fileName);
            try {
                stream = new FileInputStream(file);
            }
            catch (FileNotFoundException e1) {
                m_logger.error((Object)("Fichier " + fileName + " introuvable ou illisible"));
                return null;
            }
        }
        try {
            return this.getIndexedBuffer(stream);
        }
        catch (Exception e) {
            System.err.println("Erreur avec le fichier " + fileName);
            e.printStackTrace();
            return null;
        }
    }

    public IndexedDefinitionTagBuffer getIndexedBuffer(File file) throws Exception {
        return this.getIndexedBuffer(new FileInputStream(file));
    }

    public IndexedDefinitionTagBuffer getIndexedBuffer(InputStream stream) throws Exception {
        ByteArray array = new ByteArray(0x200000, 0x200000);
        byte[] buffer = new byte[0x200000];
        int bytesRead = 0;
        while (bytesRead != -1) {
            bytesRead = stream.read(buffer, 0, buffer.length);
            if (bytesRead <= 0) continue;
            array.put(buffer, bytesRead);
        }
        return this.getIndexedBuffer(array.internalArray());
    }

    public IndexedDefinitionTagBuffer getIndexedBuffer(byte[] buffer) throws Exception {
        IndexedDefinitionTagBuffer indexedBuffer = new IndexedDefinitionTagBuffer(buffer);
        return indexedBuffer;
    }
}

