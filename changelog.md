# Changelog

## 1.9.13
* Hopefully fixed leaf walker still being a curse
* Reduced beeg gold ore veins and increased beeg copper ore veins (generation chance)
* Bees size is now always 80%
* Fixed soul furnace showing no fire on servers

## 1.9.12
* Blast furnace
  * Raw ores no longer have 30% chance to be doubled
  * Ore blocks no longer take half the time to be smelted (also applies to Soul Blast Furnace)
  * Increased copper ingots from smelting ore blocks (7 -> 8)
  * Reduced copper ingots from smelting ore rocks (7 -> 2)
* Soul Blast Furnace now accepts 4 lava buckets and will burn for all of them
* Keego
  * Increased base efficiency (3.5 -> 5)
  * Decreased bonus efficiency with Momentum (~~+15%~~ -> +10% per level)
  * Reduced durability (~~1007~~ -> 937)
* Explosive Barrels now require 1 less gunpowder
* Fixed ~~Gravity Defying~~ Removed enchantment popping out on books

## 1.9.11
* Mining Charges now break blocks in a 3x3x6 area (from 3x3x3)
* Slowed down Reforming (1 durability every 1 -> 2 minutes)
* Fixed items with Soulbound in toolbelt not getting kept
* Fixed armor and offhand items with Soulbound not staying in their respective slots on respawn
* Fixed not being able to shift-click items into forge bottom slot if the item was both an ingredient and a bottom item (e.g. durium ingots)
* Fixed Piercing and electrocution damage killing entities twice
* ~~Removed M.A. Jump~~

## 1.9.10
* Removed soul steel ingots from end city treasures
* Part breaker is now a treasure enchantment
* Supplementaries integration has been moved to its own data pack

## 1.9.9
* Spectral arrows are now made in the fletching table
* Season bone meal use has been moved to ITR
* Flax no longer grows in winter and autumn
* Reduced Spiders movement speed by 3%
* Fixed M.A. Jump working while mounted
* Hopefully fixed Pumpkin pulp being overwritten by ITR
* Removed cooldown from firestarter
  * This fixes firestarters extinguishing candles as soon as lit
* Fixed Farmers delight data pack being loaded even if there was no farmers delight
* Fixed Farmers Delight crops growing out of season
* Fixed Farmers Delight dog food not craftable
* Fixed mixins loading only if mods were missing
* Fixed enchant with treasure giving special enchantements such as swift sneak
* Fixed (again) enchanting table learning curses

## 1.9.8
* Milk Bottle, Rennet, Cheese and Uncooked cheese have been removed
* Netherized stew now requires Pot if Farmer's Delight is installed
* Reduced Keego durability
* Heavily reduced Quaron armor durability
* Reduced Alleviating healing (0.25 -> 0.2 per XP)
* Sweet berries now grow only in winter and autumn
* Reduced Soul Steel enchantability
* Beeg veins are now slightly rarer
* Fixed Quark Shovels being Hoes
* Mobs buffs
  * Mobs from spawners now have 50% of (most of) the original buff instead of 35%
  * The xray range is now 12 if spawned from spawners instead of 6
  * Blazes no longer get bonus follow range
  * Added a few modded mobs to the tags
* Removed Organic Compost with Rotten Flesh recipe
* Pet Cemetery's zombie and skelly wolves are now affected by the attribute changes in Enhanced AI

## 1.9.7
* Keego
  * Mining Momentum now requires 24 stacks to be fully stacked instead of 32 (~~+10%~~ -> +15% mining speed per stack)
  * Increased minimum mining effect time (0.75 -> 1 seconds)
  * Added infos in EMI
* You can now use a lava bucket directly on a Soul Blast Furnace to lit it up
* Spawners that finish spawning can now give either a treasure or a curse enchanted book
* Vanilla blast furnace recipes are now hidden from EMI
* Detector Rails no longer need redstone

## 1.9.6
* Air Stealer 
  * Is now a Trident exclusive enchantment
  * Air stolen scales with attack cooldown
