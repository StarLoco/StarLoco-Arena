-- [A]
-- Sort: Mot de torture (ID: 163)
-- Classe: Eniripsa
--


function displayEffectCible()
	particleId = Particle.addParticleSystem(10745, destX, destY, destZ)	
end

function displayEffectLanceur()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
end

function playSpellSound()
	Sound.playSound(700, true)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 2)
	ScriptedAction.executeFirstAction(3, 11)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni01")

displayEffectCible();
displayEffectLanceur();
invoke(800,1,"executeAction")
invoke(200,1,"playSpellSound")