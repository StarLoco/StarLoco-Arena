package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.listener.SelectionChangedListener;

public interface IRadioButton extends IObservableLabel {
  void setOnSelectionChange(SelectionChangedListener paramSelectionChangedListener);
  
  String getValue();
  
  void setValue(String paramString);
  
  String getGroupId();
  
  void setGroupId(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IRadioButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */