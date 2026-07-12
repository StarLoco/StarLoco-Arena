package com.ankamagames.framework.kernel.core.controllers;

import java.awt.event.KeyEvent;

public interface KeyboardController {
  boolean keyTyped(KeyEvent paramKeyEvent);
  
  boolean keyPressed(KeyEvent paramKeyEvent);
  
  boolean keyReleased(KeyEvent paramKeyEvent);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\controllers\KeyboardController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */