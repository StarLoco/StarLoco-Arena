--- [Y]
-- Sort : Horloge (ID : 44)
-- Classe : Xelor
--

function displayEffectCible()
	Particle.addParticleSystem(10520, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 4)
end

function playSpellSound()
	Sound.playSound(505, true)
end

--
-- Execution du script
--
ScriptedAction.executeFirstAction(3, 91)

-- On récupere les informations du lancer de sort
startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimCadran")

-- Animation du sort
invoke(1200, 1, "displayEffectCible")
invoke(1050, 1, "playSpellSound")
invoke(2300, 1, "applyEffect")


