package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Cook;

@Component
@Transactional
public class CookToStringConverter implements Converter<Cook, String> {
	
	public String convert(Cook cook) {
		String result;
		if (cook == null) {
			result = null;
		} else {
			result = String.valueOf(cook.getId());
		}
		return result;
	}

}
