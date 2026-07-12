-- [Y]
-- Sort : Teleportation (ID : 45)
-- Classe : Xelor
--

function displayEffect()
	Particle.addParticleSystem(10505, startX, startY, startZ)
end

function displayEffectCible()
	Particle.addParticleSystem(10505, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 39)	
end

function playSpellSound()
	Sound.playSound(510, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimContre")

-- Animation du sort
playSpellSound()
invoke(1200, 1, "displayEffect")
invoke(1600, 1, "displayEffectCible")
invoke(1700 , 1, "applyEffect" )


