package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ForbiddenWord;



@Component
@Transactional
public class ForbiddenWordToStringConverter implements Converter<ForbiddenWord, String> {

	@Override
	public String convert(ForbiddenWord forbiddenWord) {
		String result;

		if (forbiddenWord == null)
			result = null;
		else
			result = String.valueOf(forbiddenWord.getId());

		return result;
	}

}
