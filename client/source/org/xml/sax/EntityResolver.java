package org.xml.sax;

import java.io.IOException;

public interface EntityResolver {
  InputSource resolveEntity(String paramString1, String paramString2) throws SAXException, IOException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\EntityResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */