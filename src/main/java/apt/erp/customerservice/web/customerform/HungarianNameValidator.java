package apt.erp.customerservice.web.customerform;

import com.vaadin.data.validator.RegexpValidator;

@SuppressWarnings("serial")
class HungarianNameValidator extends RegexpValidator {

	public HungarianNameValidator(String errorMessage) {
		super("[a-zA-ZáÁéÉőŐúÚűŰóÓöÖüÜíÍ\\- ]{2,}", errorMessage);
	}

}
