package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LearningMaterial;

@Component
@Transactional
public class LearningMaterialToStringConverter implements Converter<LearningMaterial, String> {
	
	public String convert(LearningMaterial learningMaterial) {
		String result;
		if (learningMaterial == null) {
			result = null;
		} else {
			result = String.valueOf(learningMaterial.getId());
		}
		return result;
	}

}
