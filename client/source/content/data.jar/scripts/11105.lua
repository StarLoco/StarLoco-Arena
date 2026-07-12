-- [Y]
-- Sort : Transfert de vie (ID : 176)
-- Classe : Sacrieur
--

function displayEffect()
	particleId = Particle.addParticleSystem(11105, startX, startY, startZ)
	particleId = Particle.addParticleSystem(11106, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(11105, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriTransfdevie")

-- Animation du sort
playSpellSound()
invoke(220, 1, "displayEffect");