package org.jdom.filter;

import java.io.Serializable;

public interface Filter extends Serializable {
  boolean matches(Object paramObject);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\filter\Filter.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */