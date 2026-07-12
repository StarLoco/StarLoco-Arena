package org.w3c.dom;

public interface DocumentType extends Node {
  String getName();
  
  NamedNodeMap getEntities();
  
  NamedNodeMap getNotations();
  
  String getPublicId();
  
  String getSystemId();
  
  String getInternalSubset();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\w3c\dom\DocumentType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */