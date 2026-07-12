-- [A]
-- Sort: Corbeau (ID: 61)
-- Classe: Osamoda
--

function displayEffect()
	Sound.playSound(201)
	particleId, time = Particle.addTweenParticleSystem(10210, startX, startY, startZ, destX, destY, destZ, 45, 0)
end

function executeAction ()	
	ScriptedAction.executeFirstAction(3, 2);
end


function explode() 
	Particle.addParticleSystem(10211, destX, destY, destZ)
	Sound.playSound(200)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition()

Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast")

-- Affichage de l'effet (dans 850 ms)
invoke(275, 1, "displayEffect");
invoke(600, 1, "explode")
invoke(825 , 1, "executeAction")