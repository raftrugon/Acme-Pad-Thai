package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorsementRepository;
import domain.Endorsement;



@Component
@Transactional
public class StringToEndorsementConverter  implements Converter<String, Endorsement> {
	
	@Autowired
	EndorsementRepository endorsementRepository;
	
	@Override
	public Endorsement convert(String text) {
		Endorsement result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result =endorsementRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}