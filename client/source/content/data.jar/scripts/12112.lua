-- [A]
-- Carte évènement: Action héroïque (ID: 190)
-- 
--


function executeAction ()
	ScriptedAction.executeFirstAction(3, 13)
end

function displayEffect ()
	Particle.addParticleSystem(12112, destX, destY, destZ)
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

invoke(1000,1,"executeAction")
invoke(700,1,"displayEffect")
Sound.playSound(12112, true)