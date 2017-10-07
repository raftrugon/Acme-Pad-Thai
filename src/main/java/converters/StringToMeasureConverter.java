package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MeasureRepository;
import domain.Measure;

@Component
@Transactional
public class StringToMeasureConverter implements Converter<String, Measure> {
	
	@Autowired
	MeasureRepository measureRepository;
	
	@Override
	public Measure convert(String text) {
		Measure result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = measureRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
