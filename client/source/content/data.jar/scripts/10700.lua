-- [A] 
-- Sort: Mot curatif (ID: 21)
-- Classe: Eniripsa
--


function displayEffect()
	particleId = Particle.addParticleSystem(10700, destX, destY, destZ)	
end

function executeAction(actionId)
	ScriptedAction.executeFirstAction(3, 69)
end

function playSpellSound()
	Sound.playSound(704, true)
end


--
-- Exécution du script
--

ScriptedAction.executeFirstAction(3, 91)
startMobileId = Cast.getCaster()

-- Recup des coordonnees du perso cible
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)
destX, destY, destZ = Cast.getPosition()

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimSortMotEni01")

-- Affichage de l'effet
invoke(750, 1, "displayEffect")
invoke(900, 1, "executeAction")
