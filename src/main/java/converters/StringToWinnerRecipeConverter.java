package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.WinnerRecipeRepository;
import domain.WinnerRecipe;



@Component
@Transactional
public class StringToWinnerRecipeConverter  implements Converter<String, WinnerRecipe> {
	
	@Autowired
	WinnerRecipeRepository winnerRecipeRepository;
	
	@Override
	public WinnerRecipe convert(String text) {
		WinnerRecipe result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =winnerRecipeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}