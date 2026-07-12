-- [A]
-- Sort: Carapace (ID: 143)
-- Classe: Osamoda
--



function displayEffect()
	particleId = Particle.addParticleSystem(10225, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 80)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCarapace")

-- Animation du sort
invoke(00, 1, "displayEffect")
invoke(1400,1,"executeAction")
Sound.playSound(202, true)
