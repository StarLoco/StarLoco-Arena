package com.ankamagames.xulor.template.decorator;

import com.ankamagames.xulor.theme.IThemeElement;

public abstract interface IDecorator
{
  public abstract String getState();
  
  public abstract boolean isEnabled();
  
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract IThemeElement toThemeElement();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\decorator\IDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */