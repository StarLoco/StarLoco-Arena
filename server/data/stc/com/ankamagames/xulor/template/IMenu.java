package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IMenuClosedListener;

public abstract interface IMenu
  extends IComponent
{
  public abstract int getItemCount();
  
  public abstract IComponent getItem(int paramInt);
  
  public abstract void setText(String paramString);
  
  public abstract void setOnClose(IMenuClosedListener paramIMenuClosedListener);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */