package org.opengis.cite.cdb10.cdbStructure.GSModel;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class VerifyGSModelInteriorTextureStructureTests extends GSModelStructureTests<GSModelInteriorTextureStructureTests> {
	protected static final String VALID_ARCHIVE_NAME = "N62W162_D306_S001_T001_L07_U38_R102.zip";
	protected static final String VALID_ENTRY_NAME = "N62W162_D306_S001_T001_L07_U38_R102_AcmeFactoryWall.rgb";
	
	public VerifyGSModelInteriorTextureStructureTests() throws IOException {
		this.testSuite = new GSModelInteriorTextureStructureTests();
	}
	
	/**
	 * Creates a Path for a GSModelInteriorTexture archive with a custom filename.
	 * Filename must include file extension. Archive will be placed in:
	 * CDB Root > Tiles > N99 > W162 > 306_GSModelInteriorTexture > Lod > Uref
	 * 
	 * @param archiveFilename
	 * @return Path for GSModelInteriorTexture archive file
	 * @throws IOException 
	 */
	protected Path createGSModelInteriorTextureArchive(String archiveFilename) throws IOException {
		return createGSModelArchive(GSModelInteriorTextureStructureTests.DATASET_DIRECTORY, archiveFilename);
	}

	/*
	 * Verify GS Model Interior Texture filenames
	 */
	@Test
	public void verifyGSModelInteriorTextureFile_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalid() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("0.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid file name");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidLatitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N99W162_D306_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N99)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidLongitude() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W999_D306_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W999)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidDataset() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D000_S001_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidCS1() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S000_T001_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (306)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidCS2() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S001_T000_L07_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (306)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidLod() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S001_T001_L99_U38_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidUref() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S001_T001_L07_U999_R102.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidRref() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S001_T001_L07_U38_R9999.zip");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFile_invalidExt() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive("N62W162_D306_S001_T001_L07_U38_R102.7z");
		Files.copy(EMPTY_ZIP, archive, REPLACE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: 7z");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFile();
	}
	
	/*
	 * Verify GS Model Interior Texture archive
	 */
	@Test
	public void verifyGSModelInteriorTextureFileArchive_valid() throws IOException {
		// setup
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		Files.copy(EMPTY_ZIP, archive);

		// execute
		this.testSuite.verifyGSModelInteriorTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFileArchive_zeroZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[0];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Zero-length ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFileArchive_tooBigZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[32000001];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("ZIP archive exceeds 32 Megabytes");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFileArchive_notAZip() throws IOException {
		// setup
		Path archivePath = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		byte[] bytes = new byte[100];
		Files.write(archivePath, bytes, CREATE, TRUNCATE_EXISTING);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid ZIP archive file");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFileArchive();
	}
	
	@Test
	public void verifyGSModelInteriorTextureFileArchive_compressedEntry() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithCompressedEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("should not be compressed");

		// execute
		this.testSuite.verifyGSModelInteriorTextureFileArchive();
	}
	
	/*
	 * Verify GS Model Interior Texture entries
	 */
	@Test
	public void verifyGSModelInteriorTextureEntry_valid() throws IOException {
		// setup
		String filename = VALID_ENTRY_NAME;
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalid() throws IOException {
		// setup
		String filename = "0.flt";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid entry '0.flt' in ZIP archive");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidLatitude() throws IOException {
		// setup
		String filename = "N92W162_D306_S001_T001_L07_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid latitude (N92)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidLongitude() throws IOException {
		// setup
		String filename = "N62W962_D306_S001_T001_L07_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid longitude (W962)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidDataset() throws IOException {
		// setup
		String filename = "N62W162_D000_S001_T001_L07_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid code 000 in entry");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidCS1() throws IOException {
		// setup
		String filename = "N62W162_D306_S000_T001_L07_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 1 (000) for Dataset (306)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidCS2() throws IOException {
		// setup
		String filename = "N62W162_D306_S001_T000_L07_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid Component Selector 2 (000) for CS1 (001) and Dataset (306)");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidLod() throws IOException {
		// setup
		String filename = "N62W162_D306_S001_T001_L99_U38_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid LOD name: L99");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidUref() throws IOException {
		// setup
		String filename = "N62W162_D306_S001_T001_L07_U999_R102_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("UREF value out of bounds: 999");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidRref() throws IOException {
		// setup
		String filename = "N62W162_D306_S001_T001_L07_U38_R9999_AcmeFactoryWall.rgb";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("RREF out of bounds for LOD: 9999");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
	
	@Test
	public void verifyGSModelInteriorTextureEntry_invalidExt() throws IOException {
		// setup
		String filename = "N62W162_D306_S001_T001_L07_U38_R102_AcmeFactoryWall.txt";
		Path archive = createGSModelInteriorTextureArchive(VALID_ARCHIVE_NAME);
		createArchiveWithEntryNamed(archive, filename);
		
		expectedException.expect(AssertionError.class);
        expectedException.expectMessage("Invalid archive extension: txt");

		// execute
		this.testSuite.verifyGSModelInteriorTextureEntry();
	}
}
