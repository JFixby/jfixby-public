package com.jfixby.util.iso.api;

public interface ISOComponent {

	IsoTransformSpecs newTransformSpecs();

	IsoTransform newTransform(IsoTransformSpecs specs);

	IsoTransform getFallout();

}
