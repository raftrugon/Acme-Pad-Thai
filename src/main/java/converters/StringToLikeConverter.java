package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikeRepository;
import domain.Like;



@Component
@Transactional
public class StringToLikeConverter  implements Converter<String, Like> {
	
	@Autowired
	LikeRepository likeRepository;
	
	@Override
	public Like convert(String text) {
		Like result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =likeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}