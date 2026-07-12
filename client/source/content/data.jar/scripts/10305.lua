-- [A]
-- Sort: Pelle Phantomatique (ID: 40)
-- Classe: Enutrof
--

	
function displayEffect()
	particleId = Particle.addParticleSystem(10305, destX, destY, destZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 4)
end

function playSpellSound()
	Sound.playSound(301, true)
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
Mobile.setMobileAnimation(startMobileId, "AnimEnuPellefantomatique")

-- Animation du sort
invoke(2300, 1, "executeAction")
invoke(1100, 1, "playSpellSound")
invoke(0, 1, "displayEffect")
Sound.playSound(3032, true)