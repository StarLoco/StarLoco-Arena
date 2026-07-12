-- [R]
-- Sort : Esprit Félin (ID : 11)
-- Classe : Ecaflip
--
function explode()
	-- dommages
	ScriptedAction.executeFirstAction(3, 1)
	ScriptedAction.executeFirstAction(3, 3)
	-- Ajout du système de particule sur la destination
	Particle.addParticleSystem(10620, destX, destY, destZ)	
	-- Appel le son d'explosion
	invoke(100, 1, "playExplodeSound")
end

function playExplodeSound()
	-- Joue un son en stéréo
	Sound.playSound(0606, true);
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
invoke(850, 1, "explode");