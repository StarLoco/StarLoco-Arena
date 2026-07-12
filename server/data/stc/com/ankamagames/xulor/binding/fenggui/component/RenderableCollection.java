package com.ankamagames.xulor.binding.fenggui.component;

import com.ankamagames.xulor.template.IItemRenderable;
import com.ankamagames.xulor.util.Item;

public abstract interface RenderableCollection
{
  public abstract void removeItem(Item paramItem);
  
  public abstract void addItem(Item paramItem);
  
  public abstract boolean addItem(int paramInt, Item paramItem);
  
  public abstract void addItem(Item paramItem1, Item paramItem2);
  
  public abstract boolean replaceItem(Item paramItem1, Item paramItem2);
  
  public abstract int size();
  
  public abstract Item getItem(int paramInt);
  
  public abstract IItemRenderable getSelected();
  
  public abstract int getRenderableIndex(RenderableContainer paramRenderableContainer);
  
  public abstract int getItemIndex(Object paramObject);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\RenderableCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */