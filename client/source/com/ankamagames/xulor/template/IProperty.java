package com.ankamagames.xulor.template;

import com.ankamagames.xulor.property.Property;

public interface IProperty extends IElement {
  Property getProperty();
  
  void buildProperty();
  
  void addPropertyClient(IElement paramIElement);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */