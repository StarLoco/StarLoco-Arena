-- [Y]
-- Sort : Vol de vie (ID : 71)
-- Classe : Sram
--

function displayEffectCible()
	particleIdCible = Particle.addParticleSystem(10416, destX, destY, destZ)
end

function displayEffect()
	particleId = Particle.addParticleSystem(10415, startX, startY, startZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 6)
end

function playSpellSoundCible()
	Sound.playSound(405, true)
end

function playSpellSound()
	Sound.playSound(406, true)
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
invoke(150, 1, "displayEffectCible")
invoke(150, 1, "playSpellSoundCible")
invoke(800, 1, "playSpellSound")
invoke(800, 1, "displayEffect")
invoke(1600, 1, "applyEffect")
