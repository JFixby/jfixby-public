package com.jfixby.util.iso.api;

public interface ISOComponent {

	IsoTransformSpecs newTransformSpecs();

	IsoTransform newTransform(IsoTransformSpecs specs);

	IsoTransform getFallout(double pixels_to_game_meter);

}
