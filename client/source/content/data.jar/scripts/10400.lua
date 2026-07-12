-- [Y]
-- Sort : Coup Sournois (ID : 63)
-- Classe : Sram
--

function displayEffect()
	particleId = Particle.addParticleSystem(10400, destX, destY, destZ)
end

function applyEffect(effectId)
	ScriptedAction.executeFirstAction(3, effectId)	
end

function playSpellSound()
	Sound.playSound(402, true)
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
invoke(10, 1, "playSpellSound")
invoke(700, 1, "displayEffect")
invoke(1050, 1, "applyEffect", 5)
invoke(1420, 1, "applyEffect", 37)

