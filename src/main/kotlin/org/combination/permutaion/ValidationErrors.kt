package org.combination.permutaion

class ValidationErrors(errors:List<String>): Throwable(errors.joinToString(", ")) {
    val validationErrors = errors.map { ValidationError(it) }
}