package fr.ethilvan.bukkit.portals.portal.blocks;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Faces;
import fr.aumgn.bukkitutils.geom.Vector;
import fr.ethilvan.bukkit.portals.exception.RegionNotFlat;

public enum Orientation {

    UP_DOWN {
        public Direction[] getDirections() {
            return new Direction[] {
                Faces.NORTH, Faces.SOUTH, Faces.WEST, Faces.EAST
            }; 
        }
    },
    NORTH_SOUTH {
        public Direction[] getDirections() {
            return new Direction[] {
                Faces.UP, Faces.DOWN, Faces.WEST, Faces.EAST
            }; 
        }
    },
    WEST_EAST {
        public Direction[] getDirections() {
            return new Direction[] {
                Faces.UP, Faces.DOWN, Faces.NORTH, Faces.SOUTH
            }; 
        }
    };

    public static Orientation get(String orientation) {
        if (orientation.equals(UP_DOWN.toString())) {
            return UP_DOWN;
        } else if (orientation.equals(NORTH_SOUTH.toString())) {
            return NORTH_SOUTH;
        } else if (orientation.equals(WEST_EAST.toString())) {
            return WEST_EAST;
        } else {
            return null;
        }
    }

    public abstract Direction[] getDirections();

    public static Orientation calculate(Vector vec1, Vector vec2) {
        if (vec1.getBlockX() == vec2.getBlockX()) {
            return Orientation.NORTH_SOUTH;
        } else if (vec1.getBlockY() == vec2.getBlockY()) {
            return Orientation.UP_DOWN;
        } else if (vec1.getBlockZ() == vec1.getBlockZ()) {
            return Orientation.WEST_EAST;
        } else {
            throw new RegionNotFlat();
        }
    }

}
