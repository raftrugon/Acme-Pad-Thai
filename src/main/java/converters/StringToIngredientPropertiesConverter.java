package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.IngredientPropertiesRepository;
import domain.IngredientProperties;



@Component
@Transactional
public class StringToIngredientPropertiesConverter  implements Converter<String, IngredientProperties> {
	
	@Autowired
	IngredientPropertiesRepository ingredientPropertiesRepository;
	
	@Override
	public IngredientProperties convert(String text) {
		IngredientProperties result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =ingredientPropertiesRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}