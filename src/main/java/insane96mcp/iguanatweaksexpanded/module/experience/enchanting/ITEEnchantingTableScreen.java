package insane96mcp.iguanatweaksexpanded.module.experience.enchanting;

import com.mojang.blaze3d.systems.RenderSystem;
import insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded;
import insane96mcp.iguanatweaksexpanded.network.message.SyncITEEnchantingTableEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static insane96mcp.iguanatweaksexpanded.IguanaTweaksExpanded.ONE_DECIMAL_FORMATTER;

public class ITEEnchantingTableScreen extends AbstractContainerScreen<ITEEnchantingTableMenu> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(IguanaTweaksExpanded.MOD_ID, "textures/gui/container/enchanting_table.png");
    private static final ResourceLocation ENCHANTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/enchanting_table.png");

    static final int BUTTON_X = 9;
    static final int BUTTON_Y = 37;
    static final int BUTTON_U = 0;
    static final int BUTTON_V = 166;
    static final int BUTTON_W = 36;
    static final int BUTTON_H = 16;

    static final int EXP_ORB_U = 0;
    static final int EXP_ORB_V = 212;
    static final int EXP_ORB_W = 10;
    static final int EXP_ORB_H = 10;

    static final int DOWN_BUTTON_U = 108;
    static final int DOWN_BUTTON_V = 166;
    static final int SCROLL_BUTTON_W = 12;
    static final int SCROLL_BUTTON_H = 7;

    static final int LIST_X = 52;
    static final int LIST_Y = 15;
    static final int ENCH_ENTRY_V = 184;
    static final int LVL_BTN_W = 9;
    static final int LOWER_LVL_BTN_U = 0;
    static final int ENCH_DISPLAY_U = LVL_BTN_W;
    static final int ENCH_DISPLAY_W = 81;
    static final int ENCH_LVL_U = LVL_BTN_W + ENCH_DISPLAY_W;
    static final int ENCH_LVL_W = 15;
    static final int RISE_LVL_BTN_U = LVL_BTN_W + ENCH_DISPLAY_W + ENCH_LVL_W;
    static final int ENCH_ENTRY_W = LVL_BTN_W + ENCH_DISPLAY_W + ENCH_LVL_W + LVL_BTN_W;
    static final int ENCH_ENTRY_H = 14;

    private List<EnchantmentEntry> enchantmentEntries = new ArrayList<>();
    private ScrollButton scrollUpBtn;
    private ScrollButton scrollDownBtn;
    private ItemStack lastStack;
    private int maxCost = 0;
    public List<Enchantment> unlockedEnchantments = new ArrayList<>();
    public boolean forceUpdateEnchantmentsList;

    private int scroll = 0;

    public ITEEnchantingTableScreen(ITEEnchantingTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.lastStack = ItemStack.EMPTY;
    }

    @Override
    protected void init() {
        super.init();
        int topLeftCornerX = (this.width - this.imageWidth) / 2;
        int topLeftCornerY = (this.height - this.imageHeight) / 2;

        this.maxCost = this.menu.maxCost.get();
        this.scrollUpBtn = new ScrollButton(topLeftCornerX + 158, topLeftCornerY + 5, SCROLL_BUTTON_W, SCROLL_BUTTON_H, ScrollButton.Type.UP);
        this.scrollUpBtn.active = false;
        this.scrollDownBtn = new ScrollButton(topLeftCornerX + 158, topLeftCornerY + 73, SCROLL_BUTTON_W, SCROLL_BUTTON_H, ScrollButton.Type.DOWN);
    }

    private void updatePossibleEnchantments() {
        ItemStack stack = this.menu.getSlot(0).getItem();
        this.maxCost = this.menu.maxCost.get();
        if ((ItemStack.isSameItem(stack, this.lastStack) || stack.isEnchanted()) && !this.forceUpdateEnchantmentsList)
            return;
        this.forceUpdateEnchantmentsList = false;
        this.lastStack = stack.copy();
        List<EnchantmentInstance> enchantments = new ArrayList<>();
        this.enchantmentEntries.clear();
        List<Enchantment> availableEnchantments = new ArrayList<>();
        if (stack.isEmpty())
            return;

        for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
            if ((!enchantment.isTreasureOnly() && enchantment.canApplyAtEnchantingTable(stack) && enchantment.isDiscoverable()) || (this.unlockedEnchantments.contains(enchantment) && enchantment.canApplyAtEnchantingTable(stack))) {
                availableEnchantments.add(enchantment);
            }
        }
        availableEnchantments.forEach(enchantment -> enchantments.add(new EnchantmentInstance(enchantment, 0)));
        int topLeftCornerX = (this.width - this.imageWidth) / 2;
        int topLeftCornerY = (this.height - this.imageHeight) / 2;
        for (int i = 0; i < enchantments.size(); i++) {
            EnchantmentInstance instance = enchantments.get(i);
            this.enchantmentEntries.add(new EnchantmentEntry(topLeftCornerX + LIST_X, topLeftCornerY + LIST_Y + (i * ENCH_ENTRY_H), instance.enchantment, instance.level));
        }

        if (stack.getTag() != null && stack.getTag().contains("PendingEnchantments")) {
            List<EnchantmentInstance> pendingEnchantments = new ArrayList<>();
            ListTag enchantmentsListTag = stack.getTag().getList("PendingEnchantments", CompoundTag.TAG_COMPOUND);
            for (int i = 0; i < enchantmentsListTag.size(); ++i) {
                CompoundTag compoundtag = enchantmentsListTag.getCompound(i);
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(compoundtag.getString("id")));
                if (enchantment != null) {
                    pendingEnchantments.add(new EnchantmentInstance(enchantment, compoundtag.getShort("lvl")));
                }
            }
            for (EnchantmentEntry enchantmentEntry : this.enchantmentEntries) {
                Optional<EnchantmentInstance> instanceOptional = pendingEnchantments.stream().filter(pendingEnchantment -> enchantmentEntry.enchantmentDisplay.enchantment.equals(pendingEnchantment.enchantment)).findAny();
                instanceOptional.ifPresent(pendingEnchantment -> {
                    enchantmentEntry.enchantmentDisplay.lvl = pendingEnchantment.level;
                    enchantmentEntry.updateActiveState();
                });
            }
        }
        this.scroll(-999);
    }

    private float getCurrentCost() {
        float cost = 0;
        for (EnchantmentEntry enchantmentEntry : this.enchantmentEntries) {
            int lvl = enchantmentEntry.enchantmentDisplay.lvl;
            if (lvl <= 0)
                continue;
            cost += EnchantingFeature.getCost(enchantmentEntry.enchantmentDisplay.enchantment, lvl);
        }
        return cost * 100f;
    }

    private boolean isItemEmpowered() {
        CompoundTag tag = this.menu.getSlot(0).getItem().getTag();
        return tag != null && tag.contains(EnchantingFeature.EMPOWERED_ITEM);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int topLeftCornerX = (this.width - this.imageWidth) / 2;
        int topLeftCornerY = (this.height - this.imageHeight) / 2;

        double x = pMouseX - (double)(topLeftCornerX + BUTTON_X);
        double y = pMouseY - (double)(topLeftCornerY + BUTTON_Y);
        if (this.isButtonEnabled() && x >= 0.0D && y >= 0.0D && x < BUTTON_W && y < BUTTON_H && this.menu.clickMenuButton(this.minecraft.player, 0)) {
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            this.enchantmentEntries.clear();
            this.maxCost = 0;
            return true;
        }
        for (EnchantmentEntry enchantmentEntry : this.enchantmentEntries) {
            enchantmentEntry.levelDownBtn.mouseClicked(pMouseX, pMouseY, pButton);
            enchantmentEntry.levelUpBtn.mouseClicked(pMouseX, pMouseY, pButton);
        }
        this.scrollUpBtn.mouseClicked(pMouseX, pMouseY, pButton);
        this.scrollDownBtn.mouseClicked(pMouseX, pMouseY, pButton);
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int topLeftCornerX = (this.width - this.imageWidth) / 2;
        int topLeftCornerY = (this.height - this.imageHeight) / 2;
        int x = pMouseX - (topLeftCornerX + BUTTON_X);
        int y = pMouseY - (topLeftCornerY + BUTTON_Y);
        pGuiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX, topLeftCornerY, 0, 0, this.imageWidth, this.imageHeight);
        if (!this.isButtonEnabled())
            pGuiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX + BUTTON_X, topLeftCornerY + BUTTON_Y, BUTTON_U + BUTTON_W, BUTTON_V, BUTTON_W, BUTTON_H);
        else if (x >= 0 && y >= 0 && x < BUTTON_W && y < BUTTON_H)
            pGuiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX + BUTTON_X, topLeftCornerY + BUTTON_Y, BUTTON_U + BUTTON_W * 2, BUTTON_V, BUTTON_W, BUTTON_H);
        else
            pGuiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX + BUTTON_X, topLeftCornerY + BUTTON_Y, BUTTON_U, BUTTON_V, BUTTON_W, BUTTON_H);
    }

    private boolean isButtonEnabled() {
        if (this.menu.getSlot(0).getItem().isEmpty()
                || this.menu.getSlot(0).getItem().isEnchanted())
            return false;
        if (!this.isItemEmpowered()) {
            for (EnchantmentEntry enchantmentEntry : this.enchantmentEntries) {
                if (enchantmentEntry.enchantmentDisplay.lvl > enchantmentEntry.enchantmentDisplay.enchantment.getMaxLevel())
                    return false;
            }
        }
        float cost = this.getCurrentCost();
        return ((this.minecraft.player.experienceLevel >= cost / 100f && cost <= this.maxCost) || this.minecraft.player.getAbilities().instabuild) && cost > 0;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int topLeftCornerX = (this.width - this.imageWidth) / 2;
        int topLeftCornerY = (this.height - this.imageHeight) / 2;
        updatePossibleEnchantments();
        List<EnchantmentEntry> entries = this.enchantmentEntries;
        for (int i = 0; i < entries.size(); i++) {
            EnchantmentEntry entry = entries.get(i);
            entry.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        if (this.maxCost > 0) {
            float cost = this.getCurrentCost();
            int color = cost > this.maxCost ? 0xFF0000 : 0x11FF11;
            guiGraphics.drawCenteredString(this.font, "Max: %s".formatted(ONE_DECIMAL_FORMATTER.format(this.maxCost / 100f)), topLeftCornerX + BUTTON_X + BUTTON_W / 2, topLeftCornerY + BUTTON_Y + BUTTON_H + 5, color);
            color = this.minecraft.player.experienceLevel < cost / 100f && !this.minecraft.player.isCreative() ? 0xFF0000 : 0x11FF11;
            if (this.isButtonEnabled())
                guiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX + BUTTON_X + 3, topLeftCornerY + BUTTON_Y + 3, EXP_ORB_U, EXP_ORB_V, EXP_ORB_W, EXP_ORB_H);
            else
                guiGraphics.blit(TEXTURE_LOCATION, topLeftCornerX + BUTTON_X + 3, topLeftCornerY + BUTTON_Y + 3, EXP_ORB_U + EXP_ORB_W, EXP_ORB_V, EXP_ORB_W, EXP_ORB_H);
            guiGraphics.drawCenteredString(this.font, "%s".formatted(ONE_DECIMAL_FORMATTER.format(this.getCurrentCost() / 100f)), topLeftCornerX + BUTTON_X + BUTTON_W / 2 + 6, topLeftCornerY + BUTTON_Y + BUTTON_H / 2 - (this.font.lineHeight / 2), color);
            /*if (cost > 0) {
                int lapisCost = (int)(cost / 5) + 1;
                guiGraphics.drawCenteredString(this.font, "%d".formatted(lapisCost), topLeftCornerX + (lapisCost < 10 ? 48 : 45), topLeftCornerY + 18, 0x495e9e);
            }*/
        }
        this.scrollUpBtn.render(guiGraphics, mouseX, mouseY, partialTick);
        this.scrollDownBtn.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        this.scroll(-(int) pDelta);
        return true;
    }

    public void scroll(int delta) {
        this.scroll = Mth.clamp(this.scroll + delta, 0, Math.max(this.enchantmentEntries.size() - 4, 0));
        this.updateEnchantmentEntryPositionAndVisibility();
        this.scrollUpBtn.active = this.scroll > 0 && enchantmentEntries.size() > 4;
        this.scrollDownBtn.active = this.scroll < enchantmentEntries.size() - 4 && enchantmentEntries.size() > 4;
    }

    private void updateEnchantmentEntryPositionAndVisibility() {
        int topLeftCornerY = (this.height - this.imageHeight) / 2;
        for (int i = 0; i < enchantmentEntries.size(); i++) {
            EnchantmentEntry enchantmentEntry = enchantmentEntries.get(i);
            enchantmentEntry.setY(topLeftCornerY + LIST_Y + ((i - this.scroll) * enchantmentEntry.getHeight()));
            enchantmentEntry.setActive(i >= this.scroll && i < this.scroll + 4);
        }
    }

    private class EnchantmentEntry extends AbstractWidget {
        public LevelBtn levelDownBtn;
        public EnchantmentDisplay enchantmentDisplay;
        public LevelBtn levelUpBtn;

        public EnchantmentEntry(int pX, int pY, Enchantment enchantment, int lvl) {
            super(pX, pY, ENCH_ENTRY_W, ENCH_ENTRY_H, Component.empty());
            this.enchantmentDisplay = new EnchantmentDisplay(pX + LVL_BTN_W, pY, enchantment, lvl);
            this.levelDownBtn = new LevelBtn(pX, pY, LevelBtn.Type.LOWER, this);
            this.levelUpBtn = new LevelBtn(pX + RISE_LVL_BTN_U, pY, LevelBtn.Type.RISE, this);
            this.updateActiveState();
        }

        private void updateActiveState() {
            this.levelUpBtn.active = this.enchantmentDisplay.lvl < this.enchantmentDisplay.enchantment.getMaxLevel() || (this.enchantmentDisplay.enchantment.getMaxLevel() > 1 && this.enchantmentDisplay.lvl <= this.enchantmentDisplay.enchantment.getMaxLevel() && ITEEnchantingTableScreen.this.isItemEmpowered());
            this.levelDownBtn.active = this.enchantmentDisplay.lvl > 0;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            this.levelDownBtn.render(guiGraphics, mouseX, mouseY, partialTick);
            this.enchantmentDisplay.render(guiGraphics, mouseX, mouseY, partialTick);
            this.levelUpBtn.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            return this.levelDownBtn.mouseClicked(mouseX, mouseY, button)
                    || this.enchantmentDisplay.mouseClicked(mouseX, mouseY, button)
                    || this.levelUpBtn.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void setY(int pY) {
            super.setY(pY);
            this.enchantmentDisplay.setY(pY);
            this.levelUpBtn.setY(pY);
            this.levelDownBtn.setY(pY);
        }

        public void setActive(boolean active) {
            this.visible = active;
            this.enchantmentDisplay.visible = active;
            this.levelUpBtn.visible = active;
            this.levelDownBtn.visible = active;
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }

    private class LevelBtn extends AbstractButton {
        Type type;
        EnchantmentEntry enchantmentEntry;
        public LevelBtn(int pX, int pY, Type type, EnchantmentEntry enchantmentEntry) {
            super(pX, pY, LVL_BTN_W, ENCH_ENTRY_H, Component.empty());
            this.type = type;
            this.enchantmentEntry = enchantmentEntry;
        }

        @Override
        public void onPress() {
            if (!this.isActive())
                return;
            if (this.type == Type.LOWER)
                this.enchantmentEntry.enchantmentDisplay.lower();
            else
                this.enchantmentEntry.enchantmentDisplay.rise();
            this.enchantmentEntry.updateActiveState();
            syncEnchantments();
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            if (!this.isActive()) {
                this.setTooltip(null);
                return;
            }
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            guiGraphics.blit(TEXTURE_LOCATION, this.getX(), this.getY(), this.type == Type.LOWER ? LOWER_LVL_BTN_U : RISE_LVL_BTN_U, ENCH_ENTRY_V + this.getYOffset(), this.width, this.height);
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (this.type == Type.LOWER)
                this.setTooltip(Tooltip.create(Component.literal("Previous level cost: %s".formatted(ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getCost(this.enchantmentEntry.enchantmentDisplay.enchantment, this.enchantmentEntry.enchantmentDisplay.lvl - 1))))));
            else if (this.type == Type.RISE)
                this.setTooltip(Tooltip.create(Component.literal("Next level cost: %s".formatted(ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getCost(this.enchantmentEntry.enchantmentDisplay.enchantment, this.enchantmentEntry.enchantmentDisplay.lvl + 1))))));
            else this.setTooltip(null);
        }

        private int getYOffset() {
            int i = 0;
            if (this.isHoveredOrFocused())
                i = 1;

            return i * this.height;
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }

        enum Type {
            LOWER,
            RISE
        }
    }

    private class EnchantmentDisplay extends AbstractWidget {

        public Enchantment enchantment;
        public int lvl;
        public EnchantmentDisplay(int pX, int pY, Enchantment enchantment, int lvl) {
            super(pX, pY, ENCH_DISPLAY_W, ENCH_ENTRY_H, Component.translatable(enchantment.getDescriptionId()));
            this.enchantment = enchantment;
            this.lvl = lvl;
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            if (!this.isActive()) {
                this.setTooltip(null);
                return;
            }
            pGuiGraphics.blit(TEXTURE_LOCATION, this.getX(), this.getY(), ENCH_DISPLAY_U, ENCH_ENTRY_V + this.getYOffset(), this.getWidth(), this.getHeight());
            pGuiGraphics.blit(TEXTURE_LOCATION, this.getX() + this.getWidth(), this.getY(), ENCH_DISPLAY_U + this.getWidth(), ENCH_ENTRY_V + this.getYOffset(), ENCH_LVL_W, this.getHeight());
            this.renderScrollingString(pGuiGraphics, Minecraft.getInstance().font, 1, 0xDDDDDD);
            Component lvlTxt = Component.empty();
            if (this.lvl > 0)
                lvlTxt = Component.translatable("enchantment.level." + this.lvl);
            pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, lvlTxt, this.getX() + ENCH_DISPLAY_W + LVL_BTN_W / 2 + 3, this.getY() + 3, this.lvl > this.enchantment.getMaxLevel() ? 16733695 : 0xDDDDDD);
            this.setTooltip(Tooltip.create(Component.literal("Total cost: %s".formatted(ONE_DECIMAL_FORMATTER.format(EnchantingFeature.getCost(enchantment, lvl))))));
            this.isHovered = pMouseX >= this.getX() && pMouseY >= this.getY() && pMouseX < this.getX() + this.width + ENCH_LVL_W && pMouseY < this.getY() + this.height;
        }

        /**
         * Returns if the button should be disabled
         */
        public void rise() {
            if (this.lvl < this.enchantment.getMaxLevel() || (this.enchantment.getMaxLevel() > 1 && this.lvl <= this.enchantment.getMaxLevel() && ITEEnchantingTableScreen.this.isItemEmpowered()))
                this.lvl++;
        }

        /**
         * Returns if the button should be disabled
         */
        public void lower() {
            if (this.lvl > 0)
                this.lvl--;
        }

        private int getYOffset() {
            int i = 0;
            if (this.isHoveredOrFocused())
                i = 1;

            return i * this.height;
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }
    }

    private class ScrollButton extends AbstractWidget {

        final Type type;

        public ScrollButton(int pX, int pY, int pWidth, int pHeight, Type type) {
            super(pX, pY, pWidth, pHeight, Component.empty());
            this.type = type;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            if (!this.isActive())
                return;
            guiGraphics.blit(TEXTURE_LOCATION, this.getX(), this.getY(), DOWN_BUTTON_U + this.getXOffset(), DOWN_BUTTON_V + (this.type == Type.DOWN ? SCROLL_BUTTON_H : 0), SCROLL_BUTTON_W, SCROLL_BUTTON_H);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

        }

        @Override
        public void onClick(double mouseX, double mouseY) {
            ITEEnchantingTableScreen.this.scroll(this.type == Type.DOWN ? 1 : -1);
        }

        private int getXOffset() {
            int i = 0;
            if (this.isHoveredOrFocused())
                i = 1;

            return i * this.width;
        }

        enum Type {
            UP,
            DOWN
        }
    }

    private void syncEnchantments() {
        List<EnchantmentInstance> list = new ArrayList<>();
        for (EnchantmentEntry enchantmentEntry : this.enchantmentEntries) {
            if (enchantmentEntry.enchantmentDisplay.lvl <= 0)
                continue;

            list.add(new EnchantmentInstance(enchantmentEntry.enchantmentDisplay.enchantment, enchantmentEntry.enchantmentDisplay.lvl));
        }
        SyncITEEnchantingTableEnchantments.sync(list);
    }
}
