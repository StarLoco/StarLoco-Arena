package com.ankamagames.xulor.event;

import com.ankamagames.xulor.core.ElementMap;

public interface IMouseClickListener extends IListener {
  void setMouseClickFunc(String paramString, ElementMap paramElementMap);
  
  void run(MouseClickEvent paramMouseClickEvent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\IMouseClickListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */