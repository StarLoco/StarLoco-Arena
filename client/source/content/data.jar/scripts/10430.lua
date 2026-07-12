-- [Y]
-- Sort : Double (ID : 69)
-- Classe : Sram
--

function displayEffect()
	Particle.addParticleSystem(10430, startX, startY, startZ)
end

function displayEffectCible()
	Particle.addParticleSystem(10431, destX, destY, destZ)
end

function playSpellSoundCible()
	Sound.playSound(409, true)
end

function playSpellSound()
	Sound.playSound(406, true)
end

function executeAction()
	ScriptedAction.executeFirstAction(3, 75)
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
Mobile.setMobileAnimation(startMobileId, "AnimAttaqueMortelle")

-- Animation du sort
invoke(150, 1, "playSpellSoundCible")
invoke(200, 1, "displayEffect")
invoke(800, 1, "displayEffectCible")
invoke(800, 1, "playSpellSound")
invoke(850, 1, "executeAction")
