package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.NoAdminRepository;
import domain.NoAdmin;



@Component
@Transactional
public class StringToNoAdminConverter  implements Converter<String,NoAdmin> {
	
	@Autowired
	NoAdminRepository noAdminRepository;
	
	@Override
	public NoAdmin convert(String text) {
		NoAdmin result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =noAdminRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}