package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MasterClass;

@Component
@Transactional
public class MasterClassToStringConverter implements Converter<MasterClass, String> {
	
	public String convert(MasterClass masterClass) {
		String result;
		if (masterClass == null) {
			result = null;
		} else {
			result = String.valueOf(masterClass.getId());
		}
		return result;
	}

}
