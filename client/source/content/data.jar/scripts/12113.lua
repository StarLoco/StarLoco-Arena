-- [A]
-- Carte évènement: Vision améliorée
--
--


function displayEffect()
	Particle.addParticleSystem(12113, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 72)
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
invoke(1300,1,"executeAction")
Sound.playSound(12113, true)