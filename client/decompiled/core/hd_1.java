/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/*
 * Renamed from HD
 */
class hd_1
extends abq_2 {
    hd_1(Iterator iterator) {
        super(iterator);
    }

    protected Object n(Object object) {
        File file = (File)object;
        if (!file.exists()) {
            return Collections.EMPTY_LIST.iterator();
        }
        File[] fileArray = file.listFiles(new ga_1(this));
        return new gb_2(this, Arrays.asList(fileArray).iterator());
    }
}

