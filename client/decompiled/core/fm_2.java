/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.particleSystem.Emitter;
import com.ankamagames.framework.graphics.engine.particleSystem.ParticleSystem;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Renamed from fm
 */
class fm_2 {
    private JPanel qS;
    private JTable qT;
    private JTable qU;

    fm_2() {
        this.iB();
    }

    public JPanel iA() {
        return this.qS;
    }

    private void a(ArrayList arrayList, ArrayList arrayList2) {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Id");
        defaultTableModel.addColumn("Class");
        defaultTableModel.addColumn("isAlive");
        defaultTableModel.addColumn("emitters");
        defaultTableModel.addColumn("pos");
        for (Object object : arrayList) {
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList arrayList3 = ((ParticleSystem)object).alY();
            if (arrayList3 != null) {
                int n2 = arrayList3.size();
                stringBuffer.append("cnt=").append(n2).append(" {");
                for (int j = 0; j < n2; ++j) {
                    if (j > 0) {
                        stringBuffer.append(",");
                    }
                    Emitter emitter = (Emitter)arrayList3.get(j);
                    stringBuffer.append(emitter.isAlive());
                }
                stringBuffer.append("}");
            } else {
                stringBuffer.append("cnt=0");
            }
            defaultTableModel.addRow(new Object[]{((ParticleSystem)object).getId(), object.getClass().getSimpleName(), ((ParticleSystem)object).isAlive(), stringBuffer, ((ParticleSystem)object).getX() + ";" + ((ParticleSystem)object).getY()});
        }
        this.qT.setModel(defaultTableModel);
        defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Id");
        defaultTableModel.addColumn("Pos");
        defaultTableModel.addColumn("Enabled");
        defaultTableModel.addColumn("BaseColor");
        defaultTableModel.addColumn("Range");
        for (Object object : arrayList2) {
            defaultTableModel.addRow(new Object[]{((aNH)object).getId(), ((aNH)object).qG().getX() + ";" + ((aNH)object).qG().getY(), ((aNH)object).isEnabled(), ((aNH)object).aXI(), Float.valueOf(((aNH)object).aXH())});
        }
        this.qU.setModel(defaultTableModel);
    }

    public void b(ArrayList arrayList, ArrayList arrayList2) {
        this.a(arrayList, arrayList2);
    }

    private /* synthetic */ void iB() {
        JTable jTable;
        JTable jTable2;
        JPanel jPanel;
        this.qS = jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout(0, 0));
        JScrollPane jScrollPane = new JScrollPane();
        jPanel.add((Component)jScrollPane, "Center");
        this.qU = jTable2 = new JTable();
        jScrollPane.setViewportView(jTable2);
        JScrollPane jScrollPane2 = new JScrollPane();
        jPanel.add((Component)jScrollPane2, "East");
        this.qT = jTable = new JTable();
        jScrollPane2.setViewportView(jTable);
    }

    public /* synthetic */ JComponent iC() {
        return this.qS;
    }
}

