/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.UTFDataFormatException;

/*
 * Renamed from aBy
 */
public class aby_1
extends anv {
    private final String drJ;

    public aby_1(String string) {
        if (string == null) {
            throw new aHY();
        }
        this.drJ = string;
    }

    public String getString() {
        return this.drJ;
    }

    public boolean isWide() {
        return false;
    }

    public void a(DataOutputStream dataOutputStream) {
        dataOutputStream.writeByte(1);
        try {
            dataOutputStream.writeUTF(this.drJ);
        }
        catch (UTFDataFormatException uTFDataFormatException) {
            throw new ClassFormatError("String constant too long to store in class file");
        }
    }

    public boolean equals(Object object) {
        return object instanceof aby_1 && ((aby_1)object).drJ.equals(this.drJ);
    }

    public int hashCode() {
        return this.drJ.hashCode();
    }

    static String a(aby_1 aby_12) {
        return aby_12.drJ;
    }
}

