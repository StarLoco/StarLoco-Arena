-- [A]
-- Sort: Oeil de taupe
-- Classe: Cra
--


function displayEffect()
	particleId = Particle.addParticleSystem(10905, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 73)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimOeilDeTaupe")
destX, destY, destZ = Cast.getPosition()

-- Animation du sort
invoke(350, 1, "displayEffect");
invoke(1700,1,"executeAction")
Sound.playSound(901, true)