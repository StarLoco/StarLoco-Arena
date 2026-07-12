-- [R]
-- Sort : Guigne (ID : 15)
-- Classe : Ecaflip
--
function explode()
	-- Ajout du système de particule sur la destination 
	Particle.addParticleSystem(10625, destX, destY, destZ)	
	-- Appel le son d'explosion
	invoke(0, 1, "playExplodeSound")
end

function playExplodeSound()
	-- gain EC
	ScriptedAction.executeFirstAction(3, 71)
	-- Joue un son en stéréo
	Sound.playSound(0605, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Recup des coordonnees du perso cible
destX, destY, destZ = Cast.getPosition();
-- Orientation du mobile lanceur (false = dans 4 directions uniquement)
Mobile.setMobileLookAt(startMobileId, destX, destY, false);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimCast");
-- Affichage de l'effet
invoke(800, 1, "explode");
