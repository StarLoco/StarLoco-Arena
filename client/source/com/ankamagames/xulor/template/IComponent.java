package com.ankamagames.xulor.template;

import com.ankamagames.xulor.theme.ThemeElement;
import com.ankamagames.xulor.util.Propagation;

public interface IComponent extends IElement {
  ILayoutData getLayoutData();
  
  void setLayoutData(ILayoutData paramILayoutData);
  
  void setAddedToWidgetTree(boolean paramBoolean);
  
  boolean isAddedToWidgetTree();
  
  boolean isDisplayable();
  
  int getDisplayX();
  
  int getDisplayY();
  
  void setExpandable(boolean paramBoolean);
  
  boolean isExpandable();
  
  void setShrinkable(boolean paramBoolean);
  
  boolean isShrinkable();
  
  void setUsedInLayout(boolean paramBoolean);
  
  void setThemeElement(ThemeElement paramThemeElement);
  
  ThemeElement getThemeElement();
  
  void propagateStyle(String paramString);
  
  void setStyle(String paramString);
  
  void setStyle(String paramString, boolean paramBoolean);
  
  void setStylePropagation(Propagation paramPropagation);
  
  void setStylePropagation(Propagation paramPropagation, boolean paramBoolean);
  
  void applyTheme();
  
  void setDragAndDropParent(IDragNDropable paramIDragNDropable);
  
  IDragNDropable getDragAndDropParent();
  
  void moveTo(IElement paramIElement);
  
  boolean hasFocus();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IComponent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */