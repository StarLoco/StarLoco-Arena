-- [Y]
-- Sort : Piege Mortel (ID : 154)
-- Classe : Sram
--

function displayEffectCible()
	Particle.addParticleSystem(10445, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 68)
end

function playSpellSound()
	Sound.playSound(407, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimDiversion")

-- Animation du sort
invoke(450, 1, "playSpellSound")
invoke(1650, 1, "displayEffectCible")
invoke(1750, 1, "applyEffect")
