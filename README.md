RedstoneHelperMod
=================
WIP - Build redstone circuits with the click of a button

Never able to remember how to build an RS Latch or pulse limiter? Hate toggling out of Minecraft to look up redstone
schematics? Then this mod is for you.

Features
========
- Instantly generate various circuits by right-clicking with the Redstone Helper Item
- All circuits are accessible from a single Item; cycle between them with '[' and ']'
- Auto-consume required materials from inventory upon generation, or turn off material requirements
- Tooltip displays currently selected circuit for each Redstone Helper Item
- Basic building block of the circuit can be changed via an intuitive Gui, and the default is configurable
- Input signal locations of the circuit are denoted with light blue wool
- Output signal locations of the circuit are denoted with red wool
- Disable input / output signal highlighting to use the current base block instead of wool

Current Gates/Circuits
======================
- or gate (horizontal and vertical variations)
- nor gate (horizontal and vertical variations)

Gate and circuit designs used are from the Ultimate Collection of Redstone Circuits page (link below), so credit to
the many contributors of what is truly the ultimate resource for redstone designers.
Link to Ultimate Collection: http://www.minecraftforum.net/topic/892820-ultimate-collection-of-redstone-circuits/

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
