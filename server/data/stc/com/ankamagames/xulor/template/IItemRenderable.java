package com.ankamagames.xulor.template;

import com.ankamagames.xulor.binding.fenggui.component.ItemRenderer;
import com.ankamagames.xulor.binding.fenggui.component.ItemRendererManager;
import com.ankamagames.xulor.binding.fenggui.component.RenderableCollection;
import com.ankamagames.xulor.util.Item;

public abstract interface IItemRenderable
  extends ISelection
{
  public abstract void setRenderer(ItemRenderer paramItemRenderer);
  
  public abstract ItemRenderer getRenderer();
  
  public abstract void setRendererManager(ItemRendererManager paramItemRendererManager);
  
  public abstract ItemRendererManager getRendererManager();
  
  public abstract void updateRenderer(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract RenderableCollection getRenderableCollection();
  
  public abstract void setItem(Item paramItem);
  
  public abstract void setItem(Item paramItem, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void applyItem();
  
  public abstract Item getItem();
  
  public abstract void render();
  
  public abstract void setItemValue(Object paramObject);
  
  public abstract Object getItemValue();
  
  public abstract void setDragNDropable(IDragNDropable paramIDragNDropable);
  
  public abstract IDragNDropable getDragNDropable();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IItemRenderable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */