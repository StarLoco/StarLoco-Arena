-- [Y]
-- Sort : Sacrifice (ID : 135)
-- Classe : Sacrieur
--

function displayEffect()
	Particle.addParticleSystem(11125, destX, destY, destZ)
end

function playSpellSound()
	Sound.playSound(11125, true)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 64)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriPunition01")

-- Animation du sort
playSpellSound()
invoke(1000, 1, "displayEffect");
invoke(1900, 1, "playSpellSound")


