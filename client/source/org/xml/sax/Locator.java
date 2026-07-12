package org.xml.sax;

public interface Locator {
  String getPublicId();
  
  String getSystemId();
  
  int getLineNumber();
  
  int getColumnNumber();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\Locator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */