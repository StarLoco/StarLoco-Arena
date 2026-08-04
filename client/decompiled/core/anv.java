/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class anv {
    public abstract void a(DataOutputStream var1);

    public abstract boolean isWide();

    private static anv k(DataInputStream dataInputStream) {
        byte by = dataInputStream.readByte();
        switch (by) {
            case 7: {
                return new acp_2(dataInputStream.readShort());
            }
            case 9: {
                return new cx_2(dataInputStream.readShort(), dataInputStream.readShort());
            }
            case 10: {
                return new ng_1(dataInputStream.readShort(), dataInputStream.readShort());
            }
            case 11: {
                return new dz_1(dataInputStream.readShort(), dataInputStream.readShort());
            }
            case 8: {
                return new aom_0(dataInputStream.readShort());
            }
            case 3: {
                return new aok(dataInputStream.readInt());
            }
            case 4: {
                return new ajg_1(dataInputStream.readFloat());
            }
            case 5: {
                return new ahz_2(dataInputStream.readLong());
            }
            case 6: {
                return new si(dataInputStream.readDouble());
            }
            case 12: {
                return new ow_0(dataInputStream.readShort(), dataInputStream.readShort());
            }
            case 1: {
                return new aby_1(dataInputStream.readUTF());
            }
        }
        throw new ClassFormatError("Invalid constant pool tag " + by);
    }

    static anv l(DataInputStream dataInputStream) {
        return anv.k(dataInputStream);
    }
}

