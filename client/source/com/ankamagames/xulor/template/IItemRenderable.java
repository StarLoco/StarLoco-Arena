package com.ankamagames.xulor.template;

import com.ankamagames.xulor.binding.fenggui.component.ItemRenderer;
import com.ankamagames.xulor.binding.fenggui.component.ItemRendererManager;
import com.ankamagames.xulor.binding.fenggui.component.RenderableCollection;
import com.ankamagames.xulor.util.Item;

public interface IItemRenderable extends ISelection {
  void setRenderer(ItemRenderer paramItemRenderer);
  
  ItemRenderer getRenderer();
  
  void setRendererManager(ItemRendererManager paramItemRendererManager);
  
  ItemRendererManager getRendererManager();
  
  void updateRenderer(boolean paramBoolean1, boolean paramBoolean2);
  
  RenderableCollection getRenderableCollection();
  
  void setItem(Item paramItem);
  
  void setItem(Item paramItem, boolean paramBoolean1, boolean paramBoolean2);
  
  void applyItem();
  
  Item getItem();
  
  void render();
  
  void setItemValue(Object paramObject);
  
  Object getItemValue();
  
  void setDragNDropable(IDragNDropable paramIDragNDropable);
  
  IDragNDropable getDragNDropable();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IItemRenderable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */