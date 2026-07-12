package com.ankamagames.xulor.template;

import com.ankamagames.xulor.theme.ThemeElement;
import com.ankamagames.xulor.util.Propagation;

public abstract interface IComponent
  extends IElement
{
  public abstract ILayoutData getLayoutData();
  
  public abstract void setLayoutData(ILayoutData paramILayoutData);
  
  public abstract void setAddedToWidgetTree(boolean paramBoolean);
  
  public abstract boolean isAddedToWidgetTree();
  
  public abstract boolean isDisplayable();
  
  public abstract int getDisplayX();
  
  public abstract int getDisplayY();
  
  public abstract void setExpandable(boolean paramBoolean);
  
  public abstract boolean isExpandable();
  
  public abstract void setShrinkable(boolean paramBoolean);
  
  public abstract boolean isShrinkable();
  
  public abstract void setUsedInLayout(boolean paramBoolean);
  
  public abstract void setThemeElement(ThemeElement paramThemeElement);
  
  public abstract ThemeElement getThemeElement();
  
  public abstract void propagateStyle(String paramString);
  
  public abstract void setStyle(String paramString);
  
  public abstract void setStyle(String paramString, boolean paramBoolean);
  
  public abstract void setStylePropagation(Propagation paramPropagation);
  
  public abstract void setStylePropagation(Propagation paramPropagation, boolean paramBoolean);
  
  public abstract void applyTheme();
  
  public abstract void setDragAndDropParent(IDragNDropable paramIDragNDropable);
  
  public abstract IDragNDropable getDragAndDropParent();
  
  public abstract void moveTo(IElement paramIElement);
  
  public abstract boolean hasFocus();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */