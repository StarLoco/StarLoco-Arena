-- [A]
-- Sort : Attaque nuageuse (ID : 31)
-- Classe : Feca
--

function displayEffectCible()
	Particle.addParticleSystem(10120, destX, destY, destZ)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 2)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimFaiblesse")

Sound.playSound(105, true)
invoke(0, 1, "displayEffectCible");
invoke(2300, 1, "executeAction")


