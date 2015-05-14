package com.fighthard.dtodomain.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fighthard.util.IdHandler;

// Dto domain translate util.
public class ModelDataObjectUtil {
    private static Logger log = LoggerFactory
            .getLogger(ModelDataObjectUtil.class);

    public static <T> T model2do(Object model, Class<T> dataObjectClass) {
        if(model == null || null == dataObjectClass) {
            return null;
        }

        T dataObject = null;
        try {
            dataObject = dataObjectClass.newInstance();
        } catch(InstantiationException e1) {
            log.error(e1.getMessage(), e1);
        } catch(IllegalAccessException e2) {
            log.error(e2.getMessage(), e2);
        }

        List<Field> fields = new ArrayList<Field>();
        getAllField(fields, dataObject.getClass(), 1);
        for(Field field : fields) {
            try {
                if(field.isAnnotationPresent(Desensitize.class)) {
                    Desensitize desensitize = field
                            .getAnnotation(Desensitize.class);
                    String dtoFieldName = desensitize.fieldName();
                    if(StringUtils.isBlank(dtoFieldName)) {
                        dtoFieldName = field.getName();
                    }
                    Field modelField = getFiledByName(model.getClass(),
                            dtoFieldName);
                    field.setAccessible(true);
                    modelField.setAccessible(true);
                    Object fieldValue = null;
                    Object dtoFieldValue = modelField.get(model);
                    if(null == dtoFieldValue) {
                        fieldValue = null;
                    } else {
                        String fieldStrValue = IdHandler
                                .idDecrypt(dtoFieldValue.toString());
                        // field 类型暂时只能处理 Long，Integer，String
                        if(Long.class.equals(field.getType())) {
                            fieldValue = new Long(fieldStrValue);
                        } else if(Integer.class.equals(field.getType())) {
                            fieldValue = new Integer(fieldStrValue);
                        } else {
                            fieldValue = fieldStrValue;
                        }
                        log.debug(dtoFieldName + ":" + fieldValue);
                    }
                    field.set(dataObject, fieldValue);
                } else {
                    // model field handle
                    Field modelField = getFiledByName(model.getClass(),
                            field.getName());
                    field.setAccessible(true);
                    modelField.setAccessible(true);
                    field.set(dataObject, modelField.get(model));
                }

            } catch(NumberFormatException e) {
                log.error("数据转换错误", e);
            } catch(Exception e) {
                log.warn(e.getMessage());
                continue;
            }
        }
        return dataObject;
    }

    public static <T> T do2model(Object dataObject, Class<T> modelClass) {

        if(dataObject == null || null == modelClass) {
            return null;
        }
        T model = null;
        try {
            model = modelClass.newInstance();
        } catch(InstantiationException e1) {
            log.error(e1.getMessage(), e1);
        } catch(IllegalAccessException e1) {
            log.error(e1.getMessage(), e1);
        }

        List<Field> fields = new ArrayList<Field>();
        getAllField(fields, dataObject.getClass(), 1);
        for(Field field : fields) {
            try {
                if(field.isAnnotationPresent(Desensitize.class)) {
                    Desensitize desensitize = field
                            .getAnnotation(Desensitize.class);
                    String dtoFieldName = desensitize.fieldName();
                    if(StringUtils.isBlank(dtoFieldName)) {
                        dtoFieldName = field.getName();
                    }
                    field.setAccessible(true);
                    Object fieldValue = field.get(dataObject);
                    Object dtoFieldValue;
                    if(null == fieldValue) {
                        dtoFieldValue = null;
                    } else {
                        dtoFieldValue = IdHandler.idEncrypt(fieldValue
                                .toString());
                        log.debug(dtoFieldName + ":" + dtoFieldValue.toString());
                    }
                    Field dtoField = getFiledByName(model.getClass(),
                            dtoFieldName);
                    dtoField.setAccessible(true);
                    dtoField.set(model, dtoFieldValue);
                } else {
                    Field modelField = getFiledByName(model.getClass(),
                            field.getName());

                    field.setAccessible(true);
                    modelField.setAccessible(true);
                    modelField.set(model, field.get(dataObject));
                }
            } catch(Exception e) {
                log.warn(e.getMessage());
                continue;
            }
        }

        return model;

    }

    private static List<Field> getAllField(List<Field> list,
            Class<?> currentClass, int i) {
        if(i >= 5) { // 5 ？
            return list;
        }
        try {
            Class<?> superClass = currentClass.getSuperclass();

            Field field[] = currentClass.getDeclaredFields();
            if(null != field) {
                CollectionUtils.addAll(list, field);
                return getAllField(list, superClass, ++i);
            }
            return list;
        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return list;
        }
    }

    private static Field getFiledByName(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch(SecurityException e) {
            log.error(e.getMessage(), e);
        } catch(NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
        if(field == null && clazz.getSuperclass() != null) {
            return getFiledByName(clazz.getSuperclass(), fieldName);
        } else {
            return field;
        }
    }

}
