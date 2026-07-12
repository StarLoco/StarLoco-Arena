package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.IWindowClosedListener;

public interface IWindow extends IContainer {
  void pushToTop();
  
  void setOnClose(IWindowClosedListener paramIWindowClosedListener);
  
  void setTitle(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IWindow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */