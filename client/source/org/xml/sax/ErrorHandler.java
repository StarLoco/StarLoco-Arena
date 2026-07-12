package org.xml.sax;

public interface ErrorHandler {
  void warning(SAXParseException paramSAXParseException) throws SAXException;
  
  void error(SAXParseException paramSAXParseException) throws SAXException;
  
  void fatalError(SAXParseException paramSAXParseException) throws SAXException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\ErrorHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */