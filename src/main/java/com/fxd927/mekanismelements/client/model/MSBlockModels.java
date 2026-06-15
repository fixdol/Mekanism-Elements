// package com.fxd927.mekanismelements.client.model;

// import com.fxd927.mekanismelements.common.MekanismElements;
// import net.minecraft.data.PackOutput;
// import net.minecraft.resources.ResourceLocation;
// import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
// import net.neoforged.neoforge.client.model.generators.ModelFile;
// import net.neoforged.neoforge.common.data.ExistingFileHelper;

// public class MSBlockModels extends BlockModelProvider {

//     public MSBlockModels(PackOutput output, ExistingFileHelper existingFileHelper) {
//         super(output, MekanismElements.MODID, existingFileHelper);
//     }

//     @Override
//     protected void registerModels() {
//         seawaterStill();
//         seawaterFlowing();
//     }

//     private void seawaterStill() {
//         ModelFile fluidBase = new ModelFile.UncheckedModelFile(
//                 ResourceLocation.fromNamespaceAndPath("neoforge", "block/fluid")
//         );

//         getBuilder("seawater_still")
//                 .parent(fluidBase)
//                 .texture("particle",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_still"))
//                 .texture("still",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_still"))
//                 .texture("flow",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_flow"));
//     }

//     private void seawaterFlowing() {
//         ModelFile fluidBase = new ModelFile.UncheckedModelFile(
//                 ResourceLocation.fromNamespaceAndPath("neoforge", "block/fluid")
//         );

//         getBuilder("seawater_flowing")
//                 .parent(fluidBase)
//                 .texture("particle",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_flow"))
//                 .texture("still",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_still"))
//                 .texture("flow",
//                         ResourceLocation.fromNamespaceAndPath(
//                                 MekanismElements.MODID, "liquid/seawater_flow"));
//     }
// }