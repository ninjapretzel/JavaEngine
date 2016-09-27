# JavaEngine
A silly little java game engine I made during a class I took in 2014.

###You will need LWJGL-2 for whatever operating system you are using.
Can be found at: http://legacy.lwjgl.org/

Probably also need to fix the reference in the netbeans project after opening it.

###You will also need the slick-2d-utils
http://slick.ninjacave.com/slick-util/

Same deal with LWJGL-2, you probably need to fix the references

##There's two classes that are good example 'entry points'
  * Ex6.java - A little drawing program. Inspect the code for the controls- I don't remember all of them.
  * Ex8.java - A little game. Move with WASD, 1-4 for different weapons, Mouse to aim, click to shoot.

The code's a pretty big mess, and probably not useful to use to produce games, but it's a decent starting point for people wanting to learn how to make games. Has most of the basic features a game engine would need.

The engine's structure is loosely based off of Unity, GameObject instances hold one or more components that describe their behaviour.
