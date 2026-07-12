package com.ankamagames.xulor.template;

public abstract interface IObservable
  extends IComponent, IListenerManager
{
  public abstract void setEnabled(boolean paramBoolean);
  
  public abstract boolean isEnabled();
  
  public abstract void setTraversable(boolean paramBoolean);
  
  public abstract boolean getTraversable();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\IObservable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */