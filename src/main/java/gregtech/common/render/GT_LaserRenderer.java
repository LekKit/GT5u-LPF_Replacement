package gregtech.common.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ClientRegistry;
import gregtech.common.tileentities.render.TileLaser;

public class GT_LaserRenderer extends TileEntitySpecialRenderer {

    private double zOffset = 0.0F;
    private double xOffset = 0.0F;

    private static final IModelCustom lasermodel = AdvancedModelLoader
        .loadModel(new ResourceLocation("gregtech", "textures/model/laser.obj"));

    public GT_LaserRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileLaser.class, this);
    }

    private void maths(float counter) {
        float tc = (0.05F * counter);
        zOffset = 0.5 + 0.45 * Math.sin(2 * Math.PI * tc);
        xOffset = 0.5 + 0.45 * Math.sin(0.5 * Math.PI * tc);
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float timeSinceLastTick) {
        final TileLaser ltile = (TileLaser) tile;
        if (ltile.getShouldRender()) {
            GL11.glPushMatrix();
            GL11.glTranslated(x + xOffset, y + 0.5, z + zOffset);
            maths(ltile.counter);
            ltile.counter += 0.2F;
            if (ltile.counter >= 80) {
                ltile.counter = 0;
            }
            GL11.glColor3f(ltile.getRed(), ltile.getGreen(), ltile.getBlue());
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            bindTexture(new ResourceLocation("gregtech", "textures/model/laser.png"));
            lasermodel.renderAll();
            GL11.glPopMatrix();
        }
    }
}
