package com.saulius.quantum_world.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EnergyFuelRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation ID;
    private final NonNullList<Ingredient> RECIPE_ITEMS;
    private final int ENERGY_AMOUNT;
    private final int BURN_TIME;

    public EnergyFuelRecipe(ResourceLocation id, NonNullList<Ingredient> recipeItems, int energyAmount, int burnTime) {
        this.ID = id;
        this.RECIPE_ITEMS = recipeItems;
        this.ENERGY_AMOUNT = energyAmount;
        this.BURN_TIME = burnTime;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide)
            return false;
        return RECIPE_ITEMS.get(0).test(simpleContainer.getItem(1));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return RECIPE_ITEMS;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return ItemStack.EMPTY; //OUTPUT
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override //WARNING in logger - probably result of EMPTY ItemStack
    public ItemStack getResultItem() {
        return ItemStack.EMPTY; //OUTPUT.copy()
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public int getEnergyAmount() { return ENERGY_AMOUNT; }

    public int getBurnTime() { return BURN_TIME; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<EnergyFuelRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        //public static final String ID = "energy_fuel";
    }

    public static class Serializer implements RecipeSerializer<EnergyFuelRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        //public static final ResourceLocation ID = new ResourceLocation(QuantumWorld.MODID, "energy_fuel");

        @Override
        public EnergyFuelRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            //ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int x = 0; x < inputs.size(); x++) {
                inputs.set(x, Ingredient.fromJson(ingredients.get(x)));
            }
            return new EnergyFuelRecipe(resourceLocation, inputs,
                    GsonHelper.getAsInt(jsonObject, "energy"), GsonHelper.getAsInt(jsonObject, "burn_time"));
        }

        @Override
        public @Nullable EnergyFuelRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int x = 0; x < inputs.size(); x++) {
                inputs.set(x, Ingredient.fromNetwork(friendlyByteBuf));
            }
            //ItemStack output = friendlyByteBuf.readItem();
            return new EnergyFuelRecipe(resourceLocation, inputs, friendlyByteBuf.readInt(), friendlyByteBuf.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, EnergyFuelRecipe energyFuelRecipe) {
            friendlyByteBuf.writeInt(energyFuelRecipe.getIngredients().size());
            friendlyByteBuf.writeInt(energyFuelRecipe.getEnergyAmount());
            friendlyByteBuf.writeInt(energyFuelRecipe.getBurnTime());

            for(Ingredient ingredient : energyFuelRecipe.getIngredients()) {
                ingredient.toNetwork(friendlyByteBuf);
            }
            //friendlyByteBuf.writeItemStack(energyFuelRecipe.getResultItem(), false);
        }
    }
}
