-- [A]
-- Sort: Ronce (ID: 75)
-- Classe: Sadida
--


function displayEffectCible()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	particleIdCible = Particle.addParticleSystem(11001, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 3)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()


-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimRonce")

-- Animation du sort
invoke(300, 1, "displayEffectCible");
invoke(300, 1, "executeAction");
Sound.playSound(1001, true)
