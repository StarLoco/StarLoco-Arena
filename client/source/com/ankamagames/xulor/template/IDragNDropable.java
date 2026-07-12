package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.DropValidateCallBack;
import com.ankamagames.xulor.event.IDragListener;
import com.ankamagames.xulor.event.IDropListener;
import com.ankamagames.xulor.event.IDropOutListener;
import com.ankamagames.xulor.event.listener.DragOutListener;
import com.ankamagames.xulor.event.listener.DragOverListener;
import com.ankamagames.xulor.util.Item;

public interface IDragNDropable {
  boolean isCompatible(Item paramItem);
  
  void setRenderableParent(IItemRenderable paramIItemRenderable);
  
  IItemRenderable getRenderableParent();
  
  void setOnDrag(IDragListener paramIDragListener);
  
  void setOnDrop(IDropListener paramIDropListener);
  
  void setOnDropOut(IDropOutListener paramIDropOutListener);
  
  void setOnDragOut(DragOutListener paramDragOutListener);
  
  void setOnDragOver(DragOverListener paramDragOverListener);
  
  void setValidateDrop(DropValidateCallBack paramDropValidateCallBack);
  
  void fireDrag(Object paramObject);
  
  void fireDrop(IDragNDropable paramIDragNDropable, Object paramObject);
  
  void fireDropOut(Object paramObject);
  
  void fireDragOver(IDragNDropable paramIDragNDropable, Object paramObject);
  
  void fireDragOut(IDragNDropable paramIDragNDropable, Object paramObject);
  
  boolean isDropValid(IDragNDropable paramIDragNDropable, Object paramObject);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IDragNDropable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */