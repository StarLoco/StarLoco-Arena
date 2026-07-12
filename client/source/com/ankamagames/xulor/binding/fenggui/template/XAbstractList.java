package com.ankamagames.xulor.binding.fenggui.template;

import com.ankamagames.xulor.template.IAbstractList;
import org.fenggui.ListItem;

public abstract class XAbstractList extends XComponent implements IAbstractList {
  protected abstract void addItem(ListItem paramListItem);
  
  public abstract void setItems(Object paramObject);
  
  public abstract Object getItems();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XAbstractList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */