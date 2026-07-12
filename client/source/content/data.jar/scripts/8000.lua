-- Corps à corps

ScriptedAction.executeFirstAction(3, 91)

startMobileId = Cast.getCaster()
destX, destY, destZ = Cast.getPosition()

Mobile.setMobileLookAt(startMobileId, destX, destY, false)
Mobile.setMobileAnimation(startMobileId, "AnimSort-1-0")

invoke(350, 1, "displayHit")

function displayHit()
	ScriptedAction.executeFirstAction(3, 1)
end

