/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.InputStream;

/*
 * Renamed from Ca
 */
public class ca_1
extends apm_0 {
    private final mk aKz;

    public ca_1(mk mk2, apm_0 apm_02) {
        super(apm_02);
        this.aKz = mk2;
        this.aYV();
    }

    protected asn dx(String string) {
        nw_2 nw_22;
        InputStream inputStream;
        String string2 = sA.toClassName(string);
        any_2 any_22 = this.aKz.aU(nw_2.fv(string2));
        if (any_22 == null) {
            return null;
        }
        try {
            inputStream = any_22.aBH();
        }
        catch (IOException iOException) {
            throw new ClassNotFoundException("Opening resource \"" + any_22.getFileName() + "\"", iOException);
        }
        try {
            nw_22 = new nw_2(inputStream);
        }
        catch (IOException iOException) {
            throw new ClassNotFoundException("Reading resource \"" + any_22.getFileName() + "\"", iOException);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException iOException) {}
        }
        yy_2 yy_22 = new yy_2(nw_22, this);
        this.l(yy_22);
        return yy_22;
    }
}

