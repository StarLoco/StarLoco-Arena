-- [R]
-- Sort : Loterie (ID : 159)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10635, destX, destY, destZ)
	
	-- Appel le son d'explosion
	invoke(100, 1, "playSpellSound")
end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(604, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast")

-- Affichage de l'effet
invoke(800, 1, "displayEffect");

