/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.tag.records.tags.EndTag;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
import com.ankamagames.framework.graphics.sba.records.tags.CommonDefineTag;

public class SBADefinitionTagDecoder
implements TagDecoder {
    public Tag creatTagInstanceFromCode(short code) {
        Tag tag = null;
        switch (code) {
            case 2: 
            case 3: 
            case 4: {
                tag = new CommonDefineTag();
                break;
            }
            case 0: {
                tag = EndTag.getInstance();
            }
        }
        return tag;
    }
}

