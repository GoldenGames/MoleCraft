package me.mani.molecraft.util;

import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketListener;

import org.bukkit.Location;

import com.avaje.ebeaninternal.server.cluster.Packet;

public class CustomPacketBlockChange extends Packet {
	
	private int position;
    private int block;

    public CustomPacketBlockChange() {}

    public CustomPacketBlockChange(Location loc, int block, int data) {
        this.position = ((loc.getBlockX() & 0x3FFFFFF) << 38) | ((loc.getBlockY() & 0xFFF) << 26) | (loc.getBlockZ() & 0x3FFFFFF);
        this.block = block << 4 | data;
    }

    public void a(PacketDataSerializer packetdataserializer) {
    	this.block = packetdataserializer.a();
        this.position = packetdataserializer.readInt();
    }

    public void b(PacketDataSerializer packetdataserializer) {
    	packetdataserializer.b(block);
        packetdataserializer.writeInt(this.position);
    }

    public void a(PacketPlayOutListener packetplayoutlistener) {
        packetplayoutlistener.a();
    }
    
    public void handle(PacketListener packetlistener) {
    	this.a((PacketPlayOutListener) packetlistener);
    }

}
