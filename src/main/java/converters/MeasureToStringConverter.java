package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Measure;

@Component
@Transactional
public class MeasureToStringConverter implements Converter<Measure, String> {
	
	public String convert(Measure measure) {
		String result;
		if (measure == null) {
			result = null;
		} else {
			result = String.valueOf(measure.getId());
		}
		return result;
	}

}
