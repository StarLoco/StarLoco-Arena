package org.jdom;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.jdom.filter.Filter;

public interface Parent extends Cloneable, Serializable {
  Object clone();
  
  List cloneContent();
  
  List getContent();
  
  Content getContent(int paramInt);
  
  List getContent(Filter paramFilter);
  
  int getContentSize();
  
  Iterator getDescendants();
  
  Iterator getDescendants(Filter paramFilter);
  
  Document getDocument();
  
  Parent getParent();
  
  int indexOf(Content paramContent);
  
  List removeContent();
  
  Content removeContent(int paramInt);
  
  boolean removeContent(Content paramContent);
  
  List removeContent(Filter paramFilter);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\Parent.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */