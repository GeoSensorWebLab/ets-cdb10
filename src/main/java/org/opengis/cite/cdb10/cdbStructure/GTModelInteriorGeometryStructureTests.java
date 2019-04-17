package org.opengis.cite.cdb10.cdbStructure;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.opengis.cite.cdb10.util.DirectoryStreamFilters;
import org.opengis.cite.cdb10.util.FilenamePatterns;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GTModelInteriorGeometryStructureTests extends Capability1Tests {
	
	/**
	 * Validates that GTModelInteriorGeometry filenames have valid codes/names.
	 * Test based on Section 3.4.3, Volume 1, OGC CDB Core Standard (Version 1.0)
	 *
	 * @throws IOException DirectoryStream error
	 */
	@Test
	public void verifyInteriorGeometryFile() throws IOException {
		Path gtModelInteriorGeomPath = Paths.get(this.path, "GTModel", "506_GTModelInteriorGeometry");

		if (Files.notExists(gtModelInteriorGeomPath)) {
			return;
		}

		ArrayList<String> errors = new ArrayList<String>();
		Pattern filePattern = Pattern.compile(FilenamePatterns.GTModelInteriorGeometry);

		for (Path category : Files.newDirectoryStream(gtModelInteriorGeomPath)) {
			DirectoryStream<Path> subcategories = Files.newDirectoryStream(category);

			for (Path subcategory : subcategories) {
				DirectoryStream<Path> featureTypes = Files.newDirectoryStream(subcategory);

				for (Path featureType : featureTypes) {
					DirectoryStream<Path> lods = Files.newDirectoryStream(featureType, DirectoryStreamFilters.lodFilter());
					
					for (Path lod : lods) {
						DirectoryStream<Path> files = Files.newDirectoryStream(lod);
						
						for (Path file : files) {
							String filename = file.getFileName().toString();
	
							if (StringUtils.countMatches(filename, "_") != 6) {
								errors.add("Should be six underscore separators: " + filename);
							} else {
								Matcher match = filePattern.matcher(filename);
								if (!match.find()) {
									errors.add("Invalid file name: " + filename);
								} else {

									validateComponentSelectorFormat(match.group("cs1"), 1, filename, errors);
									validateComponentSelectorFormat(match.group("cs2"), 2, filename, errors);
									validateFeatureCode(match.group("featureCode"), file, errors);
									
									if (match.group("fsc").length() != 3) {
										errors.add("Feature Sub-Code should be 3 digits: " + filename);
									}
	
									try {
										Integer fsc = Integer.parseInt(match.group("fsc"));
	
										if (((fsc < 10) && !match.group("fsc").substring(0,2).equals("00")) ||
												((fsc < 100) && !match.group("fsc").substring(0,1).equals("0"))) {
											errors.add("Invalid padding on FSC: " + filename);
										}
									}
									catch (NumberFormatException e) {
										errors.add("Invalid FSC number format: " + filename);
									}
									catch (StringIndexOutOfBoundsException e) {
										errors.add("Invalid FSC length: " + filename);
									}
									
									if (match.group("modl").length() > 32) {
										errors.add("Model name should not exceed 32 characters: " + filename);
									}
									
									if (!match.group("ext").equals("flt")) {
										errors.add("File extension must be flt: " + filename);
									}
	
								}
							}
						}
					}
				}
			}
		}

		Assert.assertTrue(errors.size() == 0, StringUtils.join(errors, "\n"));
	}

}
