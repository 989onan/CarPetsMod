package followingcar.common.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ToolTipItem extends Item{
	
	private String tooltip = "";
	private int color;
	private boolean italic;
	private boolean bold;
	
	
	public ToolTipItem(Properties p_41383_, String ToolTip,boolean italic,boolean bold,int colorid) {
		super(p_41383_);
		this.tooltip = ToolTip;
		this.color = colorid;
		this.bold = bold;
		this.italic = italic;
	}
	@Override
	public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
		MutableComponent text = new TextComponent(this.tooltip);
		
		
		if(this.color != -1) {
			text = text.withStyle(ChatFormatting.getById(color));
		}
		if(this.bold) {
			text = text.withStyle(ChatFormatting.BOLD);
		}
		if(this.italic) {
			text = text.withStyle(ChatFormatting.ITALIC);
		}
		
		tooltip.add(text);
	}

}
