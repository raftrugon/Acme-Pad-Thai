package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WinnerRecipe;



@Component
@Transactional
public class WinnerRecipeToStringConverter implements Converter<WinnerRecipe, String> {

	@Override
	public String convert(WinnerRecipe winnerRecipe) {
		String result;

		if (winnerRecipe == null)
			result = null;
		else
			result = String.valueOf(winnerRecipe.getId());

		return result;
	}

}
