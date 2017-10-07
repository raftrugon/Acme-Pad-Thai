package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.IngredientProperties;



@Component
@Transactional
public class IngredientPropertiesToStringConverter implements Converter<IngredientProperties, String> {

	@Override
	public String convert(IngredientProperties ingredientProperties) {
		String result;

		if (ingredientProperties == null)
			result = null;
		else
			result = String.valueOf(ingredientProperties.getId());

		return result;
	}

}
