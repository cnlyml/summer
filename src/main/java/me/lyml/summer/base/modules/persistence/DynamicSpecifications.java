/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.base.modules.persistence;

import com.google.common.collect.Lists;
import me.lyml.summer.common.utils.Collections3;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: DynamicSpecifications
 * @author: cnlyml
 * @date: 2016/8/31 11:39
 */
public class DynamicSpecifications {
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {
        return (root, query, builder) -> {
            if (Collections3.isNotEmpty(filters)) {

                List<Predicate> predicates = Lists.newArrayList();
                for (SearchFilter filter : filters) {
                    // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                    String[] names = StringUtils.split(filter.fieldName, ".");

                    // logic operator
                    switch (filter.operator) {
                        case EQ:
                            predicates.add(builder.equal(DynamicSpecifications.getExpression(root, names), filter.value));
                            break;
                        case NEQ:
                            predicates.add(builder.notEqual(DynamicSpecifications.getExpression(root, names), filter.value));
                            break;
                        case LIKE:
                            predicates.add(builder.like(DynamicSpecifications.getExpression(root, names), "%" + filter.value + "%"));
                            break;
                        case LIKEP:
                            predicates.add(builder.like(DynamicSpecifications.getExpression(root, names), filter.value + "%"));
                            break;
                        case PLIKE:
                            predicates.add(builder.like(DynamicSpecifications.getExpression(root, names), "%" + filter.value));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(DynamicSpecifications.getExpression(root, names), (Comparable) filter.value));
                            break;
                        case LT:
                            predicates.add(builder.lessThan(DynamicSpecifications.getExpression(root, names), (Comparable) filter.value));
                            break;
                        case GTE:
                            predicates.add(builder.greaterThanOrEqualTo(DynamicSpecifications.getExpression(root, names), (Comparable) filter.value));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(DynamicSpecifications.getExpression(root, names), (Comparable) filter.value));
                            break;
                        case IN:
                            if(filter.value instanceof Iterable) {
                                predicates.add(builder.in(DynamicSpecifications.getExpression(root, names)).value(filter.value));
                            }else if(filter.value.getClass().isArray()) {
                                predicates.add(builder.in(DynamicSpecifications.getExpression(root, names)).value(Arrays.asList(filter.value)));
                            }else {
                                predicates.add(builder.equal(DynamicSpecifications.getExpression(root, names), filter.value));
                            }
                    }
                }
                predicates.add(builder.equal(DynamicSpecifications.getExpression(root, new String[]{"deleted"}), 0));
                // 将所有条件用 and 联合起来
                if (!predicates.isEmpty()) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }

            return builder.conjunction();
        };
    }

    private static <T> Expression<T> getExpression(Root<?> root, String[] names) {
        Path<T> expression = root.<T>get(names[0]);
        for (int i = 1; i < names.length; i++) {
            expression = expression.<T>get(names[i]);
        }
        return expression;
    }
}
