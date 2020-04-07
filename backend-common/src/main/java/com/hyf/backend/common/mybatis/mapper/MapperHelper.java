package com.hyf.backend.common.mybatis.mapper;

import com.hyf.backend.common.domain.Page;
import com.hyf.backend.common.domain.PageListBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapperHelper {

    public static <R, RE, K, M extends BaseMapperWithPK<R, RE, K>> R selectByPrimaryKeyGuaranteed
            (M mapper, K id) {
        R r = mapper.selectByPrimaryKey(id);
        if (null == r) {
            throw new IllegalArgumentException(mapper.getClass().getTypeName().replace("Mapper",
                    "") + " not found");
        }
        try {
            Method m = r.getClass().getMethod("getIsDeleted");
            Boolean isDeleted = (Boolean) m.invoke(r);
            if (isDeleted) {
                throw new IllegalArgumentException(mapper.getClass().getTypeName().replace
                        ("Mapper", "") + " not " +
                        "found");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                ignored) {
        }
        return r;
    }

    public static <R, RE, K, M extends BaseMapperWithPK<R, RE, K>> Map<K, R> selectMapByExample(M mapper, RE example,
                                                                                                String primaryKeyName) {
        List<R> list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<K, R> map = new HashMap<>();
        Method getPK;
        try {
            getPK = list.get(0).getClass().getMethod("get" + StringUtils.capitalize
                    (primaryKeyName));
        } catch (NoSuchMethodException ignored) {
            return Collections.emptyMap();
        }
        for (R item : list) {
            try {
                map.put((K) getPK.invoke(item), item);
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        }
        return map;
    }

    public static <R, RE, M extends BaseMapper<R, RE>, C, Q> PageListBO<R> selectPageByQuery(M mapper, RE example, C
            criteria, Q query, Page page) {
        setupCriteriaByQueryInfo(example, criteria, query);
        return selectPageByExample(mapper, example, page);
    }

    public static <R, RE, K, M extends BaseMapperWithPKAndBlobs<R, RE, K>> PageListBO<R> selectPageByExampleWithBlobs(M mapper, RE example, Page page) {
        try {
            long totalCnt = composeExampleOrderByClause(mapper, example, page);
            List<R> list = mapper.selectByExampleWithBLOBs(example);
            return new PageListBO<>(list, page, totalCnt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new PageListBO<>(Collections.emptyList(), page, 0);
        }
    }

    public static <R, RE, K, M extends BaseMapperWithPKAndBlobs<R, RE, K>, C, Q> PageListBO<R> selectPageByQueryWithBlobs(M mapper, RE example, C criteria, Q query, Page page) {
        setupCriteriaByQueryInfo(example, criteria, query);
        return selectPageByExampleWithBlobs(mapper, example, page);
    }

    public static <R, RE, M extends BaseMapper<R, RE>> PageListBO<R> selectPageByExample(M mapper,
                                                                                         RE example, Page
                                                                                                 page) {
        try {
            long totalCnt = composeExampleOrderByClause(mapper, example, page);
            List<R> list = mapper.selectByExample(example);
            return new PageListBO<>(list, page, totalCnt);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new PageListBO<>(Collections.emptyList(), page, 0);
        }
    }


    private static <RE, C, Q> void setupCriteriaByQueryInfo(RE example, C criteria, Q query) {
        Field[] fields = query.getClass().getDeclaredFields();
        Class<?> criteriaClass = criteria.getClass();
        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.getName().equals("orderBy")) {
                    Object orderBy = null;
                    try {
                        orderBy = query.getClass().getMethod("get" + StringUtils.capitalize(field
                                .getName()))
                                .invoke(query);

                        if (null != orderBy && orderBy instanceof String && !((String) orderBy)
                                .isEmpty()) {
                            example.getClass().getMethod("setOrderByClause", String.class).invoke
                                    (example, (String)
                                            orderBy);
                        }
                    } catch (IllegalAccessException | InvocationTargetException |
                            NoSuchMethodException e) {
                        log.error(e.getMessage(), e);
                    }
                    continue;
                }
                Object value = null;
                try {
                    value = query.getClass().getMethod("get" + StringUtils.capitalize(field
                            .getName())).invoke
                            (query);
                } catch (IllegalAccessException | InvocationTargetException |
                        NoSuchMethodException e) {
                    log.error(e.getMessage(), e);
                }
                String methodPrefix = "and" + StringUtils.capitalize(field.getName());
                if (null != value) {
                    if (value instanceof List) {
                        try {
                            criteriaClass.getMethod(methodPrefix + "In", List.class).invoke
                                    (criteria, value);
                        } catch (IllegalAccessException | InvocationTargetException |
                                NoSuchMethodException ignored) {
                        }
                    } else if (value instanceof String) {
                        try {
                            criteriaClass.getMethod(methodPrefix + "Like", String.class).invoke
                                    (criteria, "%" + value
                                            + "%");
                        } catch (IllegalAccessException | InvocationTargetException |
                                NoSuchMethodException ignored) {
                        }
                    } else if (value instanceof Number) {
                        if (field.getName().startsWith("min")) {
                            try {
                                criteriaClass.getMethod(methodPrefix.replace("andMin", "and") +
                                        "GreaterThanOrEqualTo", value.getClass()).invoke
                                        (criteria, value);
                            } catch (IllegalAccessException | InvocationTargetException |
                                    NoSuchMethodException
                                    ignored) {
                            }
                        } else if (field.getName().startsWith("max")) {
                            try {
                                criteriaClass.getMethod(methodPrefix.replace("andMax", "and") +
                                                "LessThanOrEqualTo",
                                        value.getClass()).invoke(criteria, value);
                            } catch (IllegalAccessException | InvocationTargetException |
                                    NoSuchMethodException
                                    ignored) {
                            }
                        } else {
                            try {
                                criteriaClass.getMethod(methodPrefix + "EqualTo", value.getClass
                                        ()).invoke(criteria,
                                        value);
                            } catch (IllegalAccessException | InvocationTargetException |
                                    NoSuchMethodException
                                    ignored) {
                            }
                        }
                    } else if (value instanceof Date) {
                        if (field.getName().endsWith("Start")) {
                            try {
                                String realFiledName = field.getName().substring(0, field.getName().indexOf("Start"));
                                methodPrefix = "and" + StringUtils.capitalize(realFiledName);
                                criteriaClass.getMethod(methodPrefix + "GreaterThanOrEqualTo", Date.class).invoke(criteria, value);
                            } catch (IllegalAccessException | InvocationTargetException |
                                    NoSuchMethodException ignored) {
                            }
                        }
                        if (field.getName().endsWith("End")) {
                            try {
                                String realFiledName = field.getName().substring(0, field.getName().indexOf("End"));
                                methodPrefix = "and" + StringUtils.capitalize(realFiledName);
                                criteriaClass.getMethod(methodPrefix + "LessThanOrEqualTo", Date.class).invoke(criteria, value);
                            } catch (IllegalAccessException | InvocationTargetException |
                                    NoSuchMethodException ignored) {
                            }
                        }

                    } else {
                        try {
                            criteriaClass.getMethod(methodPrefix + "EqualTo", value.getClass())
                                    .invoke(criteria, value);
                        } catch (IllegalAccessException | InvocationTargetException |
                                NoSuchMethodException ignored) {
                        }
                    }
                }
            }
        }

    }

    private static <R, RE, M extends BaseMapper<R, RE>> long composeExampleOrderByClause(M mapper,
                                                                                         RE example, Page
                                                                                                 page) throws Exception {
        long totalCnt = mapper.countByExample(example);
        int offset = page.getPageSize() * (page.getPageNo() - 1);
        int limit = page.getPageSize();
        String orderByClause = page.getOrderBy();
        if (null == orderByClause) {
            orderByClause = (String) example.getClass().getMethod("getOrderByClause").invoke
                    (example);
        }
        if (null == orderByClause) {
            orderByClause = "id desc";
        }
        example.getClass().getMethod("setOrderByClause", String.class).invoke(example,
                String.format("%s limit %d,%d", orderByClause, offset, limit));

        return totalCnt;
    }
}