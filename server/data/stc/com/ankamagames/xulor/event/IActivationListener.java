package com.ankamagames.xulor.event;

import com.ankamagames.xulor.core.ElementMap;

public abstract interface IActivationListener
  extends IListener
{
  public abstract void setActivatedFunc(String paramString, ElementMap paramElementMap);
  
  public abstract void run(ActivationEvent paramActivationEvent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\IActivationListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */