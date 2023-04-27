package insane96mcp.survivalreimagined.module.combat.feature;

import insane96mcp.insanelib.base.Label;
import insane96mcp.insanelib.base.Module;
import insane96mcp.insanelib.base.config.Config;
import insane96mcp.insanelib.base.config.LoadFeature;
import insane96mcp.insanelib.util.IdTagMatcher;
import insane96mcp.survivalreimagined.base.SRFeature;
import insane96mcp.survivalreimagined.module.Modules;
import insane96mcp.survivalreimagined.module.combat.data.ItemAttributeModifier;
import insane96mcp.survivalreimagined.network.message.JsonConfigSyncMessage;
import insane96mcp.survivalreimagined.setup.Strings;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Label(name = "Stats", description = "Various changes from weapons damage to armor reduction. Item modifiers are controlled via json in this feature's folder")
@LoadFeature(module = Modules.Ids.COMBAT)
public class Stats extends SRFeature {
	static final List<ItemAttributeModifier> CLASS_ATTRIBUTE_MODIFIER = new ArrayList<>();

	public static final ArrayList<ItemAttributeModifier> ITEM_MODIFIERS_DEFAULT = new ArrayList<>(Arrays.asList(
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:netherite_helmet", UUID.fromString("da2b677f-467c-49b1-bdf0-070ee782bc0f"), EquipmentSlot.HEAD, Attributes.ARMOR, 1.0d, AttributeModifier.Operation.ADDITION),
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:netherite_boots", UUID.fromString("796d3f0c-89e4-47e8-9f95-3a3d5506f70f"), EquipmentSlot.FEET, Attributes.ARMOR, 1.0d, AttributeModifier.Operation.ADDITION),

			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:golden_sword", UUID.fromString("294e0db0-1185-4d78-b95e-8823b8bb0041"), EquipmentSlot.MAINHAND, Attributes.ATTACK_SPEED, .8d, AttributeModifier.Operation.ADDITION)/*,

			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:golden_helmet", UUID.fromString("e53774f5-c002-4bb3-b81d-4b15e01630d7"), EquipmentSlot.HEAD, Attributes.MAX_HEALTH, 2d, AttributeModifier.Operation.ADDITION),
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:golden_chestplate", UUID.fromString("bb234f07-2feb-4654-9d8c-6249631c1e26"), EquipmentSlot.CHEST, Attributes.MAX_HEALTH, 2d, AttributeModifier.Operation.ADDITION),
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:golden_leggings", UUID.fromString("985459d7-a2ab-412d-930a-38404d827ccc"), EquipmentSlot.LEGS, Attributes.MAX_HEALTH, 2d, AttributeModifier.Operation.ADDITION),
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "minecraft:golden_boots", UUID.fromString("534f3156-2caa-4638-a9e3-c13e8f56de73"), EquipmentSlot.FEET, Attributes.MAX_HEALTH, 2d, AttributeModifier.Operation.ADDITION),
			new ItemAttributeModifier(IdTagMatcher.Type.ID, "shieldsplus:golden_shield", UUID.fromString("964091e7-f808-4a31-9ce1-d79fa1896346"), EquipmentSlot.OFFHAND, Attributes.MAX_HEALTH, 2d, AttributeModifier.Operation.ADDITION)*/
	));
	public static final ArrayList<ItemAttributeModifier> itemModifiers = new ArrayList<>();

	@Config
	@Label(name = "Adjust weapons", description = "If true, Swords, Tridents and Axes get -1 damage, Axes get -0.5 attack reach and Tridents get +0.5 attack reach.")
	public static Boolean adjustWeapons = true;
	@Config
	@Label(name = "Disable Crit Arrows bonus damage", description = "If true, Arrows from Bows and Crossbows will no longer deal more damage when fully charged.")
	public static Boolean disableCritArrowsBonusDamage = true;
	@Config
	@Label(name = "Arrows don't trigger invincibility frames", description = "If true, Arrows will no longer trigger the invincibility frames (like Combat Test Snapshots).")
	public static Boolean arrowsNoInvincFrames = true;

	public Stats(Module module, boolean enabledByDefault, boolean canBeDisabled) {
		super(module, enabledByDefault, canBeDisabled);
		JSON_CONFIGS.add(new JsonConfig<>("item_modifiers.json", itemModifiers, ITEM_MODIFIERS_DEFAULT, ItemAttributeModifier.LIST_TYPE, true, JsonConfigSyncMessage.ConfigType.ITEM_ATTRIBUTE_MODIFIERS));
	}

	@Override
	public void readConfig(final ModConfigEvent event) {
		super.readConfig(event);
		CLASS_ATTRIBUTE_MODIFIER.clear();
		if (adjustWeapons) {
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(SwordItem.class, UUID.fromString("55c71d5e-fc26-418a-b531-d50c66bfb589"), EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE, -1d, AttributeModifier.Operation.ADDITION));
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(AxeItem.class, UUID.fromString("324a87bd-89ea-4d1b-866a-ce49d360d632"), EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE, -1d, AttributeModifier.Operation.ADDITION));
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(AxeItem.class, UUID.fromString("a60ab219-6c3a-453b-a55c-41e9c83f9c0f"), EquipmentSlot.MAINHAND, ForgeMod.ENTITY_REACH.get(), -0.5d, AttributeModifier.Operation.ADDITION));
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(TridentItem.class, UUID.fromString("f98cb9bc-3fa7-4fb5-b07a-babe8e35f967"), EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE, -1d, AttributeModifier.Operation.ADDITION));
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(TridentItem.class, UUID.fromString("50850a15-845a-4923-972b-f6cd1c16a7d3"), EquipmentSlot.MAINHAND, ForgeMod.ENTITY_REACH.get(), 0.5d, AttributeModifier.Operation.ADDITION));
		}
	}

	@Override
	public void loadJsonConfigs() {
		if (!this.isEnabled())
			return;
		super.loadJsonConfigs();
	}

	public static void handleItemAttributeModifiersPacket(String json) {
		loadAndReadJson(json, itemModifiers, ITEM_MODIFIERS_DEFAULT, ItemAttributeModifier.LIST_TYPE);
	}

	public static void addClassItemAttributeModifier(Class<? extends Item> itemClass, UUID uuid, EquipmentSlot slot, Attribute attribute, double amount, AttributeModifier.Operation operation) {
		synchronized (CLASS_ATTRIBUTE_MODIFIER) {
			CLASS_ATTRIBUTE_MODIFIER.add(new ItemAttributeModifier(itemClass, uuid, slot, attribute, amount, operation));
		}
	}

	public static void removeClassItemAttributeModifier(Class<? extends Item> itemClass) {
		synchronized (CLASS_ATTRIBUTE_MODIFIER) {
			CLASS_ATTRIBUTE_MODIFIER.removeIf(iam -> iam.itemClass.equals(itemClass));
		}
	}

	@SubscribeEvent
	public void onAttributeEvent(ItemAttributeModifierEvent event) {
		if (!this.isEnabled())
			return;

		classAttributeModifiers(event);
		itemAttributeModifiers(event);
	}

	private void classAttributeModifiers(ItemAttributeModifierEvent event) {
		for (ItemAttributeModifier itemAttributeModifier : CLASS_ATTRIBUTE_MODIFIER) {
			if (itemAttributeModifier.itemClass.equals(event.getItemStack().getItem().getClass()) && itemAttributeModifier.slot.equals(event.getSlotType())) {
				AttributeModifier modifier = new AttributeModifier(itemAttributeModifier.uuid, Strings.AttributeModifiers.GENERIC_ITEM_MODIFIER, itemAttributeModifier.amount, itemAttributeModifier.operation);
				event.addModifier(itemAttributeModifier.attribute, modifier);
			}
		}
	}

	private void itemAttributeModifiers(ItemAttributeModifierEvent event) {
		for (ItemAttributeModifier itemAttributeModifier : itemModifiers) {
			if (!itemAttributeModifier.matches(event.getItemStack().getItem()))
				continue;
			if (event.getSlotType() != itemAttributeModifier.slot)
				continue;

			AttributeModifier modifier = new AttributeModifier(itemAttributeModifier.uuid, Strings.AttributeModifiers.GENERIC_ITEM_MODIFIER, itemAttributeModifier.amount, itemAttributeModifier.operation);
			event.addModifier(itemAttributeModifier.attribute, modifier);
		}
	}

	public static boolean disableArrowInvFrames() {
		return isEnabled(Stats.class) && arrowsNoInvincFrames;
	}
}