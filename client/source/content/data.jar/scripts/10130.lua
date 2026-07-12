-- [A]
-- Sort : Trêve (ID : 137)
-- Classe : Feca
--

function displayEffectCible()
	-- Ajout du système de particule sur la destination (id de fichier = 10000)
	particleId = Particle.addParticleSystem(10130, startX, startY, startZ)
end

function executeAction ()
	ScriptedAction.executeFirstAction(3, 80)
	ScriptedAction.executeFirstAction(3, 83)
end

--
-- Exécution du script
--
ScriptedAction.executeFirstAction(3, 91)

startMobileId = Cast.getCaster()
startX, startY, startZ = Mobile.getMobilePosition(startMobileId)

-- Animation du lanceur
Mobile.setMobileAnimation(startMobileId, "AnimTreve")

-- Affichage de l'effet
Sound.playSound(107, true)
invoke(1600,1,"executeAction")
invoke(500, 1, "displayEffectCible");

