-- [Y]
-- Sort : Furie (ID : 30)
-- Classe : Sacrieur
--

function displayEffect()
	Particle.addParticleSystem(11120, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(1101, true)
end

function hit()
	ScriptedAction.executeFirstAction(3, 5)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriFurie01")

-- Animation du sort
playSpellSound()
invoke(400, 1, "displayEffect");
invoke(400, 1, "hit");