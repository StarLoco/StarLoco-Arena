package com.ankamagames.xulor.template;

import com.ankamagames.xulor.property.Property;

public abstract interface IProperty
  extends IElement
{
  public abstract Property getProperty();
  
  public abstract void buildProperty();
  
  public abstract void addPropertyClient(IElement paramIElement);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */