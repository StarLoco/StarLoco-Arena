package com.ankamagames.xulor.template;

import com.ankamagames.framework.preferences.PreferencePropertyChangeListener;
import com.ankamagames.xulor.core.ElementMap;
import com.ankamagames.xulor.property.Property;
import com.ankamagames.xulor.util.ElementAttributes;

public interface IElement extends PreferencePropertyChangeListener {
  void setId(String paramString);
  
  String getId();
  
  void setModalLevel(short paramShort);
  
  short getModalLevel();
  
  void setElementMap(ElementMap paramElementMap);
  
  ElementMap getElementMap();
  
  IElement[] getChildren();
  
  int getChildrenCount();
  
  void add(IElement paramIElement);
  
  IElement getChild(IElement paramIElement);
  
  void removeChild(IElement paramIElement);
  
  void removeSelfFromParent();
  
  void removeChildren();
  
  void addWidget(IElement paramIElement);
  
  Object getEncapsulatedObject();
  
  void setParent(IElement paramIElement);
  
  IElement getParent();
  
  void applyAllChildrenAttributes();
  
  void buildGUI();
  
  void buildXML();
  
  void layout();
  
  Object getElementValue();
  
  void setRenderableParent(IItemRenderable paramIItemRenderable);
  
  IItemRenderable getRenderableParent();
  
  void applyAllAttributes();
  
  IElement cloneElementStructure();
  
  String getTag();
  
  void addProperty(Property paramProperty);
  
  void propagateStyle(String paramString);
  
  void setElementAttributes(ElementAttributes paramElementAttributes);
  
  ElementAttributes getElementAttributes();
  
  void loadPreferences();
  
  void storePreferences();
  
  void setStatic(boolean paramBoolean);
  
  boolean getStatic();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */