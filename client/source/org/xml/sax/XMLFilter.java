package org.xml.sax;

public interface XMLFilter extends XMLReader {
  void setParent(XMLReader paramXMLReader);
  
  XMLReader getParent();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\XMLFilter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */