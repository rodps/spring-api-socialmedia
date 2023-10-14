package com.rodrigo.socialmedia.domain;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NamingConventions;

public class MapperUtils {

    public static void atualizarDados(Object source, Object destination) {
        ModelMapper mm = new ModelMapper();
        Condition notBlankCondition = (ctx) -> {
            if (ctx.getSourceType() == String.class) {
                return ctx.getSource() != null && !((String) ctx.getSource()).isBlank();
            }
            return true;
        };
        mm.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true)
                .setPropertyCondition(notBlankCondition);
        mm.map(source, destination);
    }
}
