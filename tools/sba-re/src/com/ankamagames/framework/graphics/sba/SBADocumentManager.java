/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba;

import com.ankamagames.framework.fileFormat.tag.TagDocument;
import com.ankamagames.framework.fileFormat.tag.TagDocumentFactory;
import com.ankamagames.framework.fileFormat.tag.TagDocumentReader;
import com.ankamagames.framework.graphics.sba.SBADocument;
import com.ankamagames.framework.graphics.sba.records.tags.SBATagDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SBADocumentManager {
    private static TagDocumentFactory m_documentFactory = new TagDocumentFactory(){

        public TagDocument createDocument() {
            return new SBADocument();
        }
    };
    private static final SBADocumentManager m_instance = new SBADocumentManager();
    private TagDocumentReader m_reader = new TagDocumentReader(null, SBATagDecoder.getInstance(), m_documentFactory);

    private SBADocumentManager() {
    }

    public static SBADocumentManager getInstance() {
        return m_instance;
    }

    public SBADocument getDocument(String fileName) throws Exception {
        return this.getDocument(new File(fileName));
    }

    public SBADocument getDocument(File file) throws Exception {
        SBADocument doc = this.getDocument(new FileInputStream(file));
        if (doc.getVersion() != 3) {
            System.out.println("Le fichier " + file.getName() + " n'est pas a jour (version: " + doc.getVersion() + " courante: " + 2);
        }
        return doc;
    }

    public SBADocument getDocument(InputStream istream) throws Exception {
        this.m_reader.create(istream, SBATagDecoder.getInstance(), m_documentFactory);
        this.m_reader.read();
        return (SBADocument)this.m_reader.getDocument();
    }
}

