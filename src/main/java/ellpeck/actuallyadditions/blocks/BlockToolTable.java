/*
 * This file ("BlockToolTable.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * � 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityInventoryBase;
import ellpeck.actuallyadditions.tile.TileEntityToolTable;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockToolTable extends BlockContainerBase implements INameableItem{

    public BlockToolTable(){
        super(Material.wood);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.5F);
        this.setResistance(5.0F);
        this.setStepSound(soundTypeWood);

        this.setBlockBounds(0F, 0F, 0F, 1F, 1F-3/16, 1F);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 0);
        if(rotation == 1) world.setBlockMetadataWithNotify(x, y, z, 1, 3);
        if(rotation == 2) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        if(rotation == 3) world.setBlockMetadataWithNotify(x, y, z, 3, 3);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityToolTable();
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.planks.getIcon(0, 0);
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.TOOL_TABLE_RENDER_ID;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityToolTable table = (TileEntityToolTable)world.getTileEntity(x, y, z);
            if(table != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.TOOL_TABLE.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(x, y, z);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                if(tile.getSizeInventory() > 0){
                    for(int i = 0; i < tile.getSizeInventory(); i++){
                        if(i != TileEntityToolTable.SLOT_OUTPUT){
                            this.dropSlotFromInventory(i, tile, world, x, y, z);
                        }
                    }
                }
            }
        }
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockToolTable";
    }

    public static class TheItemBlock extends ItemBlock{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.rare;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
