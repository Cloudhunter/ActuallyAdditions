/*
 * This file ("PacketHandlerHelper.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public final class PacketHandlerHelper{

    public static void sendButtonPacket(TileEntity tile, int buttonId){
        NBTTagCompound compound = new NBTTagCompound();
        BlockPos pos = tile.getPos();
        compound.setInteger("X", pos.getX());
        compound.setInteger("Y", pos.getY());
        compound.setInteger("Z", pos.getZ());
        compound.setInteger("WorldID", tile.getWorld().provider.getDimension());
        compound.setInteger("PlayerID", Minecraft.getMinecraft().thePlayer.getEntityId());
        compound.setInteger("ButtonID", buttonId);
        PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.GUI_BUTTON_TO_TILE_HANDLER));
    }

    public static void sendPlayerDataPacket(EntityPlayer player, boolean log, boolean toClient){
        NBTTagCompound compound = new NBTTagCompound();
        compound.setUniqueId("UUID", player.getUniqueID());
        compound.setBoolean("Log", log);

        NBTTagCompound data = new NBTTagCompound();
        PlayerData.getDataFromPlayer(player).writeToNBT(data);
        compound.setTag("Data", data);

        if(toClient){
            if(player instanceof EntityPlayerMP){
                PacketHandler.theNetwork.sendTo(new PacketServerToClient(compound, PacketHandler.CHANGE_PLAYER_DATA_HANDLER), (EntityPlayerMP)player);
            }
        }
        else{
            PacketHandler.theNetwork.sendToServer(new PacketClientToServer(compound, PacketHandler.CHANGE_PLAYER_DATA_HANDLER));
        }
    }
}
