-- [R]
-- Sort : Colère de Iop (ID : 165)
-- Classe : Iop
--
function displayEffect()
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(10840, startX-1, startY, startZ)
end
function playSpellSound()
	-- Joue un son en stéréo (id de fichier = 2)
	Sound.playSound(803, true)
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimArmureFeuVEnt");
-- Affichage de l'effet
invoke(800, 1, "displayEffect");
invoke(400, 1, "playSpellSound");