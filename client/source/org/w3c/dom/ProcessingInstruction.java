package org.w3c.dom;

public interface ProcessingInstruction extends Node {
  String getTarget();
  
  String getData();
  
  void setData(String paramString) throws DOMException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\w3c\dom\ProcessingInstruction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */