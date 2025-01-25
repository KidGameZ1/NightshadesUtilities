package net.nightshade.nightshade_core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.nightshade.nightshade_core.NightshadeCoreMod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MathHelper {

    public static final double DAMAGE_CALCULATION_VALUE = 50;

    static Vector4f getColor(int pColor) {
        float f3 = (float) FastColor.ARGB32.alpha(pColor) / 255.0F;
        float f = (float)FastColor.ARGB32.red(pColor) / 255.0F;
        float f1 = (float)FastColor.ARGB32.green(pColor) / 255.0F;
        float f2 = (float)FastColor.ARGB32.blue(pColor) / 255.0F;
        return new Vector4f(f, f1, f2, f3);
    }

    public static double round(double no, int num) {
        return Math.floor(no * Math.pow(10, num)) / (Math.pow(10, num));
    }

    public static double defRound(double no) {
        return round(no, 2);
    }

    @Contract("_, _, _ -> !null")
    public static Vector3f color(int r, int g, int b) {
        return new Vector3f(r / 255f, g / 255f, b / 255f);
    }

    public static float calculateDamage(float damage, double armorValue, double armorToughnessValue) {
        double f = DAMAGE_CALCULATION_VALUE - armorToughnessValue / 4.0F;
        double defencePercentage = armorValue / (armorValue + f);
        return (float) (damage * (1f - defencePercentage));
    }

    @Contract("_, null -> fail")
    public static Vec3 getHandHoldingItemAngle(HumanoidArm arm, Entity entity) {
        return entity.position().add(entity.calculateViewVector(0.0F, entity.getYRot() + (float)(arm == HumanoidArm.RIGHT ? 80 : -80)).scale(0.5D));
    }

    @Contract("_, null, _ -> fail; null, _, _ -> fail")
    public static Vec3 rotateHorizontalVec(Vec3 source, Vec3 pivot, int angle) {
        double x = (pivot.x - source.x) * Math.cos(angle) - (pivot.y - source.y) * Math.sin(angle) + source.x;
        double z = (pivot.x - source.x) * Math.sin(angle) + (pivot.y - source.y) * Math.cos(angle) + source.y;
        return new Vec3(x, 0, z);
    }

    public static boolean isBetween(int start, int end, double val) {
        return Mth.clamp(val, start, end) == val;
    }

    public static boolean is2dBetween(double xVal, double yVal, int xStart, int yStart, int xEnd, int yEnd) {
        return isBetween(xStart, xEnd, xVal) && isBetween(yStart, yEnd, yVal);
    }

    public static int getLargest(@NotNull Collection<Integer> floats) {
        int largest = 0;
        for (int f : floats) {
            if (f > largest) largest = f;
        }
        return largest;
    }

    @Contract("_, _ -> new")
    public static Predicate<String> checkForInteger(int min, int max) {
        return s -> {
            try {
                int num = Integer.parseInt(s);
                return num >= min && num <= max;
            } catch (Exception e) {
                return false;
            }
        };
    }

    @Contract("null, _ -> fail")
    public static AABB getMineBox(LivingEntity entity, int size) {
        AABB aabb = new AABB(-size, -size, -size, size, size, size);//creating a normal box
        switch (entity.getDirection().getAxis()) { //set the length of the rotation's Axis to 0
            case X -> {
                aabb.setMinX(0);
                aabb.setMaxX(0);
            }
            case Y -> {
                aabb.setMinY(0);
                aabb.setMaxY(0);
            }
            case Z -> {
                aabb.setMinZ(0);
                aabb.setMaxZ(0);
            }
        }
        return aabb; //return the aabb
    }

    //the code you need
    public static void useBox(LivingEntity living, int size, BlockPos minePos) {
        AABB mineBox = getMineBox(living, size);
        mineBox = mineBox.move(minePos);
        BlockPos.betweenClosedStream(mineBox).forEach(pos -> {
            //what you want to do with every block-pos
        });
    }

    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Entity source, double range) {
        Level level = source.level();
        return getEntitiesAround(tClass, level, source.getBoundingBox(), range);
    }


    @Contract("_, null, _ -> fail; null, _, _ -> fail")
    public static void add(Supplier<Integer> getter, Consumer<Integer> setter, int change) {
        setter.accept(getter.get() + change);
    }

    public static void up1(Reference<Integer> reference) {
        add(reference::getIntValue, reference::setValue, 1);
    }

    public static void mul(Supplier<Integer> getter, Consumer<Integer> setter, int mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Double> getter, Consumer<Double> setter, double mul) {
        setter.accept(getter.get() * mul);
    }

    public static void mul(Supplier<Float> getter, Consumer<Float> setter, float mul) {
        setter.accept(getter.get() * mul);
    }


    @Contract("null, _, _ -> fail; _, _, _ -> new")
    public static ArrayList<Vec3> lineOfSight(Entity entity, double range, double scaling) {
        Vec3 viewVec = entity.calculateViewVector(entity.getXRot(), entity.getYRot());
        Vec3 viewVecWithLoc = viewVec.add(entity.getEyePosition());
        Vec3 end = viewVec.scale(range).add(entity.getEyePosition());
        BlockHitResult result = entity.level().clip(new ClipContext(viewVecWithLoc, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        Vec3 diff = result.getLocation().subtract(viewVecWithLoc);
        ArrayList<Vec3> list = new ArrayList<>();
        for (int i = 0; i < diff.length() / scaling; i++) {
            list.add(setLength(diff, i * scaling).add(entity.getEyePosition()));
        }
        return list;
    }

    public static ArrayList<Vec3> lineOfSight(Vec2 vec, Vec3 pos, double range, double scaling) {
        ArrayList<Vec3> line = new ArrayList<>();
        Vec3 vec3;
        for (double i = 0; i <= range; i+=scaling) {
            vec3 = calculateViewVector(vec.x, vec.y).scale(i).add(pos.x, pos.y, pos.z);
            line.add(vec3);
        }
        return line;
    }

    public static int count(@NotNull Collection<Integer> collection) {
        int count = 0;
        for (Integer integer : collection) {
            count += integer;
        }
        return count;
    }

    public static int getHighest(@NotNull Collection<Integer> collection) {
        int value = 0;
        for (int t : collection) {
            if (t > value) {
                value = t;
            }
        }
        return value;
    }

    public static List<BlockPos> makeLine(BlockPos a, BlockPos b, LineSize size) {
        List<BlockPos> list = new ArrayList<>();
        BlockPos diff = b.subtract(a);
        double horizontal = Mth.sqrt((diff.getX() * diff.getX()) + (diff.getZ() * diff.getZ()) + (diff.getY() * diff.getY()));
        int numPoints = (int) (size == LineSize.THIN ? horizontal * 20 : horizontal * 50);
        MiscHelper.repeat(numPoints, integer -> {
            double t = integer / (numPoints - 1.);
            list.add(makeLinePos(t, a, diff));
        });
        return list;
    }

    public static float makePercentage(int value, int maxValue) {
        return value * 1f / maxValue;
    }

    public static void forCube(BlockPos cube, Consumer<BlockPos> consumer) {
        MiscHelper.repeat(cube.getX(), integer -> MiscHelper.repeat(cube.getY(), integer1 -> MiscHelper.repeat(cube.getZ(), integer2 -> {
            consumer.accept(new BlockPos(integer, integer1, integer2));
        })));
    }

    private static BlockPos makeLinePos(double t, BlockPos a, BlockPos diff) {
        return new BlockPos((int) (a.getX() + diff.getX() * t), (int) (a.getY() + diff.getY() * t), (int) (a.getZ() + diff.getZ() * t));
    }

    public enum LineSize {
        THIN,
        THICK
    }

    public static Vec3 moveTowards(Vec3 source, Vec3 target, double range, boolean percentage) {
        Vec3 change = source.subtract(target);
        double dist = source.distanceTo(target);
        return percentage ? setLength(change, dist * range) : setLength(change, range);
    }

    @Nullable
    public static <T> T pickRandom(List<T> list) {
        return pickRandom(list, NightshadeCoreMod.RANDOM_SOURCE);
    }

    @Contract("null, _ -> fail; _, null -> fail")
    public static <T> T pickRandom(List<T> list, RandomSource source) {
        return list.isEmpty() ? null : list.get(Mth.nextInt(source, 0, list.size() - 1));
    }

    public static boolean chance(double chance, @Nullable Entity entity) {
        if (entity instanceof LivingEntity living) {
            return chance(chance, living);
        } else {
            return chance(chance, null);
        }
    }

    public static boolean chance(double chance, @Nullable LivingEntity living) {
        return Math.random() <= chance * (living != null ? (1 + living.getAttributeValue(Attributes.LUCK) / 100) : 1);
    }


    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Level level, AABB source, double range) {
        return level.getEntitiesOfClass(tClass, source.inflate(range));
    }

    public static <T extends Entity> @Nullable T getClosestEntity(Class<T> tClass, Entity source, double range) {
        List<T> entities = getEntitiesAround(tClass, source, range).stream().filter(t -> t.is(source)).sorted(Comparator.comparingDouble(value -> value.distanceTo(source))).toList();
        if (entities.isEmpty()) return null;
        return entities.get(0);
    }

    public static LivingEntity getClosestLiving(Entity source, double range) {
        return getClosestEntity(LivingEntity.class, source, range);
    }

    public static List<LivingEntity> getLivingAround(Entity source, double range) {
        return getEntitiesAround(LivingEntity.class, source, range);
    }

    public static Vec3 calculateViewVector(float horizontalHeightXAxis, float verticalYAxis) {
        float f = horizontalHeightXAxis * ((float)Math.PI / 180F);
        float f1 = -verticalYAxis * ((float)Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3(f3 * f4, -f5, f2 * f4);
    }

    public static <T extends Entity> List<T> getEntitiesAround(Class<T> tClass, Level level, Vec3 loc, double range) {
        return level.getEntitiesOfClass(tClass, new AABB(loc.x - range, loc.y - range, loc.z - range, loc.x + range, loc.y + range, loc.z + range));
    }

    public static List<Entity> getAllEntitiesInsideCylinder(float radius, Vec3 sourcePos, Vec2 rot, double range, Level level) {
        List<Entity> toReturn = new ArrayList<>();
        ArrayList<Vec3> lineOfSight = lineOfSight(rot, sourcePos, range, 0.1);
        lineOfSight.forEach(vec3 -> {
            List<Entity> entities = getEntitiesAround(Entity.class, level, vec3, radius);
            toReturn.addAll(entities.stream().filter(entity -> !toReturn.contains(entity)).toList());
        });
        return toReturn;
    }

    public static Vec2 createTargetRotation(Entity source, Entity target) {
        return createTargetRotationFromPos(source.position(), target.position());
    }

    @Contract("null, _ -> fail; _, null -> fail")
    public static Vec2 createTargetRotationFromPos(Vec3 source, Vec3 target) {
        double d0 = target.x - source.x;
        double d1 = target.y - source.y;
        double d2 = target.z - source.z;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(d1, d3) * (double)(180F / (float)Math.PI)))), Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F));
    }

    public static Vec2 createTargetRotationFromEyeHeight(Entity source, Entity target) {
        return createTargetRotationFromPos(source.getEyePosition(), target.getEyePosition());
    }

    public static boolean isBehind(Entity source, Entity target) {
        Vec3 vec32 = source.position();
        Vec3 vec31 = vec32.vectorTo(target.position()).normalize();
        vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
        return !(vec31.dot(target.getViewVector(1)) < 0.0D);
    }

    @Contract("null, _ -> fail")
    public static Vec3 minimiseLength(Vec3 source, double minimum) {
        if (source.length() > minimum) {
            return source;
        } else {
            double scale = minimum / source.length();
            return source.scale(scale);
        }
    }

    @Contract("null, _ -> fail")
    public static Vec3 maximiseLength(Vec3 source, double maximum) {
        if (source.length() < maximum) {
            return source;
        } else {
            double scale = maximum / source.length();
            return source.scale(scale);
        }
    }

    @Contract("null, _ -> fail")
    public static Vec3 setLength(Vec3 source, double value) {
        if (source.length() > value) {
            return maximiseLength(source, value);
        }
        return minimiseLength(source, value);
    }

    public static Vec3 getRandomOffsetForPos(Entity target, double dist, double maxOffset) {
        maxOffset *=2;
        RandomSource source = RandomSource.create();
        Vec2 rot = target.getRotationVector();
        Vec3 targetPos = calculateViewVector(rot.x, rot.y).scale(dist);
        Vec3 secPos = removeByScale(calculateViewVector(rot.x - 90, rot.y).scale(maxOffset * source.nextFloat()), 0.5);
        Vec3 thirdPos = removeByScale(calculateViewVector(rot.x, rot.y - 90).scale(maxOffset * source.nextFloat()), 0.5);
        return targetPos.add(secPos).add(thirdPos);
    }

    @Contract("null, _ -> fail")
    public static Vec3 removeByScale(Vec3 vec3, double scale) {
        double x = vec3.x;
        double y = vec3.y;
        double z = vec3.z;
        double halfX = (x - (x * scale));
        double halfY = (y - (y * scale));
        double halfZ = (z - (z * scale));
        return new Vec3(halfX, halfY, halfZ);
    }

    @Contract(value = "_ -> new", pure = true)
    public static Vector3i intToRGB(int in) {
        int r = in >> 16 & 255;
        int g = in >> 8 & 255;
        int b = in & 255;
        return new Vector3i(r, g, b);
    }

    public static int RGBtoInt(Vector3i in) {
        int r = in.x;
        int g = in.y;
        int b = in.z;
        return RGBAtoInt(r, g, b, 1);
    }

    public static int RGBtoInt(Vector3f in) {
        return RGBtoInt(fromFloat(in, 255));
    }

    public static int RGBAtoInt(int r, int g, int b, int a) {
        int returnable = (a << 8) + r;
        returnable = (returnable << 8) + g;
        return (returnable << 8) + b;
    }

    public static int setAlpha(int alpha, int RGB) {
        Vector3i internal = intToRGB(RGB);
        return RGBAtoInt(internal.x, internal.y, internal.z, alpha);
    }

    public static int RGBtoInt(float r, float g, float b) {
        return RGBtoInt(new Vector3f(r, g, b));
    }
    public static Vector3i fromFloat(Vector3f floatValue, int mul) {
        return new Vector3i((int) (floatValue.x * mul), (int) (floatValue.y * mul), (int) (floatValue.z * mul));
    }
}