package dev.cammiescorner.townships.api;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.ChunkPos;

import java.util.*;

public class TownshipClaim {
	private final List<ChunkPos> chunkPoses = new ArrayList<>();
	private final Set<UUID> memberUuids = new HashSet<>();
	private final Set<UUID> enemyUuids = new HashSet<>();
	private UUID uuid;
	private String name;
	private UUID ownerUuid;

	@SuppressWarnings("ConstantConditions")
	public void readNbt(NbtCompound tag) {
		long[] posList = tag.getLongArray("ChunkPoses");
		NbtList memberList = tag.getList("Members", NbtElement.COMPOUND_TYPE);
		NbtList enemyList = tag.getList("Enemies", NbtElement.INT_ARRAY_TYPE);
		chunkPoses.clear();
		memberUuids.clear();
		enemyUuids.clear();

		for(long pos : posList)
			chunkPoses.add(new ChunkPos(pos));

		for(int i = 0; i < memberList.size(); i++) {
			NbtCompound nbt = memberList.getCompound(i);
			UUID member = NbtHelper.toUuid(nbt.get("Id"));
			memberUuids.add(member);
		}

		enemyList.forEach(nbtElement -> enemyUuids.add(NbtHelper.toUuid(nbtElement)));

		uuid = tag.getUuid("Id");
		name = tag.getString("Name");
		ownerUuid = tag.getUuid("Owner");
	}

	public void writeNbt(NbtCompound tag) {
		NbtList memberList = new NbtList();
		NbtList enemyList = new NbtList();

		memberUuids.forEach(uuid1 -> {
			NbtCompound nbt = new NbtCompound();
			nbt.put("Id", NbtHelper.fromUuid(uuid1));
			memberList.add(nbt);
		});

		enemyUuids.forEach(uuid1 -> enemyList.add(NbtHelper.fromUuid(uuid1)));

		tag.putLongArray("ChunkPoses", chunkPoses.stream().mapToLong(ChunkPos::toLong).toArray());
		tag.put("Members", memberList);
		tag.put("Enemies", enemyList);
		tag.putUuid("Id", uuid);
		tag.putString("Name", name);
		tag.putUuid("Owner", ownerUuid);
	}

	public List<ChunkPos> getChunkPoses() {
		return chunkPoses;
	}

	public Set<UUID> getMemberUuids() {
		return memberUuids;
	}

	public Set<UUID> getEnemyUuids() {
		return enemyUuids;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getOwnerUuid() {
		return ownerUuid;
	}

	public void setOwnerUuid(UUID ownerUuid) {
		this.ownerUuid = ownerUuid;
		memberUuids.add(ownerUuid);
	}

	public void addChunkPos(ChunkPos pos) {
		chunkPoses.add(pos);
	}

	public void removeChunkPos(ChunkPos pos) {
		chunkPoses.remove(pos);
	}
}
