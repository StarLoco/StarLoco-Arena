/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipFile;

/*
 * Renamed from gB
 */
class gb_2
extends abq_2 {
    private final hd_1 uc;

    gb_2(hd_1 hd_12, Iterator iterator) {
        super(iterator);
        this.uc = hd_12;
    }

    protected Object n(Object object) {
        File file = (File)object;
        try {
            return new ns_2(new ZipFile(file));
        }
        catch (IOException iOException) {
            return mk.Ji;
        }
    }
}

