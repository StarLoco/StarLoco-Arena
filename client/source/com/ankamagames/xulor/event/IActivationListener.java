package com.ankamagames.xulor.event;

import com.ankamagames.xulor.core.ElementMap;

public interface IActivationListener extends IListener {
  void setActivatedFunc(String paramString, ElementMap paramElementMap);
  
  void run(ActivationEvent paramActivationEvent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\IActivationListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */