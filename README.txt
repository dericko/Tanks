Tanks
=====
Description: A 2D turn-based tank shooter

This is an implementation of a Worms/Tanks/Gunbound game, with tank players who shoot/damage or "bunge" (knock off screen) each other until a single player is left. Accordingly, the final tank is transformed into a frog upon winning.

--
Implementation Notes:

Datatypes - Used LinkedList to store sprites as fieldSprites in GameField. Used ArrayList to store players to cycle turns (originally used a LinkedList to cyclically add() and remove() by turn, but remove()-ing dead players led to errors in for-each loops)

Classes (alphabetically):

Sprite - the super type of all game objects, adapted from GameObject.

Bomb - sprite created by Tank types upon release of the spacebar (shoot).

Brick - a sprite destroyable after two shots, randomly placed.

Direction - Enum from the example, to aid switch statement in Sprite class.

Game - the frame/window manager and main function for this program.

GameField - from GameCourt, handles the primary game logic for sprites and iteration.

HorizDivider - a sprite, makes up the destroyable "ground".

Instructions - sprite to hold instruction images.

Slider - sprite, the power bar for shooting.

Tank - sprite, controlled by player with arrow keys and spacebar.

VertDivider - sprite, randomly placed horizontally.

Comments:
I wish I had time to add scoring beyond health points, sound, as well item pickups, wind, and AI!
Should not require any additional libraries