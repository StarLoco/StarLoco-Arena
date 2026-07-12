package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IWindowClosedListener;

public abstract interface IWindow
  extends IContainer
{
  public abstract void pushToTop();
  
  public abstract void setOnClose(IWindowClosedListener paramIWindowClosedListener);
  
  public abstract void setTitle(String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */