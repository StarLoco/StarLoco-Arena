-- [Y]
-- Sort : Peur (ID : 67)
-- Classe : Sram
--

function displayEffect()
	particleId = Particle.addParticleSystem(10410, destX, destY, destZ+2)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 37)
end

function playSpellSound()
	Sound.playSound(408, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimCoupsSournois")

-- Animation du sort
invoke(600, 1, "playSpellSound")
invoke(700, 1, "displayEffect")
invoke(800, 1, "applyEffect")
