-- [Y]
-- Sort : Attaque Mortelle (ID : 73)
-- Classe : Sram
--

function displayEffect()
	particleId = Particle.addParticleSystem(10405, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 5)
end

function startMobileAnimation()
	Mobile.setMobileAnimation(startMobileId, "AnimAttaqueMortelle")
end

function playSpellSound()
	Sound.playSound(404, true)
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
invoke(300, 1, "startMobileAnimation")

-- Animation du sort
displayEffect()
invoke(400, 1, "playSpellSound")
invoke(1600, 1, "applyEffect")
