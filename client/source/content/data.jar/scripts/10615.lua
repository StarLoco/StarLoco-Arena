-- [R]
-- Sort : Tout ou Rien (ID : 13)
-- Classe : Ecaflip
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10615, startX, startY, startZ)
	-- Appel le son d'explosion
	invoke(100, 1, "playSpell")
end

function playSpell()	
	ScriptedAction.executeFirstAction(3, 1)
	ScriptedAction.executeFirstAction(3, 69)
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(601, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast")
-- Affichage de l'effet
invoke(750, 1, "displayEffect");