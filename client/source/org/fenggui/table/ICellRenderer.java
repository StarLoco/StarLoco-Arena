package org.fenggui.table;

import org.fenggui.render.Graphics;
import org.fenggui.util.Dimension;

public interface ICellRenderer {
  void paint(Graphics paramGraphics, Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  Dimension getCellContentSize(Object paramObject);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\table\ICellRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */