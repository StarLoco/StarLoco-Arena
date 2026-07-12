-- [R]
-- Sort : Pile ou Face (ID : 10)-
-- Classe : Ecaflip
--
function displayEffect()
	-- Lancement d'un son
	Sound.playSound(609)
	-- Lancement d'une particule avec une trajectoire
	particleId, time = Particle.addTweenParticleSystem(10600, startX, startY, startZ, destX, destY, destZ, 15, 0, 1.5)
	-- Appel de l'explosion une fois la particule arrivee
	invoke(time, 1, "explode")
	invoke(time+400, 1, "removeParticle", particleId);
end

function explode()
	-- pile ou face
	ScriptedAction.executeFirstAction(3, 3)
	ScriptedAction.executeFirstAction(3, 69)
	startMobileDirection = Mobile.getMobileDirection(startMobileId)
	-- Ajout du système de particule sur la destination
	particleId2 = Particle.addParticleSystem(10601, destX, destY, destZ)
end

function removeParticle(particleId)
	-- Suppression du système de particule en mouvement
	Particle.removeParticleSystem(particleId)	
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
Mobile.setMobileAnimation(startMobileId, "AnimAttaque");
-- Affichage de l'effet
invoke(600, 1, "displayEffect");


