-- [A]
-- Sort: Lancer de pieces (ID: 38)
-- Classe: Enutrof
--


function displayEffect()
	particleId = Particle.addParticleSystem(10345, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 4)
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuLancer")

-- Animation du sort
invoke(1200, 1, "displayEffect")
invoke(1600, 1, "executeAction")
Sound.playSound(2031, true)
