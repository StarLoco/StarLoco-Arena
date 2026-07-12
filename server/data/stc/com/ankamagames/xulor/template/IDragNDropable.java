package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.DropValidateCallBack;
import com.ankamagames.xulor.event.IDragListener;
import com.ankamagames.xulor.event.IDropListener;
import com.ankamagames.xulor.event.IDropOutListener;
import com.ankamagames.xulor.event.listener.DragOutListener;
import com.ankamagames.xulor.event.listener.DragOverListener;
import com.ankamagames.xulor.util.Item;

public abstract interface IDragNDropable
{
  public abstract boolean isCompatible(Item paramItem);
  
  public abstract void setRenderableParent(IItemRenderable paramIItemRenderable);
  
  public abstract IItemRenderable getRenderableParent();
  
  public abstract void setOnDrag(IDragListener paramIDragListener);
  
  public abstract void setOnDrop(IDropListener paramIDropListener);
  
  public abstract void setOnDropOut(IDropOutListener paramIDropOutListener);
  
  public abstract void setOnDragOut(DragOutListener paramDragOutListener);
  
  public abstract void setOnDragOver(DragOverListener paramDragOverListener);
  
  public abstract void setValidateDrop(DropValidateCallBack paramDropValidateCallBack);
  
  public abstract void fireDrag(Object paramObject);
  
  public abstract void fireDrop(IDragNDropable paramIDragNDropable, Object paramObject);
  
  public abstract void fireDropOut(Object paramObject);
  
  public abstract void fireDragOver(IDragNDropable paramIDragNDropable, Object paramObject);
  
  public abstract void fireDragOut(IDragNDropable paramIDragNDropable, Object paramObject);
  
  public abstract boolean isDropValid(IDragNDropable paramIDragNDropable, Object paramObject);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IDragNDropable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */