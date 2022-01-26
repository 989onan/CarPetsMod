package followingcar.common.items;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import followingcar.core.init.CarTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class CarChangerItem extends Item{
	
	private String tooltip = "";
	private int color;
	private boolean italic;
	private boolean bold;
	
	
	public CarChangerItem(Properties p_41383_, String ToolTip,boolean italic,boolean bold,int colorid) {
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
		
		CompoundTag compoundtag = p_41421_.getOrCreateTag();
		if(CarTypeRegistry.CarTypes.get((int) compoundtag.getShort("CarType")) != null) {
			tooltip.add(new TextComponent("Selected car type is: "+CarTypeRegistry.CarTypes.get((int) compoundtag.getShort("CarType")).getName()));
		}
		else {
			tooltip.add(new TextComponent("Selected car type is: MISSING NO AHHHHHHHHHHHHHH"));
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		
		CompoundTag compoundtag = itemstack.getOrCreateTag();
        if (!compoundtag.contains("CarType")) {
           compoundtag.putShort("CarType", (short) 0);
        }
		
        if(player.isCrouching()) {
        	short cartype = compoundtag.getShort("CarType");
        	
        	List<Integer> secretcartypes = new ArrayList<Integer>(){
				private static final long serialVersionUID = 1L;

				{
        			add(13);
        		}
        	};
        	
        	if(CarTypeRegistry.CarTypes.get(cartype+1) != null && !secretcartypes.contains(cartype+1)) {
        		compoundtag.putShort("CarType",(short) (cartype+1));
        	}
        	else if(secretcartypes.contains(cartype+1)) {
        		while(secretcartypes.contains(cartype+1)) {
        			cartype += 1;
        		}
        		if(CarTypeRegistry.CarTypes.get(cartype+1) != null) {
        			compoundtag.putShort("CarType",(short) (cartype+1));
        		}
        		else{
            		compoundtag.putShort("CarType",(short) 0);
            	}
        	}
        	else{
        		compoundtag.putShort("CarType",(short) 0);
        	}
        	return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
        }
        
        return InteractionResultHolder.pass(itemstack);
	}

}
