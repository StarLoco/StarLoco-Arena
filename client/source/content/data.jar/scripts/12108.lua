-- [A]
-- Carte évènement: La patte d'Ecaflip (ID: 188)
--
--


function displayEffect()
	Particle.addParticleSystem(12108, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeAllAction(3, 3)
	ScriptedAction.executeAllAction(3, 69)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Mobile.setMobileAnimation(startMobileId, "AnimCarte")
Particle.addParticleSystem(12200, startX, startY, startZ)

invoke(700,1,"displayEffect")
invoke(1100,1,"executeAction")
Sound.playSound(12108, true)