-- [A]
-- Sort: Sacrifice (ID: 196) 
-- Classe: Monster
--


function displayEffect()
	particleId = Particle.addParticleSystem(11027, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 3)
	Mobile.setMobileLookAt(startMobileId, 0, 0, true)
end

--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimCast")

-- Affichage de l'effet (dans 850 ms)
invoke(800, 1, "displayEffect")
invoke(1800, 1, "executeAction")
Sound.playSound(1000, true)