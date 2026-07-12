package org.fenggui.table;

public interface ITableModel {
  String getColumnName(int paramInt);
  
  int getColumnCount();
  
  Object getValue(int paramInt1, int paramInt2);
  
  int getRowCount();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\ITableModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */