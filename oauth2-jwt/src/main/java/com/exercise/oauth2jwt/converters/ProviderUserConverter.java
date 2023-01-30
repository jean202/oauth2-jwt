package com.exercise.oauth2jwt.converters;

public interface ProviderUserConverter<T, R> {

    R convert(T t);
}

