-- [A] 
-- Sort: Accélération (ID: 39)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10320, destX, destY, destZ)
end

function executeAction()
		ScriptedAction.executeFirstAction(3,17)
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

-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimEnuAcceleration")

-- Animation du sort
invoke(900, 1, "displayEffect")
invoke(900, 1, "executeAction")
Sound.playSound(2030, true);
