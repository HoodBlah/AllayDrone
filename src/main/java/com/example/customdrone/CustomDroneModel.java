package com.example.customdrone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class CustomDroneModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CustomDroneMod.MODID, "custom_drone"), "main");

    private final ModelPart root;

    public CustomDroneModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Dimensions taken from custom_drone.jem (texture size 32x32)
        // Head
        PartDefinition head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-2.5F, -2.5F, -2.5F, 5F, 5F, 5F),
                PartPose.offset(0.0F, 16.0F, 0.0F));

        // Hat brim (submodel head4)
        head.addOrReplaceChild("hat_brim",
                CubeListBuilder.create().texOffs(1, 12)
                        .addBox(-4.5F, -1.5F, -7.0F, 9F, 0F, 9F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 2.5F, (float) Math.toRadians(30), 0.0F, 0.0F));

        // Body
        root.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 10).addBox(-1.5F, -2.0F, -1F, 3F, 4F, 2F)
                        .texOffs(0, 16).addBox(-1.5F, -3.0F, -1F, 3F, 5F, 2F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 18.0F, 0.0F));

        // Right Arm
        root.addOrReplaceChild("right_arm",
                CubeListBuilder.create()
                        .texOffs(23, 0).addBox(-0.5F, -2.0F, -1.0F, 1F, 4F, 2F),
                PartPose.offset(-1.75F, 18.0F, 0.0F));

        // Left Arm
        root.addOrReplaceChild("left_arm",
                CubeListBuilder.create()
                        .texOffs(23, 6).addBox(-0.5F, -2.0F, -1.0F, 1F, 4F, 2F),
                PartPose.offset(1.75F, 18.0F, 0.0F));

        // Left Wing
        root.addOrReplaceChild("left_wing",
                CubeListBuilder.create()
                        .texOffs(16, 14).addBox(0.0F, -2.5F, 1.0F, 0F, 5F, 8F),
                PartPose.offset(0.5F, 16.0F, -1.0F));

        // Right Wing
        root.addOrReplaceChild("right_wing",
                CubeListBuilder.create()
                        .texOffs(16, 14).addBox(0.0F, -2.5F, 1.0F, 0F, 5F, 8F),
                PartPose.offset(-0.5F, 16.0F, -1.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Basic idle wing animation
        float flap = (float) Math.sin(ageInTicks * 2.0F) * 0.3F;
        root.getChild("left_wing").yRot = flap;
        root.getChild("right_wing").yRot = -flap;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
} 