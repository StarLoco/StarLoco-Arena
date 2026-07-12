-- [R]
-- Sort : Amplification (ID : 167)
-- Classe : Iop
--
function displayEffect()
	startMobileDirection = Mobile.getMobileDirection(startMobileId)	
	if startMobileDirection == 1 then
	 particleFileId = 10837
	elseif startMobileDirection == 3 then
	 particleFileId = 10838
	elseif startMobileDirection == 5 then
	 particleFileId = 10836
	elseif startMobileDirection == 7 then
	 particleFileId = 10835
	end
	-- Ajout du système de particule sur la destination
	particleId = Particle.addParticleSystem(particleFileId, destX, destY, destZ)	
end

-- Exécution du script
ScriptedAction.executeFirstAction(3, 91);
startMobileId = Cast.getCaster();
-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()
Mobile.setMobileLookAt(startMobileId, destX, destY, false);
-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimRenvoiDeSort");
-- Affichage de l'effet
invoke(600, 1, "displayEffect");
-- Joue un son en stéréo 
Sound.playSound(806, true);