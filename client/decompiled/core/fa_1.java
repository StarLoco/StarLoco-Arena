/*
 * Decompiled with CFR 0.152.
 */
import java.util.Properties;

/*
 * Renamed from fa
 */
public class fa_1
extends aNk {
    private Properties qa = new Properties();

    public fa_1() {
        this.qa.put("identity", "org.apache.tools.ant.util.IdentityMapper");
        this.qa.put("flatten", "org.apache.tools.ant.util.FlatFileNameMapper");
        this.qa.put("glob", "org.apache.tools.ant.util.GlobPatternMapper");
        this.qa.put("merge", "org.apache.tools.ant.util.MergingMapper");
        this.qa.put("regexp", "org.apache.tools.ant.util.RegexpPatternMapper");
        this.qa.put("package", "org.apache.tools.ant.util.PackageNameMapper");
        this.qa.put("unpackage", "org.apache.tools.ant.util.UnPackageNameMapper");
    }

    public String[] getValues() {
        return new String[]{"identity", "flatten", "glob", "merge", "regexp", "package", "unpackage"};
    }

    public String hZ() {
        return this.qa.getProperty(this.getValue());
    }
}

