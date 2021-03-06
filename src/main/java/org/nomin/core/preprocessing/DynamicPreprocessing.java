package org.nomin.core.preprocessing;

import org.apache.commons.beanutils.ConvertUtils;
import org.nomin.NominMapper;
import org.nomin.core.*;
import org.nomin.util.TypeInfo;

/**
 * Chooses and applies appropriate preprocessing depending on a value.
 * @author Dmitry Dobrynin
 *         Created: 12.05.2010 23:40:05
 */
public class DynamicPreprocessing extends Preprocessing {
    private TypeInfo typeInfo;
    private NominMapper mapper;
    private MappingCase mappingCase;

    public DynamicPreprocessing(TypeInfo typeInfo, NominMapper mapper, MappingCase mappingCase) {
        this.typeInfo = typeInfo;
        this.mapper = mapper;
        this.mappingCase = mappingCase;
    }

    public Object preprocess(Object source) {
        Class<?> targetClass = typeInfo.determineTypeDynamically(source);
        if (source == null || targetClass.isInstance(source)) return source;
        if (ConvertUtils.lookup(source.getClass(), targetClass) != null) return ConvertUtils.convert(source, targetClass);
        return mapper.map(source, targetClass, mappingCase.get());
    }
}