* Reduced Soul Steel total Armor (12 -> 11)
* Added a new Damage Type Tag `iguanatweaksexpanded:enchantments/protection/magic` defining which damage types are reduced by Magic Protection enchantment
* Items can now be repaired again with nuggets
* Passable foliage's Leaf Walker curse is now an enchantment
* Farmer's delight integration (Season plant growth)
* Fixed dupe glitch with soulbound****
* Fixed enchanted books from spawners having fake Curses

## 1.9.5
* Reduced Enchanted Cleansed Lapis base table enchantability bonus (5 -> 4)
* Revert Exchange's 'Fixed the sound of block placing being heard only server side'
  * Should now work again with Quark's rotation lock
* Reduced Keego total armor (11 -> 8)
* Reforming now works once every minute instead of 30 seconds
* Increased Keego ore generation
* Reduced Blasting effectiveness and changed formula
* Fixed enchanting table learning
* Fixed enchantments changing attribute modifiers showing up on non-damageable items (e.g. enchanted books)
* Fixed non-damageable items getting a tag when Forged (e.g. Buckets wouldn't stack with used buckets)

## 1.9.4
* Exchange 
  * Now consumes blocks in the inventory first
  * Fixed the sound of block placing being heard only server side
* Curse of Fragility
  * Changed 2x durability consumed to +1 durability consumed
* Nerfed Zippy (+10% -> +7% movement speed per level)
* Soulbound can no longer be applied to bundles, crates and Quark's backpacks
* Fixed missing blood pact death message
* Fixed vindication working with ranged weapons

## 1.9.3
* When spawners end being empowered now drop an enchanted book with a curse on it
* Lodestone compasses can now be "reset" in a Grindstone
* Increased Piercing, Vigour and Soulbound cost
* Added infos to Powered Rails
* Fixed supplementaries urn loot table
* Fixed Burst of Arrows' arrows being pickupable
* Fixed Grindstone consuming multiple books (if more are placed in the slot the recipe will not work)

## 1.9.2
* Enchanted Cleansed Lapis has been buffed
  * Now gives +5 base table enchantability (so not affected by tools' status (empowered/infused))
  * Redeuced bonus enchantability (+40% -> +10%)
* Reworked Blood Pact
  * No longer makes the tool indestructible, instead makes the tool have only 1 in 8 chance to lose duratility, when it does, you're damaged too
* Emi now shows infos and craftings for Cleansed Lapis
* Solarium
  * Increased efficiency (2 -> 4.5)
  * Decreased Bonus Mining Speed under sun (up to +~~100%~~->+50% speed)
* Increased Quaron enchantability by 1
* Increased Durium (2.5 -> 3), Quaron (2.5 -> 3) and Soul Steel (3.5 -> 4) efficiency
* Items can no longer be repaired with nuggets (check ITR for more info)
* Fixed non-enchantable items (e.g. Books) showing enchantability

## 1.9.1
* Supplementaries urns can now be empty
* Fixed supplementaries urn rare loot table broken
* Fixed cleansed lapis drop chance being too high

## 1.9.0
* Added Ice Arrow
  * Applies freeze to hit entities
* Added Armor Piercer enchantment
  * Increases tool's piercing damage
* Added Steady Fall enchantment
  * New Treasure enchantment that negates fall damage but redirects twice of it to the boots
* Added Exchange Enchantment
  * A new enchantment that replaces blocks mined with the ones in the offhand
* Removed Gravity Defying
  * Was too strong, and nerfing it to the ground would make it useless on its own and too strong with M.A. Jump
* Added an actual Enchanted Cleansed Lapis item
  * I have no idea why I wanted to get myself into trouble by using an "enchanted" cleansed lapis
  * Also, Ancient Lapis should now be transformed to Enchanted Cleansed Lapis
* Expanded and Veining now have a new event called when breaking blocks
  * This makes Smartness and Curse of Dumbness work with blocks broken from the enchantments
* Refactored enchanting feature tooltip
  * It was becoming spaghetti and there were untranslatable strings
  * Also fixed Base Enchantability showing on non-enchantable items
  * Also base enchantability and empowered/infused are now visible outside the enchanting screen with shift
* Mob's equipment
  * Skeletons and zombies items' chance to be enchanted has been reduced (40% -> 30%)
  * Diamond armor chance has been halved
  * Fixed zombies having way too high chance to have diamond weapons
* Soulbound can now be applied to Recovery compasses, Compasses, Bundles, Crates and Quark's Backpack
* Increased cleansed lapis drop rate
* Reduced Depth Strider and Megnetic cost
* Slightly increased Diamond Arrows damage and slightly decreased torch arrows damage
* Fixed enchantments cost when enchanting with levels
  * This should make different enchantments show up in treasure fishing loot or on mobs
* Fixed Curses not being appliable to enchanted items

## 1.8.7
* Fixed Gravity Defying applying fall damage reduction even without the enchantment 
* Fixed sawmill advancement ... again

## 1.8.6
* Changed how mobs spawn with equipment
  * Armor: Zombies and Skeletons have 15% chance (more with Full Moon) to spawn with an armor set.  
    Each armor piece has 50% chance (more in Hard) to actually be equipped.  
    Then the equipment has 40% chance (more with Full Moon) to be enchanted with 5 to 15 levels (5~22 in hard).
  * Weapons: Zombies have 15% chance (more with Full Moon) to spawn with a weapon (pickaxe, axe, shovel, hoe and sword)  
    Then the equipment has 40% chance (more with Full Moon) to be enchanted with 5 to 15 levels (5~22 in hard).
* Nerfed soul steel armor

## 1.8.5
* Fixed charcoal layer having a wrong id

## 1.8.4
* Hotfix startup crash

## 1.8.3
* Updated advancements
* Bees are now smaller
* Gravity defying
  * Reduced fall distance reduction (1.5 -> 0.5)
  * Reduced gravity reduction
* If Coal Charcoal feature is enabled, 'Unlit campfire' from IguanaTweaks Reborn is set to true

## 1.8.2
* Reach can now be applied to Chestplates
* Gravity defying
  * Max level is now I and reduces gravity by 55%
  * Cost has been increased to 5
* Horse armor can now be recycled
* Solarium no longer increases attack speed
* Increased Gravity Defying and M.A. Jump cost
* Soul Steel now requires Netherite Scrap to be made
  * Can also be made in a crafting table like Netherite
  * With this, Netherite Scrap has been renamed to Ancient Scrap
* Like the base mod, materials now have a percentage increase/decrease in attack damage and piercing pickaxes
* Fixed missing Quaron Nugget recipe
* Fixed enchanting table screen having no background
* Fixed Bundle not showing in EMI

## 1.8.1
* Blood pact
  * Now works on any destructible item
  * The item becomes indestructible at the cost that the player might get damaged
* Split Pehkui changes in the mobs_buffs data pack from other MPR changes
  * This way, if you remove Pehkui, MPR stuff will still work
* Pending enchantments and enchantments in the Enchanting Screen now show the enchantment info
* Anvils and Enchanting Tables Screens now show item's enchantability
* Reduced chance for Netherized stew to poison
* Reduced horses bonus speed (+15% -> +5%)

## 1.8.0
* New enchantments!
  * Air Born: removes the mining penalty when not on ground 
  * Blood pact: mending but the durability bar is your health (Treasure)
  * Soulbound: items are kept in the inventory on death (Treasure)
  * Adrenaline: mining and attack speed increases as the durability decreases
  * Air Stealer: attacking steals air from an entity
* Added Netherized Stew
* Removed Ancient lapis
  * The mechanic has been moved to enchanted Cleansed Lapis, made with a Cleansed Lapis and an Experience bottle in an Anvil
* Forge Hammers
  * Movement Speed -15% -> -10%
  * Entity Reach 1.5 -> 2
* Coated Copper
  * Halved electrocution damage dealt with attacks
  * Increased shield lifted time
* Increased durium scrap from smelting ore
* Fixed "data pack" error when Allurement isn't present

## 1.7.1
* Fixed starting season config setting the previous subseason
* Fixed wrong enchantability on some materials

## 1.7.0 - The "The recipe book sucks" update
* Emi support!
  * Forging, Blasting and Soul Blasting, Fletching
  * A few minor things like some infos on some items
* Treasure and Curses enchantments can now be extracted from items with a grindstone
  * Just place the enchanted item and a book in the Grindstone
* Blast furnaces
  * Smelting ore blocks now takes less time
  * Added missing ore blocks from smelting.  
    The amount of output depends on the ore, so e.g. Lapis ores give 14 lapis (Also copper ore output has been increased)  
    Some might also be better than using fortune
  * Recycling is now faster in Blast Furnaces instead of Soul
  * Fixed a glitch where you could double the output from Blast furnaces just by using <REDACTED>
* Respawn Obelisk
  * Applies Regeneration, Absorption and Speed to players respawning on it
  * Now accepts precious blocks even 3 and 2 blocks away
* Keego
  * Heavily reduced efficiency and attack speed of keego tools, but increased momentum strength
    * Efficiency: 6.5 -> 3.5
    * Attack Speed modifier: -5% -> -20%
    * Attack Momentum: 5% -> 6% per level
    * Mining Momentum: 5% -> 10% per level
  * Movement Momentum now stacks faster the more pieces you have instead of having a cap on the effect level
  * Increased movement momentum bonus speed (+5% -> +6% per level)
  * Armor speed penalty has been increased (10% -> 20%)
* Recall potion is now made from a Withering Regeneration potion and echo shard
  * Regeneration II potion + Wither Rose = Withering Regenerating
  * Withering Regeneration + Echo shard = Recall potion
* Added allurement integration!
  * Launch enchantment now works on any weapon
* Over easy egg recipe no longer loads if Abnormals' Incubation is present
* Reduced burst of arrows number of arrows (8 -> 6)
* Some features can no longer be disabled (as had no effect)
* Added a starting season config option

## 1.6.8
* Respawn obelisks can now be disabled by removing all the catalysts and right-clicking on them
  * Hopefully fixed respawn obelisk not setting the old bed spawn
* Keego ore now drops 3-5 keego
* Recall potions can no longer be "extended"
* Reduced experience from grindstones (95% -> 80%)
* Zombified piglins can now be miners
* Endermen now attack much slower

## 1.6.7
* Skeletons now have a lower chance to spawn with stick or string 
* Solium Boulders are now rarer
* Tall grass now decays more often in cold seasons
  * Also fixed tall ferns not decaying
* Changed crafting recipes for tools and armor
  * Gold items are now made from Copper, armor from chained copper
  * Solarium from Wood / leather in Forge
  * Durium from stone / chained copper in Forge
  * Keego from Flint / Chainmail
  * Quaron from Wood / leather in Forge
  * Soul Steel smithen from Quaron
* Rebalanced materials, again
* Increased mobs movement speed
  * Also the movement speed is no longer difficulty dependant
* Empowered items (with ancient lapis) now have 40% bonus enchantability instead of 25%
* Soul speed and Luck enchantments now costs less
* Integrated some loot in supplementaries urns
* Moved Chained Copper to Copper Expansion (aka Copper Tools Expansion)
* Fixed m.a. jump effect (particles and sounds) not playing server side
* Quark's foxhounds are now affected by Water Coolant

## 1.6.6
* Hammers no longer have a "damage on use" attribute, instead they always use 1 durability but have 1/3 of normal tools
* Curse of ender now increases enchantability more
* Reduced Durium Ore generation
* Rebalanced some materials
* Reduced explosive arrow explosion power
* Altimeters now have a delta of 16 not 32
* Added resource pack to replace some vanilla textures
* Fixed being able to smith enchanted items
* Fixed Bonemealing grown crops

## 1.6.5
* Altimeter can now be right-clicked to show the same message as the tooltip
* Quaron fishing rods are slightly less slow to lure
* Fixed poor and rich ores not being doubled in blast furnaces
* Blast furnaces now try to pick up an item every 5 seconds if no fuel or empty
* Rebalanced armors to match ITR changes

## 1.6.4
* Copper tools
  * The bonus now increases exponentially instead of linearly. At the first blocks below the sea will gain the most benefit
  * No longer have bonus speed and durability outside the overworld
* Fixed enchanting costs not syncing with clients

## 1.6.3
* Fixed server crash when an entity fell
* Fixed pumpkins growing in Spring instead of Autumn

## 1.6.2
* Blast furnaces now try to pick up an item every 5 seconds if empty
* Smartness now works on Fishing Rods
  * Also Curse of Dumbness
* Enchanting
  * Lapis cost is now equal to the sum of the levels of enchantments
  * Experience and lapis are no longer consumed in creative when enchanting
  * Cost is now rounded to the nearest integer
* Enchantments now use item tags to decide on which item they can go
  * This fixes Forge Hammers missing some recipes
* Rage now scales slightly better and also has min 20% damage
* Fixed enchanting table learning curses
* Fixed gravity defying not reducing fall damage on servers
  * Also reduced gravity defying fall damage reduction from 4 per level to 2.5

## 1.6.1
* Fixed crash when shifting on some enchantments

## 1.6.0
* Curses!
  * Items with curses only can now be enchanted and get a boost to max cost, based off the curses
  * Also, items with curses only no longer show the enchanted foil
  * Curse of Experience. Consumes experience when used
  * Curse of Tear. Items break when experience is picked up
  * Curse of Unhurry. Movement slowdown
  * Curse of Slow Strike. Attack slowdown
  * Curse of Inefficiency. Efficiency opposite
  * Curse of Short Arm. Range reduction
  * Curse of Fragility. Unbreaking opposite
  * Curse of Ender. Randomly teleport when hit
  * Curse of Steel Fall. Increase fall damage
  * Curse of the Void. Chance to void drops
  * Curse of Dumbness. Experience dropped reduced
* Added padding enchantment for weapons
  * Reduces knockback
* Added rage enchantment for weapons
  * Increases damage based off user missing health
* Added Part Breaker enchantment for weapons
  * Gives a chance for additional drops from mobs, e.g. heads, tnt, shulker
* Added Burst of Arrows enchantment for Crossbow
* Heavily reduced hammers range and slightly attack speed. Also they now give -15% speed
* Rebalanced shields
  * They were pretty bad
* Reduced Quaron durability
* Removed Hard mode
* Mining charge crafting is now cheaper
* All items in creative tabs are now sorted!
* Fixed beeg ore veins generating less at deepslate level
* Fixed gravity defying with slow falling
* Fixed Wither Skeletons getting strings and sticks

## 1.5.1
* Fixed Serene Seasons crash

## 1.5.0
* Reduced Swift Strike bonus attack speed (+15% -> +10% per level)
* Enchanting table 
  * Enchantments are now sorted by registry name
  * Lapis cost has been increased to 1 every 2 levels spent instead of 3
  * Level cost has been rebalanced, costs slight less for lower levels and slightly more for higher levels
  * Enchantments base cost rebalanced
  * Increased base max cost from 3 to 4
* Rotten flesh is now replaced automatically in loot with Cloth instead of being removed
* Removed Multipurpose
* Quaron Fishing Rod
  * No longer takes more than 1 damage when used (e.g. on entities or items) (basically what Multipurpose did)
  * Slightly less durability
* Changed again Quaron recipe
* Crate has been renamed to Portable Crate
  * To have less confusion with Quark's storage crates
* Fixed Vindication working with bows
* Fixed serene seasons leaving behind ice
  * This fix is already in newer versions but will not be backported

## 1.4.3
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