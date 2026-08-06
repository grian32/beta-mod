package me.grian.griansbetamod

import me.grian.griansbetamod.api.craftingrecipes.addShapedRecipe
import me.grian.griansbetamod.api.craftingrecipes.addShapelessRecipe
import me.grian.griansbetamod.api.craftingrecipes.addSmeltingRecipe
import me.grian.griansbetamod.blocks.*
import me.grian.griansbetamod.climate.BluePeonyBlock
import me.grian.griansbetamod.compat.itemenhancements.EnhancementAMIBlock
import me.grian.griansbetamod.compat.itemenhancements.EnhancementAMIItem
import me.grian.griansbetamod.hell.limbo.DeadGrassBlock
import me.grian.griansbetamod.hell.limbo.DeadGrassPlantBlock
import me.grian.griansbetamod.hell.limbo.DeadPlankSlabBlock
import me.grian.griansbetamod.icydungeons.FrostRootCropBlock
import me.grian.griansbetamod.icydungeons.FrostRootItem
import me.grian.griansbetamod.icydungeons.FrostRootSeedsItem
import me.grian.griansbetamod.icydungeons.IcyCobblestoneBlock
import me.grian.griansbetamod.icydungeons.IcyStoneBlock
import me.grian.griansbetamod.itemenhancements.Enhancement
import me.grian.griansbetamod.itemenhancements.EnhancementTableBlock
import me.grian.griansbetamod.itemenhancements.resinharvest.ResinBlock
import me.grian.griansbetamod.items.GrassyBootsItem
import me.grian.griansbetamod.itemenhancements.resinharvest.ResinItem
import me.grian.griansbetamod.itemenhancements.resinharvest.TotemOfHealthItem
import me.grian.griansbetamod.itemenhancements.sifter.ScorchedBrickBlock
import me.grian.griansbetamod.itemenhancements.sifter.ScorchedClayBallItem
import me.grian.griansbetamod.itemenhancements.sifter.ScorchedClayBlock
import me.grian.griansbetamod.lilyofthelake.LilyOfTheLakeBlock
import me.grian.griansbetamod.moonwell.MoonwellBlock
import me.grian.griansbetamod.moonwell.MoonwellBlockEntity
import me.grian.griansbetamod.util.asItemStack
import me.grian.griansbetamod.util.isEventTypeShaped
import me.grian.griansbetamod.util.isEventTypeShapeless
import me.grian.griansbetamod.util.isEventTypeSmelting
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.modificationstation.stationapi.api.datafixer.DataFixers
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent
import net.modificationstation.stationapi.api.event.datafixer.DataFixerRegisterEvent
import net.modificationstation.stationapi.api.event.mod.InitEvent
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent
import net.modificationstation.stationapi.api.template.block.TemplateBlock
import net.modificationstation.stationapi.api.template.block.TemplateStairsBlock
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem
import net.modificationstation.stationapi.api.template.item.TemplateItem
import net.modificationstation.stationapi.api.util.Namespace
import org.apache.logging.log4j.Logger
import java.util.Properties

object BetaMod {
    val NAMESPACE: Namespace = Namespace.of("griansbetamod")

    private val LOGGER: Logger = NAMESPACE.logger

    lateinit var redstoneBlock: Block

    lateinit var sawmill: Block

    lateinit var netherGlass: Block

    lateinit var enhancementTable: Block
    lateinit var pileOfLogs: Block
    lateinit var resin: Item
    lateinit var resinBlock: Block
    lateinit var totemOfHealth: Item

    lateinit var grassyBoots: Item

    lateinit var lilyOfTheLake: Block

    lateinit var bluePeony: Block

    lateinit var icyStone: Block
    lateinit var icyCobblestone: Block
    lateinit var frostRoot: Item
    lateinit var cookedFrostRoot: Item
    lateinit var frostRootSeeds: Item
    lateinit var frostRootCrop: Block

