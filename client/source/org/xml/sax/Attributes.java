package org.xml.sax;

public interface Attributes {
  int getLength();
  
  String getURI(int paramInt);
  
  String getLocalName(int paramInt);
  
  String getQName(int paramInt);
  
  String getType(int paramInt);
  
  String getValue(int paramInt);
  
  int getIndex(String paramString1, String paramString2);
  
  int getIndex(String paramString);
  
  String getType(String paramString1, String paramString2);
  
  String getType(String paramString);
  
  String getValue(String paramString1, String paramString2);
  
  String getValue(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\Attributes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */