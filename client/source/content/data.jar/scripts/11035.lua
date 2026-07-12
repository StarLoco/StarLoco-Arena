-- []
-- Sort: Sacrifice poupesque (ID: 85)
-- Classe: Sadida
--


function executeAction ()
	ScriptedAction.executeFirstAction(3, 7)
end

--
-- Exécution du script
--
	
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du lanceur
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees dela cible
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSacrifPoupe")

-- Animation du sort
invoke(1000, 1, "executeAction")
Sound.playSound(1004, true)