    lateinit var scorchedClayBall: Item
    lateinit var scorchedClayBlock: Block
    lateinit var scorchedBrick: Item
    lateinit var scorchedBricks: Block

    lateinit var stoneBricks: Block

    lateinit var shrineCenter: Block
    lateinit var goldStone: Block

    lateinit var moonwell: Block

    lateinit var cloth: Item

    lateinit var deadDirt: Block
    lateinit var deadGrass: Block
    lateinit var deadLog: Block
    lateinit var deadGrassPlant: Block
    lateinit var deadPlank: Block
    lateinit var deadPlankSlab: Block
    lateinit var deadPlankDoubleSlab: Block
    lateinit var deadPlankStair: Block

    var blocksInitialized = false

    lateinit var extraLogsAMIItem: EnhancementAMIBlock
    lateinit var resinHarvestAMIItem: EnhancementAMIItem
    lateinit var reinforcedAMIItem: EnhancementAMIItem
    lateinit var steadyHandAMIItem: EnhancementAMIItem
    lateinit var replanterAMIItem: EnhancementAMIItem
    lateinit var landscaperAMIItem: EnhancementAMIBlock
    lateinit var bountifulAMIItem: EnhancementAMIItem
    lateinit var sifterAMIItem: EnhancementAMIItem
    lateinit var quarrymanAMIItem: EnhancementAMIItem

    @JvmStatic
    var versionString: String = "unknown"

    @EventListener
    fun init(event: InitEvent) {
        val props = Properties();
        val input = this.javaClass.classLoader.getResourceAsStream("version.properties")
        props.load(input)
        versionString = props.getProperty("version", "unknown")
    }

