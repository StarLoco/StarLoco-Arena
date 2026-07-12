-- [A]
-- Sort: Tourmente visuelle (ID: 171)
-- Classe: Cra
--


function displayEffect()
	particleId = Particle.addParticleSystem(10910, startX, startY, startZ)	
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
Mobile.setMobileAnimation(startMobileId, "AnimTourmenteVisuelle")

-- Animation du sort
invoke(350, 1, "displayEffect");
invoke(1800,1,"executeAction")
Sound.playSound(900, true)