package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.NoAdmin;



@Component
@Transactional
public class NoAdminToStringConverter implements Converter<NoAdmin, String> {

	@Override
	public String convert(NoAdmin noAdmin) {
		String result;

		if (noAdmin == null)
			result = null;
		else
			result = String.valueOf(noAdmin.getId());

		return result;
	}

}
