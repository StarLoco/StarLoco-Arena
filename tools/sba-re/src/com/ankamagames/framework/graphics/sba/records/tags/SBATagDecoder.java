/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.framework.graphics.sba.records.tags;

import com.ankamagames.framework.fileFormat.tag.records.tags.EndTag;
import com.ankamagames.framework.fileFormat.tag.records.tags.Tag;
import com.ankamagames.framework.fileFormat.tag.records.tags.TagDecoder;
import com.ankamagames.framework.graphics.sba.records.tags.ActionFlag;
import com.ankamagames.framework.graphics.sba.records.tags.DefineBitmap;
import com.ankamagames.framework.graphics.sba.records.tags.DefineBitmapSequence;
import com.ankamagames.framework.graphics.sba.records.tags.DefineMovieClip;
import com.ankamagames.framework.graphics.sba.records.tags.PlaceObject;
import com.ankamagames.framework.graphics.sba.records.tags.RemoveObject;
import com.ankamagames.framework.graphics.sba.records.tags.ShowFrame;

public class SBATagDecoder
implements TagDecoder {
    private static SBATagDecoder m_instance = new SBATagDecoder();

    public static SBATagDecoder getInstance() {
        return m_instance;
    }

    public Tag creatTagInstanceFromCode(short code) {
        Tag tag = null;
        switch (code) {
            case 1: {
                tag = new ShowFrame();
                break;
            }
            case 2: {
                tag = new DefineBitmap();
                break;
            }
            case 3: {
                tag = new DefineBitmapSequence();
                break;
            }
            case 4: {
                tag = new DefineMovieClip();
                break;
            }
            case 5: {
                tag = new PlaceObject();
                break;
            }
            case 6: {
                tag = new RemoveObject();
                break;
            }
            case 7: {
                tag = new ActionFlag();
                break;
            }
            case 0: {
                tag = EndTag.getInstance();
            }
        }
        return tag;
    }
}

