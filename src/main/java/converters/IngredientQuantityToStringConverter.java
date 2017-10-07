package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.IngredientQuantity;



@Component
@Transactional
public class IngredientQuantityToStringConverter implements Converter<IngredientQuantity, String> {

	@Override
	public String convert(IngredientQuantity ingredientQuantity) {
		String result;

		if (ingredientQuantity == null)
			result = null;
		else
			result = String.valueOf(ingredientQuantity.getId());

		return result;
	}

}
