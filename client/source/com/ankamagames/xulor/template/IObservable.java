package com.ankamagames.xulor.template;

public interface IObservable extends IComponent, IListenerManager {
  void setEnabled(boolean paramBoolean);
  
  boolean isEnabled();
  
  void setTraversable(boolean paramBoolean);
  
  boolean getTraversable();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IObservable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */