package fr.ethilvan.bukkit.portals.portal.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;

import fr.aumgn.bukkitutils.geom.Direction;
import fr.aumgn.bukkitutils.geom.Vector;

public class PortalBlocksVisitor {

    private PortalBlocks blocks; 
    private Vector vec;
    private Direction from;

    public PortalBlocksVisitor(PortalBlocks blocks, Vector vec) {
        this(blocks, vec, Direction.NONE);
    }

    public PortalBlocksVisitor(PortalBlocks tree, Vector vec, Direction from) {
        this.blocks = tree;
        this.vec = vec;
        this.from = from;
    }

    public void visit() {
        findChildren();
    }

    private boolean isChild(Vector child) {
        if (blocks.getBlocks().contains(child)) {
            return false;
        }

        if (!child.isInside(blocks.getMin(), blocks.getMax())) {
            return false;
        }

        Block block = child.toBlock(blocks.getWorld());
        return  block.getType() == Material.AIR
                    || block.getType() == Material.PORTAL
                    || block.getType() == Material.ENDER_PORTAL;
    }

    private void processDir(Direction dir) {
        if (from != dir) {
            Vector childVec = vec.add(dir.getVector());
            if (isChild(childVec)) {
                addChildren(dir.rotate(180), childVec);
            }
        }
    }

    private void findChildren() {
        for (Direction direction : blocks.getOrientation().getDirections()) {
            processDir(direction);
        }
    }

    private void addChildren(Direction from, Vector childVec) {
        blocks.getBlocks().add(childVec);
        PortalBlocksVisitor visitor =
                new PortalBlocksVisitor(blocks, childVec);
        visitor.visit();
    }
}
