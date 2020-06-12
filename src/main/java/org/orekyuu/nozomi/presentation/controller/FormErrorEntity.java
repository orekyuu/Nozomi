package org.orekyuu.nozomi.presentation.controller;

import java.util.List;
import java.util.Map;

public class FormErrorEntity {
    List<String> globalErrors;
    Map<String, String> fieldErrors;

    public FormErrorEntity(List<String> globalErrors, Map<String, String> fieldErrors) {
        this.globalErrors = globalErrors;
        this.fieldErrors = fieldErrors;
    }
}
