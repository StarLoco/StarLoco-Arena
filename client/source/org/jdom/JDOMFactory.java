package org.jdom;

import java.util.Map;

public interface JDOMFactory {
  void addContent(Parent paramParent, Content paramContent);
  
  void addNamespaceDeclaration(Element paramElement, Namespace paramNamespace);
  
  Attribute attribute(String paramString1, String paramString2);
  
  Attribute attribute(String paramString1, String paramString2, int paramInt);
  
  Attribute attribute(String paramString1, String paramString2, int paramInt, Namespace paramNamespace);
  
  Attribute attribute(String paramString1, String paramString2, Namespace paramNamespace);
  
  CDATA cdata(String paramString);
  
  Comment comment(String paramString);
  
  DocType docType(String paramString);
  
  DocType docType(String paramString1, String paramString2);
  
  DocType docType(String paramString1, String paramString2, String paramString3);
  
  Document document(Element paramElement);
  
  Document document(Element paramElement, DocType paramDocType);
  
  Document document(Element paramElement, DocType paramDocType, String paramString);
  
  Element element(String paramString);
  
  Element element(String paramString1, String paramString2);
  
  Element element(String paramString1, String paramString2, String paramString3);
  
  Element element(String paramString, Namespace paramNamespace);
  
  EntityRef entityRef(String paramString);
  
  EntityRef entityRef(String paramString1, String paramString2);
  
  EntityRef entityRef(String paramString1, String paramString2, String paramString3);
  
  ProcessingInstruction processingInstruction(String paramString1, String paramString2);
  
  ProcessingInstruction processingInstruction(String paramString, Map paramMap);
  
  void setAttribute(Element paramElement, Attribute paramAttribute);
  
  Text text(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\JDOMFactory.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */