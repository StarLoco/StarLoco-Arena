package org.xml.sax;

import java.io.IOException;
import java.util.Locale;

public interface Parser {
  void setLocale(Locale paramLocale) throws SAXException;
  
  void setEntityResolver(EntityResolver paramEntityResolver);
  
  void setDTDHandler(DTDHandler paramDTDHandler);
  
  void setDocumentHandler(DocumentHandler paramDocumentHandler);
  
  void setErrorHandler(ErrorHandler paramErrorHandler);
  
  void parse(InputSource paramInputSource) throws SAXException, IOException;
  
  void parse(String paramString) throws SAXException, IOException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\Parser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */