# Changelog

## Upcoming
* Added a data pack that makes the tool belt start with 4 slots instead of 2
* Fixed some better loot

## 1.4.2
* Oups

## 1.4.1
* Made Respawn Obelisks 3 times more common
  * Also the structure is now a data pack so disabling the Respawn Obelisk feature will also disable the structure generating
* Fixed beeg ore veins not generating in deepslate

## 1.4.0
* Added Lucky Hook enchantment
* Rebalanced armors using ITR new armor and toughness
* Reduced chance for fisher zombies
* Reduced max hostile mobs in Winter and slightly increased in summer
* Fixed Solium moss not dropping with durium shears
* Fixed missing durium shears recipe
* Fixed Keego items being repairable with Gold
* Fixed souls steel and durium items amount wrong
* Fixed cursed books having treasure enchantments tooltip

## 1.3.1
* Added Quaron Reinforced Fishing rod
  * Higher durability but slower luring/hooking
* Partially fixed recipes with damaged tools not showing up in Forge Recipe book
  * Partially because if you click the recipe the item doesn't get moved to the slot
* Fixed multipurpose appearing on armor

## 1.3.0
* Added Zippy enchantment
  * +10% movement speed
* Added Multipurpose enchantment
  * Prevents items from taking more than 1 damage when used
* Added Milk bottle and Cheese
  * Milk bottle can be drank to get rid of a random effect
* Added Durium Shears
  * Higher durability but slower
* Enchanting now requires lapis each 3 levels spent
* Grindstone now gives back 95% of the experience spent on the mod's enchanting table
  * Basically much more
* Slight rework to Blast and Soul Blast furnaces recipes
  * Blast furnace 
    * Only gives 60% materials of items recycled instead of 75%
    * Ore blocks now give 2x output increase and raw ores give +30%
  * Soul Blast Furnace
    * Now consumes 2x fuel like blast furnace
    * Smelting and alloyingis 2x slower than blast furnace
    * Ore blocks and alloying now give +30% output increase
    * Is now crafted from a furnace instead of a blast furnace
  * Recycling recipes now show the amount of items returned
  * Fixed many recipes missing/wrong
* Quaron 
  * Tools and armor are now made from wooden and leather counterparts
  * Reduced armor/regen absorption, tool's enchantability and efficiency
  * Slightly changed the recipe
  * Slightly increased speed against wrong blocks with Efficiency
* Increased Durium attack damage
* Fixed torch arrows replacing already existing torches
* Removed 'Tool enchantability fix', using enchantability in Item Statistics

## 1.2.1
* Disabled angry creeper sound
* Fixed some strange shenanigans with Torch Arrow placing
* Removed beeg coal vein since in ITR coal veins are now the same size

## 1.2.0
* Added Vindication enchantment
* Recall Idol is now a potion effect brew from regeneration and wither roses
* The enchanting table ui has been enlarged a little bit
* Animals and sea creatures are now reduced in Autumn
* Bone meal now has 40% and 80% chance to fail to grow plants in autumn and winter respectively
* Added the `iguanatweaks:doSeasonGrassGrowDeath` gamerule to prevent grass from growing and dying based off seasons
* Added some stuff to some loot tables
* Seasons growth are now included in the mod through a data pack instead of the Mod pack only
* Fixed up and down arrows not working in the enchanting table
* Fixed pumpkin pulp missing at all
* Fixed enchanting table not working in servers
* Fixed EnhancedAI feature still changing its config even when disabled

## 1.1.9
* Fixed compatibility with latest ITR
* Vexes now have swords again and lowered attack_damage (-4)
* Vindicators no longer have -40% attack damage, instead they have -5

## 1.1.8
* Changed Quaron bonus speed on wrong blocks (+1 -> +0.5 + 0.2/efficiency level)
* Copper tools can no longer mine ores (like wooden tools)
  * But have increased efficiency
* Mobs from spawners now have reduced buffs
* Wolves and Trader llamas are now 10% slower
* Fixed applying cleansed and ancient lapis twice

## 1.1.7
* Witches and endermen are now 10% slower
* Keego Movement momentum now gives 5% bonus speed per level instead of 10%
* Rebalanced armor (or regen. absorption) like in the base mod

## 1.1.6
* Now works with Time control 1.6.0
  * Also added config to change how much time day and night last when changing seasons

## 1.1.5
* Shields recipes are now consistent with other tools
* Crate slowdown is now exponential (15% per crate above max -> 5%/20%/45%/80% for 1/2/3/4 crates above max)
* Added Quaron stuff to tags
* Corail woodcutter support has been replaced with Sawmill
* Solarium and keego hoe cooldown reductions are now lower 
* Slightly changed hammers cooldowns

