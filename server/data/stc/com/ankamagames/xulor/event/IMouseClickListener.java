package com.ankamagames.xulor.event;

import com.ankamagames.xulor.core.ElementMap;

public abstract interface IMouseClickListener
  extends IListener
{
  public abstract void setMouseClickFunc(String paramString, ElementMap paramElementMap);
  
  public abstract void run(MouseClickEvent paramMouseClickEvent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\IMouseClickListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */