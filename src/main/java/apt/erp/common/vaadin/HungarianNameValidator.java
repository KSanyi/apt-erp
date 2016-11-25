package apt.erp.common.vaadin;

import com.vaadin.data.validator.RegexpValidator;

@SuppressWarnings("serial")
public class HungarianNameValidator extends RegexpValidator {

	public HungarianNameValidator(String errorMessage) {
		super("[a-zA-ZáÁéÉőŐúÚűŰóÓöÖüÜíÍ\\-\\.\\+ ]{2,}", errorMessage);
	}

}
