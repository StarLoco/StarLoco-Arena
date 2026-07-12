package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMenuItemPressedListener;

public abstract interface IMenuItem
  extends IElement
{
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract void setText(String paramString);
  
  public abstract void setOnClick(IMenuItemPressedListener paramIMenuItemPressedListener);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IMenuItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */