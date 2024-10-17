package com.matthewperiut.lethalfacility.gen.control;

import com.matthewperiut.lethalfacility.LethalFacility;
import net.modificationstation.stationapi.api.util.math.Vec3d;

import java.io.*;

public class EntranceFileManager {
    public static void handle(String dir, String saveName) {
        File lethalPath = new File(dir + "/" + saveName + "/lethalfacility");
        lethalPath.mkdirs();
        LethalFacility.worldLethalEntranceFilePath = dir + "/" + saveName + "/lethalfacility/entrance.txt";
        File file = new File(LethalFacility.worldLethalEntranceFilePath);


        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(LethalFacility.worldLethalEntranceFilePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("true")) {
                        LethalFacility.worldLethalEntranceExists = true;
                    }
                    else if (line.equals("false")) {
                        LethalFacility.worldLethalEntranceExists = false;
                        break;
                    } else {
                        String[] poses = line.split(" ");

                        LethalFacility.worldLethalEntrancePos = new Vec3d(
                                Double.parseDouble(poses[0]),
                                Double.parseDouble(poses[1]),
                                Double.parseDouble(poses[2]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileWriter writer = new FileWriter(LethalFacility.worldLethalEntranceFilePath)) {
                writer.write("false");
                LethalFacility.worldLethalEntranceExists = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
