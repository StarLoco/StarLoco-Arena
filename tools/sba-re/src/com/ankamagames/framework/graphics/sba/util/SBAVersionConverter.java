/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.util;

import com.ankamagames.framework.fileFormat.tag.TagDocumentWriter;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.graphics.sba.SBADocument;
import com.ankamagames.framework.graphics.sba.SBADocumentManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class SBAVersionConverter {
    public static byte[] convert(byte[] oldData) {
        byte[] data = null;
        ByteArrayInputStream istream = new ByteArrayInputStream(oldData);
        try {
            SBADocument oldDoc = SBADocumentManager.getInstance().getDocument(istream);
            short oldVersion = oldDoc.getVersion();
            if (oldDoc.isReadable(oldVersion)) {
                if (oldVersion == 3) {
                    System.out.println("Pas de conversion necessaire");
                    data = oldData;
                } else {
                    SBADocument newDoc = new SBADocument();
                    newDoc.setCompressed(oldDoc.isCompressed());
                    ArrayList<Tag> tags = oldDoc.getTags();
                    for (Tag tag : tags) {
                        newDoc.addTag(tag);
                    }
                    ByteArrayOutputStream ostream = new ByteArrayOutputStream(oldData.length);
                    TagDocumentWriter writer = new TagDocumentWriter(newDoc, ostream);
                    writer.write();
                    data = ostream.toByteArray();
                }
            } else {
                System.err.println("Le num\u00e9ro de version est incorrect " + SBAVersionConverter.getError(oldVersion));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    private static String getError(short version) {
        return String.valueOf(version) + "(courante= " + 3 + ")";
    }
}

