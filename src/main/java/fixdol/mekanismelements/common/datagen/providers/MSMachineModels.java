package fixdol.mekanismelements.common.datagen.providers;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder.FaceRotation;

public final class MSMachineModels {

    private MSMachineModels() {
    }

    public static BlockModelBuilder adsorptionSeparator(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("adsorption_separator", "block/cube_all");
        model.renderType("minecraft:cutout");
        model.texture("0", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/adsorption_separator"));
        model.texture("1", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("2", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("particle", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/adsorption_separator"));

        //base
        model.element()
              .from(0F, 0F, 0F)
              .to(16F, 4F, 16F)
              .face(Direction.NORTH).uvs(10F, 3.5F, 14F, 4.5F).texture("#0").end()
              .face(Direction.EAST).uvs(10F, 4.5F, 14F, 5.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(10.5F, 0F, 14.5F, 1F).texture("#0").end()
              .face(Direction.WEST).uvs(10.5F, 1F, 14.5F, 2F).texture("#0").end()
              .face(Direction.UP).uvs(4F, 4F, 0F, 0F).texture("#0").end()
              .face(Direction.DOWN).uvs(4F, 4F, 0F, 8F).texture("#0").end()
              .end();

        //tank1
        model.element()
              .from(10F, 4F, 4F)
              .to(14F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(10.5F, 10.5F, 11.5F, 13.5F).texture("#0").end()
              .face(Direction.EAST).uvs(4.5F, 8.5F, 6.5F, 11.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(2F, 11.5F, 3F, 14.5F).texture("#0").end()
              .face(Direction.WEST).uvs(6.5F, 8.5F, 8.5F, 11.5F).texture("#0").end()
              .face(Direction.UP).uvs(8F, 13.5F, 7F, 11.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(12.5F, 10.5F, 11.5F, 12.5F).texture("#0").end()
              .end();

        //tank2
        model.element()
              .from(2F, 4F, 4F)
              .to(6F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(2F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(8.5F, 10.5F, 9.5F, 13.5F).texture("#0").end()
              .face(Direction.EAST).uvs(8F, 2.5F, 10F, 5.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(9.5F, 10.5F, 10.5F, 13.5F).texture("#0").end()
              .face(Direction.WEST).uvs(2.5F, 8.5F, 4.5F, 11.5F).texture("#0").end()
              .face(Direction.UP).uvs(12F, 10.5F, 11F, 8.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(12.5F, 5.5F, 11.5F, 7.5F).texture("#0").end()
              .end();

        //adsorbent
        model.element()
              .from(7F, 4F, 2F)
              .to(9F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(7F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(12F, 8.5F, 12.5F, 10.5F).texture("#0").end()
              .face(Direction.EAST).uvs(8.5F, 8.5F, 11F, 10.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(12.5F, 5.5F, 13F, 7.5F).texture("#0").end()
              .face(Direction.WEST).uvs(9F, 5.5F, 11.5F, 7.5F).texture("#0").end()
              .face(Direction.UP).uvs(8.5F, 14F, 8F, 11.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13F, 8.5F, 12.5F, 11F).texture("#0").end()
              .end();

        //connector1
        model.element()
              .from(9F, 7F, 7F)
              .to(10F, 9F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(9F, 7F, 7F).end()
              .face(Direction.NORTH).uvs(13.5F, 6.5F, 13.75F, 7F).texture("#0").end()
              .face(Direction.EAST).uvs(3.5F, 8F, 4F, 8.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(7F, 13.5F, 7.25F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(2F, 10.5F, 2.5F, 11F).texture("#0").end()
              .face(Direction.UP).uvs(13.75F, 7.5F, 13.5F, 7F).texture("#0").end()
              .face(Direction.DOWN).uvs(7.5F, 13.5F, 7.25F, 14F).texture("#0").end()
              .end();

        //connector2
        model.element()
              .from(6F, 7F, 7F)
              .to(7F, 9F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 7F, 7F).end()
              .face(Direction.NORTH).uvs(7.5F, 13.5F, 7.75F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(2F, 11F, 2.5F, 11.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(13.5F, 7.5F, 13.75F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(1.5F, 13.5F, 2F, 14F).texture("#0").end()
              .face(Direction.UP).uvs(8F, 14F, 7.75F, 13.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13.75F, 8F, 13.5F, 8.5F).texture("#0").end()
              .end();

        //behind
        model.element()
              .from(0F, 4F, 12F)
              .to(16F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(4F, 0F, 8F, 3F).texture("#0").end()
              .face(Direction.EAST).uvs(0F, 10.5F, 1F, 13.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(4F, 3F, 8F, 6F).texture("#0").end()
              .face(Direction.WEST).uvs(1F, 10.5F, 2F, 13.5F).texture("#0").end()
              .face(Direction.UP).uvs(13F, 8.5F, 9F, 7.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(14F, 2.5F, 10F, 3.5F).texture("#0").end()
              .end();

        //fence1
        model.element()
              .from(14F, 4F, 2F)
              .to(15F, 14F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(0F, 13.5F, 0.25F, 16F).texture("#0").end()
              .face(Direction.EAST).uvs(0F, 8F, 2.5F, 10.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0.25F, 13.5F, 0.5F, 16F).texture("#0").end()
              .face(Direction.WEST).uvs(8F, 0F, 10.5F, 2.5F).texture("#0").end()
              .face(Direction.UP).uvs(0.75F, 16F, 0.5F, 13.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(1F, 13.5F, 0.75F, 16F).texture("#0").end()
              .end();

        //fence2
        model.element()
              .from(10F, 4F, 2F)
              .to(14F, 14F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(5F, 11.5F, 6F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(12F, 12.5F, 12.5F, 15F).texture("#0").end()
              .face(Direction.SOUTH).uvs(6F, 11.5F, 7F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(13F, 5.5F, 13.5F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(14.5F, 6F, 13.5F, 5.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(14.5F, 6F, 13.5F, 6.5F).texture("#0").end()
              .end();

        //fence3
        model.element()
              .from(1F, 4F, 2F)
              .to(2F, 14F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(1F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(13F, 13F, 13.25F, 15.5F).texture("#0").end()
              .face(Direction.EAST).uvs(4F, 6F, 6.5F, 8.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(13.25F, 8F, 13.5F, 10.5F).texture("#0").end()
              .face(Direction.WEST).uvs(6.5F, 6F, 9F, 8.5F).texture("#0").end()
              .face(Direction.UP).uvs(13.5F, 13F, 13.25F, 10.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13.5F, 13F, 13.25F, 15.5F).texture("#0").end()
              .end();

        //fence4
        model.element()
              .from(2F, 4F, 2F)
              .to(6F, 14F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(2F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(3F, 11.5F, 4F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(12.5F, 11F, 13F, 13.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(4F, 11.5F, 5F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(11.5F, 12.5F, 12F, 15F).texture("#0").end()
              .face(Direction.UP).uvs(3.5F, 8.5F, 2.5F, 8F).texture("#0").end()
              .face(Direction.DOWN).uvs(9F, 5.5F, 8F, 6F).texture("#0").end()
              .end();

        //port1
        model.element()
              .from(15F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(15F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 16F, 1F).texture("#1").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").cullface(Direction.EAST).end()
              .face(Direction.UP).uvs(0F, 0F, 16F, 1F).texture("#1").end()
              .end();

        //port2
        model.element()
              .from(0F, 4F, 4F)
              .to(1F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(-1F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(1F, 0F, 16F, 1F).texture("#1").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0.25F, 2F).texture("#1").end()
              .end();

        //portLED1
        model.element()
              .from(16F, 5F, 5F)
              .to(16F, 11F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(18F, 5F, 5F).end()
              .emissivity(15, 15)
              .face(Direction.EAST).uvs(1F, 1F, 7F, 7F).texture("#2").cullface(Direction.EAST).end()
              .end();

        //portLED2
        model.element()
              .from(0F, 5F, 5F)
              .to(0F, 11F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 8F, 8F).end()
              .emissivity(15, 15)
              .face(Direction.WEST).uvs(1F, 1F, 7F, 7F).texture("#2").cullface(Direction.WEST).end()
              .end();
        return model;
    }

    public static BlockModelBuilder airCompressor(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("air_compressor", "mekanism:block/machine");
        model.texture("sides", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("front", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/front"));
        model.texture("west", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("east", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("south", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/back"));
        model.texture("up", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/top"));
        model.texture("down", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/bottom"));
        return model;
    }

    public static BlockModelBuilder airCompressorActive(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("air_compressor_active", "mekanism:block/machine");
        model.texture("sides", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("front", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/front_active"));
        model.texture("west", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("east", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("south", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/back"));
        model.texture("up", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/top"));
        model.texture("down", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/air_compressor/bottom"));
        return model;
    }

    public static BlockModelBuilder chemicalDemolitionMachine(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("chemical_demolition_machine", "block/cube_all");
        model.renderType("minecraft:cutout");
        model.texture("0", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/chemical_demolition_machine"));
        model.texture("1", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("2", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("particle", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/chemical_demolition_machine"));

        //base
        model.element()
              .from(0F, 0F, 0F)
              .to(16F, 4F, 16F)
              .face(Direction.NORTH).uvs(10F, 3.5F, 14F, 4.5F).texture("#0").end()
              .face(Direction.EAST).uvs(10F, 4.5F, 14F, 5.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(10.5F, 0F, 14.5F, 1F).texture("#0").end()
              .face(Direction.WEST).uvs(10.5F, 1F, 14.5F, 2F).texture("#0").end()
              .face(Direction.UP).uvs(4F, 4F, 0F, 0F).texture("#0").end()
              .face(Direction.DOWN).uvs(4F, 4F, 0F, 8F).texture("#0").end()
              .end();

        //tank1
        model.element()
              .from(10F, 4F, 4F)
              .to(14F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(10.5F, 10.5F, 11.5F, 13.5F).texture("#0").end()
              .face(Direction.EAST).uvs(4.5F, 8.5F, 6.5F, 11.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(2F, 11.5F, 3F, 14.5F).texture("#0").end()
              .face(Direction.WEST).uvs(6.5F, 8.5F, 8.5F, 11.5F).texture("#0").end()
              .face(Direction.UP).uvs(8F, 13.5F, 7F, 11.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(12.5F, 10.5F, 11.5F, 12.5F).texture("#0").end()
              .end();

        //tank2
        model.element()
              .from(2F, 4F, 4F)
              .to(6F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(2F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(8.5F, 10.5F, 9.5F, 13.5F).texture("#0").end()
              .face(Direction.EAST).uvs(8F, 2.5F, 10F, 5.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(9.5F, 10.5F, 10.5F, 13.5F).texture("#0").end()
              .face(Direction.WEST).uvs(2.5F, 8.5F, 4.5F, 11.5F).texture("#0").end()
              .face(Direction.UP).uvs(12F, 10.5F, 11F, 8.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(12.5F, 5.5F, 11.5F, 7.5F).texture("#0").end()
              .end();

        //adsorbent
        model.element()
              .from(7F, 4F, 2F)
              .to(9F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(7F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(12F, 8.5F, 12.5F, 10.5F).texture("#0").end()
              .face(Direction.EAST).uvs(8.5F, 8.5F, 11F, 10.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(12.5F, 5.5F, 13F, 7.5F).texture("#0").end()
              .face(Direction.WEST).uvs(9F, 5.5F, 11.5F, 7.5F).texture("#0").end()
              .face(Direction.UP).uvs(8.5F, 14F, 8F, 11.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13F, 8.5F, 12.5F, 11F).texture("#0").end()
              .end();

        //connector1
        model.element()
              .from(9F, 7F, 7F)
              .to(10F, 9F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(9F, 7F, 7F).end()
              .face(Direction.NORTH).uvs(13.5F, 6.5F, 13.75F, 7F).texture("#0").end()
              .face(Direction.EAST).uvs(3.5F, 8F, 4F, 8.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(7F, 13.5F, 7.25F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(2F, 10.5F, 2.5F, 11F).texture("#0").end()
              .face(Direction.UP).uvs(13.75F, 7.5F, 13.5F, 7F).texture("#0").end()
              .face(Direction.DOWN).uvs(7.5F, 13.5F, 7.25F, 14F).texture("#0").end()
              .end();

        //connector2
        model.element()
              .from(6F, 7F, 7F)
              .to(7F, 9F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 7F, 7F).end()
              .face(Direction.NORTH).uvs(7.5F, 13.5F, 7.75F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(2F, 11F, 2.5F, 11.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(13.5F, 7.5F, 13.75F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(1.5F, 13.5F, 2F, 14F).texture("#0").end()
              .face(Direction.UP).uvs(8F, 14F, 7.75F, 13.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13.75F, 8F, 13.5F, 8.5F).texture("#0").end()
              .end();

        //behind
        model.element()
              .from(0F, 4F, 12F)
              .to(16F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(4F, 0F, 8F, 3F).texture("#0").end()
              .face(Direction.EAST).uvs(0F, 10.5F, 1F, 13.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(4F, 3F, 8F, 6F).texture("#0").end()
              .face(Direction.WEST).uvs(1F, 10.5F, 2F, 13.5F).texture("#0").end()
              .face(Direction.UP).uvs(13F, 8.5F, 9F, 7.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(14F, 2.5F, 10F, 3.5F).texture("#0").end()
              .end();

        //fence1
        model.element()
              .from(14F, 4F, 2F)
              .to(15F, 14F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(0F, 13.5F, 0.25F, 16F).texture("#0").end()
              .face(Direction.EAST).uvs(0F, 8F, 2.5F, 10.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0.25F, 13.5F, 0.5F, 16F).texture("#0").end()
              .face(Direction.WEST).uvs(8F, 0F, 10.5F, 2.5F).texture("#0").end()
              .face(Direction.UP).uvs(0.75F, 16F, 0.5F, 13.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(1F, 13.5F, 0.75F, 16F).texture("#0").end()
              .end();

        //fence2
        model.element()
              .from(10F, 4F, 2F)
              .to(14F, 14F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(5F, 11.5F, 6F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(12F, 12.5F, 12.5F, 15F).texture("#0").end()
              .face(Direction.SOUTH).uvs(6F, 11.5F, 7F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(13F, 5.5F, 13.5F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(14.5F, 6F, 13.5F, 5.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(14.5F, 6F, 13.5F, 6.5F).texture("#0").end()
              .end();

        //fence3
        model.element()
              .from(1F, 4F, 2F)
              .to(2F, 14F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(1F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(13F, 13F, 13.25F, 15.5F).texture("#0").end()
              .face(Direction.EAST).uvs(4F, 6F, 6.5F, 8.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(13.25F, 8F, 13.5F, 10.5F).texture("#0").end()
              .face(Direction.WEST).uvs(6.5F, 6F, 9F, 8.5F).texture("#0").end()
              .face(Direction.UP).uvs(13.5F, 13F, 13.25F, 10.5F).texture("#0").end()
              .face(Direction.DOWN).uvs(13.5F, 13F, 13.25F, 15.5F).texture("#0").end()
              .end();

        //fence4
        model.element()
              .from(2F, 4F, 2F)
              .to(6F, 14F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(2F, 4F, 2F).end()
              .face(Direction.NORTH).uvs(3F, 11.5F, 4F, 14F).texture("#0").end()
              .face(Direction.EAST).uvs(12.5F, 11F, 13F, 13.5F).texture("#0").end()
              .face(Direction.SOUTH).uvs(4F, 11.5F, 5F, 14F).texture("#0").end()
              .face(Direction.WEST).uvs(11.5F, 12.5F, 12F, 15F).texture("#0").end()
              .face(Direction.UP).uvs(3.5F, 8.5F, 2.5F, 8F).texture("#0").end()
              .face(Direction.DOWN).uvs(9F, 5.5F, 8F, 6F).texture("#0").end()
              .end();

        //port1
        model.element()
              .from(15F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(15F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 16F, 1F).texture("#1").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").cullface(Direction.EAST).end()
              .face(Direction.UP).uvs(0F, 0F, 16F, 1F).texture("#1").end()
              .end();

        //port2
        model.element()
              .from(0F, 4F, 4F)
              .to(1F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(-1F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(1F, 0F, 16F, 1F).texture("#1").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0.25F, 2F).texture("#1").end()
              .end();

        //portLED1
        model.element()
              .from(16F, 5F, 5F)
              .to(16F, 11F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(18F, 5F, 5F).end()
              .emissivity(15, 15)
              .face(Direction.EAST).uvs(1F, 1F, 7F, 7F).texture("#2").cullface(Direction.EAST).end()
              .end();

        //portLED2
        model.element()
              .from(0F, 5F, 5F)
              .to(0F, 11F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 8F, 8F).end()
              .emissivity(15, 15)
              .face(Direction.WEST).uvs(1F, 1F, 7F, 7F).texture("#2").cullface(Direction.WEST).end()
              .end();
        return model;
    }

    public static BlockModelBuilder radiationIrradiator(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("radiation_irradiator", "block/cube_all");
        model.renderType("minecraft:cutout");
        model.texture("0", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_large_led"));
        model.texture("3", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));
        model.texture("4", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_window"));
        model.texture("particle", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));

        model.element()
              .from(0F, 4F, 12F)
              .to(4F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(7F, 10F, 8F, 12F).texture("#3").end()
              .face(Direction.EAST).uvs(10F, 9F, 11F, 11F).texture("#3").end()
              .face(Direction.SOUTH).uvs(11F, 0F, 12F, 2F).texture("#3").end()
              .face(Direction.WEST).uvs(11F, 2F, 12F, 4F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 13.5F, 12F, 12.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(14F, 0F, 13F, 1F).texture("#3").end()
              .end();

        model.element()
              .from(12F, 4F, 12F)
              .to(16F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(12F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(11F, 5F, 12F, 7F).texture("#3").end()
              .face(Direction.EAST).uvs(8F, 11F, 9F, 13F).texture("#3").end()
              .face(Direction.SOUTH).uvs(9F, 11F, 10F, 13F).texture("#3").end()
              .face(Direction.WEST).uvs(11F, 9F, 12F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(14F, 2F, 13F, 1F).texture("#3").end()
              .face(Direction.DOWN).uvs(3F, 13F, 2F, 14F).texture("#3").end()
              .end();

        model.element()
              .from(12F, 4F, 0F)
              .to(16F, 12F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(12F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(10F, 11F, 11F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(11F, 11F, 12F, 13F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 11.5F, 5F, 13.5F).texture("#3").end()
              .face(Direction.WEST).uvs(5F, 11.5F, 6F, 13.5F).texture("#3").end()
              .face(Direction.UP).uvs(4F, 14F, 3F, 13F).texture("#3").end()
              .face(Direction.DOWN).uvs(14F, 4F, 13F, 5F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 4F, 0F)
              .to(4F, 12F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(6F, 11.5F, 7F, 13.5F).texture("#3").end()
              .face(Direction.EAST).uvs(0F, 12F, 1F, 14F).texture("#3").end()
              .face(Direction.SOUTH).uvs(12F, 0F, 13F, 2F).texture("#3").end()
              .face(Direction.WEST).uvs(1F, 12F, 2F, 14F).texture("#3").end()
              .face(Direction.UP).uvs(14F, 7F, 13F, 6F).texture("#3").end()
              .face(Direction.DOWN).uvs(9F, 13F, 8F, 14F).texture("#3").end()
              .end();

        model.element()
              .from(10F, 0F, 0F)
              .to(16F, 4F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(9F, 7F, 10.5F, 8F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 0F, 11F, 1F).texture("#3").end()
              .face(Direction.SOUTH).uvs(10.5F, 7F, 12F, 8F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 1F, 11F, 2F).texture("#3").end()
              .face(Direction.UP).uvs(5.5F, 4F, 4F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 4F, 4F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 0F, 0F)
              .to(6F, 4F, 16F)
              .face(Direction.NORTH).uvs(2F, 12F, 3.5F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 2F, 11F, 3F).texture("#3").end()
              .face(Direction.SOUTH).uvs(12F, 2F, 13.5F, 3F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 3F, 11F, 4F).texture("#3").end()
              .face(Direction.UP).uvs(7F, 4F, 5.5F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(7F, 4F, 5.5F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(14F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(9F, 13F, 9.5F, 15F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 4F, 9F, 6F).texture("#3").end()
              .face(Direction.SOUTH).uvs(9.5F, 13F, 10F, 15F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 6F, 9F, 8F).texture("#3").end()
              .face(Direction.UP).uvs(13.5F, 11.5F, 13F, 9.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(10.5F, 13F, 10F, 15F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 12F, 0F)
              .to(16F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 12F, 0F).end()
              .face(Direction.NORTH).uvs(4F, 8F, 8F, 9F).texture("#3").end()
              .face(Direction.EAST).uvs(8F, 8F, 12F, 9F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 9F, 8F, 10F).texture("#3").end()
              .face(Direction.WEST).uvs(9F, 4F, 13F, 5F).texture("#3").end()
              .face(Direction.UP).uvs(4F, 4F, 0F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(4F, 4F, 0F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(14F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(13.5F, 2F, 14F, 4F).texture("#3").end()
              .face(Direction.EAST).uvs(9F, 5F, 11F, 7F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 13.5F, 4.5F, 15.5F).texture("#3").end()
              .face(Direction.WEST).uvs(8F, 9F, 10F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(5F, 15.5F, 4.5F, 13.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 13.5F, 5F, 15.5F).texture("#3").end()
              .end();

        model.element()
              .from(1F, 4F, 4F)
              .to(2F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(1F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(13.5F, 2F, 14F, 4F).texture("#3").end()
              .face(Direction.EAST).uvs(9F, 5F, 11F, 7F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 13.5F, 4.5F, 15.5F).texture("#3").end()
              .face(Direction.WEST).uvs(8F, 9F, 10F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(5F, 15.5F, 4.5F, 13.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 13.5F, 5F, 15.5F).texture("#3").end()
              .end();

        model.element()
              .from(5F, 10F, 5F)
              .to(11F, 12F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(5F, 10F, 5F).end()
              .face(Direction.NORTH).uvs(13.5F, 5.5F, 15F, 6F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 13.5F, 13.5F, 14F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 12.5F, 15F, 13F).texture("#3").end()
              .face(Direction.WEST).uvs(13.5F, 13F, 15F, 13.5F).texture("#3").end()
              .face(Direction.UP).uvs(5.5F, 11.5F, 4F, 10F).texture("#3").end()
              .face(Direction.DOWN).uvs(7F, 10F, 5.5F, 11.5F).texture("#3").end()
              .end();

        model.element()
              .from(7F, 6F, 7F)
              .to(9F, 10F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(7F, 6F, 7F).end()
              .face(Direction.NORTH).uvs(3.5F, 12F, 4F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(7.5F, 13.5F, 8F, 14.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(1F, 14F, 1.5F, 15F).texture("#3").end()
              .face(Direction.WEST).uvs(1.5F, 14F, 2F, 15F).texture("#3").end()
              .face(Direction.UP).uvs(14.5F, 2F, 14F, 1.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(2.5F, 14F, 2F, 14.5F).texture("#3").end()
              .end();

        model.element()
              .from(4F, 4F, 14F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 14F).end()
              .face(Direction.NORTH).uvs(0F, 10F, 2F, 12F).texture("#3").end()
              .face(Direction.EAST).uvs(5.5F, 13.5F, 6F, 15.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(2F, 10F, 4F, 12F).texture("#3").end()
              .face(Direction.WEST).uvs(6F, 13.5F, 6.5F, 15.5F).texture("#3").end()
              .face(Direction.UP).uvs(15F, 7.5F, 13F, 7F).texture("#3").end()
              .face(Direction.DOWN).uvs(15.5F, 5F, 13.5F, 5.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 10F)
              .to(10F, 4F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 10F).end()
              .face(Direction.NORTH).uvs(6.5F, 13.5F, 7.5F, 14.5F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 3F, 13.5F, 4F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 7.5F, 14.5F, 8.5F).texture("#3").end()
              .face(Direction.WEST).uvs(12F, 5F, 13.5F, 6F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 7.5F, 12F, 6F).texture("#3").end()
              .face(Direction.DOWN).uvs(8F, 12F, 7F, 13.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 0F)
              .to(10F, 4F, 6F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(13.5F, 8.5F, 14.5F, 9.5F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 7.5F, 13.5F, 8.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 9.5F, 14.5F, 10.5F).texture("#3").end()
              .face(Direction.WEST).uvs(12F, 8.5F, 13.5F, 9.5F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 11F, 12F, 9.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(13F, 11F, 12F, 12.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 6F)
              .to(10F, 3F, 10F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 6F).end()
              .face(Direction.NORTH).uvs(13.5F, 13.5F, 14.5F, 14.25F).texture("#3").end()
              .face(Direction.EAST).uvs(0F, 14F, 1F, 14.75F).texture("#3").end()
              .face(Direction.SOUTH).uvs(14F, 0F, 15F, 0.75F).texture("#3").end()
              .face(Direction.WEST).uvs(14F, 0.75F, 15F, 1.5F).texture("#3").end()
              .face(Direction.UP).uvs(14.5F, 11.5F, 13.5F, 10.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(14.5F, 11.5F, 13.5F, 12.5F).texture("#3").end()
              .end();

        //window
        model.element()
              .from(4F, 4F, 0F)
              .to(12F, 12F, 0F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 16F, 16F).texture("#4").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#4").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 16F, 16F).texture("#4").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#4").end()
              .face(Direction.UP).uvs(8F, 0F, 0F, 0F).texture("#4").end()
              .face(Direction.DOWN).uvs(8F, 0F, 0F, 0F).texture("#4").end()
              .end();

        //port1
        model.element()
              .from(16F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(16F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //port2
        model.element()
              .from(0F, 4F, 4F)
              .to(0F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //port3
        model.element()
              .from(4F, 4F, 16F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 16F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 8F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .end();

        //portLED1
        model.element()
              .from(16F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(16F, 4F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //portLED2
        model.element()
              .from(0F, 4F, 4F)
              .to(0F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //portLED3
        model.element()
              .from(4F, 16F, 4F)
              .to(12F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 16F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.UP).uvs(0.5F, 0.5F, 4.5F, 4.5F).texture("#2").end()
              .face(Direction.DOWN).uvs(0F, 0F, 10F, 10F).texture("#missing").end()
              .end();

        //portLED4
        model.element()
              .from(4F, 4F, 16F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 16F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 8F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(8F, 8F, 16F, 16F).texture("#1").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .end();
        return model;
    }

    public static BlockModelBuilder radiationIrradiatorActive(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("radiation_irradiator_active", "block/cube_all");
        model.renderType("minecraft:cutout");
        model.texture("0", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_large_led"));
        model.texture("3", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_active"));
        model.texture("4", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_window"));
        model.texture("particle", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));

        model.element()
              .from(0F, 4F, 12F)
              .to(4F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(7F, 10F, 8F, 12F).texture("#3").end()
              .face(Direction.EAST).uvs(10F, 9F, 11F, 11F).texture("#3").end()
              .face(Direction.SOUTH).uvs(11F, 0F, 12F, 2F).texture("#3").end()
              .face(Direction.WEST).uvs(11F, 2F, 12F, 4F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 13.5F, 12F, 12.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(14F, 0F, 13F, 1F).texture("#3").end()
              .end();

        model.element()
              .from(12F, 4F, 12F)
              .to(16F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(12F, 4F, 12F).end()
              .face(Direction.NORTH).uvs(11F, 5F, 12F, 7F).texture("#3").end()
              .face(Direction.EAST).uvs(8F, 11F, 9F, 13F).texture("#3").end()
              .face(Direction.SOUTH).uvs(9F, 11F, 10F, 13F).texture("#3").end()
              .face(Direction.WEST).uvs(11F, 9F, 12F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(14F, 2F, 13F, 1F).texture("#3").end()
              .face(Direction.DOWN).uvs(3F, 13F, 2F, 14F).texture("#3").end()
              .end();

        model.element()
              .from(12F, 4F, 0F)
              .to(16F, 12F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(12F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(10F, 11F, 11F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(11F, 11F, 12F, 13F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 11.5F, 5F, 13.5F).texture("#3").end()
              .face(Direction.WEST).uvs(5F, 11.5F, 6F, 13.5F).texture("#3").end()
              .face(Direction.UP).uvs(4F, 14F, 3F, 13F).texture("#3").end()
              .face(Direction.DOWN).uvs(14F, 4F, 13F, 5F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 4F, 0F)
              .to(4F, 12F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(6F, 11.5F, 7F, 13.5F).texture("#3").end()
              .face(Direction.EAST).uvs(0F, 12F, 1F, 14F).texture("#3").end()
              .face(Direction.SOUTH).uvs(12F, 0F, 13F, 2F).texture("#3").end()
              .face(Direction.WEST).uvs(1F, 12F, 2F, 14F).texture("#3").end()
              .face(Direction.UP).uvs(14F, 7F, 13F, 6F).texture("#3").end()
              .face(Direction.DOWN).uvs(9F, 13F, 8F, 14F).texture("#3").end()
              .end();

        model.element()
              .from(10F, 0F, 0F)
              .to(16F, 4F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(10F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(9F, 7F, 10.5F, 8F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 0F, 11F, 1F).texture("#3").end()
              .face(Direction.SOUTH).uvs(10.5F, 7F, 12F, 8F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 1F, 11F, 2F).texture("#3").end()
              .face(Direction.UP).uvs(5.5F, 4F, 4F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 4F, 4F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 0F, 0F)
              .to(6F, 4F, 16F)
              .face(Direction.NORTH).uvs(2F, 12F, 3.5F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 2F, 11F, 3F).texture("#3").end()
              .face(Direction.SOUTH).uvs(12F, 2F, 13.5F, 3F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 3F, 11F, 4F).texture("#3").end()
              .face(Direction.UP).uvs(7F, 4F, 5.5F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(7F, 4F, 5.5F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(14F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(9F, 13F, 9.5F, 15F).texture("#3").end()
              .face(Direction.EAST).uvs(7F, 4F, 9F, 6F).texture("#3").end()
              .face(Direction.SOUTH).uvs(9.5F, 13F, 10F, 15F).texture("#3").end()
              .face(Direction.WEST).uvs(7F, 6F, 9F, 8F).texture("#3").end()
              .face(Direction.UP).uvs(13.5F, 11.5F, 13F, 9.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(10.5F, 13F, 10F, 15F).texture("#3").end()
              .end();

        model.element()
              .from(0F, 12F, 0F)
              .to(16F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 12F, 0F).end()
              .face(Direction.NORTH).uvs(4F, 8F, 8F, 9F).texture("#3").end()
              .face(Direction.EAST).uvs(8F, 8F, 12F, 9F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 9F, 8F, 10F).texture("#3").end()
              .face(Direction.WEST).uvs(9F, 4F, 13F, 5F).texture("#3").end()
              .face(Direction.UP).uvs(4F, 4F, 0F, 0F).texture("#3").end()
              .face(Direction.DOWN).uvs(4F, 4F, 0F, 8F).texture("#3").end()
              .end();

        model.element()
              .from(14F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(14F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(13.5F, 2F, 14F, 4F).texture("#3").end()
              .face(Direction.EAST).uvs(9F, 5F, 11F, 7F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 13.5F, 4.5F, 15.5F).texture("#3").end()
              .face(Direction.WEST).uvs(8F, 9F, 10F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(5F, 15.5F, 4.5F, 13.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 13.5F, 5F, 15.5F).texture("#3").end()
              .end();

        model.element()
              .from(1F, 4F, 4F)
              .to(2F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(1F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(13.5F, 2F, 14F, 4F).texture("#3").end()
              .face(Direction.EAST).uvs(9F, 5F, 11F, 7F).texture("#3").end()
              .face(Direction.SOUTH).uvs(4F, 13.5F, 4.5F, 15.5F).texture("#3").end()
              .face(Direction.WEST).uvs(8F, 9F, 10F, 11F).texture("#3").end()
              .face(Direction.UP).uvs(5F, 15.5F, 4.5F, 13.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(5.5F, 13.5F, 5F, 15.5F).texture("#3").end()
              .end();

        model.element()
              .from(5F, 10F, 5F)
              .to(11F, 12F, 11F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(5F, 10F, 5F).end()
              .face(Direction.NORTH).uvs(13.5F, 5.5F, 15F, 6F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 13.5F, 13.5F, 14F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 12.5F, 15F, 13F).texture("#3").end()
              .face(Direction.WEST).uvs(13.5F, 13F, 15F, 13.5F).texture("#3").end()
              .face(Direction.UP).uvs(5.5F, 11.5F, 4F, 10F).texture("#3").end()
              .face(Direction.DOWN).uvs(7F, 10F, 5.5F, 11.5F).texture("#3").end()
              .end();

        model.element()
              .from(7F, 6F, 7F)
              .to(9F, 10F, 9F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(7F, 6F, 7F).end()
              .face(Direction.NORTH).uvs(3.5F, 12F, 4F, 13F).texture("#3").end()
              .face(Direction.EAST).uvs(7.5F, 13.5F, 8F, 14.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(1F, 14F, 1.5F, 15F).texture("#3").end()
              .face(Direction.WEST).uvs(1.5F, 14F, 2F, 15F).texture("#3").end()
              .face(Direction.UP).uvs(14.5F, 2F, 14F, 1.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(2.5F, 14F, 2F, 14.5F).texture("#3").end()
              .end();

        model.element()
              .from(4F, 4F, 14F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 14F).end()
              .face(Direction.NORTH).uvs(0F, 10F, 2F, 12F).texture("#3").end()
              .face(Direction.EAST).uvs(5.5F, 13.5F, 6F, 15.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(2F, 10F, 4F, 12F).texture("#3").end()
              .face(Direction.WEST).uvs(6F, 13.5F, 6.5F, 15.5F).texture("#3").end()
              .face(Direction.UP).uvs(15F, 7.5F, 13F, 7F).texture("#3").end()
              .face(Direction.DOWN).uvs(15.5F, 5F, 13.5F, 5.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 10F)
              .to(10F, 4F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 10F).end()
              .face(Direction.NORTH).uvs(6.5F, 13.5F, 7.5F, 14.5F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 3F, 13.5F, 4F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 7.5F, 14.5F, 8.5F).texture("#3").end()
              .face(Direction.WEST).uvs(12F, 5F, 13.5F, 6F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 7.5F, 12F, 6F).texture("#3").end()
              .face(Direction.DOWN).uvs(8F, 12F, 7F, 13.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 0F)
              .to(10F, 4F, 6F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(13.5F, 8.5F, 14.5F, 9.5F).texture("#3").end()
              .face(Direction.EAST).uvs(12F, 7.5F, 13.5F, 8.5F).texture("#3").end()
              .face(Direction.SOUTH).uvs(13.5F, 9.5F, 14.5F, 10.5F).texture("#3").end()
              .face(Direction.WEST).uvs(12F, 8.5F, 13.5F, 9.5F).texture("#3").end()
              .face(Direction.UP).uvs(13F, 11F, 12F, 9.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(13F, 11F, 12F, 12.5F).texture("#3").end()
              .end();

        model.element()
              .from(6F, 0F, 6F)
              .to(10F, 3F, 10F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(6F, 0F, 6F).end()
              .face(Direction.NORTH).uvs(13.5F, 13.5F, 14.5F, 14.25F).texture("#3").end()
              .face(Direction.EAST).uvs(0F, 14F, 1F, 14.75F).texture("#3").end()
              .face(Direction.SOUTH).uvs(14F, 0F, 15F, 0.75F).texture("#3").end()
              .face(Direction.WEST).uvs(14F, 0.75F, 15F, 1.5F).texture("#3").end()
              .face(Direction.UP).uvs(14.5F, 11.5F, 13.5F, 10.5F).texture("#3").end()
              .face(Direction.DOWN).uvs(14.5F, 11.5F, 13.5F, 12.5F).texture("#3").end()
              .end();

        //window
        model.element()
              .from(4F, 4F, 0F)
              .to(12F, 12F, 0F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 16F, 16F).texture("#4").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#4").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 16F, 16F).texture("#4").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#4").end()
              .face(Direction.UP).uvs(8F, 0F, 0F, 0F).texture("#4").end()
              .face(Direction.DOWN).uvs(8F, 0F, 0F, 0F).texture("#4").end()
              .end();

        //port1
        model.element()
              .from(16F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(16F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //port2
        model.element()
              .from(0F, 4F, 4F)
              .to(0F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //port3
        model.element()
              .from(4F, 4F, 16F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 16F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 8F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .end();

        //portLED1
        model.element()
              .from(16F, 4F, 4F)
              .to(16F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(16F, 4F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //portLED2
        model.element()
              .from(0F, 4F, 4F)
              .to(0F, 12F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(0F, 4F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 8F, 8F).texture("#1").end()
              .face(Direction.UP).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .end();

        //portLED3
        model.element()
              .from(4F, 16F, 4F)
              .to(12F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 16F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 10F, 0F).texture("#missing").end()
              .face(Direction.UP).uvs(0.5F, 0.5F, 4.5F, 4.5F).texture("#2").end()
              .face(Direction.DOWN).uvs(0F, 0F, 10F, 10F).texture("#missing").end()
              .end();

        //portLED4
        model.element()
              .from(4F, 4F, 16F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 16F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 8F, 8F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(8F, 8F, 16F, 16F).texture("#1").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 8F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 8F, 0F).texture("#missing").end()
              .end();
        return model;
    }

    public static BlockModelBuilder seawaterPump(BlockModelProvider models) {
        BlockModelBuilder model = models.withExistingParent("seawater_pump", "block/cube_all");
        model.renderType("minecraft:cutout");
        model.texture("0", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", ResourceLocation.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/seawater_pump"));
        model.texture("particle", ResourceLocation.fromNamespaceAndPath("mekanismelements", "block/seawater_pump"));

        model.element()
              .from(4F, 12F, 0F)
              .to(12F, 15F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 12F, 0F).end()
              .face(Direction.NORTH).uvs(6.375F, 4.75F, 7.375F, 5.125F).texture("#2").end()
              .face(Direction.EAST).uvs(6.375F, 1F, 8.375F, 1.375F).texture("#2").end()
              .face(Direction.SOUTH).uvs(6.375F, 5.125F, 7.375F, 5.5F).texture("#2").end()
              .face(Direction.WEST).uvs(6.375F, 1.375F, 8.375F, 1.75F).texture("#2").end()
              .face(Direction.UP).uvs(1F, 6F, 0F, 4F).texture("#2").end()
              .face(Direction.DOWN).uvs(5F, 0F, 4F, 2F).texture("#2").end()
              .end();

        model.element()
              .from(4F, 0F, 0F)
              .to(12F, 4F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(6.375F, 1.75F, 7.375F, 2.25F).texture("#2").end()
              .face(Direction.EAST).uvs(5F, 0F, 7F, 0.5F).texture("#2").end()
              .face(Direction.SOUTH).uvs(6.375F, 2.25F, 7.375F, 2.75F).texture("#2").end()
              .face(Direction.WEST).uvs(5F, 0.5F, 7F, 1F).texture("#2").end()
              .face(Direction.UP).uvs(2F, 6F, 1F, 4F).texture("#2").end()
              .face(Direction.DOWN).uvs(3F, 4F, 2F, 6F).texture("#2").end()
              .end();

        model.element()
              .from(12F, 0F, 0F)
              .to(16F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(12F, 0F, 0F).end()
              .face(Direction.NORTH).uvs(3F, 5F, 3.5F, 7F).texture("#2").end()
              .face(Direction.EAST).uvs(0F, 0F, 2F, 2F).texture("#2").end()
              .face(Direction.SOUTH).uvs(3.5F, 5F, 4F, 7F).texture("#2").end()
              .face(Direction.WEST).uvs(0F, 2F, 2F, 4F).texture("#2").end()
              .face(Direction.UP).uvs(4.5F, 7F, 4F, 5F).texture("#2").end()
              .face(Direction.DOWN).uvs(6.375F, 1F, 5.875F, 3F).texture("#2").end()
              .end();

        model.element()
              .from(0F, 0F, 0F)
              .to(4F, 16F, 16F)
              .face(Direction.NORTH).uvs(5.875F, 3F, 6.375F, 5F).texture("#2").end()
              .face(Direction.EAST).uvs(2F, 0F, 4F, 2F).texture("#2").end()
              .face(Direction.SOUTH).uvs(5.875F, 5F, 6.375F, 7F).texture("#2").end()
              .face(Direction.WEST).uvs(2F, 2F, 4F, 4F).texture("#2").end()
              .face(Direction.UP).uvs(0.5F, 8F, 0F, 6F).texture("#2").end()
              .face(Direction.DOWN).uvs(1F, 6F, 0.5F, 8F).texture("#2").rotation(FaceRotation.UPSIDE_DOWN).end()
              .end();

        model.element()
              .from(4F, 4F, 0F)
              .to(12F, 12F, 15F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 0F).end()
              .face(Direction.NORTH).uvs(1F, 6F, 2F, 7F).texture("#2").end()
              .face(Direction.EAST).uvs(4F, 2F, 5.875F, 3F).texture("#2").end()
              .face(Direction.SOUTH).uvs(2F, 6F, 3F, 7F).texture("#2").end()
              .face(Direction.WEST).uvs(3F, 4F, 4.875F, 5F).texture("#2").end()
              .face(Direction.UP).uvs(5.875F, 4.875F, 4.875F, 3F).texture("#2").end()
              .face(Direction.DOWN).uvs(5.875F, 4.875F, 4.875F, 6.75F).texture("#2").end()
              .end();

        model.element()
              .from(4F, 15F, 12F)
              .to(12F, 16F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 15F, 12F).end()
              .face(Direction.NORTH).uvs(6.375F, 5.5F, 7.375F, 5.625F).texture("#2").end()
              .face(Direction.EAST).uvs(4F, 3F, 4.5F, 3.125F).texture("#2").end()
              .face(Direction.SOUTH).uvs(6.375F, 5.625F, 7.375F, 5.75F).texture("#2").end()
              .face(Direction.WEST).uvs(4F, 3.125F, 4.5F, 3.25F).texture("#2").end()
              .face(Direction.UP).uvs(7.375F, 3.25F, 6.375F, 2.75F).texture("#2").end()
              .face(Direction.DOWN).uvs(7.375F, 3.25F, 6.375F, 3.75F).texture("#2").end()
              .end();

        model.element()
              .from(4F, 15F, 0F)
              .to(12F, 16F, 4F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 15F, 0F).end()
              .face(Direction.NORTH).uvs(6.375F, 5.75F, 7.375F, 5.875F).texture("#2").end()
              .face(Direction.EAST).uvs(4F, 3.25F, 4.5F, 3.375F).texture("#2").end()
              .face(Direction.SOUTH).uvs(6.375F, 5.875F, 7.375F, 6F).texture("#2").end()
              .face(Direction.WEST).uvs(4F, 3.375F, 4.5F, 3.5F).texture("#2").end()
              .face(Direction.UP).uvs(7.375F, 4.25F, 6.375F, 3.75F).texture("#2").end()
              .face(Direction.DOWN).uvs(7.375F, 4.25F, 6.375F, 4.75F).texture("#2").end()
              .end();

        //portLED1
        model.element()
              .from(4F, 16F, 4F)
              .to(12F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 17F, 4F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 0.75F, 0F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0.75F, 0F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 0.75F, 0F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 0.75F, 0F).texture("#missing").end()
              .face(Direction.UP).uvs(8F, 0F, 16F, 8F).texture("#1").end()
              .face(Direction.DOWN).uvs(0F, 0F, 0.75F, 0.75F).texture("#missing").end()
              .end();

        //portLED2
        model.element()
              .from(4F, 4F, 16F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 4F, 16F).end()
              .emissivity(15, 15)
              .face(Direction.NORTH).uvs(0F, 0F, 1F, 1F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 0F, 1F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(8F, 8F, 16F, 16F).texture("#1").end()
              .face(Direction.WEST).uvs(0F, 0F, 0F, 1F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 1F, 0F).texture("#missing").end()
              .face(Direction.DOWN).uvs(0F, 0F, 1F, 0F).texture("#missing").end()
              .end();

        //port1
        model.element()
              .from(4F, 15F, 4F)
              .to(12F, 16F, 12F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 15F, 4F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 2F, 0.25F).texture("#missing").end()
              .face(Direction.EAST).uvs(0F, 0F, 2F, 0.25F).texture("#missing").end()
              .face(Direction.SOUTH).uvs(0F, 0F, 2F, 0.25F).texture("#missing").end()
              .face(Direction.WEST).uvs(0F, 0F, 2F, 0.25F).texture("#missing").end()
              .face(Direction.UP).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.DOWN).uvs(0F, 0F, 2F, 2F).texture("#missing").end()
              .end();

        //port2
        model.element()
              .from(4F, 4F, 15F)
              .to(12F, 12F, 16F)
              .rotation().angle(0F).axis(Direction.Axis.Y).origin(4F, 12F, 15F).end()
              .face(Direction.NORTH).uvs(0F, 0F, 1F, 1F).texture("#missing").rotation(FaceRotation.UPSIDE_DOWN).end()
              .face(Direction.EAST).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(FaceRotation.COUNTERCLOCKWISE_90).end()
              .face(Direction.SOUTH).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(FaceRotation.CLOCKWISE_90).end()
              .face(Direction.UP).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(FaceRotation.UPSIDE_DOWN).end()
              .face(Direction.DOWN).uvs(0F, 0F, 1F, 0.125F).texture("#missing").end()
              .end();
        return model;
    }
}