## 1.1.4
* Added support for the Time Control mod
  * If the mod is installed, the day and night durations change based off the current season
* Expanded is now compatible with Pickaxes and shovels
* Respawn obelisks now have a slightly lower chance to have iron blocks
* You can now change your old respawn point when you have a respawn obelisk set
* Fixed new arrows giving normal arrows when picked up

## 1.1.3
* Reduced mobs attack reach by ~15%
* Respawn obelisks now have a higher chance to have iron blocks
* Fixed Blast furnace ghost block not working on servers
* Fixed item_stats data pack not loaded properly
* Fixed compatiblity with Paxi

## 1.1.2
* Blast furnaces now show ghost blocks on how to build them
* Charcoal can no longer be placed as a pile. Charcoal pile now drops itself with Silk Touch
* Expanded and Veining now highlight the blocks that are going to be mined
* Enchantments base cost for enchanting table can now be configured
* Item stats data pack can now be disabled
* Fixed enchanting table not dropping
* Fixed mobs despawning closer making more spawn around
* Paxi and Global Data mods now should run after this mod's, allowing for overwriting changes from integrated packs

## 1.1.1
* Enchanted books can now be used on an enchanting table to let it learn the enchantment, useful for treasure enchantments
* Reach enchantment is now treasure only
* Better enchanting table interface
* Soul Steel equipment is now made from Iron
* Repairing smithen items with their base material now costs more based off durability ratio
  * Also, equipment smithen from Iron can now be repaired with nuggets
* Changed quaron alloying and made equipment smithable
* Alloying in soul blast furnace no longer has a chance to double output
* Fixed most armors having wrong slowdown modifiers
* Fixed Keego Shield being a Solarium shield
* Fixed firestarter not breaking

## 1.1.0
* Added a brand new Enchanting Table!
  * Choose your enchantments! The limit is half the item's enchantability + 3
  * If you infuse an item with a cleansed lapis, you'll be able to use the full item's enchantability
  * If you empower an item with Ancient Lapis, max cost is increased by 25% and allows over max level enchantments
  * Enchanted items can no longer be forged or upgraded in Smithing table. Also can no longer be merged with other enchanted items. Enchanted books can only be applied to unenchanted items
  * Items in the `iguanatweaksexpanded:not_enchantable` item tag will no longer be enchantable
    * By default, books, flint and steel, shears, brush and elytra are no longer enchantable
  * Lapis (moved to Enchanting feature)
    * Reduced Cleansed Lapis drop rate
    * Cleansed lapis no longer increases an enchantment level
    * Ancient lapis no longer increase the max level of an enchantment past it's maximum
* Enchantments
  * (New) Reach (increases block and entity reach by 20%)
  * (New) Veining (does what Expaded did on Axes and shovels, basically vein mine)
  * Expanded is now limited to Pickaxes
* Added Quaron
  * A new material that increases mining speed against blocks that require no tool
* Natural veins no longer generate with poor and rich ores (can be re-enabled from 'Ore Generation' config)
* Copper tools now lasts 1% more per block below sea level
* Rebalanced some material stats
* Totally rebalanced armor
* Soul Steel items are no longer fire-resistant
* Solarium armor bonus toughness/regen. absorption speed increased (2 -> 2.5 toughness (20% -> 25% regen. abs. speed))
* Fixed villagers ignoring MultiBlock Blast furnace and fletching table as workstations
* Fixed ore rocks advancement
* Fixed Durium shield smithing recipe not working
* Fixed Soul Steel tools textures
* Fixed crash when using Forge with Step Up enchantment
* Fixed smithing items with lower durability than the original item resulting in negative durability

## 1.0.4
* Arrows
  * Diamond arrows are now heavier and fall faster
  * Quartz arrows are now faster and have less friction
* Rebalanced Hammers cooldown
  * Cooldown is now capped at 0.5 seconds
* Slight rebalancement of durium and solarium
* Fixed vanilla material hammers not having stat changes

## 1.0.3
* To reduce inventory clutter, ore rocks now drop raw ores
  * Silk touch if you want to get the rocks
* Fixed missing lang for new foods
* Fixed blast furnace recipe not unlocking
* Fixed invisible falling charcoal pile

## 1.0.2
* Fixed respawn obelisk structures having no obelisk

## 1.0.1
* Requires ITR 3.0.2
* Fixed config folder generating stuff outside the `config/iguanatweaksreborn` folder

## 1.0.0
"First" release