    @EventListener
    fun registerBlocks(event: BlockRegistryEvent) {
        redstoneBlock = RedstoneBlock(NAMESPACE.id("redstone_block"))
            .setTranslationKey(NAMESPACE, "redstone_block")
            .setSoundGroup(Block.METAL_SOUND_GROUP)
            .setResistance(6.0f)
            .setHardness(5.0f)

        sawmill = SawmillBlock(NAMESPACE.id("sawmill"))
            .setTranslationKey(NAMESPACE, "sawmill")
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setHardness(3.5f)

        netherGlass = NetherGlassBlock(NAMESPACE.id("nether_glass"))
            .setTranslationKey(NAMESPACE, "nether_glass")
            .setSoundGroup(Block.GLASS_SOUND_GROUP)
            .setHardness(0.3f)

        enhancementTable = EnhancementTableBlock(NAMESPACE.id("enhancement_table"))
            .setTranslationKey(NAMESPACE, "enhancement_table")
            .setSoundGroup(Block.STONE_SOUND_GROUP)
            .setHardness(5.0f)

        pileOfLogs = PileOfLogsBlock(NAMESPACE.id("pile_of_logs"))
            .setTranslationKey(NAMESPACE, "pile_of_logs")
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setHardness(2.0f)

        resinBlock = ResinBlock(NAMESPACE.id("resin_block"))
            .setTranslationKey(NAMESPACE, "resin_block")
            .setSoundGroup(Block.DIRT_SOUND_GROUP)
            .setHardness(2.0f)

        lilyOfTheLake = LilyOfTheLakeBlock(NAMESPACE.id("lily_of_the_lake"))
            .setTranslationKey(NAMESPACE, "lily_of_the_lake")
            .setHardness(0.0F)
            .setSoundGroup(Block.DIRT_SOUND_GROUP)
            .setLuminance(0.695f)

        bluePeony = BluePeonyBlock(NAMESPACE.id("blue_peony"))
            .setTranslationKey(NAMESPACE, "blue_peony")
            .setHardness(0F)
            .setSoundGroup(Block.DIRT_SOUND_GROUP)

        icyStone = IcyStoneBlock(NAMESPACE.id("icy_stone"))
            .setTranslationKey(NAMESPACE, "icy_stone")
            .setHardness(1.5F)
            .setResistance(10.0F)
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        icyCobblestone = IcyCobblestoneBlock(NAMESPACE.id("icy_cobblestone"))
            .setTranslationKey(NAMESPACE, "icy_cobblestone")
            .setHardness(2.0F)
            .setResistance(10.0F)
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        frostRootCrop = FrostRootCropBlock(NAMESPACE.id("frost_root_crop"))
            .setTranslationKey(NAMESPACE, "frost_root_crop")
            .setHardness(0.0F)
            .setSoundGroup(Block.DIRT_SOUND_GROUP)

        scorchedBricks = ScorchedBrickBlock(NAMESPACE.id("scorched_bricks"))
            .setTranslationKey(NAMESPACE, "scorched_bricks")
            .setHardness(2.0F)
            .setResistance(10.0F)
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        scorchedClayBlock = ScorchedClayBlock(NAMESPACE.id("scorched_clay_block"))
            .setTranslationKey(NAMESPACE, "scorched_clay_block")
            .setHardness(0.6F)
            .setSoundGroup(Block.GRAVEL_SOUND_GROUP)

        stoneBricks = StoneBricksBlock(NAMESPACE.id("stone_bricks"))
            .setTranslationKey(NAMESPACE, "stone_bricks")
            .setHardness(1.5F)
            .setResistance(10.0F)
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        shrineCenter = TemplateBlock(NAMESPACE.id("shrine_center"), Material.STONE)
            .setUnbreakable()
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        goldStone = TemplateBlock(NAMESPACE.id("gold_stone"), Material.STONE)
            .setUnbreakable()
            .setSoundGroup(Block.STONE_SOUND_GROUP)

        moonwell = MoonwellBlock(NAMESPACE.id("moonwell"))
            .setTranslationKey(NAMESPACE, "moonwell")
            .setSoundGroup(Block.STONE_SOUND_GROUP)
            .setHardness(5.0f)

        deadGrass = DeadGrassBlock(NAMESPACE.id("dead_grass"))
            .setHardness(0.6F)
            .setSoundGroup(Block.DIRT_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_grass")

        deadDirt = TemplateBlock(NAMESPACE.id("dead_dirt"), Material.SOIL)
            .setHardness(0.5F)
            .setSoundGroup(Block.GRAVEL_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_dirt")

        deadLog = TemplateBlock(NAMESPACE.id("dead_log"), Material.WOOD)
            .setHardness(2.0F)
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setTranslationKey("dead_log")

        deadGrassPlant = DeadGrassPlantBlock(NAMESPACE.id("dead_grass_plant"))
            .setHardness(0.0F)
            .setSoundGroup(Block.DIRT_SOUND_GROUP)
            .setTranslationKey("dead_grass_plant")

        deadPlank = TemplateBlock(NAMESPACE.id("dead_plank"), Material.WOOD)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_plank")

        deadPlankSlab = DeadPlankSlabBlock(NAMESPACE.id("dead_plank_slab"), false)
            .setOpacity(0)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_plank_slab")

        deadPlankDoubleSlab = DeadPlankSlabBlock(NAMESPACE.id("dead_plank_double_slab"), true)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_plank_double_slab")
        deadPlankDoubleSlab.disableAutoItemRegistration()

        deadPlankStair = TemplateStairsBlock(NAMESPACE.id("dead_plank_stairs"), deadPlank)
            .setOpacity(0)
            .setHardness(2.0F)
            .setResistance(5.0F)
            .setSoundGroup(Block.WOOD_SOUND_GROUP)
            .setTranslationKey(NAMESPACE, "dead_plank_stairs")

        blocksInitialized = true
    }

    @EventListener
    fun registerBlockEntitys(event: BlockEntityRegisterEvent) {
        event.register(BetaMod.NAMESPACE.id("moonwell"), MoonwellBlockEntity::class.java)
    }

    @EventListener
    fun registerItems(event: ItemRegistryEvent) {
        grassyBoots = GrassyBootsItem(NAMESPACE.id("grassy_boots"))
            .setTranslationKey(NAMESPACE, "grassy_boots")
            .setTexturePosition(0, 3)

        resin = ResinItem(NAMESPACE.id("resin"))
            .setTranslationKey(NAMESPACE, "resin")

        totemOfHealth = TotemOfHealthItem(NAMESPACE.id("totem_of_health"))
            .setTranslationKey(NAMESPACE, "totem_of_health")
            .setMaxCount(1)

        frostRootSeeds = FrostRootSeedsItem(NAMESPACE.id("frost_root_seeds"))
            .setTranslationKey(NAMESPACE, "frost_root_seeds")

        frostRoot = FrostRootItem(NAMESPACE.id("frost_root"))
            .setTranslationKey(NAMESPACE, "frost_root")

        cookedFrostRoot = TemplateFoodItem(NAMESPACE.id("cooked_frost_root"), 4, false)
            .setTranslationKey(NAMESPACE, "cooked_frost_root")
            .setMaxCount(1)

        scorchedClayBall = ScorchedClayBallItem(NAMESPACE.id("scorched_clay_ball"))
            .setTranslationKey(NAMESPACE, "scorched_clay_ball")

        scorchedBrick = ScorchedClayBallItem(NAMESPACE.id("scorched_brick"))
            .setTranslationKey(NAMESPACE, "scorched_brick")

        cloth = TemplateItem(NAMESPACE.id("tattered_cloth"))
            .setTranslationKey(NAMESPACE, "tattered_cloth")

        extraLogsAMIItem = EnhancementAMIBlock(NAMESPACE.id("extra_logs_ami"), "extra_logs", 4, Enhancement.EXTRA_LOGS)
        resinHarvestAMIItem = EnhancementAMIItem(NAMESPACE.id("resin_harvest_ami"), "resin_harvest", 3, Enhancement.RESIN)
        reinforcedAMIItem = EnhancementAMIItem(NAMESPACE.id("reinforced_ami"), "reinforced", 4, Enhancement.AXE_UNBREAKING)
        steadyHandAMIItem = EnhancementAMIItem(NAMESPACE.id("steady_hand_ami"), "steady_hand", 3, Enhancement.STEADY_HAND)
        replanterAMIItem = EnhancementAMIItem(NAMESPACE.id("replanter_ami"), "replanter", 1, Enhancement.REPLANTER)
        landscaperAMIItem = EnhancementAMIBlock(NAMESPACE.id("landscaper_ami"), "landscaper", 1, Enhancement.LANDSCAPER)
        bountifulAMIItem = EnhancementAMIItem(NAMESPACE.id("bountiful_ami"), "bountiful", 3, Enhancement.BOUNTIFUL)
        sifterAMIItem = EnhancementAMIItem(NAMESPACE.id("sifter_ami"), "sifter", 3, Enhancement.SIFTER)
        quarrymanAMIItem = EnhancementAMIItem(NAMESPACE.id("quarryman_ami"), "quarryman", 3, Enhancement.QUARRYMAN)
    }

    @EventListener
    fun registerRecipes(event: RecipeRegisterEvent) {
        val type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId)

        if (isEventTypeShaped(event.recipeId)) {
            addShapedRecipe {
                output(redstoneBlock.asItem())

                top(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
                middle(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
                bottom(Item.REDSTONE, Item.REDSTONE, Item.REDSTONE)
            }


            addShapedRecipe {
                output(grassyBoots)

                top(null, Item.WHEAT, null)
                middle(Item.SEEDS, Item.LEATHER_BOOTS, Item.SEEDS)
                bottom(null, Item.WHEAT, null)
            }

            addShapedRecipe {
                output(sawmill)

                top(null, Item.IRON_INGOT, null)
                middle(Block.PLANKS.asItem(), Item.IRON_INGOT, Block.PLANKS.asItem())
                bottom(Block.STONE.asItem(), Item.IRON_INGOT, Block.STONE.asItem() )
            }

            // TODO: maybe move this to sep file, not 100% sure
            addShapedRecipe {
                output(pileOfLogs)

                val logStack = ItemStack(Block.LOG, 4, 0)

                top(logStack, logStack, null)
                middle(logStack, logStack, null)
            }

            addShapedRecipe {
                output(enhancementTable)

                val lightBlueDye = ItemStack(Item.DYE, 1, 12)

                top(lightBlueDye, ItemStack(Item.DIAMOND), lightBlueDye)
                middle(Block.STONE.asItemStack(), lightBlueDye, Block.STONE.asItemStack())
                bottom(Block.STONE, null, Block.STONE)
            }

            addShapedRecipe {
                output(resinBlock)

                top(resin, resin, null)
                middle(resin, resin, null)
            }

            addShapedRecipe {
                output(Block.STICKY_PISTON)

                top(null, resin, null)
                middle(null, Block.PISTON, null)
            }

            addShapedRecipe {
                output(totemOfHealth)

                top(Block.DIAMOND_BLOCK.asItem(), resin, Block.DIAMOND_BLOCK.asItem())
                middle(resin, resin, resin)
                bottom(null, resin, null)
            }

            addShapedRecipe {
                output(scorchedClayBlock)
                top(scorchedClayBall, scorchedClayBall, null)
                middle(scorchedClayBall, scorchedClayBall, null)
            }

            addShapedRecipe {
                output(scorchedBricks)
                top(scorchedBrick, scorchedBrick, null)
                middle(scorchedBrick, scorchedBrick, null)
            }

            addShapedRecipe {
                output(stoneBricks)
                top(Block.STONE, Block.STONE, null)
                middle(Block.STONE, Block.STONE, null)
            }

            addShapedRecipe {
                output(moonwell)
                top(ItemStack(Item.DIAMOND, 1, 0), ItemStack(Item.DYE, 1, 5), ItemStack(Item.DIAMOND, 1, 0))
                middle(stoneBricks, null, stoneBricks)
                bottom(stoneBricks, stoneBricks, stoneBricks)
            }

            addShapedRecipe {
                output(ItemStack(deadPlankSlab.asItem(), 3))
                top(deadPlank, deadPlank, deadPlank)
            }

            addShapedRecipe {
                output(ItemStack(deadPlankStair.asItem(), 4))
                top(deadPlank, null, null)
                middle(deadPlank, deadPlank, null)
                bottom(deadPlank, deadPlank, deadPlank)
            }

            addShapedRecipe {
                output(Item.PAINTING)
                top(Item.STICK, Item.STICK, Item.STICK)
                middle(Item.STICK, cloth, Item.STICK)
                bottom(Item.STICK, Item.STICK, Item.STICK)
            }
        }

        if (isEventTypeShapeless(event.recipeId)) {
            addShapelessRecipe {
                output(ItemStack(Item.LEATHER, 5))

                ingredient(Item.SADDLE)
            }

            addShapelessRecipe {
                output(ItemStack(Item.REDSTONE, 9))

                ingredient(redstoneBlock.asItem())
            }

            addShapelessRecipe {
                ingredient(cloth)
                output(ItemStack(Item.STRING, 2))
            }
            addShapelessRecipe {
                ingredient(deadLog)
                output(ItemStack(deadPlank, 2))
            }
        }

        if (isEventTypeSmelting(event.recipeId)) {
            addSmeltingRecipe {
                input(Block.SOUL_SAND)
                output(netherGlass)
            }

            addSmeltingRecipe {
                input(icyCobblestone)
                output(icyStone)
            }

            addSmeltingRecipe {
                input(frostRoot)
                output(cookedFrostRoot)
            }

            addSmeltingRecipe {
                input(scorchedClayBall)
                output(scorchedBrick)
            }
        }
    }

    @EventListener
    fun registerDataFixers(event: DataFixerRegisterEvent) {
        DataFixers.registerFixer(
            NAMESPACE,
            ::createBetaModDataFixer,
            CURRENT_DF_VERSION
        )
    }
}
