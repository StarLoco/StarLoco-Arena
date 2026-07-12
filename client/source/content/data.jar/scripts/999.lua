--
-- Animation : Echec critique
--

function displayAnimation()
{
	Mobile.setMobileAnimation(startMobileId, "AnimTacle")
}

startMobileId = Cast.getCaster()
invoke(1000, 1, "displayAnimation")

