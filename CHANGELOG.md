⚡ Mekanism: Elements — Changelog

🚀 [3.0.0]

Ported from Minecraft 1.21.1 to Minecraft 1.20.1.

The last release for 1.20.1 was 2.3. Everything the mod gained across the 1.21.1 line, from 3.0.1 through 3.0.16, is now available on 1.20.1 as well. The two versions are feature equivalent.

Built against Forge 47.4.23, Mekanism 10.4.16.80, Mekanism: Generators 10.4.16.80, JEI 15.58.0.209 and Patchouli 1.20.1-85.

1) Everything that arrives from the 1.21.1 line

From 3.0.1

Chemical Demolition Machine recipe.
Air Compressor and Seawater Pump now have their own JEI recipe and info categories.
Californium and Curium are radioactive.
Ammonia works as an alternative jetpack fuel: it burns five times slower than hydrogen and gives 1.5x thrust.
Ammonia powered flight has double horizontal speed and improved braking.
Helium and Superheated Helium are fully supported as reactor coolants.

From 3.0.2

Ammonium Nitrate and Ammonium Nitrate Solution, the latter usable as a coolant.
Antimatter Fluid, derived from Mekanism's antimatter gas.
The Chemical Demolition Machine can break Totems of Undying into 20 Emeralds and 7 Enchanted Golden Apples.
The Chemical Demolition Machine now dissolves items with gaseous Seawater instead of Aqua Regia.
Fixed the Aqua High-Quality Concrete Powder painting recipe.

From 3.0.3

The Chemical Demolition Machine is part of the machine tags and drops itself properly.

From 3.0.4

New gasGeneratorDebug config option; the Gas-Burning Generator's verbose logging is now opt in instead of always on.
Removed leftover debug logging from the Chemical Demolition Machine and the Radiation Irradiator.

From 3.0.5

Ammonia tooltips show burn time and energy output, the same way Ethene does.
Clicking the progress arrow opens that machine's recipes in the recipe viewer, for the Radiation Irradiator, the Adsorption Separator and the Chemical Demolition Machine.

From 3.0.6

Fixed gas pipes not connecting to the Radiation Irradiator's output.

From 3.0.7

Fixed the Industrial Glass recipe being uncraftable because it pointed at empty glass tags.

From 3.0.8

Fixed Ammonia not generating energy in the Gas-Burning Generator.
Americium is a valid Gas-Burning Generator fuel: 500 tick burn time, 60,000 FE/t.

From 3.0.9

The Methane oxidizing recipe uses Substrate instead of Bio Fuel.

From 3.0.10

Simplified Chinese translation, by LogicWheat.

From 3.0.11

Infinite Ores. The Seawater Pump extracts Seawater from ocean biome water, High-Performance Adsorbents pull the trace metals out of it, and the new Infinity Ore Reprocessing machine reprocesses items with Seawater for extra resources.

From 3.0.12

Russian and pre-reform Russian translations, by M998__.

From 3.0.13

Crafting recipe for the Infinity Ore Reprocessing machine, which previously could not be obtained in survival.

From 3.0.14

Fixed the Chemical Demolition Machine's second output slot being unreachable by pipes, Logistical Sorters and auto-eject.

From 3.0.15

New Chemical Demolition Machine texture, redrawn in the Mekanism style instead of reusing the Adsorption Separator's.
The Infinity Ore Reprocessing machine is a normal single block machine with its own textures and a lit front while running.
Fixed the c:potassium_hydroxide fluid tag pointing at potassium chloride.
Fixed Aqua High-Quality Concrete Slab rendering in the bottom half when placed as a top slab.
Fixed White High-Quality Concrete Stairs using the gray model in one variant.

From 3.0.16

In game guide book, built with Patchouli, with a page for every block, item and machine, in all eight languages. Patchouli is now a required dependency. The book is craftable and also sits at the end of the creative tab.

2) Differences on 1.20.1

Mekanism 10.7 merged gases, infusion types, pigments and slurries into one chemical type. Mekanism 10.4 still keeps them separate, so three things behave differently here.

Adsorption Separator. Its outputs mix Mekanism's dirty slurries with this mod's own gases, which are different registries on 10.4. The machine uses a four type chemical tank, the same one the Chemical Dissolution Chamber uses, and the output side configuration, the output slot and the JEI page follow whichever chemical the current recipe produces. Piping the output into a Chemical Washer or a chemical tank works as before.

Ammonium Nitrate Solution. The Chemical Washer only accepts slurries on 10.4, so this recipe runs in the Chemical Infuser instead: Ammonium Nitrate plus Water Vapor produces Ammonium Nitrate Solution. The water to nitrate ratio and the amount produced are unchanged; the water is consumed as Water Vapor, which the Rotary Condensentrator makes from water.

MekaSuit jetpack on Ammonia. The Jetpack Unit has no thrust multiplier on 10.4, so ammonia thrust is a fixed 1.5x instead of also scaling with the installed module.

3) Fixes

Only five of the mod's twenty four fluids had a blockstate, so the other nineteen logged a missing model warning for every fluid level on every startup, over three hundred lines per launch. Every registered fluid now gets one.

Machine description screens in JEI were squeezed onto a single line and shrunk until they fit, which made them nearly unreadable. They now wrap onto as many lines as they need, including in languages that do not separate words with spaces.

4) Internal

Recipe serializers were rewritten for the pre 1.21 format, reading and writing JSON and packets directly instead of using codecs.

Syringes store their use count in item NBT instead of data components.

Chemical fuel, coolant and radioactivity are attached to each gas when it is registered rather than declared in data maps, which do not exist on 10.4.

Energy values use FloatingLong instead of long.

Datagen writes to the pre 1.21 folder names, and the common tags the mod reads and contributes to moved from the c namespace to forge.

Ammonia support for the Jetpack and the MekaSuit is applied to the gas tank Mekanism builds for its armor, since the item attachment API used on 1.21 does not exist on 1.20.1. The Gas-Burning Generator needs no patch here, because 10.4 reads the fuel attribute on its own.

The build uses ForgeGradle 6 and targets Java 17.
