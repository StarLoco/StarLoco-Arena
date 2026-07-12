package org.w3c.dom;

public interface Attr extends Node {
  String getName();
  
  boolean getSpecified();
  
  String getValue();
  
  void setValue(String paramString) throws DOMException;
  
  Element getOwnerElement();
  
  TypeInfo getSchemaTypeInfo();
  
  boolean isId();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\w3c\dom\Attr.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */