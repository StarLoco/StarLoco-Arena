-- [Y]
-- Sort : Transposition (ID : 28)
-- Classe : Sacrieur
--

function displayEffect()
	Particle.addParticleSystem(11130, startX, startY, startZ)

end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 64)
end

function playSpellSound()
	Sound.playSound(1107, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimSacriTransposition")

-- Animation du sort
playSpellSound()
invoke(600, 1, "applyEffect");
invoke(800, 1, "displayEffect");

