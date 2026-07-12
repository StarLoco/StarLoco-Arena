--- [Y]
-- Sort : Cadran du xelor (ID : 157)
-- Classe : Xelor
--
function displayEffect()
	Particle.addParticleSystem(10540, startX, startY, startZ)
end

function displayEffectCible()
	Particle.addParticleSystem(10541, destX, destY, destZ)
end

function applyEffect()
	ScriptedAction.executeFirstAction(3, 97)	
end

function playSpellSound()
	Sound.playSound(502, true)
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
displayEffect()
playSpellSound()
invoke(900 , 1, "displayEffectCible" )
invoke(2000 , 1, "applyEffect" )
