package org.w3c.dom;

public interface DOMImplementation {
  boolean hasFeature(String paramString1, String paramString2);
  
  DocumentType createDocumentType(String paramString1, String paramString2, String paramString3) throws DOMException;
  
  Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType) throws DOMException;
  
  Object getFeature(String paramString1, String paramString2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\w3c\dom\DOMImplementation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */