package com.nyuppo.block.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Function;

public class HangingSignBlockEntity extends BlockEntity {
    public static final int TEXT_COUNT = 4;
    private static final String[] TEXT_KEYS = new String[]{"Text1", "Text2", "Text3", "Text4"};
    private static final String[] FILTERED_TEXT_KEYS = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
    private final Text[] texts;
    private final Text[] filteredTexts;
    private boolean editable;
    @Nullable
    private UUID editor;
    @Nullable
    private OrderedText[] textsBeingEdited;
    private boolean filterText;
    private DyeColor textColor;
    private boolean glowingText;
    private static final int MAX_TEXT_WIDTH = 50;
    private static final int TEXT_LINE_HEIGHT = 9;

    public HangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntityType.HANGING_SIGN, blockPos, blockState);
        this.texts = new Text[]{ScreenTexts.EMPTY, ScreenTexts.EMPTY, ScreenTexts.EMPTY, ScreenTexts.EMPTY};
        this.filteredTexts = new Text[]{ScreenTexts.EMPTY, ScreenTexts.EMPTY, ScreenTexts.EMPTY, ScreenTexts.EMPTY};
        this.editable = true;
        this.textColor = DyeColor.BLACK;
    }

    public int getTextLineHeight() {
        return 9;
    }

    public int getMaxTextWidth() {
        return 50;
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        for(int i = 0; i < 4; ++i) {
            Text text = this.texts[i];
            String string = Text.Serializer.toJson(text);
            nbt.putString(TEXT_KEYS[i], string);
            Text text2 = this.filteredTexts[i];
            if (!text2.equals(text)) {
                nbt.putString(FILTERED_TEXT_KEYS[i], Text.Serializer.toJson(text2));
            }
        }

        nbt.putString("Color", this.textColor.getName());
        nbt.putBoolean("GlowingText", this.glowingText);
    }

    public void readNbt(NbtCompound nbt) {
        this.editable = false;
        super.readNbt(nbt);
        this.textColor = DyeColor.byName(nbt.getString("Color"), DyeColor.BLACK);

        for(int i = 0; i < 4; ++i) {
            String string = nbt.getString(TEXT_KEYS[i]);
            Text text = this.parseTextFromJson(string);
            this.texts[i] = text;
            String string2 = FILTERED_TEXT_KEYS[i];
            if (nbt.contains(string2, 8)) {
                this.filteredTexts[i] = this.parseTextFromJson(nbt.getString(string2));
            } else {
                this.filteredTexts[i] = text;
            }
        }

        this.textsBeingEdited = null;
        this.glowingText = nbt.getBoolean("GlowingText");
    }

    private Text parseTextFromJson(String json) {
        Text text = this.unparsedTextFromJson(json);
        if (this.world instanceof ServerWorld) {
            try {
                return Texts.parse(this.getCommandSource((ServerPlayerEntity)null), text, (Entity)null, 0);
            } catch (CommandSyntaxException var4) {
            }
        }

        return text;
    }

    private Text unparsedTextFromJson(String json) {
        try {
            Text text = Text.Serializer.fromJson(json);
            if (text != null) {
                return text;
            }
        } catch (Exception var3) {
        }

        return ScreenTexts.EMPTY;
    }

    public Text getTextOnRow(int row, boolean filtered) {
        return this.getTexts(filtered)[row];
    }

    public void setTextOnRow(int row, Text text) {
        this.setTextOnRow(row, text, text);
    }

    public void setTextOnRow(int row, Text text, Text filteredText) {
        this.texts[row] = text;
        this.filteredTexts[row] = filteredText;
        this.textsBeingEdited = null;
    }

    public OrderedText[] updateSign(boolean filterText, Function<Text, OrderedText> textOrderingFunction) {
        if (this.textsBeingEdited == null || this.filterText != filterText) {
            this.filterText = filterText;
            this.textsBeingEdited = new OrderedText[4];

            for(int i = 0; i < 4; ++i) {
                this.textsBeingEdited[i] = (OrderedText)textOrderingFunction.apply(this.getTextOnRow(i, filterText));
            }
        }

        return this.textsBeingEdited;
    }

    private Text[] getTexts(boolean filtered) {
        return filtered ? this.filteredTexts : this.texts;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public boolean copyItemDataRequiresOperator() {
        return true;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if (!editable) {
            this.editor = null;
        }

    }

    public void setEditor(UUID editor) {
        this.editor = editor;
    }

    @Nullable
    public UUID getEditor() {
        return this.editor;
    }

    public boolean onActivate(ServerPlayerEntity player) {
        Text[] var2 = this.getTexts(player.shouldFilterText());
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Text text = var2[var4];
            Style style = text.getStyle();
            ClickEvent clickEvent = style.getClickEvent();
            if (clickEvent != null && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                player.getServer().getCommandManager().executeWithPrefix(this.getCommandSource(player), clickEvent.getValue());
            }
        }

        return true;
    }

    public ServerCommandSource getCommandSource(@Nullable ServerPlayerEntity player) {
        String string = player == null ? "Sign" : player.getName().getString();
        Text text = player == null ? Text.literal("Sign") : player.getDisplayName();
        return new ServerCommandSource(CommandOutput.DUMMY, Vec3d.ofCenter(this.pos), Vec2f.ZERO, (ServerWorld)this.world, 2, string, (Text)text, this.world.getServer(), player);
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public boolean setTextColor(DyeColor value) {
        if (value != this.getTextColor()) {
            this.textColor = value;
            this.updateListeners();
            return true;
        } else {
            return false;
        }
    }

    public boolean isGlowingText() {
        return this.glowingText;
    }

    public boolean setGlowingText(boolean glowingText) {
        if (this.glowingText != glowingText) {
            this.glowingText = glowingText;
            this.updateListeners();
            return true;
        } else {
            return false;
        }
    }

    private void updateListeners() {
        this.markDirty();
        this.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }
}
