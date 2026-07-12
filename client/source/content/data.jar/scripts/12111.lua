-- [A]
-- Carte évènement: Armure divine (ID: 186)
--
--


function displayEffect()
	Particle.addParticleSystem(12111, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 80)
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

invoke(500,1,"displayEffect")
invoke(1000,1,"executeAction")
Sound.playSound(12111, true)