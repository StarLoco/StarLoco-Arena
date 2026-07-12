package org.xml.sax;

public interface DocumentHandler {
  void setDocumentLocator(Locator paramLocator);
  
  void startDocument() throws SAXException;
  
  void endDocument() throws SAXException;
  
  void startElement(String paramString, AttributeList paramAttributeList) throws SAXException;
  
  void endElement(String paramString) throws SAXException;
  
  void characters(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void ignorableWhitespace(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
  
  void processingInstruction(String paramString1, String paramString2) throws SAXException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\DocumentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */