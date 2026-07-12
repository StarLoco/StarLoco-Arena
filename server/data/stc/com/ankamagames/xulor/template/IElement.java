package com.ankamagames.xulor.template;

import com.ankamagames.framework.preferences.PreferencePropertyChangeListener;
import com.ankamagames.xulor.core.ElementMap;
import com.ankamagames.xulor.property.Property;
import com.ankamagames.xulor.util.ElementAttributes;

public abstract interface IElement
  extends PreferencePropertyChangeListener
{
  public abstract void setId(String paramString);
  
  public abstract String getId();
  
  public abstract void setModalLevel(short paramShort);
  
  public abstract short getModalLevel();
  
  public abstract void setElementMap(ElementMap paramElementMap);
  
  public abstract ElementMap getElementMap();
  
  public abstract IElement[] getChildren();
  
  public abstract int getChildrenCount();
  
  public abstract void add(IElement paramIElement);
  
  public abstract IElement getChild(IElement paramIElement);
  
  public abstract void removeChild(IElement paramIElement);
  
  public abstract void removeSelfFromParent();
  
  public abstract void removeChildren();
  
  public abstract void addWidget(IElement paramIElement);
  
  public abstract Object getEncapsulatedObject();
  
  public abstract void setParent(IElement paramIElement);
  
  public abstract IElement getParent();
  
  public abstract void applyAllChildrenAttributes();
  
  public abstract void buildGUI();
  
  public abstract void buildXML();
  
  public abstract void layout();
  
  public abstract Object getElementValue();
  
  public abstract void setRenderableParent(IItemRenderable paramIItemRenderable);
  
  public abstract IItemRenderable getRenderableParent();
  
  public abstract void applyAllAttributes();
  
  public abstract IElement cloneElementStructure();
  
  public abstract String getTag();
  
  public abstract void addProperty(Property paramProperty);
  
  public abstract void propagateStyle(String paramString);
  
  public abstract void setElementAttributes(ElementAttributes paramElementAttributes);
  
  public abstract ElementAttributes getElementAttributes();
  
  public abstract void loadPreferences();
  
  public abstract void storePreferences();
  
  public abstract void setStatic(boolean paramBoolean);
  
  public abstract boolean getStatic();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */