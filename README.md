RedstoneHelperMod
=================

WIP - Build redstone circuits with the click of a button

Never able to remember how to build an RS Latch or pulse limiter? Hate toggling out of Minecraft to look up redstone
schematics? Then this mod is for you.

Using the 'Redstone Helper' item, quickly switch between logic gates and basic circuits, then generate it in the world
with a simple right click! While in survival mode, all the required blocks will be automatically removed from the player's
inventory as though the player placed them all him/herself, or you can disable material requirements in the config to
allow free redstone building.

To change the type of block used as a base, currently you have to edit the value of 'BASE' in the array file. I plan to
make this first a configurable option, and then hopefully selectable in-game from a gui. The current base block is dirt.

Hover the mouse over the Redstone Helper for a short description of the selected gate / circuit.

Current Gates/Circuits
======================
- or gate (horizontal and vertical variations)
- nor gate (horizontal and vertical variations)

Controls
========

If you've used my StructureGenerationTool, the controls are exactly the same as for the ItemStructureSpawner.

(coming soon) Sneak to highlight area in which circuit will generate

'Arrow Keys' - up: moves circuit away from player; down: moves circuit towards/behind player; left / right

'O' - changes circuit's default orientation by 90 degrees. This will have the effect of changing which side spawns facing the player.

'I' - toggles between increment and decrement y offset

'Y' - increments or decrements y offset (i.e. circuit will generate further up / down)

'U' - reset x/y/z offsets to 0

'V' - toggles between generate / remove circuit (NOTE - you will NOT get your blocks back when you remove this way)

'[' / ']' - Previous / Next circuit in the list

Right click - generate / remove circuit at tile location clicked

Controls can be customized from the config file that is generated the first time you load this mod.

Coming Soon
===========
All logic gates (and, nand, xor, xnor)
Basic circuits (rs-latch, t flip-flop, basic clocks, pulse limiter)
Preview of circuit footprint (hopefully one day see all the blocks as they will appear, but no promises on that one)
