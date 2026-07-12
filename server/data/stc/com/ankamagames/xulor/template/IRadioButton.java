package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.listener.SelectionChangedListener;

public abstract interface IRadioButton
  extends IObservableLabel
{
  public abstract void setOnSelectionChange(SelectionChangedListener paramSelectionChangedListener);
  
  public abstract String getValue();
  
  public abstract void setValue(String paramString);
  
  public abstract String getGroupId();
  
  public abstract void setGroupId(String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IRadioButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */