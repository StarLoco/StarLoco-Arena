package org.fenggui.event;

import org.fenggui.IWidget;

public interface IDragAndDropListener {
  boolean isDndWidget(IWidget paramIWidget, int paramInt1, int paramInt2);
  
  void select(int paramInt1, int paramInt2);
  
  void drag(int paramInt1, int paramInt2);
  
  void drop(int paramInt1, int paramInt2, IWidget paramIWidget);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\event\IDragAndDropListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */