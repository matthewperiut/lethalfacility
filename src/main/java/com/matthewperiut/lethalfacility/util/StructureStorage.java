package com.matthewperiut.lethalfacility.util;

import com.matthewperiut.lethalfacility.mixin.WorldAccessor;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.io.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class StructureStorage {
    private BlockPos[] corners;
    private BlockPos[] raw_corners = new BlockPos[2];

    public void setPos1(BlockPos pos) {
        raw_corners[0] = pos;
    }
    public void setPos2(BlockPos pos) {
        raw_corners[1] = pos;
    }
    public void cleanData() {
        // makes [0] min and [1] max
        corners = new BlockPos[]{
                new BlockPos(Math.min(raw_corners[0].x, raw_corners[1].x), Math.min(raw_corners[0].y, raw_corners[1].y)-1, Math.min(raw_corners[0].z, raw_corners[1].z)),
                new BlockPos(Math.max(raw_corners[0].x, raw_corners[1].x), Math.max(raw_corners[0].y, raw_corners[1].y)-1, Math.max(raw_corners[0].z, raw_corners[1].z))
        };
    }
    public String copy(World world, String name) {
        if (raw_corners[0] == null || raw_corners[1] == null) {
            return "One of the corners are not set";
        }
        cleanData();
        if (corners[1].x - corners[0].x > 100 || corners[1].y - corners[0].y > 100 || corners[1].z - corners[0].z > 100) {
            return "Area is too large, < 100 blocks in each direction allowed";
        }
        // Block Identifier Pass
        HashMap<Block, Integer> blocks = new HashMap<>();
        StringBuilder result = new StringBuilder();
        for (int x = corners[0].x; x <= corners[1].x; x++) {
            for (int y = corners[0].y; y <= corners[1].y; y++) {
                for (int z = corners[0].z; z <= corners[1].z; z++) {
                    BlockState bs = world.getBlockState(x,y,z);
                    Block block = bs.getBlock();
                    int meta = world.getBlockMeta(x,y,z);
                    int i = blocks.getOrDefault(block, -1);
                    if (i == -1) {
                        blocks.put(block, blocks.size());
                        i = blocks.getOrDefault(block, -1);
                    }
                    result.append(x-corners[0].x).append(",").append(y-corners[0].y).append(",").append(z-corners[0].z).append(":").append(i);
                    result.append("m").append(meta);
                    AtomicReference<String> bs_contents = new AtomicReference<>("");
                    bs.getEntries().forEach((property, comparable) -> {
                        bs_contents.set(bs_contents.get() + property.getName() + "=" + comparable + ",");
                    });
                    if (bs_contents.get().isEmpty()) {
                        result.append('\n');
                    } else {
                        String remove_last_comma = bs_contents.get().substring(0, bs_contents.get().length()-1);
                        result.append(":").append(remove_last_comma).append('\n');
                    }
                }
            }
        }
        StringBuilder identifiers = new StringBuilder();
        blocks.forEach((block, i) -> {
            Optional<Identifier> id = BlockRegistry.INSTANCE.getId(block.id);
            if (id.isEmpty()) {
                identifiers.append("raw_").append(block.id).append("=").append(id).append('\n');
            } else {
                identifiers.append(i).append("=").append(id.get()).append('\n');
            }
        });

        // remove \n from end of file
        String resultStr = result.substring(0,result.length()-1);

        String path = StructureFolder.getPath();
        new File(path).mkdirs();
        // Create the file path using name + ".ss", ss for StructureStorage
        String filePath = path + File.separator + name + ".ss";
        File file = new File(filePath);

        if (file.exists()) {
            return "File with the name \"" + name + "\" already exists, cannot proceed";
        }

        // Write the output to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("ids:\n");
            writer.write(String.valueOf(identifiers));
            writer.write("data:\n");
            writer.write(String.valueOf(resultStr));
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while writing to the file.";
        }

        world.setBlockStateWithNotify(corners[0], Block.DIRT.getDefaultState());
        world.setBlockStateWithNotify(corners[1], Block.STONE.getDefaultState());

        return "Copied Successfully as " + name;
    }
    public String paste(World world, int x, int y, int z, String name) {
        return paste(world, x, y, z, name, true);
    }

    // Pretty much exclusively for world gen
    // Separated the functions to match minecraft conventions
    public String pasteWithoutNotify(World world, int x, int y, int z, String name) {
        return paste(world, x, y, z, name, false);
    }

    private String paste(World world, int x, int y, int z, String name, boolean notify) {
        y = y-1;
        String path = StructureFolder.getPath();
        new File(path).mkdirs();
        String filePath;
        if (name.contains("/")) {
            filePath = name + ".ss";
            String[] parts = name.split("/");
            name = parts[parts.length-1];
        } else {
            filePath = path + "/" + name + ".ss";
        }

        HashMap<Integer, Block> blocks = new HashMap<>();
        HashMap<BlockPos, Integer> postNotify = new HashMap<>();
        boolean getIds = true;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.equals("ids:")) {
                    getIds = true;
                    continue;
                }
                if (line.equals("data:")) {
                    getIds = false;
                    continue;
                }
                if (getIds) {
                    String[] entry = line.split("=");
                    String pseudoId = entry[0];
                    String realId = entry[1];
                    int a = Integer.parseInt(pseudoId);
                    Optional<Block> blockContainer = BlockRegistry.INSTANCE.getOrEmpty(Identifier.of(realId));
                    if (blockContainer.isEmpty()) {
                        return "Error: The block \"" + realId + "\" doesn't exist, cannot paste while parsing " + name;
                    } else {
                        blocks.put(a, blockContainer.get());
                    }
                } else {
                    String[] data = line.split(":");
                    if (data.length > 0) {
                        String[] pos = data[0].split(",");
                        if (pos.length != 3) {
                            return "Error: Incorrect number of pos parameters parsing " + name;
                        }
                        int xOffset = Integer.parseInt(pos[0]);
                        int yOffset = Integer.parseInt(pos[1]);
                        int zOffset = Integer.parseInt(pos[2]);
                        if (data.length > 1) {
                            String[] blockID = data[1].split("m");
                            int pseudoId = Integer.parseInt(blockID[0]);
                            int meta = Integer.parseInt(blockID[1]);

                            Block block = blocks.get(pseudoId);
                            if (block == null) {
                                return "Invalid pseudo ID in data while parsing " + name;
                            }

                            BlockState blockState;
                            if (data.length > 2) {
                                // optional parameter for BlockState
                                blockState = parseBlockStateInfo(block, data[2]);
                            } else {
                                blockState = block.getDefaultState();
                            }

                            world.setBlockStateWithMetadata(x + xOffset, y + yOffset, z + zOffset, blockState, meta);
                            if (notify) {
                                postNotify.put(new BlockPos(x + xOffset, y + yOffset, z + zOffset), block.id);
                            }
                        } else {
                            return "Error: data line with no block parsing " + name;
                        }
                    } else {
                        return "Error: Empty data line found parsing " + name;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: File \"" + name + "\" not found";
        } catch (NumberFormatException e) {
            return "Error: File \"" + name + "\" contents incorrect";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unknown Error has occurred parsing " + name;
        }

        // You may be asking why post notify?
        // Doors and other things may break if notifying before surrounding are updated.
        // Therefore, we must notify after all the changes.
        if (notify) {
            postNotify.forEach((pos, id) -> {
                ((WorldAccessor)world).invokeBlockUpdate(pos.x, pos.y, pos.z, id);
            });
        }

        return "Pasted Successfully";
    }

    private static Direction getHorizontalDirectionValue(String s) {
        return switch (s) {
            case "north" -> Direction.NORTH;
            case "south" -> Direction.SOUTH;
            case "west" -> Direction.WEST;
            case "east" -> Direction.EAST;
            default -> Direction.NORTH;
        };
    }

    private static BlockState parseBlockStateInfo(Block block, String data) {
        BlockState result = block.getDefaultState();

        String[] bsProperties = data.split(",");
        for (String bsProperty : bsProperties) {
            String[] segments = bsProperty.split("=");
            String p = segments[0];
            String s = segments[1];
            if (s.equals("true") || s.equals("false")) {
                BooleanProperty bp = BooleanProperty.of(p);
                result = result.with(bp, Boolean.valueOf(s));
            }
            if (p.equals("facing")) {
                DirectionProperty dp = Properties.HORIZONTAL_FACING;
                result = result.with(dp, getHorizontalDirectionValue(s));
            }
            // more to be added
        }

        return result;
    }
}
