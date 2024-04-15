package org.cirma.validor;

import java.util.List;

public interface FieldValidator {

    List<String> validateFields(List<String> args);
}
