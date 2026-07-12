package com.ankamagames.framework.kernel.core.controllers;

import java.awt.event.KeyEvent;

public abstract interface KeyboardController
{
  public abstract boolean keyTyped(KeyEvent paramKeyEvent);
  
  public abstract boolean keyPressed(KeyEvent paramKeyEvent);
  
  public abstract boolean keyReleased(KeyEvent paramKeyEvent);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\controllers\KeyboardController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */