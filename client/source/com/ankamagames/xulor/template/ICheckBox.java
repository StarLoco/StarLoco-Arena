package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.listener.SelectionChangedListener;

public interface ICheckBox extends IObservableLabel {
  void setValue(String paramString);
  
  String getValue();
  
  void setOnSelectionChange(SelectionChangedListener paramSelectionChangedListener);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ICheckBox.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */