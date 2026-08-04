/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.RenderTreeStencil;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/*
 * Renamed from kn
 */
public class kn_0 {
    private JPanel qS;
    private JTree DX;
    private DefaultMutableTreeNode DY;

    public kn_0() {
        this.oP();
    }

    public void a(RenderTreeStencil renderTreeStencil) {
        this.DY.removeAllChildren();
        DefaultMutableTreeNode defaultMutableTreeNode = this.DY;
        for (RenderTreeStencil renderTreeStencil2 = renderTreeStencil; renderTreeStencil2 != null; renderTreeStencil2 = renderTreeStencil2.avw()) {
            Entity entity = renderTreeStencil2.getEntity();
            ArrayList arrayList = renderTreeStencil2.avA();
            ArrayList arrayList2 = renderTreeStencil2.avz();
            ArrayList arrayList3 = renderTreeStencil2.avy();
            String string = entity.getClass().getSimpleName();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(string).append(" [ ");
            stringBuffer.append("radius=").append(entity.cpB);
            stringBuffer.append(", afterCount=").append(arrayList.size());
            stringBuffer.append(", maskCount=").append(arrayList2.size());
            stringBuffer.append(", overCount=").append(arrayList3.size());
            stringBuffer.append(" ]");
            DefaultMutableTreeNode defaultMutableTreeNode2 = new DefaultMutableTreeNode(stringBuffer.toString());
            defaultMutableTreeNode.add(defaultMutableTreeNode2);
            defaultMutableTreeNode = defaultMutableTreeNode2;
        }
        this.a(this.DX, true);
        this.DX.repaint();
    }

    private void oP() {
        this.qS = new JPanel();
        this.qS.setLayout(new BorderLayout(0, 0));
        JScrollPane jScrollPane = new JScrollPane();
        this.qS.add((Component)jScrollPane, "Center");
        this.DY = new DefaultMutableTreeNode("Root");
        this.DX = new JTree(this.DY);
        jScrollPane.setViewportView(this.DX);
    }

    public void a(JTree jTree, boolean bl2) {
        TreeNode treeNode = (TreeNode)jTree.getModel().getRoot();
        this.a(jTree, new TreePath(treeNode), bl2);
    }

    private void a(JTree jTree, TreePath treePath, boolean bl2) {
        TreeNode treeNode = (TreeNode)treePath.getLastPathComponent();
        if (treeNode.getChildCount() >= 0) {
            Enumeration enumeration = treeNode.children();
            while (enumeration.hasMoreElements()) {
                TreeNode treeNode2 = (TreeNode)enumeration.nextElement();
                TreePath treePath2 = treePath.pathByAddingChild(treeNode2);
                this.a(jTree, treePath2, bl2);
            }
        }
        if (bl2) {
            jTree.expandPath(treePath);
        } else {
            jTree.collapsePath(treePath);
        }
    }

    public JPanel iA() {
        return this.qS;
    }
}

