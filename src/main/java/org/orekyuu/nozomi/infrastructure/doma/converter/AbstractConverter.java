package org.orekyuu.nozomi.infrastructure.doma.converter;

import org.seasar.doma.jdbc.domain.DomainConverter;

import java.util.function.Function;

abstract class AbstractConverter<DOMAIN, PRIMITIVE> implements DomainConverter<DOMAIN, PRIMITIVE> {
    final Function<DOMAIN, PRIMITIVE> fromDomainToValue;
    final Function<PRIMITIVE, DOMAIN> fromValueToDomain;

    public AbstractConverter(Function<DOMAIN, PRIMITIVE> fromDomainToValue, Function<PRIMITIVE, DOMAIN> fromValueToDomain) {
        this.fromDomainToValue = fromDomainToValue;
        this.fromValueToDomain = fromValueToDomain;
    }

    @Override
    public PRIMITIVE fromDomainToValue(DOMAIN domain) {
        if (domain == null) {
            return null;
        }
        return fromDomainToValue.apply(domain);
    }

    @Override
    public DOMAIN fromValueToDomain(PRIMITIVE value) {
        if (value == null) {
            return null;
        }
        return fromValueToDomain.apply(value);
    }
}
