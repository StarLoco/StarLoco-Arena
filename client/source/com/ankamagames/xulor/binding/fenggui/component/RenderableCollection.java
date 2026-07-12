package com.ankamagames.xulor.binding.fenggui.component;

import com.ankamagames.xulor.template.IItemRenderable;
import com.ankamagames.xulor.util.Item;

public interface RenderableCollection {
  void removeItem(Item paramItem);
  
  void addItem(Item paramItem);
  
  boolean addItem(int paramInt, Item paramItem);
  
  void addItem(Item paramItem1, Item paramItem2);
  
  boolean replaceItem(Item paramItem1, Item paramItem2);
  
  int size();
  
  Item getItem(int paramInt);
  
  IItemRenderable getSelected();
  
  int getRenderableIndex(RenderableContainer paramRenderableContainer);
  
  int getItemIndex(Object paramObject);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\RenderableCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */