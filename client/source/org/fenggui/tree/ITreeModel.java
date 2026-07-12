package org.fenggui.tree;

import org.fenggui.render.Pixmap;

public interface ITreeModel<E> {
  int getNumberOfChildren(E paramE);
  
  Pixmap getPixmap(E paramE);
  
  E getNode(E paramE, int paramInt);
  
  String getText(E paramE);
  
  E getRoot();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\tree\ITreeModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */