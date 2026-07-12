-- [Y]
-- Sort : Diversion (ID : 65)
-- Classe : Sram
--

function displayEffectCible()
	particleIdCibl = Particle.addParticleSystem(10435, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(401, true)
end

function executeActions()
	ScriptedAction.executeAllAction(3, 68)
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
playSpellSound()
invoke(1450, 1, "displayEffectCible")
invoke(1550, 1, "executeActions")

