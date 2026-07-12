package com.ankamagames.xulor.template.decorator;

import com.ankamagames.xulor.theme.IThemeElement;

public interface IDecorator {
  String getState();
  
  boolean isEnabled();
  
  void setEnabled(boolean paramBoolean);
  
  IThemeElement toThemeElement();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\decorator\IDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */