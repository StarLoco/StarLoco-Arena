-- [A]
-- Sort: Piqûre motivante (ID: 141)
-- Classe: Osamoda 
--


function displayEffect()
	particleId = Particle.addParticleSystem(10205, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 13)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimPiqure")

-- Animation du sort
invoke(0, 1, "displayEffect")
invoke(3000,1,"executeAction")
Sound.playSound(204, true)
