package fixdol.mekanismelements.common.datagen.providers;

import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public final class MSMachineModels {

    private MSMachineModels() {
    }

    /** Elements-based block model definition that keeps the Blockbench-style fluent API and emits through the 26.1 model templates. */
    public static final class Model {
        private final Identifier parent;
        private final Map<String, Identifier> textures = new LinkedHashMap<>();
        private final List<Element> elements = new ArrayList<>();

        private Model(String parent) {
            this.parent = Identifier.parse(parent);
        }

        public Model texture(String slot, Identifier texture) {
            textures.put(slot, texture);
            return this;
        }

        public Element element() {
            Element element = new Element(this);
            elements.add(element);
            return element;
        }

        public Identifier create(Identifier target, BiConsumer<Identifier, ModelInstance> output) {
            ExtendedModelTemplateBuilder template = ExtendedModelTemplateBuilder.builder().parent(parent);
            TextureMapping mapping = new TextureMapping();
            Map<String, TextureSlot> slots = new LinkedHashMap<>();
            textures.forEach((name, texture) -> {
                TextureSlot slot = TextureSlot.create(name);
                slots.put(name, slot);
                template.requiredTextureSlot(slot);
                mapping.put(slot, new Material(texture));
            });
            for (Element element : elements) {
                template.element(builder -> {
                    builder.from(element.from[0], element.from[1], element.from[2]).to(element.to[0], element.to[1], element.to[2]);
                    if (element.angle != 0F) {
                        builder.rotation(rotation -> rotation.origin(element.origin[0], element.origin[1], element.origin[2]).singleAxis(element.axis, element.angle));
                    }
                    if (element.lightEmission > 0) {
                        builder.lightEmission(element.lightEmission);
                    }
                    for (Face face : element.faces) {
                        builder.face(face.direction, faceBuilder -> {
                            faceBuilder.texture(slots.get(face.texture.substring(1)));
                            if (face.uvs != null) {
                                faceBuilder.uvs(face.uvs[0], face.uvs[1], face.uvs[2], face.uvs[3]);
                            }
                            if (face.cullface != null) {
                                faceBuilder.cullface(face.cullface);
                            }
                            faceBuilder.rotation(face.rotation);
                        });
                    }
                });
            }
            template.build().create(target, mapping, output);
            return target;
        }
    }

    public static final class Element {
        private final Model model;
        private final List<Face> faces = new ArrayList<>();
        private float[] from = {0F, 0F, 0F};
        private float[] to = {16F, 16F, 16F};
        private float[] origin = {0F, 0F, 0F};
        private Direction.Axis axis = Direction.Axis.Y;
        private float angle;
        private int lightEmission;

        private Element(Model model) {
            this.model = model;
        }

        public Element from(float x, float y, float z) {
            from = new float[]{x, y, z};
            return this;
        }

        public Element to(float x, float y, float z) {
            to = new float[]{x, y, z};
            return this;
        }

        public Rotation rotation() {
            return new Rotation(this);
        }

        public Element emissivity(int blockLight, int skyLight) {
            lightEmission = blockLight;
            return this;
        }

        public Face face(Direction direction) {
            return new Face(this, direction);
        }

        public Model end() {
            return model;
        }
    }

    public static final class Rotation {
        private final Element element;

        private Rotation(Element element) {
            this.element = element;
        }

        public Rotation angle(float angle) {
            element.angle = angle;
            return this;
        }

        public Rotation axis(Direction.Axis axis) {
            element.axis = axis;
            return this;
        }

        public Rotation origin(float x, float y, float z) {
            element.origin = new float[]{x, y, z};
            return this;
        }

        public Element end() {
            return element;
        }
    }

    public static final class Face {
        private final Element element;
        private final Direction direction;
        private float[] uvs;
        private String texture;
        private Direction cullface;
        private Quadrant rotation = Quadrant.R0;

        private Face(Element element, Direction direction) {
            this.element = element;
            this.direction = direction;
        }

        public Face uvs(float u1, float v1, float u2, float v2) {
            uvs = new float[]{u1, v1, u2, v2};
            return this;
        }

        public Face texture(String texture) {
            this.texture = texture;
            return this;
        }

        public Face cullface(Direction cullface) {
            this.cullface = cullface;
            return this;
        }

        public Face rotation(Quadrant rotation) {
            this.rotation = rotation;
            return this;
        }

        public Element end() {
            // Faces pointing at the undefined "#missing" slot belong to zero-thickness elements and are never visible
            if (!"#missing".equals(texture)) {
                element.faces.add(this);
            }
            return element;
        }
    }

    public static Model adsorptionSeparator() {
        Model model = new Model("block/block");
        model.texture("0", Identifier.fromNamespaceAndPath("mekanismelements", "block/adsorption_separator"));
        model.texture("1", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("2", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("particle", Identifier.fromNamespaceAndPath("mekanismelements", "block/adsorption_separator"));

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

    public static Model airCompressor() {
        Model model = new Model("mekanism:block/machine");
        model.texture("sides", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("front", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/front"));
        model.texture("west", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("east", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("south", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/back"));
        model.texture("up", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/top"));
        model.texture("down", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/bottom"));
        return model;
    }

    public static Model airCompressorActive() {
        Model model = new Model("mekanism:block/machine");
        model.texture("sides", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("front", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/front_active"));
        model.texture("west", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("east", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/side"));
        model.texture("south", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/back"));
        model.texture("up", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/top"));
        model.texture("down", Identifier.fromNamespaceAndPath("mekanismelements", "block/air_compressor/bottom"));
        return model;
    }

    public static Model chemicalDemolitionMachine() {
        Model model = new Model("block/block");
        model.texture("0", Identifier.fromNamespaceAndPath("mekanismelements", "block/chemical_demolition_machine"));
        model.texture("1", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("2", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("particle", Identifier.fromNamespaceAndPath("mekanismelements", "block/chemical_demolition_machine"));

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

    public static Model radiationIrradiator() {
        Model model = new Model("block/block");
        model.texture("0", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_large_led"));
        model.texture("3", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));
        model.texture("4", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_window"));
        model.texture("particle", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));

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

    public static Model radiationIrradiatorActive() {
        Model model = new Model("block/block");
        model.texture("0", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_large_led"));
        model.texture("3", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_active"));
        model.texture("4", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator_window"));
        model.texture("particle", Identifier.fromNamespaceAndPath("mekanismelements", "block/radiation_irradiator"));

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

    public static Model seawaterPump() {
        Model model = new Model("block/block");
        model.texture("0", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports"));
        model.texture("1", Identifier.fromNamespaceAndPath("mekanism", "block/models/ports_led"));
        model.texture("2", Identifier.fromNamespaceAndPath("mekanismelements", "block/seawater_pump"));
        model.texture("particle", Identifier.fromNamespaceAndPath("mekanismelements", "block/seawater_pump"));

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
              .face(Direction.DOWN).uvs(1F, 6F, 0.5F, 8F).texture("#2").rotation(Quadrant.R180).end()
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
              .face(Direction.NORTH).uvs(0F, 0F, 1F, 1F).texture("#missing").rotation(Quadrant.R180).end()
              .face(Direction.EAST).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(Quadrant.R270).end()
              .face(Direction.SOUTH).uvs(0F, 0F, 8F, 8F).texture("#0").end()
              .face(Direction.WEST).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(Quadrant.R90).end()
              .face(Direction.UP).uvs(0F, 0F, 1F, 0.125F).texture("#missing").rotation(Quadrant.R180).end()
              .face(Direction.DOWN).uvs(0F, 0F, 1F, 0.125F).texture("#missing").end()
              .end();
        return model;
    }
}
