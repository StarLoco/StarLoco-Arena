-- [R]
-- Sort : Roulette (ID : 134)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10640, startX, startY, startZ)
	-- Appel le son d'explosion
	invoke(100, 1, "playSpellSound")
end

function playSpellSound()
	-- Joue un son en stéréo
	Sound.playSound(602, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast")

-- Affichage de l'effet
invoke(950, 1, "displayEffect");

