-- [Y]
-- Sort : Pied du sacrieur (ID : 204)
-- Classe : Sacrieur
--

function displayEffect()
	Particle.addParticleSystem(11140, destX, destY, destZ)
end

function hit()
	ScriptedAction.executeFirstAction(3, 3)
end

function playSpellSound()
	Sound.playSound(1100, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancée de sort
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSacriPied01")

-- Animation du sort
playSpellSound()
invoke(700, 1, "displayEffect");
invoke(700, 1, "hit");
