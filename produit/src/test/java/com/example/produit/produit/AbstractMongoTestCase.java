package com.example.produit.produit;

import static de.flapdoodle.embed.mongo.distribution.Version.V2_6_11;
import static de.flapdoodle.embed.mongo.distribution.Version.V2_6_8;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongoImportExecutable;
import de.flapdoodle.embed.mongo.MongoImportStarter;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongoImportConfig;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoImportConfigBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.runtime.Network;

public abstract class AbstractMongoTestCase {

	private static final Version MONGO_VERSION = V2_6_11;

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractMongoTestCase.class);

	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongodProcess;

	private static int port;

	protected static void setUp() throws Exception {
		if (_mongodProcess == null) {
			setMongoPort();
			
			_mongodExe = MongodStarter.getInstance(getRuntimeConfig(Command.MongoD)).prepare(createMongodConfig());
			_mongodProcess = _mongodExe.start();
	
		}
	}

	private static void setMongoPort() throws IOException {
		port = Network.getFreeServerPort();
		System.setProperty("mongo.test.port", String.valueOf(port));
	}

	public int port() {
		return port;
	}

	protected static IMongodConfig createMongodConfig() throws UnknownHostException, IOException {
	    return createMongodConfigBuilder().build();
	  }

	protected static MongodConfigBuilder createMongodConfigBuilder() throws UnknownHostException, IOException {
	    return new MongodConfigBuilder()
	      .version(MONGO_VERSION)
	      .net(new Net(port, Network.localhostIsIPv6()));
	}
		

	protected static void tearDown() throws Exception {
		if (_mongodProcess != null) {
			_mongodProcess.stop();
			// on dereference le mongo process
			_mongodProcess = null;
		}

		if (_mongodExe != null) {
			_mongodExe.stop();
		}
	}

	private static void loadFixtures(String database, String collection, String jsonFile, boolean upsert, boolean drop,
			boolean jsonArray) throws UnknownHostException, IOException {

		File dataFile = new File(jsonFile);

		LOGGER.info("File = {}", dataFile.getAbsolutePath());
		LOGGER.info("jsonArray = {}, drop = {}", jsonArray, drop);
		IMongoImportConfig mongoImportConfig = new MongoImportConfigBuilder()
				.version(MONGO_VERSION)
				.net(new Net(port, Network.localhostIsIPv6()))
				.db(database)
				.collection(collection)
				.upsert(upsert)
				.dropCollection(drop)
				.jsonArray(jsonArray)
				.importFile(dataFile.getAbsolutePath())
				.build();

		LOGGER.info("mongoVersion = {}", MONGO_VERSION);
		MongoImportExecutable mongoImportExecutable = MongoImportStarter.getInstance(getRuntimeConfig(Command.MongoImport))
				.prepare(mongoImportConfig);
		mongoImportExecutable.start();
	}

	private static IRuntimeConfig getRuntimeConfig(Command mongoimport) {
		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
				.defaultsWithLogger(mongoimport, LOGGER)
				.artifactStore(new ExtractedArtifactStoreBuilder()
						.defaults(mongoimport))
				.build();
		return runtimeConfig;
	}

	public static void loadFixtures(String database, String collection, String jsonFile)
			throws UnknownHostException, IOException {
		loadFixtures(database, collection, new ClassPathResource(jsonFile).getFile().getAbsolutePath(), Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);

	}

}
