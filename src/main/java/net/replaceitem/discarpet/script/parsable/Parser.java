package net.replaceitem.discarpet.script.parsable;

import carpet.script.Context;
import carpet.script.annotation.SimpleTypeConverter;
import carpet.script.exception.InternalExpressionException;
import carpet.script.exception.ThrowStatement;
import carpet.script.value.ListValue;
import carpet.script.value.MapValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import net.replaceitem.discarpet.Discarpet;
import net.replaceitem.discarpet.mixins.SimpleTypeConverterAccessor;
import net.replaceitem.discarpet.script.util.EnumUtil;
import net.replaceitem.discarpet.script.util.MapValueUtil;

import java.awt.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Parser is used to simplify the way parsable values are parsed from scarpet {@link MapValue}s to java classes.
 * Classes need to be annotated with {@link ParsableClass}.
 * A class also needs to implement {@link ParsableConstructor} and the {@link ParsableConstructor#construct(carpet.script.Context)} method,
 * and specify the class this {@link ParsableConstructor} will parse to as the generic type.
 * This is used to create the wanted value from the parsable class.
 * For instance, a parsable for {@link Color} could be called {@code ColorParsable},
 * and has the {@link Integer} fields {@code R}, {@code G} and {@code B}.
 * In the {@link ParsableConstructor#construct(carpet.script.Context)} method, 
 * the {@link Color#Color(int, int, int)} constructor could
 * call to return the actual {@link Color} object.
 * {@link ParsableConstructor#construct(carpet.script.Context)} is automatically called from the Parser.
 * A field in a {@link ParsableConstructor} class can also be annotated with {@link OptionalField},
 * which makes the {@link Parser} not throw an error when the field was not specified in the {@link MapValue}.
 * Finally, the {@link ParsableConstructor} class needs to be registered using {@link Parser#registerParsable(Class)}
 */

@SuppressWarnings("unchecked")
public class Parser {    
    private static final Map<Class<?>,Class<?>> parsableClasses = new HashMap<>();
    
    public static void registerParsable(Class<?> parsableClass) {
        Class<?> resultClass = getResultClassFromGeneric(parsableClass);
        if(resultClass == null)
            Discarpet.LOGGER.error("Error finding the result class for '{}' class", getClassName(parsableClass));
        parsableClasses.put(resultClass, parsableClass);
    }
    
    private static boolean hasRegisteredParser(Class<?> resultClass) {
        return parsableClasses.containsKey(resultClass);
    }

    public static <T> T parseType(Context context, Value value, Class<T> resultClass) {
        return parseType(context, value, resultClass, getClassName(resultClass));
    }
    
    public static <T, E extends Enum<E>> T parseType(Context context, Value value, Class<T> resultClass, String name) {
        if(Value.class.isAssignableFrom(resultClass)) {
            if(resultClass == Value.class) {
                return (T) value;
            }
            if(resultClass.isInstance(value)) {
                return (T) value;
            }
            throw new InternalExpressionException("Expected a " + resultClass.getSimpleName() + " value, but got a " + value.getTypeString());
        }
        
        if(resultClass.isEnum()) {
            return (T) parseEnum(value, (Class<E>) resultClass, name);
        }

        SimpleTypeConverter<Value, T> typeConverter = SimpleTypeConverterAccessor.callGet(resultClass);
        if(typeConverter != null) {
            T converted = typeConverter.convert(value, null);
            if(converted != null) return converted;
        }

        T primitive = tryParsePrimitive(value, resultClass, name);
        if(primitive != null) return primitive;
        
        Class<?> parsableClass = parsableClasses.get(resultClass);
        if (parsableClass != null) {
            if(ParsableConstructor.class.isAssignableFrom(parsableClass)) {
                if (!hasRegisteredParser(resultClass)) {
                    throw new InternalExpressionException("Could not parse " + name + ", since there was no registered parser");
                }
                Object parsedValue = callConstructor(parsableClass, name);
                T directlyCreated = ((ParsableConstructor<T>) parsedValue).tryCreateFromValueDirectly(value);
                if(directlyCreated != null) return directlyCreated;
                ParsableConstructor<T> parsable = (ParsableConstructor<T>) Parser.parseParsableType(context, value, parsedValue, (Class<Object>) parsableClass, name);
                try {
                    return parsable.construct(context);
                } catch (Exception e) {
                    rethrowUserException(e);
                    throw new InternalExpressionException("Could not parse " + name + ":\n" + e.getMessage());
                }
            } else if(Redirector.class.isAssignableFrom(parsableClass)) {
                Object parsedValue = callConstructor(parsableClass, name);
                return (T) parseParsableType(context, value, parsedValue, (Class<Object>) parsableClass, name);
            }
        }
        if(resultClass.getAnnotation(ParsableClass.class) == null) throw new IllegalArgumentException("Class " + resultClass + " is neither a parsable class or has a parsable class registered.");
        T parsedValue = callConstructor(resultClass, name);
        return parseParsableType(context, value, parsedValue, resultClass, name);
    }
    
    private static <T> T tryParsePrimitive(Value value, Class<T> parsableClass, String name) {
        if (parsableClass == Integer.class) {
            if (!(value instanceof NumericValue numericValue)) throw new InternalExpressionException("'" + name + "' value needs to be a number");
            return parsableClass.cast(numericValue.getInt());
        }
        if (parsableClass == Boolean.class) {
            return parsableClass.cast(value.getBoolean());
        }
        if (parsableClass == Double.class) {
            if (!(value instanceof NumericValue numericValue)) throw new InternalExpressionException("'" + name + "' value needs to be a number");
            return parsableClass.cast(numericValue.getDouble());
        }
        if (parsableClass == Float.class) {
            if (!(value instanceof NumericValue numericValue)) throw new InternalExpressionException("'" + name + "' value needs to be a number");
            return parsableClass.cast(numericValue.getFloat());
        }
        if (parsableClass == Long.class) {
            if (!(value instanceof NumericValue numericValue)) throw new InternalExpressionException("'" + name + "' value needs to be a number");
            return parsableClass.cast(numericValue.getLong());
        }
        if (parsableClass == String.class) {
            return parsableClass.cast(value.getString());
        }
        return null;
    }
    
    public static <T> T parseParsableType(Context context, Value value, T object, Class<T> parsableClass, String name) {
        try {
            if(object instanceof DirectParsable directParsable) {
                if(directParsable.tryParseDirectly(value)) {
                    return object;
                }
            }

            T parsedObject = parsableClass.cast(parseClass(context, value, parsableClass));
            
            if(Redirector.class.isAssignableFrom(parsableClass)) {
                Class<? extends T> redirectedClass = ((Redirector<T>) parsedObject).redirect();
                return parseType(context, value, redirectedClass);
            }
            return parsedObject;
        } catch (Exception e) {
            rethrowUserException(e);
            throw new InternalExpressionException("Could not parse '" + name + "' as '" + getClassName(parsableClass) + "':\n" + e.getMessage());
        }
    }
    
    
    public static <T> T parseClass(Context context, Value value, Class<T> parsableClass) {
        ParsableClass parsableClassAnnotation = getRequiredParsableClassAnnotation(parsableClass);
        String name = parsableClassAnnotation.name();
        if(!(value instanceof MapValue mapValue)) throw new InternalExpressionException("Could not parse " + name + ", value needs to be a map");
        Map<Value, Value> map = mapValue.getMap();
        Field[] declaredFields = parsableClass.getDeclaredFields();
        T parsedValue = callConstructor(parsableClass, name);
        for (Field field : declaredFields) {
            if(field.accessFlags().contains(AccessFlag.PRIVATE)) continue;
            assignField(context, map, field, parsedValue);
        }
        return parsedValue;
    }
    
    private static <T> T callConstructor(Class<T> clazz, String name) {
        try {
            return clazz.getDeclaredConstructor(new Class[]{}).newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Could not parse " + name + ":\n" + e.getMessage());
        }
    }

    private static <E extends Enum<E>> E parseEnum(Value value, Class<E> resultClass, String name) {
        String enumValue = value.getString();
        return EnumUtil.getEnumOrThrow(resultClass, enumValue, "Invalid value for '" + name + "'");
    }
    
    private static void assignField(Context context, Map<Value, Value> map, Field field, Object parsedValue) {
        String name = field.getName();
        Class<?> fieldType = field.getType();
        OptionalField optionalFieldAnnotation = field.getAnnotation(OptionalField.class);
        boolean required = optionalFieldAnnotation == null;
        try {
            Object object;
            Value value = MapValueUtil.getValueInMap(map, name, required);
            if(value != null) {
                if (fieldType == List.class) {
                    if (!(value instanceof ListValue listValue))
                        throw new InternalExpressionException("'" + name + "' value needs to be a list");
                    Type generic = getListType(field);
                    if(generic instanceof ParameterizedType parameterizedType && parameterizedType.getRawType() == List.class) {
                        Type actualGenericType = getActualGenericType(parameterizedType);
                        if(actualGenericType == null) throw new InternalExpressionException("No type argument on list");
                        if(!(actualGenericType instanceof Class<?> clazz)) throw new InternalExpressionException("Invalid type argument on list: " + actualGenericType);
                        object = parse2DList(context, listValue.getItems(), clazz,name);
                    } else if(generic instanceof Class<?> clazz) {
                        object = parseList(context, listValue.getItems(), clazz, name);
                    } else throw new InternalExpressionException("Invalid type parameter provided for field " + name);
                } else {
                    object = parseType(context, value, fieldType, name);
                }
                field.setAccessible(true);
                field.set(parsedValue, object);
            }
        } catch (IllegalAccessException e) {
            throw new InternalExpressionException("Could not assign field '" + name + "':\n" + e.getMessage());
        }
    }
    
    private static <T> List<T> parseList(Context context, List<Value> list, Class<T> generic, String name) {
        List<T> parsedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                Value value = list.get(i);
                T element = parseType(context, value, generic, generic.getSimpleName());
                parsedList.add(element);
            } catch (Exception e) {
                rethrowUserException(e);
                throw new InternalExpressionException("Could not parse value in '" + name + "' list with index " + i + ":\n" + e.getMessage());
            }
        }
        return parsedList;
    }

    private static <T> List<List<T>> parse2DList(Context context, List<Value> list, Class<T> generic, String name) {
        List<List<T>> parsedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                Value value = list.get(i);
                if(!(value instanceof ListValue listValue)) throw new InternalExpressionException("Item in " + name + " list needs to be a list");
                List<T> element = parseList(context, listValue.getItems(), generic, name);
                parsedList.add(element);
            } catch (Exception e) {
                rethrowUserException(e);
                throw new InternalExpressionException("Could not parse value in '" + name + "' list with index " + i + ":\n" + e.getMessage());
            }
        }
        return parsedList;
    }
    
    
    private static Class<?> getResultClassFromGeneric(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        if(genericInterfaces.length == 0) return null;
        Type genericInterface = genericInterfaces[0];
        Type actualGenericType = getActualGenericType(genericInterface);
        if(actualGenericType instanceof Class<?> genericClass) return genericClass;
        return null;
    }
    
    private static Type getListType(Field field) {
        Type genericType = field.getGenericType();
        return getActualGenericType(genericType);
    }
    
    public static Type getActualGenericType(Type type) {
        if(!(type instanceof ParameterizedType parameterizedType)) return null;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if(actualTypeArguments.length == 0) return null;
        return actualTypeArguments[0];
    }
    
    private static ParsableClass getRequiredParsableClassAnnotation(Class<?> parsableClass) {
        ParsableClass parsableClassAnnotation = parsableClass.getAnnotation(ParsableClass.class);
        if(parsableClassAnnotation == null) throw new InternalExpressionException("Trying to parse class " + parsableClass.getSimpleName() + " without ParsableClass annotation");
        return parsableClassAnnotation;
    }
    
    public static String getClassName(Class<?> clazz) {
        if(parsableClasses.containsKey(clazz)) {
            clazz = parsableClasses.get(clazz);
        }
        if(!clazz.isAnnotationPresent(ParsableClass.class)) return clazz.getSimpleName();
        ParsableClass parsableClassAnnotation = getRequiredParsableClassAnnotation(clazz);
        return parsableClassAnnotation.name();
    }
    
    private static void rethrowUserException(Exception e) throws ThrowStatement {
        if(e instanceof ThrowStatement throwStatement) throw throwStatement;
    } 
}
