package org.xml.sax.ext;

import org.xml.sax.SAXException;

public interface LexicalHandler {
  void startDTD(String paramString1, String paramString2, String paramString3) throws SAXException;
  
  void endDTD() throws SAXException;
  
  void startEntity(String paramString) throws SAXException;
  
  void endEntity(String paramString) throws SAXException;
  
  void startCDATA() throws SAXException;
  
  void endCDATA() throws SAXException;
  
  void comment(char[] paramArrayOfchar, int paramInt1, int paramInt2) throws SAXException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\ext\LexicalHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */